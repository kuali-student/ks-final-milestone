package org.kuali.student.poc.common.ws.exceptions;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "org.kuali.student.poc.common.ws.exceptions.jaxws.ReadOnlyExceptionBean")
public class ReadOnlyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public ReadOnlyException() {
        super();
    }

    public ReadOnlyException(String message) {
        super(message);
    }

}
