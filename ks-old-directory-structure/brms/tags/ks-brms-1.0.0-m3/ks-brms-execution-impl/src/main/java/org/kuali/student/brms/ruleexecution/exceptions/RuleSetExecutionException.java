package org.kuali.student.brms.ruleexecution.exceptions;

public class RuleSetExecutionException extends RuntimeException {
    
    /** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new rule engine exception.
     * 
     * @param cause Cause of this exception
     */
    public RuleSetExecutionException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new rule engine exception.
     * 
     * @param msg Error message description
     * @param cause Cause of this exception
     */
    public RuleSetExecutionException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructs a new rule engine exception.
     * 
     * @param msg Error message description
     */
    public RuleSetExecutionException(String msg) {
        super(msg);
    }
}
