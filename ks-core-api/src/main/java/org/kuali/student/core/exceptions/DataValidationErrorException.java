package org.kuali.student.core.exceptions;

import javax.xml.ws.WebFault;

@WebFault(faultBean="org.kuali.student.core.exceptions.jaxws.DataValidationErrorExceptionBean")
public class DataValidationErrorException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public DataValidationErrorException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DataValidationErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public DataValidationErrorException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DataValidationErrorException(Throwable cause) {
		super(cause);
	}

}
