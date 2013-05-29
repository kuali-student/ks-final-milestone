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

import org.kuali.student.r1.core.messages.dao.MessageManagementDAO;
import org.kuali.student.r1.core.messages.entity.MessageEntity;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.messages.dto.MessageInfo;
import org.kuali.student.r2.common.messages.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly=true)
	public List<LocaleInfo> getLocales(ContextInfo contextInfo) {
        
		List<String> locales = this.messageDAO.getLocales();
		
		List<LocaleInfo> localeInfos = new ArrayList<LocaleInfo>();
		for (String locale : locales){
		    LocaleInfo localeInfo = new LocaleInfo();
		    localeInfo.setLocaleLanguage(locale);
		    localeInfos.add(localeInfo);
		}
  
		return localeInfos;
	}
    
    @Override
    @Transactional(readOnly=true)
    public List<String> getMessageGroupKeys(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		List<String> groups = this.messageDAO.getMessageGroups();
		
		return groups;
	}

    @Override
    @Transactional(readOnly=true)
	public MessageInfo getMessage(LocaleInfo localeInfo, String messageGroupKey, String messageKey, ContextInfo contextInfo) {
        MessageInfo message = null;
		if(localeInfo == null || messageGroupKey == null || messageKey == null){
			return null;
		}
		else{
			MessageEntity messageEntity = this.messageDAO.getMessage(localeInfo.getLocaleLanguage(), messageGroupKey, messageKey);
			if(messageEntity != null){
				message = new MessageInfo();
				MessageAssembler.toMessage(messageEntity,message); 
			}
		}
		return message;
	}

    @Override
    @Transactional(readOnly=true)
	public List<MessageInfo> getMessagesByGroup(LocaleInfo localeInfo, String messageGroupKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		if(localeInfo == null || messageGroupKey == null){
			return new ArrayList<MessageInfo>();
		}
		else{
			List<MessageEntity> messages =  this.messageDAO.getMessages(localeInfo.getLocaleLanguage(), messageGroupKey);
	        
	        return MessageAssembler.toMessageList(messages, MessageInfo.class);
		}
	}

	@Override
    @Transactional(readOnly=true)
	public List<MessageInfo> getMessagesByGroups(LocaleInfo localeInfo, List<String> messageGroupKeys, ContextInfo contextInfo)  throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		if(localeInfo == null || messageGroupKeys == null){
		    return new ArrayList<MessageInfo>();
		}
		else{
			List<MessageEntity> messages =  this.messageDAO.getMessagesByGroups(localeInfo.getLocaleLanguage(), messageGroupKeys);
		    return MessageAssembler.toMessageList(messages, MessageInfo.class);
		}
	}
    
	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public MessageInfo updateMessage(LocaleInfo localeInfo, 
        String messageGroupKey,
        String messageKey, 
        MessageInfo messageInfo, 
        ContextInfo contextInfo) 
                throws DoesNotExistException, 
                DataValidationErrorException,
                InvalidParameterException, 
                MissingParameterException, 
                OperationFailedException, 
                PermissionDeniedException, 
                ReadOnlyException, 
                VersionMismatchException {
		
		if(localeInfo == null || messageGroupKey == null || messageKey == null || messageInfo == null){
			throw new MissingParameterException ();
		}
		else{
		    MessageEntity messageEntity = new MessageEntity();    
		    MessageAssembler.toMessageEntity( messageInfo, messageEntity);
		    messageEntity =  messageDAO.updateMessage(localeInfo.getLocaleLanguage(), messageGroupKey, messageKey, messageEntity);
		    MessageAssembler.toMessage( messageEntity,messageInfo);
		    return messageInfo;
		}        
	}

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo createMessage(LocaleInfo localeInfo,
            String messageGroupKey,
            String messageKey,
            MessageInfo messageInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (messageInfo != null) {
            MessageEntity messageEntity = new MessageEntity();
            MessageAssembler.toMessageEntity(messageInfo, messageEntity);
            messageEntity = messageDAO.addMessage(messageEntity);
            MessageAssembler.toMessage(messageEntity, messageInfo);
        }
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public List<ValidationResultInfo> validateProposal(String validationTypeKey,
            MessageInfo messageInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        throw new OperationFailedException("Not supported yet.");
    }

    

    @Override
    public StatusInfo deleteMessage(LocaleInfo localeInfo, 
    String messageGroupKey, 
    String messageKey, 
    ContextInfo contextInfo) 
            throws DoesNotExistException, 
            InvalidParameterException, 
            MissingParameterException, 
            OperationFailedException, 
            PermissionDeniedException {
        // TODO pctsw - THIS METHOD NEEDS JAVADOCS
        throw new OperationFailedException ("not implemented");
    }
    
}
