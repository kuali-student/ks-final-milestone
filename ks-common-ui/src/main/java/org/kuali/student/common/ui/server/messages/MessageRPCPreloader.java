package org.kuali.student.common.ui.server.messages;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.server.serialization.KSSerializationPolicy;
import org.kuali.student.common.ui.server.serialization.SerializationUtils;
import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.messages.dto.MessageGroupKeyList;
import org.kuali.student.core.messages.dto.MessageList;
import org.kuali.student.core.messages.service.MessageService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gwt.user.server.rpc.RPC;

public class MessageRPCPreloader {
    MessageService serviceImpl;
    public MessageRPCPreloader(){

    }
    
    public MessageService getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(MessageService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    public String getMessagesByGroupsEncodingString(String locale, String[] keys){
        ApplicationContext context = new ClassPathXmlApplicationContext("gwt-context.xml");
        serviceImpl = (MessageService)context.getBean("messageServiceImpl");
        Method serviceMethod;
        try {
            serviceMethod = MessagesService.class.getMethod("getMessagesByGroups", String.class,MessageGroupKeyList.class);
            
            MessageGroupKeyList messageGroupKeyList = new MessageGroupKeyList();
            messageGroupKeyList.setMessageGroupKeys(Arrays.asList(keys));
            
            MessageList messageList = serviceImpl.getMessagesByGroups(locale,messageGroupKeyList);

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
