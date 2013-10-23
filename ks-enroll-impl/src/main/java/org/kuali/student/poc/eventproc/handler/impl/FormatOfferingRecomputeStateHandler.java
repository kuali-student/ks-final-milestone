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
 * Created by Charles on 9/15/13
 */
package org.kuali.student.poc.eventproc.handler.impl;

import org.apache.log4j.Logger;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.poc.eventproc.api.KSInternalEventProcessor;
import org.kuali.student.poc.eventproc.event.KSEvent;
import org.kuali.student.poc.eventproc.api.KSHandler;
import org.kuali.student.poc.eventproc.event.KSEventFactory;
import org.kuali.student.poc.eventproc.event.KSHandlerResult;
import org.kuali.student.poc.eventproc.event.KSEventType;
import org.kuali.student.poc.eventproc.event.subclass.event.KSAOStateModifiedEvent;
import org.kuali.student.poc.eventproc.event.subclass.event.KSRecomputeFOStateEvent;
import org.kuali.student.poc.eventproc.event.subclass.type.KSAOStateModifiedEventType;
import org.kuali.student.poc.eventproc.handler.impl.helper.FoCoRgComputeStateUtil;
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
 * Handler for recomputing FO state
 *
 * @author Kuali Student Team
 */
public class FormatOfferingRecomputeStateHandler implements KSHandler {
    public static final Logger LOGGER = Logger.getLogger(FormatOfferingRecomputeStateHandler.class);
    KSInternalEventProcessor processor;

    public FormatOfferingRecomputeStateHandler(KSInternalEventProcessor processor) {
        this.processor = processor;
    }

    @Override
    public boolean handlesEvent(KSEvent event) {
        return event instanceof KSAOStateModifiedEvent ||
                event instanceof KSRecomputeFOStateEvent;
    }

    @Override
    public KSHandlerResult processEvent(KSEvent event, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        if (!handlesEvent(event)) {
            return new KSHandlerResult(KSHandlerResult.FAIL_HANDLER_WONT_PROCESS, FormatOfferingRecomputeStateHandler.class);
        }
        String foId = null;
        if (event instanceof KSAOStateModifiedEvent) {
            KSAOStateModifiedEvent stateModifiedEvent = (KSAOStateModifiedEvent) event;
            String aoId = stateModifiedEvent.getAoId();
            ActivityOfferingInfo aoInfo = processor.getCoService().getActivityOffering(aoId, context);
            foId = aoInfo.getFormatOfferingId();
        } else {
            KSRecomputeFOStateEvent recomputeEvent = (KSRecomputeFOStateEvent) event;
            // Must be KSEventFactory.FO_RECOMPUTE_STATE_EVENT_TYPE
            foId = recomputeEvent.getFoId();
        }
        List<ActivityOfferingInfo> aoInfos = processor.getCoService().getActivityOfferingsByFormatOffering(foId, context);
        String toFoState = FoCoRgComputeStateUtil.computeFoState(aoInfos);
        LuiInfo foLui = processor.getLuiService().getLui(foId, context);
        String fromFoState = foLui.getStateKey();
        if (fromFoState.equals(toFoState)) {
            LOGGER.info("FO state unchanged (fromState = " + fromFoState + ", toState = " + toFoState + ")");
            return new KSHandlerResult(KSHandlerResult.FAIL_STATE_UNCHANGED, FormatOfferingRecomputeStateHandler.class);
        }
        foLui.setStateKey(toFoState);

        LuiInfo modifiedFoLui = processor.getLuiService().updateLui(foLui.getId(), foLui, context);
        LOGGER.info("Setting FO to state: " + modifiedFoLui.getStateKey());

        // Fire compute CO event
        KSHandlerResult eventResult = new KSHandlerResult(KSHandlerResult.SUCCESS, FormatOfferingRecomputeStateHandler.class);
        KSEvent foStateModifiedEvent = KSEventFactory.createFormatOfferingStateModifiedEvent(foId);
        processor.internalFireEvent(foStateModifiedEvent, context);
        event.addDownstreamEvent(foStateModifiedEvent); // Tracks other events caused by "event"
        return eventResult;
    }

    @Override
    public String getName() {
        return FormatOfferingRecomputeStateHandler.class.getSimpleName();
    }

    @Override
    public void setEventProcessor(KSInternalEventProcessor processor) {
        this.processor = processor;
    }

    @Override
    public List<KSEventType> getEventTypesHandled() {
        List<KSEventType> result = new ArrayList<KSEventType>();
        result.add(KSEventFactory.AO_STATE_MODIFIED_EVENT_TYPE);
        result.add(KSEventFactory.FO_RECOMPUTE_STATE_EVENT_TYPE);
        return result;
    }
}
