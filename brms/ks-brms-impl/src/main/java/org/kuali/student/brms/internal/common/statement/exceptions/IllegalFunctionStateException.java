package org.kuali.student.brms.internal.common.statement.exceptions;

public class IllegalFunctionStateException extends IllegalStateException {

	/** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new illegal function state exception exception.
     * 
     * @param msg Error message description
     */
    public IllegalFunctionStateException(String msg) {
        super(msg);
    }
}
