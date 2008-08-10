package org.kuali.student.poc.client.login;

public class TooManyAttemptsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TooManyAttemptsException() {
        super();
    }

    public TooManyAttemptsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyAttemptsException(String message) {
        super(message);
    }

    public TooManyAttemptsException(Throwable cause) {
        super(cause);
    }

}
