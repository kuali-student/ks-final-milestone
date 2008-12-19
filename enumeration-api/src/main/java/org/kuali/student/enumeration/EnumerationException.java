package org.kuali.student.enumeration;

public class EnumerationException extends RuntimeException{
	public EnumerationException(String message, Throwable e){
		super(message, e);
	}
	
	public EnumerationException(String message){
		super(message);
	}
}
