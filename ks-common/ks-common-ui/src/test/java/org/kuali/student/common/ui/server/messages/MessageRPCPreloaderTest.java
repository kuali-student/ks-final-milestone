/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.common.ui.server.messages;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
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

/**
 *
 * @author nwright
 */
@Ignore
public class MessageRPCPreloaderTest {
    
    public MessageRPCPreloaderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getMessagesByGroupsEncodingString method, of class MessageRPCPreloader.
     */
    @Test
    public void testGetMessagesByGroupsEncodingString() {
        System.out.println("getMessagesByGroupsEncodingString");
        String locale = "";
        String[] keys = {"group1"};
        MessageRPCPreloader instance = new MessageRPCPreloader();
        instance.setMessageService(new MessageServiceTestImpl ());
        String expResult = "//OK[8,7,0,0,0,6,5,4,3,1,2,1,[\"org.kuali.student.common.ui.client.service.MessageList/1931986385\",\"java.util.ArrayList/4159755760\",\"org.kuali.student.r2.common.messages.dto.MessageInfo/835426725\",\"testgroup\",\"org.kuali.student.r2.common.dto.LocaleInfo/2642839499\",\"EN\",\"testkey\",\"testvalue\"],0,7]";

        String result = instance.getMessagesByGroupsEncodingString(locale, keys);
        assertEquals(expResult, result);
    }
    
    private static class MessageServiceTestImpl implements MessageService {

        @Override
        public List<LocaleInfo> getLocales(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public List<String> getMessageGroupKeys(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public MessageInfo getMessage(LocaleInfo localeInfo, String messageGroupKey, String messageKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public List<MessageInfo> getMessagesByGroup(LocaleInfo localeInfo, String messageGroupKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public List<MessageInfo> getMessagesByGroups(LocaleInfo localeInfo, List<String> messageGroupKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
               List<MessageInfo> list = new ArrayList<MessageInfo> ();
            MessageInfo info = new MessageInfo ();
            info.setGroupName("testgroup");
            info.setMessageKey("testkey");
            info.setLocale (new LocaleInfo ());
            info.getLocale ().setLocaleLanguage("EN");
            info.setValue("testvalue");
            list.add (info);
            return list;
        }

        @Override
        public MessageInfo updateMessage(LocaleInfo localeInfo, String messageGroupKey, String messageKey, MessageInfo messageInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, DataValidationErrorException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public StatusInfo deleteMessage(LocaleInfo localeInfo, String messageGroupKey, String messageKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public StatusInfo createMessage(LocaleInfo localeInfo, String messageGroupKey, String messageKey, MessageInfo messageInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, DataValidationErrorException, PermissionDeniedException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public List<ValidationResultInfo> validateProposal(String validationTypeKey, MessageInfo messageInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }
}
