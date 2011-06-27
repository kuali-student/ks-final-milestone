package org.kuali.student.r2.core.class1.atp.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
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
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.AtpMilestoneRelationInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:atp-test-context.xml"})
public class TestAtpServiceImplRemote {
	private AtpService atpServiceValidation;
	
	public static String principalId = "123";
    public ContextInfo callContext = ContextInfo.newInstance();

    @Autowired
	public void setAtpServiceValidation(AtpService atpServiceValidation) {
		this.atpServiceValidation = atpServiceValidation;
	}
  
	@Before
    public void setUp() {
        principalId = "123";    
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
    }
	
	@Test
    public void testAtpServiceValidationSetup() {
    	assertNotNull(atpServiceValidation);
    }
	
    @Test
    public void testGetAtp() throws DoesNotExistException, InvalidParameterException,
								    MissingParameterException, OperationFailedException, PermissionDeniedException {
		try{
			AtpInfo atpInfo = atpServiceValidation.getAtp("testAtpId1", callContext);
			assertNotNull(atpInfo);
			assertEquals("testAtpId1", atpInfo.getKey());
			assertEquals("testAtp1", atpInfo.getName());
			assertEquals("Desc 101", atpInfo.getDescr().getPlain());
			assertEquals("kuali.atp.state.Draft", atpInfo.getStateKey());
			assertEquals("kuali.atp.type.AcademicCalendar", atpInfo.getTypeKey());
			try {
				atpServiceValidation.getAtp("totallyBogusAtpId999", callContext);
			    fail("AtpService did not throw DoesNotExistException on getAtp() of nonexistent ATP");
			}
			catch (DoesNotExistException dnee) {}
		} catch (Exception ex) {
			fail("exception from service call :" + ex.getMessage());
		}
    }
    
    @Test 
    public void testAtpCrud()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException {
        // test create
        AtpInfo atpInfo = new AtpInfo();
        atpInfo.setKey("newId");
        atpInfo.setName("newId");
        atpInfo.setTypeKey("kuali.atp.type.AcademicCalendar");
        atpInfo.setStateKey("kuali.atp.state.Draft");
        atpInfo.setStartDate(Calendar.getInstance().getTime());
        atpInfo.setEndDate(Calendar.getInstance().getTime());
        AtpInfo created = null;
        try {
            created = atpServiceValidation.createAtp("newId", atpInfo, callContext);
            assertNotNull(created);
            assertEquals("newId", created.getKey());
        } catch (AlreadyExistsException e) {
            fail(e.getMessage());
        } catch (DataValidationErrorException e) {
            fail(e.getMessage());
        }
        
        // test read
		AtpInfo fetched = atpServiceValidation.getAtp("testAtpId1", callContext);
		assertNotNull(fetched);
		assertEquals("testAtpId1", fetched.getKey());
		assertEquals("testAtp1", fetched.getName());
		assertEquals("Desc 101", fetched.getDescr().getPlain());
		assertEquals("kuali.atp.state.Draft", fetched.getStateKey());
		assertEquals("kuali.atp.type.AcademicCalendar", fetched.getTypeKey());
         
        // test update
        String atpNameOrig = fetched.getName();
        AtpInfo modified = AtpInfo.getInstance(fetched);
        modified.setName(atpNameOrig + "updated");
        AtpInfo updated = null;
        try {
	        updated = atpServiceValidation.updateAtp(fetched.getKey(), modified, callContext);
        } catch (Exception e) {
            fail("Exception thrown when updating ATP: " + e.getMessage());
        }
        assertNotNull(updated);
        assertEquals(atpNameOrig + "updated", updated.getName());
        
        // test delete
        atpInfo = atpServiceValidation.getAtp("testDeleteAtpId1", callContext);
        assertNotNull(atpInfo);
        assertEquals("testDeleteAtpId1", atpInfo.getKey());
        
        try{
        	atpServiceValidation.deleteAtp("testDeleteAtpId1", callContext);
	        try {
		        AtpInfo deleted = atpServiceValidation.getAtp("testDeleteAtpId1", callContext);
		        fail("Did not receive DoesNotExistException when attempting to get already-deleted AtpEntity");
	        } catch (DoesNotExistException dnee) {}
        } catch (Exception e){
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testUpdateAtp()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException {
        AtpInfo atpInfo = atpServiceValidation.getAtp("testAtpId1", callContext);
        assertNotNull(atpInfo);
        assertEquals("testAtpId1", atpInfo.getKey());
        
        String atpNameOrig = atpInfo.getName();
        AtpInfo modified = AtpInfo.getInstance(atpInfo);
        modified.setName(atpNameOrig + "updated");
        AtpInfo updated = atpServiceValidation.updateAtp(atpInfo.getKey(), modified, callContext);
        assertNotNull(updated);
        assertEquals(atpNameOrig + "updated", updated.getName());
    }
    
    @Test
    public void testCreateAtp()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException{
        AtpInfo atpInfo = AtpInfo.newInstance();
        atpInfo.setKey("newId2");
        atpInfo.setName("newId2");
        atpInfo.setTypeKey("kuali.atp.type.AcademicCalendar");
        atpInfo.setStateKey("kuali.atp.state.Draft");
        atpInfo.setStartDate(Calendar.getInstance().getTime());
        atpInfo.setEndDate(Calendar.getInstance().getTime());
        try {
            AtpInfo created = atpServiceValidation.createAtp("newId2", atpInfo, callContext);
            assertNotNull(created);
            assertEquals("newId2", created.getKey());
        } catch (Exception e) {
            fail(e.getMessage());
        }
        
        // attempt to get
        AtpInfo retrieved = atpServiceValidation.getAtp("newId2", callContext);
        
        assertNotNull(retrieved);
        assertEquals("newId2", retrieved.getKey());
    }
   
    @Test
    public void testDeleteAtp() throws DoesNotExistException,
    InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        AtpInfo atpInfo = atpServiceValidation.getAtp("testDeleteAtpId2", callContext);
        assertNotNull(atpInfo);
        assertEquals("testDeleteAtpId2", atpInfo.getKey());
        
        try{
        	atpServiceValidation.deleteAtp("testDeleteAtpId2", callContext);
	        try {
		        AtpInfo deleted = atpServiceValidation.getAtp("testDeleteAtpId2", callContext);
		        fail("Did not receive DoesNotExistException when attempting to get already-deleted AtpEntity");
	        } catch (DoesNotExistException dnee) {}
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testCreateMilestone() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        MilestoneInfo milestone = new MilestoneInfo();
        milestone.setKey("newId");
        milestone.setName("testCreate");
        milestone.setStartDate(new Date());
        milestone.setDateRange(false);
        milestone.setAllDay(true);
        milestone.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        milestone.setTypeKey("kuali.atp.milestone.RegistrationPeriod");
        RichTextInfo descr = new RichTextInfo();
        descr.setPlain("Test");
        milestone.setDescr(descr);
       
        
        try {
            MilestoneInfo created = atpServiceValidation.createMilestone("newId", milestone, callContext);
            assertNotNull(created);
            assertEquals("newId", created.getKey());
            assertEquals("testCreate", created.getName());
        }
        catch(AlreadyExistsException e) {
            fail(e.getMessage());
        }
        
        // try to get the just-created milestone
        MilestoneInfo found = atpServiceValidation.getMilestone("newId", callContext);
        assertNotNull(found);
        
        // ensure we cannot create another of the same id
        MilestoneInfo dupeCreated = null;
        try {
            dupeCreated = atpServiceValidation.createMilestone("newId", milestone, callContext);
            fail("Did not get an AlreadyExistsException when expected");
        }
        catch(AlreadyExistsException e) {
            assertNull(dupeCreated);
        }
    }
        
    @Test
    public void testUpdateMilestone() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException {
        MilestoneInfo milestone = new MilestoneInfo();
        milestone.setKey("newId2");
        milestone.setName("testCreate");
        milestone.setStartDate(new Date());
        milestone.setDateRange(false);
        milestone.setAllDay(true);
        milestone.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        milestone.setTypeKey("kuali.atp.milestone.RegistrationPeriod");
       
        RichTextInfo descr = new RichTextInfo();
        descr.setPlain("Test");
        milestone.setDescr(descr);
       
        
        try {
            MilestoneInfo created = atpServiceValidation.createMilestone("newId2", milestone, callContext);
            assertNotNull(created);
            assertEquals("newId2", created.getKey());
            assertEquals("testCreate", created.getName());
        }
        catch(AlreadyExistsException e) {
            fail(e.getMessage());
        }
        
        MilestoneInfo updateData = atpServiceValidation.getMilestone("newId2", callContext);
        
        String updatedName = "updated " + updateData.getName();
        
        updateData.setName(updatedName);
        
        MilestoneInfo updated = atpServiceValidation.updateMilestone("newId2", updateData, callContext);
        assertNotNull(updated);
        assertEquals(updated.getKey(), "newId2");
        assertEquals(updated.getName(), updatedName);
        
        // now fetch the updated milestone fresh, and check fields
        updated = atpServiceValidation.getMilestone("newId2", callContext);
        assertNotNull(updated);
        assertEquals(updated.getKey(), "newId2");
        assertEquals(updated.getName(), updatedName);
        
        
        MilestoneInfo shouldBeNull = null;
        try {
            shouldBeNull = atpServiceValidation.updateMilestone("fakeKey", updated, callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
    }
    
    @Test
    public void testDeleteMilestone() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException {
        StatusInfo status = atpServiceValidation.deleteMilestone("testDeleteId", callContext);
        
        assertTrue(status.getIsSuccess());
        
        StatusInfo noStatus = null;
        try {
            noStatus = atpServiceValidation.deleteMilestone("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(noStatus);
        }
        
        // ensure the delete prevents future gets
        MilestoneInfo shouldBeNull = null;
        try {
            shouldBeNull = atpServiceValidation.getMilestone("testDeleteId", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
        
    }
    
    @Test
    public void testGetMilestone() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MilestoneInfo milestoneInfo = atpServiceValidation.getMilestone("testId", callContext);
        assertNotNull(milestoneInfo);
        assertEquals("testId", milestoneInfo.getKey());
        assertEquals("testId", milestoneInfo.getName());
        assertEquals("Desc 105", milestoneInfo.getDescr().getPlain());
        assertEquals(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY, milestoneInfo.getStateKey());
        assertEquals("kuali.atp.milestone.AdvanceRegistrationPeriod", milestoneInfo.getTypeKey());
        
        MilestoneInfo fakeMilestone = null;
        try {
            fakeMilestone = atpServiceValidation.getMilestone("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeMilestone);
        }
    }
    
    @Test
    public void testGetMilestonesByKeyList() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> milestoneKeys = new ArrayList<String>();
        milestoneKeys.addAll(Arrays.asList("testId", "testId2"));
        
        List<MilestoneInfo> milestones = atpServiceValidation.getMilestonesByKeyList(milestoneKeys, callContext);
        
        assertNotNull(milestones);
        assertEquals(milestoneKeys.size(), milestones.size());
        
        // check that all the expected ids came back
        for(MilestoneInfo info : milestones) {
            milestoneKeys.remove(info.getKey());
        }
        
        assertTrue(milestoneKeys.isEmpty());
        
        // now make sure an exception is thrown for any not found keys
        
        List<String> fakeKeys = Arrays.asList("testId", "fakeKey1", "fakeKey2");
        
        List<MilestoneInfo> shouldBeNull = null;
        try {
            shouldBeNull = atpServiceValidation.getMilestonesByKeyList(fakeKeys, callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
        
    }
    
    @Test
    public void testGetMilestonesByDates() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        Calendar cal = Calendar.getInstance();
        
        cal.set(Calendar.MONTH, Calendar.JULY);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.YEAR, 2011);
        
        Date startDate = cal.getTime();
        
        cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
        Date endDate = cal.getTime();
        
        List<MilestoneInfo> milestones = atpServiceValidation.getMilestonesByDates(startDate, endDate, callContext);
        
        assertNotNull(milestones);
        assertEquals(2, milestones.size());
        
        List<String> expectedIds = new ArrayList<String>();
        expectedIds.addAll(Arrays.asList("testId", "testId2"));
        
        // check that all the expected ids came back
        for(MilestoneInfo info : milestones) {
            expectedIds.remove(info.getKey());
        }
        
        assertTrue(expectedIds.isEmpty());
        
        cal.set(Calendar.YEAR, 1990);
        
        startDate = cal.getTime();
        endDate = cal.getTime();
        
        milestones = atpServiceValidation.getMilestonesByDates(startDate, endDate, callContext);
        
        assertTrue(milestones == null || milestones.isEmpty());
    }
    
    @Test
    public void testGetMilestoneKeysByType() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        String expectedMilestoneType = "kuali.atp.milestone.RegistrationPeriod";
        
        List<String> milestoneKeys = atpServiceValidation.getMilestoneKeysByType(expectedMilestoneType, callContext);
        
        assertTrue(milestoneKeys.contains("testId2"));
        
        String expectedEmptyMilestoneType = "kuali.atp.milestone.GradesDue";
        
        milestoneKeys = atpServiceValidation.getMilestoneKeysByType(expectedEmptyMilestoneType, callContext);
        
        assertTrue(milestoneKeys == null || milestoneKeys.isEmpty());
        
        String fakeMilestoneType = "fakeTypeKey";
        
        List<String> shouldBeNull = null;
        try {
            shouldBeNull = atpServiceValidation.getMilestoneKeysByType(fakeMilestoneType, callContext);
            fail("Did not get a InvalidParameterException when expected");
        }
        catch(InvalidParameterException e) {
            assertNull(shouldBeNull);
        }
    }
    
    @Test
    public void testGetAtpMilestoneRelation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AtpMilestoneRelationInfo relation = atpServiceValidation.getAtpMilestoneRelation("ATPMSTONEREL-1", callContext);
        assertEquals(relation.getMilestoneKey(), "testId");
        assertEquals(relation.getAtpKey(), "testAtpId1");
        assertEquals(relation.getTypeKey(), "kuali.atp.milestone.relation.owns");
        assertEquals(relation.getStateKey(), AtpServiceConstants.ATP_MILESTONE_RELATION_ACTIVE_STATE_KEY);
        
        AtpMilestoneRelationInfo fakeRelation = null;
        try {
            fakeRelation = atpServiceValidation.getAtpMilestoneRelation("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeRelation);
        }
    }
    
    @Test
    public void testGetAtpMilestoneRelationsByIdList() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> idList = new ArrayList<String>();
        idList.addAll(Arrays.asList("ATPMSTONEREL-1", "ATPMSTONEREL-2"));
        
        List<AtpMilestoneRelationInfo> results = atpServiceValidation.getAtpMilestoneRelationsByIdList(idList, callContext);
        
        assertNotNull(results);
        assertTrue(!results.isEmpty());
        
        for(AtpMilestoneRelationInfo rel : results) {
            idList.remove(rel.getId());
        }
        
        // ensure all the expected ids were found
        assertTrue(idList.isEmpty());
        
        idList.add("fakeKey");
        idList.add("ATPMSTONEREL-1");
        
        List<AtpMilestoneRelationInfo> fakeRelations = null;
        try {
            fakeRelations = atpServiceValidation.getAtpMilestoneRelationsByIdList(idList, callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeRelations);
        }
    }
    
    @Test
    public void testGetAtpMilestoneRelationsByAtp() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AtpMilestoneRelationInfo> results = atpServiceValidation.getAtpMilestoneRelationsByAtp("testAtpId1", callContext);
        
        assertNotNull(results);
        assertEquals(results.size(), 1);
        
        AtpMilestoneRelationInfo rel = results.get(0);
        assertEquals(rel.getAtpKey(), "testAtpId1");
        assertEquals(rel.getMilestoneKey(), "testId");
        assertEquals(rel.getTypeKey(), "kuali.atp.milestone.relation.owns");
        
        List<AtpMilestoneRelationInfo> fakeRelations = null;
        try {
            fakeRelations = atpServiceValidation.getAtpMilestoneRelationsByAtp("fakeAtp", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeRelations);
        }
    }
    @Ignore
    @Test
    public void testGetAtpMilestoneRelationsByMilestone() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AtpMilestoneRelationInfo> results = atpServiceValidation.getAtpMilestoneRelationsByMilestone("testId2", callContext);
        
        assertNotNull(results);
        assertEquals(results.size(), 1);
        
        AtpMilestoneRelationInfo rel = results.get(0);
        assertEquals(rel.getAtpKey(), "testAtpId2");
        assertEquals(rel.getMilestoneKey(), "testId2");
        assertEquals(rel.getTypeKey(), "kuali.atp.milestone.relation.owns");
        
        List<AtpMilestoneRelationInfo> fakeRelations = null;
        try {
            fakeRelations = atpServiceValidation.getAtpMilestoneRelationsByMilestone("fakeAtp", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeRelations);
        }
    }
    
    @Test
    public void testGetAtpMilestoneRelationIdsByType() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> results = atpServiceValidation.getAtpMilestoneRelationIdsByType("kuali.atp.milestone.relation.owns", callContext);
        
        assertNotNull(results);
        assertEquals(3, results.size());
        
        Collection<String> listToCheck = new ArrayList<String>();
        listToCheck.addAll(Arrays.asList("ATPMSTONEREL-1", "ATPMSTONEREL-2", "termRelationTestingRel-TermDate-1"));
        
        for(String id : results) {
            listToCheck.remove(id);
        }
        
        assertTrue(listToCheck.isEmpty());
        
        List<String> fakeIds = null;
        try {
            fakeIds = atpServiceValidation.getAtpMilestoneRelationIdsByType("fakeType", callContext);
            fail("Did not get a InvalidParameterException when expected");
        }
        catch(InvalidParameterException e) {
            assertNull(fakeIds);
        }
    }
    @Test
    public void testGetMilestonesByAtp() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<MilestoneInfo> milestones = atpServiceValidation.getMilestonesByAtp("testAtpId1", callContext);
        
        assertNotNull(milestones);
        assertEquals(1, milestones.size());
        
        List<String> expectedIds = new ArrayList<String>();
        expectedIds.add("testId");
        
        // check that all the expected ids came back
        for(MilestoneInfo info : milestones) {
            expectedIds.remove(info.getKey());
        }
        
        assertTrue(expectedIds.isEmpty());
        
        List<MilestoneInfo> fakeMilestones = null;
        
        try {
            fakeMilestones = atpServiceValidation.getMilestonesByAtp("fakeKey", callContext);
            fail("Did not get a InvalidParameterException when expected");
        }
        catch(InvalidParameterException e) {
            assertNull(fakeMilestones);
        }
        
    }
    
    @Test
    public void testCreateAtpMilestoneRelation() throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        AtpMilestoneRelationInfo rel = new AtpMilestoneRelationInfo();
        
        rel.setAtpKey("testAtpId1");
        rel.setMilestoneKey("testId");
        rel.setEffectiveDate(new Date());
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2100);
        rel.setExpirationDate(cal.getTime());
        
        rel.setId("newRelId");
        rel.setStateKey(AtpServiceConstants.ATP_MILESTONE_RELATION_ACTIVE_STATE_KEY);
        rel.setTypeKey("kuali.atp.milestone.relation.owns");
        
        AtpMilestoneRelationInfo created = atpServiceValidation.createAtpMilestoneRelation(rel, callContext);
        
        assertNotNull(created);
        
        // attempt to get the newly-created relation
        AtpMilestoneRelationInfo retrieved = atpServiceValidation.getAtpMilestoneRelation("newRelId", callContext);
        
        assertEquals(retrieved.getId(), "newRelId");
        assertEquals(retrieved.getTypeKey(), "kuali.atp.milestone.relation.owns");
        assertEquals(retrieved.getStateKey(), AtpServiceConstants.ATP_MILESTONE_RELATION_ACTIVE_STATE_KEY);
        
        AtpMilestoneRelationInfo shouldBeNull = null;
        try {
            shouldBeNull = atpServiceValidation.createAtpMilestoneRelation(rel, callContext);
            fail("Did not get a AlreadyExistsException when expected");
        }
        catch(AlreadyExistsException e) {
            assertNull(shouldBeNull);
        }
    }
    @Ignore
    @Test
    public void testUpdateAtpMilestoneRelation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException {
        AtpMilestoneRelationInfo retrieved = atpServiceValidation.getAtpMilestoneRelation("newRelId", callContext);
        
        assertNotNull(retrieved);
        
        retrieved.setMilestoneKey("testId2");
        retrieved.setEffectiveDate(new Date());
        retrieved.setStateKey(AtpServiceConstants.ATP_MILESTONE_RELATION_ACTIVE_STATE_KEY);
        
        AtpMilestoneRelationInfo updated = atpServiceValidation.updateAtpMilestoneRelation("newRelId", retrieved, callContext);
        
        
        assertNotNull(updated);
        assertEquals(updated.getId(), retrieved.getId());
        
        // check that further retrieves have updated info
        AtpMilestoneRelationInfo freshRetrieved = atpServiceValidation.getAtpMilestoneRelation("newRelId", callContext);
        
        assertNotNull(freshRetrieved);
        assertEquals(freshRetrieved.getMilestoneKey(), "testId2");
        assertEquals(freshRetrieved.getStateKey(), AtpServiceConstants.ATP_MILESTONE_RELATION_ACTIVE_STATE_KEY);
        
        // check for expected DoesNotExistException
        AtpMilestoneRelationInfo fakeUpdated = null;
        try {
            fakeUpdated = atpServiceValidation.updateAtpMilestoneRelation("fakeKey", new AtpMilestoneRelationInfo(), callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeUpdated);
        }
    }
    
    @Test
    public void testDeleteAtpMilestoneRelation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = atpServiceValidation.deleteAtpMilestoneRelation("newRelId", callContext);
        
        assertTrue(status.getIsSuccess());
        
        StatusInfo noStatus = null;
        try {
            noStatus = atpServiceValidation.deleteMilestone("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(noStatus);
        }
        
        // ensure the delete prevents future gets
        AtpMilestoneRelationInfo shouldBeNull = null;
        try {
            shouldBeNull = atpServiceValidation.getAtpMilestoneRelation("newRelId", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
    }
    
    @Test
    public void testGetType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        try {
	        TypeInfo typeInfo = atpServiceValidation.getType(AtpServiceConstants.ATP_CAMPUS_CALENDAR_TYPE_KEY, callContext);
	        assertNotNull(typeInfo);
	        try {
		        typeInfo = atpServiceValidation.getType("totally.bogus.type.key", callContext);
		        fail("Did not receive DoesNotExistException when getting nonexistent TypeInfo");
	        } catch (DoesNotExistException dnee) { /* expected */ }
	    } catch (Exception e) {
	        fail(e.getMessage());
	    }
    }
    
    @Test
    public void testGetTypesByRefObjectURI() {
        try {
            List<TypeInfo> typeInfos = atpServiceValidation.getTypesByRefObjectURI(AtpServiceConstants.REF_OBJECT_URI_ATP, callContext);
            assertNotNull(typeInfos);
            assertEquals(30, typeInfos.size());
            
            typeInfos = atpServiceValidation.getTypesByRefObjectURI(AtpServiceConstants.REF_OBJECT_URI_MILESTONE, callContext);
            assertNotNull(typeInfos);
            assertEquals(4, typeInfos.size());
            
            typeInfos = atpServiceValidation.getTypesByRefObjectURI(AtpServiceConstants.REF_OBJECT_URI_ATP_MILESTONE_RELATION, callContext);
            assertNotNull(typeInfos);
            assertEquals(2, typeInfos.size());
            assertEquals(AtpServiceConstants.ATP_MILESTONE_RELATION_OWNS_TYPE_KEY, typeInfos.get(0).getKey());
            
            try {
	            typeInfos = atpServiceValidation.getTypesByRefObjectURI("totally.bogus.object.uri", callContext);
		        fail("Did not receive DoesNotExistException when getting TypeInfos for nonexistent refObjectURI");
	        } catch (DoesNotExistException dnee) { /* expected */ }
	    } catch (Exception e) {
	        fail(e.getMessage());
	    }
    }
    
    @Test
    public void testGetState()throws DoesNotExistException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException{
    	try{
    		StateInfo stateInfo = atpServiceValidation.getState(AtpServiceConstants.ATP_PROCESS_KEY, AtpServiceConstants.ATP_DRAFT_STATE_KEY, callContext);
    		assertNotNull(stateInfo);
    		assertEquals(stateInfo.getKey(), AtpServiceConstants.ATP_DRAFT_STATE_KEY);
    	 } catch (Exception e) {
 	        fail(e.getMessage());
 	    }
    }
    
    @Test
    public void testGetStatesByProcess()throws DoesNotExistException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException{
    	try{
    		List<StateInfo> stateInfos = atpServiceValidation.getStatesByProcess(AtpServiceConstants.ATP_PROCESS_KEY, callContext);
    		assertNotNull(stateInfos);
    		assertEquals(stateInfos.size(), 2);
    	 } catch (Exception e) {
 	        fail(e.getMessage());
 	    }   	
    }
    
    @Test
    public void testGetProcessByKey()throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
    	StateProcessInfo spInfo = atpServiceValidation.getProcessByKey(AtpServiceConstants.ATP_PROCESS_KEY, callContext);
    	assertNotNull(spInfo);
		assertEquals(spInfo.getKey(), AtpServiceConstants.ATP_PROCESS_KEY);    	
    }
    
	@Test
	public void testGetInitialValidStates()throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
		List<StateInfo> stateInfo = atpServiceValidation.getInitialValidStates(AtpServiceConstants.ATP_PROCESS_KEY, callContext);
		assertNotNull(stateInfo);
		assertEquals(stateInfo.size(), 1);
	}
	
	@Test
	public void testGetNextHappyState()throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
		StateInfo stateInfo = atpServiceValidation.getNextHappyState(AtpServiceConstants.ATP_PROCESS_KEY, AtpServiceConstants.ATP_DRAFT_STATE_KEY, callContext);
		assertNotNull(stateInfo);
		assertEquals(stateInfo.getKey(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
	}
	
	@Test
	public void testValidateAtpAtpRelation() throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
    	AtpAtpRelationInfo atpRel = new AtpAtpRelationInfo();
        atpRel.setId(UUIDHelper.genStringUUID());
        atpRel.setAtpKey("testAtp1");
        atpRel.setRelatedAtpKey("testAtp2");
        atpRel.setTypeKey(AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY);
        atpRel.setStateKey(AtpServiceConstants.ATP_ATP_RELATION_ACTIVE_STATE_KEY);
        atpRel.setEffectiveDate(new Date());

        try{
        	List<ValidationResultInfo> vri= atpServiceValidation.validateAtpAtpRelation("FULL_VALIDATION", atpRel, callContext);
        	assertTrue(vri.isEmpty());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }   
	}
	
	@Test
	public void testCreatAtpAtpRelation() throws AlreadyExistsException, InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
    	AtpAtpRelationInfo atpRel = new AtpAtpRelationInfo();
        atpRel.setId(UUIDHelper.genStringUUID());
        atpRel.setAtpKey("testAtpId1");
        atpRel.setRelatedAtpKey("testAtpId2");
        atpRel.setTypeKey(AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY);
        atpRel.setStateKey(AtpServiceConstants.ATP_ATP_RELATION_ACTIVE_STATE_KEY);
        atpRel.setEffectiveDate(new Date());

        try{
        	atpServiceValidation.createAtpAtpRelation(atpRel, callContext);
        } catch (AlreadyExistsException ex){}
        
        try{
            AtpInfo atpInfo = new AtpInfo();
            atpInfo.setKey("testAtpId1-newCC");
            atpInfo.setName("testAtpId1 to new campus calendar");
            atpInfo.setTypeKey(AtpServiceConstants.ATP_CAMPUS_CALENDAR_TYPE_KEY);
            atpInfo.setStateKey("kuali.atp.state.Draft");
            atpInfo.setStartDate(Calendar.getInstance().getTime());
            atpInfo.setEndDate(Calendar.getInstance().getTime());
            AtpInfo cc = null;
            cc = atpServiceValidation.createAtp("testAtpId1-newCC", atpInfo, callContext);
            assertNotNull(cc);
            
            atpRel.setRelatedAtpKey("testAtpId1-newCC");
        	AtpAtpRelationInfo created = atpServiceValidation.createAtpAtpRelation(atpRel, callContext);
        	assertNotNull(created);
        	
        	AtpAtpRelationInfo retrieved = atpServiceValidation.getAtpAtpRelation(created.getId(), callContext);
        	assertEquals(retrieved.getAtpKey(), "testAtpId1");
        	assertEquals(retrieved.getRelatedAtpKey(), "testAtpId1-newCC");
        	assertEquals(retrieved.getTypeKey(), AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY);
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }   
	}
	
	@Test
    public void testGetDataDictionaryEntryKeys() throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        List<String> results = atpServiceValidation.getDataDictionaryEntryKeys(callContext);
        
        assertNotNull(results);
        assertTrue(!results.isEmpty());
        
        assertTrue(results.contains("http://student.kuali.org/wsdl/atp/AtpInfo"));
    }
    
    @Test
    public void testGetDataDictionaryEntry() throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
        DictionaryEntryInfo value = atpServiceValidation.getDataDictionaryEntry("http://student.kuali.org/wsdl/atp/AtpInfo", callContext);
        
        assertNotNull(value);
        
        DictionaryEntryInfo fakeEntry = null;
        try {
            fakeEntry = atpServiceValidation.getDataDictionaryEntry("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeEntry);
        }
    }
}
