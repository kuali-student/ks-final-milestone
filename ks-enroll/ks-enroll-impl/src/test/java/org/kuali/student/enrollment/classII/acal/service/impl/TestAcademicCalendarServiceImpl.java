package org.kuali.student.enrollment.classII.acal.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.r2.common.dto.ContextInfo;
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

//@Ignore
public class TestAcademicCalendarServiceImpl extends AbstractServiceTest{
    @Client(value = "org.kuali.student.enrollment.classII.acal.service.impl.AcademicCalendarServiceImpl")

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

            AcademicCalendarInfo acal = AcademicCalendarInfo.newInstance();
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
                assertEquals("testAcalId", acal.getKey());
                assertEquals("testAcal", acal.getName());
            } catch (Exception ex) {
                fail("exception from service call :" + ex.getMessage());
            }
    }
  
    @Test
    public void testCreateAcademicCalendar() throws AlreadyExistsException,
    DataValidationErrorException, InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        AcademicCalendarInfo acal = AcademicCalendarInfo.newInstance();
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
}
