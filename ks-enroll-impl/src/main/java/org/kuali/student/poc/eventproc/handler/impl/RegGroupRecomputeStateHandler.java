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
 * Created by Charles on 9/22/13
 */
package org.kuali.student.poc.eventproc.handler.impl;

import org.apache.log4j.Logger;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.poc.eventproc.KSEventProcessorImpl;
import org.kuali.student.poc.eventproc.api.KSHandler;
import org.kuali.student.poc.eventproc.api.KSInternalEventProcessor;
import org.kuali.student.poc.eventproc.event.KSEvent;
import org.kuali.student.poc.eventproc.event.KSEventFactory;
import org.kuali.student.poc.eventproc.event.KSEventResult;
import org.kuali.student.poc.eventproc.event.KSEventType;
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
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for computing the state of the RG from its component AO states
 *
 * @author Kuali Student Team
 */
public class RegGroupRecomputeStateHandler implements KSHandler {
    public static final Logger LOGGER = Logger.getLogger(RegGroupRecomputeStateHandler.class);
    KSInternalEventProcessor processor;

    public RegGroupRecomputeStateHandler(KSInternalEventProcessor processor) {
        this.processor = processor;
    }

    @Override
    public boolean handlesEvent(KSEvent event) {
        return KSEventFactory.AO_STATE_MODIFIED_EVENT_TYPE.equals(event.getEventType()) ||
                KSEventFactory.RG_RECOMPUTE_STATE_EVENT_TYPE.equals(event.getEventType()) ||
                KSEventFactory.RG_VALIDATE_STATE_EVENT_TYPE.equals(event.getEventType());
    }

    @Override
    public KSEventResult processEvent(KSEvent event, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException,
            VersionMismatchException {
        if (!handlesEvent(event)) {
            return new KSEventResult(KSEventResult.FAIL_INCORRECT_HANDLER);
        }
        KSEventResult eventResult = null;
        if (KSEventFactory.AO_STATE_MODIFIED_EVENT_TYPE.equals(event.getEventType())) {
            String aoId = event.getValueByAttributeKey(KSEventFactory.EVENT_ATTRIBUTE_KEY_AO_ID);
            eventResult = _processAoStateModifiedEvent(aoId, event, context);
        } else if (KSEventFactory.RG_VALIDATE_STATE_EVENT_TYPE.equals(event.getEventType())) {
            String rgId = event.getValueByAttributeKey(KSEventFactory.EVENT_ATTRIBUTE_KEY_RG_ID);
            eventResult = _processValidateRgStateEvent(rgId, event, context);
        } else {
            // Must be RG_RECOMPUTE_STATE_EVENT_TYPE
            String rgId = event.getValueByAttributeKey(KSEventFactory.EVENT_ATTRIBUTE_KEY_RG_ID);
            eventResult = _processRecomputeRgStateEvent(rgId, event, context);
        }
        return eventResult;

    }

    private KSEventResult _processRecomputeRgStateEvent(String rgId, KSEvent event, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException,
            VersionMismatchException {
        // Note: can't change RG state if in invalid.
        RegistrationGroupInfo rgInfo = processor.getCoService().getRegistrationGroup(rgId, context);
        if (LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY.equals(rgInfo.getStateKey())) {
            // If RG in invalid state, recompute doesn't work
            LOGGER.info("RG state unchanged: RG has invalid state");
            return new KSEventResult(KSEventResult.FAIL_CONSTRAINT_NOT_SATISFIED);
        } else {
            // Validate state is removing the invalidate of RG, then recomputing
            // If we get here, we are not in an invalid state, so it's fine to recompute
            return _processValidateRgStateEvent(rgId, event, context);
        }
    }

    private KSEventResult _processValidateRgStateEvent(String rgId, KSEvent event, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException,
            VersionMismatchException {
        RegistrationGroupInfo rgInfo = processor.getCoService().getRegistrationGroup(rgId, context);
        List<String> aoIds = rgInfo.getActivityOfferingIds();
        List<ActivityOfferingInfo> aoInfos = processor.getCoService().getActivityOfferingsByIds(aoIds, context);
        String toRgState = FoCoRgComputeStateUtil.computeRgState(aoInfos);
        LuiInfo rgLui = processor.getLuiService().getLui(rgId, context);
        String fromState = rgLui.getStateKey();
        if (fromState.equals(toRgState)) {
            // No state change
            LOGGER.info("RG state unchanged: fromState == toState");
            return new KSEventResult(KSEventResult.FAIL_STATE_UNCHANGED);
        }
        rgLui.setStateKey(toRgState);
        LuiInfo modifiedRgLui = processor.getLuiService().updateLui(rgLui.getId(), rgLui, context);
        LOGGER.info("RG state change to: " + modifiedRgLui.getStateKey());
        KSEventResult result = new KSEventResult(KSEventResult.SUCCESS);
        return result;  //To change body of created methods use File | Settings | File Templates.
    }

    private KSEventResult _processAoStateModifiedEvent(String aoId, KSEvent event, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException,
            DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        KSEventResult result = new KSEventResult(KSEventResult.SUCCESS);
        List<RegistrationGroupInfo> rgInfos =
                processor.getCoService().getRegistrationGroupsByActivityOffering(aoId, context);
        for (RegistrationGroupInfo rg: rgInfos) {
            KSEvent rgEvent = KSEventFactory.createRecomputeRegGroupStateEvent(rg.getId());
            List<KSEventResult> results = processor.internalFireEvent(rgEvent, context);
            result.addDownstreamResultList(results);
        }
        return result;
    }

    @Override
    public String getName() {
        return RegGroupRecomputeStateHandler.class.getSimpleName();
    }

    @Override
    public void setEventProcessor(KSInternalEventProcessor processor) {
        this.processor = processor;
    }

    @Override
    public List<KSEventType> getEventTypesHandled() {
        List<KSEventType> result = new ArrayList<KSEventType>();
        result.add(KSEventFactory.AO_STATE_MODIFIED_EVENT_TYPE);
        result.add(KSEventFactory.RG_RECOMPUTE_STATE_EVENT_TYPE);
        result.add(KSEventFactory.RG_VALIDATE_STATE_EVENT_TYPE);
        return result;
    }
}
