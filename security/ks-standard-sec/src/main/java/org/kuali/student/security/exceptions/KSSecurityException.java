package org.kuali.student.security.exceptions;

public class KSSecurityException extends Exception {

    private static final long serialVersionUID = 2941300199692497512L;

    /**
     * This constructs a ...
     * 
     */
    public KSSecurityException() {
        super();
    }

    /**
     * This constructs a ...
     * 
     * @param message
     * @param cause
     */
    public KSSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * This constructs a ...
     * 
     * @param message
     */
    public KSSecurityException(String message) {
        super(message);
    }

    /**
     * This constructs a ...
     * 
     * @param cause
     */
    public KSSecurityException(Throwable cause) {
        super(cause);
    }

}
