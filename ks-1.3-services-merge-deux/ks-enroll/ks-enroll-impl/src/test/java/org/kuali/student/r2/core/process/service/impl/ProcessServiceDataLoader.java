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
import org.kuali.student.r2.core.process.ProcessPocConstants;
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
    public static final String PROCESS_CATEGORY_ID_GRADUATION = "6";

    ///////////////////
    // CONSTANTS
    ///////////////////

    /**
     * known process category ids / names
     */
    public static final String PROCESS_CATEGORY_ID_ADMISSIONS = "TESTID.2";
    public static final String PROCESS_CATEGORY_NAME_ADMISSIONS = "kuali.process.type.admissions";
    public static final String PROCESS_CATEGORY_ID_COURSE_REGISTRATION = "TESTID.3";
    public static final String PROCESS_CATEGORY_NAME_COURSE_REGISTRATION = "kuali.process.type.registration";
    public static final String PROCESS_CATEGORY_ID_PROGRAM_ENROLLMENT = "TESTID.4";
    public static final String PROCESS_CATEGORY_NAME_PROGRAM_ENROLLMENT = "kuali.process.type.enrollment";
    public static final String PROCESS_CATEGORY_ID_ACADEMIC_RECORD = "TESTID.5";
    public static final String PROCESS_CATEGORY_NAME_ACADEMIC_RECORD = "kuali.process.type.acad.record";
    public static final String PROCESS_CATEGORY_NAME_GRADUATION = "kuali.process.type.graduation";
    public static final String PROCESS_CATEGORY_ID_STUDENT_ACCOUNTS = "TESTID.7";
    public static final String PROCESS_CATEGORY_NAME_STUDENT_ACCOUNTS = "kuali.process.type.student.accounts";
    public static final String PROCESS_CATEGORY_ID_LIBRARY = "TESTID.8";
    public static final String PROCESS_CATEGORY_NAME_LIBRARY = "kuali.process.type.library";
    public static final String PROCESS_CATEGORY_ID_HOUSING = "TESTID.9";
    public static final String PROCESS_CATEGORY_NAME_HOUSING = "kuali.process.type.housing";

    /**
     * known check ids / names
     */
    public static final String CHECK_ID_IS_ALIVE = "TESTID.2";
    public static final String CHECK_NAME_IS_ALIVE = "kuali.check.is.alive";
    public static final String CHECK_ID_HAS_BEEN_ADMITTED = "TESTID.3";
    public static final String CHECK_NAME_HAS_BEEN_ADMITTED = "kuali.check.has.been.admitted";
    public static final String CHECK_ID_HAS_CONFIRMED_INTEND_TO_ATTEND = "TESTID.4";
    public static final String CHECK_NAME_HAS_CONFIRMED_INTEND_TO_ATTEND = "kuali.check.has.confirmed.intend.to.attend";
    public static final String CHECK_ID_HAS_NOT_BEEN_EXPELLED = "TESTID.5";
    public static final String CHECK_NAME_HAS_NOT_BEEN_EXPELLED = "kuali.check.has.not.been.expelled";
    public static final String CHECK_ID_HAS_OVERDUE_LIBRARY_BOOK = "TESTID.7";
    public static final String CHECK_NAME_HAS_OVERDUE_LIBRARY_BOOK = "kuali.check.has.overdue.library.book";
    public static final String CHECK_ID_HAS_UNPAID_LIBRARY_FINE = "TESTID.8";
    public static final String CHECK_NAME_HAS_UNPAID_LIBRARY_FINE = "kuali.check.has.unpaid.library.fine";
    public static final String CHECK_ID_HAS_NOT_PAID_BILL_FROM_PRIOR_TERM = "TESTID.9";
    public static final String CHECK_NAME_HAS_NOT_PAID_BILL_FROM_PRIOR_TERM = "kuali.check.has.not.paid.bill.from.prior.term";
    public static final String CHECK_ID_HAS_ACKNOWLEDGED_RIAA = "TESTID.11";
    public static final String CHECK_NAME_HAS_ACKNOWLEDGED_RIAA = "kuali.check.has.acknowledged.riaa";
    public static final String CHECK_ID_HAS_ACKNOWLEDGED_HONOR_CODE = "TESTID.12";
    public static final String CHECK_NAME_HAS_ACKNOWLEDGED_HONOR_CODE = "kuali.check.has.acknowledged.honor.code";
    public static final String CHECK_ID_HAS_VERIFIED_EMERGENCY_CONTACT_DATA = "TESTID.13";
    public static final String CHECK_NAME_HAS_VERIFIED_EMERGENCY_CONTACT_DATA = "kuali.check.has.verified.emergency.contact.data";
    public static final String CHECK_ID_HAS_APPLIED_TO_GRADUATE = "TESTID.14";
    public static final String CHECK_NAME_HAS_APPLIED_TO_GRADUATE = "kuali.check.has.applied.to.graduate";
    public static final String CHECK_ID_STUDENT_HAS_BASIC_ELIGIBILITY = "TESTID.16";
    public static final String CHECK_NAME_STUDENT_HAS_BASIC_ELIGIBILITY = "kuali.check.student.has.basic.eligibility";
    public static final String CHECK_ID_IS_STUDENT_EXPECTED_IN_TERM = "TESTID.17";
    public static final String CHECK_NAME_IS_STUDENT_EXPECTED_IN_TERM = "kuali.check.is.student.expected.in.term";
    public static final String CHECK_ID_REGISTRATION_PERIOD_IS_OPEN = ProcessPocConstants.CHECK_ID_REGISTRATION_PERIOD_IS_OPEN;
    public static final String CHECK_NAME_REGISTRATION_PERIOD_IS_OPEN = "kuali.check.registration.period.is.open";
    public static final String CHECK_ID_REGISTRATION_PERIOD_IS_NOT_CLOSED = ProcessPocConstants.CHECK_ID_REGISTRATION_PERIOD_IS_NOT_CLOSED;
    public static final String CHECK_NAME_REGISTRATION_PERIOD_IS_NOT_CLOSED = "kuali.check.registration.period.is.not.closed";
    public static final String CHECK_ID_REGISTRATION_HOLDS_CLEARED = "TESTID.20";
    public static final String CHECK_NAME_REGISTRATION_HOLDS_CLEARED = "kuali.check.registration.holds.cleared";
    public static final String CHECK_ID_ACKNOWLEDGEMENTS_CONFIRMED = "TESTID.21";
    public static final String CHECK_NAME_ACKNOWLEDGEMENTS_CONFIRMED = "kuali.check.acknowledgements.confirmed";
    public static final String CHECK_ID_MAX_TOTAL_CREDITS_ALLOWED = "TESTID.22";
    public static final String CHECK_NAME_MAX_TOTAL_CREDITS_ALLOWED = "kuali.check.max.total.credits.allowed";
    public static final String CHECK_ID_INTERNATIONAL_STUDENT_CHECK_IN = "TESTID.23";
    public static final String CHECK_NAME_INTERNATIONAL_STUDENT_CHECK_IN = "kuali.check.international.student.check.in";
    public static final String CHECK_ID_IS_NOT_SUMMER_TERM = "TESTID.24";
    public static final String CHECK_NAME_IS_NOT_SUMMER_TERM = "kuali.check.is.not.summer.term";
    public static final String CHECK_ID_MANDATORY_ADVISING_CHECK = "TESTID.25";
    public static final String CHECK_NAME_MANDATORY_ADVISING_CHECK = "kuali.check.mandatory.advising.check";
    public static final String CHECK_ID_UNRESOLVED_INCOMPLETE_GRADES = "TESTID.26";
    public static final String CHECK_NAME_UNRESOLVED_INCOMPLETE_GRADES = "kuali.check.unresolved.incomplete.grades";
    public static final String CHECK_ID_ELIGIBILITY_FOR_TERM = "TESTID.28";
    public static final String CHECK_NAME_ELIGIBILITY_FOR_TERM = "kuali.check.eligibility.for.term";
    public static final String CHECK_ID_HAS_THE_NECESSARY_PREREQ = "TESTID.29";
    public static final String CHECK_NAME_HAS_THE_NECESSARY_PREREQ = "kuali.check.has.the.necessary.prereq";
    public static final String CHECK_ID_IS_ELIGIBLE_FOR_THE_COURSE_OFFERING = "TESTID.30";
    public static final String CHECK_NAME_IS_ELIGIBLE_FOR_THE_COURSE_OFFERING = "kuali.check.is.eligible.for.the.course.offering";
    public static final String CHECK_ID_NORTH_STUDENTS_MAX_SOUTH_CREDITS = "TESTID.31";
    public static final String CHECK_NAME_NORTH_STUDENTS_MAX_SOUTH_CREDITS = "kuali.check.north.students.max.south.credits";
    public static final String CHECK_ID_SOUTH_STUDENTS_MAX_NORTH_CREDITS = "TESTID.32";
    public static final String CHECK_NAME_SOUTH_STUDENTS_MAX_NORTH_CREDITS = "kuali.check.south.students.max.north.credits";
    public static final String CHECK_ID_STUDENT_HAS_ELIGIBILITY_FOR_EACH_COURSE = "TESTID.34";
    public static final String CHECK_NAME_STUDENT_HAS_ELIGIBILITY_FOR_EACH_COURSE = "kuali.check.student.has.eligibility.for.each.course";
    public static final String CHECK_ID_DOES_NOT_EXCEED_CREDIT_LIMIT = "TESTID.35";
    public static final String CHECK_NAME_DOES_NOT_EXCEED_CREDIT_LIMIT = "kuali.check.does.not.exceed.credit.limit";
    public static final String CHECK_ID_DOES_NOT_MEET_CREDIT_MINIMUM = "TESTID.36";
    public static final String CHECK_NAME_DOES_NOT_MEET_CREDIT_MINIMUM = "kuali.check.does.not.meet.credit.minimum";
    public static final String CHECK_ID_DOES_NOT_HAVE_A_TIME_CONFLICT = "TESTID.37";
    public static final String CHECK_NAME_DOES_NOT_HAVE_A_TIME_CONFLICT = "kuali.check.does.not.have.a.time.conflict";
    public static final String CHECK_ID_TOO_MANY_COURSES_DURING_INITIAL_REGISTRATION_PERIOD = "TESTID.38";
    public static final String CHECK_NAME_TOO_MANY_COURSES_DURING_INITIAL_REGISTRATION_PERIOD = "kuali.check.too.many.courses.during.initial.registration.period";
    public static final String CHECK_ID_STUDENT_IS_ELIGIBLE_FOR_THE_COURSES = "TESTID.40";
    public static final String CHECK_NAME_STUDENT_IS_ELIGIBLE_FOR_THE_COURSES = "kuali.check.student.is.eligible.for.the.courses";
    public static final String CHECK_ID_IS_STUDENTS_REGISTRATION_WINDOW = "TESTID.41";
    public static final String CHECK_NAME_IS_STUDENTS_REGISTRATION_WINDOW = "kuali.check.is.students.registration.window";
    public static final String CHECK_ID_COURSE_HAS_ROOM_FOR_STUDENT_IN_A_SEATPOOL = "TESTID.42";
    public static final String CHECK_NAME_COURSE_HAS_ROOM_FOR_STUDENT_IN_A_SEATPOOL = "kuali.check.course.has.room.for.student.in.a.seatpool";
    public static final String CHECK_ID_GRADES_HAVE_BEEN_SUBMITTED_FOR_COURSE = "TESTID.44";
    public static final String CHECK_NAME_GRADES_HAVE_BEEN_SUBMITTED_FOR_COURSE = "kuali.check.grades.have.been.submitted.for.course";
    public static final String CHECK_ID_HAS_COMPLETED_COURSE_EVALUATION = "TESTID.45";
    public static final String CHECK_NAME_HAS_COMPLETED_COURSE_EVALUATION = "kuali.check.has.completed.course.evaluation";

    /**
     * known population ids / names
     */
    public static final String POPULATION_ID_EVERYONE = "TESTID.2";
    public static final String POPULATION_NAME_EVERYONE = "kuali.population.everyone";
    public static final String POPULATION_ID_FINAL_TERM_SENIORS = "TESTID.3";
    public static final String POPULATION_NAME_FINAL_TERM_SENIORS = "kuali.population.final.term.seniors";
    public static final String POPULATION_ID_NOT_IN_A_DEGREE_GRANTING_PROGRAM = "TESTID.4";
    public static final String POPULATION_NAME_NOT_IN_A_DEGREE_GRANTING_PROGRAM = "kuali.population.not.in.a.degree.granting.program";
    public static final String POPULATION_ID_INTERNATIONAL_STUDENTS = "TESTID.6";
    public static final String POPULATION_NAME_INTERNATIONAL_STUDENTS = "kuali.population.international.student";
    public static final String POPULATION_ID_SUMMER_ONLY_STUDENT = "TESTID.7";
    public static final String POPULATION_NAME_SUMMER_ONLY_STUDENT = "kuali.population.summer.only.student";
    public static final String POPULATION_ID_FRESHMAN = "TESTID.8";
    public static final String POPULATION_NAME_FRESHMAN = "kuali.population.freshman";
    public static final String POPULATION_ID_SOPHOMORE = "TESTID.9";
    public static final String POPULATION_NAME_SOPHOMORE = "kuali.population.sophomore";
    public static final String POPULATION_ID_UNDERGRADUATE = "TESTID.11";
    public static final String POPULATION_NAME_UNDERGRADUATE = "kuali.population.undergraduate";
    public static final String POPULATION_ID_NORTH_CAMPUS_STUDENTS = "TESTID.12";
    public static final String POPULATION_NAME_NORTH_CAMPUS_STUDENTS = "kuali.population.north.campus.students";
    public static final String POPULATION_ID_SOUTH_CAMPUS_STUDENTS = "TESTID.13";
    public static final String POPULATION_NAME_SOUTH_CAMPUS_STUDENTS = "kuali.population.south.campus.students";

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

        // check if data has already been loaded, then don't load it again
        try {
            // try to load a process
            ProcessInfo processInfo = processService.getProcess(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, contextInfo);
        } catch (DoesNotExistException e) {
            // the process wasn't loaded, so data is not there, therefore load the data

            // load process categories
            // -----------------------------
            if (debugMode) { logger.warn("loading process categories"); }
            // Admissions
            loadProcessCategory(PROCESS_CATEGORY_ID_ADMISSIONS, ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, PROCESS_CATEGORY_NAME_ADMISSIONS,
                    "Blocks a student from changing her degree program", "Blocks a student from changing her degree program", contextInfo);
            // Course Registration
            loadProcessCategory(PROCESS_CATEGORY_ID_COURSE_REGISTRATION, ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, PROCESS_CATEGORY_NAME_COURSE_REGISTRATION,
                    "Processses involving registration in courses, including both initial and subsequent via add/drop processes",
                    "Processses involving registration in courses, including both initial and subsequent via add/drop processes", contextInfo);
            // Program Enrollment
            loadProcessCategory(PROCESS_CATEGORY_ID_PROGRAM_ENROLLMENT, ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, PROCESS_CATEGORY_NAME_PROGRAM_ENROLLMENT,
                    "Program enrollment processes, picking, changing major, adding a minor or 2nd major", "Program enrollment processes, picking, changing major, adding a minor or 2nd major", contextInfo);
            // Academic Record
            loadProcessCategory(PROCESS_CATEGORY_ID_ACADEMIC_RECORD, ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, PROCESS_CATEGORY_NAME_ACADEMIC_RECORD,
                    "Processes invovling access to the academic record including things like requesting and generating transcripts, getting access to grades and verification of attendance and degrees awarded",
                    "Processes invovling access to the academic record including things like requesting and generating transcripts, getting access to grades and verification of attendance and degrees awarded",
                    contextInfo);
            // Graduation
            loadProcessCategory(PROCESS_CATEGORY_ID_GRADUATION, ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, PROCESS_CATEGORY_NAME_GRADUATION,
                    "Processes involving the awarding of the degree, participating in commencement and/or physically receiving the diploma",
                    "Processes involving the awarding of the degree, participating in commencement and/or physically receiving the diploma",
                    contextInfo);
            // Student Accounts
            loadProcessCategory(PROCESS_CATEGORY_ID_STUDENT_ACCOUNTS, ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, PROCESS_CATEGORY_NAME_STUDENT_ACCOUNTS,
                    "Processes involved in setting up and managing a student's account, adding charges, payments, processing refunds",
                    "Processes involved in setting up and managing a student's account, adding charges, payments, processing refunds",
                    contextInfo);
            // Library
            loadProcessCategory(PROCESS_CATEGORY_ID_LIBRARY, ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, PROCESS_CATEGORY_NAME_LIBRARY,
                    "Processes involving the use of the library both physically or on-line",
                    "Processes involving the use of the library both physically or on-line",
                    contextInfo);
            // Housing
            loadProcessCategory(PROCESS_CATEGORY_ID_HOUSING, ProcessServiceConstants.PROCESS_CATEGORY_TYPE_KEY_CATEGORY, ProcessServiceConstants.PROCESS_CATEGORY_STATE_KEY_ACTIVE, PROCESS_CATEGORY_NAME_HOUSING,
                    "Processes involving housing and dorm access and assignment",
                    "Processes involving housing and dorm access and assignment",
                    contextInfo);

            // load checks
            // --------------
            if (debugMode) { logger.warn("loading checks"); }
            loadCheck(CHECK_ID_IS_ALIVE, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, CHECK_NAME_IS_ALIVE,
                    "Checks if student is actually alive",
                    "Checks if student is actually alive",
                    null, // issue id
                    null, // milestone type
                    ProcessServiceConstants.AGENDA_IS_ALIVE_ID, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_HAS_BEEN_ADMITTED, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, CHECK_NAME_HAS_BEEN_ADMITTED,
                    "Checks if student has been admitted",
                    "Checks if student has been admitted",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_HAS_CONFIRMED_INTEND_TO_ATTEND, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, CHECK_NAME_HAS_CONFIRMED_INTEND_TO_ATTEND,
                    "Checks if student has confirmed that they actually have accepted the offer and intend to attend",
                    "Checks if student has confirmed that they actually have accepted the offer and intend to attend",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_HAS_NOT_BEEN_EXPELLED, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, CHECK_NAME_HAS_NOT_BEEN_EXPELLED,
                    "Checks if the student has not been expelled",
                    "Checks if the student has not been expelled",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_HAS_OVERDUE_LIBRARY_BOOK, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.HOLD_CHECK_TYPE_KEY, CHECK_NAME_HAS_OVERDUE_LIBRARY_BOOK,
                    "Checks if student has an overdue library book",
                    "Checks if student has an overdue library book",
                    ProcessServiceConstants.ISSUE_HOLD_LIBRARY_BOOK_OVERDUE_ID, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_HAS_UNPAID_LIBRARY_FINE, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.HOLD_CHECK_TYPE_KEY, CHECK_NAME_HAS_UNPAID_LIBRARY_FINE,
                    "Checks if student has an unpaid library fine",
                    "Checks if student has an unpaid library fine",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_HAS_NOT_PAID_BILL_FROM_PRIOR_TERM, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.HOLD_CHECK_TYPE_KEY, CHECK_NAME_HAS_NOT_PAID_BILL_FROM_PRIOR_TERM,
                    "Checks if student has an unpaid bill from a prior term",
                    "Checks if student has an unpaid bill from a prior term",
                    ProcessServiceConstants.ISSUE_HOLD_UPAID_TUITION_FROM_LAST_TERM_ID, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_HAS_ACKNOWLEDGED_RIAA, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.ACKNOWLEDGEMENT_CHECK_TYPE_KEY, CHECK_NAME_HAS_ACKNOWLEDGED_RIAA,
                    "Checks if student has acknowledged RIAA",
                    "Checks if student has acknowledged RIAA",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_HAS_ACKNOWLEDGED_HONOR_CODE, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.ACKNOWLEDGEMENT_CHECK_TYPE_KEY, CHECK_NAME_HAS_ACKNOWLEDGED_HONOR_CODE,
                    "Checks if student has acknowledged honour code",
                    "Checks if student has acknowledged honour code",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_HAS_VERIFIED_EMERGENCY_CONTACT_DATA, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.ACKNOWLEDGEMENT_CHECK_TYPE_KEY, CHECK_NAME_HAS_VERIFIED_EMERGENCY_CONTACT_DATA,
                    "Checks if student has verified emergency contact data",
                    "Checks if student has verified emergency contact data",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_HAS_APPLIED_TO_GRADUATE, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.ACKNOWLEDGEMENT_CHECK_TYPE_KEY, CHECK_NAME_HAS_APPLIED_TO_GRADUATE,
                    "Checks if student has applied to graduate",
                    "Checks if student has applied to graduate",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_STUDENT_HAS_BASIC_ELIGIBILITY, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, CHECK_NAME_STUDENT_HAS_BASIC_ELIGIBILITY,
                    "Checks all the checks defined in the basic eligibility process",
                    "Checks all the checks defined in the basic eligibility process",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_IS_STUDENT_EXPECTED_IN_TERM, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, CHECK_NAME_IS_STUDENT_EXPECTED_IN_TERM,
                    "Checks if the student is actually expected to register in the term",
                    "Checks if the student is actually expected to register in the term",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_REGISTRATION_PERIOD_IS_OPEN, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.START_DATE_CHECK_TYPE_KEY, CHECK_NAME_REGISTRATION_PERIOD_IS_OPEN,
                    "Checks that the registration period is open",
                    "Checks that the registration period is open",
                    null, // issue id
                    ProcessServiceConstants.MILESTONE_TYPE_ATP_REGISTRATION_PERIOD, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_REGISTRATION_PERIOD_IS_NOT_CLOSED, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DEADLINE_CHECK_TYPE_KEY, CHECK_NAME_REGISTRATION_PERIOD_IS_NOT_CLOSED,
                    "Checks that the registration period is not yet closed",
                    "Checks that the registration period is not yet closed",
                    null, // issue id
                    ProcessServiceConstants.MILESTONE_TYPE_ATP_REGISTRATION_PERIOD, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_REGISTRATION_HOLDS_CLEARED, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, CHECK_NAME_REGISTRATION_HOLDS_CLEARED,
                    "Checks that the checks in the registration holds process",
                    "Checks that the checks in the registration holds process",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    ProcessServiceConstants.PROCESS_KEY_HOLDS_CLEARED, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_ACKNOWLEDGEMENTS_CONFIRMED, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, CHECK_NAME_ACKNOWLEDGEMENTS_CONFIRMED,
                    "Checks all the acknowledgements have been cleared",
                    "Checks all the acknowledgements have been cleared",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_MAX_TOTAL_CREDITS_ALLOWED, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.MAXIMUM_VALUE_CHECK_TYPE_KEY, CHECK_NAME_MAX_TOTAL_CREDITS_ALLOWED,
                    "cannot have already earned more than 32 credits",
                    "cannot have already earned more than 32 credits",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_INTERNATIONAL_STUDENT_CHECK_IN, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, CHECK_NAME_INTERNATIONAL_STUDENT_CHECK_IN,
                    "International student needs to check-in when they first arrive",
                    "International student needs to check-in when they first arrive",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_IS_NOT_SUMMER_TERM, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, CHECK_NAME_IS_NOT_SUMMER_TERM,
                    "Checks that this is not the summer term",
                    "Checks that this is not the summer term",
                    null, // issue id
                    null, // milestone type
                    ProcessServiceConstants.AGENDA_IS_NOT_SUMMER_TERM_ID, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_MANDATORY_ADVISING_CHECK, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, CHECK_NAME_MANDATORY_ADVISING_CHECK,
                    "Mandatory Advising Check",
                    "Mandatory Advising Check",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_UNRESOLVED_INCOMPLETE_GRADES, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, CHECK_NAME_UNRESOLVED_INCOMPLETE_GRADES,
                    "Unresolved Incomplete grades",
                    "Unresolved Incomplete grades",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_ELIGIBILITY_FOR_TERM, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, CHECK_NAME_ELIGIBILITY_FOR_TERM,
                    "Checks all the checks that the student is eligible for the term",
                    "Checks all the checks that the student is eligible for the term",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_HAS_THE_NECESSARY_PREREQ, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.INDIRECT_RULE_CHECK_TYPE_KEY, CHECK_NAME_HAS_THE_NECESSARY_PREREQ,
                    "Checks that the student has all the necessary pre-requisites to take the course",
                    "Checks that the student has all the necessary pre-requisites to take the course",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_IS_ELIGIBLE_FOR_THE_COURSE_OFFERING, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.INDIRECT_RULE_CHECK_TYPE_KEY, CHECK_NAME_IS_ELIGIBLE_FOR_THE_COURSE_OFFERING,
                    "Checks that the student passes all the eligibility checks associated with the course offering",
                    "Checks that the student passes all the eligibility checks associated with the course offering",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_NORTH_STUDENTS_MAX_SOUTH_CREDITS, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.MINIMUM_VALUE_CHECK_TYPE_KEY, CHECK_NAME_NORTH_STUDENTS_MAX_SOUTH_CREDITS,
                    "North Campus Students can't take South Campus courses until 25 credits",
                    "North Campus Students can't take South Campus courses until 25 credits",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_SOUTH_STUDENTS_MAX_NORTH_CREDITS, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.MINIMUM_VALUE_CHECK_TYPE_KEY, CHECK_NAME_SOUTH_STUDENTS_MAX_NORTH_CREDITS,
                    "South Campus Students can't take North Campus courses until 25 credits",
                    "South Campus Students can't take North Campus courses until 25 credits",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_STUDENT_HAS_ELIGIBILITY_FOR_EACH_COURSE, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, CHECK_NAME_STUDENT_HAS_ELIGIBILITY_FOR_EACH_COURSE,
                    "Checks all the checks that make sure the student is eligible for a particular course but does it for all the courses in the proposed set of courses",
                    "Checks all the checks that make sure the student is eligible for a particular course but does it for all the courses in the proposed set of courses",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSE, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_DOES_NOT_EXCEED_CREDIT_LIMIT, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.MAXIMUM_VALUE_CHECK_TYPE_KEY, CHECK_NAME_DOES_NOT_EXCEED_CREDIT_LIMIT,
                    "Checks that the student has not exceeded her credit limit",
                    "Checks that the student has not exceeded her credit limit",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_DOES_NOT_MEET_CREDIT_MINIMUM, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.MINIMUM_VALUE_CHECK_TYPE_KEY, CHECK_NAME_DOES_NOT_MEET_CREDIT_MINIMUM,
                    "Checks that the student has enough credits to meet the minimum required for her student type",
                    "Checks that the student has enough credits to meet the minimum required for her student type",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_DOES_NOT_HAVE_A_TIME_CONFLICT, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, CHECK_NAME_DOES_NOT_HAVE_A_TIME_CONFLICT,
                    "Checks that the set of courses does not have a time conflict between them or that it does not overlap 'too much'",
                    "Checks that the set of courses does not have a time conflict between them or that it does not overlap 'too much'",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_TOO_MANY_COURSES_DURING_INITIAL_REGISTRATION_PERIOD, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.MAXIMUM_VALUE_CHECK_TYPE_KEY, CHECK_NAME_TOO_MANY_COURSES_DURING_INITIAL_REGISTRATION_PERIOD,
                    "Checks if the student has registered for too many courses for this particular registration period",
                    "Checks if the student has registered for too many courses for this particular registration period",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_STUDENT_IS_ELIGIBLE_FOR_THE_COURSES, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, CHECK_NAME_STUDENT_IS_ELIGIBLE_FOR_THE_COURSES,
                    "Checks if student is eligible for the courses",
                    "Checks if student is eligible for the courses",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_IS_STUDENTS_REGISTRATION_WINDOW, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, CHECK_NAME_IS_STUDENTS_REGISTRATION_WINDOW,
                    "Checks if is Student's Registration Window",
                    "Checks if is Student's Registration Window",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_COURSE_HAS_ROOM_FOR_STUDENT_IN_A_SEATPOOL, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, CHECK_NAME_COURSE_HAS_ROOM_FOR_STUDENT_IN_A_SEATPOOL,
                    "Checks if course has room for student in a seatpool",
                    "Checks if course has room for student in a seatpool",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_GRADES_HAVE_BEEN_SUBMITTED_FOR_COURSE, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.HOLD_CHECK_TYPE_KEY, CHECK_NAME_GRADES_HAVE_BEEN_SUBMITTED_FOR_COURSE,
                    "Checks if Grades have been submitted for course",
                    "Checks if Grades have been submitted for course",
                    null, // issue id
                    null, // milestone type
                    null, // agenda id
                    null, // right
                    null, // left
                    null, // child process id
                    contextInfo);
            loadCheck(CHECK_ID_HAS_COMPLETED_COURSE_EVALUATION, ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY, ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY, CHECK_NAME_HAS_COMPLETED_COURSE_EVALUATION,
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
            if (debugMode) { logger.warn("loading processes"); }
            loadProcess(ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY, ProcessServiceConstants.PROCESS_TYPE_KEY,
                    ProcessServiceConstants.PROCESS_ACTIVE_STATE_KEY, "Basic Eligibility",
                    "The process of checking a student's basic eligibility to register for courses",
                    "The process of checking a student's basic eligibility to register for courses",
                    PROCESS_CATEGORY_ID_COURSE_REGISTRATION, ProcessServiceConstants.PROCESS_OWNING_ORG_GRAD_SCHOOL, contextInfo);
            loadProcess(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM, ProcessServiceConstants.PROCESS_TYPE_KEY,
                    ProcessServiceConstants.PROCESS_ACTIVE_STATE_KEY, "Eligibility for Term",
                    "The process of checking a student's eligibility to register for a particular term",
                    "The process of checking a student's eligibility to register for a particular ter.",
                    PROCESS_CATEGORY_ID_COURSE_REGISTRATION, ProcessServiceConstants.PROCESS_OWNING_ORG_GRAD_SCHOOL, contextInfo);
            loadProcess(ProcessServiceConstants.PROCESS_KEY_HOLDS_CLEARED, ProcessServiceConstants.PROCESS_TYPE_KEY,
                    ProcessServiceConstants.PROCESS_ACTIVE_STATE_KEY, "Holds Cleared",
                    "The process of checking a student's eligibility to register for a particular term",
                    "The process of checking a student's eligibility to register for a particular term",
                    PROCESS_CATEGORY_ID_COURSE_REGISTRATION, ProcessServiceConstants.PROCESS_OWNING_ORG_GRAD_SCHOOL, contextInfo);
            loadProcess(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSE, ProcessServiceConstants.PROCESS_TYPE_KEY,
                    ProcessServiceConstants.PROCESS_ACTIVE_STATE_KEY, "Eligible for Course",
                    "The process of checking a student's eligibility to register for a particular course",
                    "The process of checking a student's eligibility to register for a particular course",
                    PROCESS_CATEGORY_ID_COURSE_REGISTRATION, ProcessServiceConstants.PROCESS_OWNING_ORG_GRAD_SCHOOL, contextInfo);
            loadProcess(ProcessServiceConstants.PROCESS_KEY_ELIGIBLE_FOR_COURSES, ProcessServiceConstants.PROCESS_TYPE_KEY,
                    ProcessServiceConstants.PROCESS_ACTIVE_STATE_KEY, "Eligible for Courses",
                    "The process of checking a student's eligibility and ability to register for a proposed set of courses",
                    "The process of checking a student's eligibility and ability to register for a proposed set of courses",
                    PROCESS_CATEGORY_ID_COURSE_REGISTRATION, ProcessServiceConstants.PROCESS_OWNING_ORG_GRAD_SCHOOL, contextInfo);
            loadProcess(ProcessServiceConstants.PROCESS_KEY_ACKNOWLEDGEMENTS_CONFIRMED, ProcessServiceConstants.PROCESS_TYPE_KEY,
                    ProcessServiceConstants.PROCESS_ACTIVE_STATE_KEY, "Acknowledgements Confirmed",
                    "The process of checking a student's eligibility to register for a particular term",
                    "The process of checking a student's eligibility to register for a particular term",
                    PROCESS_CATEGORY_ID_COURSE_REGISTRATION, ProcessServiceConstants.PROCESS_OWNING_ORG_GRAD_SCHOOL, contextInfo);
            loadProcess(ProcessServiceConstants.PROCESS_KEY_REGISTER_FOR_COURSES, ProcessServiceConstants.PROCESS_TYPE_KEY,
                    ProcessServiceConstants.PROCESS_ACTIVE_STATE_KEY, "Register for Courses",
                    "The process of checking a student's eligibility and actually register for a proposed set of courses",
                    "The process of checking a student's eligibility and actually register for a proposed set of courses",
                    PROCESS_CATEGORY_ID_COURSE_REGISTRATION, ProcessServiceConstants.PROCESS_OWNING_ORG_GRAD_SCHOOL, contextInfo);
            loadProcess(ProcessServiceConstants.PROCESS_KEY_VIEW_GRADES, ProcessServiceConstants.PROCESS_TYPE_KEY,
                    ProcessServiceConstants.PROCESS_ACTIVE_STATE_KEY, "View Grades",
                    "The process of checking a student's basic ability to view grades",
                    "The process of checking a student's basic ability to view grades",
                    PROCESS_CATEGORY_ID_ACADEMIC_RECORD, ProcessServiceConstants.PROCESS_OWNING_ORG_GRAD_SCHOOL, contextInfo);
            loadProcess(ProcessServiceConstants.PROCESS_KEY_VIEW_GRADES_FOR_TERM, ProcessServiceConstants.PROCESS_TYPE_KEY,
                    ProcessServiceConstants.PROCESS_ACTIVE_STATE_KEY, "View Grades for Term",
                    "The process of checking a student's basic ability to view grades for a particular term",
                    "The process of checking a student's basic ability to view grades for a particular term",
                    PROCESS_CATEGORY_ID_ACADEMIC_RECORD, ProcessServiceConstants.PROCESS_OWNING_ORG_GRAD_SCHOOL, contextInfo);
            loadProcess(ProcessServiceConstants.PROCESS_KEY_VIEW_COURSE_GRADE, ProcessServiceConstants.PROCESS_TYPE_KEY,
                    ProcessServiceConstants.PROCESS_ACTIVE_STATE_KEY, "View Course Grade",
                    "The process of checking if a student can actually view a grade in a particular course",
                    "The process of checking if a student can actually view a grade in a particular course",
                    PROCESS_CATEGORY_ID_ACADEMIC_RECORD, ProcessServiceConstants.PROCESS_OWNING_ORG_GRAD_SCHOOL, contextInfo);

            // load instructions
            // -------------------
            if (debugMode) { logger.warn("loading instructions"); }
            loadInstruction("TESTID.2",ProcessServiceConstants.INSTRUCTION_TYPE_KEY, ProcessServiceConstants.INSTRUCTION_ACTIVE_STATE_KEY, null, null,
                    ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY,CHECK_ID_IS_ALIVE,POPULATION_ID_EVERYONE,
                    "A key piece of data is wrong on your biographic record, please come to the Registrar's office to clear it up",
                    "A key piece of data is wrong on your biographic record, please come to the Registrar's office to clear it up",1,
                    false, // warning
                    false, // continue on fail
                    false, // exemptible
                    null, contextInfo);
            loadInstruction("TESTID.3",ProcessServiceConstants.INSTRUCTION_TYPE_KEY, ProcessServiceConstants.INSTRUCTION_ACTIVE_STATE_KEY, null, null,
                    ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY,CHECK_ID_HAS_BEEN_ADMITTED,POPULATION_ID_EVERYONE,
                    "According to our records you have not (yet) been admitted to this school",
                    "According to our records you have not (yet) been admitted to this school", 2,
                    false, // warning
                    false, // continue on fail
                    false, // exemptible
                    null, contextInfo);
            loadInstruction("TESTID.4",ProcessServiceConstants.INSTRUCTION_TYPE_KEY, ProcessServiceConstants.INSTRUCTION_ACTIVE_STATE_KEY, null, null,
                    ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY,CHECK_ID_HAS_CONFIRMED_INTEND_TO_ATTEND,POPULATION_ID_EVERYONE,
                    "According to our records you have never confirmed your intent to attend",
                    "According to our records you have never confirmed your intent to attend",3,
                    false, // warning
                    false, // continue on fail
                    true, // exemptible
                    null, contextInfo);
            loadInstruction("TESTID.5",ProcessServiceConstants.INSTRUCTION_TYPE_KEY, ProcessServiceConstants.INSTRUCTION_ACTIVE_STATE_KEY, null, null,
                    ProcessServiceConstants.PROCESS_KEY_BASIC_ELIGIBILITY,CHECK_ID_HAS_NOT_BEEN_EXPELLED,POPULATION_ID_EVERYONE,
                    "You are not allowed to continue at this university",
                    "You are not allowed to continue at this university",4,
                    false, // warning
                    false, // continue on fail
                    false, // exemptible
                    null, contextInfo);
            loadInstruction("TESTID.7",ProcessServiceConstants.INSTRUCTION_TYPE_KEY, ProcessServiceConstants.INSTRUCTION_ACTIVE_STATE_KEY, null, null,
                    ProcessServiceConstants.PROCESS_KEY_HOLDS_CLEARED,CHECK_ID_HAS_OVERDUE_LIBRARY_BOOK,POPULATION_ID_EVERYONE,
                    "Please note: you have an overdue library book",
                    "Please note: you have an overdue library book",3,
                    true, // warning
                    true, // continue on fail
                    true, // exemptible
                    null, contextInfo);
            loadInstruction("TESTID.8",ProcessServiceConstants.INSTRUCTION_TYPE_KEY, ProcessServiceConstants.INSTRUCTION_ACTIVE_STATE_KEY, null, null,
                    ProcessServiceConstants.PROCESS_KEY_HOLDS_CLEARED,CHECK_ID_HAS_UNPAID_LIBRARY_FINE,POPULATION_ID_EVERYONE,
                    "You have an unpaid library fine, please contact the library to resolve the matter",
                    "You have an unpaid library fine, please contact the library to resolve the matter",4,
                    false, // warning
                    false, // continue on fail
                    false, // exemptible
                    null, contextInfo);
            loadInstruction("TESTID.9",ProcessServiceConstants.INSTRUCTION_TYPE_KEY, ProcessServiceConstants.INSTRUCTION_ACTIVE_STATE_KEY, null, null,
                    ProcessServiceConstants.PROCESS_KEY_HOLDS_CLEARED,CHECK_ID_HAS_NOT_PAID_BILL_FROM_PRIOR_TERM,POPULATION_ID_EVERYONE,
                    "You have unpaid tuition charges from last term, please contact the bursars office to resolve this matter",
                    "You have unpaid tuition charges from last term, please contact the bursars office to resolve this matter",5,
                    false, // warning
                    true, // continue on fail
                    true, // exemptible
                    null, contextInfo);
            loadInstruction("TESTID.11",ProcessServiceConstants.INSTRUCTION_TYPE_KEY, ProcessServiceConstants.INSTRUCTION_ACTIVE_STATE_KEY, null, null,
                    ProcessServiceConstants.PROCESS_KEY_ACKNOWLEDGEMENTS_CONFIRMED,CHECK_ID_HAS_ACKNOWLEDGED_RIAA,POPULATION_ID_EVERYONE,
                    "Please acknowledge the RIAA",
                    "Please acknowledge the RIAA",5,
                    false, // warning
                    false, // continue on fail
                    false, // exemptible
                    null, contextInfo);
            loadInstruction("TESTID.12",ProcessServiceConstants.INSTRUCTION_TYPE_KEY, ProcessServiceConstants.INSTRUCTION_ACTIVE_STATE_KEY, null, null,
                    ProcessServiceConstants.PROCESS_KEY_ACKNOWLEDGEMENTS_CONFIRMED,CHECK_ID_HAS_ACKNOWLEDGED_HONOR_CODE,POPULATION_ID_EVERYONE,
                    "Please acknowledge the honour code",
                    "Please acknowledge the honour code",6,
                    false, // warning
                    false, // continue on fail
                    false, // exemptible
                    null, contextInfo);
            loadInstruction("TESTID.13",ProcessServiceConstants.INSTRUCTION_TYPE_KEY, ProcessServiceConstants.INSTRUCTION_ACTIVE_STATE_KEY, null, null,
                    ProcessServiceConstants.PROCESS_KEY_ACKNOWLEDGEMENTS_CONFIRMED,CHECK_ID_HAS_VERIFIED_EMERGENCY_CONTACT_DATA,POPULATION_ID_EVERYONE,
                    "Please verify your Emergency Contact info",
                    "Please verify your Emergency Contact info",7,
                    false, // warning
                    false, // continue on fail
                    false, // exemptible
                    null, contextInfo);
            loadInstruction("TESTID.14",ProcessServiceConstants.INSTRUCTION_TYPE_KEY, ProcessServiceConstants.INSTRUCTION_ACTIVE_STATE_KEY, null, null,
                    ProcessServiceConstants.PROCESS_KEY_ACKNOWLEDGEMENTS_CONFIRMED,CHECK_ID_HAS_APPLIED_TO_GRADUATE,POPULATION_ID_FINAL_TERM_SENIORS,
                    "Since you have sufficient credits, please apply to graduate ",
                    "Since you have sufficient credits, please apply to graduate ",8,
                    false, // warning
                    false, // continue on fail
                    false, // exemptible
                    null, contextInfo);
        }
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
                              String categoryId, String ownerOrgId, ContextInfo contextInfo)
            throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        ProcessInfo info = new ProcessInfo();
        info.setKey(processId);
        info.setTypeKey(type);
        info.setStateKey(state);
        info.setName(name);
        info.setOwnerOrgId(ownerOrgId);
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
        info.setHoldIssueId(issueId);
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
        info.setCheckId(checkId);
        info.setAppliedPopulationId(appliedPopulationId);
        info.setMessage(new RichTextInfo(messagePlain, messageFormatted));
        info.setPosition(position);
        info.setIsWarning(warning);
        info.setContinueOnFail(continueOnFail);
        info.setIsExemptible(exemptible);
        info.setAppliedAtpTypeKeys(appliedAtpTypes);
        processService.createInstruction(processId, checkId, type, info, contextInfo);
    }
}