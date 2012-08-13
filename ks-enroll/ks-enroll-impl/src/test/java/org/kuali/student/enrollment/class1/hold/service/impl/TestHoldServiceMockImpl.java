package org.kuali.student.enrollment.class1.hold.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.test.util.AttributeTester;
import org.kuali.student.enrollment.test.util.IdEntityTester;
import org.kuali.student.enrollment.test.util.ListOfStringTester;
import org.kuali.student.enrollment.test.util.MetaTester;
import org.kuali.student.enrollment.test.util.TimeTester;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;
import org.kuali.student.r2.core.hold.service.HoldService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:hold-mock-service-test-context.xml"})
//@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
//@Transactional
public class TestHoldServiceMockImpl {

    public HoldService getHoldService() {
        return holdService;
    }

    public void setHoldService(HoldService holdService) {
        this.holdService = holdService;
    }
    @Resource
    private HoldService holdService;
    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
    }

    @Test
    public void testCrudIssue()
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException {
        // test create
        HoldIssueInfo expected = new HoldIssueInfo();
        expected.setName("name of issue");
        expected.setDescr(new RichTextHelper().fromPlain("description of issue"));
        expected.setOrganizationId("org1");
        expected.setTypeKey(HoldServiceConstants.ACADEMIC_PROGRESS_ISSUE_TYPE_KEY);
        expected.setStateKey(HoldServiceConstants.ISSUE_INACTIVE_STATE_KEY);
        new AttributeTester().add2ForCreate(expected.getAttributes());
        HoldIssueInfo actual = holdService.createHoldIssue(expected.getTypeKey(), expected, callContext);
        assertNotNull(actual.getId());
        new IdEntityTester().check(expected, actual);
        assertEquals(expected.getOrganizationId(), actual.getOrganizationId());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // test read
        expected = actual;
//        for (AttributeInfo itemInfo : expected.getAttributes()) {
//            // clear out any id's set during the persistence
//            // to let the checks work properly
//            itemInfo.setId(null);
//        }
        actual = holdService.getHoldIssue(actual.getId(), callContext);
        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);
        assertEquals(expected.getOrganizationId(), actual.getOrganizationId());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // test update
        expected = actual;
        expected.setName(expected.getName() + " updated");
        expected.setDescr(new RichTextHelper().fromPlain(expected.getDescr().getPlain() + " updated"));
        expected.setStateKey(HoldServiceConstants.ISSUE_ACTIVE_STATE_KEY);
        expected.setOrganizationId("org2");
        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        actual = holdService.updateHoldIssue(expected.getId(), expected, callContext);
        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);
        assertEquals(expected.getOrganizationId(), actual.getOrganizationId());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // test read after update
        expected = actual;
        actual = holdService.getHoldIssue(actual.getId(), callContext);
        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);
        assertEquals(expected.getOrganizationId(), actual.getOrganizationId());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        HoldIssueInfo acadIssue = actual;

        // create a 2nd issue
        HoldIssueInfo finAidIssue = new HoldIssueInfo();
        finAidIssue.setName("fin aid issue");
        finAidIssue.setDescr(new RichTextHelper().fromPlain("financial aid issue"));
        finAidIssue.setOrganizationId("org2");
        finAidIssue.setTypeKey(HoldServiceConstants.FINANCIAL_AID_ISSUE_TYPE_KEY);
        finAidIssue.setStateKey(HoldServiceConstants.ISSUE_INACTIVE_STATE_KEY);
        finAidIssue = holdService.createHoldIssue(finAidIssue.getTypeKey(), finAidIssue, callContext);

        // test bulk get
        List<String> ids = new ArrayList<String>();
        ids.add(acadIssue.getId());
        ids.add(finAidIssue.getId());
        List<HoldIssueInfo> issues = holdService.getHoldIssuesByIds(ids, callContext);
        assertEquals(ids.size(), issues.size());
        for (HoldIssueInfo issue : issues) {
            if (!ids.remove(issue.getId())) {
                fail(issue.getId());
            }
        }
        assertEquals(0, ids.size());

        // test get by type
        ids = holdService.getHoldIssueIdsByType(HoldServiceConstants.FINANCIAL_AID_ISSUE_TYPE_KEY, callContext);
        assertEquals(1, ids.size());
        assertEquals(finAidIssue.getId(), ids.get(0));

        // test get by other type
        ids = holdService.getHoldIssueIdsByType(HoldServiceConstants.ACADEMIC_PROGRESS_ISSUE_TYPE_KEY, callContext);
        assertEquals(1, ids.size());
        assertEquals(acadIssue.getId(), ids.get(0));

        // test get by org1
        issues = holdService.getHoldIssuesByOrg("org1", callContext);
        assertEquals(0, issues.size());

        // test get by org2
        issues = holdService.getHoldIssuesByOrg("org2", callContext);
        assertEquals(2, issues.size());
        assertEquals(acadIssue.getId(), issues.get(0).getId());
        ids = new ArrayList<String>();
        ids.add(acadIssue.getId());
        ids.add(finAidIssue.getId());
        for (HoldIssueInfo issue : issues) {
            if (!ids.remove(issue.getId())) {
                fail(issue.getId());
            }
        }
        assertEquals(0, ids.size());


        //TODO: check that service throws dependent object exception propertly
        AppliedHoldInfo holdInfo = this.testCrudHold(acadIssue, finAidIssue);

        // test for circular dependency when delete issue with a hold
        try {
            StatusInfo status = holdService.deleteHoldIssue(actual.getId(), callContext);
            fail("Did not receive DependentObjectsExistException when attempting to delete an issue with a hold that exists");
        } catch (DependentObjectsExistException dnee) {
            // expected
        }

        // now test delete of hold
        StatusInfo status = holdService.deleteAppliedHold(holdInfo.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            holdInfo = holdService.getAppliedHold(holdInfo.getId(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted AppliedHoldEntity");
        } catch (DoesNotExistException dnee) {
            // expected
        }

        // test delete issue
        status = holdService.deleteHoldIssue(finAidIssue.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = holdService.getHoldIssue(finAidIssue.getId(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted IssueEntity");
        } catch (DoesNotExistException dnee) {
            // expected
        }

    }

    private AppliedHoldInfo testCrudHold(HoldIssueInfo acadIssue,
            HoldIssueInfo finAidIssue)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException {
        // test create
        AppliedHoldInfo expected = new AppliedHoldInfo();
        expected.setPersonId("student1");
        expected.setHoldIssueId(acadIssue.getId());
        expected.setName("name of hold");
        expected.setDescr(new RichTextHelper().fromPlain("description of hold"));
        expected.setEffectiveDate(new Date());
        expected.setReleasedDate(new Date());
        expected.setTypeKey(HoldServiceConstants.STUDENT_HOLD_TYPE_KEY);
        expected.setStateKey(HoldServiceConstants.HOLD_ACTIVE_STATE_KEY);
        new AttributeTester().add2ForCreate(expected.getAttributes());
        AppliedHoldInfo actual = holdService.createAppliedHold(expected.getPersonId(), expected.getHoldIssueId(), expected.getTypeKey(), expected,
                callContext);
        assertNotNull(actual.getId());
        new IdEntityTester().check(expected, actual);
        assertEquals(expected.getPersonId(), actual.getPersonId());
        assertEquals(expected.getHoldIssueId(), actual.getHoldIssueId());
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getReleasedDate(), actual.getReleasedDate());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // test read
        expected = actual;
        actual = holdService.getAppliedHold(actual.getId(), callContext);
        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);
        assertEquals(expected.getPersonId(), actual.getPersonId());
        assertEquals(expected.getHoldIssueId(), actual.getHoldIssueId());
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getReleasedDate(), actual.getReleasedDate());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // test update
        expected = actual;
        expected.setName(expected.getName() + " updated");
        expected.setDescr(new RichTextHelper().fromPlain(expected.getDescr().getPlain() + " updated"));
        expected.setEffectiveDate(new Date(expected.getEffectiveDate().getTime() - 1000));
        expected.setReleasedDate(new Date());
        expected.setStateKey(HoldServiceConstants.HOLD_RELEASED_STATE_KEY);
        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        actual = holdService.updateAppliedHold(expected.getId(), expected, callContext);
        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);
        assertEquals(expected.getPersonId(), actual.getPersonId());
        assertEquals(expected.getHoldIssueId(), actual.getHoldIssueId());
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getReleasedDate(), actual.getReleasedDate());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // test read
        expected = actual;
        actual = holdService.getAppliedHold(actual.getId(), callContext);
        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);
        assertEquals(expected.getPersonId(), actual.getPersonId());
        assertEquals(expected.getHoldIssueId(), actual.getHoldIssueId());
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getReleasedDate(), actual.getReleasedDate());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        AppliedHoldInfo acadHoldReleasedStudent1 = actual;
        // create 2nd
        AppliedHoldInfo finAidHoldStudent1 = new AppliedHoldInfo();
        finAidHoldStudent1.setPersonId("student1");
        finAidHoldStudent1.setHoldIssueId(finAidIssue.getId());
        finAidHoldStudent1.setName("name of hold");
        finAidHoldStudent1.setDescr(new RichTextHelper().fromPlain("description of hold"));
        finAidHoldStudent1.setEffectiveDate(new Date());
        finAidHoldStudent1.setReleasedDate(null);
        finAidHoldStudent1.setTypeKey(HoldServiceConstants.STUDENT_HOLD_TYPE_KEY);
        finAidHoldStudent1.setStateKey(HoldServiceConstants.HOLD_ACTIVE_STATE_KEY);
        finAidHoldStudent1 = holdService.createAppliedHold(finAidHoldStudent1.getPersonId(), 
                finAidHoldStudent1.getHoldIssueId(),
                finAidHoldStudent1.getTypeKey(), finAidHoldStudent1,
                callContext);
        // create a 3rd
        AppliedHoldInfo acadHoldActiveStudent1 = new AppliedHoldInfo();
        acadHoldActiveStudent1.setPersonId("student1");
        acadHoldActiveStudent1.setHoldIssueId(acadIssue.getId());
        acadHoldActiveStudent1.setName("name of hold");
        acadHoldActiveStudent1.setDescr(new RichTextHelper().fromPlain("description of hold"));
        acadHoldActiveStudent1.setEffectiveDate(new Date());
        acadHoldActiveStudent1.setReleasedDate(null);
        acadHoldActiveStudent1.setTypeKey(HoldServiceConstants.STUDENT_HOLD_TYPE_KEY);
        acadHoldActiveStudent1.setStateKey(HoldServiceConstants.HOLD_ACTIVE_STATE_KEY);
        acadHoldActiveStudent1 = holdService.createAppliedHold(acadHoldActiveStudent1.getPersonId(), 
                acadHoldActiveStudent1.getHoldIssueId(),
                acadHoldActiveStudent1.getTypeKey(), acadHoldActiveStudent1,
                callContext);

        // create a 4th
        AppliedHoldInfo acadHoldActiveInstructor1 = new AppliedHoldInfo();
        acadHoldActiveInstructor1.setPersonId("instructor1");
        acadHoldActiveInstructor1.setHoldIssueId(acadIssue.getId());
        acadHoldActiveInstructor1.setName("name of hold");
        acadHoldActiveInstructor1.setDescr(new RichTextHelper().fromPlain("description of hold"));
        acadHoldActiveInstructor1.setEffectiveDate(new Date());
        acadHoldActiveInstructor1.setReleasedDate(null);
        acadHoldActiveInstructor1.setTypeKey(HoldServiceConstants.INTRUCTOR_HOLD_TYPE_KEY);
        acadHoldActiveInstructor1.setStateKey(HoldServiceConstants.HOLD_ACTIVE_STATE_KEY);
        acadHoldActiveInstructor1 = holdService.createAppliedHold(acadHoldActiveInstructor1.getPersonId(),
                acadHoldActiveInstructor1.getHoldIssueId(), acadHoldActiveInstructor1.getTypeKey(), acadHoldActiveInstructor1,
                callContext);

        // test bulk get
        List<String> expIds = new ArrayList<String>();
        expIds.add(acadHoldReleasedStudent1.getId());
        expIds.add(finAidHoldStudent1.getId());
        expIds.add(acadHoldActiveStudent1.getId());
        expIds.add(acadHoldActiveInstructor1.getId());
        List<AppliedHoldInfo> holds = holdService.getAppliedHoldsByIds(expIds, callContext);
        assertEquals(expIds.size(), holds.size());
        for (AppliedHoldInfo issue : holds) {
            if (!expIds.remove(issue.getId())) {
                fail(issue.getId());
            }
        }
        assertEquals(0, expIds.size());

        // test get by type
        List<String> actIds = holdService.getAppliedHoldIdsByType(HoldServiceConstants.INTRUCTOR_HOLD_TYPE_KEY, callContext);
        assertEquals(1, actIds.size());
        assertEquals(acadHoldActiveInstructor1.getId(), actIds.get(0));

        // test get by other type
        actIds = holdService.getAppliedHoldIdsByType(HoldServiceConstants.STUDENT_HOLD_TYPE_KEY, callContext);
        expIds = new ArrayList<String>();
        expIds.add(acadHoldReleasedStudent1.getId());
        expIds.add(finAidHoldStudent1.getId());
        expIds.add(acadHoldActiveStudent1.getId());
        new ListOfStringTester().check(expIds, actIds);

        // test get by student1
        holds = holdService.getAppliedHoldsByPerson("student1", callContext);
        expIds = new ArrayList<String>();
        expIds.add(acadHoldReleasedStudent1.getId());
        expIds.add(finAidHoldStudent1.getId());
        expIds.add(acadHoldActiveStudent1.getId());
        for (AppliedHoldInfo hold : holds) {
            if (!expIds.remove(hold.getId())) {
                fail(hold.getId());
            }
        }
        assertEquals(0, expIds.size());

        // test get by instructor1
        holds = holdService.getAppliedHoldsByPerson("instructor1", callContext);
        assertEquals(1, holds.size());
        assertEquals(acadHoldActiveInstructor1.getId(), holds.get(0).getId());

        // test get by student1
        holds = holdService.getActiveAppliedHoldsByPerson("student1", callContext);
        expIds = new ArrayList<String>();
        expIds.add(finAidHoldStudent1.getId());
        expIds.add(acadHoldActiveStudent1.getId());
        System.out.println (holds.size() + " active applied holds found for student1");
        for (AppliedHoldInfo hold : holds) {
            System.out.println ("active applied hold id=" + hold.getId());
            if (!expIds.remove(hold.getId())) {
                fail(hold.getId());
            }
        }
        assertEquals(0, expIds.size());

//        getHoldsByIssue
        actIds = holdService.getAppliedHoldIdsByIssue(acadIssue.getId(), callContext);
        expIds = new ArrayList<String>();
        expIds.add(acadHoldReleasedStudent1.getId());
        expIds.add(acadHoldActiveStudent1.getId());
        expIds.add(acadHoldActiveInstructor1.getId());
        for (String id : actIds) {
            if (!expIds.remove(id)) {
                fail(id);
            }
        }
        assertEquals(0, expIds.size());
        
//        getHoldsByIssueAndPerson
        holds = holdService.getAppliedHoldsByIssueAndPerson(acadIssue.getId(), "student1", callContext);
        expIds = new ArrayList<String>();
        expIds.add(acadHoldReleasedStudent1.getId());
        expIds.add(acadHoldActiveStudent1.getId());
        for (AppliedHoldInfo hold : holds) {
            if (!expIds.remove(hold.getId())) {
                fail(hold.getId());
            }
        }
        assertEquals(0, expIds.size());
        
//        getActiveHoldsByIssueAndPerson
        holds = holdService.getActiveAppliedHoldsByIssueAndPerson(acadIssue.getId(), "student1", callContext);
        expIds = new ArrayList<String>();
        expIds.add(acadHoldActiveStudent1.getId());
        for (AppliedHoldInfo hold : holds) {
            if (!expIds.remove(hold.getId())) {
                fail(hold.getId());
            }
        }
        assertEquals(0, expIds.size());
        
        // now test delete all but one hold      
        StatusInfo status = holdService.deleteAppliedHold(acadHoldActiveStudent1.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = holdService.getAppliedHold(acadHoldActiveStudent1.getId(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted AppliedHoldEntity");
        } catch (DoesNotExistException dnee) {
            // expected
        }
        status = holdService.deleteAppliedHold(finAidHoldStudent1.getId(), callContext);
        status = holdService.deleteAppliedHold(acadHoldActiveInstructor1.getId(), callContext);
        
        return acadHoldReleasedStudent1;

    }
}
