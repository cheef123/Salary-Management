package com.cognizant.upload.exception;

/**
 * This class is used for handling exception. It will be thrown when concurrent upload is attempted
 * @author cheef
 *
 */
public class ConcurrentUploadException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ConcurrentUploadException(String message) {
		super(message);
	}

}
