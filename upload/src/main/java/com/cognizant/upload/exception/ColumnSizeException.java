package com.cognizant.upload.exception;

/**
 * This class is used for handling exception. It will be thrown when column size is less than or more than 4
 * @author cheef
 *
 */

public class ColumnSizeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ColumnSizeException(String message) {
		super(message);
	}

}
