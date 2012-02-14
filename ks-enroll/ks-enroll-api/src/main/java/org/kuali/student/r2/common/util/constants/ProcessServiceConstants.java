/**
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
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
    /**
     * ProcessCategory types
     */
    public static final String PROCESS_CATEGORY_TYPE_KEY_CATEGORY = "kuali.process.category.type";
    /**
     * Process types
     */
    public static final String PROCESS_TYPE_KEY = "kuali.process.process.type";
    /**
     * Check types
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
    /**
     * Instruction types
     */
    public static final String INSTRUCTION_TYPE_KEY = "kuali.process.instruction.type ";
    /**
     * States for Process Categories
     */
    public static final String PROCESS_CATEGORY_LIFECYCLE_KEY = "kuali.process.process.category.lifecycle";
    /***
     * Process Category Types
     */
    public static final String PROCESS_CATEGORY_STUDENT_ELIGIBILITY = "IS_STUDENT_ELIGIBLE_FOR_REGISTRATION";
    /**
     * Dummy Process Owner Organization
     */
    public static final String PROCESS_OWNING_ORG_GRAD_SCHOOL = "GRAD_SCHOOL_ORG";
    /**
     * States for Processes
     */
    public static final String PROCESS_LIFECYCLE_KEY = "kuali.process.process.lifecycle";
    public static final String PROCESS_ENABLED_STATE_KEY = "kuali.process.process.state.enabled";
    public static final String PROCESS_DISABLED_STATE_KEY = "kuali.process.process.state.disabled";
    public static final String PROCESS_INACTIVE_STATE_KEY = "kuali.process.process.state.inactive";
    public static final String[] PROCESS_LIFECYCLE_KEYS = {PROCESS_ENABLED_STATE_KEY, PROCESS_DISABLED_STATE_KEY, PROCESS_INACTIVE_STATE_KEY};
    /**
     * States for Checks
     */
    public static final String CHECK_LIFECYCLE_KEY = "kuali.process.check.lifecycle";
    public static final String PROCESS_CHECK_STATE_ENABLED = "kuali.process.check.state.enabled";
    public static final String PROCESS_CHECK_STATE_DISABLED = "kuali.process.check.state.disabled";
    public static final String PROCESS_CHECK_STATE_INACTIVE = "kuali.process.check.state.inactive";
    public static final String[] CHECK_LIFECYCLE_KEYS = {PROCESS_CHECK_STATE_ENABLED, PROCESS_CHECK_STATE_DISABLED, PROCESS_CHECK_STATE_INACTIVE};
    /**
     * States for Instructions
     */
    public static final String INSTRUCTION_LIFECYCLE_KEY = "kuali.process.instruction.lifecycle";
    public static final String INSTRUCTION_ENABLED_STATE_KEY = "kuali.process.instruction.state.enabled";
    public static final String INSTRUCTION_DISABLED_STATE_KEY = "kuali.process.instruction.state.disabled";
    public static final String[] INSTRUCTION_LIFECYCLE_KEYS = {INSTRUCTION_ENABLED_STATE_KEY, INSTRUCTION_DISABLED_STATE_KEY};
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
     * known check keys
     */
    public static final String CHECK_KEY_IS_ALIVE = "kuali.check.is.alive";
    public static final String CHECK_KEY_HAS_BEEN_ADMITTED = "kuali.check.has.been.admitted";
    public static final String CHECK_KEY_HAS_CONFIRMED_INTEND_TO_ATTEND = "kuali.check.has.confirmed.intend.to.attend";
    public static final String CHECK_KEY_HAS_NOT_BEEN_EXPELLED = "kuali.check.has.not.been.expelled";
    public static final String CHECK_KEY_HAS_OVERDUE_LIBRARY_BOOK = "kuali.check.has.overdue.library.book";
    public static final String CHECK_KEY_HAS_UNPAID_LIBRARY_FINE = "kuali.check.has.unpaid.library.fine";
    public static final String CHECK_KEY_HAS_NOT_PAID_BILL_FROM_PRIOR_TERM = "kuali.check.has.not.paid.bill.from.prior.term";
    public static final String CHECK_KEY_HAS_ACKNOWLEDGED_RIAA = "kuali.check.has.acknowledged.riaa";
    public static final String CHECK_KEY_HAS_ACKNOWLEDGED_HONOR_CODE = "kuali.check.has.acknowledged.honor.code";
    public static final String CHECK_KEY_HAS_VERIFIED_EMERGENCY_CONTACT_DATA = "kuali.check.has.verified.emergency.contact.data";
    public static final String CHECK_KEY_HAS_APPLIED_TO_GRADUATE = "kuali.check.has.applied.to.graduate";
    public static final String CHECK_KEY_STUDENT_HAS_BASIC_ELIGIBILITY = "kuali.check.student.has.basic.eligibility";
    public static final String CHECK_KEY_IS_STUDENT_EXPECTED_IN_TERM = "kuali.check.is.student.expected.in.term";
    public static final String CHECK_KEY_REGISTRATION_PERIOD_IS_OPEN = "kuali.check.registration.period.is.open";
    public static final String CHECK_KEY_REGISTRATION_PERIOD_IS_NOT_CLOSED = "kuali.check.registration.period.is.not.closed";
    public static final String CHECK_KEY_REGISTRATION_HOLDS_CLEARED = "kuali.check.registration.holds.cleared";
    public static final String CHECK_KEY_ACKNOWLEDGEMENTS_CONFIRMED = "kuali.check.acknowledgements.confirmed";
    public static final String CHECK_KEY_MAX_TOTAL_CREDITS_ALLOWED = "kuali.check.max.total.credits.allowed";
    public static final String CHECK_KEY_INTERNATIONAL_STUDENT_CHECK_IN = "kuali.check.international.student.check.in";
    public static final String CHECK_KEY_IS_NOT_SUMMER_TERM = "kuali.check.is.not.summer.term";
    public static final String CHECK_KEY_MANDATORY_ADVISING_CHECK = "kuali.check.mandatory.advising.check";
    public static final String CHECK_KEY_UNRESOLVED_INCOMPLETE_GRADES = "kuali.check.unresolved.incomplete.grades";
    public static final String CHECK_KEY_ELIGIBILITY_FOR_TERM = "kuali.check.eligibility.for.term";
    public static final String CHECK_KEY_HAS_THE_NECESSARY_PREREQ = "kuali.check.has.the.necessary.prereq";
    public static final String CHECK_KEY_IS_ELIGIBLE_FOR_THE_COURSE_OFFERING = "kuali.check.is.eligible.for.the.course.offering";
    public static final String CHECK_KEY_NORTH_STUDENTS_MAX_SOUTH_CREDITS = "kuali.check.north.students.max.south.credits";
    public static final String CHECK_KEY_SOUTH_STUDENTS_MAX_NORTH_CREDITS = "kuali.check.south.students.max.north.credits";
    public static final String CHECK_KEY_STUDENT_HAS_ELIGIBILITY_FOR_EACH_COURSE = "kuali.check.student.has.eligibility.for.each.course";
    public static final String CHECK_KEY_DOES_NOT_EXCEED_CREDIT_LIMIT = "kuali.check.does.not.exceed.credit.limit";
    public static final String CHECK_KEY_DOES_NOT_MEET_CREDIT_MINIMUM = "kuali.check.does.not.meet.credit.minimum";
    public static final String CHECK_KEY_DOES_NOT_HAVE_A_TIME_CONFLICT = "kuali.check.does.not.have.a.time.conflict";
    public static final String CHECK_KEY_TOO_MANY_COURSES_DURING_INITIAL_REGISTRATION_PERIOD = "kuali.check.too.many.courses.during.initial.registration.period";
    public static final String CHECK_KEY_STUDENT_IS_ELIGIBLE_FOR_THE_COURSES = "kuali.check.student.is.eligible.for.the.courses";
    public static final String CHECK_KEY_IS_STUDENTS_REGISTRATION_WINDOW = "kuali.check.is.students.registration.window";
    public static final String CHECK_KEY_COURSE_HAS_ROOM_FOR_STUDENT_IN_A_SEATPOOL = "kuali.check.course.has.room.for.student.in.a.seatpool";
    public static final String CHECK_KEY_GRADES_HAVE_BEEN_SUBMITTED_FOR_COURSE = "kuali.check.grades.have.been.submitted.for.course";
    public static final String CHECK_KEY_HAS_COMPLETED_COURSE_EVALUATION = "kuali.check.has.completed.course.evaluation";
}
