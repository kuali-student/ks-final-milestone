package org.kuali.student.core.exceptions;

import javax.xml.ws.WebFault;

@WebFault(faultBean="org.kuali.student.core.exceptions.jaxws.DisabledIdentifierExceptionBean")
public class DisabledIdentifierException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


	public DisabledIdentifierException() {
        super();
    }

    public DisabledIdentifierException(String message) {
        super(message);
    }

}
