package org.kuali.student.rules.ruleexecution.runtime.report.ast.exceptions;

public class MessageBuilderException extends RuntimeException {
    
    /** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new message builder exception.
     * 
     * @param cause Cause of this exception
     */
    public MessageBuilderException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new message builder exception.
     * 
     * @param msg Error message description
     * @param cause Cause of this exception
     */
    public MessageBuilderException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructs a new message builder exception.
     * 
     * @param msg Error message description
     */
    public MessageBuilderException(String msg) {
        super(msg);
    }
}
