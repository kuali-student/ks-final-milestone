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
    public static final String REF_OBJECT_URI_SCHEDULE_REQUEST_SET = NAMESPACE + "/" + ScheduleRequestSetInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_SCHEDULE_REQUEST_COMPONENT = NAMESPACE + "/" + ScheduleRequestComponentInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_SCHEDULE_TIME_SLOT = NAMESPACE + "/" + TimeSlotInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_MEETING_TIME = NAMESPACE + "/" + MeetingTimeInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_SCHEDULE_BATCH = NAMESPACE + "/" + ScheduleBatchInfo.class.getSimpleName();

    /////////////////////////////
    // TYPES
    /////////////////////////////

    // schedule request types
    public static final String SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST = "kuali.scheduling.schedule.request.type.schedule.request";

    // schedule request set types
    public static final String SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET = "kuali.scheduling.schedule.request.set.type.schedule.request.set";

    // schedule transaction types
    public static final String SCHEDULE_TRANSACTION_TYPE_REQUEST_TRANSACTION = "kuali.scheduling.schedule.transaction.type.request";
    public static final String SCHEDULE_TRANSACTION_TYPE_RELEASE_TRANSACTION = "kuali.scheduling.schedule.transaction.type.release";

    // schedule batch types
    public static final String SCHEDULE_BATCH_TYPE_BATCH = "kuali.scheduling.schedule.batch.type.schedule.batch";

    // schedule types
    public static final String SCHEDULE_TYPE_SCHEDULE = "kuali.scheduling.schedule.type.schedule";

    // time slot types
    // See https://wiki.kuali.org/display/STUDENT/Time+Slot+Types

    public static final String TIME_SLOT_TYPE_GROUPING = "kuali.scheduling.type.time.slot";
    public static final String TIME_SLOT_TYPE_ACTIVITY_OFFERING_ADHOC = "kuali.scheduling.time.slot.type.activityoffering.adhoc";
    public static final String TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD = "kuali.scheduling.time.slot.type.activityoffering.standard";
    public static final String TIME_SLOT_TYPE_ACTIVITY_OFFERING_TBA = "kuali.scheduling.time.slot.type.activityoffering.tba";
    public static final String TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM = "kuali.scheduling.time.slot.type.activityoffering.standard.fullterm";
    public static final String TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_FALL = "kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall";
    public static final String TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_SPRING = "kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring";
    public static final String TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_WINTER = "kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.winter";
    public static final String TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_SUMMER = "kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.summer";
    public static final String TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_HALFTERM = "kuali.scheduling.time.slot.type.activityoffering.standard.halfterm";
    public static final String TIME_SLOT_TYPE_EXAM = "kuali.scheduling.time.slot.type.exam"; // currently this is a Placeholder, not an Approved

    //room feature types
    public static final String ROOM_FEATURE_TYPE_3_4_UMATIC_VCR = "kuali.scheduling.type.room.feature.umaticvcr";
    public static final String ROOM_FEATURE_TYPE_16MM_FILM_PROJECTOR = "kuali.scheduling.type.room.feature.projector.16mm";
    public static final String ROOM_FEATURE_TYPE_AIR_CONDITIONING = "kuali.scheduling.type.room.feature.aircond";
    public static final String ROOM_FEATURE_TYPE_AIR_CONDITIONING_WINDOW_UNIT = "kuali.scheduling.type.room.feature.aircondwindow";
    public static final String ROOM_FEATURE_TYPE_ASSISTED_LISTENING_SYSTEM = "kuali.scheduling.type.room.feature.assistlisten";
    public static final String ROOM_FEATURE_TYPE_AUDIO_CAPTURE = "kuali.scheduling.type.room.feature.audiocap";
    public static final String ROOM_FEATURE_TYPE_AUDIO_STEREO = "kuali.scheduling.type.room.feature.audiostereo";
    public static final String ROOM_FEATURE_TYPE_AUDIO_SURROUND_SOUND = "kuali.scheduling.type.room.feature.audiosurround";
    public static final String ROOM_FEATURE_TYPE_AUX_INPUT = "kuali.scheduling.type.room.feature.auxinput";
    public static final String ROOM_FEATURE_TYPE_AV_CONFERENCE_PHONE = "kuali.scheduling.type.room.feature.avconfphone";
    public static final String ROOM_FEATURE_TYPE_BLU_RAY_PLAYER = "kuali.scheduling.type.room.feature.bluray";
    public static final String ROOM_FEATURE_TYPE_BOARD_CHALK = "kuali.scheduling.type.room.feature.chalkboard";
    public static final String ROOM_FEATURE_TYPE_BOARD_CHALK_MOVEABLE = "kuali.scheduling.type.room.feature.chalkboard.moveable";
    public static final String ROOM_FEATURE_TYPE_BOARD_WHITE = "kuali.scheduling.type.room.feature.whiteboard";
    public static final String ROOM_FEATURE_TYPE_BOARD_WHITE_MOVEABLE = "kuali.scheduling.type.room.feature.whiteboard.moveable";
    public static final String ROOM_FEATURE_TYPE_CASSETTE_PLAYER = "kuali.scheduling.type.room.feature.cassetteplayer";
    public static final String ROOM_FEATURE_TYPE_CLICKER = "kuali.scheduling.type.room.feature.clicker";
    public static final String ROOM_FEATURE_TYPE_COMPUTER_INSTRUCTOR_MAC_DUAL_BOOT_ = "kuali.scheduling.type.room.feature.computer.instructmac";
    public static final String ROOM_FEATURE_TYPE_COMPUTER_INSTRUCTOR_WINDOWS = "kuali.scheduling.type.room.feature.computer.instructwin";
    public static final String ROOM_FEATURE_TYPE_COMPUTER_MAC = "kuali.scheduling.type.room.feature.computer.mac";
    public static final String ROOM_FEATURE_TYPE_COMPUTER_MAC_DUAL_BOOT_ = "kuali.scheduling.type.room.feature.computer.macdual";
    public static final String ROOM_FEATURE_TYPE_COMPUTER_WINDOWS = "kuali.scheduling.type.room.feature.computer.win";
    public static final String ROOM_FEATURE_TYPE_DOCUMENT_CAMERA = "kuali.scheduling.type.room.feature.doccamera";
    public static final String ROOM_FEATURE_TYPE_DVD_CD_PLAYER = "kuali.scheduling.type.room.feature.dvdcd";
    public static final String ROOM_FEATURE_TYPE_LASER_DISC = "kuali.scheduling.type.room.feature.laserdisc";
    public static final String ROOM_FEATURE_TYPE_LECTERN_STAND_UP = "kuali.scheduling.type.room.feature.lecternstand";
    public static final String ROOM_FEATURE_TYPE_LECTERN_STAND_UP_ADJUSTABLE = "kuali.scheduling.type.room.feature.lecternstandadjust";
    public static final String ROOM_FEATURE_TYPE_LECTERN_TABLE_TOP = "kuali.scheduling.type.room.feature.lecterntable";
    public static final String ROOM_FEATURE_TYPE_MEDIA_CONTROL_SYSTEM = "kuali.scheduling.type.room.feature.mediacontrol";
    public static final String ROOM_FEATURE_TYPE_MICROPHONE = "kuali.scheduling.type.room.feature.mic.general";
    public static final String ROOM_FEATURE_TYPE_MICROPHONE_WIRED_LECTERN = "kuali.scheduling.type.room.feature.mic.wiredlectern";
    public static final String ROOM_FEATURE_TYPE_MICROPHONE_WIRELESS_HANDHELD = "kuali.scheduling.type.room.feature.mic.wirelesshand";
    public static final String ROOM_FEATURE_TYPE_MICROPHONE_WIRELESS_LAVALIER = "kuali.scheduling.type.room.feature.mic.wirelesslavalier";
    public static final String ROOM_FEATURE_TYPE_PROJECTOR_OVERHEAD_TRANSPANCY = "kuali.scheduling.type.room.feature.projector.overhead";
    public static final String ROOM_FEATURE_TYPE_PROJECTOR_VIDEO = "kuali.scheduling.type.room.feature.projector.video";
    public static final String ROOM_FEATURE_TYPE_ROOM_DIVIDER = "kuali.scheduling.type.room.feature.roomdivider";
    public static final String ROOM_FEATURE_TYPE_SCREEN_FRONT_PROJECTION_ELECTRIC = "kuali.scheduling.type.room.feature.screen.frontelec";
    public static final String ROOM_FEATURE_TYPE_SCREEN_FRONT_PROJECTION_MANUAL = "kuali.scheduling.type.room.feature.screen.frontmanual";
    public static final String ROOM_FEATURE_TYPE_SCREEN_REAR_PROJECTION = "kuali.scheduling.type.room.feature.screen.rear";
    public static final String ROOM_FEATURE_TYPE_SCREEN_WALL = "kuali.scheduling.type.room.feature.screen.wall";
    public static final String ROOM_FEATURE_TYPE_SINK = "kuali.scheduling.type.room.feature.sink";
    public static final String ROOM_FEATURE_TYPE_SLIDE_PROJECTOR = "kuali.scheduling.type.room.feature.projector.slide";
    public static final String ROOM_FEATURE_TYPE_SMART_BOARD = "kuali.scheduling.type.room.feature.smartboard";
    public static final String ROOM_FEATURE_TYPE_SMART_PODIUM_MONITORS = "kuali.scheduling.type.room.feature.smartpodium";
    public static final String ROOM_FEATURE_TYPE_STAGE_INSTRUCTOR_ACCESSIBLE = "kuali.scheduling.type.room.feature.stage.instraccess";
    public static final String ROOM_FEATURE_TYPE_STAGE_INSTRUCTOR_NOT_ACCESSIBLE = "kuali.scheduling.type.room.feature.stage.instrnotaccess";
    public static final String ROOM_FEATURE_TYPE_TELEPHONE = "kuali.scheduling.type.room.feature.telephone";
    public static final String ROOM_FEATURE_TYPE_VCR_VHS_ = "kuali.scheduling.type.room.feature.vcrvhs";
    public static final String ROOM_FEATURE_TYPE_WINDOWS = "kuali.scheduling.type.room.feature.windows";
    public static final String ROOM_FEATURE_TYPE_WEB_CONFERENCING = "kuali.scheduling.type.room.feature.webconference";
    public static final String ROOM_FEATURE_TYPE_VIDEO_CONFERENCING = "kuali.scheduling.type.room.feature.videoconference";

    // schedule transaction group types
    public static final String SCHEDULE_TRANSACTION_GROUP_TYPE_STANDARD = "kuali.scheduling.schedule.transaction.group.type.standard";

    // schedule request group types
    public static final String SCHEDULE_REQUEST_GROUP_TYPE_STANDARD = "kuali.scheduling.schedule.request.group.constraint.type.standard";

    /////////////////////////////
    // States
    /////////////////////////////

    // schedule request states
    public static final String SCHEDULE_REQUEST_STATE_LIFECYCLE_KEY = "kuali.scheduling.schedule.request.lifecycle";
    public static final String SCHEDULE_REQUEST_STATE_CREATED = "kuali.scheduling.schedule.request.state.created";
    public static final String SCHEDULE_REQUEST_STATE_MODIFIED = "kuali.scheduling.schedule.request.state.modified";
    public static final String SCHEDULE_REQUEST_STATE_REQUESTED = "kuali.scheduling.schedule.request.state.requested";
    public static final String SCHEDULE_REQUEST_STATE_PROCESSED = "kuali.scheduling.schedule.request.state.processed";
    public static final String SCHEDULE_REQUEST_STATE_ERROR = "kuali.scheduling.schedule.request.state.error";

    // schedule request set states
    public static final String SCHEDULE_REQUEST_SET_STATE_LIFECYCLE_KEY = "kuali.scheduling.schedule.request.set.lifecycle";
    public static final String SCHEDULE_REQUEST_SET_STATE_CREATED = "kuali.scheduling.schedule.request.set.state.created";
    public static final String SCHEDULE_REQUEST_SET_STATE_MODIFIED = "kuali.scheduling.schedule.request.set.state.modified";
    public static final String SCHEDULE_REQUEST_SET_STATE_REQUESTED = "kuali.scheduling.schedule.request.set.state.requested";
    public static final String SCHEDULE_REQUEST_SET_STATE_PARTIAL = "kuali.scheduling.schedule.request.set.state.partial";
    public static final String SCHEDULE_REQUEST_SET_STATE_PROCESSED = "kuali.scheduling.schedule.request.set.state.processed";
    public static final String SCHEDULE_REQUEST_SET_STATE_ERROR = "kuali.scheduling.schedule.request.set.state.error";


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

    // schedule transaction group states
    public static final String SCHEDULE_TRANSACTION_GROUP_LIFECYCLE_KEY = "kuali.scheduling.schedule.transaction.group.lifecycle";
    public static final String SCHEDULE_TRANSACTION_GROUP_STATE_ACTIVE = "kuali.scheduling.schedule.transaction.group.state.active";
    public static final String SCHEDULE_TRANSACTION_GROUP_STATE_INACTIVE = "kuali.scheduling.schedule.transaction.group.state.inactive";

    // schedule request group states
    public static final String SCHEDULE_REQUEST_GROUP_LIFECYCLE_KEY = "kuali.scheduling.schedule.request.group.constraint.lifecycle";
    public static final String SCHEDULE_REQUEST_GROUP_STATE_ACTIVE = "kuali.scheduling.schedule.request.group.constraint.state.active";
    public static final String SCHEDULE_REQUEST_GROUP_STATE_INACTIVE = "kuali.scheduling.schedule.request.group.constraint.state.inactive";

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
