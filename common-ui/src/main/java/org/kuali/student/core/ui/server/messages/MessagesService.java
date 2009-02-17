package org.kuali.student.core.ui.server.messages;

import org.kuali.student.core.messages.dto.LocaleKeyList;
import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.messages.dto.MessageGroupKeyList;
import org.kuali.student.core.messages.dto.MessageList;
import org.kuali.student.core.messages.service.MessageService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MessagesService extends RemoteServiceServlet implements org.kuali.student.core.ui.client.messages.MessagesService {

    private static final long serialVersionUID = 1L;

    private MessageService impl; // TODO inject this
    
    @Override
    public Message addMessage(Message messageInfo) {
        return impl.addMessage(messageInfo);
    }

    @Override
    public LocaleKeyList getLocales() {
        return impl.getLocales();
    }

    @Override
    public Message getMessage(String localeKey, String messageGroupKey, String messageKey) {
        return impl.getMessage(localeKey, messageGroupKey, messageKey);
    }

    @Override
    public MessageGroupKeyList getMessageGroups() {
        return impl.getMessageGroups();
    }

    @Override
    public MessageList getMessages(String localeKey, String messageGroupKey) {
        return impl.getMessages(localeKey, messageGroupKey);
    }

    @Override
    public MessageList getMessagesByGroups(String localeKey, MessageGroupKeyList messageGroupKeyList) {
        return impl.getMessagesByGroups(localeKey, messageGroupKeyList);
    }

    @Override
    public Message updateMessage(String localeKey, String messageGroupKey, String messageKey, Message messageInfo) {
        return impl.updateMessage(localeKey, messageGroupKey, messageKey, messageInfo);
    }

}
