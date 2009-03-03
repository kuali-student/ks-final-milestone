package org.kuali.student.rules.internal.common.runtime.ast;

public class BooleanMessage {
	private String id;
	private Boolean succesful;
	private String successMessage; 
	private String failureMessage;

	public BooleanMessage(String id, Boolean succesful, String successMessage, String failureMessage) {
    	this.id = id;
    	this.succesful = succesful;
    	this.successMessage = successMessage; 
    	this.failureMessage = failureMessage;
	}

	public String getId() {
		return this.id;
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
		return "[id="+this.id + ", successful=" + this.succesful
			+ ", successMessage='" + this.successMessage
			+ "', failureMessage='" + this.failureMessage
			+ "']";
	}
}
