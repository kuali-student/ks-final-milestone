package org.kuali.student.r2.core.class1.atp.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Ignore;
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
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:atp-test-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class TestAtpServiceImpl {

    @Resource(name = "atpServiceAuthDecorator")
    public AtpService atpService;
    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        try {
            loadData();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    @Ignore
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
//    @Ignore
    public void testGetAtp() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        AtpInfo atpInfo = atpService.getAtp("testAtpId1", callContext);
        assertNotNull(atpInfo);
        assertEquals("testAtpId1", atpInfo.getId());
        assertEquals("testAtp1", atpInfo.getName());
        assertEquals("Desc 101", atpInfo.getDescr().getPlain());
        assertEquals("kuali.atp.state.Draft", atpInfo.getStateKey());
        assertEquals("kuali.atp.type.AcademicCalendar", atpInfo.getTypeKey());
        try {
            atpService.getAtp("totallyBogusAtpId999", callContext);
            fail("AtpService did not throw DoesNotExistException on getAtp() of nonexistent ATP");
        } catch (DoesNotExistException dnee) {
            // expected
        }
    }

    @Test
//    @Ignore
    public void testAtpCrud() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, VersionMismatchException, ReadOnlyException {
        // test create
        AtpInfo atpInfo = new AtpInfo();
        atpInfo.setName("newId");
        atpInfo.setTypeKey("kuali.atp.type.AcademicCalendar");
        atpInfo.setStateKey("kuali.atp.state.Draft");
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
        assertEquals("kuali.atp.state.Draft", fetched.getStateKey());
        assertEquals("kuali.atp.type.AcademicCalendar", fetched.getTypeKey());

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
        }
        // undo the update done above
        updated = atpService.getAtp(updated.getId(), callContext);
        updated.setName(atpNameOrig);

        updated = atpService.updateAtp(updated.getId(), updated, callContext);
        assertNotNull(updated);
        assertEquals(atpNameOrig, updated.getName());
    }

    @Test
//    @Ignore
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
//    @Ignore
    public void testCreateAtp() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException {
        String atpId = null;
        AtpInfo atpInfo = new AtpInfo();
        atpInfo.setName("newId2");
        atpInfo.setTypeKey("kuali.atp.type.AcademicCalendar");
        atpInfo.setStateKey("kuali.atp.state.Draft");
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
//    @Ignore
    public void testDeleteAtp() throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AtpInfo atpInfo = atpService.getAtp("testDeleteAtpId2", callContext);
        assertNotNull(atpInfo);
        assertEquals("testDeleteAtpId2", atpInfo.getId());

        try {
            atpService.deleteAtp("testDeleteAtpId2", callContext);
            try {
                atpService.getAtp("testDeleteAtpId2", callContext);
                fail("Did not receive DoesNotExistException when attempting to get already-deleted AtpEntity");
            } catch (DoesNotExistException dnee) {
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
//    @Ignore
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
//    @Ignore
    public void testSearchForAtps() throws DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("id", "testAtpId1"),
                PredicateFactory.equal("attributes[CredentialProgramType]", "kuali.lu.type.credential.Baccalaureate"));
        QueryByCriteria qbc = qbcBuilder.build();
        try {
            List<AtpInfo> atpInfos = atpService.searchForAtps(qbc, callContext);
            assertNotNull(atpInfos);
            assertEquals(1, atpInfos.size());
            AtpInfo atpInfo = atpInfos.get(0);
            assertEquals("testAtpId1", atpInfo.getId());
            assertEquals("testAtp1", atpInfo.getName());
            assertEquals("Desc 101", atpInfo.getDescr().getPlain());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
//    @Ignore
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
        milestone.setTypeKey("kuali.atp.milestone.RegistrationPeriod");
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
//    @Ignore
    public void testUpdateMilestone() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, VersionMismatchException {
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
        milestone.setTypeKey("kuali.atp.milestone.RegistrationPeriod");

        RichTextInfo descr = new RichTextInfo();
        descr.setPlain("Test");
        milestone.setDescr(descr);
        milestone.setIsInstructionalDay(false);
        milestone.setIsRelative(false);

        try {
            MilestoneInfo created = atpService.createMilestone(milestone.getTypeKey(), milestone, callContext);
            assertNotNull(created);
            milestoneId = created.getId();
            assertNotNull(milestoneId);
            assertEquals("testCreate", created.getName());
        } catch (ReadOnlyException e) {
            fail(e.getMessage());
        }

        MilestoneInfo updateData = atpService.getMilestone(milestoneId, callContext);

        String updatedName = "updated " + updateData.getName();

        updateData.setName(updatedName);

        MilestoneInfo updated = null;
        try {
            updated = atpService.updateMilestone(milestoneId, updateData, callContext);
        } catch (ReadOnlyException e) {
            fail(e.getMessage());
        }
        assertNotNull(updated);
        assertEquals(updated.getId(), milestoneId);
        assertEquals(updated.getName(), updatedName);

        // now fetch the updated milestone fresh, and check fields
        updated = atpService.getMilestone(milestoneId, callContext);
        assertNotNull(updated);
        assertEquals(updated.getId(), milestoneId);
        assertEquals(updated.getName(), updatedName);

        MilestoneInfo shouldBeNull = null;
        try {
            shouldBeNull = atpService.updateMilestone("fakeKey", updated, callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(shouldBeNull);
        } catch (ReadOnlyException e) {
            fail(e.getMessage());
        }
    }

    @Test
//    @Ignore
    public void testDeleteMilestone() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException,
            DataValidationErrorException {
        StatusInfo status = atpService.deleteMilestone("testDeleteId", callContext);

        assertTrue(status.getIsSuccess());

        StatusInfo noStatus = null;
        try {
            noStatus = atpService.deleteMilestone("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(noStatus);
        }

        // ensure the delete prevents future gets
        MilestoneInfo shouldBeNull = null;
        try {
            shouldBeNull = atpService.getMilestone("testDeleteId", callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(shouldBeNull);
        }

    }

    @Test
//    @Ignore
    public void testGetMilestone() throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        MilestoneInfo milestoneInfo = atpService.getMilestone("testId", callContext);
        assertNotNull(milestoneInfo);
        assertEquals("testId", milestoneInfo.getId());
        assertEquals("testId", milestoneInfo.getName());
        assertEquals("Desc 105", milestoneInfo.getDescr().getPlain());
        assertEquals(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY, milestoneInfo.getStateKey());
        assertEquals("kuali.atp.milestone.AdvanceRegistrationPeriod", milestoneInfo.getTypeKey());

        MilestoneInfo fakeMilestone = null;
        try {
            fakeMilestone = atpService.getMilestone("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(fakeMilestone);
        }
    }

    @Test
//    @Ignore
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

        List<MilestoneInfo> shouldBeNull = null;
        try {
            shouldBeNull = atpService.getMilestonesByIds(fakeKeys, callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(shouldBeNull);
        }

    }

    @Test
//    @Ignore
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
//    @Ignore
    public void testGetMilestoneIdsByType() throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        String expectedMilestoneType = "kuali.atp.milestone.RegistrationPeriod";
        List<String> milestoneIds = atpService.getMilestoneIdsByType(expectedMilestoneType, callContext);
        assertTrue(milestoneIds.contains("testId2"));

        milestoneIds = atpService.getMilestoneIdsByType("fakeTypeKey", callContext);
        assertTrue("Milestone IDs should be empty for fake type key", milestoneIds.isEmpty());
    }

    @Test
//    @Ignore
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

        List<MilestoneInfo> fakeMilestones = null;

        try {
            fakeMilestones = atpService.getMilestonesForAtp("fakeKey", callContext);
            fail("Did not get a InvalidParameterException when expected");
        } catch (InvalidParameterException e) {
            assertNull(fakeMilestones);
        }

    }

    @Test
//    @Ignore
    public void testSearchForMilestones() throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("id", "testId2"),
                PredicateFactory.equal("type", "kuali.atp.milestone.RegistrationPeriod"));
        QueryByCriteria qbc = qbcBuilder.build();
        try {
            List<MilestoneInfo> milestoneInfos = atpService.searchForMilestones(qbc, callContext);
            assertNotNull(milestoneInfos);
            assertEquals(1, milestoneInfos.size());
            MilestoneInfo milestoneInfo = milestoneInfos.get(0);
            assertEquals("testId2", milestoneInfo.getId());
            assertEquals("testId2", milestoneInfo.getName());

        } catch (Exception e) {
            fail(e.getMessage());
        }

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
        List<MilestoneInfo> milestoneInfos = atpService.searchForMilestones(qbc, callContext);
        assertNotNull(milestoneInfos);
        assertEquals(2, milestoneInfos.size());
    }

    @Test
//    @Ignore
    public void testValidateAtpAtpRelation()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        AtpAtpRelationInfo atpRel = new AtpAtpRelationInfo();
        atpRel.setAtpId("testAtp1");
        atpRel.setRelatedAtpId("testAtp2");
        atpRel.setTypeKey(AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY);
        atpRel.setStateKey(AtpServiceConstants.ATP_ATP_RELATION_ACTIVE_STATE_KEY);
        atpRel.setEffectiveDate(new Date());

        try {
            List<ValidationResultInfo> vri = atpService.validateAtpAtpRelation("FULL_VALIDATION", "testAtp1",
                    "testAtp2", AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY, atpRel, callContext);
            assertTrue(vri.isEmpty());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }

    @Test
//    @Ignore
    public void testCreateAtpAtpRelation()
            throws AlreadyExistsException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DataValidationErrorException, DoesNotExistException,
            ReadOnlyException {
        AtpAtpRelationInfo atpRel = new AtpAtpRelationInfo();
        atpRel.setAtpId("testAtpId1");
        atpRel.setRelatedAtpId("testAtpId2");
        atpRel.setTypeKey(AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY);
        atpRel.setStateKey(AtpServiceConstants.ATP_ATP_RELATION_ACTIVE_STATE_KEY);
        atpRel.setEffectiveDate(new Date());

        atpService.createAtpAtpRelation("testAtpId1", "testAtpId2", atpRel.getTypeKey(), atpRel, callContext);

        AtpInfo atpInfo = new AtpInfo();
        atpInfo.setName("testAtpId1 to new holiday calendar");
        atpInfo.setTypeKey(AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY);
        atpInfo.setStateKey("kuali.atp.state.Draft");
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
//    @Ignore
    public void testGetAtpAtpRelationsByTypeAndAtp() {
        try {
            List<AtpAtpRelationInfo> aaRelInfos = atpService.getAtpAtpRelationsByTypeAndAtp("testTermId1",
                    AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, callContext);
            assertEquals(1, aaRelInfos.size());
            assertEquals("testTermId2", aaRelInfos.get(0).getRelatedAtpId());

            aaRelInfos = atpService.getAtpAtpRelationsByTypeAndAtp("totallyBogusAtpIdJustMadeUpForTesting",
                    AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, callContext);
            assertTrue(null == aaRelInfos || aaRelInfos.size() == 0);
            //	            fail("atpService.getAtpAtpRelationsByAtpAndRelationType() did not throw expected DoesNotExistException for nonexistent Atp ID");
            // should return empty list for bogus relation type
            aaRelInfos = atpService.getAtpAtpRelationsByTypeAndAtp("testAtpId1",
                    "totallyBogusRelationTypeJustMadeUpForTesting", callContext);
            // sigh. service is returning an empty list, but client seems to get a null. have asked KSDevs chat about how to fix; not finding a solution via my google-fu
            assertTrue(null == aaRelInfos || aaRelInfos.size() == 0);
            // assertEquals(0, aaRelInfos.size());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
//    @Ignore
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
        List<AtpInfo> shouldBeNull = null;
        try {
            shouldBeNull = atpService.getAtpsByIds(fakeKeys, callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
    }

    @Test
//    @Ignore
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
//    @Ignore
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
//    @Ignore
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
//    @Ignore
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
