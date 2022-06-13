package com.cognizant.upload.exception;

/**
 * This class is used for handling exception. It will be thrown when login exist but being tied to a different id 
 * @author cheef
 *
 */
public class LoginConflictException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LoginConflictException(String message) {
		super(message);
	}
}
