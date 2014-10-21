/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by Charles on 11/7/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.helper;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.util.container.ADLTimeSlotContainer;
import org.kuali.student.r2.core.scheduling.util.container.RDLTimeSlotContainer;
import org.kuali.student.r2.core.scheduling.util.container.TimeSlotContainer;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

/**
 * Refactored this code outside of CourseOfferingServiceImpl.  Originally wanted to put it in SchedulingServiceImpl
 * but Scheduling Service is in core which lacks access to CourseOfferingServiceConstants, so I placed this in the
 * Course Offering end of things (KSENROLL-10523)
 *
 * @author Charles (cclin)
 */
public class CourseOfferingServiceScheduleHelper {
    private static SchedulingService schedulingService;

    public static TimeSlotContainer computeTimeSlotContainer(String aoId, List<String> scheduleIds, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        TimeSlotContainer container = null;
        if (scheduleIds != null && !scheduleIds.isEmpty()) {
            container = computeADLTimeSlotIdsByActivityOffering(aoId, scheduleIds, context);
        } else {
            container = computeRDLTimeSlotIdsByActivityOffering(aoId, context);
        }
        return container;
    }

    private static RDLTimeSlotContainer computeRDLTimeSlotIdsByActivityOffering(String activityOfferingId,
                                                                                ContextInfo context)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException,
            PermissionDeniedException, OperationFailedException {
        List<String> timeSlotIds = new ArrayList<String>();
        List<ScheduleRequestInfo> scheduleRequestInfos = getSchedulingService().getScheduleRequestsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, activityOfferingId, context);
        if (scheduleRequestInfos != null && !scheduleRequestInfos.isEmpty()) {
            for (ScheduleRequestInfo scheduleRequestInfo : scheduleRequestInfos) {
                List<ScheduleRequestComponentInfo> scheduleRequestComponentInfos = scheduleRequestInfo.getScheduleRequestComponents();
                if (scheduleRequestComponentInfos != null) {
                    for (ScheduleRequestComponentInfo scheduleRequestComponentInfo : scheduleRequestComponentInfos) {
                        if (scheduleRequestComponentInfo.getTimeSlotIds() != null) {
                            // Only add time slots if component is not null
                            timeSlotIds.addAll(scheduleRequestComponentInfo.getTimeSlotIds());
                        }
                    }
                }
            }
        }
        RDLTimeSlotContainer container = new RDLTimeSlotContainer(activityOfferingId, timeSlotIds);
        return container;
    }

    private static ADLTimeSlotContainer computeADLTimeSlotIdsByActivityOffering(String activityOfferingId,
                                                                         List<String> scheduleIds,
                                                                         ContextInfo context)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException,
            PermissionDeniedException, OperationFailedException {

        List<String> timeSlotIds = new ArrayList<String>();
        for (String scheduleId : scheduleIds) {
            if (scheduleId != null && !scheduleId.isEmpty()) {
                // Only do this if there's an schedule ID with length greater than 0.
                ScheduleInfo scheduleInfo = getSchedulingService().getSchedule(scheduleId, context);
                if (scheduleInfo != null) {
                    List<ScheduleComponentInfo> scheduleComponentInfos = scheduleInfo.getScheduleComponents();
                    if (scheduleComponentInfos != null) {
                        for (ScheduleComponentInfo scheduleComponentInfo : scheduleComponentInfos) {
                            if (scheduleComponentInfo.getTimeSlotIds() != null) {
                                // Only add time slots if component is not null
                                timeSlotIds.addAll(scheduleComponentInfo.getTimeSlotIds());
                            }
                        }
                    }
                }
            }
        }
        ADLTimeSlotContainer container = new ADLTimeSlotContainer(activityOfferingId, timeSlotIds);
        return container;
    }

    private static SchedulingService getSchedulingService() {
        if (schedulingService == null) {
            QName qname = new QName(SchedulingServiceConstants.NAMESPACE,
                    SchedulingServiceConstants.SERVICE_NAME_LOCAL_PART);
            schedulingService =  GlobalResourceLoader.getService(qname);
        }
        return schedulingService;
    }

    public static void setSchedulingService(SchedulingService schedulingServiceParam) {
        schedulingService = schedulingServiceParam;
    }
}
