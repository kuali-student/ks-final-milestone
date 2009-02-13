package org.kuali.student.core.exceptions;

//@WebFault(faultBean="org.kuali.student.core.exceptions.jaxws.InvalidParameterExceptionBean")
public class InvalidParameterException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    public InvalidParameterException() {
        super();
    }

    public InvalidParameterException(String message) {
        super(message);
    }

}
