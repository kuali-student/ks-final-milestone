/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.common.util.constants;

import org.kuali.student.enrollment.classI.lpr.dto.LuiPersonRelationInfo;

/**
 * Constants used by for LuiPersonRelationService
 *
 * @author nwright
 */
public class LuiPersonRelationServiceConstants {

    /**
     * Reference Object URI's
     */
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "luiPersonRelation";
    public static final String REF_OBJECT_URI_LUI_PERSON_RELATION = NAMESPACE + "/" + LuiPersonRelationInfo.class.getSimpleName();
    /**
     * Types and knowns groups of types
     */
    public static final String INSTRUCTOR_MAIN_TYPE_KEY = "kuali.lpr.type.instructor.main";
    public static final String INSTRUCTOR_ASSISTANT_TYPE_KEY = "kuali.lpr.type.instructor.assistant";
    public static final String INSTRUCTOR_SUPPORT_TYPE_KEY = "kuali.lpr.type.instructor.support";
    public static final String REGISTRANT_TYPE_KEY = "kuali.lpr.type.student";
    public static final String ENROLLEE_TYPE_KEY = "kuali.lpr.type.enrollee";
    public static final String ADVISOR_TYPE_KEY = "kuali.lpr.type.advisor";
    public static final String[] COURSE_INSTRUCTOR_TYPE_KEYS = {INSTRUCTOR_MAIN_TYPE_KEY, INSTRUCTOR_ASSISTANT_TYPE_KEY, INSTRUCTOR_SUPPORT_TYPE_KEY};
    public static final String[] COURSE_STUDENT_TYPE_KEYS = {REGISTRANT_TYPE_KEY};
    /**
     * Student states to courses
     */
    public static final String PLANNED_STATE_KEY = "kuali.lpr.state.planned";
    public static final String REGISTERED_STATE_KEY = "kuali.lpr.state.registered ";
    public static final String WAITLISTED_STATE_KEY = "kuali.lpr.state.waitlisted";
    public static final String DROPPED_STATE_KEY = "kuali.lpr.state.dropped.early";
    public static final String DROPPED_LATE_STATE_KEY = "kuali.lpr.state.dropped.late";
    /**
     * Instructor states
     */
    public static final String TENATIVE_STATE_KEY = "kuali.lpr.state.tentative";
    public static final String ASSIGNED_STATE_KEY = "kuali.lpr.state.assigned";
    public static final String UNASSIGNED_STATE_KEY = "kuali.lpr.state.unassigned";
    /**
     * Program states
     */
    public static final String INQUIRED_STATE_KEY = "kuali.lpr.state.inquired";
    public static final String APPLIED_STATE_KEY = "kuali.lpr.state.applied";
    public static final String ADMITTED_STATE_KEY = "kuali.lpr.state.admitted ";
    public static final String DENIED_STATE_KEY = "kuali.lpr.state.denied";
    public static final String CONFIRMED_STATE_KEY = "kuali.lpr.state.confirmed";
    public static final String CANCELED_STATE_KEY = "kuali.lpr.state.canceled";
    public static final String DEFERED_STATE_KEY = "kuali.lpr.state.defered";
    public static final String ENROLLED_STATE_KEY = "kuali.lpr.state.enrolled";
    public static final String TEMPORARY_ABSENCE_STATE_KEY = "kuali.lpr.state.temp.absence";
    public static final String WITHDRAWN_STATE_KEY = "kuali.lpr.state.withdrawn";
    public static final String PROBATION_STATE_KEY = "kuali.lpr.state.probation";
    // advisor states
    public static final String ACTIVE_STATE_KEY = "kuali.lpr.state.active";
    public static final String INACTIVE_STATE_KEY = "kuali.lpr.state.inactive";
    /**
     * States used for isntructors of courses
     */
    public static final String INSTRUCTOR_COURSE_ASSIGNMENT_PROCESS_KEY = "kuali.lpr.process.instructor.course.assignment";
    public static final String[] INSTRUCTOR_COURSE_ASSIGNMENT_STATE_KEYS = {TENATIVE_STATE_KEY, ASSIGNED_STATE_KEY, UNASSIGNED_STATE_KEY};
    /**
     * States  used for students in courses
     */
    public static final String STUDENT_COURSE_REGISTRATION_PROCESS_KEY = "kuali.lpr.process.student.course.registration";
    public static final String[] STUDENT_COURSE_REGISTRATION_STATE_KEYS = {PLANNED_STATE_KEY, REGISTERED_STATE_KEY, WAITLISTED_STATE_KEY, DROPPED_STATE_KEY, DROPPED_LATE_STATE_KEY};
    /**
     * States used for instructors of PROGRAMS
     */
    public static final String PROGRAM_ADVISOR_ASSIGNMENT_PROCESS_KEY = "kuali.lpr.process.program.advisor.assignment";
    public static final String[] PROGRAM_ADVISOR_ASSIGNMENT_STATE_KEYS = {ACTIVE_STATE_KEY, INACTIVE_STATE_KEY};
    /**
     * Types used for students in PROGRAMS
     */
    public static final String STUDENT_PROGRAM_ENROLLMENT_PROCESS_KEY = "kuali.lpr.process.student.program.enrollment";
    public static final String[] STUDENT_PROGRAM_ENROLLMENT_STATE_KEYS = {PLANNED_STATE_KEY, INQUIRED_STATE_KEY, APPLIED_STATE_KEY, WAITLISTED_STATE_KEY, DENIED_STATE_KEY, CONFIRMED_STATE_KEY, CANCELED_STATE_KEY, DEFERED_STATE_KEY, ENROLLED_STATE_KEY, TEMPORARY_ABSENCE_STATE_KEY, WITHDRAWN_STATE_KEY, PROBATION_STATE_KEY};
    /**
     * All process keys
     */
    public static final String[] LPR_PROCESS_KEYS = {STUDENT_COURSE_REGISTRATION_PROCESS_KEY, INSTRUCTOR_COURSE_ASSIGNMENT_PROCESS_KEY, STUDENT_PROGRAM_ENROLLMENT_PROCESS_KEY, PROGRAM_ADVISOR_ASSIGNMENT_PROCESS_KEY};
}
