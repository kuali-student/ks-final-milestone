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

package org.kuali.student.common.ui.server.gwt;

import org.kuali.student.common.messages.dto.LocaleKeyList;
import org.kuali.student.common.messages.dto.Message;
import org.kuali.student.common.messages.dto.MessageGroupKeyList;
import org.kuali.student.common.messages.dto.MessageList;
import org.kuali.student.common.messages.service.MessageService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MessagesRpcGwtServlet extends RemoteServiceServlet implements org.kuali.student.common.ui.client.service.MessagesRpcService {

    private static final long serialVersionUID = 1L;

    private MessageService serviceImpl; 
    
    @Override
    public Message addMessage(Message messageInfo) {
        return serviceImpl.addMessage(messageInfo);
    }

    @Override
    public LocaleKeyList getLocales() {
        return serviceImpl.getLocales();
    }

    @Override
    public Message getMessage(String localeKey, String messageGroupKey, String messageKey) {
        return serviceImpl.getMessage(localeKey, messageGroupKey, messageKey);
    }

    @Override
    public MessageGroupKeyList getMessageGroups() {
        return serviceImpl.getMessageGroups();
    }

    @Override
    public MessageList getMessages(String localeKey, String messageGroupKey) {
        return serviceImpl.getMessages(localeKey, messageGroupKey);
    }

    @Override
    public MessageList getMessagesByGroups(String localeKey, MessageGroupKeyList messageGroupKeyList) {
        return serviceImpl.getMessagesByGroups(localeKey, messageGroupKeyList);
    }

    @Override
    public Message updateMessage(String localeKey, String messageGroupKey, String messageKey, Message messageInfo) {
        return serviceImpl.updateMessage(localeKey, messageGroupKey, messageKey, messageInfo);
    }

    public MessageService getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(MessageService impl) {
        this.serviceImpl = impl;
    }

}
