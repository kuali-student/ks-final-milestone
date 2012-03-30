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


import javax.annotation.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.HoldInfo;
import org.kuali.student.r2.core.hold.dto.IssueInfo;
import org.kuali.student.r2.core.hold.service.HoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:hold-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestHoldServiceImpl {

    @Autowired
    @Resource(name = "holdServiceAuthDecorator")
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
    public void testHoldServiceSetup() {
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
        } catch (DoesNotExistException e) {
            // expected
        }
    }

    @Test
    public void testGetIssuesByIds() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        List<String> issueIds = new ArrayList<String>();
        issueIds.addAll(Arrays.asList("Hold-Issue-1", "Hold-Issue-2"));

        List<IssueInfo> issues = holdService.getIssuesByIds(issueIds, callContext);

        assertNotNull(issues);
        assertEquals(issueIds.size(), issues.size());

        // check that all the expected Ids came back
        for (IssueInfo info : issues) {
            issueIds.remove(info.getId());
        }
        assertTrue(issueIds.isEmpty());

        // now make sure an exception is thrown for any not found keys

        List<String> fakeKeys = Arrays.asList("Hold-Issue-1", "fakeKey1", "fakeKey2");

        List<IssueInfo> shouldBeNull = null;
        try {
            shouldBeNull = holdService.getIssuesByIds(fakeKeys, callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            // expected
        }
    }

    @Test
    public void testGetIssuesByOrg() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<IssueInfo> issues = holdService.getIssuesByOrg("102", callContext);

        assertNotNull(issues);
        assertEquals(2, issues.size());

        List<String> issueIds = new ArrayList<String>();
        issueIds.addAll(Arrays.asList("Hold-Issue-1", "Hold-Issue-2"));

        // check that all the expected Ids came back
        for (IssueInfo info : issues) {
            issueIds.remove(info.getId());
        }

        assertTrue(issueIds.isEmpty());

        // now make sure an empty list is returned for a non matching org id
        List<IssueInfo> shouldBeEmpty = holdService.getIssuesByOrg("fakeOrg", callContext);

        assertTrue(shouldBeEmpty.isEmpty());
    }

    @Test
    public void testCreateHold()
            throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            DoesNotExistException,
            ReadOnlyException {
        HoldInfo info = new HoldInfo();
        //info.setId("Test-Hold-1"); id should be system generated
        info.setName("Test hold one");
        info.setPersonId("person.1");
        info.setStateKey(HoldServiceConstants.HOLD_ACTIVE_STATE_KEY);
        info.setTypeKey(HoldServiceConstants.STUDENT_HOLD_TYPE_KEY);
        info.setIssueId("Hold-Issue-1");
        info.setEffectiveDate(Calendar.getInstance().getTime());

        HoldInfo created = null;
        created = holdService.createHold(info.getPersonId(), info.getIssueId(), info.getTypeKey(), info, callContext);
        assertNotNull(created);
        assertEquals("Test hold one", created.getName());

        HoldInfo retrieved = holdService.getHold(created.getId(), callContext);
        assertNotNull(retrieved);
        assertEquals("Test hold one", retrieved.getName());
    }

    @Test
    public void testGetHold() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
            holdService.getHold("Hold-blah", callContext);
        } catch (DoesNotExistException e) {
            //expected
        }

        try {
            HoldInfo info = holdService.getHold("Hold-1", callContext);
            assertNotNull(info);
            assertEquals("Hold one", info.getName());
            assertEquals(HoldServiceConstants.HOLD_ACTIVE_STATE_KEY, info.getStateKey());
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
        try {
            noStatus = holdService.releaseHold("Hold-Blah", callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            assertNull(noStatus);
        }

        HoldInfo released = holdService.releaseHold("Hold-1", callContext);
        assertNotNull(released);
        assertEquals("Hold one", released.getName());
        assertEquals(HoldServiceConstants.HOLD_RELEASED_STATE_KEY, released.getStateKey());
    }

    @Test
    public void testDeleteHold() throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        
        try {
            StatusInfo status = holdService.deleteHold("Hold-Blah", callContext);
            fail("Did not get a DoesNotExistException when expected");
        } catch (DoesNotExistException e) {
            // expected
        }

        StatusInfo info = holdService.deleteHold("Hold-1", callContext);
        assertNotNull(info);
        assertTrue(info.getIsSuccess());

        try {
            HoldInfo deleted = holdService.getHold("Hold-1", callContext);
            fail("should not exist");
        } catch (DoesNotExistException ex) {
            // expected
        }

    }
}
