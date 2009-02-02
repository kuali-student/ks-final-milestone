package org.kuali.student.core.exceptions;

//@WebFault(faultBean="org.kuali.student.core.exceptions.jaxws.DoesNotExistExceptionBean")
public class DoesNotExistException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    public DoesNotExistException() {
        super("");
    }

    public DoesNotExistException(String message) {
        super(message);
    }
}
