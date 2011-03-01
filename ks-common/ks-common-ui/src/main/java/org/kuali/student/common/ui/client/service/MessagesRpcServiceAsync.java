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

import org.kuali.student.common.messages.dto.LocaleKeyList;
import org.kuali.student.common.messages.dto.Message;
import org.kuali.student.common.messages.dto.MessageGroupKeyList;
import org.kuali.student.common.messages.dto.MessageList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MessagesRpcServiceAsync {
    public void getLocales(AsyncCallback<LocaleKeyList> callback);

    public void getMessageGroups(AsyncCallback<MessageGroupKeyList> callback);

    public void getMessage(
            String localeKey, 
            String messageGroupKey, 
            String messageKey, 
            AsyncCallback<Message> callback);
    
    public void getMessages(
            String localeKey, 
            String messageGroupKey, 
            AsyncCallback<MessageList> callback);
    
    public void getMessagesByGroups(
            String localeKey, 
            MessageGroupKeyList messageGroupKeyList, 
            AsyncCallback<MessageList> callback);
    
    public void updateMessage(
            String localeKey, 
            String messageGroupKey, 
            String messageKey,
            Message messageInfo, 
            AsyncCallback<Message> callback);
    
    public void addMessage(Message messageInfo, 
            AsyncCallback<Message> callback);
}
