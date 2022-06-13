package com.cognizant.upload.exception;

/**
 * This class is used for handling exception. It will be thrown when salary is not of a decimal format
 * @author cheef
 *
 */
public class SalaryFormatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SalaryFormatException(String message) {
		super(message);
	}
}
