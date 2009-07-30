package org.kuali.student.common.ui.server.gwt;

import org.kuali.student.core.messages.dto.LocaleKeyList;
import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.messages.dto.MessageGroupKeyList;
import org.kuali.student.core.messages.dto.MessageList;
import org.kuali.student.core.messages.service.MessageService;

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
