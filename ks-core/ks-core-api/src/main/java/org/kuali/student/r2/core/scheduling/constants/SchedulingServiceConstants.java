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
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;


/**
 * This class holds the constants used by the Appointment service
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
    public static final String REF_OBJECT_URI_SCHEDULE_TIME_SLOT = NAMESPACE + "/" + TimeSlotInfo.class.getSimpleName();
}
