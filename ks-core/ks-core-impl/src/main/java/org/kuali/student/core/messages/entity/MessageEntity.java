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

package org.kuali.student.core.messages.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.common.entity.BaseEntity;
import org.kuali.student.common.entity.KSEntityConstants;

@Entity
@Table(name = "KSMG_MESSAGE")
public class MessageEntity extends BaseEntity{
	 
	 @Column(name="MSG_ID")
	 private String messageId; 
	 @Column(name="LOCALE")
	 private String locale;
	 @Column(name="GRP_NAME")
	 private String groupName;
	 @Column(name="MSG_VALUE",length=KSEntityConstants.LONG_TEXT_LENGTH)
	 private String value;
	 
	 public String getLocale() {
		 return locale;
	 }
	 public void setLocale(String locale) {
		 this.locale = locale;
	 }
	 public String getGroupName() {
		 return groupName;
	 }
	 public void setGroupName(String groupName) {
		 this.groupName = groupName;
	 }
	 public String getValue() {
		 return value;
	 }
	 public void setValue(String value) {
		 this.value = value;
	 }
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

}
