package org.serratec.TrabalhoFinal.exception;

public class ResourceNotFoundException extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7357731209981227631L;

	public ResourceNotFoundException(String message) {
        super(message);
    }
}
