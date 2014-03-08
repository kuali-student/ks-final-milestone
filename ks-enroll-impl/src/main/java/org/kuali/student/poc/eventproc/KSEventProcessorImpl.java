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
 * Created by Charles on 9/11/13
 */
package org.kuali.student.poc.eventproc;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.poc.eventproc.api.KSEventProcessor;
import org.kuali.student.poc.eventproc.api.KSInternalEventProcessor;
import org.kuali.student.poc.eventproc.event.KSEvent;
import org.kuali.student.poc.eventproc.api.KSHandler;
import org.kuali.student.poc.eventproc.event.KSHandlerResult;
import org.kuali.student.poc.eventproc.event.KSEventType;
import org.kuali.student.poc.eventproc.handler.impl.helper.KSHandlerLoader;
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
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.constants.StateServiceConstants;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main class to process events.
 *
 * @author Kuali Student Team
 */
public class KSEventProcessorImpl implements KSEventProcessor, KSInternalEventProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(KSEventProcessorImpl.class);

    private CourseOfferingService coService;
    private LuiService luiService;
    private SchedulingService schedulingService;
    private CourseOfferingSetService socService;
    private StateService stateService;

    private boolean hasLoadedHandlers = false;

    private Map<KSEventType, List<KSHandler>> eventTypeToHandlers = new HashMap<KSEventType, List<KSHandler>>();

    @Override
    public void registerHandler(KSHandler handler) {
        for (KSEventType eventType: handler.getEventTypesHandled()) {
            if (!eventTypeToHandlers.containsKey(eventType)) {
                eventTypeToHandlers.put(eventType, new ArrayList<KSHandler>());
            }
            // May want to avoid having a handler entered in twice...
            eventTypeToHandlers.get(eventType).add(handler);
        }
    }

    @Override
    public void fireEvent(KSEvent event, ContextInfo context)
            throws DataValidationErrorException, PermissionDeniedException, OperationFailedException,
                   VersionMismatchException, InvalidParameterException, ReadOnlyException,
                   MissingParameterException, DoesNotExistException {
        if (eventTypeToHandlers.isEmpty()) {
            KSHandlerLoader.loadHandlersIntoEventProcessor(this);
        }
        LOGGER.info("========== Received external event: {}", event);
        // Create a way to prevent infinite looping (e.g. store set of events
        internalFireEvent(event, context);
        LOGGER.info("========== Finished external event: {}", event);
    }

    @Override
    public void internalFireEvent(KSEvent event, ContextInfo context)
            throws DataValidationErrorException, PermissionDeniedException, OperationFailedException,
            VersionMismatchException, InvalidParameterException, ReadOnlyException,
            MissingParameterException, DoesNotExistException {
        LOGGER.info("....Internal event: {}", event);
        List<KSHandler> handlers = eventTypeToHandlers.get(event.getEventType());
        int count = 0;
        for (KSHandler handler: handlers) {
            count++;
            LOGGER.info("Handler ({} of {}), {}, processing event: {}",
                    count, handlers.size(), handler.getName(), event);
            KSHandlerResult handlerResult = handler.processEvent(event, context);
            // Helps track results
            event.addHandlerResult(handlerResult);
        }
    }

    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }

    @Override
    public CourseOfferingService getCoService() {
        if (coService != null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                        CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return coService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    @Override
    public LuiService getLuiService() {
        if (luiService != null) {
            luiService = (LuiService) GlobalResourceLoader.getService(new QName(LuiServiceConstants.NAMESPACE,
                    LuiServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return luiService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @Override
    public SchedulingService getSchedulingService() {
        if (schedulingService != null) {
            schedulingService = (SchedulingService) GlobalResourceLoader.getService(new QName(SchedulingServiceConstants.NAMESPACE,
                    SchedulingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return schedulingService;
    }

    public void setSocService(CourseOfferingSetService socService) {
        this.socService = socService;
    }

    @Override
    public CourseOfferingSetService getSocService() {
        if (socService != null) {
            socService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE,
                    CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return socService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    @Override
    public StateService getStateService() {
        if (stateService != null) {
            stateService = (StateService) GlobalResourceLoader.getService(new QName(StateServiceConstants.NAMESPACE,
                    StateServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return stateService;
    }
}
