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
 * @author Kuali Student Team
 */

package org.kuali.student.enrollment.class2.courseoffering.util;

public class ManageSocConstants {

    public static final Long ONE_MINUTE_IN_MILLIS = 1000l * 60l;  // (1000 milliseconds per second * 60 seconds per minute)

    public static final String NOT_STARTED_STATUS_UI = "Not Started";
    public static final String SOC_IN_PROGRESS_PUBLISHING_STATUS_UI = "In Progress";
    public static final String SOC_COMPLETED_PUBLISHING_STATUS_UI = "Completed";

    public static final String SCHEDULE_DATE_FORMAT = "MM-dd-yyyy hh:mm a";
    public static final String SCHEDULE_DURATION_TIME_FORMAT = "HH:mm";

    /**
     * These are the confirm dialog bean ids used in Manage SOC view.
     */
    public static class ConfirmDialogs{
        public static final String MASS_SCHEDULING = "massScheduleConfirmDialog";
        public static final String MASS_PUBLISHLING = "massPublishConfirmDialog";
        public static final String LOCK = "lockConfirmDialog";
        public static final String FINAL_EDITS = "finalEditConfirmDialog";
        public static final String CLOSE_SET = "closeConfirmDialog";
    }

    /**
     * UI Display constants
     */
    public static final  String DISPLAY_SCHEDULE_IN_PROGRESS = "Scheduling in progress";
    public static final  String DISPLAY_IN_PROGRESS = "  (in progress)";
    public static final  String DISPLAY_PUBLISHING_IN_PROGRESS = "Publishing in progress";


    /**
     * Info/Error/Warn message keys goes here
     */
    public static class MessageKeys {

        //Info keys here
        public static final String INFO_SOC_LOCKED = "info.managesoc.locked";
        public static final String INFO_FINAL_EDITS = "info.managesoc.finaledit";
        public static final String INFO_CLOSED = "info.managesoc.closed";
        public static final String INFO_SEND_TO_SCHEDULER = "info.managesoc.send.to.scheduler";

        //Error keys here
        public static final String ERROR_SOC_NOT_EXISTS = "error.mangesoc.soc.notexits";
        public static final String ERROR_MULTIPLE_SOCS = "error.mangesoc.multiple.soc";
        public static final String ERROR_STATUS_CHANGE_FAILED = "error.managesoc.statuschange.failed";
        public static final String ERROR_SEND_TO_SCHEDULER = "error.managesoc.send.to.scheduler.failed";
        public static final String ERROR_INVALID_STATUS_FOR_LOCK = "error.managesoc.invalid.status.for.lock";
        public static final String ERROR_INVALID_STATUS_FOR_SCHEDULE = "error.managesoc.invalid.status.for.schedule";
        public static final String ERROR_MULTIPLE_TERMS = "error.managesoc.multiple.terms";
        public static final String ERROR_INVALID_TERM = "error.managesoc.term.not.found";
        public static final String ERROR_INVALID_STATUS_FOR_FINALEDIT = "error.managesoc.invalid.status.for.finaledit";
        public static final String ERROR_INVALID_STATUS_FOR_PUBLISH = "error.managesoc.invalid.status.for.publish";
        public static final String ERROR_INVALID_STATUS_FOR_CLOSE = "error.managesoc.invalid.status.for.close";

        //Warn keys here
    }

}
