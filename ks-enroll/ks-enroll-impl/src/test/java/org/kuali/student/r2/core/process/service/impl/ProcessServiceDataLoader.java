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
 * Created by Mezba Mahtab on 6/21/12
 */
package org.kuali.student.r2.core.process.service.impl;

import org.apache.log4j.Logger;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessCategoryInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.service.ProcessService;

import java.util.Date;
import java.util.List;

/**
 * This class loads Process Service data
 *
 * @author Mezba Mahtab
 */
public class ProcessServiceDataLoader {

    ///////////////////
    // DATA FIELDS
    ///////////////////

    private  String principalId = ProcessServiceDataLoader.class.getSimpleName();
    private ProcessService processService = null;
    private boolean debugMode = true;
    private static Logger logger = null;

    ////////////////////
    // CONSTRUCTORS
    ////////////////////

    public ProcessServiceDataLoader (ProcessService processService, boolean debugMode, Logger logger){
        this.processService = processService;
        this.debugMode = debugMode;
        this.logger = logger;
    }

    ////////////////////
    // FUNCTIONALS
    ////////////////////

    public void loadData () throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (debugMode) { logger.warn("loadData called"); }

        // create the context
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
        contextInfo.setCurrentDate(new Date());
        CommonServiceConstants.setIsIdAllowedOnCreate(contextInfo, true);

        // load process categories
        loadProcessCategory("kuali.process.type.admissions", ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, "Admissions",
                "Blocks a student from changing her degree program", "Blocks a student from changing her degree program", contextInfo);
        loadProcessCategory("kuali.process.type.registration", ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, "Course Registration",
                "Processses involving registration in courses, including both initial and subsequent via add/drop processes",
                "Processses involving registration in courses, including both initial and subsequent via add/drop processes", contextInfo);
        loadProcessCategory("kuali.process.type.enrollment", ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, "Program Enrollment",
                "Program enrollment processes, picking, changing major, adding a minor or 2nd major", "Program enrollment processes, picking, changing major, adding a minor or 2nd major", contextInfo);
        loadProcessCategory("kuali.process.type.acad.record", ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, "Academic Record",
                "Processes invovling access to the academic record including things like requesting and generating transcripts, getting access to grades and verification of attendance and degrees awarded",
                "Processes invovling access to the academic record including things like requesting and generating transcripts, getting access to grades and verification of attendance and degrees awarded",
                contextInfo);
        loadProcessCategory("kuali.process.type.graduation", ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, "Graduation",
                "Processes involving the awarding of the degree, participating in commencement and/or physically receiving the diploma",
                "Processes involving the awarding of the degree, participating in commencement and/or physically receiving the diploma",
                contextInfo);
        loadProcessCategory("kuali.process.type.student.accounts", ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, "Student Accounts",
                "Processes involved in setting up and managing a student's account, adding charges, payments, processing refunds",
                "Processes involved in setting up and managing a student's account, adding charges, payments, processing refunds",
                contextInfo);
        loadProcessCategory("kuali.process.type.library", ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, "Library",
                "Processes involving the use of the library both physically or on-line",
                "Processes involving the use of the library both physically or on-line",
                contextInfo);
        loadProcessCategory("kuali.process.type.housing", ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, "Housing",
                "Processes involving housing and dorm access and assignment",
                "Processes involving housing and dorm access and assignment",
                contextInfo);

        // load checks
        loadCheck(ProcessServiceConstants.CHECK_KEY_IS_ALIVE, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, "is alive",
                "Checks if student is actually alive",
                "Checks if student is actually alive",
                null, // issue id
                null, // milestone type
                "kuali.agenda.is.alive", // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_HAS_BEEN_ADMITTED, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, "has been admitted",
                "Checks if student has been admitted",
                "Checks if student has been admitted",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_HAS_CONFIRMED_INTEND_TO_ATTEND, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, "has confirmed intent to attend",
                "Checks if student has confirmed that they actually have accepted the offer and intend to attend",
                "Checks if student has confirmed that they actually have accepted the offer and intend to attend",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_HAS_NOT_BEEN_EXPELLED, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, "has not been expelled",
                "Checks if the student has not been expelled",
                "Checks if the student has not been expelled",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_HAS_OVERDUE_LIBRARY_BOOK, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.HOLD_CHECK_TYPE_KEY, "has overdue library book",
                "Checks if student has an overdue library book",
                "Checks if student has an overdue library book",
                "kuali.hold.issue.library.book.overdue", // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_HAS_UNPAID_LIBRARY_FINE, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.HOLD_CHECK_TYPE_KEY, "has unpaid library fine",
                "Checks if student has an unpaid library fine",
                "Checks if student has an unpaid library fine",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_HAS_NOT_PAID_BILL_FROM_PRIOR_TERM, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.HOLD_CHECK_TYPE_KEY, "has not paid bill from prior term",
                "Checks if student has an unpaid bill from a prior term",
                "Checks if student has an unpaid bill from a prior term",
                "kuali.hold.issue.financial.unpaid.tuition.prior.term", // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_HAS_ACKNOWLEDGED_RIAA, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.ACKNOWLEDGEMENT_CHECK_TYPE_KEY , "has acknowledged RIAA",
                "Checks if student has acknowledged RIAA",
                "Checks if student has acknowledged RIAA",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_HAS_ACKNOWLEDGED_HONOR_CODE, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.ACKNOWLEDGEMENT_CHECK_TYPE_KEY , "has acknowledged honour code",
                "Checks if student has acknowledged honour code",
                "Checks if student has acknowledged honour code",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_HAS_VERIFIED_EMERGENCY_CONTACT_DATA, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.ACKNOWLEDGEMENT_CHECK_TYPE_KEY , "has verified emergency contact data",
                "Checks if student has verified emergency contact data",
                "Checks if student has verified emergency contact data",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_HAS_APPLIED_TO_GRADUATE, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.ACKNOWLEDGEMENT_CHECK_TYPE_KEY , "has applied to graduate",
                "Checks if student has applied to graduate",
                "Checks if student has applied to graduate",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);

    }

    private void loadProcessCategory (String categoryId, String type, String state, String name,
                                      String descriptionPlain, String descriptionFormatted, ContextInfo contextInfo)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        ProcessCategoryInfo info = new ProcessCategoryInfo();
        info.setId(categoryId);
        info.setTypeKey(type);
        info.setStateKey(state);
        info.setName(name);
        info.setDescr(new RichTextInfo(descriptionPlain, descriptionFormatted));
        processService.createProcessCategory(type, info, contextInfo);
    }

    private void loadProcess (String processId, String type, String state, String name,
                              String descriptionPlain, String descriptionFormatted,
                              String categoryId, ContextInfo contextInfo)
            throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        ProcessInfo info = new ProcessInfo();
        info.setKey(processId);
        info.setTypeKey(type);
        info.setStateKey(state);
        info.setName(name);
        info.setDescr(new RichTextInfo(descriptionPlain, descriptionFormatted));
        processService.createProcess(processId, type, info, contextInfo);
        processService.addProcessToProcessCategory(processId, categoryId, contextInfo);
    }

    private void loadCheck (String checkId, String type, String state, String name, String descriptionPlain, String descriptionFormatted,
                            String issueId,String milestoneType, String agendaId, String checkRightAgendaId,
                            String checkLeftAgendaId, String checkChildProcessId, ContextInfo contextInfo)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        CheckInfo info = new CheckInfo();
        info.setId(checkId);
        info.setTypeKey(type);
        info.setStateKey(state);
        info.setName(name);
        info.setDescr(new RichTextInfo(descriptionPlain, descriptionFormatted));
        info.setIssueId(issueId);
        info.setMilestoneTypeKey(milestoneType);
        info.setAgendaId(agendaId);
        info.setRightComparisonAgendaId(checkRightAgendaId);
        info.setLeftComparisonAgendaId(checkLeftAgendaId);
        info.setChildProcessKey(checkChildProcessId);
        processService.createCheck(type, info, contextInfo);
    }

    private void loadInstruction (String instructionId, String type, String state,
                                  Date effectiveDate, Date expirationDate,
                                  String processId, String checkId, String appliedPopulationId,
                                  String messagePlain, String messageFormatted, int position,
                                  boolean warning, boolean continueOnFail, boolean exemptible,
                                  List<String> appliedAtpTypes, ContextInfo contextInfo)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        InstructionInfo info = new InstructionInfo();
        info.setId(instructionId);
        info.setTypeKey(type);
        info.setStateKey(state);
        info.setEffectiveDate(effectiveDate);
        info.setExpirationDate(expirationDate);
        info.setProcessKey(processId);
        info.setCheckKey(checkId);
        info.setAppliedPopulationKey(appliedPopulationId);
        info.setMessage(new RichTextInfo(messagePlain, messageFormatted));
        info.setPosition(position);
        info.setIsWarning(warning);
        info.setContinueOnFail(continueOnFail);
        info.setIsExemptible(exemptible);
        info.setAppliedAtpTypeKeys(appliedAtpTypes);
        processService.createInstruction(processId, checkId, type, info, contextInfo);
    }
}