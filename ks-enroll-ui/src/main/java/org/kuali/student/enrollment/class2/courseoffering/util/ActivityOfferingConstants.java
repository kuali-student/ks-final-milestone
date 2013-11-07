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
 */
package org.kuali.student.enrollment.class2.courseoffering.util;

/**
 * This class defines constants related to the Activity Offering ui
 *
 * @author Kuali Student Team
 */
public class ActivityOfferingConstants {

    public final static String ACTIVITYOFFERING_ID = "id";
    public final static String ACTIVITYOFFERING_TERM_ID = "termId";
    public final static String ACTIVITYOFFERING_TERM_CODE = "termCode";
    public final static String ACTIVITYOFFERING_COURSE_OFFERING_ID = "courseOfferingId";
    public final static String ACTIVITYOFFERING_COURSE_OFFERING_CODE = "courseOfferingCode";

    public static final String ATP_CODE = "atpCode";
    public static final String ACTIVITY_OFFERING_WRAPPER_ID = "aoInfo.id";

    public static final String MSG_INFO_AO_MOVED = "info.enroll.ao.moved";
    public static final String MSG_INFO_AO_MODIFIED = "info.enroll.ao.modified";

    public final static String MSG_ERROR_COLOCATED_NOTFOUND = "error.enroll.ao.colocated.notfound";
    public static final String MSG_ERROR_INSTRUCTOR_NOTFOUND = "error.enroll.ao.instructor.notfound";
    public static final String MSG_ERROR_INSTRUCTOR_OVERFLOW = "error.enroll.ao.instructor.overflow";
    public static final String MSG_ERROR_INSTRUCTOR_DUPLICATE = "error.enroll.ao.instructor.duplicate";
    public static final String MSG_ERROR_SEATPOOL_DUPLICATE = "error.enroll.ao.seatpool.duplicate";
    public static final String MSG_ERROR_INVALID_START_TIME = "error.enroll.ao.starttime.invalid";
    public static final String MSG_ERROR_TBA_VALIDATION_ERROR = "error.enroll.ao.tba.invalid";
    public static final String MSG_ERROR_ORGANIZATION_ID_REQUIRED = "error.enroll.ao.organization.id.required";
    public static final String MSG_ERROR_AO_PART_COLOCATE = "error.enroll.ao.part.colocate";
    public static final String MSG_ERROR_AO_CURRENT_COLOCATE = "error.enroll.ao.current.colocate";
    public static final String MSG_ERROR_AO_CANCEL_STATE_COLOCATE = "error.enroll.ao.cancel.state.colocate";
    public static final String MSG_ERROR_AO_DUPLICATE_COLOCATE = "error.enroll.ao.duplicate.colocate";
    public static final String MSG_ERROR_AO_INVALID_CO_CODE_COLOCATE = "error.enroll.ao.invalid.co.code.colocate";
    public static final String MSG_ERROR_AO_MULTIPLE_CO_COLOCATE = "error.enroll.ao.multiple.co.colocate";
    public static final String MSG_ERROR_AO_NOT_EXIST_CO_COLOCATE = "error.enroll.ao.not.exist.co.colocate";

    public static final String ACTIVITYOFFERING_ACTION_APPROVE = "approveAO";
    public static final String ACTIVITYOFFERING_ACTION_ADD = "addAO";
    public static final String ACTIVITYOFFERING_ACTION_SET_DRAFT = "setDraftAO";
    public static final String ACTIVITYOFFERING_ACTION_DELETE = "deleteAO";
    public static final String ACTIVITYOFFERING_ACTION_ADD_CLUSTER = "addCluster";
    public static final String ACTIVITYOFFERING_ACTION_MOVE = "moveAO";
    public static final String ACTIVITYOFFERING_ACTION_CANCEL = "cancelAO";
    public static final String ACTIVITYOFFERING_ACTION_SUSPEND = "suspendAO";
    public static final String ACTIVITYOFFERING_ACTION_REINSTATE = "reinstateAO";
    public static final String ACTIVITYOFFERINGS_ACTION_CANCEL = "cancelAOs";
    public static final String ACTIVITYOFFERINGS_ACTION_SUSPEND = "suspendAOs";
    public static final String ACTIVITYOFFERINGS_ACTION_REINSTATE = "reinstateAOs";

    public static class ConfigProperties {
        public static final String NON_STANDARD_TIMESLOT_CREATION_MODE = "kuali.ks.enrollment.timeslots.adhoc_creation_mode";
    }


    private ActivityOfferingConstants() {
        /* Sonar-fix: "Utility classes should not have a public or default constructor" */
    }

}
