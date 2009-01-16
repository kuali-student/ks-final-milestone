package org.kuali.student.message.bootstrap;



import javax.xml.namespace.QName;

import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.kuali.student.message.MessageException;
import org.kuali.student.message.dto.Message;
import org.kuali.student.message.dto.MessageList;
import org.kuali.student.message.entity.MessageEntity;
import org.kuali.student.message.service.MessageService;
import org.kuali.student.message.service.impl.MessageServiceImpl;
import org.kuali.student.message.service.impl.util.POJOConverter;

public class MessageBootstrapper {
	static final String CONTEXT_NAME = "org.kuali.student.message.dto";
	public static MessageService messageService;
	
	public static void bootstrap(String locale, String groupName, String fileLocation){
		MessageList foundMessages = messageService.getMessages(locale, groupName);
		if(foundMessages.getMessages().isEmpty()){
			
			JAXBContext context;
			Unmarshaller unmarshaller;

			try {
    			context = JAXBContext.newInstance(CONTEXT_NAME);
    			unmarshaller = context.createUnmarshaller();
    			MessageList messageList = (MessageList)unmarshaller.unmarshal(MessageBootstrapper.class.getResource(fileLocation));
    	        List<Message> messages =  messageList.getMessages();
    		    for(Message m: messages){
    		    	messageService.addMessage(m);
    		    }
    		}	
    		catch (JAXBException e) {
    				throw new MessageException("Message test data instantiation failed.", e);
    		}

		}
	}
	
}
