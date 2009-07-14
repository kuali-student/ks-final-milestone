package org.kuali.student.common.ui.client.messages;

import org.kuali.student.core.messages.dto.LocaleKeyList;
import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.messages.dto.MessageGroupKeyList;
import org.kuali.student.core.messages.dto.MessageList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MessagesServiceAsync {
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
