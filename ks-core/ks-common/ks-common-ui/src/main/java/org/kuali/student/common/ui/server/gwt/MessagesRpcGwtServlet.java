/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.common.ui.server.gwt;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import org.kuali.student.common.ui.client.service.MessagesRpcService;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
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
import org.kuali.student.r2.common.util.ContextUtils;

public class MessagesRpcGwtServlet extends RemoteServiceServlet implements MessagesRpcService {

    private static final long serialVersionUID = 1L;

    private MessageService serviceImpl;

    @Override
    public StatusInfo createMessage(MessageInfo messageInfo) 
            throws DoesNotExistException, 
            InvalidParameterException, 
            MissingParameterException, 
            OperationFailedException, 
            PermissionDeniedException {
        try {
            return serviceImpl.createMessage(messageInfo.getLocale(), messageInfo.getGroupName(), messageInfo.getMessageKey(), messageInfo, ContextUtils.getContextInfo());
        } catch (DataValidationErrorException ex) {
            // data validation error cannot translate to GWT so have to convert
            throw new OperationFailedException ("data errors", ex);
        }

    }

    @Override
    public ArrayList<String> getMessageGroups() 
            throws InvalidParameterException, 
            MissingParameterException, 
            OperationFailedException, 
            PermissionDeniedException {
        return new ArrayList (serviceImpl.getMessageGroupKeys(ContextUtils.getContextInfo()));
    }
    
    

    @Override
    public ArrayList<LocaleInfo> getLocales() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList (serviceImpl.getLocales(ContextUtils.getContextInfo()));
    }

    @Override
    public MessageInfo getMessage(String localeKey, String messageGroupKey, String messageKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(localeKey);
        return serviceImpl.getMessage(localeInfo, messageGroupKey, messageKey, ContextUtils.getContextInfo());
    }

    @Override
    public ArrayList<MessageInfo> getMessagesByGroup(String localeKey, String messageGroupKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(localeKey);
        return new ArrayList (serviceImpl.getMessagesByGroup(localeInfo, messageGroupKey, ContextUtils.getContextInfo()));
    }

    @Override
    public ArrayList<MessageInfo> getMessagesByGroups(String localeKey, ArrayList<String> messageGroupKeys) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(localeKey);
        return new ArrayList (serviceImpl.getMessagesByGroups(localeInfo, messageGroupKeys, ContextUtils.getContextInfo()));
    }

    @Override
    public MessageInfo updateMessage(String localeKey, String messageGroupKey, String messageKey, MessageInfo messageInfo) 
            throws DoesNotExistException, 
            InvalidParameterException, 
            MissingParameterException, 
            OperationFailedException, 
            PermissionDeniedException, 
            ReadOnlyException, 
            VersionMismatchException {
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(localeKey);
        try {
            return serviceImpl.updateMessage(localeInfo, messageGroupKey, messageKey, messageInfo, ContextUtils.getContextInfo());
        } catch (DataValidationErrorException ex) {
            // data validation error cannot translate to GWT so have to convert
            throw new OperationFailedException ("data errors", ex);
        }
    }

    public MessageService getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(MessageService impl) {
        this.serviceImpl = impl;
    }

}