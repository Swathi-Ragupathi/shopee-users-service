package com.learn.microservices.shopeeusersservice.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRequest {
	
	@NotBlank(message = "Name cannot be empty")
	private String firstName;
	private String lastName;
	@Email(message = "Enter valid email")
	private String emailAddress;
	private String password;
	private String phoneNumber;
	private String alternatePhoneNumber;
	private String streetLine1;
	private String streetLine2;
	private String postalCode;
	private String country;
	private String state;
	private String city;
	private boolean primaryAddress;
	
		
	
}
