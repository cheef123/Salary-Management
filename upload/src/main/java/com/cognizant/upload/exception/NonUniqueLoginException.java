package com.cognizant.upload.exception;

public class NonUniqueLoginException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NonUniqueLoginException(String message) {
		super(message);
	}

}
