package org.kuali.student.common.ui.server.messages;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.messages.dto.MessageGroupKeyList;
import org.kuali.student.core.messages.dto.MessageList;
import org.kuali.student.core.messages.service.MessageService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.impl.StandardSerializationPolicy;

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
            
            
            return escapeForSingleQuotedJavaScriptString(serializedData);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
 // Server Side String utils
    public static String escapeForSingleQuotedJavaScriptString( String s ) 
    {
        s = escapeScriptTags( s ); // <script> -> <xxxscript>
        s = escapeBackslash( s );
        s = escapeSingleQuotes( s );
        return s;
    }
    public static String escapeScriptTags( String s )
    {
        return s
            .replaceAll("(?si)<\\s*script.*?>", "<xxxscript>")
            .replaceAll("(?si)</\\s*script\\s*>", "</xxxscript>");
    }
    public static String escapeBackslash( String s )
    {
        return s.replaceAll("\\\\","\\\\\\\\" );
    }
    public static String escapeSingleQuotes( String s ) {
        return s.replaceAll("'","\\\\'" );
    }
}
