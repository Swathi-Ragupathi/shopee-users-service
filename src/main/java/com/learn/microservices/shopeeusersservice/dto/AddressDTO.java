package com.learn.microservices.shopeeusersservice.dto;

import java.util.Date;

import com.learn.microservices.shopeeusersservice.annotation.IgnoreCopy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

	private UserInfoDTO userInfoDTO;
	@IgnoreCopy
	private Long userInfoId;
	@IgnoreCopy
	private Long id;
	private boolean primaryAddress;
	private String streetLine1;
	private String streetLine2;
	private String postalCode;
	private String country;
	private String state;
	private String city;
	@IgnoreCopy
	private Date createdDate;
	@IgnoreCopy
	private Date modifiedDate;
	
}
