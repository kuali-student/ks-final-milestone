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
 * Created by Charles on 9/13/13
 */
package org.kuali.student.poc.eventproc.handler.impl;

import org.apache.log4j.Logger;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.poc.eventproc.api.KSInternalEventProcessor;
import org.kuali.student.poc.eventproc.event.KSEvent;
import org.kuali.student.poc.eventproc.event.KSEventFactory;
import org.kuali.student.poc.eventproc.api.KSHandler;
import org.kuali.student.poc.eventproc.event.KSEventType;
import org.kuali.student.poc.eventproc.event.KSHandlerResult;
import org.kuali.student.poc.eventproc.event.subclass.event.KSAOStateChangeEvent;
import org.kuali.student.poc.eventproc.event.subclass.type.KSAOStateChangeEventType;
import org.kuali.student.poc.eventproc.handler.constraint.ActivityOfferingStateChangeConstraintUtil;
import org.kuali.student.poc.eventproc.handler.constraint.ConstraintResult;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for initiating an AO state change.
 *
 * @author Kuali Student Team
 */
public class ActivityOfferingStateChangeHandler implements KSHandler {
    public static final Logger LOGGER = Logger.getLogger(ActivityOfferingStateChangeHandler.class);
    KSInternalEventProcessor processor;

    public ActivityOfferingStateChangeHandler(KSInternalEventProcessor processor) {
        this.processor = processor;
    }

    @Override
    public boolean handlesEvent(KSEvent event) {
        return event.getEventType() instanceof KSAOStateChangeEventType;
    }

    @Override
    public KSHandlerResult processEvent(KSEvent event, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException,
            VersionMismatchException {
        if (!handlesEvent(event)) {
            // Shouldn't happen, but just in case
            return new KSHandlerResult(KSHandlerResult.FAIL_HANDLER_WONT_PROCESS, ActivityOfferingStateChangeHandler.class);
        }
        KSAOStateChangeEvent stateChangeEvent = (KSAOStateChangeEvent) event;
        String aoId = stateChangeEvent.getAoId();
        LuiInfo aoLui = processor.getLuiService().getLui(aoId, context);
        String toState = stateChangeEvent.getToState();
        String fromState = aoLui.getStateKey();
        // Check to see if there is a state change key
        ActivityOfferingInfo aoInfo = processor.getCoService().getActivityOffering(aoId, context);
        ConstraintResult constraintResult =
            ActivityOfferingStateChangeConstraintUtil.checkConstraint(aoInfo, toState, processor, context);
        if (constraintResult.satisfiesConstraint()) {
            // If so, update the state
            aoLui.setStateKey(toState);
            LuiInfo modifiedAoLui = processor.getLuiService().updateLui(aoLui.getId(), aoLui, context);
            String newToState = modifiedAoLui.getStateKey();
            LOGGER.info("Setting AO to state: " + newToState);
            // Get ready to send AO state modified event
            KSEvent aoModifiedEvent = KSEventFactory.createActivityOfferingStateModifiedEvent(aoId, fromState, newToState);

            processor.internalFireEvent(aoModifiedEvent, context);
            // Add aoModifiedEvent to event (helps track what happens
            event.addDownstreamEvent(aoModifiedEvent);
            // Result
            KSHandlerResult result = new KSHandlerResult(KSHandlerResult.SUCCESS, ActivityOfferingStateChangeHandler.class);
            return result;
        } else if (constraintResult.isNoStateChange()) {
            // Didn't pass, but no state change is special case
            LOGGER.info("AO state unchanged (fromState = " + fromState + ", toState = " + toState + ")");
            return new KSHandlerResult(KSHandlerResult.FAIL_STATE_UNCHANGED, ActivityOfferingStateChangeHandler.class);
        } else {
            // Doesn't satisfy constraint
            LOGGER.info("AO state unchanged--doesn't satisfy constraint");
            return new KSHandlerResult(KSHandlerResult.FAIL_CONSTRAINT_NOT_SATISFIED, ActivityOfferingStateChangeHandler.class);
        }

    }

    @Override
    public String getName() {
        return ActivityOfferingStateChangeHandler.class.getSimpleName();
    }

    @Override
    public void setEventProcessor(KSInternalEventProcessor processor) {
        this.processor = processor;
    }

    @Override
    public List<KSEventType> getEventTypesHandled() {
        List<KSEventType> result = new ArrayList<KSEventType>();
        result.add(KSEventFactory.AO_CHANGE_STATE_EVENT_TYPE);
        return result;
    }
}
