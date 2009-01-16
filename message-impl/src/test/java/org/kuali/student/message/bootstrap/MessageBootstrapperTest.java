package org.kuali.student.message.bootstrap;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.kuali.student.message.dao.impl.MessageManagementDAOImpl;
import org.kuali.student.message.dto.Message;
import org.kuali.student.message.service.impl.MessageServiceImpl;
import org.kuali.student.message.bootstrap.*;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
@PersistenceFileLocation("classpath:META-INF/message-persistence.xml")
public class MessageBootstrapperTest  extends AbstractTransactionalDaoTest{
    @Dao(value = "org.kuali.student.message.dao.impl.MessageManagementDAOImpl")
    public MessageManagementDAOImpl messageManagementDAO;
    
    private MessageServiceImpl messageService = new MessageServiceImpl();
    
    @Test
    public void testMessageBootstapper(){
    	messageService.setMessageDAO(messageManagementDAO);
    	MessageBootstrapper.messageService = this.messageService;
    	MessageBootstrapper.bootstrap("US", "Address", "/org/kuali/student/message/testMessages/address_EN.xml");
    	
    	List<Message> messages = messageService.getMessages("US", "Address").getMessages();
    	assertEquals(messages.size(), 2);
    	for(Message m: messages){
    		assertEquals(m.getLocale(), "US");
    		assertEquals(m.getGroupName(), "Address");
    	}
    	
    	MessageBootstrapper.bootstrap("US", "Address", "/org/kuali/student/message/testMessages/address_EN.xml");
    	messages = messageService.getMessages("US", "Address").getMessages();
    	assertEquals(messages.size(), 2);
    	for(Message m: messages){
    		assertEquals(m.getLocale(), "US");
    		assertEquals(m.getGroupName(), "Address");
    	}
    	
    	MessageBootstrapper.bootstrap("US", "Name", "/org/kuali/student/message/testMessages/name_EN.xml");
    	messages = messageService.getMessages("US", "Name").getMessages();
    	assertEquals(messages.size(), 2);
    	
    	List<String> groups = messageService.getMessageGroups().getMessageGroupKeys();
    	assertEquals(groups.size(), 2);
    	
    }
}
