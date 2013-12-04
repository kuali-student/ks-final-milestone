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
 * Created by Charles on 9/20/13
 */
package org.kuali.student.poc.eventproc.handler.impl;

import org.apache.log4j.Logger;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.poc.eventproc.api.KSHandler;
import org.kuali.student.poc.eventproc.api.KSInternalEventProcessor;
import org.kuali.student.poc.eventproc.event.KSEvent;
import org.kuali.student.poc.eventproc.event.KSEventFactory;
import org.kuali.student.poc.eventproc.event.KSHandlerResult;
import org.kuali.student.poc.eventproc.event.KSEventType;
import org.kuali.student.poc.eventproc.event.subclass.event.KSFOStateModifiedEvent;
import org.kuali.student.poc.eventproc.event.subclass.event.KSRecomputeCOStateEvent;
import org.kuali.student.poc.eventproc.event.subclass.type.KSFOStateModifiedEventType;
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
 * Handles request to recompute CO state
 *
 * @author Kuali Student Team
 */
public class CourseOfferingRecomputeStateHandler implements KSHandler {
    public static final Logger LOGGER = Logger.getLogger(CourseOfferingRecomputeStateHandler.class);
    KSInternalEventProcessor processor;

    public CourseOfferingRecomputeStateHandler(KSInternalEventProcessor processor) {
        this.processor = processor;
    }

    @Override
    public boolean handlesEvent(KSEvent event) {
        return event instanceof KSFOStateModifiedEvent ||
                event instanceof KSRecomputeCOStateEvent;
    }

    @Override
    public KSHandlerResult processEvent(KSEvent event, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        if (!handlesEvent(event)) {
            return new KSHandlerResult(KSHandlerResult.FAIL_HANDLER_WONT_PROCESS, CourseOfferingRecomputeStateHandler.class);
        }
        String coId = null;
        if (event instanceof KSFOStateModifiedEvent) {
            KSFOStateModifiedEvent stateModifiedEvent = (KSFOStateModifiedEvent) event;
            String foId = stateModifiedEvent.getFoId();
            FormatOfferingInfo foInfo = processor.getCoService().getFormatOffering(foId, context);
            coId = foInfo.getCourseOfferingId();
        } else {
            KSRecomputeCOStateEvent recomputeEvent = (KSRecomputeCOStateEvent) event;
            // Must be KSEventFactory.CO_RECOMPUTE_STATE_EVENT_TYPE
            coId = recomputeEvent.getCoId();
        }
        List<FormatOfferingInfo> foInfos = processor.getCoService().getFormatOfferingsByCourseOffering(coId, context);
        String toCoState = FoCoRgComputeStateUtil.computeCoState(foInfos);
        LuiInfo coLui = processor.getLuiService().getLui(coId, context);
        coLui.setStateKey(toCoState);
        LuiInfo modifiedCoLui = processor.getLuiService().updateLui(coLui.getId(), coLui, context);
        LOGGER.info("Setting CO to state: " + modifiedCoLui.getStateKey());

        // No further propagation
        KSHandlerResult eventResult = new KSHandlerResult(KSHandlerResult.SUCCESS, CourseOfferingRecomputeStateHandler.class);
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
        result.add(KSEventFactory.FO_STATE_MODIFIED_EVENT_TYPE);
        result.add(KSEventFactory.CO_RECOMPUTE_STATE_EVENT_TYPE);
        return result;
    }
}
