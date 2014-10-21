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
 * Created by Charles on 9/24/13
 */
package org.kuali.student.poc.eventproc.handler.impl;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.poc.eventproc.api.KSHandler;
import org.kuali.student.poc.eventproc.api.KSInternalEventProcessor;
import org.kuali.student.poc.eventproc.event.KSEvent;
import org.kuali.student.poc.eventproc.event.KSEventFactory;
import org.kuali.student.poc.eventproc.event.KSHandlerResult;
import org.kuali.student.poc.eventproc.event.KSEventType;
import org.kuali.student.poc.eventproc.event.subclass.event.KSAOStateModifiedEvent;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Listens for state modified event to cancel/draft and drops ADLs
 *
 * @author Kuali Student Team
 */
public class ActivityOfferingDropADLsHandler implements KSHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityOfferingDropADLsHandler.class);
    KSInternalEventProcessor processor;

    public ActivityOfferingDropADLsHandler(KSInternalEventProcessor processor) {
        this.processor = processor;
    }

    @Override
    public boolean handlesEvent(KSEvent event) {
        if (!(event instanceof KSAOStateModifiedEvent)) {
            return false;
        }
        KSAOStateModifiedEvent stateModifiedEvent = (KSAOStateModifiedEvent) event;
        // Must be ao state modified event--fetch to state
        String toState = stateModifiedEvent.getToState();
        // Only handles if toState is draft/canceled
        boolean result = LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY.equals(toState) ||
                LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY.equals(toState);
        return result;
    }

    @Override
    public KSHandlerResult processEvent(KSEvent event, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        if (!handlesEvent(event)) {
            return new KSHandlerResult(KSHandlerResult.FAIL_HANDLER_WONT_PROCESS, ActivityOfferingDropADLsHandler.class);
        }
        KSAOStateModifiedEvent stateModifiedEvent = (KSAOStateModifiedEvent) event;
        String aoId = stateModifiedEvent.getAoId();
        ActivityOfferingInfo aoInfo = processor.getCoService().getActivityOffering(aoId, context);
        List<ScheduleRequestSetInfo> srsList =
                processor.getSchedulingService().getScheduleRequestSetsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, aoId, context);
        ScheduleRequestSetInfo srs = null;
        if (srsList.size() == 1) {
            // Not able to handle multiple SRS yet (partial colocation, etc)
            srs = srsList.get(0);
        } else {
            throw new OperationFailedException("Expecting SRS list to be size 1: actual size = " + srsList.size());
        }
        if (srs.getRefObjectIds().size() > 1) {
            // This AO is co-located, so just remove the schedule IDs, but do not delete them
            if (aoInfo.getScheduleIds() == null || aoInfo.getScheduleIds().isEmpty()) {
                LOGGER.info("AO has no ADLs to remove");
            } else {
                // Empty it out
                LOGGER.info("Removing ADLs from AO");
                aoInfo.setScheduleIds(new ArrayList<String>());
                processor.getCoService().updateActivityOffering(aoInfo.getId(), aoInfo, context);
            }
        } else if (srs.getRefObjectIds().size() == 1) {
            for (String scheduleId: aoInfo.getScheduleIds()) {
                processor.getSchedulingService().deleteSchedule(scheduleId, context);
            }
            // Empty it out
            LOGGER.info("Removing ADLs from AO");
            aoInfo.setScheduleIds(new ArrayList<String>());
            processor.getCoService().updateActivityOffering(aoInfo.getId(), aoInfo, context);
            List<ScheduleRequestInfo> requests =
                    processor.getSchedulingService().getScheduleRequestsByScheduleRequestSet(srs.getId(), context);
            for (ScheduleRequestInfo request: requests) {
                // Clear out the schedule IDs from the schedule (should this really be done by updateSR?) --cclin
                request.setScheduleId(null);
                processor.getSchedulingService().updateScheduleRequest(request.getId(), request, context);
            }
        }
        return new KSHandlerResult(KSHandlerResult.SUCCESS, ActivityOfferingDropADLsHandler.class);
    }

    @Override
    public String getName() {
        return ActivityOfferingDropADLsHandler.class.getSimpleName();
    }

    @Override
    public void setEventProcessor(KSInternalEventProcessor processor) {
        this.processor = processor;
    }

    @Override
    public List<KSEventType> getEventTypesHandled() {
        List<KSEventType> result = new ArrayList<KSEventType>();
        result.add(KSEventFactory.AO_STATE_MODIFIED_EVENT_TYPE);
        return result;
    }
}
