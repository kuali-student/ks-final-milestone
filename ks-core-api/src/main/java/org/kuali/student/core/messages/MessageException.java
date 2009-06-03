package org.kuali.student.core.messages;

public class MessageException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MessageException(String message, Throwable e) {
		super(message, e);
	}

	public MessageException(String message) {
		super(message);
	}
}
