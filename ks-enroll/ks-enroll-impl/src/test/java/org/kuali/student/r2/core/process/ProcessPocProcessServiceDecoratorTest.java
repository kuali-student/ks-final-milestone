/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.process;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.service.ProcessService;
import org.kuali.student.r2.core.process.service.impl.ProcessServiceDataLoader;
import org.kuali.student.r2.core.process.service.impl.ProcessServiceMockImpl;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author nwright
 */
public class ProcessPocProcessServiceDecoratorTest {

    public ProcessPocProcessServiceDecoratorTest() {
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

    //    @Test
    public void testPocMethods() throws Exception {
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("POC-tester");

        ProcessService processService = new ProcessServiceMockImpl();
        processService = new ProcessPocProcessServiceDecorator(processService);

        ProcessInfo process = null;
        process = processService.getProcess(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, context);
        assertEquals(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, process.getKey());
        process = processService.getProcess(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, context);
        assertEquals(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, process.getKey());

        CheckInfo check = null;
        check = processService.getCheck(ProcessServiceDataLoader.CHECK_ID_IS_ALIVE, context);
        assertEquals (ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, check.getTypeKey());
        
        check = processService.getCheck(ProcessServiceDataLoader.CHECK_ID_HAS_OVERDUE_LIBRARY_BOOK, context);
        assertEquals (ProcessServiceConstants.HOLD_CHECK_TYPE_KEY, check.getTypeKey());

        List<InstructionInfo> instructions = null;
        InstructionInfo instruction = null;
        instructions = processService.getInstructionsByProcess(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, context);
        assertEquals(1, instructions.size());
        instruction = instructions.get(0);
        assertEquals (ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, instruction.getProcessKey());
        assertEquals (new Integer (1), instruction.getPosition());
        assertEquals (Boolean.FALSE, instruction.getContinueOnFail());
        assertEquals (Boolean.FALSE, instruction.getIsExemptible());
        assertEquals (Boolean.FALSE, instruction.getIsWarning());
        assertEquals (ProcessServiceDataLoader.CHECK_ID_IS_ALIVE, instruction.getCheckId());
        
        instructions = processService.getInstructionsByProcess(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, context);
        assertEquals(5, instructions.size());
        
        instructions = processService.getInstructionsForEvaluation(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, context);
        assertEquals(1, instructions.size());
        
        instructions = processService.getInstructionsForEvaluation(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, context);
        assertEquals(5, instructions.size());
        assertEquals (new Integer (1), instructions.get(0).getPosition());
        assertEquals (new Integer (3), instructions.get(1).getPosition());
        assertEquals (new Integer (4), instructions.get(2).getPosition());
        assertEquals (new Integer (5), instructions.get(3).getPosition());
        assertEquals (new Integer (9), instructions.get(4).getPosition());        
    }
}
