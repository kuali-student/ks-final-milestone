package org.kuali.student.rules.translators.exceptions;

/**
 * This is a generate rule exception class. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class RuleSetTranslatorException extends RuntimeException {

    /** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new rule exception.
     * 
     * @param cause Cause of this exception
     */
    public RuleSetTranslatorException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new rule exception.
     * 
     * @param msg Error message description
     * @param cause Cause of this exception
     */
    public RuleSetTranslatorException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructs a new rule exception.
     * 
     * @param msg Error message description
     */
    public RuleSetTranslatorException(String msg) {
        super(msg);
    }
}
