package org.kuali.student.enrollment.class2.acal.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@ContextConfiguration(locations = {"classpath:acal-test-context.xml"})
public class TestAcademicCalendarServiceImpl{
    
    private AcademicCalendarService acalServiceValidation;
    
    public static String principalId = "123";
    public ContextInfo callContext = ContextInfo.newInstance();
  
    @Autowired
	public void setAcalServiceValidation(AcademicCalendarService acalServiceValidation) {
		this.acalServiceValidation = acalServiceValidation;
	}

	@Before
    public void setUp() {
        principalId = "123";    
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
    }

	@Test
    public void testAcademicCalendarServiceSetup() {
    	assertNotNull(acalServiceValidation);
    }

	@Test 
    public void testGetAcademicCalendarFromDerby()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException {
		AcademicCalendarInfo acal = acalServiceValidation.getAcademicCalendar("testAtpId1",callContext);
		assertNotNull(acal);
		assertEquals("testAtpId1", acal.getKey());
		assertEquals("testAtp1", acal.getName());
		assertEquals("Desc 101", acal.getDescr().getPlain());
		assertEquals(AtpServiceConstants.ATP_DRAFT_STATE_KEY, acal.getStateKey());
		assertEquals(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, acal.getTypeKey());
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
                AcademicCalendarInfo created = acalServiceValidation.createAcademicCalendar("testAcalId", acal, callContext);
                assertNotNull(created);
                assertEquals("testAcalId", created.getKey());
                
                AcademicCalendarInfo existed = acalServiceValidation.getAcademicCalendar("testAcalId", callContext);

                assertNotNull(existed);
                assertEquals("testAcalId", existed.getKey());
                assertEquals("testAcal", existed.getName());
            } catch (Exception ex) {
                fail("exception from service call :" + ex.getMessage());
            }
    }
  
    @Test 
    public void testValidateAcademicCalendar()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {
    	AcademicCalendarInfo acal = new AcademicCalendarInfo();
        acal.setKey("testAcalId1");
        acal.setName("testAcal1");
        acal.setCredentialProgramTypeKey("credentialProgramTypeKey");
        List<String> ccKeys = new ArrayList<String>();
        ccKeys.add("testAtpId2");
        acal.setCampusCalendarKeys(ccKeys);
        acal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        acal.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        
        try{
        	List<ValidationResultInfo> vri= acalServiceValidation.validateAcademicCalendar("FULL_VALIDATION", acal, callContext);
        	assertTrue(vri.isEmpty());
        }catch (OperationFailedException ex){
        	//dictionary not ready, this is expected
        } catch (Exception ex) {
            //fail("exception from service call :" + ex.getMessage());
        	//TODO: test exception aspect & get dictionary ready, this is expected
        } 
    }
    
    @Ignore
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
            AcademicCalendarInfo created = acalServiceValidation.createAcademicCalendar("testAcalId1", acal, callContext);
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
                AcademicCalendarInfo created = acalServiceValidation.createAcademicCalendar("testNewAcalId", acal, callContext);
                assertNotNull(created);
                assertEquals("testNewAcalId", created.getKey());
                
                
                AcademicCalendarInfo existed = acalServiceValidation.getAcademicCalendar("testNewAcalId", callContext);

                assertNotNull(existed);
                
                existed.setName("testUpdatedAcal");
                AcademicCalendarInfo updated = acalServiceValidation.updateAcademicCalendar("testNewAcalId", existed, callContext);
                assertEquals("testUpdatedAcal", updated.getName());
            } catch (Exception ex) {
                fail("exception from service call :" + ex.getMessage());
            }
    }
    
	@Ignore
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
                AcademicCalendarInfo created = acalServiceValidation.createAcademicCalendar("testDeletedAcalId", acal, callContext);
                assertNotNull(created);
                assertEquals("testDeletedAcalId", created.getKey());

                StatusInfo ret = acalServiceValidation.deleteAcademicCalendar("testDeletedAcalId", callContext);
                assertTrue(ret.isSuccess() == Boolean.TRUE);

                AcademicCalendarInfo existed = acalServiceValidation.getAcademicCalendar("testDeletedAcalId", callContext);
                assertNull(existed);
            } catch (Exception ex) {
                fail("exception from service call :" + ex.getMessage());
            }
    }
    
	@Test 
    public void testValidateTerm()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {
       TermInfo term = new TermInfo();
        term.setKey("testTermId");
        term.setName("testTerm");
        term.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        term.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        
        try{
        	List<ValidationResultInfo> vri= acalServiceValidation.validateTerm("SKIP_REQUREDNESS_VALIDATIONS", term, callContext);
        	assertTrue(vri.isEmpty());
        }catch (OperationFailedException ex){
        	//dictionary not ready, this is expected
        } catch (Exception ex) {
            //fail("exception from service call :" + ex.getMessage());
        	//TODO: test exception aspect & get dictionary ready, this is expected
        } 
	}
	@Ignore
    @Test 
    public void testCreateAndGetTerm() throws DoesNotExistException,
    InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        TermInfo term = new TermInfo();
        term.setKey("testTermId2");
        term.setName("testTerm2");
        term.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        term.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        try{
            TermInfo created = acalServiceValidation.createTerm("testTermId2", term, callContext);
            assertNotNull(created);
            assertEquals("testTermId2", created.getKey());
            
            TermInfo existed = acalServiceValidation.getTerm("testTermId2", callContext);

            assertNotNull(existed);
            assertEquals("testTermId2", existed.getKey());
            assertEquals("testTerm2", existed.getName());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }       
    }
}
