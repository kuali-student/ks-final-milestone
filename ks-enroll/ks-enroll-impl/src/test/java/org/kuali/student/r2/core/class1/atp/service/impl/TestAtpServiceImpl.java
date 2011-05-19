package org.kuali.student.r2.core.class1.atp.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Daos( { @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.AtpDao", testSqlFile = "classpath:ks-atp.sql"),
    @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.AtpTypeDao"),
    @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.AtpStateDao"),
    @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.AtpRichTextDao"),
    @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.AtpAtpRelationDao"),
    @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.AtpAtpRelationTypeDao"), 
    @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.MilestoneTypeDao"),
    @Dao(value = "org.kuali.student.r2.core.class1.atp.dao.MilestoneDao")} )
@PersistenceFileLocation("classpath:META-INF/persistence_jta.xml")
public class TestAtpServiceImpl extends AbstractServiceTest{
    @Client(value = "org.kuali.student.r2.core.class1.atp.service.impl.AtpServiceImpl")
    
    public AtpService atpService;
    public ApplicationContext appContext;
    public static String principalId = "123";
    public ContextInfo callContext = ContextInfo.newInstance();
    
    @Before
    public void setUp() {
        principalId = "123";
        appContext = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
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
			assertEquals("Desc", atpInfo.getDescr().getPlain());
			assertEquals("kuali.atp.state.Draft", atpInfo.getStateKey());
           assertEquals("kuali.atp.type.AcademicCalendar", atpInfo.getTypeKey());
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
        atpInfo.setStartDate(Calendar.getInstance().getTime());
        atpInfo.setEndDate(Calendar.getInstance().getTime());
        AtpInfo created = null;
        try {
            created = atpService.createAtp("newId", atpInfo, callContext);
            assertNotNull(created);
            assertEquals("newId", created.getKey());
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        }
        
        // test read
		AtpInfo fetched = atpService.getAtp("testAtpId1", callContext);
		assertNotNull(fetched);
		assertEquals("testAtpId1", fetched.getKey());
		assertEquals("testAtp1", fetched.getName());
		assertEquals("Desc", fetched.getDescr().getPlain());
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
        atpInfo = atpService.getAtp("testAtpId2", callContext);
        assertNotNull(atpInfo);
        assertEquals("testAtpId2", atpInfo.getKey());
        
        try{
	        atpService.deleteAtp("testAtpId2", callContext);
	        AtpInfo deleted = atpService.getAtp("testAtpId1", callContext);
	        assertEquals(deleted, null);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    @Test
    //TODO: fix locking issue
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
        
        // attempt to get
        AtpInfo retrieved = atpService.getAtp("newId", callContext);
        
        assertNotNull(retrieved);
        assertEquals("newId", retrieved.getKey());
    }
   
    @Test
    public void testDeleteAtp() throws DoesNotExistException,
    InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        AtpInfo atpInfo = atpService.getAtp("testAtpId2", callContext);
        assertNotNull(atpInfo);
        assertEquals("testAtpId2", atpInfo.getKey());
        
        try{
        atpService.deleteAtp("testAtpId2", callContext);
        AtpInfo deleted = atpService.getAtp("testAtpId1", callContext);
        assertEquals(deleted, null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    @Test
    public void testCreateMilestone() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        MilestoneInfo milestone = MilestoneInfo.newInstance();
        milestone.setKey("newId");
        milestone.setName("testCreate");
        milestone.setStartDate(new Date());
        milestone.setDateRange(false);
        milestone.setAllDay(true);
        milestone.setStateKey("kuali.atp.state.Draft");
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
            e.printStackTrace();
            fail("Got an AlreadyExistsException when testing create");
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
        MilestoneInfo milestone = MilestoneInfo.newInstance();
        milestone.setKey("newId2");
        milestone.setName("testCreate");
        milestone.setStartDate(new Date());
        milestone.setDateRange(false);
        milestone.setAllDay(true);
        milestone.setStateKey("kuali.atp.state.Draft");
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
            e.printStackTrace();
            fail("Got an AlreadyExistsException when testing create");
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
    @Ignore
    public void testDeleteMilestone() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = atpService.deleteMilestone("testDeleteId", callContext);
        assertTrue(status.isSuccess());
        
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
            atpService.getMilestone("testDeleteId", callContext);
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
        assertEquals("Desc", milestoneInfo.getDescr().getPlain());
        assertEquals("kuali.atp.state.Draft", milestoneInfo.getStateKey());
        assertEquals("kuali.atp.milestone.AdvanceRegistrationPeriod", milestoneInfo.getTypeKey());
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
    @Ignore
    public void testValidateMilestone() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        // validating an existing milestone should return an empty list
        MilestoneInfo existingMilestone = atpService.getMilestone("testId", callContext);
        
        List<ValidationResultInfo> existingResults = atpService.validateMilestone("FULL_VALIDATION", existingMilestone, callContext);
        
        assertTrue(existingResults.isEmpty());
        
        MilestoneInfo invalid = MilestoneInfo.newInstance();
        
        List<ValidationResultInfo> invalidResults = atpService.validateMilestone("FULL_VALIDATION", invalid, callContext);
        
        assertTrue(!invalidResults.isEmpty());        
    }
}
