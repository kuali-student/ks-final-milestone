package org.kuali.maven.mojo.properties;

@SuppressWarnings("serial")
public class PropertyHandlingException extends Exception {

	public PropertyHandlingException() {
		super();
	}

	public PropertyHandlingException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropertyHandlingException(String message) {
		super(message);
	}

	public PropertyHandlingException(Throwable cause) {
		super(cause);
	}
}
