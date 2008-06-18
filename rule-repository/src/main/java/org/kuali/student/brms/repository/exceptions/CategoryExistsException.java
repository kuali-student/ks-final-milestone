package org.kuali.student.brms.repository.exceptions;

/**
 * This is a rule engine repository category exception class.
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class CategoryExistsException extends RuntimeException {

    /** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new category exception.
     * 
     * @param cause Cause of this exception
     */
    public CategoryExistsException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new category exception.
     * 
     * @param msg Error message description
     * @param cause Cause of this exception
     */
    public CategoryExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructs a new category exception.
     * 
     * @param msg Error message description
     */
    public CategoryExistsException(String msg) {
        super(msg);
    }
}
