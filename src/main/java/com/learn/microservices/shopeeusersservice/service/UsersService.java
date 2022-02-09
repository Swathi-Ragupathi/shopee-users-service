package com.learn.microservices.shopeeusersservice.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.learn.microservices.shopeeusersservice.config.CustomBeanUtils;
import com.learn.microservices.shopeeusersservice.constants.AccountStatusEnum;
import com.learn.microservices.shopeeusersservice.constants.ErrorConstants;
import com.learn.microservices.shopeeusersservice.dto.AddressDTO;
import com.learn.microservices.shopeeusersservice.dto.UserInfoDTO;
import com.learn.microservices.shopeeusersservice.exception.UserServiceException;
import com.learn.microservices.shopeeusersservice.mapper.DtoToEntityMapper;
import com.learn.microservices.shopeeusersservice.mapper.EntityToDtoMapper;
import com.learn.microservices.shopeeusersservice.model.Address;
import com.learn.microservices.shopeeusersservice.model.UserInfo;
import com.learn.microservices.shopeeusersservice.repository.UserInfoRepository;
import com.learn.microservices.shopeeusersservice.request.UserInfoRequest;

@Service
public class UsersService {

	@Autowired
	private UserInfoRepository userInfoRepo;

	@Autowired
	private PasswordService passwordService;

	@Autowired
	private DtoToEntityMapper dtoToEntityMapper;

	@Autowired
	private EntityToDtoMapper entityToDtoMapper;

	public UserInfo register(UserInfoRequest userInfoRequest) {
		Optional<UserInfo> existinguser = userInfoRepo.findByEmailAddress(userInfoRequest.getEmailAddress());
		if (existinguser.isPresent()) {
			throw new UserServiceException(ErrorConstants.DUPLICATE_EMAIL);
		}
		try {
			UserInfoDTO userInfoDTO = new UserInfoDTO();
			AddressDTO addressDTO = new AddressDTO();
			CustomBeanUtils.copyProperties(userInfoRequest, userInfoDTO);
			CustomBeanUtils.copyProperties(userInfoRequest, addressDTO);
			userInfoDTO.putContactAddress(addressDTO);
			userInfoDTO.setActivationStatus(AccountStatusEnum.CREATED.name());
			userInfoDTO.setActive(true);
			userInfoDTO.setPassword(passwordService.passwordEncryption(userInfoDTO.getPassword()));
			UserInfo userInfo = dtoToEntityMapper.userInfoDtoToEntity(userInfoDTO);
			return userInfoRepo.save(userInfo);
		} catch (Exception e) {
			throw new UserServiceException(ErrorConstants.UNABLE_TO_PROCESS);
		}
	}

	public List<UserInfoDTO> getAllUsers() {
		List<UserInfo> userInfoList = userInfoRepo.findAll();
		if (CollectionUtils.isEmpty(userInfoList)) {
			return Collections.<UserInfoDTO>emptyList();
		}
		return entityToDtoMapper.userInfoEntityToDto(userInfoList);

	}

	public UserInfoDTO getUserById(Long userId) {
		UserInfo activeUserInfoById = userEntityById(userId);

		return entityToDtoMapper.userInfoEntityToDto(activeUserInfoById);
	}

	public UserInfoDTO updateUser(Long userId, UserInfoRequest userInfoRequest) {
		UserInfo userInfo = userEntityById(userId);
		try {

			// use existing password if password is empty in request else encrypt
			userInfoRequest.setPassword(StringUtils.hasLength(userInfoRequest.getPassword())
					? passwordService.passwordEncryption(userInfoRequest.getPassword())
					: userInfo.getPassword());

			Address primaryAddress = userInfo.getContactAddress().stream().filter(address -> address.isPrimaryAddress())
					.findFirst().get();
			CustomBeanUtils.copyProperties(userInfoRequest, primaryAddress);
			CustomBeanUtils.copyProperties(userInfoRequest, userInfo);
			userInfo = userInfoRepo.save(userInfo);
		} catch (Exception e) {
			throw new UserServiceException(ErrorConstants.UNABLE_TO_PROCESS);
		}
		return entityToDtoMapper.userInfoEntityToDto(userInfo);
	}

	public boolean disableUser(Long userId) {
		UserInfo activeUserInfoById = userEntityById(userId);
		activeUserInfoById.setActive(false);
		activeUserInfoById.setActivationStatus(AccountStatusEnum.DISABLED.name());
		userInfoRepo.save(activeUserInfoById);
		return true;

	}

	public UserInfo userEntityById(Long userId) {
		UserInfo activeUserInfoById = userInfoRepo.findByIdAndActiveIsTrue(userId);

		if (activeUserInfoById == null) {
			throw new UserServiceException(ErrorConstants.USER_NOT_FOUND);
		}
		return activeUserInfoById;
	}
}
