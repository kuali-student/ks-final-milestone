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
package org.kuali.student.poc.eventproc.api;

import org.kuali.student.poc.eventproc.event.KSEvent;
import org.kuali.student.poc.eventproc.event.KSHandlerResult;
import org.kuali.student.poc.eventproc.event.KSEventType;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import java.util.List;

/**
 * Interface for handlers used in EventProcessorImpl
 *
 * @author Kuali Student Team
 */
public interface KSHandler {
    /**
     * Returns true if it handles the event's eventSpec
     * @param event The event
     * @return true if it can handle event's eventSpec
     */
    boolean handlesEvent(KSEvent event);

    /**
     * Processes the event
     * @param event The event to be processed
     * @return  response An object that will handle the response to the event (e.g., success/fail)
     */
    KSHandlerResult processEvent(KSEvent event, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException;

    String getName();

    void setEventProcessor(KSInternalEventProcessor processor);

    List<KSEventType> getEventTypesHandled();
}
