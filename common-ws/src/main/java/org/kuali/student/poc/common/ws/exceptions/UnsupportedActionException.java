package org.kuali.student.poc.common.ws.exceptions;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "org.kuali.student.poc.common.ws.exceptions.jaxws.UnsupportedActionException")
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
