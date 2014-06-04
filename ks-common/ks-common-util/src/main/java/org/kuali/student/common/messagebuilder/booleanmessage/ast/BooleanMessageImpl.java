/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.messagebuilder.booleanmessage.ast;

import org.kuali.student.common.messagebuilder.booleanmessage.BooleanMessage;

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
