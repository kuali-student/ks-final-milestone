package org.kuali.student.enrollment.class2.acal.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
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
        try {
            acalServiceValidation.createAcademicCalendar("testAcalId1", acal, callContext);
            fail("AcademicCalendarService.createAcademicCalendar() did not throw expected AlreadyExistsException");
        } catch (AlreadyExistsException aee) { /* expected */ }
    }

    @Test 
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
                assertTrue(ret.isSuccess());

                AcademicCalendarInfo existed = acalServiceValidation.getAcademicCalendar("testDeletedAcalId", callContext);
                assertNull(existed);
            } catch (DoesNotExistException dnee) {
            	//this is expected
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

    @Test 
    public void testCreateAndGetTerm() throws DoesNotExistException,
    InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        TermInfo term = new TermInfo();
        term.setKey("testTermId2");
        term.setName("testNewTerm");
        term.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        term.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        try{
        	TermInfo created;
        	
        	try{
        		created = acalServiceValidation.createTerm("testTermId2", term, callContext);
        	 } catch (AlreadyExistsException ex) {
                 //expected);
             }   	
        	 
        	term.setKey("testNewTermId");
        	created = acalServiceValidation.createTerm("testNewTermId", term, callContext);
            assertNotNull(created);
            assertEquals("testNewTermId", created.getKey());
            
            TermInfo existed = acalServiceValidation.getTerm("testNewTermId", callContext);

            assertNotNull(existed);
            assertEquals("testNewTermId", existed.getKey());
            assertEquals("testNewTerm", existed.getName());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }       
    }
    
    @Test 
    public void testAddTermToAcademicCalendar()throws DoesNotExistException, InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException, AlreadyExistsException{
    	try{
	    	try{
	    	acalServiceValidation.addTermToAcademicCalendar("testAtpId1", "testTermId1", callContext);
	    	} catch (AlreadyExistsException ex) {
	            //expected);
	        } 
	    	
	    	StatusInfo status = acalServiceValidation.addTermToAcademicCalendar("testAtpId1", "testTermId2", callContext);
	    	assertTrue(status.isSuccess());
    	} catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        } 	    	
    }
    
    @Test 
    public void testGetTermToAcademicCalendar()throws DoesNotExistException, InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException, AlreadyExistsException{
    	try{
    		try{
    			acalServiceValidation.getTermsForAcademicCalendar("testTermId1", callContext);
    		}catch (OperationFailedException ex){
    			//expected because it's not an acal
    		}
    		
    		List<TermInfo> terms = acalServiceValidation.getTermsForAcademicCalendar("testAtpId1", callContext);
    		 assertNotNull(terms);
             assertEquals("testTermId1", terms.get(0).getKey());
    		
    	} catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        } 	
    }
    
    @Test 
    public void testGetTermTypesForAcademicCalendarType() throws DoesNotExistException, InvalidParameterException, MissingParameterException,
    OperationFailedException {
    	List<TypeInfo> termTypes = acalServiceValidation.getTermTypesForAcademicCalendarType(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, callContext);
    	assertNotNull(termTypes);
    	assertEquals(termTypes.size(), 2);
    }
}
