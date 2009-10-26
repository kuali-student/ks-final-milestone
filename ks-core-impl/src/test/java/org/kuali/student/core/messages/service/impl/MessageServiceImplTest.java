/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.messages.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.messages.dto.MessageGroupKeyList;
import org.kuali.student.core.messages.service.MessageService;


@Daos( { @Dao(value = "org.kuali.student.core.messages.dao.impl.MessageManagementDAOImpl", testDataFile = "classpath:messages-test-beans.xml") })
@PersistenceFileLocation("classpath:META-INF/messages-persistence.xml")
public class MessageServiceImplTest extends AbstractServiceTest{    
    
    @Client(value = "org.kuali.student.core.messages.service.impl.MessageServiceImpl")
    private MessageService messageService;
    
    
    @Test
    public void testGetLocales()
    {
    	List<String> locales = messageService.getLocales().getLocales();
    	
    	assertEquals(2, locales.size());
    }
    
	@Test
    public void testGetMessageGroup(){
    	List<String> groups = messageService.getMessageGroups().getMessageGroupKeys();

    	assertEquals(2, groups.size());
    }
	
	@Test
	public void testGetMessage(){
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
		List<Message> messages = messageService.getMessages("US", "Address").getMessages();
		assertEquals(2, messages.size());
		for(Message m: messages){
			assertEquals(m.getLocale(), "US");
			assertEquals(m.getGroupName(), "Address");
            assertTrue(m.getId().equals("State") ? ("State:".equals(m.getValue())):("Enter the US city where you live:".equals(m.getValue())));
		}
		messages = messageService.getMessages("CA", "Address").getMessages();
		assertEquals(2, messages.size());
		for(Message m: messages){
			assertEquals(m.getLocale(), "CA");
			assertEquals(m.getGroupName(), "Address");
            assertTrue(m.getId().equals("State") ? ("Province:".equals(m.getValue())):("Enter the Canadian city where you live:".equals(m.getValue())));
		}
		messages = messageService.getMessages("US", "Name").getMessages();
		assertEquals(1, messages.size());
		for(Message m: messages){
			assertEquals(m.getLocale(), "US");
			assertEquals(m.getGroupName(), "Name");
            assertEquals(m.getValue(), "Enter your last name:");
		}
	}
	
	@Test
	public void testGetMessagesByGroup(){
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
