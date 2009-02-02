package org.kuali.student.core.exceptions;

//@WebFault(faultBean="org.kuali.student.core.exceptions.jaxws.AlreadyExistsExceptionBean")
public class AlreadyExistsException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    public AlreadyExistsException(){
        super();
    }

	public AlreadyExistsException(String message) {
        super(message);
    }

}
