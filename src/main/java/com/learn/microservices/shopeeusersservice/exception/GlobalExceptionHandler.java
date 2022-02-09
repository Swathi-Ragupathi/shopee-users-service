package com.learn.microservices.shopeeusersservice.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.learn.microservices.shopeeusersservice.response.ErrorResponse;
import com.learn.microservices.shopeeusersservice.response.ValidationErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(UserServiceException.class)
	protected ResponseEntity<?> handleGlobalException(UserServiceException userException, Locale locale) {
		return ResponseEntity.status(userException.getStatusCode()).body(
				ErrorResponse.builder().code(userException.getCode()).message(userException.getMessage()).build());
	}

	@ExceptionHandler({ Exception.class })
	protected ResponseEntity<?> handleException(Exception e, Locale locale) {
		return ResponseEntity.internalServerError().body("Exception occur inside API " + e);
	}
	
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ValidationErrorResponse error = new ValidationErrorResponse("Validation Failed", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return ResponseEntity.badRequest().body(ErrorResponse.builder().code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
				.message("Input Type mismatch for parameter: " + ex.getValue()).build());
	}
}
