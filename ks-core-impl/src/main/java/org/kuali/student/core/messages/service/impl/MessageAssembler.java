package org.kuali.student.core.messages.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.messages.entity.MessageEntity;

public class MessageAssembler {

	public static void toMessageEntity(Message message,
			MessageEntity messageEntity) {

		try {
			BeanUtils.copyProperties(messageEntity, message);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static void toMessage(MessageEntity messageEntity, Message message) {

		try {
			BeanUtils.copyProperties(message, messageEntity);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
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
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
			}
			result.add((m1));
		}
		return result;

	}
}
