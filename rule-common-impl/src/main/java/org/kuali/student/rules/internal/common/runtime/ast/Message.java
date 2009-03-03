package org.kuali.student.rules.internal.common.runtime.ast;

public interface Message {
	/**
	 * Gets the message id.
	 * 
	 * @return Message id
	 */
	public String getMessageId();
	
    /**
	 * Gets a boolean message.
     * 
     * @return Boolean message
     */
    public BooleanMessage getBooleanMessage();
}
