package org.kuali.student.common.ui.server.messages;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.student.common.ui.server.gwt.MessagesRpcGwtServlet;
import org.kuali.student.common.ui.server.serialization.KSSerializationPolicy;
import org.kuali.student.common.ui.server.serialization.SerializationUtils;
import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.messages.dto.MessageGroupKeyList;
import org.kuali.student.core.messages.dto.MessageList;
import org.kuali.student.core.messages.service.MessageService;

import com.google.gwt.user.server.rpc.RPC;

public class MessageRPCPreloader {
    private final String MESSAGE_SERVICE_MOCK = "ks.messageServiceMock";
	private final String MESSAGE_SERVICE = "{http://student.kuali.org/core/messages}MessageService";
    
    MessageService messageService;
    
    public MessageRPCPreloader(){
        
    }
    
    public MessageService getMessageService() {
        if (messageService == null){
            setMessageService((MessageService)GlobalResourceLoader.getService(MESSAGE_SERVICE_MOCK));
            if (messageService == null){
                setMessageService((MessageService)GlobalResourceLoader.getService(MESSAGE_SERVICE));
            }
        }
        return messageService;
    }

    public void setMessageService(MessageService serviceImpl) {
        this.messageService = serviceImpl;
    }

    public String getMessagesByGroupsEncodingString(String locale, String[] keys){
        Method serviceMethod;
        try {
            serviceMethod = MessagesRpcGwtServlet.class.getMethod("getMessagesByGroups", String.class,MessageGroupKeyList.class);
            
            MessageGroupKeyList messageGroupKeyList = new MessageGroupKeyList();
            messageGroupKeyList.setMessageGroupKeys(Arrays.asList(keys));
            
            MessageList messageList = getMessageService().getMessagesByGroups(locale,messageGroupKeyList);

            Map<Class<?>, Boolean> whitelist = new HashMap<Class<?>, Boolean>();
            whitelist.put(MessageService.class, true);
            whitelist.put(MessageList.class, true);
            whitelist.put(MessageGroupKeyList.class,true);
            whitelist.put(Message.class,true);
            whitelist.put(MessageGroupKeyList.class,true);
            
            KSSerializationPolicy myPolicy = new KSSerializationPolicy(whitelist);
            
            //String serializedData = RPC.encodeResponseForSuccess(serviceMethod, messageList,KSSerializationPolicy.getInstance());
            String serializedData = RPC.encodeResponseForSuccess(serviceMethod, messageList,myPolicy);
            
            
            return SerializationUtils.escapeForSingleQuotedJavaScriptString(serializedData);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
    
}
