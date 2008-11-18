package org.kuali.student.core.exceptions;

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
