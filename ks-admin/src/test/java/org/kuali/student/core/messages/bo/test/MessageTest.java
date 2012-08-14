package org.kuali.student.core.messages.bo.test;

import javax.xml.namespace.QName;

import org.junit.Test;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.messages.infc.Message;
import org.kuali.student.r2.common.messages.service.MessageService;
import org.kuali.student.core.test.BaseCase;

public class MessageTest extends BaseCase {

	@Test
	public void testSaveAndUpdate() {
	    QName qname = new QName("http://student.kuali.org/wsdl/messages", "MessageService");
	    MessageService messageService = (MessageService) GlobalResourceLoader.getService(qname);
		
	    //TODO: fix test merge error.
		//Message message = new Message();
		//message.setGroupName("testGroup");
		//message.setId("testId");
		//message.setLocale("testLocale");
		//message.setValue("testValue");
		
//		messageService.addMessage(message, new ContextInfo());
		
//		message = messageService.getMessage("testLocale", "testGroup", "testId", new ContextInfo());
		
		//assertNotNull(message);
		//assertEquals(message.getValue(), "testValue");
		
		//message.setGroupName("notTestGroup");
		//messageService.updateMessage("testLocale", "testGroup", "testId", message, new ContextInfo());
		
		//message = messageService.getMessage("testLocale", "notTestGroup", "testId", new ContextInfo());
		
		//assertNotNull(message);
        //assertEquals(message.getValue(), "testValue");
	}
}
