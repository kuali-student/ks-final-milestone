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
 * Created by Charles on 11/6/13
 */
package org.kuali.student.r2.core.scheduling.util.container;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;

import java.util.List;

/**
 * KSENROLL-10523
 * For an AO (or similar), this stores timeSlotIds and corresponding timeSlotInfos.  Used to compartmentalize the
 * code in verifyRegistrationGroup which was sprawiling out of control.
 *
 * @author Kuali Student Team
 */
public abstract class TimeSlotContainer implements HasTimeSlotIdList {
    private List<String> timeSlotIds;
    private List<TimeSlotInfo> timeSlotInfos;

    public TimeSlotContainer(List<String> timeSlotIds) {
        this.timeSlotIds = timeSlotIds;
        timeSlotInfos = null; // lazily compute
    }

    public List<String> getTimeSlotIds() {
        return timeSlotIds;
    }

    public List<TimeSlotInfo> getTimeSlots() {
        return timeSlotInfos;
    }

    public List<TimeSlotInfo> fetchTimeSlots(SchedulingService schedulingService, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        // Always reevaluate
        timeSlotInfos = schedulingService.getTimeSlotsByIds(timeSlotIds, context);
        return timeSlotInfos;
    }

    public boolean conflictsWith(TimeSlotContainer other) {
        List<TimeSlotInfo> otherTimeSlots = other.getTimeSlots();
        if (otherTimeSlots == null || otherTimeSlots.isEmpty()) {
            return false;
        }
        if (timeSlotInfos == null || timeSlotInfos.isEmpty()) {
            return false;
        }
        for (TimeSlotInfo outer: timeSlotInfos) {
            for (TimeSlotInfo inner: otherTimeSlots) {
                boolean inConflict = SchedulingServiceUtil.areTimeSlotsInConflict(outer, inner);
                if (inConflict) {
                    return true;
                }
            }
        }
        return false;
    }

    public abstract String getRefObjectId();
}
