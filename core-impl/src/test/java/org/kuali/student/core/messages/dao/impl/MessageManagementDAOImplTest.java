package org.kuali.student.core.messages.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.core.messages.MessageException;
import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.messages.dto.MessageList;
import org.kuali.student.core.messages.entity.MessageEntity;
import org.kuali.student.core.messages.service.impl.MessageServiceImpl;
import org.kuali.student.core.messages.service.impl.util.POJOConverter;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;

@PersistenceFileLocation("classpath:META-INF/messages-persistence.xml")
public class MessageManagementDAOImplTest extends AbstractTransactionalDaoTest{
    @Dao(value = "org.kuali.student.core.messages.dao.impl.MessageManagementDAOImpl"/*, testDataFile = "classpath:messages-test-beans.xml"*/)
    public MessageManagementDAOImpl messageManagementDAO;
    
	private String xmlFile = "/testMessageData.xml";
	private static final String CONTEXT_NAME = "org.kuali.student.core.messages.dto";
	private JAXBContext context;
	private Unmarshaller unmarshaller;
	private boolean populated;
	
	@Before
	public void setUp(){
    		if(!populated)
    		{
    			
				try {
	    			context = JAXBContext.newInstance(CONTEXT_NAME);
	    			unmarshaller = context.createUnmarshaller();
	    			MessageList messageList = (MessageList)unmarshaller.unmarshal(MessageServiceImpl.class.getResource(xmlFile));
	    	        List<Message> messages =  messageList.getMessages();
	    		    List<MessageEntity> messageEntities =  POJOConverter.mapList(messages, MessageEntity.class);
	    		    for(MessageEntity me: messageEntities){
	    		    	messageManagementDAO.addMessage(me);
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
    public void testGetLocales(){
    	List<String> locales = messageManagementDAO.getLocales();
    	
    	assertEquals(2, locales.size());
    }
    
	@Test
    public void testGetMessageGroup(){
    	List<String> groups = messageManagementDAO.getMessageGroups();

    	assertEquals(2, groups.size());
    }
	
	@Test
	public void testGetMessage(){
		MessageEntity message = messageManagementDAO.getMessage("US", "Address", "State");
		assertEquals(message.getLocale(), "US");
		assertEquals(message.getGroupName(), "Address");
		assertEquals(message.getId(), "State");
		assertEquals(message.getValue(), "State:");
		message = messageManagementDAO.getMessage("CA", "Address", "State");
		assertEquals(message.getLocale(), "CA");
		assertEquals(message.getGroupName(), "Address");
		assertEquals(message.getId(), "State");
		assertEquals(message.getValue(), "Province:");
	}
	
	@Test
	public void testGetMessages(){
		List<MessageEntity> messages = messageManagementDAO.getMessages("US", "Address");
		assertEquals(2, messages.size());
		for(MessageEntity me: messages){
			assertEquals(me.getLocale(), "US");
			assertEquals(me.getGroupName(), "Address");
		}
		messages = messageManagementDAO.getMessages("CA", "Address");
		assertEquals(2, messages.size());
		for(MessageEntity me: messages){
			assertEquals(me.getLocale(), "CA");
			assertEquals(me.getGroupName(), "Address");
		}
		messages = messageManagementDAO.getMessages("US", "Name");
		assertEquals(1, messages.size());
		for(MessageEntity me: messages){
			assertEquals(me.getLocale(), "US");
			assertEquals(me.getGroupName(), "Name");
		}
	}
	
	@Test
	public void testGetMessagesByGroup(){
		List<String> groupKeys = new ArrayList<String>();
		groupKeys.add("Address");
		groupKeys.add("Name");
		List<MessageEntity> messages = messageManagementDAO.getMessagesByGroups("US", groupKeys);
		assertEquals(3, messages.size());
		for(MessageEntity me: messages){
			assertEquals(me.getLocale(), "US");
			assertTrue(me.getGroupName().equals("Address") || me.getGroupName().equals("Name"));
		}
		
		messages = messageManagementDAO.getMessagesByGroups("CA", groupKeys);
		assertEquals(3, messages.size());
		for(MessageEntity me: messages){
			assertEquals(me.getLocale(), "CA");
			assertTrue(me.getGroupName().equals("Address") || me.getGroupName().equals("Name"));
		}
	}
	
	@Test
	public void testUpdateMessage(){
		MessageEntity me = new MessageEntity();
		me.setGroupName("Course");
		me.setLocale("US");
		me.setId("Grading");
		me.setValue("Grading Scale");
		
		messageManagementDAO.updateMessage("US", "Name", "Last", me);
		MessageEntity result = messageManagementDAO.getMessage("US", "Course", "Grading");
		assertEquals(result.getLocale(), me.getLocale());
		assertEquals(result.getId(), me.getId());
		assertEquals(result.getValue(), me.getValue());
		assertEquals(result.getGroupName(), me.getGroupName());
		result = messageManagementDAO.getMessage("US", "Name", "Last");
		assertTrue(result == null);
	}
}
