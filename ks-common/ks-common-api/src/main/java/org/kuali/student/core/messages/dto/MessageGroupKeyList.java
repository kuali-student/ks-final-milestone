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

package org.kuali.student.core.messages.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MessageGroupKeyList")
public class MessageGroupKeyList implements java.io.Serializable {

	private static final long serialVersionUID = -840422925676029014L;

	@XmlElement(name = "messageGroup", required = true)
	protected List<String> messageGroupKeys;

	public List<String> getMessageGroupKeys() {
		if (messageGroupKeys == null) {
			messageGroupKeys = new ArrayList<String>();
		}
		return this.messageGroupKeys;
	}

	public void setMessageGroupKeys(List<String> l) {
		messageGroupKeys = l;

	}
}
