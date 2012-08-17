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

package org.kuali.student.r1.common.messages.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.kuali.student.r2.common.messages.dto.MessageInfo;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MessageList")
public class MessageList implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "message", required = true)
	protected List<MessageInfo> messages;

	public List<MessageInfo> getMessages() {
		if (messages == null) {
			messages = new ArrayList<MessageInfo>();
		}
		return this.messages;
	}

	public void setMessages(List<MessageInfo> l) {
		messages = l;

	}
}
