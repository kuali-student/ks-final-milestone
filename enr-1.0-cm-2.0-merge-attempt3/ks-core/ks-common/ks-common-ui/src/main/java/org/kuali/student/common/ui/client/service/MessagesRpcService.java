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


import java.util.List;

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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

@RemoteServiceRelativePath("rpcservices/MessagesRpcService")
public interface MessagesRpcService extends RemoteService {
    public static class Util {

        public static MessagesRpcServiceAsync getInstance(String uri) {
            MessagesRpcServiceAsync result = GWT.create(MessagesRpcService.class);
            ((ServiceDefTarget)result).setServiceEntryPoint(GWT.getModuleBaseURL() + uri);
            return result;
        }
    }
    
    public List<LocaleInfo> getLocales() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

 // TODO fix merge
    //public MessageGroupKeyList getMessageGroups();

    public MessageInfo getMessage(String localeKey, 
            String messageGroupKey, 
            String messageKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    public List<MessageInfo> getMessages(String localeKey, 
            String messageGroupKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    public MessageList getMessagesByGroups(String localeKey, 
            MessageGroupKeyList messageGroupKeyList)throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    public MessageInfo updateMessage(String localeKey, 
            String messageGroupKey, 
            String messageKey,
            Message messageInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;
    
    public StatusInfo addMessage(Message messageInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
}
