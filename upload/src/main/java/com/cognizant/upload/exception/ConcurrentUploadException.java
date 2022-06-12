package com.cognizant.upload.exception;

public class ConcurrentUploadException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ConcurrentUploadException(String message) {
		super(message);
	}

}
