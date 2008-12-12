package org.kuali.student.dictionary;

public class DictionaryException extends RuntimeException{
	public DictionaryException(String message, Throwable e){
		super(message, e);
	}
}
