package org.kuali.student.r2.core.classI.atp.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.enrollment.classI.lpr.service.utilities.DataLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAtpServiceImpl {
    public AtpService atpService;
    public ApplicationContext appContext;
    public static String principalId = "123";
    public ContextInfo callContext = ContextInfo.newInstance();

    private AtpDataGenerator dataLoader;
    
    @Before
    public void setUp() {
        principalId = "123";
        appContext = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml", "atp-test-context.xml"});
        atpService = (AtpService) appContext.getBean("atpService");
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
        dataLoader = (AtpDataGenerator) appContext.getBean("dataLoader");
        dataLoader.load();
    }
    
    @Test 
    public void testGetAtp()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException {
       AtpInfo atpInfo = atpService.getAtp("testId", callContext);
       assertNotNull(atpInfo);
       assertEquals("testId", atpInfo.getKey());
       assertEquals("testId", atpInfo.getName());
       assertEquals("testId plain descr", atpInfo.getDescr().getPlain());
       assertEquals("testId formatted descr", atpInfo.getDescr().getFormatted());
    }
    
    @Ignore
    @Test
    public void testUpdateAtp()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException {
        AtpInfo atpInfo = atpService.getAtp("testId", callContext);
        assertNotNull(atpInfo);
        assertEquals("testId", atpInfo.getKey());
        
        String atpNameOrig = atpInfo.getName();
        AtpInfo modified = AtpInfo.newInstance();
        modified.setName(atpNameOrig + "updated");
        AtpInfo updated = atpService.updateAtp(atpInfo.getKey(), modified, callContext);
        assertNotNull(updated);
        assertEquals(atpNameOrig + "updated", updated.getName());
    }
    
    @Test
    public void testCreateAtp()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException{
        AtpInfo atpInfo = AtpInfo.newInstance();
        atpInfo.setKey("newId");
        atpInfo.setName("newId");
        atpInfo.setStartDate(Calendar.getInstance().getTime());
        atpInfo.setEndDate(Calendar.getInstance().getTime());
        try {
            AtpInfo created = atpService.createAtp("newId", atpInfo, callContext);
            assertNotNull(created);
            assertEquals("newId", created.getKey());
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        }
    }
}
