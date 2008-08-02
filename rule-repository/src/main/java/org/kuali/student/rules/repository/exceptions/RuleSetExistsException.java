package org.kuali.student.rules.repository.exceptions;
/**
 * This is a rule engine repository rule set exists exception class. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class RuleSetExistsException extends RuntimeException {

    /** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new rule set exception.
     * 
     * @param cause Cause of this exception
     */
    public RuleSetExistsException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new rule set exception.
     * 
     * @param msg Error message description
     * @param cause Cause of this exception
     */
    public RuleSetExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructs a new rule set exception.
     * 
     * @param msg Error message description
     */
    public RuleSetExistsException(String msg) {
        super(msg);
    }
}
