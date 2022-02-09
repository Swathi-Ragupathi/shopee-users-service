package com.learn.microservices.shopeeusersservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {
	
	private String streetLine1;
	private String streetLine2;
	private String postalCode;
	private String country;
	private String state;
	private String city;
	private boolean primaryAddress;
	
		
	
}
