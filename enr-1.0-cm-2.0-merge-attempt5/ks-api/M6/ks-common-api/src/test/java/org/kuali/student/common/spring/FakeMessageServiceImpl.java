/*
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.spring;

import java.io.Serializable;
import java.util.List;

import javax.jws.WebParam;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.messages.dto.MessageInfo;
import org.kuali.student.r2.common.messages.service.MessageService;

/**
 * @author Kuali Student Team
 * 
 * This class exists purely to test the WebServiceAwareSpringBeanPostProcessor class.
 */
public class FakeMessageServiceImpl  extends AbstractFakeService implements MessageService, Serializable {

	/**
	 * 
	 */
	public FakeMessageServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.r2.common.messages.service.MessageService#getLocales(org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<LocaleInfo> getLocales(ContextInfo contextInfo)
	        throws InvalidParameterException, MissingParameterException,
	        OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.r2.common.messages.service.MessageService#getMessageGroupKeys(org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<String> getMessageGroupKeys(ContextInfo contextInfo)
	        throws InvalidParameterException, MissingParameterException,
	        OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.r2.common.messages.service.MessageService#getMessage(org.kuali.student.r2.common.dto.LocaleInfo, java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public MessageInfo getMessage( LocaleInfo localeInfo,
	         String messageGroupKey,
	         String messageKey,
	         ContextInfo contextInfo)
	        throws DoesNotExistException, InvalidParameterException,
	        MissingParameterException, OperationFailedException,
	        PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.r2.common.messages.service.MessageService#getMessages(org.kuali.student.r2.common.dto.LocaleInfo, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<MessageInfo> getMessages( LocaleInfo localeInfo,
	         String messageGroupKey,
	         ContextInfo contextInfo)
	        throws DoesNotExistException, InvalidParameterException,
	        MissingParameterException, OperationFailedException,
	        PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.r2.common.messages.service.MessageService#getMessagesByGroups(org.kuali.student.r2.common.dto.LocaleInfo, java.util.List, org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<MessageInfo> getMessagesByGroups( LocaleInfo localeInfo,
	         List<String> messageGroupKeys,
	         ContextInfo contextInfo)
	        throws DoesNotExistException, InvalidParameterException,
	        MissingParameterException, OperationFailedException,
	        PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.r2.common.messages.service.MessageService#updateMessage(org.kuali.student.r2.common.dto.LocaleInfo, java.lang.String, org.kuali.student.r2.common.messages.dto.MessageInfo, org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public MessageInfo updateMessage( LocaleInfo localeInfo,
	         String messageKey,
	         MessageInfo messageInfo,
	         ContextInfo contextInfo)
	        throws DoesNotExistException, InvalidParameterException,
	        MissingParameterException, OperationFailedException,
	        PermissionDeniedException, ReadOnlyException,
	        VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.r2.common.messages.service.MessageService#deleteMessage(org.kuali.student.r2.common.dto.LocaleInfo, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public StatusInfo deleteMessage( LocaleInfo localeInfo,
	         String messageKey,
	         ContextInfo contextInfo)
	        throws DoesNotExistException, InvalidParameterException,
	        MissingParameterException, OperationFailedException,
	        PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.r2.common.messages.service.MessageService#addMessage(org.kuali.student.r2.common.dto.LocaleInfo, java.lang.String, org.kuali.student.r2.common.messages.dto.MessageInfo, org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public StatusInfo addMessage( LocaleInfo localeInfo,
	         String messageGroupKey,
	         MessageInfo messageInfo,
	         ContextInfo contextInfo)
	        throws DoesNotExistException, InvalidParameterException,
	        MissingParameterException, OperationFailedException,
	        PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public String toString() {
		return getClass().getName();
    }

	
}
