package org.kuali.student.admin.messages.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.util.MaxAgeSoftReference;
import org.kuali.rice.core.util.MaxSizeMap;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.student.core.enumerationmanagement.bo.EnumeratedValue;
import org.kuali.student.core.messages.bo.MessageEntity;
import org.kuali.student.core.messages.dto.LocaleKeyList;
import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.messages.dto.MessageGroupKeyList;
import org.kuali.student.core.messages.dto.MessageList;
import org.kuali.student.core.messages.service.MessageService;
import org.springframework.beans.factory.InitializingBean;

@WebService(endpointInterface = "org.kuali.student.core.messages.service.MessageService", serviceName = "MessageService", portName = "MessageService", targetNamespace = "http://student.kuali.org/wsdl/messages")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class MessageServiceImpl implements MessageService, InitializingBean {

	protected boolean cachingEnabled = false;
	protected int msgsCacheMaxSize = 20;
	protected int msgsCacheMaxAgeSeconds = 90;
	protected Map<String,MaxAgeSoftReference<MessageList>> msgsCache;
	
    private BusinessObjectService businessObjectService;

    public Message addMessage(Message messageInfo) {
        MessageEntity entity = toMessageEntity(messageInfo);
        getBusinessObjectService().save(entity);

        return toMessage(entity);
    }

	@SuppressWarnings("unchecked")
    public LocaleKeyList getLocales() {
	    List<String> keyList = new ArrayList<String>();
	    Map<String, Object> criteria = new HashMap<String,Object>();
        
        criteria.put(EnumeratedValue.ENUMERATION_KEY, MessageEntity.LOCALE_ENUMERATION);
        BusinessObjectService boService = KNSServiceLocator.getBusinessObjectService();
        Collection<EnumeratedValue> values = boService.findMatching(EnumeratedValue.class, criteria);
        
        Iterator<EnumeratedValue> iterator = values.iterator(); 
        while(iterator.hasNext()) {
            EnumeratedValue value = iterator.next();
            keyList.add(value.getCode());
        }
    	
    	LocaleKeyList localeKeyList = new LocaleKeyList();
    	localeKeyList.setLocales(keyList);
    	
        return localeKeyList;
    }

    public Message getMessage(String localeKey, String messageGroupKey, String messageKey) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("locale", localeKey);
        queryMap.put("groupName", messageGroupKey);
        queryMap.put("messageId", messageKey);
        
        MessageEntity entity = (MessageEntity) getBusinessObjectService().findByPrimaryKey(MessageEntity.class, queryMap);
        
        return toMessage(entity);
    }

    @SuppressWarnings("unchecked")
    public MessageGroupKeyList getMessageGroups() {
        List<String> keyList = new ArrayList<String>();
        Map<String, Object> criteria = new HashMap<String,Object>();
        
        criteria.put(EnumeratedValue.ENUMERATION_KEY, MessageEntity.GROUP_NAME_ENUMERATION);
        BusinessObjectService boService = KNSServiceLocator.getBusinessObjectService();
        Collection<EnumeratedValue> values = boService.findMatching(EnumeratedValue.class, criteria);
        
        Iterator<EnumeratedValue> iterator = values.iterator(); 
        while(iterator.hasNext()) {
            EnumeratedValue value = iterator.next();
            keyList.add(value.getCode());
        }
    	
    	MessageGroupKeyList messageGroupKeyList = new MessageGroupKeyList();
    	messageGroupKeyList.setMessageGroupKeys(keyList);
    	
        return messageGroupKeyList;
    }

    @SuppressWarnings("unchecked")
    public MessageList getMessages(String localeKey, String messageGroupKey) {
        
    	if(cachingEnabled){
    		//Get From Cache
    		MaxAgeSoftReference<MessageList> ref = msgsCache.get("localeKey="+localeKey+", messageGroupKey="+messageGroupKey);
    		if ( ref != null ) {
    			MessageList messageList = ref.get();
    			if(messageList!=null){
    				return messageList;
    			}
    		}
		}
    	
    	Map<String,String> fieldValues = new HashMap<String,String>();
    	fieldValues.put("locale", localeKey);
    	fieldValues.put("groupName", messageGroupKey);
    	
    	List<MessageEntity> messageEntityList = (List<MessageEntity>) getBusinessObjectService().findMatching(MessageEntity.class, fieldValues);
    	
    	List<Message> messages = new ArrayList<Message>();
    	
    	for(MessageEntity messageEntity: messageEntityList){
    		messages.add(toMessage(messageEntity));
    	}
    	
    	MessageList messageList = new MessageList();
    	messageList.setMessages(messages);
    	
    	if(cachingEnabled){
    		//Store to cache
    		msgsCache.put("localeKey="+localeKey+", messageGroupKey="+messageGroupKey, new MaxAgeSoftReference<MessageList>( msgsCacheMaxAgeSeconds, messageList) );
    	}
    	
    	return messageList;
    	
    }

    public MessageList getMessagesByGroups(String localeKey, MessageGroupKeyList messageGroupKeyList) {
    	
    	List<Message> messages = new ArrayList<Message>();
    	
    	for(String messageGroupKey: messageGroupKeyList.getMessageGroupKeys()){
    		messages.addAll(getMessages(localeKey, messageGroupKey).getMessages());
    	}
    	
    	MessageList messageList = new MessageList();
    	messageList.setMessages(messages);
    	
    	return messageList;

    }

    public Message updateMessage(String localeKey, String messageGroupKey, String messageKey, Message messageInfo) {
        
    	Map<String,String> primaryKeys = new HashMap<String,String>();
    	primaryKeys.put("locale", localeKey);
    	primaryKeys.put("groupName", messageGroupKey);
    	primaryKeys.put("messageId", messageKey);
    	
    	MessageEntity entity = (MessageEntity) getBusinessObjectService().findByPrimaryKey(MessageEntity.class, primaryKeys);
    	
    	entity.setMessageId(messageInfo.getId());
    	entity.setLocale(messageInfo.getLocale());
    	entity.setGroupName(messageInfo.getGroupName());
    	entity.setValue(messageInfo.getValue());
    	
    	getBusinessObjectService().save(entity);
    	
    	return toMessage(entity);
    	
    }

    protected MessageEntity toMessageEntity(Message message) {
        MessageEntity result = new MessageEntity();

        result.setMessageId(message.getId());
        result.setLocale(message.getLocale());
        result.setGroupName(message.getGroupName());
        result.setValue(message.getValue());

        return result;
    }

    protected Message toMessage(MessageEntity entity) {
        Message result = new Message();

        result.setGroupName(entity.getGroupName());
        result.setId(entity.getMessageId());
        result.setLocale(entity.getLocale());
        result.setValue(entity.getValue());

        return result;
    }

    protected BusinessObjectService getBusinessObjectService() {
        if (businessObjectService == null) {
            businessObjectService = KNSServiceLocator.getBusinessObjectService();
        }
        return businessObjectService;
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		if(cachingEnabled){
			msgsCache = Collections.synchronizedMap( new MaxSizeMap<String,MaxAgeSoftReference<MessageList>>( msgsCacheMaxSize ) );
		}
	}

	public void setCachingEnabled(boolean cachingEnabled) {
		this.cachingEnabled = cachingEnabled;
	}
	
}
