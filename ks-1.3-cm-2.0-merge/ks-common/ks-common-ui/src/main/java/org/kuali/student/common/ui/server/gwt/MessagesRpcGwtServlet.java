/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.common.ui.server.gwt;

import java.util.List;

import org.kuali.student.common.ui.client.service.MessagesRpcService;
import org.kuali.student.r1.common.messages.dto.Message;
import org.kuali.student.r1.common.messages.dto.MessageGroupKeyList;
import org.kuali.student.r1.common.messages.dto.MessageList;
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
import org.kuali.student.r2.common.util.ContextUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MessagesRpcGwtServlet extends RemoteServiceServlet implements MessagesRpcService {

    private static final long serialVersionUID = 1L;

    private MessageService serviceImpl;

    @Override
    public StatusInfo addMessage(Message messageInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(messageInfo.getLocale());
        return serviceImpl.addMessage(localeInfo, messageInfo.getId(), new MessageInfo(), ContextUtils.getContextInfo());

    }

    @Override
    public List<LocaleInfo> getLocales() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return serviceImpl.getLocales(ContextUtils.getContextInfo());
    }

    @Override
    public MessageInfo getMessage(String localeKey, String messageGroupKey, String messageKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(localeKey);
        return serviceImpl.getMessage(localeInfo, messageGroupKey, messageKey, ContextUtils.getContextInfo());
    }

    // TODO fix merge
    // @Override
    // public MessageGroupKeyList getMessageGroups() {
    // return serviceImpl.getMessageGroups();
    // }

    @Override
    public List<MessageInfo> getMessages(String localeKey, String messageGroupKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(localeKey);
        return serviceImpl.getMessages(localeInfo, messageGroupKey, ContextUtils.getContextInfo());
    }

    @Override
    public MessageList getMessagesByGroups(String localeKey, MessageGroupKeyList messageGroupKeyList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(localeKey);
        MessageList list = new MessageList();
        list.setMessages(serviceImpl.getMessagesByGroups(localeInfo, messageGroupKeyList.getMessageGroupKeys(), ContextUtils.getContextInfo()));
        return list;
    }

    @Override
    public MessageInfo updateMessage(String localeKey, String messageGroupKey, String messageKey, Message messageInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(localeKey);
        return serviceImpl.updateMessage(localeInfo, messageKey, new MessageInfo(), ContextUtils.getContextInfo());
    }

    public MessageService getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(MessageService impl) {
        this.serviceImpl = impl;
    }

}