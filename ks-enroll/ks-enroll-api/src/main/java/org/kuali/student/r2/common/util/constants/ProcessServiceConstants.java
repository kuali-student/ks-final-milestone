/**
 * Copyright 2011 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.util.constants;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessCategoryInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;

/**
 * This class holds the constants used by the Process service.
 * 
 * @author tom
 */
public class ProcessServiceConstants {

    /**
     * Reference Object URI's
     */
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "process";
    public static final String REF_OBJECT_URI_PROCESS_CATEGORY = NAMESPACE + "/" + ProcessCategoryInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_PROCESS = NAMESPACE + "/" + ProcessInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_CHECK = NAMESPACE + "/" + CheckInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_INSTRUCTION = NAMESPACE + "/" + InstructionInfo.class.getSimpleName();

    ///////////////////////////////////
    // TYPES AND STATES
    ///////////////////////////////////

    /**
     * ProcessCategory Types/States
     */
    public static final String PROCESS_CATEGORY_TYPE_KEY_CATEGORY = "kuali.process.category.type";

    public static final String PROCESS_CATEGORY_LIFECYCLE_KEY = "kuali.process.process.category.lifecycle";
    public static final String PROCESS_CATEGORY_STATE_KEY_ACTIVE = "kuali.process.process.state.active";
    public static final String PROCESS_CATEGORY_STUDENT_ELIGIBILITY = "IS_STUDENT_ELIGIBLE_FOR_REGISTRATION";

    /**
     * Process Types/States
     */
    public static final String PROCESS_TYPE_KEY = "kuali.process.process.type";

    public static final String PROCESS_LIFECYCLE_KEY = "kuali.process.process.lifecycle";
    public static final String PROCESS_ENABLED_STATE_KEY = "kuali.process.process.state.enabled";
    public static final String PROCESS_DISABLED_STATE_KEY = "kuali.process.process.state.disabled";
    public static final String PROCESS_INACTIVE_STATE_KEY = "kuali.process.process.state.inactive";
    public static final String[] PROCESS_LIFECYCLE_KEYS = {PROCESS_ENABLED_STATE_KEY, PROCESS_DISABLED_STATE_KEY, PROCESS_INACTIVE_STATE_KEY};

    /**
     * Instruction Types/States
     */
    public static final String INSTRUCTION_TYPE_KEY = "kuali.process.instruction.type ";

    public static final String INSTRUCTION_LIFECYCLE_KEY = "kuali.process.instruction.lifecycle";
    public static final String INSTRUCTION_ENABLED_STATE_KEY = "kuali.process.instruction.state.enabled";
    public static final String INSTRUCTION_DISABLED_STATE_KEY = "kuali.process.instruction.state.disabled";
    public static final String[] INSTRUCTION_LIFECYCLE_KEYS = {INSTRUCTION_ENABLED_STATE_KEY, INSTRUCTION_DISABLED_STATE_KEY};

    /**
     * Check Types/States
     */
    public static final String HOLD_CHECK_TYPE_KEY = "kuali.process.check.type.hold";
    public static final String START_DATE_CHECK_TYPE_KEY = "kuali.process.check.type.milestone.startdate";
    public static final String DEADLINE_CHECK_TYPE_KEY = "kuali.process.check.type.milestone.deadline";
    public static final String TIME_PERIOD_CHECK_TYPE_KEY = "kuali.process.check.type.milestone.period";
    public static final String PROCESS_CHECK_TYPE_KEY = "kuali.process.check.type.process";
    public static final String DIRECT_RULE_CHECK_TYPE_KEY = "kuali.process.check.type.rule.direct";
    public static final String INDIRECT_RULE_CHECK_TYPE_KEY = "kuali.process.check.type.rule.indirect";
    public static final String EQUAL_VALUE_CHECK_TYPE_KEY = "kuali.process.check.type.value.equals";
    public static final String MAXIMUM_VALUE_CHECK_TYPE_KEY = "kuali.process.check.type.value.max";
    public static final String MINIMUM_VALUE_CHECK_TYPE_KEY = "kuali.process.check.type.value.min";
    public static final String ACKNOWLEDGEMENT_CHECK_TYPE_KEY = "kuali.process.check.type.acknowledgement";
    public static final String CHECK_LIFECYCLE_KEY = "kuali.process.check.lifecycle";

    public static final String PROCESS_CHECK_STATE_ENABLED = "kuali.process.check.state.enabled";
    public static final String PROCESS_CHECK_STATE_DISABLED = "kuali.process.check.state.disabled";
    public static final String PROCESS_CHECK_STATE_INACTIVE = "kuali.process.check.state.inactive";
    public static final String[] CHECK_LIFECYCLE_KEYS = {PROCESS_CHECK_STATE_ENABLED, PROCESS_CHECK_STATE_DISABLED, PROCESS_CHECK_STATE_INACTIVE};

    /**
     * Milestone Types
     */
    public static final String MILESTONE_TYPE_ATP_REGISTRATION_PERIOD = "kuali.atp.milestone.RegistrationPeriod";

    /**
     * Population Types/States
     */
    public static final String POPULATION_TYPE_KEY_AUTOMATIC = "kuali.population.type.automatic";
    public static final String POPULATION_TYPE_KEY_MANUAL = "kuali.population.type.manual";

    ///////////////////////////////////
    // KEYS / IDS / NAMES
    ///////////////////////////////////

    /**
     * Dummy Process Owner Organization
     */
    public static final String PROCESS_OWNING_ORG_GRAD_SCHOOL = "GRAD_SCHOOL_ORG";

    /**
     * known process keys
     */
    public static final String PROCESS_KEY_BASIC_ELIGIBILITY = "kuali.process.registration.basic.eligibility";
    public static final String PROCESS_KEY_ELIGIBILITY_FOR_TERM = "kuali.process.registration.eligibility.for.term";
    public static final String PROCESS_KEY_HOLDS_CLEARED = "kuali.process.registration.holds.cleared";
    public static final String PROCESS_KEY_ACKNOWLEDGEMENTS_CONFIRMED = "kuali.process.registration.acknowledgements.confirmed";
    public static final String PROCESS_KEY_ELIGIBLE_FOR_COURSE = "kuali.process.registration.eligible.for.course";
    public static final String PROCESS_KEY_ELIGIBLE_FOR_COURSES = "kuali.process.registration.eligible.for.courses";
    public static final String PROCESS_KEY_REGISTER_FOR_COURSES = "kuali.process.registration.register.for.courses";
    public static final String PROCESS_KEY_VIEW_GRADES = "kuali.process.acad.record.view.grades";
    public static final String PROCESS_KEY_VIEW_GRADES_FOR_TERM = "kuali.process.acad.record.view.grades.for.term";
    public static final String PROCESS_KEY_VIEW_COURSE_GRADE = "kuali.process.acad.record.view.course.grade";

    /**
     * known process category ids / names
     */
    public static final String PROCESS_CATEGORY_ID_ADMISSIONS = "2";
    public static final String PROCESS_CATEGORY_NAME_ADMISSIONS = "kuali.process.type.admissions";
    public static final String PROCESS_CATEGORY_ID_COURSE_REGISTRATION = "3";
    public static final String PROCESS_CATEGORY_NAME_COURSE_REGISTRATION = "kuali.process.type.registration";
    public static final String PROCESS_CATEGORY_ID_PROGRAM_ENROLLMENT = "4";
    public static final String PROCESS_CATEGORY_NAME_PROGRAM_ENROLLMENT = "kuali.process.type.enrollment";
    public static final String PROCESS_CATEGORY_ID_ACADEMIC_RECORD = "5";
    public static final String PROCESS_CATEGORY_NAME_ACADEMIC_RECORD = "kuali.process.type.acad.record";
    public static final String PROCESS_CATEGORY_ID_GRADUATION = "6";
    public static final String PROCESS_CATEGORY_NAME_GRADUATION = "kuali.process.type.graduation";
    public static final String PROCESS_CATEGORY_ID_STUDENT_ACCOUNTS = "7";
    public static final String PROCESS_CATEGORY_NAME_STUDENT_ACCOUNTS = "kuali.process.type.student.accounts";
    public static final String PROCESS_CATEGORY_ID_LIBRARY = "8";
    public static final String PROCESS_CATEGORY_NAME_LIBRARY = "kuali.process.type.library";
    public static final String PROCESS_CATEGORY_ID_HOUSING = "9";
    public static final String PROCESS_CATEGORY_NAME_HOUSING = "kuali.process.type.housing";

    /**
     * known agenda ids / names
     */
    public static final String AGENDA_IS_ALIVE_ID = "kuali.agenda.is.alive";
    public static final String AGENDA_IS_NOT_SUMMER_TERM_ID = "kuali.agenda.is.not.summer.term";

    /**
     * known issue ids / names
     */
    public static final String ISSUE_HOLD_LIBRARY_BOOK_OVERDUE_ID = "kuali.hold.issue.library.book.overdue";
    public static final String ISSUE_HOLD_UPAID_TUITION_FROM_LAST_TERM_ID = "kuali.hold.issue.financial.unpaid.tuition.prior.term";

    /**
     * known check ids / names
     */
    public static final String CHECK_ID_IS_ALIVE = "2";
    public static final String CHECK_NAME_IS_ALIVE = "kuali.check.is.alive";
    public static final String CHECK_ID_HAS_BEEN_ADMITTED = "3";
    public static final String CHECK_NAME_HAS_BEEN_ADMITTED = "kuali.check.has.been.admitted";
    public static final String CHECK_ID_HAS_CONFIRMED_INTEND_TO_ATTEND = "4";
    public static final String CHECK_NAME_HAS_CONFIRMED_INTEND_TO_ATTEND = "kuali.check.has.confirmed.intend.to.attend";
    public static final String CHECK_ID_HAS_NOT_BEEN_EXPELLED = "5";
    public static final String CHECK_NAME_HAS_NOT_BEEN_EXPELLED = "kuali.check.has.not.been.expelled";
    public static final String CHECK_ID_HAS_OVERDUE_LIBRARY_BOOK = "7";
    public static final String CHECK_NAME_HAS_OVERDUE_LIBRARY_BOOK = "kuali.check.has.overdue.library.book";
    public static final String CHECK_ID_HAS_UNPAID_LIBRARY_FINE = "8";
    public static final String CHECK_NAME_HAS_UNPAID_LIBRARY_FINE = "kuali.check.has.unpaid.library.fine";
    public static final String CHECK_ID_HAS_NOT_PAID_BILL_FROM_PRIOR_TERM = "9";
    public static final String CHECK_NAME_HAS_NOT_PAID_BILL_FROM_PRIOR_TERM = "kuali.check.has.not.paid.bill.from.prior.term";
    public static final String CHECK_ID_HAS_ACKNOWLEDGED_RIAA = "11";
    public static final String CHECK_NAME_HAS_ACKNOWLEDGED_RIAA = "kuali.check.has.acknowledged.riaa";
    public static final String CHECK_ID_HAS_ACKNOWLEDGED_HONOR_CODE = "12";
    public static final String CHECK_NAME_HAS_ACKNOWLEDGED_HONOR_CODE = "kuali.check.has.acknowledged.honor.code";
    public static final String CHECK_ID_HAS_VERIFIED_EMERGENCY_CONTACT_DATA = "13";
    public static final String CHECK_NAME_HAS_VERIFIED_EMERGENCY_CONTACT_DATA = "kuali.check.has.verified.emergency.contact.data";
    public static final String CHECK_ID_HAS_APPLIED_TO_GRADUATE = "14";
    public static final String CHECK_NAME_HAS_APPLIED_TO_GRADUATE = "kuali.check.has.applied.to.graduate";
    public static final String CHECK_ID_STUDENT_HAS_BASIC_ELIGIBILITY = "16";
    public static final String CHECK_NAME_STUDENT_HAS_BASIC_ELIGIBILITY = "kuali.check.student.has.basic.eligibility";
    public static final String CHECK_ID_IS_STUDENT_EXPECTED_IN_TERM = "17";
    public static final String CHECK_NAME_IS_STUDENT_EXPECTED_IN_TERM = "kuali.check.is.student.expected.in.term";
    public static final String CHECK_ID_REGISTRATION_PERIOD_IS_OPEN = "18";
    public static final String CHECK_NAME_REGISTRATION_PERIOD_IS_OPEN = "kuali.check.registration.period.is.open";
    public static final String CHECK_ID_REGISTRATION_PERIOD_IS_NOT_CLOSED = "19";
    public static final String CHECK_NAME_REGISTRATION_PERIOD_IS_NOT_CLOSED = "kuali.check.registration.period.is.not.closed";
    public static final String CHECK_ID_REGISTRATION_HOLDS_CLEARED = "20";
    public static final String CHECK_NAME_REGISTRATION_HOLDS_CLEARED = "kuali.check.registration.holds.cleared";
    public static final String CHECK_ID_ACKNOWLEDGEMENTS_CONFIRMED = "21";
    public static final String CHECK_NAME_ACKNOWLEDGEMENTS_CONFIRMED = "kuali.check.acknowledgements.confirmed";
    public static final String CHECK_ID_MAX_TOTAL_CREDITS_ALLOWED = "22";
    public static final String CHECK_NAME_MAX_TOTAL_CREDITS_ALLOWED = "kuali.check.max.total.credits.allowed";
    public static final String CHECK_ID_INTERNATIONAL_STUDENT_CHECK_IN = "23";
    public static final String CHECK_NAME_INTERNATIONAL_STUDENT_CHECK_IN = "kuali.check.international.student.check.in";
    public static final String CHECK_ID_IS_NOT_SUMMER_TERM = "24";
    public static final String CHECK_NAME_IS_NOT_SUMMER_TERM = "kuali.check.is.not.summer.term";
    public static final String CHECK_ID_MANDATORY_ADVISING_CHECK = "25";
    public static final String CHECK_NAME_MANDATORY_ADVISING_CHECK = "kuali.check.mandatory.advising.check";
    public static final String CHECK_ID_UNRESOLVED_INCOMPLETE_GRADES = "26";
    public static final String CHECK_NAME_UNRESOLVED_INCOMPLETE_GRADES = "kuali.check.unresolved.incomplete.grades";
    public static final String CHECK_ID_ELIGIBILITY_FOR_TERM = "28";
    public static final String CHECK_NAME_ELIGIBILITY_FOR_TERM = "kuali.check.eligibility.for.term";
    public static final String CHECK_ID_HAS_THE_NECESSARY_PREREQ = "29";
    public static final String CHECK_NAME_HAS_THE_NECESSARY_PREREQ = "kuali.check.has.the.necessary.prereq";
    public static final String CHECK_ID_IS_ELIGIBLE_FOR_THE_COURSE_OFFERING = "30";
    public static final String CHECK_NAME_IS_ELIGIBLE_FOR_THE_COURSE_OFFERING = "kuali.check.is.eligible.for.the.course.offering";
    public static final String CHECK_ID_NORTH_STUDENTS_MAX_SOUTH_CREDITS = "31";
    public static final String CHECK_NAME_NORTH_STUDENTS_MAX_SOUTH_CREDITS = "kuali.check.north.students.max.south.credits";
    public static final String CHECK_ID_SOUTH_STUDENTS_MAX_NORTH_CREDITS = "32";
    public static final String CHECK_NAME_SOUTH_STUDENTS_MAX_NORTH_CREDITS = "kuali.check.south.students.max.north.credits";
    public static final String CHECK_ID_STUDENT_HAS_ELIGIBILITY_FOR_EACH_COURSE = "34";
    public static final String CHECK_NAME_STUDENT_HAS_ELIGIBILITY_FOR_EACH_COURSE = "kuali.check.student.has.eligibility.for.each.course";
    public static final String CHECK_ID_DOES_NOT_EXCEED_CREDIT_LIMIT = "35";
    public static final String CHECK_NAME_DOES_NOT_EXCEED_CREDIT_LIMIT = "kuali.check.does.not.exceed.credit.limit";
    public static final String CHECK_ID_DOES_NOT_MEET_CREDIT_MINIMUM = "36";
    public static final String CHECK_NAME_DOES_NOT_MEET_CREDIT_MINIMUM = "kuali.check.does.not.meet.credit.minimum";
    public static final String CHECK_ID_DOES_NOT_HAVE_A_TIME_CONFLICT = "37";
    public static final String CHECK_NAME_DOES_NOT_HAVE_A_TIME_CONFLICT = "kuali.check.does.not.have.a.time.conflict";
    public static final String CHECK_ID_TOO_MANY_COURSES_DURING_INITIAL_REGISTRATION_PERIOD = "38";
    public static final String CHECK_NAME_TOO_MANY_COURSES_DURING_INITIAL_REGISTRATION_PERIOD = "kuali.check.too.many.courses.during.initial.registration.period";
    public static final String CHECK_ID_STUDENT_IS_ELIGIBLE_FOR_THE_COURSES = "40";
    public static final String CHECK_NAME_STUDENT_IS_ELIGIBLE_FOR_THE_COURSES = "kuali.check.student.is.eligible.for.the.courses";
    public static final String CHECK_ID_IS_STUDENTS_REGISTRATION_WINDOW = "41";
    public static final String CHECK_NAME_IS_STUDENTS_REGISTRATION_WINDOW = "kuali.check.is.students.registration.window";
    public static final String CHECK_ID_COURSE_HAS_ROOM_FOR_STUDENT_IN_A_SEATPOOL = "42";
    public static final String CHECK_NAME_COURSE_HAS_ROOM_FOR_STUDENT_IN_A_SEATPOOL = "kuali.check.course.has.room.for.student.in.a.seatpool";
    public static final String CHECK_ID_GRADES_HAVE_BEEN_SUBMITTED_FOR_COURSE = "44";
    public static final String CHECK_NAME_GRADES_HAVE_BEEN_SUBMITTED_FOR_COURSE = "kuali.check.grades.have.been.submitted.for.course";
    public static final String CHECK_ID_HAS_COMPLETED_COURSE_EVALUATION = "45";
    public static final String CHECK_NAME_HAS_COMPLETED_COURSE_EVALUATION = "kuali.check.has.completed.course.evaluation";

    /**
     * known population ids / names
     */
    public static final String POPULATION_ID_EVERYONE = "2";
    public static final String POPULATION_NAME_EVERYONE = "kuali.population.everyone";
    public static final String POPULATION_ID_FINAL_TERM_SENIORS = "3";
    public static final String POPULATION_NAME_FINAL_TERM_SENIORS = "kuali.population.final.term.seniors";
    public static final String POPULATION_ID_NOT_IN_A_DEGREE_GRANTING_PROGRAM = "4";
    public static final String POPULATION_NAME_NOT_IN_A_DEGREE_GRANTING_PROGRAM = "kuali.population.not.in.a.degree.granting.program";
    public static final String POPULATION_ID_INTERNATIONAL_STUDENTS = "6";
    public static final String POPULATION_NAME_INTERNATIONAL_STUDENTS = "kuali.population.international.student";
    public static final String POPULATION_ID_SUMMER_ONLY_STUDENT = "7";
    public static final String POPULATION_NAME_SUMMER_ONLY_STUDENT = "kuali.population.summer.only.student";
    public static final String POPULATION_ID_FRESHMAN = "8";
    public static final String POPULATION_NAME_FRESHMAN = "kuali.population.freshman";
    public static final String POPULATION_ID_SOPHOMORE = "9";
    public static final String POPULATION_NAME_SOPHOMORE = "kuali.population.sophomore";
    public static final String POPULATION_ID_UNDERGRADUATE = "11";
    public static final String POPULATION_NAME_UNDERGRADUATE = "kuali.population.undergraduate";
    public static final String POPULATION_ID_NORTH_CAMPUS_STUDENTS = "12";
    public static final String POPULATION_NAME_NORTH_CAMPUS_STUDENTS = "kuali.population.north.campus.students";
    public static final String POPULATION_ID_SOUTH_CAMPUS_STUDENTS = "13";
    public static final String POPULATION_NAME_SOUTH_CAMPUS_STUDENTS = "kuali.population.south.campus.students";


}
