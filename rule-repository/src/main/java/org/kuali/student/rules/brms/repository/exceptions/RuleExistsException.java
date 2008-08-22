package org.kuali.student.rules.brms.repository.exceptions;

/**
 * This is a rule engine repository rule exists exception class. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class RuleExistsException extends RuntimeException {

    /** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new rule exception.
     * 
     * @param cause Cause of this exception
     */
    public RuleExistsException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new rule exception.
     * 
     * @param msg Error message description
     * @param cause Cause of this exception
     */
    public RuleExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructs a new rule exception.
     * 
     * @param msg Error message description
     */
    public RuleExistsException(String msg) {
        super(msg);
    }
}
