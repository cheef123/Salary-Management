package com.cognizant.upload.exception;

/**
 * This class is used for handling exception. It will be thrown when an existing login is found in database
 * @author cheef
 *
 */
public class NonUniqueLoginException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NonUniqueLoginException(String message) {
		super(message);
	}

}
