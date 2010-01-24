package org.kuali.student.commons.ui.logging.client;

public class LogFailedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LogFailedException() {
		super();
	}

	public LogFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public LogFailedException(String message) {
		super(message);
	}

	public LogFailedException(Throwable cause) {
		super(cause);
	}

}
