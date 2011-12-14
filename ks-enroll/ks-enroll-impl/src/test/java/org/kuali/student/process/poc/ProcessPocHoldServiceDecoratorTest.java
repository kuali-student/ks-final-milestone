/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.process.poc;

import org.kuali.student.process.poc.ProcessPocConstants;
import org.kuali.student.process.poc.ProcessPocHoldServiceDecorator;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.enrollment.classI.hold.mock.HoldServiceMockImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.hold.dto.HoldInfo;
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
    public void testPocMethods() {
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("POC-tester");
        
        HoldService holdService = new HoldServiceMockImpl();
        holdService = new ProcessPocHoldServiceDecorator(holdService);
        List<HoldInfo> holds = null;
        try {
            // TODO: fix this
            holds = holdService.getActiveHoldsByIssueAndPerson("fixme", ProcessPocConstants.PERSON_ID_NINA_WELCH_2166, context);
        } catch (Exception ex) {
            throw new RuntimeException("unexpected", ex);
        }
        assertEquals (holds.size(), 1);
    }
}
