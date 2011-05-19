package org.kuali.student.enrollment.class2.acal.service.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAcademicCalendarServiceImpl extends AbstractServiceTest{
    @Client(value = "org.kuali.student.enrollment.class2.acal.service.impl.AcademicCalendarServiceImpl")

    public AcademicCalendarService acalService;
    public ApplicationContext appContext;
    public static String principalId = "123";
    public ContextInfo callContext = ContextInfo.newInstance();
  
    @Before
    public void setUp() {
        principalId = "123";
        appContext = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
        
        acalService = (AcademicCalendarService) appContext.getBean("acalService"); 
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
    }

    @Test 
    public void testGetAcademicCalendar()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException {

            AcademicCalendarInfo acal = new AcademicCalendarInfo();
            acal.setKey("testAcalId");
            acal.setName("testAcal");
            acal.setCredentialProgramTypeKey("credentialProgramTypeKey");
            acal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
            acal.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
            try{
                AcademicCalendarInfo created = acalService.createAcademicCalendar("testAcalId", acal, callContext);
                assertNotNull(created);
                assertEquals("testAcalId", created.getKey());
                
                AcademicCalendarInfo existed = acalService.getAcademicCalendar("testAcalId", callContext);

                assertNotNull(existed);
                assertEquals("testAcalId", existed.getKey());
                assertEquals("testAcal", existed.getName());
            } catch (Exception ex) {
                fail("exception from service call :" + ex.getMessage());
            }
    }
  
    @Test
    public void testCreateAcademicCalendar() throws AlreadyExistsException,
    DataValidationErrorException, InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        AcademicCalendarInfo acal = new AcademicCalendarInfo();
        acal.setKey("testAcalId1");
        acal.setName("testAcal1");
        acal.setCredentialProgramTypeKey("credentialProgramTypeKey");
        List<String> ccKeys = new ArrayList<String>();
        //Assume campusCalendarKeys picking up from dropdown and valid(already in db)
        ccKeys.add("testAtpId2");
        acal.setCampusCalendarKeys(ccKeys);
        acal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        acal.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        try{
            AcademicCalendarInfo created = acalService.createAcademicCalendar("testAcalId1", acal, callContext);
            assertNotNull(created);
            assertEquals("testAcalId1", created.getKey());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }
    
    @Ignore
    @Test 
    //TODO: fix locking issue
    public void testUpdateAcademicCalendar()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException {

            AcademicCalendarInfo acal =  new AcademicCalendarInfo();
            acal.setKey("testNewAcalId");
            acal.setName("testNewAcal");
            acal.setCredentialProgramTypeKey("credentialProgramTypeKey");
            acal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
            acal.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
            try{
                AcademicCalendarInfo created = acalService.createAcademicCalendar("testNewAcalId", acal, callContext);
                assertNotNull(created);
                assertEquals("testNewAcalId", created.getKey());
                
                
                AcademicCalendarInfo existed = acalService.getAcademicCalendar("testNewAcalId", callContext);

                assertNotNull(existed);
                
                existed.setName("testUpdatedAcal");
                AcademicCalendarInfo updated = acalService.updateAcademicCalendar("testNewAcalId", existed, callContext);
                assertEquals("testUpdatedAcal", updated.getName());
            } catch (Exception ex) {
                fail("exception from service call :" + ex.getMessage());
            }
    }
    
    
    @Test 
    public void testDeleteAcademicCalendar()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException {

            AcademicCalendarInfo acal =  new AcademicCalendarInfo();
            acal.setKey("testDeletedAcalId");
            acal.setName("testDeletedAcal");
            acal.setCredentialProgramTypeKey("credentialProgramTypeKey");
            acal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
            acal.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
            try{
                AcademicCalendarInfo created = acalService.createAcademicCalendar("testDeletedAcalId", acal, callContext);
                assertNotNull(created);
                assertEquals("testDeletedAcalId", created.getKey());

                StatusInfo ret = acalService.deleteAcademicCalendar("testDeletedAcalId", callContext);
                assertTrue(ret.isSuccess() == Boolean.TRUE);

                AcademicCalendarInfo existed = acalService.getAcademicCalendar("testDeletedAcalId", callContext);
                assertNull(existed);
            } catch (Exception ex) {
                fail("exception from service call :" + ex.getMessage());
            }
    }
    
    @Test 
    public void testCreateAndGetTerm() throws DoesNotExistException,
    InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        TermInfo term = new TermInfo();
        term.setKey("testTermId2");
        term.setName("testTerm2");
        term.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        term.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        try{
            TermInfo created = acalService.createTerm("testTermId2", term, callContext);
            assertNotNull(created);
            assertEquals("testTermId2", created.getKey());
            
            TermInfo existed = acalService.getTerm("testTermId2", callContext);

            assertNotNull(existed);
            assertEquals("testTermId2", existed.getKey());
            assertEquals("testTerm2", existed.getName());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }       
    }
}
