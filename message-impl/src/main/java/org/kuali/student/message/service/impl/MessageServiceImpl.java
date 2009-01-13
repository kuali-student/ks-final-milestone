package org.kuali.student.message.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;


import org.kuali.student.message.MessageException;
import org.kuali.student.message.dao.MessageManagementDAO;
import org.kuali.student.message.dto.LocaleKeyList;
import org.kuali.student.message.dto.Message;
import org.kuali.student.message.dto.MessageGroupKeyList;
import org.kuali.student.message.dto.MessageList;
import org.kuali.student.message.entity.MessageEntity;
import org.kuali.student.message.service.MessageService;
import org.kuali.student.message.service.impl.util.POJOConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.message.service.MessageService", serviceName = "MessageService", portName = "MessageService", targetNamespace = "http://student.kuali.org/wsdl/MessageService")
@Transactional
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class MessageServiceImpl implements MessageService{
    
	final static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	private MessageManagementDAO messageDAO;
	
	private static boolean populated = false;
	
	private String xmlFile = "/testMessageData.xml";
	private static final String CONTEXT_NAME = "org.kuali.student.message.dto";
	private JAXBContext context;
	private Unmarshaller unmarshaller;

	public MessageServiceImpl() {
	   if (!populated) {
	        populate();
	    }
	}
	
	private synchronized void populate(){

	}
	
	private int getMessagesCount(){
		return messageDAO.getTotalMessages();
	}
	
    public MessageManagementDAO getMessageDAO() {
        return messageDAO;
    }

    public void setMessageDAO(MessageManagementDAO messageDAO) {
        this.messageDAO = messageDAO;
/*		 if (!populated) {
		        populated = true;
		        if (messageDAO != null) {
		    		try {
		    			context = JAXBContext.newInstance(CONTEXT_NAME);
		    			unmarshaller = context.createUnmarshaller();
		    			MessageList messageList = (MessageList)unmarshaller.unmarshal(MessageServiceImpl.class.getResource(xmlFile));
		    			System.out.println("**************SIZE:" + messageList.getMessages().size() + "    " + messageList.getMessages().get(0).getValue());
		    	        List<Message> messages =  messageList.getMessages();
		    		    List<MessageEntity> messageEntities =  POJOConverter.mapList(messages, MessageEntity.class);
		    		    for(MessageEntity me: messageEntities){
		    		    	System.out.println("**************VALUE: " + me.getValue());
		    		    	messageDAO.addMessage(me);
		    		    }
		    		}
		    		catch (JAXBException e) {
		    				logger.error("Message test data instantiation failed.", e);
		    				throw new MessageException("Message test data instantiation failed.", e);
		    		}
		        }
		    }*/
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
		
		MessageEntity messageEntity = this.messageDAO.getMessage(localeKey, messageGroupKey, messageKey);
		if(messageEntity != null){
			message = new Message();
			POJOConverter.map(messageEntity, message);
		}
		return message;
	}



	public MessageList getMessages(String localeKey, String messageGroupKey) {
        List<MessageEntity> messages =  this.messageDAO.getMessages(localeKey, messageGroupKey);
        
        MessageList messageList = new MessageList();
        List<Message> messageDTOs =  POJOConverter.mapList(messages, Message.class);
        messageList.setMessages(messageDTOs);
		return messageList;
	}

	public MessageList getMessagesByGroups(String localeKey, MessageGroupKeyList messageGroupKeyList) {
		List<MessageEntity> messages =  this.messageDAO.getMessagesByGroups(localeKey, messageGroupKeyList.getMessageGroupKeys());
		
        MessageList messageList = new MessageList();
        List<Message> messageDTOs =  POJOConverter.mapList(messages, Message.class);
        messageList.setMessages(messageDTOs);
		return messageList;
	}

	public Message updateMessage(String localeKey, String messageGroupKey, String messageKey, Message messageInfo) {

	    MessageEntity messageEntity = new MessageEntity();    
	    POJOConverter.map(messageInfo, messageEntity);
	    messageEntity =  messageDAO.updateMessage(localeKey, messageGroupKey, messageKey, messageEntity);
	    POJOConverter.map(messageEntity, messageInfo);
	    
        return messageInfo;
	}
    
}
