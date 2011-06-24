package org.kuali.student.enrollment.class1.hold.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class1.hold.service.decorators.HoldServiceValidationDecorator;
import org.kuali.student.enrollment.hold.dto.HoldInfo;
import org.kuali.student.enrollment.hold.dto.IssueInfo;
import org.kuali.student.enrollment.hold.dto.RestrictionInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:hold-test-context.xml"})
public class TestHoldServiceImpl {

    private HoldServiceValidationDecorator holdService;
    
    public static String principalId = "123";
    
    public ContextInfo callContext = ContextInfo.newInstance();
    
    @Autowired
    public void setHoldServiceValidation(HoldServiceValidationDecorator holdServiceValidationDecorator) {
        this.holdService = holdServiceValidationDecorator;
    }
    
    @Before
    public void setUp() {
        principalId = "123";    
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
    }
    
    @Test
    public void testAtpServiceValidationSetup() {
        assertNotNull(holdService);
    }
    
    @Test
    public void testGetIssue() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        IssueInfo issue = holdService.getIssue("Hold-Issue-1", callContext);
        
        assertNotNull(issue);
        assertEquals("Issue one", issue.getName());
        assertEquals("102", issue.getOrganizationId());
        
        IssueInfo fakeTest = null;
        try {
            fakeTest = holdService.getIssue("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeTest);
        }
    }
    
    @Test
    public void testGetIssuesByKeyList() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        List<String> issueKeys = new ArrayList<String>();
        issueKeys.addAll(Arrays.asList("Hold-Issue-1", "Hold-Issue-2"));
        
        List<IssueInfo> issues = holdService.getIssuesByIdList(issueKeys, callContext);
        
        assertNotNull(issues);
        assertEquals(issueKeys.size(), issues.size());
        
        // check that all the expected ids came back
        for(IssueInfo info : issues) {
            issueKeys.remove(info.getId());
        }
        
        assertTrue(issueKeys.isEmpty());
        
        // now make sure an exception is thrown for any not found keys
        
        List<String> fakeKeys = Arrays.asList("Hold-Issue-1", "fakeKey1", "fakeKey2");
        
        List<IssueInfo> shouldBeNull = null;
        try {
            shouldBeNull = holdService.getIssuesByIdList(fakeKeys, callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
    }

    
    @Test
    public void testGetIssuesByOrg() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<IssueInfo> issues = holdService.getIssuesByOrg("102", callContext);
        
        assertNotNull(issues);
        assertEquals(2, issues.size());
        
        List<String> issueKeys = new ArrayList<String>();
        issueKeys.addAll(Arrays.asList("Hold-Issue-1", "Hold-Issue-2"));
        
        // check that all the expected ids came back
        for(IssueInfo info : issues) {
            issueKeys.remove(info.getId());
        }
        
        assertTrue(issueKeys.isEmpty());
        
        // now make sure an empty list is returned for a non matching org id
        List<IssueInfo> shouldBeEmpty = holdService.getIssuesByOrg("fakeOrg", callContext);
        
        assertTrue(shouldBeEmpty.isEmpty());
    }
    
    @Test
    public void testCreateHold()throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, 
    MissingParameterException, OperationFailedException, PermissionDeniedException{
    	HoldInfo info = new HoldInfo();
    	//info.setId("Test-Hold-1"); id should be system generated
    	info.setName("Test hold one");
    	info.setStateKey(HoldServiceConstants.HOLD_ACIVE_STATE_KEY);
    	info.setTypeKey(HoldServiceConstants.STUDENT_HOLD_TYPE_KEY);
    	info.setIssueId("Hold-Issue-1");
    	info.setEffectiveDate(Calendar.getInstance().getTime());
    	
    	HoldInfo created = null;
    	try{
    		created = holdService.createHold(info, callContext);
    		assertNotNull(created);
            assertEquals("Test hold one", created.getName());
    	} catch (Exception e) {
            fail(e.getMessage());
        }
    	
    	try {
			HoldInfo retrieved = holdService.getHold(created.getId(), callContext);
			assertNotNull(retrieved);
			assertEquals("Test hold one", retrieved.getName());
		} catch (DoesNotExistException e) {
			fail(e.getMessage());
		}
    }
   
    @Test 
    public void testGetHold()throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
    	try{
    		holdService.getHold("Hold-blah", callContext);
    	} catch (DoesNotExistException e) {
			//expected
		}
    	
    	try{
    		HoldInfo info = holdService.getHold("Hold-1", callContext);
    		assertNotNull(info);
    		assertEquals("Hold one", info.getName()); 
 	        assertEquals(HoldServiceConstants.HOLD_ACIVE_STATE_KEY, info.getStateKey()); 
 	        assertEquals(HoldServiceConstants.STUDENT_HOLD_TYPE_KEY, info.getTypeKey()); 
 	        assertEquals("Hold Desc student", info.getDescr().getPlain()); 
    	} catch (Exception e) {
            fail(e.getMessage());
        }
    
    }
    
    @Test
    public void testReleaseHold() throws DoesNotExistException, InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {   
    	HoldInfo noStatus = null;
    	try{
    		noStatus = holdService.releaseHold("Hold-Blah", callContext);
    		fail("Did not get a DoesNotExistException when expected");
	    }
	    catch(DoesNotExistException e) {
	        assertNull(noStatus);
	    }
	    
    	try{
    		HoldInfo released = holdService.releaseHold("Hold-1", callContext);
    		assertNotNull(released);
			assertEquals("Hold one", released.getName());
			assertEquals(HoldServiceConstants.HOLD_RELEASED_STATE_KEY, released.getStateKey());
    	} catch (Exception e) {
            fail(e.getMessage());
        }  	
    }
    
    @Test
    public void testDeleteHold() throws DoesNotExistException, InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException { 
    	StatusInfo noStatus = null;
    	try{
    		noStatus = holdService.deleteHold("Hold-Blah", callContext);
    		fail("Did not get a DoesNotExistException when expected");
	    }
	    catch(DoesNotExistException e) {
	        assertNull(noStatus);
	    }
	    
    	try{
    		StatusInfo info = holdService.deleteHold("Hold-1", callContext);
    		assertNotNull(info);
    		assertTrue(info.isSuccess());
    		
    		HoldInfo deleted = holdService.getHold("Hold-1", callContext);
			assertEquals("Hold one", deleted.getName());
			assertEquals(HoldServiceConstants.HOLD_CANCELED_STATE_KEY, deleted.getStateKey());
    	} catch (Exception e) {
            fail(e.getMessage());
        }
    	
    }

    @Test
    public void testGetRestrictionsByIssue() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<RestrictionInfo> restrictions = holdService.getRestrictionsByIssue("Hold-Issue-1", callContext);
        
        assertNotNull(restrictions);
        assertEquals(1, restrictions.size());
        
        List<String> expectedIds = new ArrayList<String>();
        expectedIds.add("Hold-Restriction-1");
        
        // check that all the expected ids came back
        for(RestrictionInfo info : restrictions) {
            expectedIds.remove(info.getKey());
        }
        
        assertTrue(expectedIds.isEmpty());
        
        List<RestrictionInfo> fakeRestrictions = null;
        
        try {
            fakeRestrictions = holdService.getRestrictionsByIssue("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeRestrictions);
        }
    }
    
    @Test
    public void testGetRestrictionKeysByType() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        String expectedRestrictionType = "kuali.hold.restriction.type.registration";
        
        List<String> restrictionKeys = holdService.getRestrictionKeysByType(expectedRestrictionType, callContext);
        
        assertTrue(restrictionKeys.contains("Hold-Restriction-1"));
        
        String expectedEmptyRestrictionType = "kuali.hold.restriction.type.add.drop.class";
        
        restrictionKeys = holdService.getRestrictionKeysByType(expectedEmptyRestrictionType, callContext);
        
        assertTrue(restrictionKeys == null || restrictionKeys.isEmpty());
        
        String fakeRestrictionType = "fakeTypeKey";
        
        List<String> shouldBeNull = null;
        try {
            shouldBeNull = holdService.getRestrictionKeysByType(fakeRestrictionType, callContext);
            fail("Did not get a InvalidParameterException when expected");
        }
        catch(InvalidParameterException e) {
            assertNull(shouldBeNull);
        }
    }
    
    @Test
    public void testGetDataDictionaryEntryKeys() throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        List<String> results = holdService.getDataDictionaryEntryKeys(callContext);
        
        assertNotNull(results);
        assertTrue(!results.isEmpty());
        
        assertTrue(results.contains("http://student.kuali.org/wsdl/hold/HoldInfo"));
    }
    	   
}
