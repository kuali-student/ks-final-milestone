package org.kuali.student.core.exceptions;

public class DoesNotExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public DoesNotExistException() {
        super();
    }

    public DoesNotExistException(String message) {
        super(message);
    }

}
