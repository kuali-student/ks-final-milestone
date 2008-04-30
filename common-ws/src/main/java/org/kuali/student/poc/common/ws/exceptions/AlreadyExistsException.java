package org.kuali.student.poc.common.ws.exceptions;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "org.kuali.student.poc.common.ws.exceptions.jaxws.AlreadyExistsExceptionBean")
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
