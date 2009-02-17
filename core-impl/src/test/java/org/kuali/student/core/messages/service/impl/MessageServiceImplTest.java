package org.kuali.student.core.messages.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;


import org.junit.Before;
import org.junit.Test;
import org.kuali.student.core.messages.MessageException;
import org.kuali.student.core.messages.dao.impl.MessageManagementDAOImpl;
import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.messages.dto.MessageGroupKeyList;
import org.kuali.student.core.messages.dto.MessageList;
import org.kuali.student.core.messages.service.impl.MessageServiceImpl;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;

@PersistenceFileLocation("classpath:META-INF/messages-persistence.xml")
public class MessageServiceImplTest extends AbstractTransactionalDaoTest{
    @Dao(value = "org.kuali.student.core.messages.dao.impl.MessageManagementDAOImpl"/*, testDataFile = "classpath:messages-test-beans.xml"*/)
    public MessageManagementDAOImpl messageManagementDAO;
    
    private MessageServiceImpl messageService = new MessageServiceImpl();
    
	private String xmlFile = "/testMessageData.xml";
	private static final String CONTEXT_NAME = "org.kuali.student.core.messages.dto";
	private JAXBContext context;
	private Unmarshaller unmarshaller;
	private boolean populated;
	
	@Before
	public void setUp(){
			messageService.setMessageDAO(messageManagementDAO);
			if(!populated)
    		{
    			
				try {
	    			context = JAXBContext.newInstance(CONTEXT_NAME);
	    			unmarshaller = context.createUnmarshaller();
	    			MessageList messageList = (MessageList)unmarshaller.unmarshal(MessageServiceImpl.class.getResource(xmlFile));
	    	        List<Message> messages =  messageList.getMessages();
	    		    for(Message m: messages){
	    		    	messageService.addMessage(m);
	    		    }
	    		    populated =true;
	    		}
	    		catch (JAXBException e) {
	    				throw new MessageException("Message test data instantiation failed.", e);
	    		}
    		}
    		assertEquals(6, messageManagementDAO.getTotalMessages());
	}
    
    @Test
    public void testGetLocales()
    {
    	messageService.setMessageDAO(messageManagementDAO);
    	List<String> locales = messageService.getLocales().getLocales();
    	
    	assertEquals(2, locales.size());
    }
    
	@Test
    public void testGetMessageGroup(){
		messageService.setMessageDAO(messageManagementDAO);
    	List<String> groups = messageService.getMessageGroups().getMessageGroupKeys();

    	assertEquals(2, groups.size());
    }
	
	@Test
	public void testGetMessage(){
		messageService.setMessageDAO(messageManagementDAO);
		Message message = messageService.getMessage("US", "Address", "State");
		assertEquals(message.getLocale(), "US");
		assertEquals(message.getGroupName(), "Address");
		assertEquals(message.getId(), "State");
		assertEquals(message.getValue(), "State:");
		message = messageService.getMessage("CA", "Address", "State");
		assertEquals(message.getLocale(), "CA");
		assertEquals(message.getGroupName(), "Address");
		assertEquals(message.getId(), "State");
		assertEquals(message.getValue(), "Province:");
	}
	
	@Test
	public void testGetMessages(){
		messageService.setMessageDAO(messageManagementDAO);
		List<Message> messages = messageService.getMessages("US", "Address").getMessages();
		assertEquals(2, messages.size());
		for(Message m: messages){
			assertEquals(m.getLocale(), "US");
			assertEquals(m.getGroupName(), "Address");
		}
		messages = messageService.getMessages("CA", "Address").getMessages();
		assertEquals(2, messages.size());
		for(Message m: messages){
			assertEquals(m.getLocale(), "CA");
			assertEquals(m.getGroupName(), "Address");
		}
		messages = messageService.getMessages("US", "Name").getMessages();
		assertEquals(1, messages.size());
		for(Message m: messages){
			assertEquals(m.getLocale(), "US");
			assertEquals(m.getGroupName(), "Name");
		}
	}
	
	@Test
	public void testGetMessagesByGroup(){
		messageService.setMessageDAO(messageManagementDAO);
		List<String> groupKeys = new ArrayList<String>();
		groupKeys.add("Address");
		groupKeys.add("Name");
		MessageGroupKeyList groupKeyList = new MessageGroupKeyList();
		groupKeyList.setMessageGroupKeys(groupKeys);
		List<Message> messages = messageService.getMessagesByGroups("US", groupKeyList).getMessages();
		assertEquals(3, messages.size());
		for(Message m: messages){
			assertEquals(m.getLocale(), "US");
			assertTrue(m.getGroupName().equals("Address") || m.getGroupName().equals("Name"));
		}
		
		messages = messageService.getMessagesByGroups("CA", groupKeyList).getMessages();
		assertEquals(3, messages.size());
		for(Message m: messages){
			assertEquals(m.getLocale(), "CA");
			assertTrue(m.getGroupName().equals("Address") || m.getGroupName().equals("Name"));
		}
	}
	
	@Test
	public void testUpdateMessage(){
		messageService.setMessageDAO(messageManagementDAO);
		Message m = new Message();
		m.setGroupName("Course");
		m.setLocale("US");
		m.setId("Grading");
		m.setValue("Grading Scale");
		
		messageService.updateMessage("US", "Name", "Last", m);
		Message result = messageService.getMessage("US", "Course", "Grading");
		assertEquals(result.getLocale(), m.getLocale());
		assertEquals(result.getId(), m.getId());
		assertEquals(result.getValue(), m.getValue());
		assertEquals(result.getGroupName(), m.getGroupName());
		result = messageService.getMessage("US", "Name", "Last");
		assertTrue(result == null);
	}
}
