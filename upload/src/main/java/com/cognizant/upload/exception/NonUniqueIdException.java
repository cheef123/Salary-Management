package com.cognizant.upload.exception;

/**
 * This class is used for handling exception. It will be thrown when an existing id is found in database
 * @author cheef
 *
 */
public class NonUniqueIdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NonUniqueIdException(String message) {
		super(message);
	}
	
}
