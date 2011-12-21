package org.kuali.student.core.messages.bo.test;

import javax.xml.namespace.QName;

import org.junit.Test;
import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.student.common.messages.dto.Message;
import org.kuali.student.common.messages.service.MessageService;
import org.kuali.student.core.test.BaseCase;

public class MessageTest extends BaseCase {

	@Test
	public void testSaveAndUpdate() {
	    QName qname = new QName("http://student.kuali.org/wsdl/messages", "MessageService");
	    MessageService messageService = (MessageService) GlobalResourceLoader.getService(qname);
		
		Message message = new Message();
		message.setGroupName("testGroup");
		message.setId("testId");
		message.setLocale("testLocale");
		message.setValue("testValue");
		
		messageService.addMessage(message);
		
		message = messageService.getMessage("testLocale", "testGroup", "testId");
		
		assertNotNull(message);
		assertEquals(message.getValue(), "testValue");
		
		message.setGroupName("notTestGroup");
		messageService.updateMessage("testLocale", "testGroup", "testId", message);
		
		message = messageService.getMessage("testLocale", "notTestGroup", "testId");
		
		assertNotNull(message);
        assertEquals(message.getValue(), "testValue");
	}
}
