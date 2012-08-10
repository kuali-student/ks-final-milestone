package org.kuali.student.enrollment.class1.hold.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.test.util.AttributeTester;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:hold-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestHoldServiceJpaPersistenceImpl extends TestHoldServiceMockImpl {


    @Test
    public void testSearchForAppliedHoldIds() throws InvalidParameterException, DataValidationErrorException, MissingParameterException, ReadOnlyException, PermissionDeniedException, OperationFailedException {
        //Create a Hold Issue
        HoldIssueInfo myIssue = new HoldIssueInfo();
        myIssue.setName("name of issue");
        myIssue.setDescr(new RichTextHelper().fromPlain("description of issue"));
        myIssue.setOrganizationId("org1");
        myIssue.setTypeKey(HoldServiceConstants.ACADEMIC_PROGRESS_ISSUE_TYPE_KEY);
        myIssue.setStateKey(HoldServiceConstants.ISSUE_INACTIVE_STATE_KEY);
        new AttributeTester().add2ForCreate(myIssue.getAttributes());
        HoldIssueInfo actualIssue = getHoldService().createHoldIssue(myIssue.getTypeKey(), myIssue, callContext);
        // Create an Applied Hold
        AppliedHoldInfo expected = new AppliedHoldInfo();
        expected.setPersonId("student1");
        expected.setHoldIssueId(actualIssue.getId());
        expected.setName("name of hold");
        expected.setDescr(new RichTextHelper().fromPlain("description of hold"));
        expected.setEffectiveDate(new Date());
        expected.setReleasedDate(new Date());
        expected.setTypeKey(HoldServiceConstants.STUDENT_HOLD_TYPE_KEY);
        expected.setStateKey(HoldServiceConstants.HOLD_ACTIVE_STATE_KEY);
        new AttributeTester().add2ForCreate(expected.getAttributes());
        AppliedHoldInfo actual = getHoldService().createAppliedHold(expected.getPersonId(), expected.getHoldIssueId(), expected.getTypeKey(), expected,
                callContext);
        assertNotNull(actual.getId());

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("id", actual.getId()));
        QueryByCriteria qbc = qbcBuilder.build();
        List<String> results = getHoldService().searchForAppliedHoldIds(qbc, callContext);

        assertTrue(results.size() == 1);
        String myResult = results.get(0);
        assertEquals(actual.getId(), myResult);
    }

    @Test
    public void testSearchForAppliedHolds() throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException, DataValidationErrorException, ReadOnlyException {
        //Create a Hold Issue
        HoldIssueInfo myIssue = new HoldIssueInfo();
        myIssue.setName("name of issue");
        myIssue.setDescr(new RichTextHelper().fromPlain("description of issue"));
        myIssue.setOrganizationId("org1");
        myIssue.setTypeKey(HoldServiceConstants.ACADEMIC_PROGRESS_ISSUE_TYPE_KEY);
        myIssue.setStateKey(HoldServiceConstants.ISSUE_INACTIVE_STATE_KEY);
        new AttributeTester().add2ForCreate(myIssue.getAttributes());
        HoldIssueInfo actualIssue = getHoldService().createHoldIssue(myIssue.getTypeKey(), myIssue, callContext);
        // Create an Applied Hold
        AppliedHoldInfo expected = new AppliedHoldInfo();
        expected.setPersonId("student1");
        expected.setHoldIssueId(actualIssue.getId());
        expected.setName("name of hold");
        expected.setDescr(new RichTextHelper().fromPlain("description of hold"));
        expected.setEffectiveDate(new Date());
        expected.setReleasedDate(new Date());
        expected.setTypeKey(HoldServiceConstants.STUDENT_HOLD_TYPE_KEY);
        expected.setStateKey(HoldServiceConstants.HOLD_ACTIVE_STATE_KEY);
        new AttributeTester().add2ForCreate(expected.getAttributes());
        AppliedHoldInfo actual = getHoldService().createAppliedHold(expected.getPersonId(), expected.getHoldIssueId(), expected.getTypeKey(), expected,
                callContext);
        assertNotNull(actual.getId());

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("id", actual.getId()),
                PredicateFactory.equal("attributes[attribute.key1]", "attribute value1"));
        QueryByCriteria qbc = qbcBuilder.build();
        List<AppliedHoldInfo> results = getHoldService().searchForAppliedHolds(qbc, callContext);

        assertTrue(results.size() == 1);
        AppliedHoldInfo myResult = results.get(0);
        assertEquals(actual.getId(), myResult.getId());
        new AttributeTester().check(actual.getAttributes(), myResult.getAttributes());
    }

    @Test
    public void testSearchForHoldIssueIds() throws InvalidParameterException, DataValidationErrorException, MissingParameterException, ReadOnlyException, PermissionDeniedException, OperationFailedException {
        //Create a Hold Issue
        HoldIssueInfo myIssue = new HoldIssueInfo();
        myIssue.setName("name of issue");
        myIssue.setDescr(new RichTextHelper().fromPlain("description of issue"));
        myIssue.setOrganizationId("org1");
        myIssue.setTypeKey(HoldServiceConstants.ACADEMIC_PROGRESS_ISSUE_TYPE_KEY);
        myIssue.setStateKey(HoldServiceConstants.ISSUE_INACTIVE_STATE_KEY);
        new AttributeTester().add2ForCreate(myIssue.getAttributes());
        HoldIssueInfo actualIssue = getHoldService().createHoldIssue(myIssue.getTypeKey(), myIssue, callContext);
        assertNotNull(actualIssue.getId());

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("id", actualIssue.getId()));
        QueryByCriteria qbc = qbcBuilder.build();
        List<String> results = getHoldService().searchForHoldIssueIds(qbc, callContext);

        assertTrue(results.size() == 1);
        String myResult = results.get(0);
        assertEquals(actualIssue.getId(), myResult);
    }

    @Test
    public void testSearchForHoldIssues() throws InvalidParameterException, DataValidationErrorException, MissingParameterException, ReadOnlyException, PermissionDeniedException, OperationFailedException {
        //Create a Hold Issue
        HoldIssueInfo myIssue = new HoldIssueInfo();
        myIssue.setName("name of issue");
        myIssue.setDescr(new RichTextHelper().fromPlain("description of issue"));
        myIssue.setOrganizationId("org1");
        myIssue.setTypeKey(HoldServiceConstants.ACADEMIC_PROGRESS_ISSUE_TYPE_KEY);
        myIssue.setStateKey(HoldServiceConstants.ISSUE_INACTIVE_STATE_KEY);
        new AttributeTester().add2ForCreate(myIssue.getAttributes());
        HoldIssueInfo actualIssue = getHoldService().createHoldIssue(myIssue.getTypeKey(), myIssue, callContext);
        assertNotNull(actualIssue.getId());

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("id", actualIssue.getId()));
        QueryByCriteria qbc = qbcBuilder.build();
        List<HoldIssueInfo> results = getHoldService().searchForHoldIssues(qbc, callContext);

        assertTrue(results.size() == 1);
        HoldIssueInfo myResult = results.get(0);
        assertEquals(actualIssue.getId(), myResult.getId());
        new AttributeTester().check(actualIssue.getAttributes(), myResult.getAttributes());
    }
}
