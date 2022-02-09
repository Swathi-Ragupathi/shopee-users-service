package com.learn.microservices.shopeeusersservice.constants;

import org.springframework.http.HttpStatus;

public enum ErrorConstants {

	UNABLE_TO_PROCESS("1000", "Failure Occurs", HttpStatus.INTERNAL_SERVER_ERROR),
	DUPLICATE_EMAIL("1001", "Unable to register!! User with same email already exists", HttpStatus.CONFLICT),
	USER_NOT_FOUND("1002", "User is unavailable", HttpStatus.NOT_FOUND),
	ADDRESS_NOT_FOUND("1003", "Requested Address is unavailable", HttpStatus.NOT_FOUND),
	MISSING_PRIMARY_ADDRESS("1004", "Error!! Current address is marked as Primary address. "
			+ "Please consider adding the Primary address before processing this request", HttpStatus.BAD_REQUEST);

	String code;
	String message;
	HttpStatus httpStatus;

	ErrorConstants(String code, String message, HttpStatus statusCode) {
		this.code = code;
		this.message = message;
		this.httpStatus = statusCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus statusCode) {
		this.httpStatus = statusCode;
	}

}
