package com.learn.microservices.shopeeusersservice.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.microservices.shopeeusersservice.config.CustomBeanUtils;
import com.learn.microservices.shopeeusersservice.constants.ErrorConstants;
import com.learn.microservices.shopeeusersservice.dto.AddressDTO;
import com.learn.microservices.shopeeusersservice.exception.UserServiceException;
import com.learn.microservices.shopeeusersservice.mapper.EntityToDtoMapper;
import com.learn.microservices.shopeeusersservice.model.Address;
import com.learn.microservices.shopeeusersservice.model.UserInfo;
import com.learn.microservices.shopeeusersservice.repository.AddressRepository;
import com.learn.microservices.shopeeusersservice.request.AddressRequest;

@Service
public class AddressService {

	@Autowired
	private UsersService usersService;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private EntityToDtoMapper entityToDtoMapper;

	public UserInfo addAdress(Long userInfoId, AddressRequest addressRequest) {
		UserInfo existinguser = usersService.userEntityById(userInfoId);
		if (existinguser == null) {
			throw new UserServiceException(ErrorConstants.USER_NOT_FOUND);
		}
		try {
			Address newAddress = new Address();
			CustomBeanUtils.copyProperties(addressRequest, newAddress);
			newAddress.setUserInfo(existinguser);
			addressRepository.save(newAddress);
			return usersService.userEntityById(userInfoId);
		} catch (Exception e) {
			throw new UserServiceException(ErrorConstants.UNABLE_TO_PROCESS);
		}
	}

	public Set<AddressDTO> getAllAddressByUserInfoId(Long userInfoId) {
		UserInfo existinguser = usersService.userEntityById(userInfoId);
		if (existinguser == null) {
			throw new UserServiceException(ErrorConstants.USER_NOT_FOUND);
		}
		return entityToDtoMapper.addressEntityToDTO(existinguser.getContactAddress());

	}

	public AddressDTO getAddressById(Long userInfoId, Long addressId) {
		UserInfo existinguser = usersService.userEntityById(userInfoId);
		if (existinguser == null) {
			throw new UserServiceException(ErrorConstants.USER_NOT_FOUND);
		}

		Address address = addressRepository.getById(addressId);
		if (address == null) {
			throw new UserServiceException(ErrorConstants.ADDRESS_NOT_FOUND);
		}

		return entityToDtoMapper.addressEntityToDTO(address);
	}

	public AddressDTO updateAddressById(Long userInfoId, Long addressId, AddressRequest addressRequest) {

		UserInfo existinguser = usersService.userEntityById(userInfoId);
		if (existinguser == null) {
			throw new UserServiceException(ErrorConstants.USER_NOT_FOUND);
		}

		Address addressToEdit = addressRepository.getById(addressId);
		if (addressToEdit == null) {
			throw new UserServiceException(ErrorConstants.ADDRESS_NOT_FOUND);
		}

		// if current address is primary but modified to remove the primary type, then
		// throw error
		if (addressToEdit.isPrimaryAddress() && !addressRequest.isPrimaryAddress()) {
			throw new UserServiceException(ErrorConstants.MISSING_PRIMARY_ADDRESS);
		}
		try {
			CustomBeanUtils.copyProperties(addressRequest, addressToEdit);
			addressRepository.save(addressToEdit);
		} catch (Exception e) {
			throw new UserServiceException(ErrorConstants.UNABLE_TO_PROCESS);
		}
		return entityToDtoMapper.addressEntityToDTO(addressToEdit);
	}

	public boolean deleteAddress(Long userInfoId, Long addressId) {
		UserInfo existinguser = usersService.userEntityById(userInfoId);
		if (existinguser == null) {
			throw new UserServiceException(ErrorConstants.USER_NOT_FOUND);
		}

		Address addressToEdit = addressRepository.getById(addressId);
		if (addressToEdit == null) {
			throw new UserServiceException(ErrorConstants.ADDRESS_NOT_FOUND);
		}

		// if current address is primary but modified to remove the primary type, then
		// throw error
		if (addressToEdit.isPrimaryAddress()) {
			throw new UserServiceException(ErrorConstants.MISSING_PRIMARY_ADDRESS);
		}

		addressRepository.delete(addressToEdit);
		return true;

	}
}
