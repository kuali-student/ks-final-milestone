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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.kuali.student.core.messages.dto.LocaleKeyList;
import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.messages.dto.MessageGroupKeyList;
import org.kuali.student.core.messages.dto.MessageList;
import org.kuali.student.core.messages.entity.MessageEntity;
import org.kuali.student.core.messages.service.MessageService;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * This is poor man's implementation of a mock messages service to be used for
 * UI testing, it will load gwt-messages.xml configured via the set method.
 * 
 * @author Kuali Student Team
 * 
 */
public class MessageServiceMock implements MessageService {

	final Logger logger = Logger.getLogger(MessageServiceMock.class);

	List<String> messageFiles;

	Map<String, LocaleMessages> messages = new HashMap<String, LocaleMessages>();

	private static class LocaleMessages {
		Map<String, Map<String, String>> localeMessages = new HashMap<String, Map<String, String>>();

		public Map<String, String> getMessages(String groupKey) {
			return localeMessages.get(groupKey);
		}

		public Set<String> getGroups() {
			return localeMessages.keySet();
		}

		public void putMessage(String groupKey, String messageKey,
				String message) {
			Map<String, String> groupMessages;

			groupMessages = localeMessages.get(groupKey);
			if (groupMessages == null) {
				groupMessages = new HashMap<String, String>();
			}
			groupMessages.put(messageKey, message);
			this.localeMessages.put(groupKey, groupMessages);
		}
	}

	private void putMessage(String locale, String group, String key,
			String value) {

		LocaleMessages localeMessages = messages.get(locale);
		if (localeMessages == null) {
			localeMessages = new LocaleMessages();
		}
		localeMessages.putMessage(group, key, value);
		this.messages.put(locale, localeMessages);
	}

	/**
	 * @see org.kuali.student.core.messages.service.MessageService#addMessage(org.kuali.student.core.messages.dto.Message)
	 */
	@Override
	public Message addMessage(Message messageInfo) {

		LocaleMessages localeMessages = this.messages.get(messageInfo
				.getLocale());
		if (localeMessages == null) {
			localeMessages = new LocaleMessages();
		}
		localeMessages.putMessage(messageInfo.getGroupName(), messageInfo
				.getId(), messageInfo.getValue());

		return messageInfo;
	}

	/**
	 * @see org.kuali.student.core.messages.service.MessageService#getLocales()
	 */
	@Override
	public LocaleKeyList getLocales() {

		LocaleKeyList locales = new LocaleKeyList();

		if (!this.messages.isEmpty()) {
			locales.setLocales(new ArrayList<String>(this.messages.keySet()));
		}

		return locales;
	}

	/**
	 * @see org.kuali.student.core.messages.service.MessageService#getMessage(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public Message getMessage(String localeKey, String messageGroupKey,
			String messageKey) {

		Message m = null;
		m = new Message();
		m.setGroupName(messageGroupKey);
		m.setLocale(localeKey);
		m.setId(messageKey);
		m.setValue(this.messages.get(localeKey).getMessages(messageGroupKey)
				.get(messageKey));

		return m;
	}

	/**
	 * @see org.kuali.student.core.messages.service.MessageService#getMessageGroups()
	 */
	@Override
	public MessageGroupKeyList getMessageGroups() {

		MessageGroupKeyList groupKeys = new MessageGroupKeyList();
		Iterator<String> i = messages.keySet().iterator();
		Set<String> groupKeySet = new HashSet<String>();
		while (i.hasNext()) {
			String locale = i.next();
			groupKeySet.addAll(messages.get(locale).getGroups());
		}
		groupKeys.setMessageGroupKeys(new ArrayList<String>(groupKeySet));

		return groupKeys;
	}

	/**
	 * @see org.kuali.student.core.messages.service.MessageService#getMessages(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public MessageList getMessages(String localeKey, String messageGroupKey) {

		MessageList messageList = new MessageList();

		Map<String, String> groupMessages = ((LocaleMessages) messages
				.get(localeKey)).getMessages(messageGroupKey);
		List<Message> messageArrayList = new ArrayList<Message>();

		Iterator<String> i = groupMessages.keySet().iterator();
		while (i.hasNext()) {
			String id = i.next();
			Message m = new Message();
			m.setGroupName(messageGroupKey);
			m.setId(id);
			m.setValue(groupMessages.get(id));
			m.setLocale(localeKey);
			messageArrayList.add(m);
		}
		messageList.setMessages(messageArrayList);

		return messageList;
	}

	/**
	 * @see org.kuali.student.core.messages.service.MessageService#getMessagesByGroups(java.lang.String,
	 *      org.kuali.student.core.messages.dto.MessageGroupKeyList)
	 */
	@Override
	public MessageList getMessagesByGroups(String localeKey,
			MessageGroupKeyList messageGroupKeyList) {

		MessageList groupMessageList = new MessageList();
		List<Message> messageArrayList = new ArrayList<Message>();
		for (String groupKey : messageGroupKeyList.getMessageGroupKeys()) {
			MessageList messageList = getMessages(localeKey, groupKey);
			messageArrayList.addAll(messageList.getMessages());
		}
		groupMessageList.setMessages(messageArrayList);

		return groupMessageList;
	}

	/**
	 * @see org.kuali.student.core.messages.service.MessageService#updateMessage(java.lang.String,
	 *      java.lang.String, java.lang.String,
	 *      org.kuali.student.core.messages.dto.Message)
	 */
	@Override
	public Message updateMessage(String localeKey, String messageGroupKey,
			String messageKey, Message messageInfo) {
		if (getMessage(localeKey, messageGroupKey, messageKey) != null) {
			putMessage(localeKey, messageGroupKey, messageKey, messageInfo
					.getValue());
		}
		return messageInfo;
	}

	public List<String> getMessageFiles() {
		return messageFiles;
	}

	public void setMessageFiles(List<String> messageFiles) {
		this.messageFiles = messageFiles;

		// bootstrap the data
		logger.debug("Bootstrap messages started.");
		if (this.messages.isEmpty()) {
			for (String messageFileName : messageFiles) {
				Resource res;
				try {
					if (messageFileName.startsWith("classpath:")) {
						res = new ClassPathResource(messageFileName
								.substring("classpath:".length()));
					} else {
						res = new FileSystemResource(messageFileName);
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

				XmlBeanFactory factory = new XmlBeanFactory(res);

				try {
					Object[] beanArray = factory.getBeansOfType(
							MessageEntity.class).values().toArray();
					for (Object o : beanArray) {
						MessageEntity m = (MessageEntity) o;
						putMessage(m.getLocale(), m.getGroupName(), m.getMessageId(),
								m.getValue());
					}
				} catch (Exception e) {
					logger.debug(e);
				}
			}
		}

		logger.debug("Bootstrap messages finished.");
	}

}
