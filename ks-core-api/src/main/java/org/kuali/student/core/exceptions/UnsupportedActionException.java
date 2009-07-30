package org.kuali.student.core.exceptions;

import javax.xml.ws.WebFault;

@WebFault(faultBean="org.kuali.student.core.exceptions.jaxws.UnsupportedActionExceptionBean")
public class UnsupportedActionException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    public UnsupportedActionException(){
        super();
    }

	public UnsupportedActionException(String message) {
        super(message);
    }

}
