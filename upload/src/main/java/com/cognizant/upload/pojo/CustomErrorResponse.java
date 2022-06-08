package com.cognizant.upload.pojo;

import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {
	/**
	 * This field will show the time when the exception occurs
	 */
	private LocalDateTime timestamp; 
	
	/**
	 * This field will represent the message which will be set in the constructor of
	 * the exception class
	 */
	private String message;
}
