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

package org.kuali.student.core.messages.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.r1.core.messages.entity.MessageEntity;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.messages.dto.MessageInfo;

public class MessageAssembler {

    final static Logger logger = Logger.getLogger(MessageAssembler.class);
    
	public static void toMessageEntity(MessageInfo message,
			MessageEntity messageEntity) {
		messageEntity.setGroupName(message.getGroupName());
		messageEntity.setMessageId(message.getMessageKey());
		messageEntity.setLocale(message.getLocale().getLocaleLanguage());
		messageEntity.setValue(message.getValue());
	}

	public static void toMessage(MessageEntity messageEntity, MessageInfo message) {
		message.setGroupName(messageEntity.getGroupName());
		message.setMessageKey(messageEntity.getMessageId());
		LocaleInfo locale = new LocaleInfo();
		locale.setLocaleLanguage(messageEntity.getLocale());
		locale.setLocaleRegion(messageEntity.getLocale());
		message.setLocale(locale);
		message.setValue(messageEntity.getValue());
	}

	public static List<MessageInfo> toMessageList(List<MessageEntity> messages,
			Class<MessageInfo> message) {
		List<MessageInfo> result = new ArrayList<MessageInfo>();
		MessageInfo m1;
		for (MessageEntity e : messages) {
		    m1 = new MessageInfo();
			toMessage(e, m1);
			result.add(m1);
		}
		return result;

	}
}
