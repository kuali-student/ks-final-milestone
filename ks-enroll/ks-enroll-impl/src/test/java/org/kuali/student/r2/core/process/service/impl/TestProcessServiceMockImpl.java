/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Mezba Mahtab on 6/20/12
 */
package org.kuali.student.r2.core.process.service.impl;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessCategoryInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.service.ProcessService;
import org.mortbay.log.Log;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import org.kuali.student.r2.common.dto.StatusInfo;

/**
 * This class tests ProcessServiceMockImpl
 *
 * @author Mezba Mahtab
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:process-test-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
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

    @Resource(name = "processServiceMockImpl")
    private ProcessService processService;

    @Resource
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
        try {
            ProcessServiceDataLoader loader = new ProcessServiceDataLoader (processService, debugMode, logger);
            if (debugMode) { Log.warn("Calling loader.loadData()"); }
            loader.loadData();
        } catch (Exception ex) {
            throw new RuntimeException (ex);
        }
        // test setup
        assertNotNull(processService);
    }

    @Test
    public void testProcessCrud () throws Exception {
        if (debugMode) { logger.warn("testing Process CRUD"); }
        // create
        String processRequestId = "request1";
        ProcessInfo info = new ProcessInfo();
        info.setKey(processRequestId);
        info.setTypeKey(ProcessServiceConstants.PROCESS_TYPE_KEY);
        info.setStateKey(ProcessServiceConstants.PROCESS_ENABLED_STATE_KEY);
        info.setName("basic eligibility");
        Date before = new Date();
        ProcessInfo result = processService.createProcess(processRequestId,ProcessServiceConstants.PROCESS_TYPE_KEY, info, contextInfo);
        Date after = new Date();
        if (result == info) {
            fail("returned object should not be the same as the one passed in");
        }
        assertEquals(processRequestId, result.getKey());
        assertEquals(ProcessServiceConstants.PROCESS_TYPE_KEY, result.getTypeKey());
        assertEquals(info.getStateKey(), result.getStateKey());
        assertEquals(principalId, result.getMeta().getCreateId());
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
        assertEquals(principalId, result.getMeta().getUpdateId());
        assertNotNull(result.getMeta().getVersionInd());

        // read / get
        info = new ProcessInfo(result);
        result = processService.getProcess(info.getKey(), contextInfo);
        assertEquals(result.getKey(), info.getKey());
        assertEquals(result.getTypeKey(), info.getTypeKey());
        assertEquals(result.getStateKey(), info.getStateKey());
        assertEquals(result.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(result.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(result.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(result.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());
        assertEquals(result.getMeta().getVersionInd(), info.getMeta().getVersionInd());
        assertEquals(result.getMeta().getCreateId(), info.getMeta().getCreateId());

        // update
        info = new ProcessInfo(result);
        info.setName("new name");
        contextInfo.setPrincipalId(principalId2);
        before = new Date();
        result = processService.updateProcess(info.getKey(), info, contextInfo);
        after = new Date();
        if (result == info) {
            fail("returned object should not be the same as the one passed in");
        }
        assertEquals (info.getKey(), result.getKey());
        assertEquals(info.getTypeKey(), result.getTypeKey());
        assertEquals(info.getStateKey(), result.getStateKey());
        assertEquals(info.getName(), result.getName());
        assertEquals(principalId, result.getMeta().getCreateId());
        if (result.getMeta().getCreateTime().after(before)) {
            fail("create time should be before the update call");
        }
        if (result.getMeta().getUpdateTime().before(before)) {
            fail("update time should not be before the call");
        }
        if (result.getMeta().getUpdateTime().after(after)) {
            fail("update time should not be after the call");
        }
        assertEquals(principalId2, result.getMeta().getUpdateId());
        if (info.getMeta().getVersionInd().compareTo(result.getMeta().getVersionInd())>= 0) {
            fail ("version ind should be lexically greater than the old version id");
        }

        // delete
        info = new ProcessInfo(result);
        result = processService.getProcess(info.getKey(), contextInfo);
        processService.deleteProcess(info.getKey(), contextInfo);
        try {
            result = processService.getProcess(info.getKey(), contextInfo);
        }catch (DoesNotExistException e) {}
        catch (Exception e) { fail("Threw exception " + e + " when expecting a DoesNotExistException");}
    }

    @Test
    public void testInstructionCrud () throws Exception {
        if (debugMode) { logger.warn("testing Instruction CRUD"); }
        // create
        String instructionRequestId = "request1";
        InstructionInfo info = new InstructionInfo();
        info.setId(instructionRequestId);
        info.setTypeKey(ProcessServiceConstants.INSTRUCTION_TYPE_KEY);
        info.setStateKey(ProcessServiceConstants.INSTRUCTION_ENABLED_STATE_KEY);
        info.setProcessKey(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY);
        info.setCheckId(ProcessServiceDataLoader.CHECK_ID_HAS_NOT_BEEN_EXPELLED);
        info.setAppliedPopulationId(ProcessServiceDataLoader.POPULATION_ID_EVERYONE);
        info.setPosition(3);
        info.setMessage(new RichTextInfo("You are not allowed to continue at this university", "You are not allowed to continue at this university"));
        Date before = new Date();
        InstructionInfo result = processService.createInstruction(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, ProcessServiceDataLoader.CHECK_ID_HAS_NOT_BEEN_EXPELLED, ProcessServiceConstants.INSTRUCTION_TYPE_KEY, info, contextInfo);
        Date after = new Date();
        if (result == info) {
            fail("returned object should not be the same as the one passed in");
        }
        assertEquals(instructionRequestId, result.getId());
        assertEquals(ProcessServiceConstants.INSTRUCTION_TYPE_KEY, result.getTypeKey());
        assertEquals(info.getStateKey(), result.getStateKey());
        assertEquals(principalId, result.getMeta().getCreateId());
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
        assertEquals(info.getPosition(), result.getPosition());
        assertEquals(principalId, result.getMeta().getUpdateId());
        assertNotNull(result.getMeta().getVersionInd());

        // read / get
        info = new InstructionInfo(result);
        result = processService.getInstruction(info.getId(), contextInfo);
        assertEquals(result.getId(), info.getId());
        assertEquals(result.getTypeKey(), info.getTypeKey());
        assertEquals(result.getStateKey(), info.getStateKey());
        assertEquals(result.getProcessKey(), info.getProcessKey());
        assertEquals(result.getCheckId(), info.getCheckId());
        assertEquals(result.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(result.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(result.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(result.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());
        assertEquals(result.getMeta().getVersionInd(), info.getMeta().getVersionInd());
        assertEquals(result.getMeta().getCreateId(), info.getMeta().getCreateId());

        // update
        info = new InstructionInfo(result);
        info.setPosition(5);
        contextInfo.setPrincipalId(principalId2);
        before = new Date();
        result = processService.updateInstruction(info.getId(), info, contextInfo);
        after = new Date();
        if (result == info) {
            fail("returned object should not be the same as the one passed in");
        }
        assertEquals (info.getId(), result.getId());
        assertEquals(info.getTypeKey(), result.getTypeKey());
        assertEquals(info.getStateKey(), result.getStateKey());
        assertEquals(info.getPosition(), result.getPosition());
        assertEquals(principalId, result.getMeta().getCreateId());
        if (result.getMeta().getCreateTime().after(before)) {
            fail("create time should be before the update call");
        }
        if (result.getMeta().getUpdateTime().before(before)) {
            fail("update time should not be before the call");
        }
        if (result.getMeta().getUpdateTime().after(after)) {
            fail("update time should not be after the call");
        }
        assertEquals(principalId2, result.getMeta().getUpdateId());
        if (info.getMeta().getVersionInd().compareTo(result.getMeta().getVersionInd())>= 0) {
            fail ("version ind should be lexically greater than the old version id");
        }

        // delete
        info = new InstructionInfo(result);
        result = processService.getInstruction(info.getId(), contextInfo);
        processService.deleteInstruction(info.getId(), contextInfo);
        try {
            result = processService.getInstruction(info.getId(), contextInfo);
        }catch (DoesNotExistException e) {}
        catch (Exception e) { fail("Threw exception " + e + " when expecting a DoesNotExistException");}
    }

    @Test
    public void testCheckCrud () throws Exception {
        if (debugMode) { logger.warn("testing Check CRUD"); }
        // create
        String checkRequestId = "request1";
        CheckInfo info = new CheckInfo();
        info.setId(checkRequestId);
        info.setTypeKey(ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY);
        info.setStateKey(ProcessServiceConstants.INDIRECT_RULE_CHECK_TYPE_KEY);
        info.setName ("A Necessary Check");
        Date before = new Date();
        CheckInfo result = processService.createCheck(ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, info, contextInfo);
        Date after = new Date();
        if (result == info) {
            fail("returned object should not be the same as the one passed in");
        }
        assertEquals(checkRequestId, result.getId());
        assertEquals(ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, result.getTypeKey());
        assertEquals(info.getStateKey(), result.getStateKey());
        assertEquals(principalId, result.getMeta().getCreateId());
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
        assertEquals(info.getName(), result.getName());
        assertEquals(principalId, result.getMeta().getUpdateId());
        assertNotNull(result.getMeta().getVersionInd());

        // read / get
        info = new CheckInfo(result);
        result = processService.getCheck(info.getId(), contextInfo);
        assertEquals(result.getId(), info.getId());
        assertEquals(result.getTypeKey(), info.getTypeKey());
        assertEquals(result.getStateKey(), info.getStateKey());
        assertEquals(result.getProcessKey(), info.getProcessKey());
        assertEquals(result.getName(), info.getName());
        assertEquals(result.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(result.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(result.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(result.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());
        assertEquals(result.getMeta().getVersionInd(), info.getMeta().getVersionInd());
        assertEquals(result.getMeta().getCreateId(), info.getMeta().getCreateId());

        // update
        info = new CheckInfo(result);
        info.setName("Are you gone with the wind?");
        contextInfo.setPrincipalId(principalId2);
        before = new Date();
        result = processService.updateCheck(info.getId(), info, contextInfo);
        after = new Date();
        if (result == info) {
            fail("returned object should not be the same as the one passed in");
        }
        assertEquals (info.getId(), result.getId());
        assertEquals(info.getTypeKey(), result.getTypeKey());
        assertEquals(info.getStateKey(), result.getStateKey());
        assertEquals(info.getName(), result.getName());
        assertEquals(principalId, result.getMeta().getCreateId());
        if (result.getMeta().getCreateTime().after(before)) {
            fail("create time should be before the update call");
        }
        if (result.getMeta().getUpdateTime().before(before)) {
            fail("update time should not be before the call");
        }
        if (result.getMeta().getUpdateTime().after(after)) {
            fail("update time should not be after the call");
        }
        assertEquals(principalId2, result.getMeta().getUpdateId());
        if (info.getMeta().getVersionInd().compareTo(result.getMeta().getVersionInd())>= 0) {
            fail ("version ind should be lexically greater than the old version id");
        }

        // delete
        info = new CheckInfo(result);
        result = processService.getCheck(info.getId(), contextInfo);
        processService.deleteCheck(info.getId(), contextInfo);
        try {
            result = processService.getCheck(info.getId(), contextInfo);
        }catch (DoesNotExistException e) {}
        catch (Exception e) { fail("Threw exception " + e + " when expecting a DoesNotExistException");}
    }

    @Test
    public void testProcessCategoryCrud () throws Exception {
        if (debugMode) { logger.warn("testing ProcessCategory CRUD"); }
        // create
        String processCategoryRequestId = "request1";
        ProcessCategoryInfo info = new ProcessCategoryInfo();
        info.setId(processCategoryRequestId);
        info.setTypeKey(ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY);
        info.setStateKey(ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE);
        info.setName ("Category of Tests");
        Date before = new Date();
        ProcessCategoryInfo result = processService.createProcessCategory(ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, info, contextInfo);
        Date after = new Date();
        if (result == info) {
            fail("returned object should not be the same as the one passed in");
        }
        assertEquals(processCategoryRequestId, result.getId());
        assertEquals(ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, result.getTypeKey());
        assertEquals(info.getStateKey(), result.getStateKey());
        assertEquals(principalId, result.getMeta().getCreateId());
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
        assertEquals(info.getName(), result.getName());
        assertEquals(principalId, result.getMeta().getUpdateId());
        assertNotNull(result.getMeta().getVersionInd());

        // read / get
        info = new ProcessCategoryInfo(result);
        result = processService.getProcessCategory(info.getId(), contextInfo);
        assertEquals(result.getId(), info.getId());
        assertEquals(result.getTypeKey(), info.getTypeKey());
        assertEquals(result.getStateKey(), info.getStateKey());
        assertEquals(result.getName(), info.getName());
        assertEquals(result.getMeta().getCreateId(), info.getMeta().getCreateId());
        assertEquals(result.getMeta().getUpdateId(), info.getMeta().getUpdateId());
        assertEquals(result.getMeta().getCreateTime(), info.getMeta().getCreateTime());
        assertEquals(result.getMeta().getUpdateTime(), info.getMeta().getUpdateTime());
        assertEquals(result.getMeta().getVersionInd(), info.getMeta().getVersionInd());
        assertEquals(result.getMeta().getCreateId(), info.getMeta().getCreateId());

        // update
        info = new ProcessCategoryInfo(result);
        info.setName("A newer category - kuali!");
        contextInfo.setPrincipalId(principalId2);
        before = new Date();
        result = processService.updateProcessCategory(info.getId(), info, contextInfo);
        after = new Date();
        if (result == info) {
            fail("returned object should not be the same as the one passed in");
        }
        assertEquals (info.getId(), result.getId());
        assertEquals(info.getTypeKey(), result.getTypeKey());
        assertEquals(info.getStateKey(), result.getStateKey());
        assertEquals(info.getName(), result.getName());
        assertEquals(principalId, result.getMeta().getCreateId());
        if (result.getMeta().getCreateTime().after(before)) {
            fail("create time should be before the update call");
        }
        if (result.getMeta().getUpdateTime().before(before)) {
            fail("update time should not be before the call");
        }
        if (result.getMeta().getUpdateTime().after(after)) {
            fail("update time should not be after the call");
        }
        assertEquals(principalId2, result.getMeta().getUpdateId());
        if (info.getMeta().getVersionInd().compareTo(result.getMeta().getVersionInd())>= 0) {
            fail ("version ind should be lexically greater than the old version id");
        }

        // delete
        info = new ProcessCategoryInfo(result);
        result = processService.getProcessCategory(info.getId(), contextInfo);
        processService.deleteProcessCategory(info.getId(), contextInfo);
        try {
            result = processService.getProcessCategory(info.getId(), contextInfo);
        }catch (DoesNotExistException e) {}
        catch (Exception e) { fail("Threw exception " + e + " when expecting a DoesNotExistException");}
    }

    @Test
    public void testProcessCategoryOperations () throws Exception {
        List<String> processCategoryIds = new ArrayList<String>();
        processCategoryIds.add(ProcessServiceDataLoader.PROCESS_CATEGORY_ID_ADMISSIONS);
        processCategoryIds.add(ProcessServiceDataLoader.PROCESS_CATEGORY_ID_COURSE_REGISTRATION);
        List<ProcessCategoryInfo> processCategoryInfos = processService.getProcessCategoriesByIds(processCategoryIds, contextInfo);
        assertEquals(2, processCategoryInfos.size());
        for (ProcessCategoryInfo info: processCategoryInfos) {
            assertTrue(processCategoryIds.contains(info.getId()));
        }

        List<String> processCategoryIdsByType = processService.getProcessCategoryIdsByType(ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, contextInfo);
        assertEquals(8, processCategoryIdsByType.size());
        for (String id: processCategoryIdsByType) {
            assertEquals(processService.getProcessCategory(id, contextInfo).getTypeKey(), ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY);
        }
        List<String> processCategoryIdsByType2 = processService.getProcessCategoryIdsByType(ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY + "2", contextInfo);
        assertEquals(0, processCategoryIdsByType2.size());

        List<ProcessCategoryInfo> catInfosForProcess = processService.getProcessCategoriesForProcess(ProcessServiceConstants.PROCESS_KEY_VIEW_GRADES_FOR_TERM, contextInfo);
        assertEquals(1, catInfosForProcess.size());
        assertEquals(catInfosForProcess.get(0).getId(), ProcessServiceDataLoader.PROCESS_CATEGORY_ID_ACADEMIC_RECORD);
        List<ProcessCategoryInfo> catInfosForProcess2 = processService.getProcessCategoriesForProcess(ProcessServiceConstants.PROCESS_KEY_REGISTER_FOR_COURSES, contextInfo);
        assertEquals(1, catInfosForProcess2.size());
        assertEquals(catInfosForProcess2.get(0).getId(), ProcessServiceDataLoader.PROCESS_CATEGORY_ID_COURSE_REGISTRATION);

        try {
            processService.removeProcessFromProcessCategory(ProcessServiceConstants.PROCESS_KEY_VIEW_GRADES_FOR_TERM, ProcessServiceDataLoader.PROCESS_CATEGORY_ID_COURSE_REGISTRATION, contextInfo);
        } catch (DoesNotExistException e) {
            // should be here
        } catch (Exception e) {
            throw e;
        }
        catInfosForProcess = processService.getProcessCategoriesForProcess(ProcessServiceConstants.PROCESS_KEY_VIEW_GRADES_FOR_TERM, contextInfo);
        processService.removeProcessFromProcessCategory(ProcessServiceConstants.PROCESS_KEY_VIEW_GRADES_FOR_TERM, ProcessServiceDataLoader.PROCESS_CATEGORY_ID_ACADEMIC_RECORD, contextInfo);
        assertEquals(0, catInfosForProcess.size());
        processService.addProcessToProcessCategory(ProcessServiceConstants.PROCESS_KEY_VIEW_GRADES_FOR_TERM, ProcessServiceDataLoader.PROCESS_CATEGORY_ID_ACADEMIC_RECORD, contextInfo);
        catInfosForProcess = processService.getProcessCategoriesForProcess(ProcessServiceConstants.PROCESS_KEY_VIEW_GRADES_FOR_TERM, contextInfo);
        assertEquals(1, catInfosForProcess.size());
    }

    @Test
    public void testProcessOperations () throws Exception {
        List<String> processKeys = new ArrayList<String>();
        processKeys.add(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY);
        processKeys.add(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSES);
        processKeys.add(ProcessServiceConstants.PROCESS_KEY_VIEW_COURSE_GRADE);
        List<ProcessInfo> processInfosByKeys = processService.getProcessesByKeys(processKeys, contextInfo);
        assertEquals(3, processInfosByKeys.size());
        for (ProcessInfo info: processInfosByKeys) {
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

        List<ProcessInfo> processInfosByCateg = processService.getProcessesForProcessCategory(ProcessServiceDataLoader.PROCESS_CATEGORY_ID_ACADEMIC_RECORD, contextInfo);
        assertEquals(3, processInfosByCateg.size());
        assertTrue(processInfosByCateg.contains(processService.getProcess(ProcessServiceConstants.PROCESS_KEY_VIEW_COURSE_GRADE, contextInfo)));
        assertTrue(processInfosByCateg.contains(processService.getProcess(ProcessServiceConstants.PROCESS_KEY_VIEW_GRADES_FOR_TERM, contextInfo)));
        assertTrue(processInfosByCateg.contains(processService.getProcess(ProcessServiceConstants.PROCESS_KEY_VIEW_GRADES, contextInfo)));
    }

    @Test
    public void testCheckOperations () throws Exception {
        List<String> checkIds = new ArrayList<String>();
        checkIds.add(ProcessServiceDataLoader.CHECK_ID_IS_STUDENTS_REGISTRATION_WINDOW);
        checkIds.add(ProcessServiceDataLoader.CHECK_ID_TOO_MANY_COURSES_DURING_INITIAL_REGISTRATION_PERIOD);
        checkIds.add(ProcessServiceDataLoader.CHECK_ID_NORTH_STUDENTS_MAX_SOUTH_CREDITS);
        checkIds.add(ProcessServiceDataLoader.CHECK_ID_HAS_THE_NECESSARY_PREREQ);
        checkIds.add(ProcessServiceDataLoader.CHECK_ID_DOES_NOT_HAVE_A_TIME_CONFLICT);
        checkIds.add(ProcessServiceDataLoader.CHECK_ID_IS_NOT_SUMMER_TERM);
        List<CheckInfo> checksByIds = processService.getChecksByIds(checkIds, contextInfo);
        assertEquals(6, checksByIds.size());
        for (CheckInfo info: checksByIds) {
            assertTrue(checkIds.contains(info.getId()));
        }

        List<String> checkIdsByType = processService.getCheckIdsByType(ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, contextInfo);
        assertEquals(37, checkIdsByType.size());
    }

    @Test
    public void testInstructionOperations () throws Exception {
        List<String> instructionIds = new ArrayList<String>();
        instructionIds.add("2");
        instructionIds.add("3");
        instructionIds.add("5");
        instructionIds.add("8");
        List<InstructionInfo> instructionByIds = processService.getInstructionsByIds(instructionIds, contextInfo);
        assertEquals(4, instructionByIds.size());
        for (InstructionInfo info: instructionByIds) {
            assertTrue(instructionIds.contains(info.getId()));
        }

        List<String> instructionIdsByType = processService.getInstructionIdsByType(ProcessServiceConstants.INSTRUCTION_TYPE_KEY, contextInfo);
        assertEquals(11, instructionIdsByType.size());

        List<InstructionInfo> instructionByProcess = processService.getInstructionsByProcess(ProcessServiceConstants.PROCESS_KEY_ACKNOWLEDGEMENTS_CONFIRMED, contextInfo);
        assertEquals(4, instructionByProcess.size());
        assertTrue(instructionByProcess.contains(processService.getInstruction("14", contextInfo)));
        assertTrue(instructionByProcess.contains(processService.getInstruction("13", contextInfo)));
        assertTrue(instructionByProcess.contains(processService.getInstruction("12", contextInfo)));
        assertTrue(instructionByProcess.contains(processService.getInstruction("11", contextInfo)));

        List<InstructionInfo> instructionByCheck = processService.getInstructionsByCheck(ProcessServiceDataLoader.CHECK_ID_HAS_ACKNOWLEDGED_RIAA, contextInfo);
        assertEquals(1, instructionByCheck.size());
        assertTrue(instructionByCheck.contains(processService.getInstruction("11", contextInfo)));

        List<InstructionInfo> instructionByProcessAndCheck = processService.getInstructionsByProcessAndCheck(ProcessServiceConstants.PROCESS_KEY_HOLDS_CLEARED, ProcessServiceDataLoader.CHECK_ID_HAS_UNPAID_LIBRARY_FINE, contextInfo);
        assertEquals(1, instructionByProcessAndCheck.size());
        assertTrue(instructionByProcessAndCheck.contains(processService.getInstruction("8", contextInfo)));

        instructionIds = new ArrayList<String> ();
        instructionIds.add("13");
        instructionIds.add("11");        
        instructionIds.add("14"); 
        
        StatusInfo status = processService.reorderInstructions (ProcessServiceConstants.PROCESS_KEY_ACKNOWLEDGEMENTS_CONFIRMED, instructionIds, contextInfo);
        assertEquals(new Integer (0), processService.getInstruction("13", contextInfo).getPosition());
        assertEquals(new Integer (1), processService.getInstruction("11", contextInfo).getPosition());
        assertEquals(new Integer (2), processService.getInstruction("14", contextInfo).getPosition());
        assertEquals(new Integer (3), processService.getInstruction("12", contextInfo).getPosition());
    }

}