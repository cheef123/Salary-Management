package com.cognizant.dashboard.pojo;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
*  This class we are using as a response of error handling message.
*          In the {@link GlobalErrorHandler} class we are using this
*          {@link CustomErrorResponse} class as a return type that will be
*          shown to the client whenever any kind of exception occurs. The
*          fields of this class will be used to show this kind of response.
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {
	/**
	 * This field will show the time when the exception occurs
	 */
	private LocalDateTime timestamp; 
	
	/**
	 * This field will show the status
	 */
	private HttpStatus status;
	/**
	 * This field represents the reason why the exception occurs
	 */
	private String reason;
	
	/**
	 * This field will represent the message which will be set in the constructor of
	 * the exception class
	 */
	private String message;
}
