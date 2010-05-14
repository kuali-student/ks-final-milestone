package org.kuali.student.rules.internal.common.runtime.exceptions;

public class BooleanFunctionException extends RuntimeException {
    
    /** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new boolean function exception.
     * 
     * @param cause Cause of this exception
     */
    public BooleanFunctionException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new boolean function exception.
     * 
     * @param msg Error message description
     * @param cause Cause of this exception
     */
    public BooleanFunctionException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructs a new boolean function exception.
     * 
     * @param msg Error message description
     */
    public BooleanFunctionException(String msg) {
        super(msg);
    }
}
