package org.kuali.student.poc.common.ws.exceptions;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "org.kuali.student.poc.common.ws.exceptions.jaxws.MissingParameterExceptionBean")
public class MissingParameterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public MissingParameterException() {
        super();
    }

    public MissingParameterException(String message) {
        super(message);
    }

}
