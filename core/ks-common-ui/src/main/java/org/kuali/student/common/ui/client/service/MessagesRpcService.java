/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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


import org.kuali.student.core.messages.dto.LocaleKeyList;
import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.messages.dto.MessageGroupKeyList;
import org.kuali.student.core.messages.dto.MessageList;

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
    
    public LocaleKeyList getLocales();

    public MessageGroupKeyList getMessageGroups();

    public Message getMessage(
            String localeKey, 
            String messageGroupKey, 
            String messageKey);
    
    public MessageList getMessages(
            String localeKey, 
            String messageGroupKey);
    
    public MessageList getMessagesByGroups(
            String localeKey, 
            MessageGroupKeyList messageGroupKeyList);
    
    public Message updateMessage(
            String localeKey, 
            String messageGroupKey, 
            String messageKey,
            Message messageInfo);
    
    public Message addMessage(Message messageInfo);
    
}
