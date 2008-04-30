package org.kuali.student.poc.common.ws.exceptions;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "org.kuali.student.poc.common.ws.exceptions.jaxws.DependentObjectsExistExceptionBean")
public class DependentObjectsExistException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public DependentObjectsExistException(){
        super();
    }
	
	public DependentObjectsExistException(String message) {
        super(message);
    }
}
