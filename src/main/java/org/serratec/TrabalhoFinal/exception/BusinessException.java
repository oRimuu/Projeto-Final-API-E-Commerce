package org.serratec.TrabalhoFinal.exception;


public class BusinessException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3259076521998467959L;

	public BusinessException(String message) {
        super(message);
    }
}