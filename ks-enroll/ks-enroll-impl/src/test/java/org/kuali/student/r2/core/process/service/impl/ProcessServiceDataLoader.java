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

    public void loadData () throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (debugMode) { logger.warn("loadData called"); }

        // create the context
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
        contextInfo.setCurrentDate(new Date());
        CommonServiceConstants.setIsIdAllowedOnCreate(contextInfo, true);

        // load process categories
        // -----------------------------
        loadProcessCategory(ProcessServiceConstants.PROCESS_CATEGORY_KEY_ADMISSIONS, ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, "Admissions",
                "Blocks a student from changing her degree program", "Blocks a student from changing her degree program", contextInfo);
        loadProcessCategory(ProcessServiceConstants.PROCESS_CATEGORY_KEY_COURSE_REGISTRATION, ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, "Course Registration",
                "Processses involving registration in courses, including both initial and subsequent via add/drop processes",
                "Processses involving registration in courses, including both initial and subsequent via add/drop processes", contextInfo);
        loadProcessCategory(ProcessServiceConstants.PROCESS_CATEGORY_KEY_PROGRAM_ENROLLMENT, ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, "Program Enrollment",
                "Program enrollment processes, picking, changing major, adding a minor or 2nd major", "Program enrollment processes, picking, changing major, adding a minor or 2nd major", contextInfo);
        loadProcessCategory(ProcessServiceConstants.PROCESS_CATEGORY_KEY_ACADEMIC_RECORD, ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, "Academic Record",
                "Processes invovling access to the academic record including things like requesting and generating transcripts, getting access to grades and verification of attendance and degrees awarded",
                "Processes invovling access to the academic record including things like requesting and generating transcripts, getting access to grades and verification of attendance and degrees awarded",
                contextInfo);
        loadProcessCategory(ProcessServiceConstants.PROCESS_CATEGORY_KEY_GRADUATION, ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, "Graduation",
                "Processes involving the awarding of the degree, participating in commencement and/or physically receiving the diploma",
                "Processes involving the awarding of the degree, participating in commencement and/or physically receiving the diploma",
                contextInfo);
        loadProcessCategory(ProcessServiceConstants.PROCESS_CATEGORY_KEY_STUDENT_ACCOUNTS, ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, "Student Accounts",
                "Processes involved in setting up and managing a student's account, adding charges, payments, processing refunds",
                "Processes involved in setting up and managing a student's account, adding charges, payments, processing refunds",
                contextInfo);
        loadProcessCategory(ProcessServiceConstants.PROCESS_CATEGORY_KEY_LIBRARY, ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, "Library",
                "Processes involving the use of the library both physically or on-line",
                "Processes involving the use of the library both physically or on-line",
                contextInfo);
        loadProcessCategory(ProcessServiceConstants.PROCESS_CATEGORY_KEY_HOUSING, ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, "Housing",
                "Processes involving housing and dorm access and assignment",
                "Processes involving housing and dorm access and assignment",
                contextInfo);

        // load checks
        // --------------
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
        loadCheck(ProcessServiceConstants.CHECK_KEY_HAS_ACKNOWLEDGED_RIAA, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.ACKNOWLEDGEMENT_CHECK_TYPE_KEY, "has acknowledged RIAA",
                "Checks if student has acknowledged RIAA",
                "Checks if student has acknowledged RIAA",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_HAS_ACKNOWLEDGED_HONOR_CODE, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.ACKNOWLEDGEMENT_CHECK_TYPE_KEY, "has acknowledged honour code",
                "Checks if student has acknowledged honour code",
                "Checks if student has acknowledged honour code",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_HAS_VERIFIED_EMERGENCY_CONTACT_DATA, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.ACKNOWLEDGEMENT_CHECK_TYPE_KEY, "has verified emergency contact data",
                "Checks if student has verified emergency contact data",
                "Checks if student has verified emergency contact data",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_HAS_APPLIED_TO_GRADUATE, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.ACKNOWLEDGEMENT_CHECK_TYPE_KEY, "has applied to graduate",
                "Checks if student has applied to graduate",
                "Checks if student has applied to graduate",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_STUDENT_HAS_BASIC_ELIGIBILITY, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, "student has basic eligibility",
                "Checks all the checks defined in the basic eligibility process",
                "Checks all the checks defined in the basic eligibility process",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_IS_STUDENT_EXPECTED_IN_TERM, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, "is student expected in term",
                "Checks if the student is actually expected to register in the term",
                "Checks if the student is actually expected to register in the term",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_REGISTRATION_PERIOD_IS_OPEN, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.START_DATE_CHECK_TYPE_KEY, "registration period is open",
                "Checks that the registration period is open",
                "Checks that the registration period is open",
                null, // issue id
                "kuali.atp.milestone.RegistrationPeriod", // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_REGISTRATION_PERIOD_IS_NOT_CLOSED, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DEADLINE_CHECK_TYPE_KEY, "registration period is not yet closed",
                "Checks that the registration period is not yet closed",
                "Checks that the registration period is not yet closed",
                null, // issue id
                "kuali.atp.milestone.RegistrationPeriod", // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_REGISTRATION_HOLDS_CLEARED, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, "Registration Holds Cleared",
                "Checks that the checks in the registration holds process",
                "Checks that the checks in the registration holds process",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                ProcessServiceConstants.PROCESS_KEY_HOLDS_CLEARED, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_ACKNOWLEDGEMENTS_CONFIRMED, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, "Acknowledgements Confirmed",
                "Checks all the acknowledgements have been cleared",
                "Checks all the acknowledgements have been cleared",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_MAX_TOTAL_CREDITS_ALLOWED, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.MAXIMUM_VALUE_CHECK_TYPE_KEY, "Maximum Total Credits Allowed",
                "cannot have already earned more than 32 credits",
                "cannot have already earned more than 32 credits",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_INTERNATIONAL_STUDENT_CHECK_IN, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, "International student check in",
                "International student needs to check-in when they first arrive",
                "International student needs to check-in when they first arrive",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_IS_NOT_SUMMER_TERM, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, "is not Summer Term",
                "Checks that this is not the summer term",
                "Checks that this is not the summer term",
                null, // issue id
                null, // milestone type
                "kuali.agenda.is.not.summer.term", // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_MANDATORY_ADVISING_CHECK, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, "Mandatory Advising Check",
                "Mandatory Advising Check",
                "Mandatory Advising Check",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_UNRESOLVED_INCOMPLETE_GRADES, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, "Unresolved Incomplete grades",
                "Unresolved Incomplete grades",
                "Unresolved Incomplete grades",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_ELIGIBILITY_FOR_TERM, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, "Eligibility for Terms",
                "Checks all the checks that the student is eligible for the term",
                "Checks all the checks that the student is eligible for the term",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_HAS_THE_NECESSARY_PREREQ, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.INDIRECT_RULE_CHECK_TYPE_KEY, "Has the necessary pre-req",
                "Checks that the student has all the necessary pre-requisites to take the course",
                "Checks that the student has all the necessary pre-requisites to take the course",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_IS_ELIGIBLE_FOR_THE_COURSE_OFFERING, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.INDIRECT_RULE_CHECK_TYPE_KEY, "is eligible for the course offering",
                "Checks that the student passes all the eligibility checks associated with the course offering",
                "Checks that the student passes all the eligibility checks associated with the course offering",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_NORTH_STUDENTS_MAX_SOUTH_CREDITS, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.MINIMUM_VALUE_CHECK_TYPE_KEY, "North Students Max South Credits",
                "North Campus Students can't take South Campus courses until 25 credits",
                "North Campus Students can't take South Campus courses until 25 credits",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_SOUTH_STUDENTS_MAX_NORTH_CREDITS, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.MINIMUM_VALUE_CHECK_TYPE_KEY, "South Students Max North Credits",
                "South Campus Students can't take North Campus courses until 25 credits",
                "South Campus Students can't take North Campus courses until 25 credits",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_STUDENT_HAS_ELIGIBILITY_FOR_EACH_COURSE, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, "student has eligibility for each course",
                "Checks all the checks that make sure the student is eligible for a particular course but does it for all the courses in the proposed set of courses",
                "Checks all the checks that make sure the student is eligible for a particular course but does it for all the courses in the proposed set of courses",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSE, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_DOES_NOT_EXCEED_CREDIT_LIMIT, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.MAXIMUM_VALUE_CHECK_TYPE_KEY, "does not exceed credit limit",
                "Checks that the student has not exceeded her credit limit",
                "Checks that the student has not exceeded her credit limit",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_DOES_NOT_MEET_CREDIT_MINIMUM, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.MINIMUM_VALUE_CHECK_TYPE_KEY, "does not meet credit minimum",
                "Checks that the student has enough credits to meet the minimum required for her student type",
                "Checks that the student has enough credits to meet the minimum required for her student type",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_DOES_NOT_HAVE_A_TIME_CONFLICT, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, "Does not have a time conflict",
                "Checks that the set of courses does not have a time conflict between them or that it does not overlap 'too much'",
                "Checks that the set of courses does not have a time conflict between them or that it does not overlap 'too much'",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_TOO_MANY_COURSES_DURING_INITIAL_REGISTRATION_PERIOD, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.MAXIMUM_VALUE_CHECK_TYPE_KEY, "Too many courses during initial registration period",
                "Checks if the student has registered for too many courses for this particular registration period",
                "Checks if the student has registered for too many courses for this particular registration period",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_STUDENT_IS_ELIGIBLE_FOR_THE_COURSES, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, "student is eligible for the courses",
                "Checks if student is eligible for the courses",
                "Checks if student is eligible for the courses",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_IS_STUDENTS_REGISTRATION_WINDOW, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, "is Student's Registration Window",
                "Checks if is Student's Registration Window",
                "Checks if is Student's Registration Window",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_COURSE_HAS_ROOM_FOR_STUDENT_IN_A_SEATPOOL, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, "course has room for student in a seatpool",
                "Checks if course has room for student in a seatpool",
                "Checks if course has room for student in a seatpool",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_GRADES_HAVE_BEEN_SUBMITTED_FOR_COURSE, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.HOLD_CHECK_TYPE_KEY, "Grades have been submitted for course",
                "Checks if Grades have been submitted for course",
                "Checks if Grades have been submitted for course",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);
        loadCheck(ProcessServiceConstants.CHECK_KEY_HAS_COMPLETED_COURSE_EVALUATION, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, "has completed course evaluation",
                "Checks if student has completed course evaluation",
                "Checks if student has completed course evaluation",
                null, // issue id
                null, // milestone type
                null, // agenda id
                null, // right
                null, // left
                null, // child process id
                contextInfo);

        // load processes
        // ------------------
        loadProcess(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, ProcessServiceConstants.PROCESS_TYPE_KEY,
                ProcessServiceConstants.PROCESS_ENABLED_STATE_KEY, "Basic Eligibility",
                "The process of checking a student's basic eligibility to register for courses",
                "The process of checking a student's basic eligibility to register for courses",
                ProcessServiceConstants.PROCESS_CATEGORY_KEY_COURSE_REGISTRATION, contextInfo);

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