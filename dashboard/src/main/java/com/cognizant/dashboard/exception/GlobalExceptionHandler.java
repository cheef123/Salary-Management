package com.cognizant.dashboard.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cognizant.dashboard.pojo.CustomErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<CustomErrorResponse> handleResourceNotFoundException(EmployeeNotFoundException ex){
		CustomErrorResponse response = new CustomErrorResponse(LocalDateTime.now(),HttpStatus.EXPECTATION_FAILED,"Resource not found!",ex.getMessage());
		return new ResponseEntity<CustomErrorResponse>(response,HttpStatus.EXPECTATION_FAILED);
	}
}	