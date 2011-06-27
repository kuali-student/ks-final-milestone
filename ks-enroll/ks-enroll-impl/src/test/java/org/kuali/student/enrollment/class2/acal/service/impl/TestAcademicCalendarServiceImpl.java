package org.kuali.student.enrollment.class2.acal.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
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
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
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
                assertTrue(ret.getIsSuccess());

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
	    	assertTrue(status.getIsSuccess());
    	} catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
    	}
    	
    	List<TermInfo> terms = acalServiceValidation.getTermsForAcademicCalendar("testAtpId1", callContext);
    	
    	assertEquals(2, terms.size());
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
    		
    		// make sure an expected term is in the list of returned terms
    		boolean found = false;
    		for(TermInfo term : terms) {
    		    if(term.getKey().equals("testTermId1")) {
    		        found = true;
    		        break;
    		    }
    		}
    		 
    		assertTrue(found);
    		
    	} catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        } 	
    }
    
    @Test
    public void testGetKeyDatesForTerm() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<KeyDateInfo> results = acalServiceValidation.getKeyDatesForTerm("termRelationTestingTerm1", callContext);
        
        assertNotNull(results);
        assertEquals(1, results.size());
        
        List<String> expectedIds = new ArrayList<String>();
        expectedIds.add("testKeyDate1");
        
        // check that all the expected ids came back
        for(KeyDateInfo info : results) {
            expectedIds.remove(info.getKey());
        }
        
        assertTrue(expectedIds.isEmpty());
        
        List<KeyDateInfo> fakeKeyDates = null;
        
        try {
            fakeKeyDates = acalServiceValidation.getKeyDatesForTerm("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeKeyDates);
        }
    }
    
    @Test
    public void testGetContainingTerms() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TermInfo> results = acalServiceValidation.getContainingTerms("termRelationTestingTerm2", callContext);
        
        assertNotNull(results);
        assertEquals(1, results.size());
        
        List<String> expectedIds = new ArrayList<String>();
        expectedIds.add("termRelationTestingTerm1");
        
        // check that all the expected ids came back
        for(TermInfo info : results) {
            expectedIds.remove(info.getKey());
        }
        
        assertTrue(expectedIds.isEmpty());
        
        List<TermInfo> fakeResults = null;
        
        try {
            fakeResults = acalServiceValidation.getContainingTerms("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeResults);
        }
    }
    
    @Test
    public void testGetTermKeysByType() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        String expectedTermType = "kuali.atp.type.HalfFall1";
        
        List<String> termKeys = acalServiceValidation.getTermKeysByType(expectedTermType, callContext);
        
        assertTrue(termKeys.contains("termRelationTestingTerm4"));
        
        String expectedEmptyTermType = "kuali.atp.type.SessionG2";
        
        termKeys = acalServiceValidation.getTermKeysByType(expectedEmptyTermType, callContext);
        
        assertTrue(termKeys == null || termKeys.isEmpty());
        
        String fakeTermType = "fakeTypeKey";
        
        List<String> shouldBeNull = null;
        try {
            shouldBeNull = acalServiceValidation.getTermKeysByType(fakeTermType, callContext);
            fail("Did not get a InvalidParameterException when expected");
        }
        catch(InvalidParameterException e) {
            assertNull(shouldBeNull);
        }
    }
    
    @Test
    public void testGetTermsByKeyList() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> termKeys = new ArrayList<String>();
        termKeys.addAll(Arrays.asList("termRelationTestingTerm1", "termRelationTestingTerm2"));
        
        List<TermInfo> terms = acalServiceValidation.getTermsByKeyList(termKeys, callContext);
        
        assertNotNull(terms);
        assertEquals(termKeys.size(), terms.size());
        
        // check that all the expected ids came back
        for(TermInfo info : terms) {
            termKeys.remove(info.getKey());
        }
        
        assertTrue(termKeys.isEmpty());
        
        // now make sure an exception is thrown for any not found keys
        
        List<String> fakeKeys = Arrays.asList("termRelationTestingTerm1", "fakeKey1", "fakeKey2");
        
        List<TermInfo> shouldBeNull = null;
        try {
            shouldBeNull = acalServiceValidation.getTermsByKeyList(fakeKeys, callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
    }
    
    @Test
    public void testGetTermsForAcademicCalendar() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TermInfo> results = acalServiceValidation.getTermsForAcademicCalendar("termRelationTestingAcal1", callContext);
        
        assertNotNull(results);
        assertEquals(1, results.size());
        
        List<String> expectedIds = new ArrayList<String>();
        expectedIds.add("termRelationTestingTerm1");
        
        // check that all the expected ids came back
        for(TermInfo info : results) {
            expectedIds.remove(info.getKey());
        }
        
        assertTrue(expectedIds.isEmpty());
        
        List<TermInfo> fakeResults = null;
        
        try {
            fakeResults = acalServiceValidation.getTermsForAcademicCalendar("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeResults);
        }
    }
    
    @Test
    public void testGetIncludedTermsInTerm() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TermInfo> results = acalServiceValidation.getIncludedTermsInTerm("termRelationTestingTerm1", callContext);
        
        assertNotNull(results);
        assertEquals(1, results.size());
        
        List<String> expectedIds = new ArrayList<String>();
        expectedIds.add("termRelationTestingTerm2");
        
        // check that all the expected ids came back
        for(TermInfo info : results) {
            expectedIds.remove(info.getKey());
        }
        
        assertTrue(expectedIds.isEmpty());
        
        List<TermInfo> fakeResults = null;
        
        try {
            fakeResults = acalServiceValidation.getIncludedTermsInTerm("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeResults);
        }
    }
    
    @Test
    public void testGetTermState() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        StateInfo result = acalServiceValidation.getTermState(AtpServiceConstants.ATP_DRAFT_STATE_KEY, callContext);
        
        assertNotNull(result);
        assertEquals(result.getName(), "Draft");
        
        StateInfo fakeState = null;
        try {
            fakeState = acalServiceValidation.getTermState("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeState);
        }
    }
    
    @Test
    public void testGetTermStates() throws InvalidParameterException, MissingParameterException, OperationFailedException, DoesNotExistException {
        List<StateInfo> result = acalServiceValidation.getTermStates(callContext);
        
        assertNotNull(result);
        assertTrue(!result.isEmpty());
        
        List<String> expectedIds = new ArrayList<String>();
        expectedIds.addAll(Arrays.asList(AtpServiceConstants.ATP_DRAFT_STATE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY));
        
        // ensure we have all the expected ids in our list
        assertEquals(expectedIds.size(), result.size());
        
        for(StateInfo state : result) {
            expectedIds.remove(state.getKey());
        }
        
        assertTrue(expectedIds.isEmpty());
    }
    
    @Test
    public void testGetTermType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        TypeInfo result = acalServiceValidation.getTermType(AtpServiceConstants.ATP_HALF_FALL_1_TYPE_KEY, callContext);
        
        assertNotNull(result);
        assertEquals(result.getName(), "Fall Half-Semester 1");
        
        TypeInfo fakeType = null;
        try {
            fakeType = acalServiceValidation.getTermType("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeType);
        }
    }
    
    @Test
    public void testGetTermTypes() throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<TypeInfo> result = acalServiceValidation.getTermTypes(callContext);
        
        assertNotNull(result);
        assertTrue(!result.isEmpty());
    }
    
    @Test
    public void testGetTermTypesForAcademicCalendarType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<TypeInfo> results = acalServiceValidation.getTermTypesForAcademicCalendarType(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, callContext);
        
        assertNotNull(results);
        List<String> expectedIds = new ArrayList<String>(2);
        expectedIds.addAll(Arrays.asList("kuali.atp.type.Fall", "kuali.atp.type.Spring"));
        
        // check that all the expected ids came back
        for(TypeInfo info : results) {
            expectedIds.remove(info.getKey());
        }
        
        assertTrue(expectedIds.isEmpty());
        
        List<TypeInfo> fakeTypes = null;
        try {
            fakeTypes = acalServiceValidation.getTermTypesForAcademicCalendarType("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeTypes);
        }
        
    }
    
    @Test
    public void testGetTermTypesForTermType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO
        List<TypeInfo> results = acalServiceValidation.getTermTypesForTermType(AtpServiceConstants.ATP_SPRING_TYPE_KEY, callContext);
        
        assertNotNull(results);
        List<String> expectedIds = new ArrayList<String>(2);
        expectedIds.addAll(Arrays.asList(AtpServiceConstants.ATP_HALF_SPRING_1_TYPE_KEY, AtpServiceConstants.ATP_HALF_SPRING_2_TYPE_KEY, AtpServiceConstants.ATP_SPRING_BREAK_TYPE_KEY));
        
        // check that all the expected ids came back
        for(TypeInfo info : results) {
            expectedIds.remove(info.getKey());
        }
        
        assertTrue(expectedIds.isEmpty());
        
        List<TypeInfo> fakeTypes = null;
        try {
            fakeTypes = acalServiceValidation.getTermTypesForTermType("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeTypes);
        }
        
        List<TypeInfo> expectedEmpty = acalServiceValidation.getTermTypesForTermType(AtpServiceConstants.ATP_SESSION_G2_TYPE_KEY, callContext);
        
        assertTrue(expectedEmpty == null || expectedEmpty.isEmpty());
    }
    
    @Test
    public void testRemoveTermFromAcademicCalendar() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = acalServiceValidation.removeTermFromAcademicCalendar("termRelationTestingAcal2", "termRelationTestingTerm2", callContext);
        
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        
        // retrieve the terms for the acal and make sure it does not include the removed term
        List<TermInfo> results = acalServiceValidation.getTermsForAcademicCalendar("termRelationTestingAcal2", callContext);
        
        if(results != null) {
            for(TermInfo term : results) {
                assertTrue(!term.getKey().equals("termRelationTestingTerm2"));
            }
        }
        
        StatusInfo noStatus = null;
        try {
            noStatus = acalServiceValidation.removeTermFromAcademicCalendar("termRelationTestingAcal2", "fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(noStatus);
        }
    }
    
    @Test
    public void testRemoveTermFromTerm() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = acalServiceValidation.removeTermFromTerm("termRelationTestingTerm3", "termRelationTestingTerm4", callContext);
        
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        
        // retrieve the terms for the parent term and make sure it does not include the removed term
        List<TermInfo> results = acalServiceValidation.getIncludedTermsInTerm("termRelationTestingTerm3", callContext);
        
        assertTrue(results == null || results.isEmpty());
        
        // try to remove the same term again, should get a DoesNotExistException
        StatusInfo noRepeatStatus = null;
        try {
            noRepeatStatus = acalServiceValidation.removeTermFromTerm("termRelationTestingTerm3", "termRelationTestingTerm4", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(noRepeatStatus);
        }
        
        StatusInfo noStatus = null;
        try {
            noStatus = acalServiceValidation.removeTermFromTerm("termRelationTestingTerm3", "fakeKey", callContext);
            fail("Did not get a InvalidParameterException when expected");
        }
        catch(InvalidParameterException e) {
            assertNull(noStatus);
        }
    }
    
    @Test
    public void testUpdateTerm() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException {
        
        // test updating an invalid term
        TermInfo blankTerm = new TermInfo();
        blankTerm.setKey("fakeKey");
        blankTerm.setTypeKey("fakeType");
        blankTerm.setStateKey("fakeState");
        blankTerm.setStartDate(new Date());
        blankTerm.setEndDate(new Date());
        
        TermInfo fakeTerm = null;
        try {
            fakeTerm = acalServiceValidation.updateTerm("fakeKey", blankTerm, callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeTerm);
        }
        
        TermInfo existing = acalServiceValidation.getTerm("termRelationTestingTerm3", callContext);
        String updatedName = "updated " + existing.getName();
        
        existing.setName(updatedName);
        existing.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        
        TermInfo revised = acalServiceValidation.updateTerm("termRelationTestingTerm3", existing, callContext);
        
        assertNotNull(revised);
        assertEquals(revised.getKey(), existing.getKey());
        
        TermInfo retrieved = acalServiceValidation.getTerm("termRelationTestingTerm3", callContext);
        
        assertNotNull(retrieved);
        assertEquals(retrieved.getName(), updatedName);
        assertEquals(retrieved.getStateKey(), AtpServiceConstants.ATP_DRAFT_STATE_KEY);
    }
    
    @Test
    public void testDeleteTerm() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = acalServiceValidation.deleteTerm("termRelationTestingTermDelete", callContext);
        
        assertTrue(status.getIsSuccess());
        
        StatusInfo noStatus = null;
        try {
            noStatus = acalServiceValidation.deleteTerm("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(noStatus);
        }
        
        // ensure the delete prevents future gets
        TermInfo shouldBeNull = null;
        try {
            shouldBeNull = acalServiceValidation.getTerm("termRelationTestingTermDelete", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
    }
    
    @Test
    public void testAddTermToTerm() throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        StatusInfo status = acalServiceValidation.addTermToTerm("termRelationTestingTerm5", "termRelationTestingTerm6", callContext);
        
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        
        // retrieve the terms for the parent term and make sure it does include the added term
        List<TermInfo> results = acalServiceValidation.getIncludedTermsInTerm("termRelationTestingTerm5", callContext);
        
        assertNotNull(results);
        assertEquals(1, results.size());
        
        TermInfo added = results.iterator().next();
        assertEquals("termRelationTestingTerm6", added.getKey());
        
        // assert that we can't add the term to the same term twice
        StatusInfo nullStatus = null;
        
        try { 
            nullStatus = acalServiceValidation.addTermToTerm("termRelationTestingTerm5", "termRelationTestingTerm6", callContext);
            fail("Did not get an AlreadyExistsException when expected");
        }
        catch(AlreadyExistsException e) {
            assertNull(nullStatus);
        }
        
        // assert that adding an invalid term fails
        try {
            nullStatus = acalServiceValidation.addTermToTerm("termRelationTestingTerm5", "fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(nullStatus);
        }
        
    }
    
    @Test
    public void testGetDataDictionaryEntryKeys() throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        List<String> results = acalServiceValidation.getDataDictionaryEntryKeys(callContext);
        
        assertNotNull(results);
        assertTrue(!results.isEmpty());
        
        assertTrue(results.contains("http://student.kuali.org/wsdl/acal/AcademicCalendarInfo"));
    }
    
    @Test
    public void testGetDataDictionaryEntry() throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
        DictionaryEntryInfo value = acalServiceValidation.getDataDictionaryEntry("http://student.kuali.org/wsdl/acal/AcademicCalendarInfo", callContext);
        
        assertNotNull(value);
        
        DictionaryEntryInfo fakeEntry = null;
        try {
            fakeEntry = acalServiceValidation.getDataDictionaryEntry("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeEntry);
        }
    }
}
