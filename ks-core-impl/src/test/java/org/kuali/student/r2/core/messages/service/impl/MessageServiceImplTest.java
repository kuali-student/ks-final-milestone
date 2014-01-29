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
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.util.ContextInfoTestUtility;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.messages.dto.MessageInfo;
import org.kuali.student.r2.common.messages.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:message-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class MessageServiceImplTest extends AbstractServiceTest{
    
    @Autowired
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
            assertEquals(message.getMessageKey(), "State");
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
            assertEquals(message.getMessageKey(), "State");
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
            messages = messageService.getMessagesByGroup(localeInfo, "Address", contextInfo);
            assertEquals(2, messages.size());
            for(MessageInfo m: messages){
                assertEquals(m.getLocale().getLocaleLanguage(), "US");
                assertEquals(m.getGroupName(), "Address");
                assertTrue(m.getMessageKey().equals("State") ? ("State:".equals(m.getValue())):("Enter the US city where you live:".equals(m.getValue())));
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
            messages = messageService.getMessagesByGroup(localeInfo1, "Address", contextInfo);
            assertEquals(2, messages.size());
            for(MessageInfo m: messages){
                assertEquals(m.getLocale().getLocaleLanguage(), "CA");
                assertEquals(m.getGroupName(), "Address");
                assertTrue(m.getMessageKey().equals("State") ? ("Province:".equals(m.getValue())):("Enter the Canadian city where you live:".equals(m.getValue())));
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
            messages = messageService.getMessagesByGroup(localeInfo, "Name", contextInfo);
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


    // run this at the end because delete has not been implemented
	@Test
    @Transactional
	public void testCRUDMessage() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
	    ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();
        MessageInfo m = new MessageInfo();
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage("US");  // part of the where kry
        m.setGroupName("Test Group");  // part of the where key
        m.setMessageKey("Test Key");
        m.setValue("Test Message Text");
        m.setLocale(localeInfo);
        messageService.createMessage(localeInfo, m.getGroupName(), m.getMessageKey(), m, contextInfo);
        MessageInfo dummyMsg = messageService.getMessage(localeInfo, m.getGroupName(), m.getMessageKey(), contextInfo);
        assertEquals(m.getLocale().getLocaleLanguage(), dummyMsg.getLocale().getLocaleLanguage());
        assertEquals(m.getMessageKey(), dummyMsg.getMessageKey());
        assertEquals(m.getValue(), dummyMsg.getValue());
        assertEquals(m.getGroupName(), dummyMsg.getGroupName());

        dummyMsg.setMessageKey("Test Key - Updated");
        dummyMsg.setValue("Modified Text");

        try {
            messageService.updateMessage(localeInfo, m.getGroupName(), m.getMessageKey(), dummyMsg, contextInfo);
            MessageInfo result = messageService.getMessage(localeInfo, dummyMsg.getGroupName(), dummyMsg.getMessageKey(), contextInfo );
            assertEquals(result.getLocale().getLocaleLanguage(), dummyMsg.getLocale().getLocaleLanguage());
            assertEquals(result.getMessageKey(), dummyMsg.getMessageKey());
            assertEquals(result.getValue(), dummyMsg.getValue());
            assertEquals(result.getGroupName(), dummyMsg.getGroupName());
            result = messageService.getMessage(localeInfo , m.getGroupName(), m.getMessageKey(), contextInfo);
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

        //TODO: add delete and remove @Transactional
    }
}
