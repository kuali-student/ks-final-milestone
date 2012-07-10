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
    public static final String PROCESS_CATEGORY_STATE_KEY_INACTIVE = "kuali.process.process.state.inactive";
    public static final String PROCESS_CATEGORY_STUDENT_ELIGIBILITY = "IS_STUDENT_ELIGIBLE_FOR_REGISTRATION";

    /**
     * Process Types/States
     */
    public static final String PROCESS_TYPE_KEY = "kuali.process.process.type";

    public static final String PROCESS_LIFECYCLE_KEY = "kuali.process.process.lifecycle";
    public static final String PROCESS_ACTIVE_STATE_KEY = "kuali.process.process.state.active";
    public static final String PROCESS_DISABLED_STATE_KEY = "kuali.process.process.state.disabled";
    public static final String PROCESS_INACTIVE_STATE_KEY = "kuali.process.process.state.inactive";
    public static final String[] PROCESS_LIFECYCLE_KEYS = {PROCESS_ACTIVE_STATE_KEY, PROCESS_DISABLED_STATE_KEY, PROCESS_INACTIVE_STATE_KEY};

    /**
     * Instruction Types/States
     */
    public static final String INSTRUCTION_TYPE_KEY = "kuali.process.instruction.type ";

    public static final String INSTRUCTION_LIFECYCLE_KEY = "kuali.process.instruction.lifecycle";
    public static final String INSTRUCTION_ACTIVE_STATE_KEY = "kuali.process.instruction.state.active";
    public static final String INSTRUCTION_INACTIVE_STATE_KEY = "kuali.process.instruction.state.inactive";
    public static final String INSTRUCTION_DISABLED_STATE_KEY = "kuali.process.instruction.state.disabled";
    public static final String[] INSTRUCTION_LIFECYCLE_KEYS = {INSTRUCTION_ACTIVE_STATE_KEY, INSTRUCTION_DISABLED_STATE_KEY, INSTRUCTION_INACTIVE_STATE_KEY};

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

    public static final String PROCESS_CHECK_STATE_ACTIVE = "kuali.process.check.state.active";
    public static final String PROCESS_CHECK_STATE_DISABLED = "kuali.process.check.state.disabled";
    public static final String PROCESS_CHECK_STATE_INACTIVE = "kuali.process.check.state.inactive";
    public static final String[] CHECK_LIFECYCLE_KEYS = {PROCESS_CHECK_STATE_ACTIVE, PROCESS_CHECK_STATE_DISABLED, PROCESS_CHECK_STATE_INACTIVE};

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
     * known agenda ids / names
     */
    public static final String AGENDA_IS_ALIVE_ID = "kuali.agenda.is.alive";
    public static final String AGENDA_IS_NOT_SUMMER_TERM_ID = "kuali.agenda.is.not.summer.term";

    /**
     * known issue ids / names
     */
    public static final String ISSUE_HOLD_LIBRARY_BOOK_OVERDUE_ID = "kuali.hold.issue.library.book.overdue";
    public static final String ISSUE_HOLD_UPAID_TUITION_FROM_LAST_TERM_ID = "kuali.hold.issue.financial.unpaid.tuition.prior.term";

}
