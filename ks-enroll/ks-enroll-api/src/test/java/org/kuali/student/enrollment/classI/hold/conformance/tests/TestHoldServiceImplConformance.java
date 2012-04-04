package org.kuali.student.enrollment.classI.hold.conformance.tests;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;


import org.kuali.student.enrollment.classI.hold.mock.HoldServiceMockImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.HoldInfo;
import org.kuali.student.r2.core.hold.dto.IssueInfo;
import org.kuali.student.r2.core.hold.service.HoldService;

@Ignore
public class TestHoldServiceImplConformance {

    public TestHoldServiceImplConformance() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    private HoldService service = new HoldServiceMockImpl ();

    public HoldService getService() {

        return service;
    }

    public void setService(HoldService service) {
        this.service = service;
    }
    private static final String TEST_PRINCIPAL_ID1 = "testPrincipalId1";
    private static final String TEST_PRINCIPAL_ID2 = "testPrincipalId2";

    private ContextInfo getContext() {
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("testPrincipalId1");
        return context;
    }

    @Test
    public void testHoldCrud() throws Exception {

        System.out.println("holdCrud");
        ContextInfo context = getContext();
        // create
        HoldInfo info = new HoldInfo();
        info.setIssueId("my.issue");
        info.setTypeKey(HoldServiceConstants.STUDENT_HOLD_TYPE_KEY);
        info.setStateKey(HoldServiceConstants.HOLD_RELEASED_STATE_KEY);
        info.setPersonId("person1");
        Date before = new Date();
        HoldInfo result = service.createHold(info.getPersonId(), info.getIssueId(), info.getTypeKey(), info, context);
        Date after = new Date();
        if (result == info) {
            fail("returned object should not be the same as the one passed in");
        }
        assertNotNull(result.getId());
        assertEquals(info.getTypeKey(), result.getTypeKey());
        assertEquals(info.getStateKey(), result.getStateKey());
        assertEquals(info.getPersonId(), result.getPersonId());
        assertEquals(TEST_PRINCIPAL_ID1, result.getMeta().getCreateId());
        if (result.getMeta().getCreateTime().before(before)) {
            fail("create time should not be before the call");
        }
        if (result.getMeta().getCreateTime().after(after)) {
            fail("create time should not be after the call");
        }
        if (result.getMeta().getUpdateTime().before(before)) {
            fail("update time should not be before the call");
        }
        if (result.getMeta().getUpdateTime().after(after)) {
            fail("update time should not be after the call");
        }
        assertEquals(TEST_PRINCIPAL_ID1, result.getMeta().getUpdateId());
        assertNotNull(result.getMeta().getVersionInd());

        // READ/get
        info = new HoldInfo(result);

        result = service.getHold(info.getId(), context);
        assertEquals(result.getId(), info.getId());
        assertEquals(result.getTypeKey(), info.getTypeKey());
        assertEquals(result.getStateKey(), info.getStateKey());
        assertEquals(result.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(result.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(result.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(result.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());
        assertEquals(result.getMeta().getVersionInd(), info.getMeta().getVersionInd());
        assertEquals(result.getMeta().getCreateId(), info.getMeta().getCreateId());

        // update
        info = new HoldInfo(result);
        info.setEffectiveDate(new Date());
        context.setPrincipalId(TEST_PRINCIPAL_ID2);
        before = new Date();
        result = service.updateHold(info.getId(), info, context);
        after = new Date();
        if (result == info) {
            fail("returned object should not be the same as the one passed in");
        }
        assertEquals(info.getId(), result.getId());
        assertEquals(info.getTypeKey(), result.getTypeKey());
        assertEquals(info.getStateKey(), result.getStateKey());
        assertEquals(info.getPersonId(), result.getPersonId());
        assertEquals(TEST_PRINCIPAL_ID1, result.getMeta().getCreateId());
        if (result.getMeta().getCreateTime().after(before)) {
            fail("create time should be before the update call");
        }
        if (result.getMeta().getUpdateTime().before(before)) {
            fail("update time should not be before the call");
        }
        if (result.getMeta().getUpdateTime().after(after)) {
            fail("update time should not be after the call");
        }
        assertEquals(TEST_PRINCIPAL_ID2, result.getMeta().getUpdateId());
        if (info.getMeta().getVersionInd().compareTo(result.getMeta().getVersionInd()) >= 0) {
            fail("version ind should be lexically greater than the old version id");
        }

        // TODO: delete

    }
    
    
     @Test
    public void testIssueCrud() throws Exception {

        System.out.println("issueCrud");
        ContextInfo context = getContext();
        // create
        IssueInfo info = new IssueInfo();
        info.setId(HoldServiceConstants.ISSUE_KEY_BOOK_OVERDUE);
        info.setTypeKey(HoldServiceConstants.OVERDUE_LIBRARY_MATERIALS_ISSUE_TYPE_KEY);
        info.setStateKey(HoldServiceConstants.ISSUE_ACTIVE_STATE_KEY);
        info.setName("overdue books");
        Date before = new Date();
        IssueInfo result = service.createIssue(info.getTypeKey(), info, context);
        Date after = new Date();
        if (result == info) {
            fail("returned object should not be the same as the one passed in");
        }
        assertEquals(info.getTypeKey(), result.getTypeKey());
        assertEquals(info.getStateKey(), result.getStateKey());
        assertEquals(info.getName(), result.getName());        
        assertEquals(TEST_PRINCIPAL_ID1, result.getMeta().getCreateId());
        if (result.getMeta().getCreateTime().before(before)) {
            fail("create time should not be before the call");
        }
        if (result.getMeta().getCreateTime().after(after)) {
            fail("create time should not be after the call");
        }
        if (result.getMeta().getUpdateTime().before(before)) {
            fail("update time should not be before the call");
        }
        if (result.getMeta().getUpdateTime().after(after)) {
            fail("update time should not be after the call");
        }
        assertEquals(TEST_PRINCIPAL_ID1, result.getMeta().getUpdateId());
        assertNotNull(result.getMeta().getVersionInd());

        // READ/get
        info = new IssueInfo(result);

        result = service.getIssue(info.getId(), context);
        assertEquals(result.getId(), info.getId ());
        assertEquals(result.getTypeKey(), info.getTypeKey());
        assertEquals(result.getStateKey(), info.getStateKey());
        assertEquals(info.getName(), result.getName());        
        assertEquals(result.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(result.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(result.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(result.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());
        assertEquals(result.getMeta().getVersionInd(), info.getMeta().getVersionInd());
        assertEquals(result.getMeta().getCreateId(), info.getMeta().getCreateId());

        // update
        info = new IssueInfo(result);
        info.setName("new issue name");
        context.setPrincipalId(TEST_PRINCIPAL_ID2);
        before = new Date();
        result = service.updateIssue(info.getId(), info, context);
        after = new Date();
        if (result == info) {
            fail("returned object should not be the same as the one passed in");
        }
        assertEquals(info.getId(), result.getId());
        assertEquals(info.getTypeKey(), result.getTypeKey());
        assertEquals(info.getStateKey(), result.getStateKey());
        assertEquals(info.getName(), result.getName());
        assertEquals(TEST_PRINCIPAL_ID1, result.getMeta().getCreateId());
        if (result.getMeta().getCreateTime().after(before)) {
            fail("create time should be before the update call");
        }
        if (result.getMeta().getUpdateTime().before(before)) {
            fail("update time should not be before the call");
        }
        if (result.getMeta().getUpdateTime().after(after)) {
            fail("update time should not be after the call");
        }
        assertEquals(TEST_PRINCIPAL_ID2, result.getMeta().getUpdateId());
        if (info.getMeta().getVersionInd().compareTo(result.getMeta().getVersionInd()) >= 0) {
            fail("version ind should be lexically greater than the old version id");
        }

        // TODO: delete

    }
}
