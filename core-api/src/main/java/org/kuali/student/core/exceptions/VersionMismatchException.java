package org.kuali.student.core.exceptions;

//@WebFault(faultBean="org.kuali.student.core.exceptions.jaxws.VersionMismatchExceptionBean")
public class VersionMismatchException extends Exception {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    public VersionMismatchException(){
        super();
    }

	public VersionMismatchException(String message) {
        super(message);
    }
}
