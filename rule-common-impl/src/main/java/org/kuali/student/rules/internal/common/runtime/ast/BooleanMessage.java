package org.kuali.student.rules.internal.common.runtime.ast;

public class BooleanMessage {
	private Boolean succesful;
	private String successMessage; 
	private String failureMessage;

	public BooleanMessage(Boolean succesful, String successMessage, String failureMessage) {
    	this.succesful = succesful;
    	this.successMessage = successMessage; 
    	this.failureMessage = failureMessage;
	}

	public Boolean isSuccesful() {
		return this.succesful;
	}

	public String getSuccessMessage() {
		return this.successMessage;
	}

	public String getFailureMessage() {
		return this.failureMessage;
	}
	
	public String toString() {
		return "[successful=" + this.succesful
			+ ", successMessage='" + this.successMessage
			+ "', failureMessage='" + this.failureMessage
			+ "']";
	}
}
