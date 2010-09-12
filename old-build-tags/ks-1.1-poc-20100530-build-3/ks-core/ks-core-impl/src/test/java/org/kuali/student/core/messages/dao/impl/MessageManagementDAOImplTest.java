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

package org.kuali.student.core.messages.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.messages.entity.MessageEntity;

@PersistenceFileLocation("classpath:META-INF/messages-persistence.xml")
public class MessageManagementDAOImplTest extends AbstractTransactionalDaoTest{
    @Dao(value = "org.kuali.student.core.messages.dao.impl.MessageManagementDAOImpl", testDataFile = "classpath:messages-test-beans.xml")
    public MessageManagementDAOImpl messageManagementDAO;
    	    
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
