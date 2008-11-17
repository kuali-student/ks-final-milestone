package org.kuali.student.common.ws.exceptions;

public class CircularReferenceException extends Exception {

	private static final long serialVersionUID = -6652661226236017610L;

	/**
	 * 
	 */
	public CircularReferenceException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CircularReferenceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public CircularReferenceException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public CircularReferenceException(Throwable cause) {
		super(cause);
	}

}
