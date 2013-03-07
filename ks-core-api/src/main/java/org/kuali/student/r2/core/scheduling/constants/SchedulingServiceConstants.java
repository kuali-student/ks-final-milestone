/*
 * Copyright 2012 The Kuali Foundation Licensed under the
 *  Educational Community License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may
 *  obtain a copy of the License at
 *
 *   http://www.osedu.org/licenses/ECL-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS"
 *  BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.kuali.student.r2.core.scheduling.constants;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


/**
 * This class holds the constants used by the Scheduling service
 *
 * @Version 1.0
 * @Author Sri komandur@uw.edu
 * @Author Mezba mezba.mahtab@utoronto.ca
 */
public class SchedulingServiceConstants {

    /**
     * Reference Object URI's
     */
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "scheduling";
    public static final String SERVICE_NAME_LOCAL_PART = "SchedulingService";
    public static final String REF_OBJECT_URI_SCHEDULE = NAMESPACE + "/" + ScheduleInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_SCHEDULE_COMPONENT = NAMESPACE + "/" + ScheduleComponentInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_SCHEDULE_REQUEST = NAMESPACE + "/" + ScheduleRequestInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_SCHEDULE_REQUEST_COMPONENT = NAMESPACE + "/" + ScheduleRequestComponentInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_SCHEDULE_TIME_SLOT = NAMESPACE + "/" + TimeSlotInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_MEETING_TIME = NAMESPACE + "/" + MeetingTimeInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_SCHEDULE_BATCH = NAMESPACE + "/" + ScheduleBatchInfo.class.getSimpleName();

    /////////////////////////////
    // TYPES
    /////////////////////////////

    // schedule request types
    public static final String SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST = "kuali.scheduling.schedule.request.type.schedule.request";

    // schedule transaction types
    public static final String SCHEDULE_TRANSACTION_TYPE_REQUEST_TRANSACTION = "kuali.scheduling.schedule.transaction.type.request";

    // schedule batch types
    public static final String SCHEDULE_BATCH_TYPE_BATCH = "kuali.scheduling.schedule.batch.type.schedule.batch";

    // schedule types
    public static final String SCHEDULE_TYPE_SCHEDULE = "kuali.scheduling.schedule.type.schedule";

    // time slot types
    // See https://wiki.kuali.org/display/STUDENT/Time+Slot+Types
    public static final String TIME_SLOT_TYPE_ACTIVITY_OFFERING = "kuali.scheduling.time.slot.type.activityoffering";
    public static final String TIME_SLOT_TYPE_ACTIVITY_OFFERING_TBA = "kuali.scheduling.time.slot.type.activityoffering.tba";
    public static final String TIME_SLOT_TYPE_FINAL_EXAM = "kuali.scheduling.time.slot.type.finalexam"; // currently this is a Placeholder, not an Approved

    /////////////////////////////
    // States
    /////////////////////////////

    // schedule request states
    public static final String SCHEDULE_REQUEST_STATE_LIFECYCLE_KEY = "kuali.scheduling.schedule.request.lifecycle";
    public static final String SCHEDULE_REQUEST_STATE_CREATED = "kuali.scheduling.schedule.request.state.created";

    // schedule transaction states
    public static final String SCHEDULE_TRANSACTION_STATE_LIFECYCLE_KEY = "kuali.scheduling.schedule.transaction.lifecycle";
    public static final String SCHEDULE_TRANSACTION_STATE_COMPLETED = "kuali.scheduling.schedule.transaction.state.completed";

    // schedule batch states
    public static final String SCHEDULE_BATCH_STATE_LIFECYCLE_KEY = "kuali.scheduling.schedulebatch.lifecycle";
    public static final String SCHEDULE_BATCH_STATE_COMPLETED = "kuali.scheduling.schedulebatch.state.completed";

    // schedule states
    public static final String SCHEDULE_STATE_LIFECYCLE_KEY = "kuali.scheduling.schedule.lifecycle";
    public static final String SCHEDULE_STATE_ACTIVE = "kuali.scheduling.schedule.state.active";

    // time slot states
    public static final String TIME_SLOT_LIFECYCLE_KEY = "kuali.scheduling.timeslot.lifecycle";
    public static final String TIME_SLOT_STATE_ACTIVE = "kuali.scheduling.timeslot.state.active";

    ////////////////////////////////////////
    // TYPES / STATES not yet approved
    // for M5,  but used in the code,
    // kept for now.
    ////////////////////////////////////////

    // TODO: go through usages to see if these should remain

    // Time slot types
    public static final String TIME_SLOT_STATE_STANDARD_KEY = "kuali.scheduling.time.slot.state.standard";
    public static final String TIME_SLOT_STATE_NON_STANDARD_KEY = "kuali.scheduling.time.slot.state.non.standard";

    public static final String MONDAY_TIMESLOT_DAY_CODE = "M";
    public static final String TUESDAY_TIMESLOT_DAY_CODE = "T";
    public static final String WEDNESDAY_TIMESLOT_DAY_CODE = "W";
    public static final String THURSDAY_TIMESLOT_DAY_CODE = "H";
    public static final String FRIDAY_TIMESLOT_DAY_CODE = "F";
    public static final String SATURDAY_TIMESLOT_DAY_CODE = "S";
    public static final String SUNDAY_TIMESLOT_DAY_CODE = "U";

    public static final String MONDAY_TIMESLOT_DISPLAY_DAY_CODE = "M";
    public static final String TUESDAY_TIMESLOT_DISPLAY_DAY_CODE = "Tu";
    public static final String WEDNESDAY_TIMESLOT_DISPLAY_DAY_CODE = "W";
    public static final String THURSDAY_TIMESLOT_DISPLAY_DAY_CODE = "Th";
    public static final String FRIDAY_TIMESLOT_DISPLAY_DAY_CODE = "F";
    public static final String SATURDAY_TIMESLOT_DISPLAY_DAY_CODE = "Sa";
    public static final String SUNDAY_TIMESLOT_DISPLAY_DAY_CODE = "Su";

    public static List<Integer> TIME_SLOT_DAYS_OF_WEEK_ACTIVITY_OFFERING_TYPE;

    static {
        List<Integer> temp = new ArrayList<Integer>(7);
        temp.add(Calendar.MONDAY);
        temp.add(Calendar.TUESDAY);
        temp.add(Calendar.WEDNESDAY);
        temp.add(Calendar.THURSDAY);
        temp.add(Calendar.FRIDAY);
        temp.add(Calendar.SATURDAY);
        temp.add(Calendar.SUNDAY);

        TIME_SLOT_DAYS_OF_WEEK_ACTIVITY_OFFERING_TYPE = Collections.unmodifiableList(temp);
    }
}
