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

import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.messages.dao.MessageManagementDAO;
import org.kuali.student.core.messages.dto.LocaleKeyList;
import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.messages.dto.MessageGroupKeyList;
import org.kuali.student.core.messages.dto.MessageList;
import org.kuali.student.core.messages.entity.MessageEntity;
import org.kuali.student.core.messages.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.core.messages.service.MessageService", serviceName = "MessageService", portName = "MessageService", targetNamespace = "http://student.kuali.org/wsdl/messages")
@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class MessageServiceImpl implements MessageService{
    
	final static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	private MessageManagementDAO messageDAO;
	
	public MessageServiceImpl() {
	}
	
    public MessageManagementDAO getMessageDAO() {
        return messageDAO;
    }

    public void setMessageDAO(MessageManagementDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

	public LocaleKeyList getLocales() {
        
		List<String> locales = this.messageDAO.getLocales();
		
		LocaleKeyList keyList = new LocaleKeyList();
		keyList.setLocales(locales);
  
		return keyList;
	}
	
	public MessageGroupKeyList getMessageGroups() {
		List<String> groups = this.messageDAO.getMessageGroups();
		
		MessageGroupKeyList keyList = new MessageGroupKeyList();
		keyList.setMessageGroupKeys(groups);

		return keyList;
	}

	public Message getMessage(String localeKey, String messageGroupKey, String messageKey) {
		Message message = null;
		if(localeKey == null || messageGroupKey == null || messageKey == null){
			return null;
		}
		else{
			MessageEntity messageEntity = this.messageDAO.getMessage(localeKey, messageGroupKey, messageKey);
			if(messageEntity != null){
				message = new Message();
				MessageAssembler.toMessage(messageEntity,message); 
			}
		}
		return message;
	}

	public MessageList getMessages(String localeKey, String messageGroupKey) {
		if(localeKey == null || messageGroupKey == null){
			return new MessageList();
		}
		else{
			List<MessageEntity> messages =  this.messageDAO.getMessages(localeKey, messageGroupKey);
	        
	        MessageList messageList = new MessageList();
	        List<Message> messageDTOs =  MessageAssembler.toMessageList(messages,Message.class);
	        messageList.setMessages(messageDTOs);
			return messageList;
		}
	}

	public MessageList getMessagesByGroups(String localeKey, MessageGroupKeyList messageGroupKeyList) {
		if(localeKey == null || messageGroupKeyList == null){
			return new MessageList();
		}
		else{
			List<MessageEntity> messages =  this.messageDAO.getMessagesByGroups(localeKey, messageGroupKeyList.getMessageGroupKeys());
		    MessageList messageList = new MessageList();
		    List<Message> messageDTOs =  MessageAssembler.toMessageList(messages,Message.class);
		    messageList.setMessages(messageDTOs);
			return messageList;
		}
	}

	@Transactional(readOnly=false)
	public Message updateMessage(String localeKey, String messageGroupKey, String messageKey, Message messageInfo) {
		
		if(localeKey == null || messageGroupKey == null || messageKey == null || messageInfo == null){
			return null;
		}
		else{
		    MessageEntity messageEntity = new MessageEntity();    
		    MessageAssembler.toMessageEntity( messageInfo, messageEntity);
		    messageEntity =  messageDAO.updateMessage(localeKey, messageGroupKey, messageKey, messageEntity);
		    MessageAssembler.toMessage( messageEntity,messageInfo);
		    return messageInfo;
		}        
	}

	@Transactional(readOnly=false)
	public Message addMessage(Message messageInfo) {
		if(messageInfo != null)	{
			MessageEntity messageEntity = new MessageEntity();    
			MessageAssembler.toMessageEntity(messageInfo, messageEntity);
			messageEntity =  messageDAO.addMessage(messageEntity);
			MessageAssembler.toMessage(messageEntity, messageInfo);
		}
		return messageInfo;
	}
    
}
