package org.kuali.student.common.validator;

public class DateParseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DateParseException() {
        super();
    }

    public DateParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateParseException(String message) {
        super(message);
    }

    public DateParseException(Throwable cause) {
        super(cause);
    }
    

}
