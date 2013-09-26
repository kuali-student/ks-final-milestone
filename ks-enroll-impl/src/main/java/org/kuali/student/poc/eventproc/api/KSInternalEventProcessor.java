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
package org.kuali.student.poc.eventproc.api;

import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.poc.eventproc.event.KSEvent;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

/**
 * Internal view of event processor (used for successive event firing and implementation of handlers)
 *
 * @author Kuali Student Team
 */
public interface KSInternalEventProcessor {
    void internalFireEvent(KSEvent event, ContextInfo context)
            throws DataValidationErrorException, PermissionDeniedException, OperationFailedException,
            VersionMismatchException, InvalidParameterException, ReadOnlyException,
            MissingParameterException, DoesNotExistException;
    CourseOfferingService getCoService();
    CourseOfferingSetService getSocService();
    LuiService getLuiService();
    SchedulingService getSchedulingService();
    StateService getStateService();
}
