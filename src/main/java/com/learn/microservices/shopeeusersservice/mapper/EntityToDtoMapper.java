package com.learn.microservices.shopeeusersservice.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.learn.microservices.shopeeusersservice.config.CustomBeanUtils;
import com.learn.microservices.shopeeusersservice.dto.AddressDTO;
import com.learn.microservices.shopeeusersservice.dto.UserInfoDTO;
import com.learn.microservices.shopeeusersservice.model.Address;
import com.learn.microservices.shopeeusersservice.model.UserInfo;

@Component
public class EntityToDtoMapper {

	public List<UserInfoDTO> userInfoEntityToDto(List<UserInfo> userInfoEntities) {

		return userInfoEntities.stream().map(userInfoEnt -> userInfoEntityToDto(userInfoEnt))
				.collect(Collectors.toList());
	}

	public UserInfoDTO userInfoEntityToDto(UserInfo userInfoEntity) {
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		CustomBeanUtils.copyProperties(userInfoEntity, userInfoDTO);
		Set<AddressDTO> addressDTOSet = addressEntityToDTO(userInfoEntity.getContactAddress());
		userInfoDTO.setContactAddresses(addressDTOSet);
		return userInfoDTO;
	}

	public Set<AddressDTO> addressEntityToDTO(Set<Address> addressEntities) {
		if (CollectionUtils.isEmpty(addressEntities)) {
			return new HashSet<>();
		}
		return addressEntities.stream().map(addressEnt -> addressEntityToDTO(addressEnt)).collect(Collectors.toSet());
	}

	public AddressDTO addressEntityToDTO(Address addressEntity) {
		AddressDTO addressDTO = new AddressDTO();
		CustomBeanUtils.copyProperties(addressEntity, addressDTO);
		addressDTO.setUserInfoId(addressEntity.getUserInfo().getId());
		return addressDTO;
	}
}
