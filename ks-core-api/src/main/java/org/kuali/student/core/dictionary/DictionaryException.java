package org.kuali.student.core.dictionary;

public class DictionaryException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DictionaryException(String message, Throwable e){
		super(message, e);
	}
}
