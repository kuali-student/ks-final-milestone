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
import org.kuali.student.r2.core.scheduling.dto.MeetingTimeInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleBatchInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleBatchRespInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRespInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRespItemInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


/**
 * This class holds the constants used by the Scheduling service
 *
 * @Version 1.0
 * @Author Sri komandur@uw.edu
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
    public static final String REF_OBJECT_URI_SCHEDULE_BATCH_RESP = NAMESPACE + "/" + ScheduleBatchRespInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_SCHEDULE_RESP = NAMESPACE + "/" + ScheduleRespInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_SCHEDULE_RESP_ITEM = NAMESPACE + "/" + ScheduleRespItemInfo.class.getSimpleName();

    // Schedule request types
    public static final String SCHEDULE_REQUEST_NORMAL_REQUEST_TYPE = "kuali.scheduling.schedule.request.type.request";

    // Schedule request states
    public static final String SCHEDULE_REQUEST_STATE_LIFECYCLE_KEY = "kuali.scheduling.schedule.request.lifecycle";
    public static final String SCHEDULE_REQUEST_REQUESTED_STATE = "kuali.scheduling.schedule.state.requested";
    public static final String SCHEDULE_REQUEST_WITHDRAWN_STATE = "kuali.scheduling.schedule.state.withdrawn";
    public static final String SCHEDULE_REQUEST_PROCESSED_STATE = "kuali.scheduling.schedule.state.processed";

    // Time slot types
    public static final String TIME_SLOT_STATE_STANDARD_KEY = "kuali.scheduling.time.slot.state.standard";
    public static final String TIME_SLOT_STATE_NON_STANDARD_KEY = "kuali.scheduling.time.slot.state.non.standard";
    public static final String TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY = "kuali.scheduling.time.slot.type.activityoffering";
    public static final String TIME_SLOT_TYPE_FINAL_EXAM_KEY = "kuali.scheduling.time.slot.type.finalexam";

    public static final String MONDAY_TIMESLOT_DAY_CODE = "M";
    public static final String TUESDAY_TIMESLOT_DAY_CODE = "Tu";
    public static final String WEDNESDAY_TIMESLOT_DAY_CODE = "W";
    public static final String THURSDAY_TIMESLOT_DAY_CODE = "Th";
    public static final String FRIDAY_TIMESLOT_DAY_CODE = "F";
    public static final String SATURDAY_TIMESLOT_DAY_CODE = "Sa";
    public static final String SUNDAY_TIMESLOT_DAY_CODE = "Su";

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
