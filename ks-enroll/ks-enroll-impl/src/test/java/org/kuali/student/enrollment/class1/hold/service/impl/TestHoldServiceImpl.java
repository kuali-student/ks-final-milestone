package org.kuali.student.enrollment.class1.hold.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class1.hold.service.decorators.HoldServiceValidationDecorator;
import org.kuali.student.enrollment.hold.dto.IssueInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
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
        assertEquals("1", issue.getOrganizationId());
        
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
        List<IssueInfo> issues = holdService.getIssuesByOrg("1", callContext);
        
        assertNotNull(issues);
        assertEquals(1, issues.size());
        
        // check that the expected issue was returned
        IssueInfo issue = issues.iterator().next();
        
        assertEquals("Hold-Issue-1", issue.getId());
        
        // now make sure an empty list is returned for a non matching org id
        List<IssueInfo> shouldBeEmpty = holdService.getIssuesByOrg("fakeOrg", callContext);
        
        assertTrue(shouldBeEmpty.isEmpty());
    }
}
