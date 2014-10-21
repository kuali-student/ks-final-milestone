package org.kuali.student.r2.core.atp.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
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
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:atp-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional(readOnly=false, noRollbackFor= {DoesNotExistException.class}, rollbackFor= {Throwable.class})
public class TestAtpServiceImpl implements ApplicationContextAware {

    @Resource(name = "atpServiceAuthDecorator")
    public AtpService atpService;
    
    @Resource (name="entityManager")
    private EntityManager em;
    
    public static String principalId = "123";
    public ContextInfo callContext = null;


    private ApplicationContext applicationContext;

    
    /* (non-Javadoc)
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
                this.applicationContext = applicationContext;
        
    }

    @Before
    public void setUp() throws Exception {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        loadData();
    }

    @Test
    public void testAtpServiceValidationSetup() {
        assertNotNull(atpService);
    }

    private void loadData() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException, VersionMismatchException, AlreadyExistsException {
        AtpTestDataLoader loader = new AtpTestDataLoader (this.atpService);
        loader.loadData();
    }

    @Test
    public void testGetAtp() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        AtpInfo atpInfo = atpService.getAtp("testAtpId1", callContext);
        assertNotNull(atpInfo);
        assertEquals("testAtpId1", atpInfo.getId());
        assertEquals("testAtp1", atpInfo.getName());
        assertEquals("Desc 101", atpInfo.getDescr().getPlain());
        assertEquals(AtpServiceConstants.ATP_DRAFT_STATE_KEY, atpInfo.getStateKey());
        assertEquals(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, atpInfo.getTypeKey());
        try {
            atpService.getAtp("totallyBogusAtpId999", callContext);
            fail("AtpService did not throw DoesNotExistException on getAtp() of nonexistent ATP");
        } catch (DoesNotExistException dnee) {
            assertNotNull(dnee.getMessage());
            assertEquals("totallyBogusAtpId999", dnee.getMessage());
        }
    }

    @Test
    public void testAtpVersionMismatch () throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException, ReadOnlyException {
        
     // test create
        AtpInfo atpInfo = new AtpInfo();
        atpInfo.setName("newId");
        atpInfo.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        atpInfo.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        atpInfo.setStartDate(Calendar.getInstance().getTime());
        RichTextInfo rt = new RichTextInfo();
        rt.setPlain("TestDesc1");
        atpInfo.setDescr(rt);
        atpInfo.setEndDate(Calendar.getInstance().getTime());
        AtpInfo created = null;
        created = atpService.createAtp(atpInfo.getTypeKey(), atpInfo, callContext);
        assertNotNull(created);
        assertNotNull(created.getId());

        
        String atpId = created.getId();
        
        AtpInfo atp = atpService.getAtp(atpId , callContext);
        
        Assert.assertTrue(atp.getMeta().getVersionInd().equals("0"));
        
        atp.setCode("test-code");
        
        AtpInfo updated = atpService.updateAtp(atpId, new AtpInfo(atp), callContext);
        
        Assert.assertNotEquals(atp.getMeta().getVersionInd(), updated.getMeta().getVersionInd());
        
        // test version mismatch detection
        try {
            atpService.updateAtp(atpId, new AtpInfo(atp), callContext);
            fail("VersionMismatchException should have been thrown");
        } catch (VersionMismatchException e) {
            assertNotNull(e.getMessage());
            assertEquals("Failed for entity.id = " + atpId, e.getMessage());
        }
    }
    
    private String testAtpCrudBase () throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
     // test create
        AtpInfo atpInfo = new AtpInfo();
        atpInfo.setName("newId");
        atpInfo.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        atpInfo.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        atpInfo.setStartDate(Calendar.getInstance().getTime());
        RichTextInfo rt = new RichTextInfo();
        rt.setPlain("TestDesc1");
        atpInfo.setDescr(rt);
        atpInfo.setEndDate(Calendar.getInstance().getTime());
        AtpInfo created = null;
        created = atpService.createAtp(atpInfo.getTypeKey(), atpInfo, callContext);
        assertNotNull(created);
        assertNotNull(created.getId());

        // test read
        AtpInfo fetched = atpService.getAtp(created.getId(), callContext);
        assertNotNull(fetched);
        assertEquals(created.getId(), fetched.getId());
        assertEquals("newId", fetched.getName());
        assertEquals("TestDesc1", fetched.getDescr().getPlain());
        assertEquals(AtpServiceConstants.ATP_DRAFT_STATE_KEY, fetched.getStateKey());
        assertEquals(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, fetched.getTypeKey());

        // test update
        String atpNameOrig = fetched.getName();
        AtpInfo modified = new AtpInfo(fetched);
        modified.setName(atpNameOrig + "updated");
        AtpInfo updated = null;
        updated = atpService.updateAtp(fetched.getId(), modified, callContext);
        assertNotNull(updated);
        assertEquals(atpNameOrig + "updated", updated.getName());
        
        // test delete
        atpInfo = atpService.getAtp("testDeleteAtpId1", callContext);
        assertNotNull(atpInfo);
        assertEquals("testDeleteAtpId1", atpInfo.getId());

        atpService.deleteAtp("testDeleteAtpId1", callContext);
        try {
            atpService.getAtp("testDeleteAtpId1", callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted AtpEntity");
        } catch (DoesNotExistException dnee) {
            assertNotNull(dnee.getMessage());
            assertEquals("testDeleteAtpId1", dnee.getMessage());
        }
        // undo the update done above
        updated = atpService.getAtp(updated.getId(), callContext);
        updated.setName(atpNameOrig);

        updated = atpService.updateAtp(updated.getId(), updated, callContext);
        assertNotNull(updated);
        assertEquals(atpNameOrig, updated.getName());
        
        return updated.getId();
    }
    
    @Test
    public void testAtpCrud() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, VersionMismatchException, ReadOnlyException {
        testAtpCrudBase();
    }

    @Test
    public void testUpdateAtp() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, VersionMismatchException, ReadOnlyException {
        AtpInfo atpInfo = atpService.getAtp("testAtpId1", callContext);
        assertNotNull(atpInfo);
        assertEquals("testAtpId1", atpInfo.getId());

        String atpNameOrig = atpInfo.getName();
        AtpInfo modified = new AtpInfo(atpInfo);
        modified.setName(atpNameOrig + "updated");
        AtpInfo updated = null;
        updated = atpService.updateAtp(atpInfo.getId(), modified, callContext);
        assertNotNull(updated);
        assertEquals(atpNameOrig + "updated", updated.getName());
    }

    @Test
    public void testCreateAtp() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException {
        String atpId = null;
        AtpInfo atpInfo = new AtpInfo();
        atpInfo.setName("newId2");
        atpInfo.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        atpInfo.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        atpInfo.setStartDate(Calendar.getInstance().getTime());
        atpInfo.setEndDate(Calendar.getInstance().getTime());
        RichTextInfo rti = new RichTextInfo();
        rti.setPlain("Test ATP for create test.");
        atpInfo.setDescr(rti);
        AtpInfo created = atpService.createAtp(atpInfo.getTypeKey(), atpInfo, callContext);
        atpId = created.getId();
        assertNotNull(created);
        assertNotNull(created.getId());

        // attempt to get
        AtpInfo retrieved = atpService.getAtp(atpId, callContext);

        assertNotNull(retrieved);
        assertNotNull(retrieved.getId());
    }

    @Test
    public void testDeleteAtp() throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AtpInfo atpInfo = atpService.getAtp("testDeleteAtpId2", callContext);
        assertNotNull(atpInfo);
        assertEquals("testDeleteAtpId2", atpInfo.getId());

        atpService.deleteAtp("testDeleteAtpId2", callContext);
        try {
            atpService.getAtp("testDeleteAtpId2", callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted AtpEntity");
        } catch (DoesNotExistException dnee) {
            assertNotNull(dnee.getMessage());
            assertEquals("testDeleteAtpId2", dnee.getMessage());
        }
    }

    @Test
    public void testGetAtpsByDate()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2011, 0, 1);
        List<AtpInfo> atpInfos = atpService.getAtpsByDate(cal.getTime(), callContext);
        assertNotNull("getAtpsByDate() should return a list, not null", atpInfos);

        List<String> requiredKeys = new ArrayList<String>(Arrays.asList(
                "testAtpId1", "testAtpId2", "testTermId1", "testTermId2"));
        int listSize = requiredKeys.size();
        assertTrue("Date " + cal.getTime().toString() + " should return at least " + listSize + " records",
                atpInfos.size() >= listSize);

        // make sure the required keys are in the retrieved list:
        for (AtpInfo atpInfo : atpInfos) {
            requiredKeys.remove(atpInfo.getId());
        }
        if (!requiredKeys.isEmpty()) {
            fail("Failed to find key " + requiredKeys.get(0) + " in returned list");
        }
    }

    @Test
    public void testSearchForAtps() throws DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("id", "testAtpId1"),
                PredicateFactory.equal("attributes[CredentialProgramType]", "kuali.lu.type.credential.Baccalaureate"));
        QueryByCriteria qbc = qbcBuilder.build();
        List<AtpInfo> atpInfos = atpService.searchForAtps(qbc, callContext);
        assertNotNull(atpInfos);
        assertEquals(1, atpInfos.size());
        AtpInfo atpInfo = atpInfos.get(0);
        assertEquals("testAtpId1", atpInfo.getId());
        assertEquals("testAtp1", atpInfo.getName());
        assertEquals("Desc 101", atpInfo.getDescr().getPlain());

    }

    @Test
    public void testCreateMilestone() throws DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException,
            ReadOnlyException {
        MilestoneInfo milestone = new MilestoneInfo();
        milestone.setName("testCreate");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2005);

        milestone.setStartDate(cal.getTime());
        milestone.setEndDate(cal.getTime());
        milestone.setIsDateRange(false);
        milestone.setIsAllDay(true);
        milestone.setIsInstructionalDay(false);
        milestone.setIsRelative(false);
        milestone.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        milestone.setTypeKey(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY);
        RichTextInfo descr = new RichTextInfo();
        descr.setPlain("Test");
        milestone.setDescr(descr);

        MilestoneInfo created = atpService.createMilestone(milestone.getTypeKey(), milestone, callContext);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("testCreate", created.getName());

        // try to get the just-created milestone
        MilestoneInfo found = atpService.getMilestone(created.getId(), callContext);
        assertNotNull(found);

        // ensure we cannot create another of the same id
        MilestoneInfo dupeCreated = null;
        dupeCreated = atpService.createMilestone(milestone.getTypeKey(), milestone, callContext);
        assertFalse(created.getId().equals(dupeCreated.getId()));
    }

    @Test
    public void testUpdateMilestone() throws Exception {
        String milestoneId = null;
        MilestoneInfo milestone = new MilestoneInfo();
        milestone.setName("testCreate");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2005);

        milestone.setStartDate(cal.getTime());
        milestone.setEndDate(cal.getTime());
        milestone.setIsDateRange(false);
        milestone.setIsAllDay(true);
        milestone.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        milestone.setTypeKey(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY);

        RichTextInfo descr = new RichTextInfo();
        descr.setPlain("Test");
        milestone.setDescr(descr);
        milestone.setIsInstructionalDay(false);
        milestone.setIsRelative(false);

        MilestoneInfo created = atpService.createMilestone(milestone.getTypeKey(), milestone, callContext);
        assertNotNull(created);
        milestoneId = created.getId();
        assertNotNull(milestoneId);
        assertEquals("testCreate", created.getName());

        MilestoneInfo updateData = atpService.getMilestone(milestoneId, callContext);

        String updatedName = "updated " + updateData.getName();

        updateData.setName(updatedName);

        MilestoneInfo updated = null;
        updated = atpService.updateMilestone(milestoneId, updateData, callContext);
        assertNotNull(updated);
        assertEquals(updated.getId(), milestoneId);
        assertEquals(updated.getName(), updatedName);

        // now fetch the updated milestone fresh, and check fields
        updated = atpService.getMilestone(milestoneId, callContext);
        assertNotNull(updated);
        assertEquals(updated.getId(), milestoneId);
        assertEquals(updated.getName(), updatedName);

        try {
            atpService.updateMilestone("fakeKey", updated, callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertEquals("fakeKey", e.getMessage());
        }
    }

    @Test
    public void testDeleteMilestone() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException,
            DataValidationErrorException {
        StatusInfo status = atpService.deleteMilestone("testDeleteId", callContext);

        assertTrue(status.getIsSuccess());

        try {
            atpService.deleteMilestone("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertEquals("fakeKey", e.getMessage());
        }

        // ensure the delete prevents future gets
        try {
            atpService.getMilestone("testDeleteId", callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertEquals("testDeleteId", e.getMessage());
        }

    }

    @Test
    public void testGetMilestone() throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        MilestoneInfo milestoneInfo = atpService.getMilestone("testId", callContext);
        assertNotNull(milestoneInfo);
        assertEquals("testId", milestoneInfo.getId());
        assertEquals("testId", milestoneInfo.getName());
        assertEquals("Desc 105", milestoneInfo.getDescr().getPlain());
        assertEquals(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY, milestoneInfo.getStateKey());
        assertEquals(AtpServiceConstants.MILESTONE_ADVANCED_REGISTRATION_PERIOD_TYPE_KEY, milestoneInfo.getTypeKey());

        try {
            atpService.getMilestone("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertEquals("fakeKey", e.getMessage());
        }
    }

    @Test
    public void testGetMilestonesByKeyList() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> milestoneIds = new ArrayList<String>();
        milestoneIds.addAll(Arrays.asList("testId", "testId2"));

        List<MilestoneInfo> milestones = atpService.getMilestonesByIds(milestoneIds, callContext);

        assertNotNull(milestones);
        assertEquals(milestoneIds.size(), milestones.size());

        // check that all the expected Ids came back
        for (MilestoneInfo info : milestones) {
            milestoneIds.remove(info.getId());
        }

        assertTrue(milestoneIds.isEmpty());

        // now make sure an exception is thrown for any not found keys

        List<String> fakeKeys = Arrays.asList("testId", "fakeKey1", "fakeKey2");

        try {
            atpService.getMilestonesByIds(fakeKeys, callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().startsWith("Missing data for :"));
            assertTrue(e.getMessage().contains("fakeKey2"));
            assertTrue(e.getMessage().contains("fakeKey1"));
        }

    }

    @Test
    public void testGetMilestonesByDates()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Calendar.JULY);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.YEAR, 2011);
        Date startDate = cal.getTime();

        cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.YEAR, 2011);
        Date endDate = cal.getTime();

        List<MilestoneInfo> milestones = atpService.getMilestonesByDates(startDate, endDate, callContext);
        System.out.println(milestones.size() + " milestones selected");
        for (MilestoneInfo info : milestones) {
            System.out.println(info.getId() + " from " + info.getStartDate() + " to " + info.getEndDate() + " is allday=" + info.getIsAllDay() + " isRange=" + info.getIsDateRange());
        }
        assertNotNull("getMilestonesByDates() should return a list, not null", milestones);
        List<String> expectedIds = new ArrayList(Arrays.asList("testId", "testId2"));
        assertTrue("Should have returned at least " + expectedIds.size() + " records", milestones.size() >= expectedIds.size());

        // check that all the expected Ids came back
        for (MilestoneInfo info : milestones) {
            expectedIds.remove(info.getId());
        }

        assertTrue(expectedIds.isEmpty());

        cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1989);
        cal.set(Calendar.MONTH, Calendar.JULY);
        cal.set(Calendar.DATE, 1);
        startDate = cal.getTime();

        cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1989);
        cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
        cal.set(Calendar.DATE, 1);
        endDate = cal.getTime();

        milestones = atpService.getMilestonesByDates(startDate, endDate, callContext);

        assertTrue(milestones.isEmpty());
    }

    @Test
    public void testGetMilestoneIdsByType() throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        String expectedMilestoneType = AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY;
        List<String> milestoneIds = atpService.getMilestoneIdsByType(expectedMilestoneType, callContext);
        assertTrue(milestoneIds.contains("testId2"));

        milestoneIds = atpService.getMilestoneIdsByType("fakeTypeKey", callContext);
        assertTrue("Milestone IDs should be empty for fake type key", milestoneIds.isEmpty());
    }

    @Test
    public void testGetMilestonesForAtp() throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<MilestoneInfo> milestones = atpService.getMilestonesForAtp("testAtpId1", callContext);

        assertNotNull(milestones);
        assertEquals(1, milestones.size());

        List<String> expectedIds = new ArrayList<String>();
        expectedIds.add("testId");

        // check that all the expected Ids came back
        for (MilestoneInfo info : milestones) {
            expectedIds.remove(info.getId());
        }

        assertTrue(expectedIds.isEmpty());

        try {
            atpService.getMilestonesForAtp("fakeKey", callContext);
            fail("Did not get a InvalidParameterException when expected");
        } catch (InvalidParameterException e) {
            assertNotNull(e.getMessage());
            assertEquals("fakeKey", e.getMessage());
        }

    }

    @Test
    public void testSearchForMilestones() throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("id", "testId2"),
                PredicateFactory.equal("typeKey", AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY));
        QueryByCriteria qbc = qbcBuilder.build();

        List<MilestoneInfo> milestoneInfos = atpService.searchForMilestones(qbc, callContext);
        assertNotNull(milestoneInfos);
        assertEquals(1, milestoneInfos.size());
        MilestoneInfo milestoneInfo = milestoneInfos.get(0);
        assertEquals("testId2", milestoneInfo.getId());
        assertEquals("testId2", milestoneInfo.getName());

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2011);
        cal.set(Calendar.MONTH, Calendar.MAY);
        cal.set(Calendar.DATE, 1);
        Predicate startPredicate = PredicateFactory.greaterThanOrEqual("startDate", new Timestamp(cal.getTime().getTime()));

        cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2011);
        cal.set(Calendar.MONTH, Calendar.NOVEMBER);
        cal.set(Calendar.DATE, 30);
        cal.set(2011, 11, 30);
        Predicate endPredicate = PredicateFactory.lessThanOrEqual("endDate",
                new Timestamp(cal.getTime().getTime()));
        qbcBuilder.setPredicates(startPredicate, endPredicate);
        qbc = qbcBuilder.build();
        milestoneInfos = atpService.searchForMilestones(qbc, callContext);
        assertNotNull(milestoneInfos);
        assertEquals(2, milestoneInfos.size());
    }

    @Test
    public void testValidateAtpAtpRelation() throws Exception {
        AtpAtpRelationInfo atpRel = new AtpAtpRelationInfo();
        atpRel.setAtpId("testAtp1");
        atpRel.setRelatedAtpId("testAtp2");
        atpRel.setTypeKey(AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY);
        atpRel.setStateKey(AtpServiceConstants.ATP_ATP_RELATION_ACTIVE_STATE_KEY);
        atpRel.setEffectiveDate(new Date());

        List<ValidationResultInfo> vri = atpService.validateAtpAtpRelation("FULL_VALIDATION", "testAtp1",
                "testAtp2", AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY, atpRel, callContext);
        assertTrue(vri.isEmpty());
    }

    @Test
    public void testCreateAtpAtpRelation()
            throws AlreadyExistsException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DataValidationErrorException, DoesNotExistException,
            ReadOnlyException {
        AtpAtpRelationInfo atpRel = createSimpleAtpAtpRelation(false);

        AtpInfo atpInfo = new AtpInfo();
        atpInfo.setName("testAtpId1 to new holiday calendar");
        atpInfo.setTypeKey(AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY);
        atpInfo.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        atpInfo.setStartDate(Calendar.getInstance().getTime());
        atpInfo.setEndDate(Calendar.getInstance().getTime());
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setPlain("");
        atpInfo.setDescr(richTextInfo);
        AtpInfo cc = null;
        cc = atpService.createAtp(atpInfo.getTypeKey(), atpInfo, callContext);
        assertNotNull(cc);

        atpRel.setRelatedAtpId(cc.getId());
        AtpAtpRelationInfo created = atpService.createAtpAtpRelation("testAtpId1", cc.getId(), atpRel.getTypeKey(), atpRel, callContext);
        assertNotNull(created);

        AtpAtpRelationInfo retrieved = atpService.getAtpAtpRelation(created.getId(), callContext);
        assertEquals("testAtpId1", retrieved.getAtpId());
        assertEquals(cc.getId(), retrieved.getRelatedAtpId());
        assertEquals(AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY, retrieved.getTypeKey());
    }

    @Test
    public void testGetAtpAtpRelation() throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException {
        AtpAtpRelationInfo atpRel = createSimpleAtpAtpRelation(true);

        // test retrieval
        AtpAtpRelationInfo retrieved = atpService.getAtpAtpRelation(atpRel.getId(), callContext);
        assertNotNull(retrieved);
        assertEquals(atpRel.getId(), retrieved.getId());
        assertEquals(atpRel.getAtpId(), retrieved.getAtpId());
        assertEquals(atpRel.getRelatedAtpId(), retrieved.getRelatedAtpId());
        assertEquals(atpRel.getTypeKey(), retrieved.getTypeKey());
        assertEquals(atpRel.getStateKey(), retrieved.getStateKey());

        // assert getting a bogus atp id throws exception
        try {
            atpService.getAtpAtpRelation("totallyBogusAtpAtpRelationId", callContext);
            fail("Expected a DoesNotExistException");
        } catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertEquals("totallyBogusAtpAtpRelationId", e.getMessage());
        }
    }

    @Test
    public void testUpdateAtpAtpRelation() throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException, VersionMismatchException {
        AtpAtpRelationInfo createdRel = createSimpleAtpAtpRelation(true);

        Date newExpirationDate = new Date();
        String newState = AtpServiceConstants.ATP_ATP_RELATION_INACTIVE_STATE_KEY;

        createdRel.setStateKey(newState);
        createdRel.setExpirationDate(newExpirationDate);

        AtpAtpRelationInfo result = atpService.updateAtpAtpRelation(createdRel.getId(), createdRel, callContext);

        // assert updated fields have been changed
        assertEquals(newState, result.getStateKey());
        assertEquals(newExpirationDate, result.getExpirationDate());

        // make sure expected failure happens if we update a non-existent Atp-Atp Relation
        try {
            atpService.updateAtpAtpRelation("totallMadeUpAtpAtpRelation", new AtpAtpRelationInfo(), callContext);
            fail("Expecting a DoesNotExistException");
        }
        catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertEquals("totallMadeUpAtpAtpRelation", e.getMessage());
        }
    }

    private AtpAtpRelationInfo createSimpleAtpAtpRelation(boolean persist) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        AtpAtpRelationInfo atpRel = new AtpAtpRelationInfo();
        atpRel.setAtpId("testAtpId1");
        atpRel.setRelatedAtpId("testAtpId2");
        atpRel.setTypeKey(AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY);
        atpRel.setStateKey(AtpServiceConstants.ATP_ATP_RELATION_ACTIVE_STATE_KEY);
        atpRel.setEffectiveDate(new Date());

        if(persist) {
            return atpService.createAtpAtpRelation("testAtpId1", "testAtpId2", atpRel.getTypeKey(), atpRel, callContext);
        }
        else {
            return atpRel;
        }
    }

    @Test
    public void testDeleteAtpAtpRelation() throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException {
        AtpAtpRelationInfo createdRel = createSimpleAtpAtpRelation(true);

        StatusInfo result = atpService.deleteAtpAtpRelation(createdRel.getId(), callContext);

        assertTrue(result.getIsSuccess());

        // assert the record is deleted
        try {
            atpService.getAtpAtpRelation(createdRel.getId(), callContext);
            fail("Expecting a DoesNotExistException");
        } catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertEquals(createdRel.getId(), e.getMessage());
        }

        // assert the same record cannot be deleted twice
        try {
            atpService.deleteAtpAtpRelation(createdRel.getId(), callContext);
            fail("Expecting a DoesNotExistException");
        } catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertEquals(createdRel.getId(), e.getMessage());
        }


    }

    @Test
    public void testGetAtpAtpRelationsByTypeAndAtp() throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        List<AtpAtpRelationInfo> aaRelInfos = atpService.getAtpAtpRelationsByTypeAndAtp("testTermId1", AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, callContext);
        assertEquals(1, aaRelInfos.size());
        assertEquals("testTermId2", aaRelInfos.get(0).getRelatedAtpId());

        // should return empty list for bogus atp id
        aaRelInfos = atpService.getAtpAtpRelationsByTypeAndAtp("totallyBogusAtpIdJustMadeUpForTesting", AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, callContext);
        assertTrue(aaRelInfos.isEmpty());

        // should return empty list for bogus relation type
        aaRelInfos = atpService.getAtpAtpRelationsByTypeAndAtp("testAtpId1", "totallyBogusRelationTypeJustMadeUpForTesting", callContext);
        assertTrue(aaRelInfos.isEmpty());
    }

    @Test
    public void testGetAtpAtpRelationsByAtp() throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException {
        String targetAtpId = "termRelationTestingTerm1";
        List<AtpAtpRelationInfo> results = atpService.getAtpAtpRelationsByAtp(targetAtpId, callContext);

        assertEquals(2, results.size());

        // One result should have a relation with the target id as the 'parent',
        // the other should have the target id as the 'child'
        AtpAtpRelationInfo parent = null;
        AtpAtpRelationInfo child = null;
        for (AtpAtpRelationInfo relInfo : results) {

            // all the relations should have the same state and type
            assertEquals(AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, relInfo.getTypeKey());
            assertEquals(AtpServiceConstants.ATP_ATP_RELATION_ACTIVE_STATE_KEY, relInfo.getStateKey());

            if (relInfo.getAtpId().equals(targetAtpId)) {
                parent = relInfo;
            } else if (relInfo.getRelatedAtpId().equals(targetAtpId)) {
                child = relInfo;
            }
        }

        assertNotNull(parent);
        assertEquals("termRelationTestingTerm2", parent.getRelatedAtpId());

        assertNotNull(child);
        assertEquals("termRelationTestingAcal1", child.getAtpId());

        // assert that searching for a bogus atp id returns an empty list
        results = atpService.getAtpAtpRelationsByAtp("bogusAtpMadeUpForTesting", callContext);

        assertTrue(results.isEmpty());
    }

    @Test
    public void testGetAtpsByKeys()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<String> atpIds = new ArrayList<String>();
        atpIds.addAll(Arrays.asList("testAtpId1", "testAtpId2"));

        List<AtpInfo> atps = atpService.getAtpsByIds(atpIds, callContext);

        assertNotNull(atps);
        assertEquals(atpIds.size(), atps.size());

        // check that all the expected Ids came back
        for (AtpInfo info : atps) {
            atpIds.remove(info.getId());
        }
        assertTrue(atpIds.isEmpty());

        // now make sure an exception is thrown for any not found keys
        List<String> fakeKeys = Arrays.asList("testAtpId1", "fakeKey1", "fakeKey2");
        try {
            atpService.getAtpsByIds(fakeKeys, callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().startsWith("Missing data for :"));
            assertTrue(e.getMessage().contains("fakeKey2"));
            assertTrue(e.getMessage().contains("fakeKey1"));
        }
    }

    @Test
    public void testGetAtpsByDates()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        Calendar startCal = Calendar.getInstance();
        startCal.clear();
        startCal.set(2000, 8, 1); // Sept 1 2000
        Calendar endCal = Calendar.getInstance();
        endCal.clear();
        endCal.set(2001, 5, 1); // June 1 2001

        List<AtpInfo> atpInfos = atpService.getAtpsByDates(startCal.getTime(), endCal.getTime(), callContext);
        assertNotNull(atpInfos);
        assertTrue("There should be at least 5 Academic Time Periods between 9/1/00 and 6/1/01.",
                atpInfos.size() >= 5);
        int priorSize = atpInfos.size();

        endCal.add(Calendar.DAY_OF_YEAR, -1); // Roll back to May 31 2001
        atpInfos = atpService.getAtpsByDates(startCal.getTime(), endCal.getTime(), callContext);
        assertNotNull(atpInfos);

        int expectedSize = priorSize - 1;
        assertEquals("There should be " + expectedSize + " Academic Time Periods between 9/1/00 and 5/31/01.",
                expectedSize, atpInfos.size());

        startCal.add(Calendar.DAY_OF_YEAR, 1); // Sept 2 2000
        atpInfos = atpService.getAtpsByDates(startCal.getTime(), endCal.getTime(), callContext);
        assertNotNull(atpInfos);
        assertEquals("There should be 1 Academic Time Period between 9/2/00 and 5/31/01.", 1, atpInfos.size());

        startCal.set(2001, 0, 2); // Jan 2 2001
        atpInfos = atpService.getAtpsByDates(startCal.getTime(), endCal.getTime(), callContext);
        assertNotNull("getAtpsByDates() should return an empty list instead of null.", atpInfos);
        assertEquals("There should be zero Academic Time Periods between 1/2/01 and 5/31/01.", 0, atpInfos.size());
    }

    @Test
    public void testGetAtpIdsByType()
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        String expectedAtpType = AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY;
        List<String> atpIds = atpService.getAtpIdsByType(expectedAtpType, callContext);
        assertTrue(atpIds.contains("testAtpId1"));
        assertTrue(atpIds.contains("termRelationTestingAcal1"));

        String expectedEmptyAtpType = "kuali.atp.type.SessionG2";
        atpIds = atpService.getAtpIdsByType(expectedEmptyAtpType, callContext);
        assertTrue(atpIds.isEmpty());

        atpIds = atpService.getAtpIdsByType("fakeTypeKey", callContext);
        // fake type key should result in empty ATP IDs as well
        assertTrue("ATP IDs should be empty for fake type key", atpIds.isEmpty());
    }

    @Test
    public void testGetImpactedMilestones() throws InvalidParameterException, MissingParameterException,
            DoesNotExistException, PermissionDeniedException, OperationFailedException {
        List<MilestoneInfo> impactedMilestones = atpService.getImpactedMilestones("FALLTERM1990INSTRUCTIONPERIOD",
                callContext);
        assertNotNull(impactedMilestones);
        List<String> impactedMilestoneIds = new ArrayList<String>();
        for (MilestoneInfo impactedMilestone : impactedMilestones) {
            impactedMilestoneIds.add(impactedMilestone.getId());
        }
        assertTrue(impactedMilestoneIds.contains("FALLTERM1990CENSUS"));
    }

    @Test
    public void testCalculateMilestone() throws InvalidParameterException, MissingParameterException,
            DoesNotExistException, PermissionDeniedException, OperationFailedException {
        // Census start needs to be recalculated to 14-sept-1990
        final Date censusExpectedStartDate = (new GregorianCalendar(1990, Calendar.SEPTEMBER, 14)).getTime();
        final Date instructionStartDate = (new GregorianCalendar(1990, Calendar.SEPTEMBER, 3)).getTime();

        final String censusId = "FALLTERM1990CENSUS";
        final String instructionPeriodId = "FALLTERM1990INSTRUCTIONPERIOD";

        MilestoneInfo census = atpService.getMilestone(censusId, callContext);
        assertFalse(censusExpectedStartDate.equals(census.getStartDate()));
        MilestoneInfo instructionPeriod = atpService.getMilestone(instructionPeriodId, callContext);
        assertEquals(instructionStartDate, instructionPeriod.getStartDate());

        census = atpService.calculateMilestone(censusId, callContext);
        assertTrue("Milestone start date not calculated as expected.",
                censusExpectedStartDate.equals(census.getStartDate()));
        census = atpService.getMilestone(censusId, callContext);
        // TODO should the milestone be saved in the calculation method or is that a seperate call?
        assertFalse("Milestone was saved after calculation.", censusExpectedStartDate.equals(census.getStartDate()));
    }
}
