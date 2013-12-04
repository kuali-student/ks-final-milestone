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
 * Created by Charles on 9/23/13
 */
package org.kuali.student.poc.eventproc.handler.impl;

import org.apache.log4j.Logger;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.poc.eventproc.api.KSHandler;
import org.kuali.student.poc.eventproc.api.KSInternalEventProcessor;
import org.kuali.student.poc.eventproc.event.KSEvent;
import org.kuali.student.poc.eventproc.event.KSEventFactory;
import org.kuali.student.poc.eventproc.event.KSHandlerResult;
import org.kuali.student.poc.eventproc.event.KSEventType;
import org.kuali.student.poc.eventproc.event.subclass.event.KSInvalidateRGStateEvent;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for handling invalidate RG events
 *
 * @author Kuali Student Team
 */
public class RegGroupInvalidateStateHandler implements KSHandler {
    public static final Logger LOGGER = Logger.getLogger(RegGroupInvalidateStateHandler.class);
    KSInternalEventProcessor processor;

    public RegGroupInvalidateStateHandler(KSInternalEventProcessor processor) {
        this.processor = processor;
    }

    @Override
    public boolean handlesEvent(KSEvent event) {
        return event instanceof KSInvalidateRGStateEvent;
    }

    @Override
    public KSHandlerResult processEvent(KSEvent event, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException,
            VersionMismatchException {
        if (!handlesEvent(event)) {
            return new KSHandlerResult(KSHandlerResult.FAIL_HANDLER_WONT_PROCESS, RegGroupInvalidateStateHandler.class);
        }
        LOGGER.info(">>> " + getName() + " handling " + event.toString());
        KSInvalidateRGStateEvent invalidateEvent = (KSInvalidateRGStateEvent) event;
        String rgId = invalidateEvent.getRgId();
        LuiInfo rgLui = processor.getLuiService().getLui(rgId, context);
        if (rgLui.getStateKey().equals(LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY)) {
            // RG is already invalid
            return new KSHandlerResult(KSHandlerResult.FAIL_STATE_UNCHANGED, RegGroupInvalidateStateHandler.class);
        }
        rgLui.setStateKey(LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY);
        LuiInfo modifiedRgLui =
                processor.getLuiService().updateLui(rgLui.getId(), rgLui, context);
        LOGGER.info("RG state change to: " + modifiedRgLui.getStateKey());
        // RG doesn't fire an event, but one could add a RG modified state event
        KSHandlerResult eventResult = new KSHandlerResult(KSHandlerResult.SUCCESS, RegGroupInvalidateStateHandler.class);
        return eventResult;
    }

    @Override
    public String getName() {
        return RegGroupInvalidateStateHandler.class.getSimpleName();
    }

    @Override
    public void setEventProcessor(KSInternalEventProcessor processor) {
        this.processor = processor;
    }

    @Override
    public List<KSEventType> getEventTypesHandled() {
        List<KSEventType> result = new ArrayList<KSEventType>();
        result.add(KSEventFactory.RG_INVALIDATE_STATE_EVENT_TYPE);
        return result;
    }
}
