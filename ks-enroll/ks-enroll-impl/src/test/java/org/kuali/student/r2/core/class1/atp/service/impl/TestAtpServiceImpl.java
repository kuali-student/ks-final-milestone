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
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.AtpMilestoneRelationInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

@Daos( { @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.AtpDao", testSqlFile = "classpath:ks-atp.sql"),
    @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.AtpTypeDao"),
    @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.AtpStateDao"),
    @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.AtpRichTextDao"),
    @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.AtpAtpRelationDao"),
    @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.AtpAtpRelationTypeDao"), 
    @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.MilestoneTypeDao"),
    @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.MilestoneDao"),
    @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.AtpMilestoneRelationDao"),
    @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.AtpMilestoneRelationTypeDao"),
    @Dao(value = "org.kuali.student.r2.common.dao.TypeTypeRelationDao")} )
@PersistenceFileLocation("classpath:META-INF/acal-persistence.xml")
public class TestAtpServiceImpl extends AbstractServiceTest {
    @Client(value = "org.kuali.student.r2.core.class1.atp.service.impl.AtpServiceImpl")
    
    public AtpService atpService;
    public static String principalId = "123";
    public ContextInfo callContext = ContextInfo.newInstance();
    
    @Before
    public void setUp() {
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
    }
     
    @Test
    public void testGetAtp() throws DoesNotExistException, InvalidParameterException,
								    MissingParameterException, OperationFailedException, PermissionDeniedException {
		try{
			AtpInfo atpInfo = atpService.getAtp("testAtpId1", callContext);
			assertNotNull(atpInfo);
			assertEquals("testAtpId1", atpInfo.getKey());
			assertEquals("testAtp1", atpInfo.getName());
			assertEquals("Desc 101", atpInfo.getDescr().getPlain());
			assertEquals("kuali.atp.state.Draft", atpInfo.getStateKey());
			assertEquals("kuali.atp.type.AcademicCalendar", atpInfo.getTypeKey());
			try {
			    atpService.getAtp("totallyBogusAtpId999", callContext);
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
            created = atpService.createAtp("newId", atpInfo, callContext);
            assertNotNull(created);
            assertEquals("newId", created.getKey());
        } catch (AlreadyExistsException e) {
            fail(e.getMessage());
        } catch (DataValidationErrorException e) {
            fail(e.getMessage());
        }
        
        // test read
		AtpInfo fetched = atpService.getAtp("testAtpId1", callContext);
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
	        updated = atpService.updateAtp(fetched.getKey(), modified, callContext);
        } catch (Exception e) {
            fail("Exception thrown when updating ATP: " + e.getMessage());
        }
        assertNotNull(updated);
        assertEquals(atpNameOrig + "updated", updated.getName());
        
        // test delete
        atpInfo = atpService.getAtp("testDeleteAtpId1", callContext);
        assertNotNull(atpInfo);
        assertEquals("testDeleteAtpId1", atpInfo.getKey());
        
        try{
	        atpService.deleteAtp("testDeleteAtpId1", callContext);
	        try {
		        atpService.getAtp("testDeleteAtpId1", callContext);
		        fail("Did not receive DoesNotExistException when attempting to get already-deleted AtpEntity");
	        } catch (DoesNotExistException dnee) {}
        } catch (Exception e){
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testUpdateAtp()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException {
        AtpInfo atpInfo = atpService.getAtp("testAtpId1", callContext);
        assertNotNull(atpInfo);
        assertEquals("testAtpId1", atpInfo.getKey());
        
        String atpNameOrig = atpInfo.getName();
        AtpInfo modified = AtpInfo.getInstance(atpInfo);
        modified.setName(atpNameOrig + "updated");
        AtpInfo updated = atpService.updateAtp(atpInfo.getKey(), modified, callContext);
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
            AtpInfo created = atpService.createAtp("newId2", atpInfo, callContext);
            assertNotNull(created);
            assertEquals("newId2", created.getKey());
        } catch (Exception e) {
            fail(e.getMessage());
        }
        
        // attempt to get
        AtpInfo retrieved = atpService.getAtp("newId2", callContext);
        
        assertNotNull(retrieved);
        assertEquals("newId2", retrieved.getKey());
    }
   
    @Test
    public void testDeleteAtp() throws DoesNotExistException,
    InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        AtpInfo atpInfo = atpService.getAtp("testDeleteAtpId2", callContext);
        assertNotNull(atpInfo);
        assertEquals("testDeleteAtpId2", atpInfo.getKey());
        
        try{
	        atpService.deleteAtp("testDeleteAtpId2", callContext);
	        try {
		        atpService.getAtp("testDeleteAtpId2", callContext);
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
            MilestoneInfo created = atpService.createMilestone("newId", milestone, callContext);
            assertNotNull(created);
            assertEquals("newId", created.getKey());
            assertEquals("testCreate", created.getName());
        }
        catch(AlreadyExistsException e) {
            fail(e.getMessage());
        }
        
        // try to get the just-created milestone
        MilestoneInfo found = atpService.getMilestone("newId", callContext);
        assertNotNull(found);
        
        // ensure we cannot create another of the same id
        MilestoneInfo dupeCreated = null;
        try {
            dupeCreated = atpService.createMilestone("newId", milestone, callContext);
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
            MilestoneInfo created = atpService.createMilestone("newId2", milestone, callContext);
            assertNotNull(created);
            assertEquals("newId2", created.getKey());
            assertEquals("testCreate", created.getName());
        }
        catch(AlreadyExistsException e) {
            fail(e.getMessage());
        }
        
        MilestoneInfo updateData = atpService.getMilestone("newId2", callContext);
        
        String updatedName = "updated " + updateData.getName();
        
        updateData.setName(updatedName);
        
        MilestoneInfo updated = atpService.updateMilestone("newId2", updateData, callContext);
        assertNotNull(updated);
        assertEquals(updated.getKey(), "newId2");
        assertEquals(updated.getName(), updatedName);
        
        // now fetch the updated milestone fresh, and check fields
        updated = atpService.getMilestone("newId2", callContext);
        assertNotNull(updated);
        assertEquals(updated.getKey(), "newId2");
        assertEquals(updated.getName(), updatedName);
        
        
        MilestoneInfo shouldBeNull = null;
        try {
            shouldBeNull = atpService.updateMilestone("fakeKey", updated, callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
    }
    
    @Test
    public void testDeleteMilestone() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException {
        StatusInfo status = atpService.deleteMilestone("testDeleteId", callContext);
        
        assertTrue(status.getIsSuccess());
        
        StatusInfo noStatus = null;
        try {
            noStatus = atpService.deleteMilestone("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(noStatus);
        }
        
        // ensure the delete prevents future gets
        MilestoneInfo shouldBeNull = null;
        try {
            shouldBeNull = atpService.getMilestone("testDeleteId", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
        
    }
    
    @Test
    public void testGetMilestone() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MilestoneInfo milestoneInfo = atpService.getMilestone("testId", callContext);
        assertNotNull(milestoneInfo);
        assertEquals("testId", milestoneInfo.getKey());
        assertEquals("testId", milestoneInfo.getName());
        assertEquals("Desc 105", milestoneInfo.getDescr().getPlain());
        assertEquals(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY, milestoneInfo.getStateKey());
        assertEquals("kuali.atp.milestone.AdvanceRegistrationPeriod", milestoneInfo.getTypeKey());
        
        MilestoneInfo fakeMilestone = null;
        try {
            fakeMilestone = atpService.getMilestone("fakeKey", callContext);
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
        
        List<MilestoneInfo> milestones = atpService.getMilestonesByKeyList(milestoneKeys, callContext);
        
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
            shouldBeNull = atpService.getMilestonesByKeyList(fakeKeys, callContext);
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
        
        List<MilestoneInfo> milestones = atpService.getMilestonesByDates(startDate, endDate, callContext);
        
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
        
        milestones = atpService.getMilestonesByDates(startDate, endDate, callContext);
        
        assertTrue(milestones == null || milestones.isEmpty());
    }
    
    @Test
    public void testGetMilestoneKeysByType() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        String expectedMilestoneType = "kuali.atp.milestone.RegistrationPeriod";
        
        List<String> milestoneKeys = atpService.getMilestoneKeysByType(expectedMilestoneType, callContext);
        
        assertTrue(milestoneKeys.contains("testId2"));
        
        String expectedEmptyMilestoneType = "kuali.atp.milestone.GradesDue";
        
        milestoneKeys = atpService.getMilestoneKeysByType(expectedEmptyMilestoneType, callContext);
        
        assertTrue(milestoneKeys == null || milestoneKeys.isEmpty());
        
        String fakeMilestoneType = "fakeTypeKey";
        
        List<String> shouldBeNull = null;
        try {
            shouldBeNull = atpService.getMilestoneKeysByType(fakeMilestoneType, callContext);
            fail("Did not get a InvalidParameterException when expected");
        }
        catch(InvalidParameterException e) {
            assertNull(shouldBeNull);
        }
    }
    
    @Test
    public void testGetAtpMilestoneRelation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AtpMilestoneRelationInfo relation = atpService.getAtpMilestoneRelation("ATPMSTONEREL-1", callContext);
        assertEquals(relation.getMilestoneKey(), "testId");
        assertEquals(relation.getAtpKey(), "testAtpId1");
        assertEquals(relation.getTypeKey(), "kuali.atp.milestone.relation.owns");
        assertEquals(relation.getStateKey(), AtpServiceConstants.ATP_MILESTONE_RELATION_ACTIVE_STATE_KEY);
        
        AtpMilestoneRelationInfo fakeRelation = null;
        try {
            fakeRelation = atpService.getAtpMilestoneRelation("fakeKey", callContext);
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
        
        List<AtpMilestoneRelationInfo> results = atpService.getAtpMilestoneRelationsByIdList(idList, callContext);
        
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
            fakeRelations = atpService.getAtpMilestoneRelationsByIdList(idList, callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeRelations);
        }
    }
    
    @Test
    public void testGetAtpMilestoneRelationsByAtp() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AtpMilestoneRelationInfo> results = atpService.getAtpMilestoneRelationsByAtp("testAtpId1", callContext);
        
        assertNotNull(results);
        assertEquals(results.size(), 1);
        
        AtpMilestoneRelationInfo rel = results.get(0);
        assertEquals(rel.getAtpKey(), "testAtpId1");
        assertEquals(rel.getMilestoneKey(), "testId");
        assertEquals(rel.getTypeKey(), "kuali.atp.milestone.relation.owns");
        
        List<AtpMilestoneRelationInfo> fakeRelations = null;
        try {
            fakeRelations = atpService.getAtpMilestoneRelationsByAtp("fakeAtp", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeRelations);
        }
    }
    
    @Test
    public void testGetAtpMilestoneRelationsByMilestone() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AtpMilestoneRelationInfo> results = atpService.getAtpMilestoneRelationsByMilestone("testId2", callContext);
        
        assertNotNull(results);
        assertEquals(results.size(), 1);
        
        AtpMilestoneRelationInfo rel = results.get(0);
        assertEquals(rel.getAtpKey(), "testAtpId2");
        assertEquals(rel.getMilestoneKey(), "testId2");
        assertEquals(rel.getTypeKey(), "kuali.atp.milestone.relation.owns");
        
        List<AtpMilestoneRelationInfo> fakeRelations = null;
        try {
            fakeRelations = atpService.getAtpMilestoneRelationsByMilestone("fakeAtp", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeRelations);
        }
    }
    
    @Test
    public void testGetAtpMilestoneRelationIdsByType() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> results = atpService.getAtpMilestoneRelationIdsByType("kuali.atp.milestone.relation.owns", callContext);
        
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
            fakeIds = atpService.getAtpMilestoneRelationIdsByType("fakeType", callContext);
            fail("Did not get a InvalidParameterException when expected");
        }
        catch(InvalidParameterException e) {
            assertNull(fakeIds);
        }
    }
    
    @Test
    public void testGetMilestonesByAtp() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<MilestoneInfo> milestones = atpService.getMilestonesByAtp("testAtpId1", callContext);
        
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
            fakeMilestones = atpService.getMilestonesByAtp("fakeKey", callContext);
            fail("Did not get a InvalidParameterException when expected");
        }
        catch(InvalidParameterException e) {
            assertNull(fakeMilestones);
        }
        
    }
    
    @Test
    public void testCreateAtpMilestoneRelation() throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DataValidationErrorException {
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
        
        AtpMilestoneRelationInfo created = atpService.createAtpMilestoneRelation(rel, callContext);
        
        assertNotNull(created);
        
        // attempt to get the newly-created relation
        AtpMilestoneRelationInfo retrieved = atpService.getAtpMilestoneRelation("newRelId", callContext);
        
        assertEquals(retrieved.getId(), "newRelId");
        assertEquals(retrieved.getTypeKey(), "kuali.atp.milestone.relation.owns");
        assertEquals(retrieved.getStateKey(), AtpServiceConstants.ATP_MILESTONE_RELATION_ACTIVE_STATE_KEY);
        
        AtpMilestoneRelationInfo shouldBeNull = null;
        try {
            shouldBeNull = atpService.createAtpMilestoneRelation(rel, callContext);
            fail("Did not get a AlreadyExistsException when expected");
        }
        catch(AlreadyExistsException e) {
            assertNull(shouldBeNull);
        }
    }
    
    @Test
    public void testUpdateAtpMilestoneRelation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException {
        AtpMilestoneRelationInfo retrieved = atpService.getAtpMilestoneRelation("newRelId", callContext);
        
        assertNotNull(retrieved);
        
        retrieved.setMilestoneKey("testId2");
        retrieved.setEffectiveDate(new Date());
        retrieved.setStateKey(AtpServiceConstants.ATP_MILESTONE_RELATION_ACTIVE_STATE_KEY);
        
        AtpMilestoneRelationInfo updated = atpService.updateAtpMilestoneRelation("newRelId", retrieved, callContext);
        
        
        assertNotNull(updated);
        assertEquals(updated.getId(), retrieved.getId());
        
        // check that further retrieves have updated info
        AtpMilestoneRelationInfo freshRetrieved = atpService.getAtpMilestoneRelation("newRelId", callContext);
        
        assertNotNull(freshRetrieved);
        assertEquals(freshRetrieved.getMilestoneKey(), "testId2");
        assertEquals(freshRetrieved.getStateKey(), AtpServiceConstants.ATP_MILESTONE_RELATION_ACTIVE_STATE_KEY);
        
        // check for expected DoesNotExistException
        AtpMilestoneRelationInfo fakeUpdated = null;
        try {
            fakeUpdated = atpService.updateAtpMilestoneRelation("fakeKey", new AtpMilestoneRelationInfo(), callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeUpdated);
        }
    }
    
    @Test
    public void testDeleteAtpMilestoneRelation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = atpService.deleteAtpMilestoneRelation("newRelId", callContext);
        
        assertTrue(status.getIsSuccess());
        
        StatusInfo noStatus = null;
        try {
            noStatus = atpService.deleteMilestone("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(noStatus);
        }
        
        // ensure the delete prevents future gets
        AtpMilestoneRelationInfo shouldBeNull = null;
        try {
            shouldBeNull = atpService.getAtpMilestoneRelation("newRelId", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
    }
    
    @Test
    public void testGetType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        try {
	        TypeInfo typeInfo = atpService.getType(AtpServiceConstants.ATP_CAMPUS_CALENDAR_TYPE_KEY, callContext);
	        assertNotNull(typeInfo);
	        try {
		        typeInfo = atpService.getType("totally.bogus.type.key", callContext);
		        fail("Did not receive DoesNotExistException when getting nonexistent TypeInfo");
	        } catch (DoesNotExistException dnee) { /* expected */ }
	    } catch (Exception e) {
	        fail(e.getMessage());
	    }
    }
    
    @Test
    public void testGetTypeRelationsByOwnerType() {
        try {
            List<TypeTypeRelationInfo> typeInfos = atpService.getTypeRelationsByOwnerType(AtpServiceConstants.ATP_AY_TYPE_KEY, TypeServiceConstants.TYPE_TYPE_RELATION_CONTAINS_TYPE_KEY, callContext);
            assertNotNull(typeInfos);
            assertEquals(3, typeInfos.size());
	    } catch (Exception e) {
	        fail(e.getMessage());
	    }
    }
    
    @Test
    public void testGetTypesByRefObjectURI() {
        try {
            List<TypeInfo> typeInfos = atpService.getTypesByRefObjectURI(AtpServiceConstants.REF_OBJECT_URI_ATP, callContext);
            assertNotNull(typeInfos);
            assertEquals(30, typeInfos.size());
            
            typeInfos = atpService.getTypesByRefObjectURI(AtpServiceConstants.REF_OBJECT_URI_MILESTONE, callContext);
            assertNotNull(typeInfos);
            assertEquals(4, typeInfos.size());
            
            typeInfos = atpService.getTypesByRefObjectURI(AtpServiceConstants.REF_OBJECT_URI_ATP_MILESTONE_RELATION, callContext);
            assertNotNull(typeInfos);
            assertEquals(2, typeInfos.size());
            assertTrue(AtpServiceConstants.ATP_MILESTONE_RELATION_OWNS_TYPE_KEY.equals(typeInfos.get(0).getKey()) || AtpServiceConstants.ATP_MILESTONE_RELATION_REUSES_TYPE_KEY.equals(typeInfos.get(1).getKey()));
            
            try {
	            typeInfos = atpService.getTypesByRefObjectURI("totally.bogus.object.uri", callContext);
		        fail("Did not receive DoesNotExistException when getting TypeInfos for nonexistent refObjectURI");
	        } catch (DoesNotExistException dnee) { /* expected */ }
	    } catch (Exception e) {
	        fail(e.getMessage());
	    }
    }
    
    @Test
    public void testGetAtpAtpRelationsByAtpAndRelationType() {
        try {
            List<AtpAtpRelationInfo> aaRelInfos = atpService.getAtpAtpRelationsByAtpAndRelationType("testTermId1", AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, callContext);
            assertEquals(1, aaRelInfos.size());
            assertEquals("testTermId2", aaRelInfos.get(0).getRelatedAtpKey());
            try { // should throw DNEE for bogus atpKey
	            aaRelInfos = atpService.getAtpAtpRelationsByAtpAndRelationType("totallyBogusAtpIdJustMadeUpForTesting", AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, callContext);
	            fail("atpService.getAtpAtpRelationsByAtpAndRelationType() did not throw expected DoesNotExistException for nonexistent Atp ID");
            } catch (DoesNotExistException dnee) { /* expected */ }
            // should return empty list for bogus relation type
            aaRelInfos = atpService.getAtpAtpRelationsByAtpAndRelationType("testAtpId1", "totallyBogusRelationTypeJustMadeUpForTesting", callContext);
            // sigh. service is returning an empty list, but client seems to get a null. have asked KSDevs chat about how to fix; not finding a solution via my google-fu
            assertTrue(null == aaRelInfos || aaRelInfos.size() == 0);
            // assertEquals(0, aaRelInfos.size());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testGetAllowedTypesForType() {
        try {
            List<TypeInfo> typeInfos = atpService.getAllowedTypesForType(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, AtpServiceConstants.REF_OBJECT_URI_ATP, callContext);
            assertEquals(6, typeInfos.size());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testGetAtpsByKeyList() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> atpKeys = new ArrayList<String>();
        atpKeys.addAll(Arrays.asList("testAtpId1", "testAtpId2"));
        
        List<AtpInfo> atps = atpService.getAtpsByKeyList(atpKeys, callContext);
        
        assertNotNull(atps);
        assertEquals(atpKeys.size(), atps.size());
        
        // check that all the expected ids came back
        for(AtpInfo info : atps) {
            atpKeys.remove(info.getKey());
        }
        
        assertTrue(atpKeys.isEmpty());
        
        // now make sure an exception is thrown for any not found keys
        
        List<String> fakeKeys = Arrays.asList("testAtpId1", "fakeKey1", "fakeKey2");
        
        List<AtpInfo> shouldBeNull = null;
        try {
            shouldBeNull = atpService.getAtpsByKeyList(fakeKeys, callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
    }
    
    @Test
    public void testGetAtpKeysByType() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        String expectedAtpType = AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY;
        
        List<String> atpKeys = atpService.getAtpKeysByType(expectedAtpType, callContext);
        
        assertTrue(atpKeys.contains("testAtpId1"));
        assertTrue(atpKeys.contains("termRelationTestingAcal1"));
        
        String expectedEmptyAtpType = AtpServiceConstants.ATP_SESSION_G2_TYPE_KEY;
        
        atpKeys = atpService.getAtpKeysByType(expectedEmptyAtpType, callContext);
        
        assertTrue(atpKeys == null || atpKeys.isEmpty());
        
        String fakeAtpType = "fakeTypeKey";
        
        List<String> shouldBeNull = null;
        try {
            shouldBeNull = atpService.getAtpKeysByType(fakeAtpType, callContext);
            fail("Did not get a InvalidParameterException when expected");
        }
        catch(InvalidParameterException e) {
            assertNull(shouldBeNull);
        }
    }
}
