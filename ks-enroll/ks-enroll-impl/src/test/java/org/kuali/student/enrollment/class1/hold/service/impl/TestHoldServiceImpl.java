package org.kuali.student.enrollment.class1.hold.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.hold.dto.IssueInfo;
import org.kuali.student.r2.core.hold.service.HoldService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityExistsException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:hold-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestHoldServiceImpl {

    @Resource(name = "holdServiceAuthDecorator")
    private HoldService holdService;

    public static String principalId = "123";
    public ContextInfo callContext = null;

    private HoldTestDataLoader dataLoader;

    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        dataLoader = new HoldTestDataLoader(this.holdService);
        try {
            dataLoader.loadData();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void testHoldServiceSetup() {
        assertNotNull(holdService);
    }

    @Test
    public void testGetHoldIssue() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        IssueInfo issue = holdService.getIssue(HoldTestConstants.HOLD_ISSUE_1, callContext);
        assertNotNull(issue);
        assertEquals(HoldTestConstants.HOLD_ISSUE_1, issue.getId());
        assertEquals("Test issue 1", issue.getName());
        assertEquals(HoldTestConstants.ORG_1, issue.getOrganizationId());

    }

    @Test
    public void testShouldNotGetNonExistentHoldIssue() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            IssueInfo issue = holdService.getIssue("HoldIssue-dummy", callContext);
            fail("Should throw a DoesNotExistException if the issue does not exist.");
        } catch (DoesNotExistException e) {
            //this is expected
        }
    }

    @Test
    public void testCreateHoldIssue() throws ReadOnlyException, AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        IssueInfo issueInfo = new IssueInfo();
        issueInfo.setId(HoldTestConstants.HOLD_ISSUE_5);
        issueInfo.setStateKey(HoldTestConstants.DRAFT_STATE);
        issueInfo.setTypeKey("random type");
        issueInfo.setOrganizationId(HoldTestConstants.ORG_2);
        issueInfo.setDescr(new RichTextInfo("plain issue", "<b>Formatted</b> issue"));
        issueInfo.setName("Random new issue");
        issueInfo.setAttributes(dataLoader.createAttributes());
        holdService.createIssue(issueInfo.getTypeKey(), issueInfo, callContext);

        //test that the created object was properly persisted
        IssueInfo issue = holdService.getIssue(HoldTestConstants.HOLD_ISSUE_5, callContext);
        assertEquals(HoldTestConstants.DRAFT_STATE, issue.getStateKey());
        assertEquals("random type", issue.getTypeKey());
        assertEquals(HoldTestConstants.ORG_2, issue.getOrganizationId());
        assertEquals("plain issue", issue.getDescr().getPlain());
        assertEquals("<b>Formatted</b> issue", issue.getDescr().getFormatted());
        assertEquals("Random new issue", issue.getName());
        List<AttributeInfo> attribs = issue.getAttributes();
        assertEquals(2, attribs.size());
        for (AttributeInfo att : attribs) {
            if (att.getKey().equals(HoldTestConstants.TEST_KEY_1)) {
                assertEquals(att.getValue(), HoldTestConstants.TEST_VALUE_1);
            } else if (att.getKey().equals(HoldTestConstants.TEST_KEY_2)) {
                assertEquals(att.getValue(), HoldTestConstants.TEST_VALUE_2);
            } else {
                fail("We did not put this attribute in the list");
            }
        }
    }

    @Test
    public void testShouldNotCreateIncompleteHoldIssue() throws ReadOnlyException, AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        IssueInfo issueInfo = new IssueInfo();
        issueInfo.setId(HoldTestConstants.HOLD_ISSUE_5);
        issueInfo.setTypeKey("random type");
        issueInfo.setName("Random new issue");
        issueInfo.setAttributes(dataLoader.createAttributes());
        try {
            holdService.createIssue(issueInfo.getTypeKey(), issueInfo, callContext);
            fail("Should fail validation when required fields are null");
        } catch (InvalidParameterException e) {
            //expected
        }

    }

    @Test
    public void testShouldNotCreateDuplicateHoldIssue() throws ReadOnlyException, AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        IssueInfo issueInfo = new IssueInfo();
        issueInfo.setId(HoldTestConstants.HOLD_ISSUE_1);
        issueInfo.setStateKey("fake state");
        issueInfo.setTypeKey("random type");
        issueInfo.setOrganizationId(HoldTestConstants.ORG_2);
        issueInfo.setDescr(new RichTextInfo("plain issue", "<b>Formatted</b> issue"));
        issueInfo.setName("Random duplicate issue");
        try {
            holdService.createIssue(issueInfo.getTypeKey(), issueInfo, callContext);
            fail("Should not be able to create this duplicate issue");
        } catch (EntityExistsException e) {
            //this is expected
        }
    }

    @Test
    public void testUpdateHoldIssue() throws ReadOnlyException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        IssueInfo issue = holdService.getIssue(HoldTestConstants.HOLD_ISSUE_3, callContext);
        issue.setName("New Name");
        issue.setStateKey("fake state");
        issue.setTypeKey("random type");
        issue.setOrganizationId(HoldTestConstants.ORG_1);
        issue.setDescr(new RichTextInfo("New plain issue", "New <b>Formatted</b> issue"));
        holdService.updateIssue(issue.getId(), issue, callContext);

        //test updated issue
        IssueInfo updatedIssue = holdService.getIssue(HoldTestConstants.HOLD_ISSUE_3, callContext);
        assertEquals("New Name", updatedIssue.getName());
        assertEquals("fake state", updatedIssue.getStateKey());
        assertEquals("random type", updatedIssue.getTypeKey());
        assertEquals(HoldTestConstants.ORG_1, updatedIssue.getOrganizationId());
        assertEquals("New plain issue", updatedIssue.getDescr().getPlain());
        assertEquals("New <b>Formatted</b> issue", updatedIssue.getDescr().getFormatted());
    }


    @Test
    public void testDeleteHoldIssue()
            throws DependentObjectsExistException,
            DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        StatusInfo status = holdService.deleteIssue(HoldTestConstants.HOLD_ISSUE_2, callContext);
        assertEquals(Boolean.TRUE, status.getIsSuccess());
        try {
            holdService.deleteIssue(HoldTestConstants.HOLD_ISSUE_2, callContext);
            fail("Should throw exception when trying to delete non-existent object");
        } catch (DoesNotExistException e) {
            //expected
        }
    }
}
