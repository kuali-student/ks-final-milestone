package org.kuali.student.rules.internal.common.runtime.ast;

public interface BooleanMessage {

	/**
	 * Gets the message id.
	 * 
	 * @return Message id
	 */
	public String getMessageId();

	/**
	 * Returns whether the message is a success or failure.
	 * 
	 * @return True if successful; otherwise false
	 */
	public Boolean isSuccesful();

	/**
	 * Gets the success message.
	 * 
	 * @return Success message
	 */
	public String getSuccessMessage();

	/**
	 * Gets the failure message.
	 * 
	 * @return Failure message
	 */
	public String getFailureMessage();
}
