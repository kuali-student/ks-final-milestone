package org.kuali.student.core.exceptions;

import javax.xml.ws.WebFault;

@WebFault(faultBean="org.kuali.student.core.exceptions.jaxws.PermissionDeniedExceptionBean")
public class PermissionDeniedException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    public PermissionDeniedException() {
        super();
    }

    public PermissionDeniedException(String message) {
        super(message);
    }

}
