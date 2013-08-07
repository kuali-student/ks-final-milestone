/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.process;

import org.kuali.student.r2.core.constants.HoldServiceConstants;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.r2.core.class1.hold.mock.HoldServiceMockImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.service.HoldService;

import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class ProcessPocHoldServiceDecoratorTest {

    public ProcessPocHoldServiceDecoratorTest() {
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
    public void testPocMethods() throws Exception {
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("POC-tester");

        HoldService holdService = new HoldServiceMockImpl();
        holdService = new ProcessPocHoldServiceDecorator(holdService);
        List<AppliedHoldInfo> holds = null;
            holds = holdService.getActiveAppliedHoldsByIssueAndPerson(HoldServiceConstants.ISSUE_KEY_BOOK_OVERDUE, 
                    ProcessPocConstants.PERSON_ID_BETTY_MARTIN_2005, context);

        assertEquals(1, holds.size());

        try {
            holds = holdService.getActiveAppliedHoldsByIssueAndPerson(HoldServiceConstants.ISSUE_KEY_UNPAID_TUITION_PRIOR_TERM, 
                    ProcessPocConstants.PERSON_ID_CLIFFORD_RIDDLE_2397, context);
        } catch (Exception ex) {
            throw new RuntimeException("unexpected", ex);
        }
        assertEquals(1, holds.size());

        try {
            holds = holdService.getActiveAppliedHoldsByIssueAndPerson(HoldServiceConstants.ISSUE_KEY_BOOK_OVERDUE, 
                    ProcessPocConstants.PERSON_ID_NINA_WELCH_2166, context);
        } catch (Exception ex) {
            throw new RuntimeException("unexpected", ex);
        }
        assertEquals(1, holds.size());

        try {
            holds = holdService.getActiveAppliedHoldsByIssueAndPerson(HoldServiceConstants.ISSUE_KEY_UNPAID_TUITION_PRIOR_TERM,
                    ProcessPocConstants.PERSON_ID_NINA_WELCH_2166, context);
        } catch (Exception ex) {
            throw new RuntimeException("unexpected", ex);
        }
        assertEquals(1, holds.size());
    }
}
