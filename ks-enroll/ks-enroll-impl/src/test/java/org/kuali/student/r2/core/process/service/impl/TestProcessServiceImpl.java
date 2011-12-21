package org.kuali.student.r2.core.process.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
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
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.service.ProcessService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:process-test-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class TestProcessServiceImpl {

    @Resource(name="processServiceAuthDecorator")
    public ProcessService processService;

    public ContextInfo context = ContextInfo.newInstance();

    
    @Before
    public void setUp() {
        context = ContextInfo.getInstance(context);
        context.setPrincipalId("123");
    }

    @Test
    public void testServiceValidationSetup() {
        assertNotNull(processService);
    }

    @Test
    public void testCrudProcess() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, DataValidationErrorException, AlreadyExistsException, ReadOnlyException, VersionMismatchException {

        // Read
        ProcessInfo existingProcess = processService.getProcess("StudentEligibleForRegistrationThisTermProcess", context);
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
        assertEquals(ProcessServiceConstants.PROCESS_DISABLED_STATE_KEY, process.getStateKey());

        // Delete
        processService.deleteProcess(processId, context);
        try {
            process = processService.getProcess(processId, context);
            fail("Process not deleted properly.");
        } catch (DoesNotExistException e) {
            // expected, do nothing
        }

    }

    @Test
    public void testCrudCheck() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, DataValidationErrorException, AlreadyExistsException, ReadOnlyException, VersionMismatchException {

        // Read
        CheckInfo existingCheck = processService.getCheck("StudentPaidTuitonCheck", context);
        assertNotNull(existingCheck);
        assertNotNull(existingCheck.getAgendaId());
        assertNotNull(existingCheck.getIssueKey());
        assertNotNull(existingCheck.getMilestoneTypeKey());
        assertNull(existingCheck.getProcessKey());
        assertNotNull(existingCheck.getTypeKey());
        assertNotNull(existingCheck.getStateKey());

        String checkKey = "newCheck";

        // Create
        CheckInfo check = new CheckInfo();
        check.setAgendaId("AgendaId-1");
        check.setIssueKey("Hold-Issue-2");
        check.setMilestoneTypeKey("milestoneTypeKey-1");
        check.setProcessKey("StudentEligibleForRegistrationThisTermProcess");
        check.setTypeKey(ProcessServiceConstants.HOLD_CHECK_TYPE_KEY);
        check.setStateKey(ProcessServiceConstants.PROCESS_CHECK_STATE_ENABLED);
        processService.createCheck(checkKey, check, context);
        check = processService.getCheck(checkKey, context);
        assertNotNull(check);
        assertEquals("AgendaId-1", check.getAgendaId());
        assertEquals("Hold-Issue-2", check.getIssueKey());
        assertEquals("milestoneTypeKey-1", check.getMilestoneTypeKey());
        assertEquals("StudentEligibleForRegistrationThisTermProcess", check.getProcessKey());
        assertEquals(ProcessServiceConstants.HOLD_CHECK_TYPE_KEY, check.getTypeKey());
        assertEquals(ProcessServiceConstants.PROCESS_CHECK_STATE_ENABLED, check.getStateKey());

        // Update
        check.setIssueKey("Hold-Issue-1");
        check.setMilestoneTypeKey("milestoneTypeKey-2");
        check.setProcessKey(null);
        check.setAgendaId("AgendaId-2");
        check.setTypeKey(ProcessServiceConstants.START_DATE_CHECK_TYPE_KEY);
        check.setStateKey(ProcessServiceConstants.PROCESS_CHECK_STATE_INACTIVE);
        processService.updateCheck(check.getKey(), check, context);
        check = processService.getCheck(checkKey, context);
        assertNotNull(check);
        assertEquals("AgendaId-2", check.getAgendaId());
        assertEquals("Hold-Issue-1", check.getIssueKey());
        assertEquals("milestoneTypeKey-2", check.getMilestoneTypeKey());
        assertNull(check.getProcessKey());
        assertEquals(ProcessServiceConstants.START_DATE_CHECK_TYPE_KEY, check.getTypeKey());
        assertEquals(ProcessServiceConstants.PROCESS_CHECK_STATE_INACTIVE, check.getStateKey());

        // Delete
        processService.deleteCheck(checkKey, context);
        try {
            check = processService.getCheck(checkKey, context);
            fail("Check not deleted properly.");
        } catch (DoesNotExistException e) {
            // expected, do nothing
        }
    }

}