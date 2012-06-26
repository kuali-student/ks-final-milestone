package org.kuali.student.r2.core.process.service.impl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.ProcessServiceConstants;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:process-test-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
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
    public void testCrudProcess() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, DataValidationErrorException, AlreadyExistsException, ReadOnlyException, VersionMismatchException {

        // Read
        ProcessInfo existingProcess = processService.getProcess("kuali.process.registration.basic.eligibility", context);
        assertNotNull(existingProcess);
        assertNotNull(existingProcess.getStateKey());
        assertNotNull(existingProcess.getTypeKey());

        final String processId = "newProcess";

        // Create
        ProcessInfo process = new ProcessInfo();
        process.setOwnerOrgId("Owner1");
        process.setTypeKey(ProcessServiceConstants.PROCESS_TYPE_KEY);
        process.setStateKey(ProcessServiceConstants.PROCESS_ENABLED_STATE_KEY);
        processService.createProcess(processId, process, context);
        process = processService.getProcess(processId, context);
        assertNotNull(process);
        assertEquals("Owner1", process.getOwnerOrgId());
        assertEquals(ProcessServiceConstants.PROCESS_TYPE_KEY, process.getTypeKey());
        assertEquals(ProcessServiceConstants.PROCESS_ENABLED_STATE_KEY, process.getStateKey());

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
    public void testCrudCheck() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, DataValidationErrorException, AlreadyExistsException, ReadOnlyException, VersionMismatchException {

        // Read
        CheckInfo existingCheck = processService.getCheck("kuali.check.is.alive", context);
        assertNotNull(existingCheck);
        assertNotNull(existingCheck.getAgendaId());
        assertNotNull(existingCheck.getIssueId());
        assertNotNull(existingCheck.getMilestoneTypeKey());
        assertNull(existingCheck.getProcessKey());
        assertNotNull(existingCheck.getTypeKey());
        assertNotNull(existingCheck.getStateKey());

        String checkKey = "newCheck";

        // Create
        CheckInfo check = new CheckInfo();
        check.setAgendaId("AgendaId-1");
        check.setIssueId("Hold-Issue-2");
        check.setMilestoneTypeKey("milestoneTypeKey-1");
        check.setProcessKey("kuali.process.registration.basic.eligibility");
        check.setTypeKey(ProcessServiceConstants.HOLD_CHECK_TYPE_KEY);
        check.setStateKey(ProcessServiceConstants.PROCESS_CHECK_STATE_ENABLED);
        processService.createCheck(checkKey, check, context);
        check = processService.getCheck(checkKey, context);
        assertNotNull(check);
        assertEquals("AgendaId-1", check.getAgendaId());
        assertEquals("Hold-Issue-2", check.getIssueId());
        assertEquals("milestoneTypeKey-1", check.getMilestoneTypeKey());
        assertEquals("kuali.process.registration.basic.eligibility", check.getProcessKey());
        assertEquals(ProcessServiceConstants.HOLD_CHECK_TYPE_KEY, check.getTypeKey());
        assertEquals("kuali.process.check.lifecycle",check.getStateKey());

        // Update
        check.setIssueId("Hold-Issue-1");
        check.setMilestoneTypeKey("milestoneTypeKey-2");
        check.setProcessKey(null);
        check.setAgendaId("AgendaId-2");
        check.setTypeKey(ProcessServiceConstants.START_DATE_CHECK_TYPE_KEY);
        check.setStateKey(ProcessServiceConstants.PROCESS_CHECK_STATE_INACTIVE);
        processService.updateCheck(check.getKey(), check, context);
        check = processService.getCheck(checkKey, context);
        assertNotNull(check);
        assertEquals("AgendaId-2", check.getAgendaId());
        assertEquals("Hold-Issue-1", check.getIssueId());
        assertEquals("milestoneTypeKey-2", check.getMilestoneTypeKey());
        assertNull(check.getProcessKey());
        assertEquals(ProcessServiceConstants.START_DATE_CHECK_TYPE_KEY, check.getTypeKey());
        assertEquals("kuali.process.check.lifecycle", check.getStateKey());

        // Delete
        processService.deleteCheck(checkKey, context);
        try {
            processService.getCheck(checkKey, context);
            fail("Check not deleted properly.");
        } catch (DoesNotExistException e) {
            // expected, do nothing
        }
    }

    @Test
    public void testCrudInstruction() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, DataValidationErrorException, ReadOnlyException, VersionMismatchException {

        // Read
        InstructionInfo existingInstruction = processService.getInstruction("KUALI.PROCESS.REGISTRATION.BASIC.ELIGIBILITY.1", context);
        assertNotNull(existingInstruction);

        assertNotNull(existingInstruction.getAppliedAtpTypeKeys());
        assertTrue(existingInstruction.getAppliedAtpTypeKeys().isEmpty()); // TODO
        assertNotNull(existingInstruction.getAppliedPopulationKeys());
        assertFalse(existingInstruction.getAppliedPopulationKeys().isEmpty());
        assertEquals(1, existingInstruction.getAppliedPopulationKeys().size());
        assertTrue(existingInstruction.getAppliedPopulationKeys().contains("kuali.population.everyone"));
        assertNotNull(existingInstruction.getAttributes());
        assertNotNull(existingInstruction.getCheckKey());
        assertNotNull(existingInstruction.getContinueOnFail());
        assertNotNull(existingInstruction.getEffectiveDate());
        assertNull(existingInstruction.getExpirationDate());
        assertNotNull(existingInstruction.getId());
        assertNotNull(existingInstruction.getIsExemptable());
        assertNotNull(existingInstruction.getIsWarning());
        assertNotNull(existingInstruction.getMessage());
        assertNotNull(existingInstruction.getPosition());
        assertNotNull(existingInstruction.getProcessKey());
        assertNotNull(existingInstruction.getStateKey());
        assertNotNull(existingInstruction.getTypeKey());
        

        // Create
        InstructionInfo instruction = new InstructionInfo();
        instruction.setAppliedAtpTypeKeys(new ArrayList<String>(){{add("kuali.atp.type.Fall");}});
        instruction.setAppliedPopulationKeys(new ArrayList<String>(){{add("Population-1");}});
//        instruction.setAttributes();
        instruction.setCheckKey("kuali.check.has.not.paid.bill.from.prior.term");
        instruction.setContinueOnFail(Boolean.TRUE);
//        instruction.setEffectiveDate();
//        instruction.setExpirationDate();
        instruction.setIsExemptable(Boolean.TRUE);
        instruction.setIsWarning(Boolean.TRUE);
        instruction.setMessage(new RichTextInfo(){{setPlain("Message-1");setFormatted("<p>Message-1<p>");}});
//        instruction.setMeta();
        instruction.setPosition(5);
        instruction.setProcessKey("kuali.process.registration.eligibility.for.term");
        instruction.setStateKey(ProcessServiceConstants.INSTRUCTION_ENABLED_STATE_KEY);
        instruction.setTypeKey(ProcessServiceConstants.INSTRUCTION_TYPE_KEY);
        instruction = processService.createInstruction(instruction.getProcessKey(), instruction.getCheckKey(), instruction, context);
        String instructionId = instruction.getId();
        instruction = processService.getInstruction(instructionId, context);
        assertNotNull(instruction.getAppliedAtpTypeKeys());
        assertEquals(1, instruction.getAppliedAtpTypeKeys().size());
        assertTrue(instruction.getAppliedAtpTypeKeys().contains("kuali.atp.type.Fall"));
        assertNotNull(instruction.getAppliedPopulationKeys());
//        assertEquals(1, instruction.getAppliedPopulationKeys().size());
//        assertTrue(instruction.getAppliedPopulationKeys().contains("Population-1"));
        assertEquals("kuali.check.has.not.paid.bill.from.prior.term", instruction.getCheckKey());
        assertTrue(instruction.getContinueOnFail());
        assertNotNull(instruction.getId());
        assertTrue(instruction.getIsExemptable());
        assertTrue(instruction.getIsWarning());
        assertEquals("Message-1", instruction.getMessage().getPlain());
        assertEquals(new Integer(5), instruction.getPosition());
        assertEquals("kuali.process.registration.eligibility.for.term", instruction.getProcessKey());
        assertEquals(ProcessServiceConstants.INSTRUCTION_ENABLED_STATE_KEY, instruction.getStateKey());
        assertEquals(ProcessServiceConstants.INSTRUCTION_TYPE_KEY, instruction.getTypeKey());

        // Update
        instruction.setAppliedAtpTypeKeys(new ArrayList<String>(){{add("kuali.atp.type.Spring");}});
        instruction.setAppliedPopulationKeys(new ArrayList<String>(){{add("Population-2");}});
//        instruction.setAttributes();
        instruction.setCheckKey("kuali.check.has.overdue.library.book");
        instruction.setContinueOnFail(Boolean.FALSE);
//        instruction.setEffectiveDate();
//        instruction.setExpirationDate();
        instruction.setIsExemptable(Boolean.FALSE);
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
        assertNotNull(instruction.getAppliedPopulationKeys());
//        assertEquals(1, instruction.getAppliedPopulationKeys().size());
//        assertTrue(instruction.getAppliedPopulationKeys().contains("Population-1"));
        assertEquals("kuali.check.has.overdue.library.book", instruction.getCheckKey());
        assertFalse(instruction.getContinueOnFail());
        assertNotNull(instruction.getId());
        assertFalse(instruction.getIsExemptable());
        assertFalse(instruction.getIsWarning());
        assertEquals("Message-2", instruction.getMessage().getPlain());
        assertEquals(new Integer(6), instruction.getPosition());
        assertEquals("kuali.process.registration.register.for.courses", instruction.getProcessKey());
        assertEquals(ProcessServiceConstants.INSTRUCTION_DISABLED_STATE_KEY, instruction.getStateKey());
        assertEquals(ProcessServiceConstants.INSTRUCTION_TYPE_KEY, instruction.getTypeKey());

        // Delete
        processService.deleteInstruction(instruction.getId(), context);
        try {
            processService.getInstruction(instruction.getId(), context);
            fail("Insruction not deleted properly.");
        } catch (DoesNotExistException e) {
            // expected, do nothing
        }
    }

    @Test
    public void testGetInstructionsByProcess() throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        List<InstructionInfo> instructions = processService.getInstructionsByProcess("kuali.process.registration.basic.eligibility", context);
        assertNotNull(instructions);
        assertFalse(instructions.isEmpty());
    }

}