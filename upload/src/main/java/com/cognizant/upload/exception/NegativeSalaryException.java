package com.cognizant.upload.exception;
/**
 * This class is used for handling exception. It will be thrown when value of salary is negative
 * @author cheef
 *
 */
public class NegativeSalaryException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NegativeSalaryException(String message) {
		super(message);
	}

}
