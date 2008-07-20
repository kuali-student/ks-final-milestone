package org.kuali.student.rules.brms.agenda.exceptions;
/**
 * This is an agenda discovery exception class. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class AgendaDiscoveryException extends RuntimeException {

    /** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new rule set exception.
     * 
     * @param cause Cause of this exception
     */
    public AgendaDiscoveryException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new rule set exception.
     * 
     * @param msg Error message description
     * @param cause Cause of this exception
     */
    public AgendaDiscoveryException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructs a new rule set exception.
     * 
     * @param msg Error message description
     */
    public AgendaDiscoveryException(String msg) {
        super(msg);
    }
}
