package org.kuali.student.rules.repository.exceptions;

/**
 * This is a generate rule exception class. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class GenerateRuleSetException extends RuntimeException {

    /** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new rule exception.
     * 
     * @param cause Cause of this exception
     */
    public GenerateRuleSetException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new rule exception.
     * 
     * @param msg Error message description
     * @param cause Cause of this exception
     */
    public GenerateRuleSetException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructs a new rule exception.
     * 
     * @param msg Error message description
     */
    public GenerateRuleSetException(String msg) {
        super(msg);
    }
}
