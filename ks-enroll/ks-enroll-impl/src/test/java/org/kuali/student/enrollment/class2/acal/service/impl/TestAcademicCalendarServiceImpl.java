package org.kuali.student.enrollment.class2.acal.service.impl;

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.lum.program.service.assembler.ProgramAssemblerConstants;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:acal-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestAcademicCalendarServiceImpl{
    @Autowired
    @Qualifier("acalServiceAuthDecorator")
    private AcademicCalendarService acalService;
    
    public static String principalId = "123";
    public ContextInfo callContext = ContextInfo.newInstance();

	@Before
    public void setUp() {
        principalId = "123";    
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
    }

	@Test
    public void testAcademicCalendarServiceSetup() {
    	assertNotNull(acalService);
    }

	@Test 
    public void testGetAcademicCalendarFromDerby()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException {
		AcademicCalendarInfo acal = acalService.getAcademicCalendar("testAtpId1",callContext);
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
        	List<ValidationResultInfo> vris= acalService.validateAcademicCalendar("FULL_VALIDATION", acal, callContext);
                if ( ! vris.isEmpty()) {
                    StringBuilder buf = new StringBuilder ();
                    buf.append(vris.size()).append (" validaton errors found did not expect any");
                    for (ValidationResultInfo vri : vris) {
                        buf.append("\n");
                        buf.append(vri.getElement()).append(": ").append(vri.getMessage());
                    }
                    fail (buf.toString());
                }
        	assertTrue(vris.isEmpty());
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
            AcademicCalendarInfo created = acalService.createAcademicCalendar("testAcalId1", acal, callContext);
            assertNotNull(created);
            assertEquals("testAcalId1", created.getKey());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
        try {
            acalService.createAcademicCalendar("testAcalId1", acal, callContext);
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
                AcademicCalendarInfo created = acalService.createAcademicCalendar("testNewAcalId", acal, callContext);
                assertNotNull(created);
                assertEquals("testNewAcalId", created.getKey());
                
                
                AcademicCalendarInfo existed = acalService.getAcademicCalendar("testNewAcalId", callContext);

                assertNotNull(existed);
                
                existed.setName("testUpdatedAcal");
                // TODO - need actual ProgramTypeKey attribute in test database sql
                existed.setCredentialProgramTypeKey(ProgramAssemblerConstants.BACCALAUREATE_PROGRAM);
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
                assertTrue(ret.getIsSuccess());

                AcademicCalendarInfo existed = acalService.getAcademicCalendar("testDeletedAcalId", callContext);
                assertNull(existed);
            } catch (DoesNotExistException dnee) {
            	//this is expected
            } catch (Exception ex) {
                fail("exception from service call :" + ex.getMessage());
            }
    }

    @Test
    public void testGetAcademicCalendarsByStartYear()throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, AlreadyExistsException{
        List<AcademicCalendarInfo> acalInfos = acalService.getAcademicCalendarsByStartYear(1980, callContext);
        assertNotNull("No Calendars returned.", acalInfos);

        Set<String> acalNames = new HashSet<String>();
        for (AcademicCalendarInfo acalInfo : acalInfos) {
            acalNames.add(acalInfo.getName());
        }

        Set<String> expected = new HashSet<String>();
        expected.add("testEdgeAtpId1");
        expected.add("testEdgeAtpId4");
        expected.add("testEdgeAtpId6");
        expected.add("testEdgeAtpId7");
        expected.add("testEdgeAtpId8");

        Set<String> unexpected = new HashSet<String>();
        unexpected.add("testEdgeAtpId2");
        unexpected.add("testEdgeAtpId3");
        unexpected.add("testEdgeAtpId5");
        unexpected.add("testEdgeAtpId9");
        unexpected.add("testEdgeAtpId10");

        for (String acalName : expected) {
            assertTrue("Expected calendar not returned: " + acalName, acalNames.contains(acalName));
        }
        for (String acalName : unexpected) {
            assertFalse("Unexpected calendar returned: " + acalName, acalNames.contains(acalName));
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
        	List<ValidationResultInfo> vri= acalService.validateTerm("SKIP_REQUREDNESS_VALIDATIONS", term, callContext);
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
        		created = acalService.createTerm("testTermId2", term, callContext);
        	 } catch (AlreadyExistsException ex) {
                 //expected);
             }   	
        	 
        	term.setKey("testNewTermId");
        	created = acalService.createTerm("testNewTermId", term, callContext);
            assertNotNull(created);
            assertEquals("testNewTermId", created.getKey());
            
            TermInfo existed = acalService.getTerm("testNewTermId", callContext);

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
	    	acalService.addTermToAcademicCalendar("testAtpId1", "testTermId1", callContext);
	    	} catch (AlreadyExistsException ex) {
	            //expected);
	        } 
	    	
	    	StatusInfo status = acalService.addTermToAcademicCalendar("testAtpId1", "testTermId2", callContext);
	    	assertTrue(status.getIsSuccess());
    	} catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
    	}
    	
    	List<TermInfo> terms = acalService.getTermsForAcademicCalendar("testAtpId1", callContext);
    	
    	assertEquals(2, terms.size());
    }
    
    @Test 
    public void testGetTermToAcademicCalendar()throws DoesNotExistException, InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException, AlreadyExistsException{
    	try{
    		try{
    			acalService.getTermsForAcademicCalendar("testTermId1", callContext);
    		}catch (OperationFailedException ex){
    			//expected because it's not an acal
    		}
    		
    		List<TermInfo> terms = acalService.getTermsForAcademicCalendar("testAtpId1", callContext);
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
        List<KeyDateInfo> results = acalService.getKeyDatesForTerm("termRelationTestingTerm1", callContext);
        
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
            fakeKeyDates = acalService.getKeyDatesForTerm("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeKeyDates);
        }
    }
    
    @Test
    public void testGetContainingTerms() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TermInfo> results = acalService.getContainingTerms("termRelationTestingTerm2", callContext);
        
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
            fakeResults = acalService.getContainingTerms("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeResults);
        }
    }
    
    @Test
    public void testGetTermKeysByType() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        String expectedTermType = "kuali.atp.type.HalfFall1";
        
        List<String> termKeys = acalService.getTermKeysByType(expectedTermType, callContext);
        
        assertTrue(termKeys.contains("termRelationTestingTerm4"));
        
        String expectedEmptyTermType = "kuali.atp.type.SessionG2";
        
        termKeys = acalService.getTermKeysByType(expectedEmptyTermType, callContext);
        
        assertTrue(termKeys == null || termKeys.isEmpty());
        
        String fakeTermType = "fakeTypeKey";
        
        List<String> shouldBeNull = null;
        try {
            shouldBeNull = acalService.getTermKeysByType(fakeTermType, callContext);
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
        
        List<TermInfo> terms = acalService.getTermsByKeyList(termKeys, callContext);
        
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
            shouldBeNull = acalService.getTermsByKeyList(fakeKeys, callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
    }
    
    @Test
    public void testGetTermsForAcademicCalendar() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TermInfo> results = acalService.getTermsForAcademicCalendar("termRelationTestingAcal1", callContext);
        
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
            fakeResults = acalService.getTermsForAcademicCalendar("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeResults);
        }
    }
    
    @Test
    public void testGetIncludedTermsInTerm() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TermInfo> results = acalService.getIncludedTermsInTerm("termRelationTestingTerm1", callContext);
        
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
            fakeResults = acalService.getIncludedTermsInTerm("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeResults);
        }
    }
    
    @Test
    public void testGetTermState() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        StateInfo result = acalService.getTermState(AtpServiceConstants.ATP_DRAFT_STATE_KEY, callContext);
        
        assertNotNull(result);
        assertEquals(result.getName(), "Draft");
        
        StateInfo fakeState = null;
        try {
            fakeState = acalService.getTermState("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeState);
        }
    }
    
    @Test
    public void testGetTermStates() throws InvalidParameterException, MissingParameterException, OperationFailedException, DoesNotExistException {
        List<StateInfo> result = acalService.getTermStates(callContext);
        
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
        TypeInfo result = acalService.getTermType(AtpServiceConstants.ATP_HALF_FALL_1_TYPE_KEY, callContext);
        
        assertNotNull(result);
        assertEquals(result.getName(), "Fall Half-Semester 1");
        
        TypeInfo fakeType = null;
        try {
            fakeType = acalService.getTermType("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeType);
        }
    }
    
    @Test
    public void testGetTermTypes() throws InvalidParameterException, MissingParameterException, OperationFailedException {
        List<TypeInfo> result = acalService.getTermTypes(callContext);
        
        assertNotNull(result);
        assertTrue(!result.isEmpty());
    }
    
    @Test
    public void testGetTermTypesForAcademicCalendarType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<TypeInfo> results = acalService.getTermTypesForAcademicCalendarType(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, callContext);
        
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
            fakeTypes = acalService.getTermTypesForAcademicCalendarType("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeTypes);
        }
        
    }
    
    @Test
    public void testGetTermTypesForTermType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO
        List<TypeInfo> results = acalService.getTermTypesForTermType(AtpServiceConstants.ATP_SPRING_TYPE_KEY, callContext);
        
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
            fakeTypes = acalService.getTermTypesForTermType("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeTypes);
        }
        
        List<TypeInfo> expectedEmpty = acalService.getTermTypesForTermType(AtpServiceConstants.ATP_SESSION_G2_TYPE_KEY, callContext);
        
        assertTrue(expectedEmpty == null || expectedEmpty.isEmpty());
    }
    
    @Test
    public void testRemoveTermFromAcademicCalendar() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = acalService.removeTermFromAcademicCalendar("termRelationTestingAcal2", "termRelationTestingTerm2", callContext);
        
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        
        // retrieve the terms for the acal and make sure it does not include the removed term
        List<TermInfo> results = acalService.getTermsForAcademicCalendar("termRelationTestingAcal2", callContext);
        
        if(results != null) {
            for(TermInfo term : results) {
                assertTrue(!term.getKey().equals("termRelationTestingTerm2"));
            }
        }
        
        StatusInfo noStatus = null;
        try {
            noStatus = acalService.removeTermFromAcademicCalendar("termRelationTestingAcal2", "fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(noStatus);
        }
    }
    
    @Test
    public void testRemoveTermFromTerm() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = acalService.removeTermFromTerm("termRelationTestingTerm3", "termRelationTestingTerm4", callContext);
        
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        
        // retrieve the terms for the parent term and make sure it does not include the removed term
        List<TermInfo> results = acalService.getIncludedTermsInTerm("termRelationTestingTerm3", callContext);
        
        assertTrue(results == null || results.isEmpty());
        
        // try to remove the same term again, should get a DoesNotExistException
        StatusInfo noRepeatStatus = null;
        try {
            noRepeatStatus = acalService.removeTermFromTerm("termRelationTestingTerm3", "termRelationTestingTerm4", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(noRepeatStatus);
        }
        
        StatusInfo noStatus = null;
        try {
            noStatus = acalService.removeTermFromTerm("termRelationTestingTerm3", "fakeKey", callContext);
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
            fakeTerm = acalService.updateTerm("fakeKey", blankTerm, callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeTerm);
        }
        
        TermInfo existing = acalService.getTerm("termRelationTestingTerm3", callContext);
        String updatedName = "updated " + existing.getName();
        
        existing.setName(updatedName);
        existing.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        
        TermInfo revised = acalService.updateTerm("termRelationTestingTerm3", existing, callContext);
        
        assertNotNull(revised);
        assertEquals(revised.getKey(), existing.getKey());
        
        TermInfo retrieved = acalService.getTerm("termRelationTestingTerm3", callContext);
        
        assertNotNull(retrieved);
        assertEquals(retrieved.getName(), updatedName);
        assertEquals(retrieved.getStateKey(), AtpServiceConstants.ATP_DRAFT_STATE_KEY);
    }
    
    @Test
    public void testDeleteTerm() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = acalService.deleteTerm("termRelationTestingTermDelete", callContext);
        
        assertTrue(status.getIsSuccess());
        
        StatusInfo noStatus = null;
        try {
            noStatus = acalService.deleteTerm("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(noStatus);
        }
        
        // ensure the delete prevents future gets
        TermInfo shouldBeNull = null;
        try {
            shouldBeNull = acalService.getTerm("termRelationTestingTermDelete", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
    }
    
    @Test
    public void testAddTermToTerm() throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        StatusInfo status = acalService.addTermToTerm("termRelationTestingTerm5", "termRelationTestingTerm6", callContext);
        
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        
        // retrieve the terms for the parent term and make sure it does include the added term
        List<TermInfo> results = acalService.getIncludedTermsInTerm("termRelationTestingTerm5", callContext);
        
        assertNotNull(results);
        assertEquals(1, results.size());
        
        TermInfo added = results.iterator().next();
        assertEquals("termRelationTestingTerm6", added.getKey());
        
        // assert that we can't add the term to the same term twice
        StatusInfo nullStatus = null;
        
        try { 
            nullStatus = acalService.addTermToTerm("termRelationTestingTerm5", "termRelationTestingTerm6", callContext);
            fail("Did not get an AlreadyExistsException when expected");
        }
        catch(AlreadyExistsException e) {
            assertNull(nullStatus);
        }
        
        // assert that adding an invalid term fails
        try {
            nullStatus = acalService.addTermToTerm("termRelationTestingTerm5", "fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(nullStatus);
        }
        
    }
    
    @Test
    public void testGetDataDictionaryEntryKeys() throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        List<String> results = acalService.getDataDictionaryEntryKeys(callContext);
        
        assertNotNull(results);
        assertTrue(!results.isEmpty());
        
        assertTrue(results.contains("http://student.kuali.org/wsdl/acal/AcademicCalendarInfo"));
    }
    
    @Test
    public void testGetDataDictionaryEntry() throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
        DictionaryEntryInfo value = acalService.getDataDictionaryEntry("http://student.kuali.org/wsdl/acal/AcademicCalendarInfo", callContext);
        
        assertNotNull(value);
        
        DictionaryEntryInfo fakeEntry = null;
        try {
            fakeEntry = acalService.getDataDictionaryEntry("fakeKey", callContext);            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeEntry);
        }
    }
    
    @Test
    public void testGetAcademicCalendarsByCredentialProgramType() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AcademicCalendarInfo acal = new AcademicCalendarInfo();
        acal.setKey("testAcal-CPT-Id1");
        acal.setName("testGetAcalByCpt");
        acal.setCredentialProgramTypeKey("kuali.lu.type.credential.Baccalaureate");
        acal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        acal.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        try{
            AcademicCalendarInfo created = acalService.createAcademicCalendar("testAcal-CPT-Id1", acal, callContext);
            assertNotNull(created);

            AcademicCalendarInfo retrieved = acalService.getAcademicCalendar("testAcal-CPT-Id1", callContext);
            assertNotNull(retrieved);
            
            List<AcademicCalendarInfo> acals = acalService.getAcademicCalendarsByCredentialProgramType("kuali.lu.type.credential.Baccalaureate", callContext);
            assertNotNull(acals);
            assertEquals(1, acals.size());
            assertEquals("kuali.lu.type.credential.Baccalaureate", acals.get(0).getCredentialProgramTypeKey());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }    	
    }

    @Test
    public void testCreateKeyDateForTerm() throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	KeyDateInfo keyDate = new KeyDateInfo();
    	keyDate.setKey("new-keydate-Id");
    	keyDate.setName("testCreate");
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2005);
        
        keyDate.setStartDate(cal.getTime());
        keyDate.setIsAllDay(false);
        keyDate.setIsDateRange(true);
        keyDate.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        keyDate.setTypeKey(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY);
        RichTextInfo descr = new RichTextInfo();
        descr.setPlain("Test");
        keyDate.setDescr(descr);
       
        
        try {
        	KeyDateInfo created = acalService.createKeyDateForTerm("termRelationTestingTerm1", "new-keydate-Id", keyDate, callContext);
            assertNotNull(created);
            assertEquals("new-keydate-Id", created.getKey());
            assertEquals("testCreate", created.getName());
            
            try{
            	KeyDateInfo retrieved = acalService.getKeyDate("new-keydate-Id", callContext);
            	assertNotNull(retrieved);
            	assertEquals("new-keydate-Id", retrieved.getKey());
                assertEquals("testCreate", retrieved.getName());
            }catch(DoesNotExistException e) {
                fail(e.getMessage());
            }
            
            try{
            	List<KeyDateInfo> kds = acalService.getKeyDatesForTerm("termRelationTestingTerm1", callContext);
            	assertNotNull(kds);
            	assertTrue(!kds.isEmpty());
            	List<String> kdIds = new ArrayList<String>();
            	for(KeyDateInfo kd : kds){
            		kdIds.add(kd.getKey());
            	}
            	assertTrue(kdIds.contains("new-keydate-Id"));
            }catch(DoesNotExistException e) {
                fail(e.getMessage());
            }
        }
        catch(AlreadyExistsException e) {
            fail(e.getMessage());
        } 
    }
}
