package org.kuali.student.rules.internal.common.runtime.ast;

public interface BooleanMessage {

	/**
	 * Gets the message id.
	 * 
	 * @return Message id
	 */
	public String getMessageId();

	public Boolean isSuccesful();

	public String getSuccessMessage();

	public String getFailureMessage();
}
