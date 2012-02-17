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

package org.kuali.student.r1.common.ui.server.gwt;

import org.kuali.student.r1.common.messages.dto.LocaleKeyList;
import org.kuali.student.r1.common.messages.dto.Message;
import org.kuali.student.r1.common.messages.dto.MessageGroupKeyList;
import org.kuali.student.r1.common.messages.dto.MessageList;
import org.kuali.student.r1.common.ui.client.service.MessagesRpcService;

import org.kuali.student.r2.common.dto.StatusInfo;
import java.util.List;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.messages.dto.MessageInfo;
import org.kuali.student.r2.common.messages.service.MessageService;

@Deprecated
public class MessagesRpcGwtServlet extends RemoteServiceServlet implements MessagesRpcService {

    private static final long serialVersionUID = 1L;

    private MessageService serviceImpl; 

    @Override
    public StatusInfo addMessage(LocaleInfo localeInfo, String messageGroupKey, MessageInfo messageInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        return serviceImpl.addMessage(localeInfo, messageGroupKey ,messageInfo, contextInfo);
        
    }

    @Override
    public List<LocaleInfo> getLocales(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return serviceImpl.getLocales(contextInfo);
    }

    @Override
    public MessageInfo getMessage(LocaleInfo localeInfo, String messageGroupKey, String messageKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        return serviceImpl.getMessage(localeInfo, messageGroupKey, messageKey, contextInfo);
    }

//    @Override
//    public MessageGroupKeyList getMessageGroups() {
//        return serviceImpl.getMessageGroups();
//    }

    @Override
    public List<MessageInfo> getMessages(LocaleInfo localeInfo, String messageGroupKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        return serviceImpl.getMessages(localeInfo, messageGroupKey, contextInfo);
    }

    @Override
    public List<MessageInfo> getMessagesByGroups(LocaleInfo localeInfo, List<String> messageGroupKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        return serviceImpl.getMessagesByGroups(localeInfo, messageGroupKeys, contextInfo);
    }

    @Override
    public MessageInfo updateMessage(LocaleInfo localeInfo, String messageKey, MessageInfo messageInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException{
        return serviceImpl.updateMessage(localeInfo, messageKey, messageInfo, contextInfo);
    }

    public MessageService getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(MessageService impl) {
        this.serviceImpl = impl;
    }

}