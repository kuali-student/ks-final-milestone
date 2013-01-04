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

package org.kuali.student.r2.core.messages.service.impl;

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
import org.kuali.student.common.test.util.ContextInfoTestUtility;
import org.kuali.student.r1.common.messages.dto.MessageGroupKeyList;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.messages.dto.MessageInfo;
import org.kuali.student.r2.common.messages.service.MessageService;


@Daos( { @Dao(value = "org.kuali.student.r1.core.messages.dao.impl.MessageManagementDAOImpl", testDataFile = "classpath:messages-test-beans.xml") })
@PersistenceFileLocation("classpath:META-INF/messages-persistence.xml")
public class MessageServiceImplTest extends AbstractServiceTest{    
    
    @Client(value = "org.kuali.student.core.messages.service.impl.MessageServiceImpl")
    private MessageService messageService;
    
    
    @Test
    public void testGetLocales()
    {
        ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
    	List<LocaleInfo> locales;
        try {
            locales = messageService.getLocales(contextInfo);
            assertEquals(2, locales.size());
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }    	
    }
    
	@Test
    public void testGetMessageGroup(){
	    ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
    	List<String> groups;
        try {
            groups = messageService.getMessageGroupKeys(contextInfo);
            assertEquals(2, groups.size());
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }    	
    }
	
	@Test
	public void testGetMessage() {
	    ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
	    LocaleInfo localeInfo = new LocaleInfo();
	    localeInfo.setLocaleRegion("US");
	    localeInfo.setLocaleLanguage("US");
		MessageInfo message = new MessageInfo();
        try {
        	//ES probleem
            message = messageService.getMessage(localeInfo, "Address", "State", contextInfo);
            //
            assertEquals(message.getLocale().getLocaleLanguage(), "US");
            assertEquals(message.getGroupName(), "Address");
            assertEquals(message.getKey(), "State");
            assertEquals(message.getValue(), "State:");
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }
		
		LocaleInfo localeInfo1 = new LocaleInfo();
        localeInfo1.setLocaleRegion("CA");
        localeInfo1.setLocaleLanguage("CA");
		try {
            message = messageService.getMessage(localeInfo1, "Address", "State", contextInfo);
            assertEquals(message.getLocale().getLocaleRegion(), "CA");
            assertEquals(message.getLocale().getLocaleLanguage(), "CA");
            assertEquals(message.getGroupName(), "Address");
            assertEquals(message.getKey(), "State");
            assertEquals(message.getValue(), "Province:");
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }		
	}
	
	@Test
	public void testGetMessages(){
	    ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
	    LocaleInfo localeInfo = new LocaleInfo();
	    localeInfo.setLocaleLanguage("US");
		List<MessageInfo> messages;
        try {
            messages = messageService.getMessages(localeInfo, "Address", contextInfo);
            assertEquals(2, messages.size());
            for(MessageInfo m: messages){
                assertEquals(m.getLocale().getLocaleLanguage(), "US");
                assertEquals(m.getGroupName(), "Address");
                assertTrue(m.getKey().equals("State") ? ("State:".equals(m.getValue())):("Enter the US city where you live:".equals(m.getValue())));
            }
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }
		
		LocaleInfo localeInfo1 = new LocaleInfo();
		localeInfo1.setLocaleLanguage("CA");
		try {
            messages = messageService.getMessages(localeInfo1, "Address", contextInfo);
            assertEquals(2, messages.size());
            for(MessageInfo m: messages){
                assertEquals(m.getLocale().getLocaleLanguage(), "CA");
                assertEquals(m.getGroupName(), "Address");
                assertTrue(m.getKey().equals("State") ? ("Province:".equals(m.getValue())):("Enter the Canadian city where you live:".equals(m.getValue())));
            }
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }
		
		try {
            messages = messageService.getMessages(localeInfo, "Name", contextInfo);
            assertEquals(1, messages.size());
            for(MessageInfo m: messages){
                assertEquals(m.getLocale().getLocaleLanguage(), "US");
                assertEquals(m.getGroupName(), "Name");
                assertEquals(m.getValue(), "Enter your last name:");
            }
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }		
	}
	
	@Test
	public void testGetMessagesByGroup(){
	    ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
		List<String> groupKeys = new ArrayList<String>();
		groupKeys.add("Address");
		groupKeys.add("Name");
		LocaleInfo localeInfo = new LocaleInfo();
		localeInfo.setLocaleLanguage("US");
		List<MessageInfo> messages;
        try {
            messages = messageService.getMessagesByGroups(localeInfo, groupKeys, contextInfo);
            assertEquals(3, messages.size());
            for(MessageInfo m: messages){
                assertEquals(m.getLocale().getLocaleLanguage(), "US");
                assertTrue(m.getGroupName().equals("Address") || m.getGroupName().equals("Name"));
            }
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }
				
		LocaleInfo localeInfo1 = new LocaleInfo();
		localeInfo1.setLocaleLanguage("CA");
		try {
            messages = messageService.getMessagesByGroups(localeInfo1, groupKeys, contextInfo);
            assertEquals(3, messages.size());
            for(MessageInfo m: messages){
                assertEquals(m.getLocale().getLocaleLanguage(), "CA");
                assertTrue(m.getGroupName().equals("Address") || m.getGroupName().equals("Name"));
            }
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }		
	}
	
	@Test
	public void testUpdateMessage() {
	    ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
		MessageInfo m = new MessageInfo();
		LocaleInfo localeInfo = new LocaleInfo();
		localeInfo.setLocaleLanguage("US");  // part of the where kry
		m.setGroupName("Name");  // part of the where key
		m.setKey("Grading");
		m.setValue("Grading Scale");
		m.setLocale(localeInfo);
		try {
            messageService.updateMessage(localeInfo, "Last", m, contextInfo);
            MessageInfo result = messageService.getMessage(localeInfo, "Name", "Grading", contextInfo );
            assertEquals(result.getLocale().getLocaleLanguage(), m.getLocale().getLocaleLanguage());
            assertEquals(result.getKey(), m.getKey());
            assertEquals(result.getValue(), m.getValue());
            assertEquals(result.getGroupName(), m.getGroupName());
            result = messageService.getMessage(localeInfo , "Name", "Last", contextInfo);
            assertTrue(result == null);
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
       }
            catch (ReadOnlyException e) {
            e.printStackTrace();
        } catch (VersionMismatchException e) {
            e.printStackTrace();
        }		
	}
}
