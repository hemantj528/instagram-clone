package com.insta.clone.instagram.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.insta.clone.instagram.exception.AuthorizationException;
import com.insta.clone.instagram.exception.UserWithGivenUserNameIsAlreadyRegistered;
import com.insta.clone.instagram.exception.WrongUserNameOrPassword;
import com.insta.clone.instagram.model.ErrorResponse;

@RestControllerAdvice
public class MyControllerAdvice {

	@ExceptionHandler(UserWithGivenUserNameIsAlreadyRegistered.class)
	public ResponseEntity<ErrorResponse> handleUserWithGivenUserNameIsAlreadyRegistered(UserWithGivenUserNameIsAlreadyRegistered e){
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.OK, e.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(WrongUserNameOrPassword.class)
	public ResponseEntity<ErrorResponse> handleWrongUserNameOrPassword(WrongUserNameOrPassword e){
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		Map<String, String> errors = new HashMap<>();
	    e.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<ErrorResponse> handleAuthorizationException(AuthorizationException e){
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}
}
