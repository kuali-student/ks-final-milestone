package org.kuali.student.rules.brms.repository.exceptions;

/**
 * This is a rule engine repository login exception class.
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class RepositoryLoginException extends Exception {

    /** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new repository login exception.
     * 
     * @param cause Cause of this exception
     */
    public RepositoryLoginException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new repository login exception.
     * 
     * @param msg Error message description
     * @param cause Cause of this exception
     */
    public RepositoryLoginException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructs a new repository login exception.
     * 
     * @param msg Error message description
     */
    public RepositoryLoginException(String msg) {
        super(msg);
    }
}
