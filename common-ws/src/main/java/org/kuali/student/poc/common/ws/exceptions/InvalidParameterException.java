package org.kuali.student.poc.common.ws.exceptions;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "org.kuali.student.poc.common.ws.exceptions.jaxws.InvalidParameterExceptionBean")
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
