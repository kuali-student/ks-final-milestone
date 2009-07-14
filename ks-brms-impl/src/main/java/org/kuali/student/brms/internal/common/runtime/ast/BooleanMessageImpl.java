package org.kuali.student.brms.internal.common.runtime.ast;

import org.kuali.student.brms.internal.common.runtime.BooleanMessage;

public class BooleanMessageImpl implements BooleanMessage {
	private String messageId;
	private Boolean successful;
	private String message;

	/**
	 * Constructs a new boolean message.
	 * 
	 * @param messageId Message id
	 * @param succesful Success or failure
	 * @param message Success or failure message
	 */
	public BooleanMessageImpl(String messageId, Boolean successful, String message) {
		this.messageId = messageId;
		this.successful = successful;
		this.message = message;
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
		return this.successful;
	}

	/**
	 * Returns either the success or failure message.
	 * 
	 *  @param Success or failure message
	 */
	public String getMessage() {
		return this.message;
	}

	public String toString() {
		return "[messageId=" + this.messageId
			+ ", successful='" + this.successful
			+ ", message='" + this.message
			+ "']";
	}
}
