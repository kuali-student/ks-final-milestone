package org.kuali.student.message;

public class MessageException extends RuntimeException{
	public MessageException(String message, Throwable e){
		super(message, e);
	}
	
	public MessageException(String message){
		super(message);
	}
}
