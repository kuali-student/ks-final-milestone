/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.process.service.integration.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.core.process.service.impl.ProcessServiceMapImpl;
import org.kuali.student.r2.core.process.service.ProcessService;

import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author nwright
 */
public class ProcessIntegrationTestProcessServiceDataLoadingDecoratorTest {

    public ProcessIntegrationTestProcessServiceDataLoadingDecoratorTest() {
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

        ProcessService processService = new ProcessServiceMapImpl();
        processService = new ProcessIntegrationTestProcessServiceDataLoadingDecorator(processService);

        ProcessInfo process;
        process = processService.getProcess(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, context);
        assertEquals(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, process.getKey());
        process = processService.getProcess(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, context);
        assertEquals(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, process.getKey());

        CheckInfo check;
        check = processService.getCheck(ProcessIntegrationTestConstants.CHECK_ID_IS_ALIVE, context);
        assertEquals(ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, check.getTypeKey());

        check = processService.getCheck(ProcessIntegrationTestConstants.CHECK_ID_HAS_OVERDUE_LIBRARY_BOOK, context);
        assertEquals(ProcessServiceConstants.HOLD_CHECK_TYPE_KEY, check.getTypeKey());

        List<InstructionInfo> instructions;
        InstructionInfo instruction;
        instructions = processService.getInstructionsByProcess(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, context);
        assertEquals(1, instructions.size());
        instruction = instructions.get(0);
        assertEquals(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, instruction.getProcessKey());
        assertEquals(new Integer(1), instruction.getPosition());
        assertEquals(Boolean.FALSE, instruction.getContinueOnFail());
        assertEquals(Boolean.FALSE, instruction.getIsExemptible());
        assertEquals(Boolean.FALSE, instruction.getIsWarning());
        assertEquals(ProcessIntegrationTestConstants.CHECK_ID_IS_ALIVE, instruction.getCheckId());

        instructions = processService.getInstructionsByProcess(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, context);
        assertEquals(5, instructions.size());

        instructions = processService.getInstructionsForEvaluation(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, context);
        assertEquals(1, instructions.size());

        instructions = processService.getInstructionsForEvaluation(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM,
                context);
        assertEquals(5, instructions.size());
        assertEquals(new Integer(1), instructions.get(0).getPosition());
        assertEquals(new Integer(3), instructions.get(1).getPosition());
        assertEquals(new Integer(4), instructions.get(2).getPosition());
        assertEquals(new Integer(5), instructions.get(3).getPosition());
        assertEquals(new Integer(9), instructions.get(4).getPosition());
        
        instructions = processService.getInstructionsForEvaluation(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSE,
                context);
        assertEquals(3, instructions.size());
        assertEquals(new Integer(1), instructions.get(0).getPosition());
        assertEquals(new Integer(2), instructions.get(1).getPosition());
        assertEquals(new Integer(3), instructions.get(2).getPosition());
    }
}
