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

}
