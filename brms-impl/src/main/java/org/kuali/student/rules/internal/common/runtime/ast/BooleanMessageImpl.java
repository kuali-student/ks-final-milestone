package org.kuali.student.rules.internal.common.runtime.ast;

import org.kuali.student.rules.internal.common.runtime.BooleanMessage;

public class BooleanMessageImpl implements BooleanMessage {
	private String messageId;
	private Boolean succesful;
	private String successMessage; 
	private String failureMessage;

	public BooleanMessageImpl(String messageId, Boolean succesful, String successMessage, String failureMessage) {
    	this.messageId = messageId;
		this.succesful = succesful;
    	this.successMessage = successMessage; 
    	this.failureMessage = failureMessage;
	}

	/**
	 * Gets the message id.
	 * 
	 * @return Message id
	 */
	public String getMessageId() {
		return this.messageId;
	}
	
	/**
	 * Returns whether the message is a success or failure.
	 * 
	 * @return True if successful; otherwise false
	 */
	public Boolean isSuccesful() {
		return this.succesful;
	}

	/**
	 * Gets the success message.
	 * 
	 * @return Success message
	 */
	public String getSuccessMessage() {
		return this.successMessage;
	}

	/**
	 * Gets the failure message.
	 * 
	 * @return Failure message
	 */
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
