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

package org.kuali.student.common.ui.client.service;


import org.kuali.student.r1.common.messages.dto.LocaleKeyList;

import org.kuali.student.r1.common.messages.dto.Message;
import org.kuali.student.r1.common.messages.dto.MessageGroupKeyList;
import org.kuali.student.r1.common.messages.dto.MessageList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import org.kuali.student.r2.common.dto.StatusInfo;
import java.util.List;
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

@RemoteServiceRelativePath("rpcservices/MessagesRpcService")
public interface MessagesRpcService extends RemoteService {
    public static class Util {

        public static MessagesRpcServiceAsync getInstance(String uri) {
            MessagesRpcServiceAsync result = GWT.create(MessagesRpcService.class);
            ((ServiceDefTarget)result).setServiceEntryPoint(GWT.getModuleBaseURL() + uri);
            return result;
        }
    }
    
    public List<LocaleInfo> getLocales(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

//    public MessageGroupKeyList getMessageGroups();

    public MessageInfo getMessage(LocaleInfo localeInfo, String messageGroupKey, String messageKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    public List<MessageInfo> getMessages(LocaleInfo localeInfo, String messageGroupKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    public List<MessageInfo> getMessagesByGroups(LocaleInfo localeInfo, List<String> messageGroupKeys, ContextInfo contextInfo)throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    public MessageInfo updateMessage(LocaleInfo localeInfo, String messageKey, MessageInfo messageInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;
    
    public StatusInfo addMessage(LocaleInfo localeInfo, String messageGroupKey, MessageInfo messageInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
}
