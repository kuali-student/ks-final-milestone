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
import org.kuali.student.poc.eventproc.KSEventProcessorImpl;
import org.kuali.student.poc.eventproc.api.KSInternalEventProcessor;
import org.kuali.student.poc.eventproc.event.KSEvent;
import org.kuali.student.poc.eventproc.event.KSEventFactory;
import org.kuali.student.poc.eventproc.api.KSHandler;
import org.kuali.student.poc.eventproc.event.KSEventType;
import org.kuali.student.poc.eventproc.event.KSEventResult;
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
        return KSEventFactory.AO_CHANGE_STATE_EVENT_TYPE.equals(event.getEventType());
    }

    @Override
    public KSEventResult processEvent(KSEvent event, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException,
            VersionMismatchException {
        if (!handlesEvent(event)) {
            // Shouldn't happen, but just in case
            return new KSEventResult(KSEventResult.FAIL_INCORRECT_HANDLER);
        }
        String aoId = event.getValueByAttributeKey(KSEventFactory.EVENT_ATTRIBUTE_KEY_AO_ID);
        LuiInfo aoLui = processor.getLuiService().getLui(aoId, context);
        String toState = event.getValueByAttributeKey(KSEventFactory.EVENT_ATTRIBUTE_KEY_AO_TO_STATE);
        String fromState = aoLui.getStateKey();
        // Check to see if there is a state change key
        if (fromState.equals(toState)) {
            return new KSEventResult(KSEventResult.FAIL_STATE_UNCHANGED);
        } else {
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
                KSEvent aoModifiedEvent = KSEventFactory.createActivityOfferingStateModifiedEvent(aoId, newToState);

                List<KSEventResult> downstreamResults = processor.internalFireEvent(aoModifiedEvent, context);
                KSEventResult result = new KSEventResult(KSEventResult.SUCCESS);
                result.addDownstreamResultList(downstreamResults);
                return result;
            } else {
                // Doesn't satisfy constraint
                return new KSEventResult(KSEventResult.FAIL_CONSTRAINT_NOT_SATISFIED);
            }
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
