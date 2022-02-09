package com.learn.microservices.shopeeusersservice.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.learn.microservices.shopeeusersservice.annotation.IgnoreCopy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
	
	@IgnoreCopy
	private Long id;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String password;
	private String phoneNumber;
	private String alternatePhoneNumber;
	private String activationStatus;
	private boolean active;
	private Set<AddressDTO> contactAddresses = new HashSet<>();
	@IgnoreCopy
	private Date createdDate;
	@IgnoreCopy
	private Date modifiedDate;
	
	public void putContactAddress(AddressDTO addressDTO){
		contactAddresses.add(addressDTO);
	}
		
	
}
