package com.learn.microservices.shopeeusersservice.mapper;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.learn.microservices.shopeeusersservice.config.CustomBeanUtils;
import com.learn.microservices.shopeeusersservice.constants.AccountStatusEnum;
import com.learn.microservices.shopeeusersservice.dto.AddressDTO;
import com.learn.microservices.shopeeusersservice.dto.UserInfoDTO;
import com.learn.microservices.shopeeusersservice.model.Address;
import com.learn.microservices.shopeeusersservice.model.UserInfo;

@Component
public class DtoToEntityMapper {

	public UserInfo userInfoDtoToEntity(UserInfoDTO userInfoDTO) throws Exception {
		UserInfo userInfoEntity = new UserInfo();
		userInfoEntity.setAlternatePhoneNumber(userInfoDTO.getAlternatePhoneNumber());
		userInfoEntity.setActive(userInfoDTO.isActive());
		userInfoEntity.setActivationStatus(userInfoDTO.getActivationStatus());
		userInfoEntity.setEmailAddress(userInfoDTO.getEmailAddress());
		userInfoEntity.setFirstName(userInfoDTO.getFirstName());
		CustomBeanUtils.copyProperties(userInfoDTO, userInfoEntity);
		userInfoEntity.setActivationStatus(AccountStatusEnum.CREATED.name());
	
		if (!CollectionUtils.isEmpty(userInfoDTO.getContactAddresses())) {
			userInfoDTO.getContactAddresses()
					.forEach(addrDTO -> userInfoEntity.putContactAddress(addressDtoToEntity(addrDTO, userInfoEntity)));
			
		}
		return userInfoEntity;
	}
	
	public Address addressDtoToEntity(AddressDTO addressDTO, UserInfo userInfoEntity) {
		Address addressEntity = new Address();
		CustomBeanUtils.copyProperties(addressDTO, addressEntity);
		addressEntity.setUserInfo(userInfoEntity);
		return addressEntity;
	}
}
