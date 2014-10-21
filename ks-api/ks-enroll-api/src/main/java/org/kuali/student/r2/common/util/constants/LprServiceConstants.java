/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.r2.common.util.constants;

import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;

import javax.xml.namespace.QName;

/**
 * Constants used by for LprService
 * 
 * @author nwright
 */
@SuppressWarnings("unused")
public class LprServiceConstants {

    /**
     * Reference Object URI's
     */   
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "lpr";
    public static final String SERVICE_NAME_LOCAL_PART = LprService.class.getSimpleName();
    public static final String REF_OBJECT_URI_LUI_PERSON_RELATION = NAMESPACE + "/"
            + LprInfo.class.getSimpleName();
    // Called as
    // CourseRegistrationService service =
    //     (CourseRegistrationService) GlobalResourceLoader.getService(CourseRegistrationServiceConstants.Q_NAME);
    public static final QName Q_NAME = new QName(NAMESPACE, SERVICE_NAME_LOCAL_PART);
    /**
     * Types and known groups of types
     */
    public static final String LPR_KEY_PREFIX = "kuali.lpr";
    public static final String INSTRUCTOR_MAIN_TYPE_KEY = "kuali.lpr.type.instructor.main";
    public static final String COURSE_OFFERING_INSTRUCTOR_MAIN_TYPE_KEY = "kuali.lpr.type.courseoffering.instructor.main";
    public static final String INSTRUCTOR_ASSISTANT_TYPE_KEY = "kuali.lpr.type.instructor.assistant";
    public static final String INSTRUCTOR_SUPPORT_TYPE_KEY = "kuali.lpr.type.instructor.support";

    public static final String REGISTRANT_CO_LPR_TYPE_KEY = "kuali.lpr.type.registrant.course.offering";
    public static final String REGISTRANT_AO_LPR_TYPE_KEY = "kuali.lpr.type.registrant.activity.offering";
    public static final String REGISTRANT_RG_LPR_TYPE_KEY = "kuali.lpr.type.registrant.registration.group";
    public static final String WAITLIST_CO_LPR_TYPE_KEY = "kuali.lpr.type.waitlist.course.offering";
//    public static final String WAITLIST_FO_TYPE_KEY = "kuali.lpr.type.waitlist.format.offering";
    public static final String WAITLIST_AO_LPR_TYPE_KEY = "kuali.lpr.type.waitlist.activity.offering";
    public static final String WAITLIST_RG_LPR_TYPE_KEY = "kuali.lpr.type.waitlist.registration.group";
    public static final String ENROLLEE_TYPE_KEY = "kuali.lpr.type.enrollee";
    public static final String ADVISOR_TYPE_KEY = "kuali.lpr.type.advisor";

    // lpr transaction type
    public static final String LPRTRANS_REGISTRATION_TYPE_KEY = "kuali.lpr.trans.type.registration"; // KSENROLL-13667
    public static final String LPRTRANS_REG_CART_TYPE_KEY = "kuali.lpr.trans.type.registration.cart"; // KSENROLL-11784

    // lpr transaction item request options
    public static final String LPRTRANS_ITEM__WAITLIST_OPTION_KEY = "kuali.lpr.option.waitlist";
    public static final String LPRTRANS_ITEM_HOLD_UNTIL_LIST_OPTION_KEY = "kuali.lpr.option.hold.until.list";

    // lpr transaction item types (operations)
    public static final String LPRTRANS_ITEM_CREATE_TYPE_KEY = "kuali.lpr.trans.item.type.create";
    public static final String LPRTRANS_ITEM_UPDATE_TYPE_KEY = "kuali.lpr.trans.item.type.update";
    public static final String LPRTRANS_ITEM_DELETE_TYPE_KEY = "kuali.lpr.trans.item.type.delete";
    public static final String LPRTRANS_ITEM_NO_CHANGE_TYPE_KEY = "kuali.lpr.trans.item.type.no.change";
    public static final String[] LPRTRANS_ITEM_TYPE_KEYS = {LPRTRANS_ITEM_CREATE_TYPE_KEY, 
        LPRTRANS_ITEM_UPDATE_TYPE_KEY, 
        LPRTRANS_ITEM_DELETE_TYPE_KEY,
        LPRTRANS_ITEM_NO_CHANGE_TYPE_KEY};

    // transaction states
    public static final String LPRTRANS_NEW_STATE_KEY = "kuali.lpr.trans.state.new";
    public static final String LPRTRANS_PROCESSING_STATE_KEY = "kuali.lpr.trans.state.processing";
    public static final String LPRTRANS_SUCCEEDED_STATE_KEY = "kuali.lpr.trans.state.succeeded";
    public static final String LPRTRANS_FAILED_STATE_KEY = "kuali.lpr.trans.state.failed";
    public static final String LPRTRANS_DISCARDED_STATE_KEY = "kuali.lpr.trans.state.discarded";

    // transaction item waitlist messages
    public static final String LPRTRANS_ITEM_WAITLIST_STUDENT_REMOVED_MESSAGE_KEY = "kuali.lpr.trans.message.waitlist.student.removed";
    public static final String LPRTRANS_ITEM_WAITLIST_OPTIONS_UPDATED_MESSAGE_KEY = "kuali.lpr.trans.message.waitlist.options.updated";
    public static final String LPRTRANS_ITEM_WAITLIST_WAITLISTED_MESSAGE_KEY = "kuali.lpr.trans.message.waitlist.waitlisted";
    public static final String LPRTRANS_ITEM_WAITLIST_FULL_MESSAGE_KEY = "kuali.lpr.trans.message.waitlist.full";
    public static final String LPRTRANS_ITEM_WAITLIST_NOT_OFFERED_MESSAGE_KEY = "kuali.lpr.trans.message.waitlist.not.offered";
    public static final String LPRTRANS_ITEM_WAITLIST_AVAILABLE_MESSAGE_KEY = "kuali.lpr.trans.message.waitlist.available";
    public static final String LPRTRANS_ITEM_ADD_FROM_WAITLIST_MESSAGE_KEY = "kuali.lpr.trans.message.waitlist.add.from";
    public static final String LPRTRANS_ITEM_EXCEPTION_MESSAGE_KEY = "kuali.lpr.trans.item.message.exception";


    // transaction item course action messages
    public static final String LPRTRANS_ITEM_COURSE_UPDATED_MESSAGE_KEY = "kuali.lpr.trans.message.course.updated";
    public static final String LPRTRANS_ITEM_COURSE_DROPPED_MESSAGE_KEY = "kuali.lpr.trans.message.course.dropped";

    // other messages
    public static final String LPRTRANS_ITEM_SEAT_UNAVAILABLE_MESSAGE_KEY = "kuali.lpr.trans.message.seat.unavailable";
    public static final String LPRTRANS_ITEM_PERSON_REGISTERED_MESSAGE_KEY = "kuali.lpr.trans.message.person.registered";
    public static final String LPRTRANS_ITEM_CREDIT_LOAD_EXCEEDED_MESSAGE_KEY = "kuali.lpr.trans.message.credit.load.exceeded";
    public static final String LPRTRANS_ITEM_CREDIT_LOAD_REACHED_MESSAGE_KEY = "kuali.lpr.trans.message.credit.load.reached";
    public static final String LPRTRANS_ITEM_TIME_CONFLICT_MESSAGE_KEY = "kuali.lpr.trans.message.time.conflict";
    public static final String LPRTRANS_ITEM_REG_GROUP_NOT_OFFERED_MESSAGE_KEY = "kuali.lpr.trans.message.reggroup.notoffered";
    public static final String LPRTRANS_ITEM_FAILED_ANTI_REQUISITES_MESSAGE_KEY = "kuali.lpr.trans.message.failed.antirequisites";
    public static final String LPRTRANS_ITEM_COURSE_ALREADY_TAKEN_MESSAGE_KEY = "kuali.lpr.trans.message.course.already.taken";
    public static final String LPRTRANS_ITEM_COURSE_REPEATABILITY_MESSAGE_KEY = "kuali.lpr.trans.message.repeatability.warning";
    public static final String LPRTRANS_ITEM_DROP_PERIOD_CLOSED_MESSAGE_KEY = "kuali.lpr.trans.message.drop.period.closed";
    public static final String LPRTRANS_ITEM_EDIT_PERIOD_CLOSED_MESSAGE_KEY = "kuali.lpr.trans.message.edit.period.closed";
    public static final String LPRTRANS_ITEM_ALREADY_REGISTERED_MESSAGE_KEY = "kuali.lpr.trans.message.course.already.registered";
    public static final String LPRTRANS_ITEM_COURSE_NOT_OPEN_MESSAGE_KEY = "kuali.lpr.trans.message.course.not.open";
    public static final String LPRTRANS_ITEM_TRANSACTIONS_LIMIT_MESSAGE_KEY = "kuali.lpr.trans.message.failed.holds.transactions.limit";
    public static final String LPRTRANS_ITEM_MANDATORY_ADVISING_MESSAGE_KEY = "kuali.lpr.trans.message.failed.holds.mandatory.advising";

    // transaction item states
    public static final String LPRTRANS_ITEM_NEW_STATE_KEY = "kuali.lpr.trans.item.state.new";
    public static final String LPRTRANS_ITEM_PROCESSING_STATE_KEY = "kuali.lpr.trans.item.state.processing";
    public static final String LPRTRANS_ITEM_SUCCEEDED_STATE_KEY = "kuali.lpr.trans.item.state.succeeded";
    public static final String LPRTRANS_ITEM_FAILED_STATE_KEY = "kuali.lpr.trans.item.state.failed";

    public static final String[] COURSE_INSTRUCTOR_TYPE_KEYS = {INSTRUCTOR_MAIN_TYPE_KEY,
        INSTRUCTOR_ASSISTANT_TYPE_KEY, INSTRUCTOR_SUPPORT_TYPE_KEY};
    public static final String[] COURSE_STUDENT_TYPE_KEYS = {REGISTRANT_AO_LPR_TYPE_KEY,
            REGISTRANT_CO_LPR_TYPE_KEY, REGISTRANT_RG_LPR_TYPE_KEY};

    
      // lpr transaction item types (operations)
    public static final String REQ_ITEM_ADD_TYPE_KEY = "kuali.course.registration.request.item.type.add";
    public static final String REQ_ITEM_DROP_TYPE_KEY = "kuali.course.registration.request.item.type.drop";
    public static final String REQ_ITEM_SWAP_TYPE_KEY = "kuali.course.registration.request.item.type.swap";
    public static final String REQ_ITEM_UPDATE_TYPE_KEY = "kuali.course.registration.request.item.type.update";

    public static final String REQ_ITEM_DROP_WAITLIST_TYPE_KEY = "kuali.course.registration.request.item.type.drop.waitlist";
    public static final String REQ_ITEM_UPDATE_WAITLIST_TYPE_KEY = "kuali.course.registration.request.item.type.update.waitlist";

    public static final String REQ_ITEM_ADD_TO_WAITLIST_TYPE_KEY = "kuali.course.registration.request.item.type.add.to.waitlist";
    public static final String REQ_ITEM_ADD_TO_HOLD_UNTIL_LIST_TYPE_KEY = "kuali.course.registration.request.item.type.add.to.hold.until.list";
    public static final String REQ_ITEM_ADD_FROM_WAITLIST_TYPE_KEY = "kuali.course.registration.request.item.type.add.from.waitlist";

    /**
     * LprRoster types
     */
    // reverted public static final String LPRROSTER_COURSE_FINAL_GRADEROSTER_TYPE_KEY = "kuali.roster.type.course.assessment.final ";
    // roster type keys
    public static final String LPRROSTER_COURSE_FINAL_GRADE_TYPE_KEY = "kuali.lpr.roster.type.course.grade.final";
    public static final String LPRROSTER_COURSE_MIDTERM_GRADE_TYPE_KEY = "kuali.lpr.roster.type.course.assessment.interim.midterm";
    public static final String LPRROSTER_COURSE_WAITLIST_TYPE_KEY = "kuali.lpr.roster.type.course.waitlist";
    public static final String LPRROSTER_COURSE_HOLD_UNTIL_LIST_TYPE_KEY = "kuali.lpr.roster.type.course.hold.until.list";    

    /**
     * LprRoster states
     */
      // grading roster process and state key
    public static final String LPRROSTER_GRADING_POCESS_KEY = "kuali.lpr.roster.process.course.grading";
    public static final String LPRROSTER_READY_STATE_KEY = "kuali.lpr.roster.state.ready";    
    public static final String LPRROSTER_SAVED_STATE_KEY = "kuali.lpr.roster.state.saved";
    public static final String LPRROSTER_SUBMITTED_STATE_KEY = "kuali.lpr.roster.state.submitted";
    public static final String[] LPRROSTER_GRADING_POCESS_KEYS = {LPRROSTER_READY_STATE_KEY, LPRROSTER_SAVED_STATE_KEY,
        LPRROSTER_SUBMITTED_STATE_KEY};

    // other roster process state keys
    public static final String LPRROSTER_LIST_POCESS_KEY = "kuali.lpr.roster.process.lists";   
    public static final String LPRROSTER_CREATED_STATE_KEY = "kuali.lpr.roster.state.created";    
    public static final String[] LPRROSTER_LIST_POCESS_KEYS = {LPRROSTER_CREATED_STATE_KEY};

    // TODO: switch these constants to those above since they now point to the same thing
    public static final String LPRROSTER_COURSE_FINAL_GRADEROSTER_NEW_STATE_KEY = "kuali.lpr.roster.state.created"; // Is this needed/to-be-used?
    public static final String LPRROSTER_COURSE_FINAL_GRADEROSTER_READY_STATE_KEY = "kuali.lpr.roster.state.ready";
    public static final String LPRROSTER_COURSE_FINAL_GRADEROSTER_SAVED_STATE_KEY = "kuali.lpr.roster.state.saved";
    public static final String LPRROSTER_COURSE_FINAL_GRADEROSTER_SUBMITTED_STATE_KEY = "kuali.lpr.roster.state.submitted";

    /**
     * Student states to courses
     */
    // the active state is also used
    public static final String PLANNED_STATE_KEY = "kuali.lpr.state.planned";
//    public static final String REGISTERED_STATE_KEY = "kuali.lpr.state.registered"; // No longer used
    public static final String DROPPED_STATE_KEY = "kuali.lpr.state.dropped.early";
    public static final String DROPPED_LATE_STATE_KEY = "kuali.lpr.state.dropped.late";
    // To handle transition from "waitlist" to "registered"
    public static final String RECEIVED_LPR_STATE_KEY = "kuali.lpr.state.received";
    // To handle updates (which causes the expiration date to be set).
    public static final String SUPERCEDED_LPR_STATE_KEY = "kuali.lpr.state.superceded";

    /**
     * Instructor states
     */
    public static final String TENTATIVE_STATE_KEY = "kuali.lpr.state.tentative";
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
    public static final String WAITLISTED_STATE_KEY = "kuali.lpr.state.waitlisted";

    // advisor states
    public static final String ACTIVE_STATE_KEY = "kuali.lpr.state.active";
    public static final String INACTIVE_STATE_KEY = "kuali.lpr.state.inactive";

    /**
     * States used for isntructors of courses
     */
    public static final String INSTRUCTOR_COURSE_ASSIGNMENT_PROCESS_KEY = "kuali.lpr.process.instructor.course.assignment";
    public static final String[] INSTRUCTOR_COURSE_ASSIGNMENT_STATE_KEYS = {TENTATIVE_STATE_KEY, ASSIGNED_STATE_KEY,
        UNASSIGNED_STATE_KEY};

    /**
     * States used for students in courses
     */
    public static final String STUDENT_COURSE_REGISTRATION_PROCESS_KEY = "kuali.lpr.process.student.course.registration";
    public static final String[] STUDENT_COURSE_REGISTRATION_STATE_KEYS = {PLANNED_STATE_KEY, ACTIVE_STATE_KEY,
        DROPPED_STATE_KEY, DROPPED_LATE_STATE_KEY};

    /**
     * States used for instructors of PROGRAMS
     */
    public static final String PROGRAM_ADVISOR_ASSIGNMENT_PROCESS_KEY = "kuali.lpr.process.program.advisor.assignment";
    public static final String[] PROGRAM_ADVISOR_ASSIGNMENT_STATE_KEYS = {ACTIVE_STATE_KEY, INACTIVE_STATE_KEY};

    /**
     * Types used for students in PROGRAMS
     */
    public static final String STUDENT_PROGRAM_ENROLLMENT_PROCESS_KEY = "kuali.lpr.process.student.program.enrollment";
    public static final String[] STUDENT_PROGRAM_ENROLLMENT_STATE_KEYS = {PLANNED_STATE_KEY, INQUIRED_STATE_KEY,
        APPLIED_STATE_KEY, WAITLISTED_STATE_KEY, DENIED_STATE_KEY, CONFIRMED_STATE_KEY, CANCELED_STATE_KEY,
        DEFERED_STATE_KEY, ENROLLED_STATE_KEY, TEMPORARY_ABSENCE_STATE_KEY, WITHDRAWN_STATE_KEY,
        PROBATION_STATE_KEY};

    /**
     * All process keys
     */
    public static final String[] LPR_PROCESS_KEYS = {STUDENT_COURSE_REGISTRATION_PROCESS_KEY,
        INSTRUCTOR_COURSE_ASSIGNMENT_PROCESS_KEY, STUDENT_PROGRAM_ENROLLMENT_PROCESS_KEY,
        PROGRAM_ADVISOR_ASSIGNMENT_PROCESS_KEY};
}
