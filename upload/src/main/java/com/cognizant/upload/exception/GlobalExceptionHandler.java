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
		return new ResponseEntity<CustomErrorResponse>(response,HttpStatus.EXPECTATION_FAILED);
	}

}
