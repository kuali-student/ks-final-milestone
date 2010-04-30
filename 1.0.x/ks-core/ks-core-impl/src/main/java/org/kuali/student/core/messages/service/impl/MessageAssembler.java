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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.messages.entity.MessageEntity;

public class MessageAssembler {

    final static Logger logger = Logger.getLogger(MessageAssembler.class);
    
	public static void toMessageEntity(Message message,
			MessageEntity messageEntity) {

		try {
			BeanUtils.copyProperties(messageEntity, message);
		} catch (IllegalAccessException e) {
		    logger.error("Exception occured: ", e);
		} catch (InvocationTargetException e) {
		    logger.error("Exception occured: ", e);
		}
	}

	public static void toMessage(MessageEntity messageEntity, Message message) {

		try {
			BeanUtils.copyProperties(message, messageEntity);
		} catch (IllegalAccessException e) {
		    logger.error("Exception occured: ", e);
		} catch (InvocationTargetException e) {
		    logger.error("Exception occured: ", e);
		}

	}

	public static List<Message> toMessageList(List<MessageEntity> messages,
			Class<Message> message) {
		List<Message> result = new ArrayList<Message>();
		Message m1 = new Message();
		for (MessageEntity e : messages) {

			try {
				BeanUtils.copyProperties(m1, e);
			} catch (IllegalAccessException e1) {
			    logger.error("Exception occured: ", e1);
			} catch (InvocationTargetException e1) {
			    logger.error("Exception occured: ", e1);
			}
			result.add((m1));
		}
		return result;

	}
}
