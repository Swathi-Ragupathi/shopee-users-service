package com.learn.microservices.shopeeusersservice.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.microservices.shopeeusersservice.constants.ErrorConstants;
import com.learn.microservices.shopeeusersservice.dto.AddressDTO;
import com.learn.microservices.shopeeusersservice.exception.UserServiceException;
import com.learn.microservices.shopeeusersservice.request.AddressRequest;
import com.learn.microservices.shopeeusersservice.response.ResponseData;
import com.learn.microservices.shopeeusersservice.service.AddressService;

@RestController
@RequestMapping("/api/address")
public class AddressController {

	@Autowired
	private AddressService addressService;

	@PostMapping("/add/{userInfoId}")
	public ResponseEntity<ResponseData> addAdress(@PathVariable("userInfoId") Long userInfoId,@RequestBody @Valid AddressRequest addressRequest) {
		addressService.addAdress(userInfoId, addressRequest);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseData(HttpStatus.CREATED.name(), "Address added Successfully"));
	}

	@GetMapping("/all/{userInfoId}")
	public ResponseEntity<Set<AddressDTO>> allAddress(@PathVariable("userInfoId") Long userInfoId) {
		return ResponseEntity.ok().body(addressService.getAllAddressByUserInfoId(userInfoId));
	}

	@GetMapping("/{userInfoId}/{addressId}")
	public ResponseEntity<AddressDTO> addressById(@PathVariable("userInfoId") Long userId, @PathVariable("addressId") Long addressId) {
		return ResponseEntity.ok().body(addressService.getAddressById(userId, addressId));
	}
	
	@PutMapping("/{userInfoId}/{addressId}")
	public ResponseEntity<AddressDTO> updateAddress(@PathVariable("userInfoId") Long userInfoId,@PathVariable("addressId") Long addressId,@RequestBody @Valid AddressRequest addressRequest) {
		return ResponseEntity.ok().body(addressService.updateAddressById(userInfoId, addressId, addressRequest));
	}

	@DeleteMapping("/{userInfoId}/{addressId}")
	public ResponseEntity<ResponseData> deleteAddress(@PathVariable("userInfoId") Long userInfoId,@PathVariable("addressId") Long addressId) {
		if(addressService.deleteAddress(userInfoId, addressId)) {
			return ResponseEntity.ok()
					.body(new ResponseData(HttpStatus.OK.name(), "User Delete Successfully"));
		}
		throw new UserServiceException(ErrorConstants.UNABLE_TO_PROCESS);
		
	}
}
