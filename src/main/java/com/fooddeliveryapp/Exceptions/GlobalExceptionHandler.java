package com.fooddeliveryapp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fooddeliveryapp.Response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler({ResourceNotFoundException.class})
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionhandler(ResourceNotFoundException ex){
		String message = ex.getMessage();
		ApiResponse response = new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler({UsernameNotFoundException.class})
	public ResponseEntity<ApiResponse> usernameNotFoundException(UsernameNotFoundException ex){
		String message = ex.getMessage();
		ApiResponse response = new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
	}

}
