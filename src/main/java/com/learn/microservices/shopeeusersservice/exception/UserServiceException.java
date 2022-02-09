package com.learn.microservices.shopeeusersservice.exception;

import org.springframework.http.HttpStatus;

import com.learn.microservices.shopeeusersservice.constants.ErrorConstants;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserServiceException extends RuntimeException {

	private String code;
	private String message;
	private String status = HttpStatus.INTERNAL_SERVER_ERROR.name();
	private int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

	public UserServiceException(ErrorConstants error) {
		this.code = error.getCode();
		this.message = error.getMessage();
		this.statusCode = error.getHttpStatus().value();
		this.status = error.getHttpStatus().name();
	}

	public UserServiceException(String message) {
		this.message = message;
	}

}
