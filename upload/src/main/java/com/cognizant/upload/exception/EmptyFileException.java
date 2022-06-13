package com.cognizant.upload.exception;

/**
 * This class is used for handling exception. It will be thrown when uploaded csv file is empty
 * @author cheef
 *
 */
public class EmptyFileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EmptyFileException(String message) {
		super(message);
	}

}
