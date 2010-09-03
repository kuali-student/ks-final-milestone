package org.kuali.core.db.torque;

@SuppressWarnings("serial")
public class PropertyHandlingException extends Exception {

	public PropertyHandlingException() {
	}

	public PropertyHandlingException(String message) {
		super(message);
	}

	public PropertyHandlingException(Throwable cause) {
		super(cause);
	}

	public PropertyHandlingException(String message, Throwable cause) {
		super(message, cause);
	}
}
