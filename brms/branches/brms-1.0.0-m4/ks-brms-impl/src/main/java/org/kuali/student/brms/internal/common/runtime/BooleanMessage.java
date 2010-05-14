package org.kuali.student.brms.internal.common.runtime;

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
	 * Returns either the success or failure message.
	 * 
	 *  @return Success or failure message
	 */
	public String getMessage();
}
