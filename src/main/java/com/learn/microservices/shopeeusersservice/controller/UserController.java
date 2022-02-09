package com.learn.microservices.shopeeusersservice.controller;

import java.util.List;

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
import com.learn.microservices.shopeeusersservice.dto.UserInfoDTO;
import com.learn.microservices.shopeeusersservice.exception.UserServiceException;
import com.learn.microservices.shopeeusersservice.request.UserInfoRequest;
import com.learn.microservices.shopeeusersservice.response.ResponseData;
import com.learn.microservices.shopeeusersservice.service.UsersService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UsersService usersService;

	@PostMapping("/register")
	public ResponseEntity<ResponseData> register(@RequestBody @Valid UserInfoRequest userInfoRequest) {
		usersService.register(userInfoRequest);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseData(HttpStatus.CREATED.name(), "User Created Successfully"));
	}

	@GetMapping("/all")
	public ResponseEntity<List<UserInfoDTO>> allUsers() {
		return ResponseEntity.ok().body(usersService.getAllUsers());
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserInfoDTO> userById(@PathVariable("id") Long userId) {
		return ResponseEntity.ok().body(usersService.getUserById(userId));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserInfoDTO> updateUser(@PathVariable("id") Long userId,
			@RequestBody UserInfoRequest userInfoRequest) {
		return ResponseEntity.ok().body(usersService.updateUser(userId, userInfoRequest));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseData> disableUser(@PathVariable("id") Long userId) {
		if(usersService.disableUser(userId)) {
			return ResponseEntity.ok()
					.body(new ResponseData(HttpStatus.OK.name(), "User Delete Successfully"));
		}
		throw new UserServiceException(ErrorConstants.UNABLE_TO_PROCESS);
		
	}
}
