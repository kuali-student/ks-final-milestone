package org.kuali.student.core.enumerationmanagement;

public class EnumerationException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EnumerationException(String message, Throwable e){
		super(message, e);
	}
	
	public EnumerationException(String message){
		super(message);
	}
}
