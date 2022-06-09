package com.cognizant.upload.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.cognizant.upload.pojo.CustomErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<CustomErrorResponse> handleMaxSizeException(MaxUploadSizeExceededException ex){
		log.info("Start of MaxUploadSizeExceededException");
		CustomErrorResponse response = new CustomErrorResponse(LocalDateTime.now(),HttpStatus.EXPECTATION_FAILED,"Size of file is too large!",ex.getMessage());
		log.info("End of MaxUploadSizeExceededException");
		return new ResponseEntity<CustomErrorResponse>(response,HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<CustomErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex){
		log.info("Start of IllegalArgumentException");
		CustomErrorResponse response = new CustomErrorResponse(LocalDateTime.now(),HttpStatus.EXPECTATION_FAILED,"Illegal Argument",ex.getMessage());
		log.info("End of IllegalArgumentException");
		return new ResponseEntity<CustomErrorResponse>(response,HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(NonUniqueIdException.class)
	public ResponseEntity<CustomErrorResponse> handleNonUniqueIdException(NonUniqueIdException ex){
		log.info("Start of NonUniqueIdException");
		CustomErrorResponse response = new CustomErrorResponse(LocalDateTime.now(),HttpStatus.EXPECTATION_FAILED,"Id must be unique! Cannot be repeated in another row!",ex.getMessage());
		log.info("End of NonUniqueIdException");
		return new ResponseEntity<CustomErrorResponse>(response,HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(NonUniqueLoginException.class)
	public ResponseEntity<CustomErrorResponse> handleNonUniqueLoginException(NonUniqueLoginException ex){
		log.info("Start of NonUniqueLoginException");
		CustomErrorResponse response = new CustomErrorResponse(LocalDateTime.now(),HttpStatus.EXPECTATION_FAILED,"Login details must be unique! Cannot be repeated in another row!",ex.getMessage());
		log.info("End of NonUniqueLoginException");
		return new ResponseEntity<CustomErrorResponse>(response,HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<CustomErrorResponse> handleNullPointerException(NullPointerException ex){
		log.info("Start of NullPointerException");
		CustomErrorResponse response = new CustomErrorResponse(LocalDateTime.now(),HttpStatus.EXPECTATION_FAILED,"All columns must be filled!",ex.getMessage());
		log.info("End of NullPointerException");
		return new ResponseEntity<CustomErrorResponse>(response,HttpStatus.EXPECTATION_FAILED);
	}
	
	
	@ExceptionHandler(LoginConflictException.class)
	public ResponseEntity<CustomErrorResponse> handleLoginConflictException(LoginConflictException ex){
		log.info("Start of LoginConflictException");
		CustomErrorResponse response = new CustomErrorResponse(LocalDateTime.now(),HttpStatus.FORBIDDEN,"Login conflict! Consider complex use case if necessary",ex.getMessage());
		log.info("End of LoginConflictException");
		return new ResponseEntity<CustomErrorResponse>(response,HttpStatus.FORBIDDEN);
	}
	

}
