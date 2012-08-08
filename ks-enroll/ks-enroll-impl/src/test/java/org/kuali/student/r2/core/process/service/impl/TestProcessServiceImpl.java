package org.kuali.student.r2.core.process.service.impl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.service.ProcessService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:process-test-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
//@Ignore
//todo fix these tests and unignore. They are breaking and process service is not currently in scope as of 7/16/2012
public class TestProcessServiceImpl {

    @Resource(name="processServiceAuthDecorator")
    public ProcessService processService;

    public ContextInfo context = new ContextInfo();

    
    @Before
    public void setUp() {
        context = new ContextInfo(context);
        context.setPrincipalId("123");
    }

    @Test
    public void testServiceValidationSetup() {
        assertNotNull(processService);
    }

    @Test
    public void testCrudProcess() throws DependentObjectsExistException, InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, DataValidationErrorException, AlreadyExistsException, ReadOnlyException, VersionMismatchException {

        // Read
//        ProcessInfo existingProcess = processService.getProcess("kuali.process.registration.basic.eligibility", context);
//        assertNotNull(existingProcess);
//        assertNotNull(existingProcess.getStateKey());
//        assertNotNull(existingProcess.getTypeKey());

        final String processId = "newProcess";

        // Create & Read
        ProcessInfo process = new ProcessInfo();
        process.setOwnerOrgId("Owner1");
        process.setStateKey(ProcessServiceConstants.PROCESS_ACTIVE_STATE_KEY);
        processService.createProcess(processId, ProcessServiceConstants.PROCESS_TYPE_KEY, process, context);
        process = processService.getProcess(processId, context);
        assertNotNull(process);
        assertEquals("Owner1", process.getOwnerOrgId());
        assertEquals(ProcessServiceConstants.PROCESS_TYPE_KEY, process.getTypeKey());
        assertEquals(ProcessServiceConstants.PROCESS_ACTIVE_STATE_KEY, process.getStateKey());

        // Update
        process.setOwnerOrgId("Owner2");
        process.setStateKey(ProcessServiceConstants.PROCESS_DISABLED_STATE_KEY);
        processService.updateProcess(processId, process, context);
        process = processService.getProcess(processId, context);
        assertNotNull(process);
        assertEquals("Owner2", process.getOwnerOrgId());
        assertEquals(ProcessServiceConstants.PROCESS_TYPE_KEY, process.getTypeKey());
        assertEquals("kuali.process.process.lifecycle", process.getStateKey());

        // Delete
        processService.deleteProcess(processId, context);
        try {
            processService.getProcess(processId, context);
            fail("Process not deleted properly.");
        } catch (DoesNotExistException e) {
            // expected, do nothing
        }

    }

    @Test
    public void testCrudCheck() throws DependentObjectsExistException, InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, DataValidationErrorException, AlreadyExistsException, ReadOnlyException, VersionMismatchException {

        // Read
//        CheckInfo existingCheck = processService.getCheck("kuali.check.is.alive", context);
//        assertNotNull(existingCheck);
//        assertNotNull(existingCheck.getAgendaId());
//        assertNotNull(existingCheck.getHoldIssueId());
//        assertNotNull(existingCheck.getMilestoneTypeKey());
//        assertNull(existingCheck.getChildProcessKey());
//        assertNotNull(existingCheck.getTypeKey());
//        assertNotNull(existingCheck.getStateKey());


        // Create Process for testing
        final String processId = "kuali.process.registration.basic.eligibility";
        ProcessInfo process = new ProcessInfo();
        process.setOwnerOrgId("Owner1");
        process.setStateKey(ProcessServiceConstants.PROCESS_ACTIVE_STATE_KEY);
        processService.createProcess(processId, ProcessServiceConstants.PROCESS_TYPE_KEY, process, context);
        // Create & Read
        CheckInfo check = new CheckInfo();
        check.setAgendaId("AgendaId-1");
        check.setHoldIssueId("Hold-Issue-2");
        check.setMilestoneTypeKey("milestoneTypeKey-1");
        check.setChildProcessKey("kuali.process.registration.basic.eligibility");
        check.setStateKey(ProcessServiceConstants.PROCESS_CHECK_STATE_ACTIVE);
        CheckInfo checkR = processService.createCheck(ProcessServiceConstants.HOLD_CHECK_TYPE_KEY, check, context);
        check = processService.getCheck(checkR.getId(), context);
        assertNotNull(check);
        assertEquals("AgendaId-1", check.getAgendaId());
        assertEquals("Hold-Issue-2", check.getHoldIssueId());
        assertEquals("milestoneTypeKey-1", check.getMilestoneTypeKey());
        assertEquals("kuali.process.registration.basic.eligibility", check.getChildProcessKey());
        assertEquals(ProcessServiceConstants.HOLD_CHECK_TYPE_KEY, check.getTypeKey());
        assertEquals("kuali.process.check.lifecycle",check.getStateKey());

        // Update
        check.setHoldIssueId("Hold-Issue-1");
        check.setMilestoneTypeKey("milestoneTypeKey-2");
        check.setChildProcessKey(null);
        check.setAgendaId("AgendaId-2");
        check.setTypeKey(ProcessServiceConstants.START_DATE_CHECK_TYPE_KEY);
        check.setStateKey(ProcessServiceConstants.PROCESS_CHECK_STATE_INACTIVE);
        processService.updateCheck(check.getId(), check, context);
        check = processService.getCheck(check.getId(), context);
        assertNotNull(check);
        assertEquals("AgendaId-2", check.getAgendaId());
        assertEquals("Hold-Issue-1", check.getHoldIssueId());
        assertEquals("milestoneTypeKey-2", check.getMilestoneTypeKey());
        assertNull(check.getChildProcessKey());
        assertEquals(ProcessServiceConstants.START_DATE_CHECK_TYPE_KEY, check.getTypeKey());
        assertEquals("kuali.process.check.lifecycle", check.getStateKey());

        // Delete
        processService.deleteCheck(check.getId(), context);
        try {
            processService.getCheck(check.getId(), context);
            fail("Check not deleted properly.");
        } catch (DoesNotExistException e) {
            // expected, do nothing
        }
    }

    @Test
    public void testCrudInstruction() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, DataValidationErrorException, ReadOnlyException, VersionMismatchException, AlreadyExistsException {

        // Read
//        InstructionInfo existingInstruction = processService.getInstruction("KUALI.PROCESS.REGISTRATION.BASIC.ELIGIBILITY.1", context);
//        assertNotNull(existingInstruction);
//
//        assertNotNull(existingInstruction.getAppliedAtpTypeKeys());
//        assertTrue(existingInstruction.getAppliedAtpTypeKeys().isEmpty()); // TODO
//        assertNotNull(existingInstruction.getAppliedPopulationId());
//        assertFalse(existingInstruction.getAppliedPopulationId().isEmpty());
//        // assertEquals(1, existingInstruction.getAppliedPopulationKeys().size());
//        // assertTrue(existingInstruction.getAppliedPopulationKeys().contains("kuali.population.everyone"));
//        assertEquals(existingInstruction.getAppliedPopulationId(), "kuali.population.everyone");
//        assertNotNull(existingInstruction.getAttributes());
//        assertNotNull(existingInstruction.getCheckId());
//        assertNotNull(existingInstruction.getContinueOnFail());
//        assertNotNull(existingInstruction.getEffectiveDate());
//        assertNull(existingInstruction.getExpirationDate());
//        assertNotNull(existingInstruction.getId());
//        assertNotNull(existingInstruction.getIsExemptible());
//        assertNotNull(existingInstruction.getIsWarning());
//        assertNotNull(existingInstruction.getMessage());
//        assertNotNull(existingInstruction.getPosition());
//        assertNotNull(existingInstruction.getProcessKey());
//        assertNotNull(existingInstruction.getStateKey());
//        assertNotNull(existingInstruction.getTypeKey());


        // Create Process and Check for testing
        String processId = "kuali.process.registration.eligibility.for.term";
        ProcessInfo process = new ProcessInfo();
        process.setOwnerOrgId("Owner1");
        process.setStateKey(ProcessServiceConstants.PROCESS_ACTIVE_STATE_KEY);
        processService.createProcess(processId, ProcessServiceConstants.PROCESS_TYPE_KEY, process, context);
        CheckInfo check = new CheckInfo();
        check.setId("kuali.check.has.not.paid.bill.from.prior.term");
        check.setAgendaId("AgendaId-1");
        check.setHoldIssueId("Hold-Issue-2");
        check.setMilestoneTypeKey("milestoneTypeKey-1");
        check.setChildProcessKey(processId);
        check.setStateKey(ProcessServiceConstants.PROCESS_CHECK_STATE_ACTIVE);
        CheckInfo checkR = processService.createCheck(ProcessServiceConstants.HOLD_CHECK_TYPE_KEY, check, context);

        // Create & Read
        InstructionInfo instruction = new InstructionInfo();
        instruction.setAppliedAtpTypeKeys(new ArrayList<String>(){{add("kuali.atp.type.Fall");}});
        instruction.setAppliedPopulationId("Population-1");
        // instruction.setAppliedPopulationKeys(new ArrayList<String>(){{add("Population-1");}});
//        instruction.setAttributes();
        instruction.setCheckId("kuali.check.has.not.paid.bill.from.prior.term");
        instruction.setContinueOnFail(Boolean.TRUE);
//        instruction.setEffectiveDate();
//        instruction.setExpirationDate();
        instruction.setIsExemptible(Boolean.TRUE);
        instruction.setIsWarning(Boolean.TRUE);
        instruction.setMessage(new RichTextInfo(){{setPlain("Message-1");setFormatted("<p>Message-1<p>");}});
//        instruction.setMeta();
        instruction.setPosition(5);
        instruction.setProcessKey("kuali.process.registration.eligibility.for.term");
        instruction.setStateKey(ProcessServiceConstants.INSTRUCTION_ACTIVE_STATE_KEY);
        instruction = processService.createInstruction(instruction.getProcessKey(), instruction.getCheckId(), ProcessServiceConstants.INSTRUCTION_TYPE_KEY, instruction, context);
        String instructionId = instruction.getId();
        instruction = processService.getInstruction(instructionId, context);
        assertNotNull(instruction.getAppliedAtpTypeKeys());
        assertEquals(1, instruction.getAppliedAtpTypeKeys().size());
        assertTrue(instruction.getAppliedAtpTypeKeys().contains("kuali.atp.type.Fall"));
        assertNotNull(instruction.getAppliedPopulationId());
//        assertEquals(1, instruction.getAppliedPopulationKeys().size());
//        assertTrue(instruction.getAppliedPopulationKeys().contains("Population-1"));
        assertEquals("kuali.check.has.not.paid.bill.from.prior.term", instruction.getCheckId());
        assertTrue(instruction.getContinueOnFail());
        assertNotNull(instruction.getId());
        assertTrue(instruction.getIsExemptible());
        assertTrue(instruction.getIsWarning());
        assertEquals("Message-1", instruction.getMessage().getPlain());
        assertEquals(new Integer(5), instruction.getPosition());
        assertEquals("kuali.process.registration.eligibility.for.term", instruction.getProcessKey());
        assertEquals(ProcessServiceConstants.INSTRUCTION_ACTIVE_STATE_KEY, instruction.getStateKey());
        assertEquals(ProcessServiceConstants.INSTRUCTION_TYPE_KEY, instruction.getTypeKey());

        // Create Process and Check for testing
        processId = "kuali.process.registration.register.for.courses";
        process = new ProcessInfo();
        process.setOwnerOrgId("Owner1");
        process.setStateKey(ProcessServiceConstants.PROCESS_ACTIVE_STATE_KEY);
        processService.createProcess(processId, ProcessServiceConstants.PROCESS_TYPE_KEY, process, context);
        check = new CheckInfo();
        check.setId("kuali.check.has.overdue.library.book");
        check.setAgendaId("AgendaId-1");
        check.setHoldIssueId("Hold-Issue-2");
        check.setMilestoneTypeKey("milestoneTypeKey-1");
        check.setChildProcessKey(processId);
        check.setStateKey(ProcessServiceConstants.PROCESS_CHECK_STATE_ACTIVE);
        processService.createCheck(ProcessServiceConstants.HOLD_CHECK_TYPE_KEY, check, context);

        // Update
        instruction.setAppliedAtpTypeKeys(new ArrayList<String>(){{add("kuali.atp.type.Spring");}});
        instruction.setAppliedPopulationId("Population-2");
        // instruction.setAppliedPopulationKeys(new ArrayList<String>(){{add("Population-2");}});
//        instruction.setAttributes();
        instruction.setCheckId("kuali.check.has.overdue.library.book");
        instruction.setContinueOnFail(Boolean.FALSE);
//        instruction.setEffectiveDate();
//        instruction.setExpirationDate();
        instruction.setIsExemptible(Boolean.FALSE);
        instruction.setIsWarning(Boolean.FALSE);
        instruction.setMessage(new RichTextInfo(){{setPlain("Message-2");setFormatted("<p>Message-2<p>");}});
//        instruction.setMeta();
        instruction.setPosition(6);
        instruction.setProcessKey("kuali.process.registration.register.for.courses");
        instruction.setStateKey(ProcessServiceConstants.INSTRUCTION_DISABLED_STATE_KEY);
        instruction.setTypeKey(ProcessServiceConstants.INSTRUCTION_TYPE_KEY);
        processService.updateInstruction(instruction.getId(), instruction, context);
        instruction = processService.getInstruction(instructionId, context);
        assertNotNull(instruction.getAppliedAtpTypeKeys());
        assertEquals(1, instruction.getAppliedAtpTypeKeys().size());
//        assertTrue(instruction.getAppliedAtpTypeKeys().contains("kuali.atp.type.Spring"));
        assertNotNull(instruction.getAppliedPopulationId());
//        assertEquals(1, instruction.getAppliedPopulationKeys().size());
//        assertTrue(instruction.getAppliedPopulationKeys().contains("Population-1"));
        assertEquals("kuali.check.has.overdue.library.book", instruction.getCheckId());
        assertFalse(instruction.getContinueOnFail());
        assertNotNull(instruction.getId());
        assertFalse(instruction.getIsExemptible());
        assertFalse(instruction.getIsWarning());
        assertEquals("Message-2", instruction.getMessage().getPlain());
        assertEquals(new Integer(6), instruction.getPosition());
        assertEquals("kuali.process.registration.register.for.courses", instruction.getProcessKey());
        assertEquals(ProcessServiceConstants.INSTRUCTION_DISABLED_STATE_KEY, instruction.getStateKey());
        assertEquals(ProcessServiceConstants.INSTRUCTION_TYPE_KEY, instruction.getTypeKey());

        // Test get instructions by process
        List<InstructionInfo> instructions = processService.getInstructionsByProcess(processId, context);
        if(instructions != null && !(instructions.isEmpty())){
            //expected
        }else{
            fail("getInstructionsByProcess failed to return results");
        }

        // Delete
        processService.deleteInstruction(instruction.getId(), context);
        try {
            processService.getInstruction(instruction.getId(), context);
            fail("Insruction not deleted properly.");
        } catch (DoesNotExistException e) {
            // expected, do nothing
        }
    }

 }