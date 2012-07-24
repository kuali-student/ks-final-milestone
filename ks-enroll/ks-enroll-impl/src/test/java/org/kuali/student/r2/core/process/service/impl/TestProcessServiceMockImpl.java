/**
 * Copyright 2012 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 *
 * Created by Mezba Mahtab on 6/20/12
 */
package org.kuali.student.r2.core.process.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.test.util.AttributeTester;
import org.kuali.student.enrollment.test.util.IdEntityTester;
import org.kuali.student.enrollment.test.util.KeyEntityTester;
import org.kuali.student.enrollment.test.util.ListOfStringTester;
import org.kuali.student.enrollment.test.util.MetaTester;
import org.kuali.student.enrollment.test.util.RelationshipTester;
import org.kuali.student.enrollment.test.util.RichTextTester;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessCategoryInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.service.ProcessService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This class tests ProcessServiceMockImpl
 *
 * @author Mezba Mahtab
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:process-mock-service-test-context.xml"})
//@Transactional
//@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class TestProcessServiceMockImpl {

    ///////////////////
    // CONSTANTS
    ///////////////////
    private static final Logger logger = Logger.getLogger(TestProcessServiceModel.class);
    ///////////////////
    // DATA FIELDS
    ///////////////////
    public static String principalId = "123";
    public static String principalId2 = "321";
    public ContextInfo contextInfo = null;
    @Resource(name = "processService")
    private ProcessService processService;
    @Resource(name = "debugMode")
    private boolean debugMode;
    /////////////////////////
    // GETTERS AND SETTERS
    /////////////////////////

    public ProcessService getProcessService() {
        return processService;
    }

    public void setProcessService(ProcessService processService) {
        this.processService = processService;
    }

    /////////////////////////
    // FUNCTIONALS
    /////////////////////////
    @Before
    public void setUp() {
        // set up context
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);

//         test setup
        assertNotNull(processService);
    }

    @Test
    public void testCrud()
            throws AlreadyExistsException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            DoesNotExistException,
            VersionMismatchException,
            DependentObjectsExistException {
        if (debugMode) {
            logger.debug("testing CRUD");
        }

        ProcessInfo process = this.testCrudProcess();
        CheckInfo check = testCrudCheck();
        InstructionInfo instruction = testCrudInstruction(process, check);
        ProcessCategoryInfo category = this.testCrudProcessCategory(process);
        testCrudProcess2ProcessCategory(process, category);

        StatusInfo status;

        try {
            processService.deleteCheck(check.getId(), contextInfo);
            fail("should have gotten a dependent objects exist exception");
        } catch (DependentObjectsExistException ex) {
            // expected
        }
        try {
            processService.deleteProcess(process.getKey(), contextInfo);
            fail("should have gotten a dependent objects exist exception");
        } catch (DependentObjectsExistException ex) {
            // expected
        }

        // delete category
        status = processService.deleteProcessCategory(category.getId(), contextInfo);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            processService.getProcessCategory(category.getId(), contextInfo);
            fail("should not be able to get deleted category");
        } catch (DoesNotExistException e) {
            // expected
        }

        // delete category
        status = processService.deleteInstruction(instruction.getId(), contextInfo);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            processService.getInstruction(instruction.getId(), contextInfo);
            fail("should not be able to get deleted instruction");
        } catch (DoesNotExistException e) {
            // expected
        }


        // delete process
        status = processService.deleteProcess(process.getKey(), contextInfo);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            processService.getProcess(process.getKey(), contextInfo);
            fail("should not be able to get deleted process");
        } catch (DoesNotExistException e) {
            // expected
        }


        // delete check
        status = processService.deleteCheck(check.getId(), contextInfo);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            processService.getCheck(check.getId(), contextInfo);
            fail("should not be able to get deleted check");
        } catch (DoesNotExistException e) {
            // expected
        }
        
        // load data for additional testing
        ProcessServiceDataLoader loader = new ProcessServiceDataLoader(processService, debugMode, logger);
        if (debugMode) logger.debug("Calling loader.loadData()");
        loader.loadData();
        testOtherOperations();
    }

    private void testCrudProcess2ProcessCategory(ProcessInfo process,
            ProcessCategoryInfo category)
            throws AlreadyExistsException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        // add process to category
        StatusInfo status = processService.addProcessToProcessCategory(process.getKey(), category.getId(), contextInfo);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());

        try {
            status = processService.addProcessToProcessCategory(process.getKey(), category.getId(), contextInfo);
            fail("should have thrown AlreadyExistsException");
        } catch (AlreadyExistsException ex) {
            // expected
        }

        List<ProcessInfo> processes = processService.getProcessesForProcessCategory(category.getId(), contextInfo);
        assertEquals(1, processes.size());
        assertEquals(process.getKey(), processes.get(0).getKey());

        List<ProcessCategoryInfo> processCategories = processService.getProcessCategoriesForProcess(process.getKey(), contextInfo);
        assertEquals(1, processCategories.size());
        assertEquals(category.getId(), processCategories.get(0).getId());


        try {
            status = processService.deleteProcess(process.getKey(), contextInfo);
            fail("should have gotten dependent object exists exception");
        } catch (DependentObjectsExistException ex) {
            // expected
        }

        try {
            status = processService.deleteProcessCategory(category.getId(), contextInfo);
            fail("should have gotten dependent object exists exception");
        } catch (DependentObjectsExistException ex) {
            // expected
        }

        // remove process from category
        status = processService.removeProcessFromProcessCategory(process.getKey(), category.getId(), contextInfo);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());

        processes = processService.getProcessesForProcessCategory(category.getId(), contextInfo);
        assertEquals(0, processes.size());

        processCategories = processService.getProcessCategoriesForProcess(process.getKey(), contextInfo);
        assertEquals(0, processCategories.size());
    }

    private ProcessInfo testCrudProcess()
            throws AlreadyExistsException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            DoesNotExistException,
            VersionMismatchException {
        if (debugMode) {
            logger.debug("testing CRUD");
        }
        // create
        ProcessInfo expected = new ProcessInfo();
        expected.setKey("testProcessKey1");
        expected.setTypeKey(ProcessServiceConstants.PROCESS_TYPE_KEY);
        expected.setStateKey(ProcessServiceConstants.PROCESS_DISABLED_STATE_KEY);
        expected.setName("process name");
        expected.setDescr(new RichTextHelper().fromPlain("description of process"));
        expected.setOwnerOrgId("org1");
        new AttributeTester().add2ForCreate(expected.getAttributes());
        ProcessInfo actual = processService.createProcess(expected.getKey(), expected.getTypeKey(), expected, contextInfo);
        new KeyEntityTester().check(expected, actual);
        assertEquals(expected.getOwnerOrgId(), actual.getOwnerOrgId());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // read / get after create
        expected = actual;
        actual = processService.getProcess(expected.getKey(), contextInfo);
        new KeyEntityTester().check(expected, actual);
        assertEquals(expected.getOwnerOrgId(), actual.getOwnerOrgId());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // update
        expected = actual;
        expected.setName(expected.getName() + " updated");
        expected.setDescr(new RichTextHelper().fromPlain(expected.getDescr().getPlain() + " updated"));
        expected.setStateKey(ProcessServiceConstants.PROCESS_ACTIVE_STATE_KEY);
        expected.setOwnerOrgId("org2");
        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        actual = processService.updateProcess(expected.getKey(), expected, contextInfo);
        new KeyEntityTester().check(expected, actual);
        assertEquals(expected.getOwnerOrgId(), actual.getOwnerOrgId());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // read / get after update
        expected = actual;
        actual = processService.getProcess(expected.getKey(), contextInfo);
        new KeyEntityTester().check(expected, actual);
        assertEquals(expected.getOwnerOrgId(), actual.getOwnerOrgId());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
        return actual;
    }

    private CheckInfo testCrudCheck()
            throws AlreadyExistsException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            DoesNotExistException,
            VersionMismatchException,
            DependentObjectsExistException {
        if (debugMode) {
            logger.debug("testing Check CRUD");
        }
        // create
        CheckInfo expected = new CheckInfo();
        expected.setTypeKey(ProcessServiceConstants.HOLD_CHECK_TYPE_KEY);
        expected.setStateKey(ProcessServiceConstants.PROCESS_CHECK_STATE_DISABLED);
        expected.setName("check name");
        expected.setDescr(new RichTextHelper().fromPlain("description of check"));
        expected.setHoldIssueId("holdIssue1");
        expected.setAgendaId("agendaId1");
        expected.setChildProcessKey("childprocesskey1");
        expected.setLeftComparisonAgendaId("leftcomparisonagendaid1");
        expected.setMilestoneTypeKey("milestonetypekey1");
        expected.setRightComparisonAgendaId("rightcomparisonagendaid1");
        expected.setRightComparisonValue("rightcomparisonvalue1");
        new AttributeTester().add2ForCreate(expected.getAttributes());
        CheckInfo actual = processService.createCheck(expected.getTypeKey(), expected, contextInfo);
        new IdEntityTester().check(expected, actual);
        assertEquals(expected.getHoldIssueId(), actual.getHoldIssueId());
        assertEquals(expected.getAgendaId(), actual.getAgendaId());
        assertEquals(expected.getChildProcessKey(), actual.getChildProcessKey());
        assertEquals(expected.getLeftComparisonAgendaId(), actual.getLeftComparisonAgendaId());
        assertEquals(expected.getMilestoneTypeKey(), actual.getMilestoneTypeKey());
        assertEquals(expected.getRightComparisonValue(), actual.getRightComparisonValue());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // read / get after create
        expected = actual;
        actual = processService.getCheck(expected.getId(), contextInfo);
        new IdEntityTester().check(expected, actual);
        assertEquals(expected.getHoldIssueId(), actual.getHoldIssueId());
        assertEquals(expected.getAgendaId(), actual.getAgendaId());
        assertEquals(expected.getChildProcessKey(), actual.getChildProcessKey());
        assertEquals(expected.getLeftComparisonAgendaId(), actual.getLeftComparisonAgendaId());
        assertEquals(expected.getMilestoneTypeKey(), actual.getMilestoneTypeKey());
        assertEquals(expected.getRightComparisonValue(), actual.getRightComparisonValue());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // update
        expected = actual;
        expected.setName(expected.getName() + " updated");
        expected.setDescr(new RichTextHelper().fromPlain(expected.getDescr().getPlain() + " updated"));
        expected.setStateKey(ProcessServiceConstants.PROCESS_ACTIVE_STATE_KEY);
        expected.setHoldIssueId("holdIssue2");
        expected.setAgendaId("agendaId2");
        expected.setChildProcessKey("childprocesskey2");
        expected.setLeftComparisonAgendaId("leftcomparisonagendaid2");
        expected.setMilestoneTypeKey("milestonetypekey2");
        expected.setRightComparisonAgendaId("rightcomparisonagendaid2");
        expected.setRightComparisonValue("rightcomparisonvalue2");
        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        actual = processService.updateCheck(expected.getId(), expected, contextInfo);
        assertEquals(expected.getHoldIssueId(), actual.getHoldIssueId());
        assertEquals(expected.getAgendaId(), actual.getAgendaId());
        assertEquals(expected.getChildProcessKey(), actual.getChildProcessKey());
        assertEquals(expected.getLeftComparisonAgendaId(), actual.getLeftComparisonAgendaId());
        assertEquals(expected.getMilestoneTypeKey(), actual.getMilestoneTypeKey());
        assertEquals(expected.getRightComparisonValue(), actual.getRightComparisonValue());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // read / get after update
        expected = actual;
        actual = processService.getCheck(expected.getId(), contextInfo);
        new IdEntityTester().check(expected, actual);
        assertEquals(expected.getHoldIssueId(), actual.getHoldIssueId());
        assertEquals(expected.getAgendaId(), actual.getAgendaId());
        assertEquals(expected.getChildProcessKey(), actual.getChildProcessKey());
        assertEquals(expected.getLeftComparisonAgendaId(), actual.getLeftComparisonAgendaId());
        assertEquals(expected.getMilestoneTypeKey(), actual.getMilestoneTypeKey());
        assertEquals(expected.getRightComparisonValue(), actual.getRightComparisonValue());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        return actual;
    }

    private InstructionInfo testCrudInstruction(ProcessInfo process1,
            CheckInfo check1)
            throws AlreadyExistsException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            DoesNotExistException,
            VersionMismatchException,
            DependentObjectsExistException {
        if (debugMode) {
            logger.debug("testing Instruction CRUD");
        }
        // create
        InstructionInfo expected = new InstructionInfo();
        expected.setTypeKey(ProcessServiceConstants.INSTRUCTION_TYPE_KEY);
        expected.setStateKey(ProcessServiceConstants.INSTRUCTION_DISABLED_STATE_KEY);
        expected.setProcessKey(process1.getKey());
        expected.setCheckId(check1.getId());
        expected.setEffectiveDate(new Date());
        expected.setExpirationDate(new Date(new Date().getTime() + 20000));
        expected.setAppliedPopulationId(ProcessServiceDataLoader.POPULATION_ID_EVERYONE);
        expected.getAppliedAtpTypeKeys().add(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        expected.getAppliedAtpTypeKeys().add(AtpServiceConstants.ATP_SPRING_TYPE_KEY);
        expected.setPosition(1);
        expected.setMessage(new RichTextHelper().fromPlain("message of instruction"));
        expected.setIsWarning(Boolean.TRUE);
        expected.setIsExemptible(Boolean.TRUE);
        expected.setContinueOnFail(Boolean.TRUE);
        new AttributeTester().add2ForCreate(expected.getAttributes());
        InstructionInfo actual = processService.createInstruction(expected.getProcessKey(),
                expected.getCheckId(),
                expected.getTypeKey(),
                expected,
                contextInfo);
        new RelationshipTester().check(expected, actual);
        assertEquals(expected.getProcessKey(), actual.getProcessKey());
        assertEquals(expected.getCheckId(), actual.getCheckId());
        assertEquals(expected.getAppliedPopulationId(), actual.getAppliedPopulationId());
        assertEquals(expected.getPosition(), actual.getPosition());
        new RichTextTester().check(expected.getMessage(), actual.getMessage());
        new ListOfStringTester().check(expected.getAppliedAtpTypeKeys(), actual.getAppliedAtpTypeKeys());
        assertEquals(expected.getIsWarning(), actual.getIsWarning());
        assertEquals(expected.getIsExemptible(), actual.getIsExemptible());
        assertEquals(expected.getContinueOnFail(), actual.getContinueOnFail());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // read / get after create
        expected = actual;
        actual = processService.getInstruction(expected.getId(), contextInfo);
        new RelationshipTester().check(expected, actual);
        assertEquals(expected.getProcessKey(), actual.getProcessKey());
        assertEquals(expected.getCheckId(), actual.getCheckId());
        assertEquals(expected.getAppliedPopulationId(), actual.getAppliedPopulationId());
        assertEquals(expected.getPosition(), actual.getPosition());
        new RichTextTester().check(expected.getMessage(), actual.getMessage());
        new ListOfStringTester().check(expected.getAppliedAtpTypeKeys(), actual.getAppliedAtpTypeKeys());
        assertEquals(expected.getIsWarning(), actual.getIsWarning());
        assertEquals(expected.getIsExemptible(), actual.getIsExemptible());
        assertEquals(expected.getContinueOnFail(), actual.getContinueOnFail());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // update
        expected = actual;
        expected.setStateKey(ProcessServiceConstants.INSTRUCTION_ACTIVE_STATE_KEY);
        expected.setEffectiveDate(new Date(expected.getEffectiveDate().getTime() - 1000));
        expected.setExpirationDate(new Date(expected.getExpirationDate().getTime() + 10000));
        expected.setAppliedPopulationId(ProcessServiceDataLoader.POPULATION_ID_FINAL_TERM_SENIORS);
        expected.getAppliedAtpTypeKeys().remove(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        expected.getAppliedAtpTypeKeys().add(AtpServiceConstants.ATP_WINTER_TYPE_KEY);
        expected.setPosition(2);
        expected.setMessage(new RichTextHelper().fromPlain("message for instruction updated"));
        expected.setIsWarning(Boolean.FALSE);
        expected.setIsExemptible(Boolean.FALSE);
        expected.setContinueOnFail(Boolean.FALSE);
        actual = processService.updateInstruction(expected.getId(), expected, contextInfo);
        new RelationshipTester().check(expected, actual);
        assertEquals(expected.getProcessKey(), actual.getProcessKey());
        assertEquals(expected.getCheckId(), actual.getCheckId());
        assertEquals(expected.getAppliedPopulationId(), actual.getAppliedPopulationId());
        assertEquals(expected.getPosition(), actual.getPosition());
        new RichTextTester().check(expected.getMessage(), actual.getMessage());
        new ListOfStringTester().check(expected.getAppliedAtpTypeKeys(), actual.getAppliedAtpTypeKeys());
        assertEquals(expected.getIsWarning(), actual.getIsWarning());
        assertEquals(expected.getIsExemptible(), actual.getIsExemptible());
        assertEquals(expected.getContinueOnFail(), actual.getContinueOnFail());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // read / get after update
        expected = actual;
        actual = processService.getInstruction(expected.getId(), contextInfo);
        new RelationshipTester().check(expected, actual);
        assertEquals(expected.getProcessKey(), actual.getProcessKey());
        assertEquals(expected.getCheckId(), actual.getCheckId());
        assertEquals(expected.getAppliedPopulationId(), actual.getAppliedPopulationId());
        assertEquals(expected.getPosition(), actual.getPosition());
        new RichTextTester().check(expected.getMessage(), actual.getMessage());
        new ListOfStringTester().check(expected.getAppliedAtpTypeKeys(), actual.getAppliedAtpTypeKeys());
        assertEquals(expected.getIsWarning(), actual.getIsWarning());
        assertEquals(expected.getIsExemptible(), actual.getIsExemptible());
        assertEquals(expected.getContinueOnFail(), actual.getContinueOnFail());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        return actual;
    }

    private ProcessCategoryInfo testCrudProcessCategory(ProcessInfo process1)
            throws AlreadyExistsException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            DoesNotExistException,
            VersionMismatchException,
            DependentObjectsExistException {
        if (debugMode) {
            logger.debug("testing ProcessCategory CRUD");
        }

        // create
        ProcessCategoryInfo expected = new ProcessCategoryInfo();
        expected.setTypeKey(ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY);
        expected.setStateKey(ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_INACTIVE);
        expected.setName("process Category name");
        expected.setDescr(new RichTextHelper().fromPlain("description of process category"));
        new AttributeTester().add2ForCreate(expected.getAttributes());
        ProcessCategoryInfo actual = processService.createProcessCategory(expected.getTypeKey(), expected, contextInfo);
        new IdEntityTester().check(expected, actual);
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());


        // read / get after create
        expected = actual;
        actual = processService.getProcessCategory(expected.getId(), contextInfo);
        new IdEntityTester().check(expected, actual);
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // update
        expected = actual;
        expected.setName(expected.getName() + " updated");
        expected.setDescr(new RichTextHelper().fromPlain(expected.getDescr().getPlain() + " updated"));
        expected.setStateKey(ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE);
        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        actual = processService.updateProcessCategory(expected.getId(), expected, contextInfo);
        new IdEntityTester().check(expected, actual);
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // read / get after update
        expected = actual;
        actual = processService.getProcessCategory(expected.getId(), contextInfo);
        new IdEntityTester().check(expected, actual);
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        return actual;
    }

    private void testOtherOperations()
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            AlreadyExistsException,
            DataValidationErrorException {
        testProcessCategoryOperations();
        testProcessOperations();
        testInstructionOperations();
        testCheckOperations();
    }

    private void testProcessCategoryOperations()
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            AlreadyExistsException {
        List<String> processCategoryIds = new ArrayList<String>();
        processCategoryIds.add(ProcessServiceDataLoader.PROCESS_CATEGORY_ID_ADMISSIONS);
        processCategoryIds.add(ProcessServiceDataLoader.PROCESS_CATEGORY_ID_COURSE_REGISTRATION);
        List<ProcessCategoryInfo> processCategoryInfos = processService.getProcessCategoriesByIds(processCategoryIds, contextInfo);
        assertEquals(2, processCategoryInfos.size());
        for (ProcessCategoryInfo info : processCategoryInfos) {
            assertTrue(processCategoryIds.contains(info.getId()));
        }

        List<String> processCategoryIdsByType = processService.getProcessCategoryIdsByType(
                ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, contextInfo);
        assertEquals(8, processCategoryIdsByType.size());
        for (String id : processCategoryIdsByType) {
            assertEquals(processService.getProcessCategory(id, contextInfo).getTypeKey(),
                    ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY);
        }
        List<String> processCategoryIdsByType2 = processService.getProcessCategoryIdsByType(ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY +
                "2", contextInfo);
        assertEquals(0, processCategoryIdsByType2.size());

        List<ProcessCategoryInfo> catInfosForProcess = processService.getProcessCategoriesForProcess(
                ProcessServiceConstants.PROCESS_KEY_VIEW_GRADES_FOR_TERM, contextInfo);
        assertEquals(1, catInfosForProcess.size());
        assertEquals(catInfosForProcess.get(0).getId(), ProcessServiceDataLoader.PROCESS_CATEGORY_ID_ACADEMIC_RECORD);
        List<ProcessCategoryInfo> catInfosForProcess2 = processService.getProcessCategoriesForProcess(
                ProcessServiceConstants.PROCESS_KEY_REGISTER_FOR_COURSES, contextInfo);
        assertEquals(1, catInfosForProcess2.size());
        assertEquals(catInfosForProcess2.get(0).getId(), ProcessServiceDataLoader.PROCESS_CATEGORY_ID_COURSE_REGISTRATION);

        try {
            processService.removeProcessFromProcessCategory(ProcessServiceConstants.PROCESS_KEY_VIEW_GRADES_FOR_TERM,
                    ProcessServiceDataLoader.PROCESS_CATEGORY_ID_COURSE_REGISTRATION, contextInfo);
            fail("should have thrown a does not exist exception");
        } catch (DoesNotExistException e) {
            // should be here
        }

        processService.removeProcessFromProcessCategory(ProcessServiceConstants.PROCESS_KEY_VIEW_GRADES_FOR_TERM,
                ProcessServiceDataLoader.PROCESS_CATEGORY_ID_ACADEMIC_RECORD, contextInfo);
        catInfosForProcess = processService.getProcessCategoriesForProcess(
                ProcessServiceConstants.PROCESS_KEY_VIEW_GRADES_FOR_TERM, contextInfo);
        assertEquals(0, catInfosForProcess.size());
        processService.addProcessToProcessCategory(ProcessServiceConstants.PROCESS_KEY_VIEW_GRADES_FOR_TERM,
                ProcessServiceDataLoader.PROCESS_CATEGORY_ID_ACADEMIC_RECORD, contextInfo);
        catInfosForProcess = processService.getProcessCategoriesForProcess(
                ProcessServiceConstants.PROCESS_KEY_VIEW_GRADES_FOR_TERM, contextInfo);
        assertEquals(1, catInfosForProcess.size());
    }

    private void testProcessOperations()
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            AlreadyExistsException {
        List<String> processKeys = new ArrayList<String>();
        processKeys.add(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY);
        processKeys.add(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSES);
        processKeys.add(ProcessServiceConstants.PROCESS_KEY_VIEW_COURSE_GRADE);
        List<ProcessInfo> processInfosByKeys = processService.getProcessesByKeys(processKeys, contextInfo);
        assertEquals(3, processInfosByKeys.size());
        for (ProcessInfo info : processInfosByKeys) {
            assertTrue(processKeys.contains(info.getKey()));
        }

        List<String> processKeysByType = processService.getProcessKeysByType(ProcessServiceConstants.PROCESS_TYPE_KEY, contextInfo);
        assertEquals(10, processKeysByType.size());
        assertTrue(processKeysByType.contains(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY));
        assertTrue(processKeysByType.contains(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM));
        assertTrue(processKeysByType.contains(ProcessServiceConstants.PROCESS_KEY_HOLDS_CLEARED));
        assertTrue(processKeysByType.contains(ProcessServiceConstants.PROCESS_KEY_ACKNOWLEDGEMENTS_CONFIRMED));
        assertTrue(processKeysByType.contains(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSE));
        assertTrue(processKeysByType.contains(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSES));
        assertTrue(processKeysByType.contains(ProcessServiceConstants.PROCESS_KEY_REGISTER_FOR_COURSES));
        assertTrue(processKeysByType.contains(ProcessServiceConstants.PROCESS_KEY_VIEW_GRADES));
        assertTrue(processKeysByType.contains(ProcessServiceConstants.PROCESS_KEY_VIEW_GRADES_FOR_TERM));
        assertTrue(processKeysByType.contains(ProcessServiceConstants.PROCESS_KEY_VIEW_COURSE_GRADE));

        List<ProcessInfo> processInfosByCateg = processService.getProcessesForProcessCategory(
                ProcessServiceDataLoader.PROCESS_CATEGORY_ID_ACADEMIC_RECORD, contextInfo);
        assertEquals(3, processInfosByCateg.size());
        assertTrue(processInfosByCateg.contains(processService.getProcess(ProcessServiceConstants.PROCESS_KEY_VIEW_COURSE_GRADE,
                contextInfo)));
        assertTrue(processInfosByCateg.contains(processService.getProcess(ProcessServiceConstants.PROCESS_KEY_VIEW_GRADES_FOR_TERM,
                contextInfo)));
        assertTrue(processInfosByCateg.contains(processService.getProcess(ProcessServiceConstants.PROCESS_KEY_VIEW_GRADES,
                contextInfo)));
    }

    private void testCheckOperations()
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            AlreadyExistsException {
        List<String> checkIds = new ArrayList<String>();
        checkIds.add(ProcessServiceDataLoader.CHECK_ID_IS_STUDENTS_REGISTRATION_WINDOW);
        checkIds.add(ProcessServiceDataLoader.CHECK_ID_TOO_MANY_COURSES_DURING_INITIAL_REGISTRATION_PERIOD);
        checkIds.add(ProcessServiceDataLoader.CHECK_ID_NORTH_STUDENTS_MAX_SOUTH_CREDITS);
        checkIds.add(ProcessServiceDataLoader.CHECK_ID_HAS_THE_NECESSARY_PREREQ);
        checkIds.add(ProcessServiceDataLoader.CHECK_ID_DOES_NOT_HAVE_A_TIME_CONFLICT);
        checkIds.add(ProcessServiceDataLoader.CHECK_ID_IS_NOT_SUMMER_TERM);
        List<CheckInfo> checksByIds = processService.getChecksByIds(checkIds, contextInfo);
        assertEquals(6, checksByIds.size());
        for (CheckInfo info : checksByIds) {
            assertTrue(checkIds.contains(info.getId()));
        }

        List<String> checkIdsByType = processService.getCheckIdsByType(ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, contextInfo);
        assertEquals(37, checkIdsByType.size());
    }

    private void testInstructionOperations()
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            AlreadyExistsException,
            DataValidationErrorException {
        List<String> instructionIds = new ArrayList<String>();
        instructionIds.add("TESTID.2");
        instructionIds.add("TESTID.3");
        instructionIds.add("TESTID.5");
        instructionIds.add("TESTID.8");
        List<InstructionInfo> instructionByIds = processService.getInstructionsByIds(instructionIds, contextInfo);
        assertEquals(4, instructionByIds.size());
        for (InstructionInfo info : instructionByIds) {
            assertTrue(instructionIds.contains(info.getId()));
        }

        List<String> instructionIdsByType = processService.getInstructionIdsByType(ProcessServiceConstants.INSTRUCTION_TYPE_KEY,
                contextInfo);
        assertEquals(11, instructionIdsByType.size());

        List<InstructionInfo> instructionByProcess = processService.getInstructionsByProcess(
                ProcessServiceConstants.PROCESS_KEY_ACKNOWLEDGEMENTS_CONFIRMED, contextInfo);
        assertEquals(4, instructionByProcess.size());
        assertTrue(instructionByProcess.contains(processService.getInstruction("TESTID.14", contextInfo)));
        assertTrue(instructionByProcess.contains(processService.getInstruction("TESTID.13", contextInfo)));
        assertTrue(instructionByProcess.contains(processService.getInstruction("TESTID.12", contextInfo)));
        assertTrue(instructionByProcess.contains(processService.getInstruction("TESTID.11", contextInfo)));

        List<InstructionInfo> instructionByCheck = processService.getInstructionsByCheck(
                ProcessServiceDataLoader.CHECK_ID_HAS_ACKNOWLEDGED_RIAA, contextInfo);
        assertEquals(1, instructionByCheck.size());
        assertTrue(instructionByCheck.contains(processService.getInstruction("TESTID.11", contextInfo)));

        List<InstructionInfo> instructionByProcessAndCheck = processService.getInstructionsByProcessAndCheck(
                ProcessServiceConstants.PROCESS_KEY_HOLDS_CLEARED, ProcessServiceDataLoader.CHECK_ID_HAS_UNPAID_LIBRARY_FINE,
                contextInfo);
        assertEquals(1, instructionByProcessAndCheck.size());
        assertTrue(instructionByProcessAndCheck.contains(processService.getInstruction("TESTID.8", contextInfo)));

        instructionIds = new ArrayList<String>();
        instructionIds.add("TESTID.13");
        instructionIds.add("TESTID.11");
        instructionIds.add("TESTID.14");

        StatusInfo status = processService.reorderInstructions(ProcessServiceConstants.PROCESS_KEY_ACKNOWLEDGEMENTS_CONFIRMED,
                instructionIds, contextInfo);
        assertEquals(new Integer(0), processService.getInstruction("TESTID.13", contextInfo).getPosition());
        assertEquals(new Integer(1), processService.getInstruction("TESTID.11", contextInfo).getPosition());
        assertEquals(new Integer(2), processService.getInstruction("TESTID.14", contextInfo).getPosition());
        assertEquals(new Integer(3), processService.getInstruction("TESTID.12", contextInfo).getPosition());
    }
}