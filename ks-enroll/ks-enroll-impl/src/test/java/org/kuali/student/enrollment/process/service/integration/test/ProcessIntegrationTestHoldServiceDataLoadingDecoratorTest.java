/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.process.service.integration.test;

import org.kuali.student.r2.core.constants.HoldServiceConstants;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.core.hold.service.impl.HoldServiceMapImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.service.HoldService;

import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class ProcessIntegrationTestHoldServiceDataLoadingDecoratorTest {

    public ProcessIntegrationTestHoldServiceDataLoadingDecoratorTest() {
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

    @Test
    public void testTestMethods() throws Exception {
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("Test-tester");

        HoldService holdService = new HoldServiceMapImpl();
        holdService = new ProcessIntegrationTestHoldServiceDataLoadingDecorator(holdService);
        List<AppliedHoldInfo> holds = null;
            holds = holdService.getActiveAppliedHoldsByIssueAndPerson(HoldServiceConstants.ISSUE_KEY_BOOK_OVERDUE, 
                    ProcessIntegrationTestConstants.PERSON_ID_BETTY_MARTIN_2005, context);

        assertEquals(1, holds.size());

        try {
            holds = holdService.getActiveAppliedHoldsByIssueAndPerson(HoldServiceConstants.ISSUE_KEY_UNPAID_TUITION_PRIOR_TERM, 
                    ProcessIntegrationTestConstants.PERSON_ID_CLIFFORD_RIDDLE_2397, context);
        } catch (Exception ex) {
            throw new RuntimeException("unexpected", ex);
        }
        assertEquals(1, holds.size());

        try {
            holds = holdService.getActiveAppliedHoldsByIssueAndPerson(HoldServiceConstants.ISSUE_KEY_BOOK_OVERDUE, 
                    ProcessIntegrationTestConstants.PERSON_ID_NINA_WELCH_2166, context);
        } catch (Exception ex) {
            throw new RuntimeException("unexpected", ex);
        }
        assertEquals(1, holds.size());

        try {
            holds = holdService.getActiveAppliedHoldsByIssueAndPerson(HoldServiceConstants.ISSUE_KEY_UNPAID_TUITION_PRIOR_TERM,
                    ProcessIntegrationTestConstants.PERSON_ID_NINA_WELCH_2166, context);
        } catch (Exception ex) {
            throw new RuntimeException("unexpected", ex);
        }
        assertEquals(1, holds.size());
    }
}
