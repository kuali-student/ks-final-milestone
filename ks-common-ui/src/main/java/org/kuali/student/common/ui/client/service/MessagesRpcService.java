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
