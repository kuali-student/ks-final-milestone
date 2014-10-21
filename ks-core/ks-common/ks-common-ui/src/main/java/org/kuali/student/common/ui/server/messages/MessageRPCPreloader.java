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

package org.kuali.student.common.ui.server.messages;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.common.ui.server.gwt.MessagesRpcGwtServlet;
import org.kuali.student.common.ui.server.serialization.KSSerializationPolicy;
import org.kuali.student.common.ui.server.serialization.SerializationUtils;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.messages.dto.MessageInfo;
import org.kuali.student.r2.common.messages.service.MessageService;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.common.util.constants.MessageServiceConstants;

import com.google.gwt.user.server.rpc.RPC;

public class MessageRPCPreloader {
	final Logger LOG = Logger.getLogger(MessageRPCPreloader.class);
    private final String MESSAGE_SERVICE_MOCK = "ks.messageService";
    
    MessageService messageService;
    
    public MessageRPCPreloader(){
        
    }
    
    public MessageService getMessageService() {
        if (messageService == null){
            setMessageService((MessageService)GlobalResourceLoader.getService(MESSAGE_SERVICE_MOCK));
            if (messageService == null){
                setMessageService((MessageService)GlobalResourceLoader.getService(new QName(MessageServiceConstants.NAMESPACE,"MessageService")));
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
            serviceMethod = MessagesRpcGwtServlet.class.getMethod("getMessagesByGroups", String.class, ArrayList.class);
            
            LocaleInfo localeInfo = new LocaleInfo();
            localeInfo.setLocaleLanguage(locale);
            ArrayList<String> messageGroupKeys = new ArrayList (Arrays.asList(keys));
            ArrayList<MessageInfo> messages = new ArrayList<MessageInfo>();
            for (MessageInfo info : getMessageService().getMessagesByGroups(localeInfo, messageGroupKeys, ContextUtils.getContextInfo())){
                messages.add(info);
            }
            
            Map<Class<?>, Boolean> whitelist = new HashMap<Class<?>, Boolean>();
            whitelist.put(MessageService.class, true);
            whitelist.put(MessageInfo.class,true);
            whitelist.put(LocaleInfo.class,true);
            
            KSSerializationPolicy myPolicy = new KSSerializationPolicy(whitelist);
            
            //String serializedData = RPC.encodeResponseForSuccess(serviceMethod, messageList,KSSerializationPolicy.getInstance());
            String serializedData = RPC.encodeResponseForSuccess(serviceMethod, messages, myPolicy);
            LOG.info("preloaded " + messages.size() + " messages for " + keys);
            return SerializationUtils.escapeForSingleQuotedJavaScriptString(serializedData);
        } catch (Exception e) {
            LOG.error(e);
            return "";
        }

    }
    
}
