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
 * Created by Charles on 6/10/13
 */
package org.kuali.student.r2.core.acal.service.facade;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

/**
 * We expect to replace the void
 *
 * @author Kuali Student Team
 */
public interface AcademicCalendarServiceFacade {
    /**
     * Changes the state of the term and any ancestor term official, as well as the calendar.
     * KSENROLL-7251
     * @param termId
     * @param contextInfo
     */
    void makeTermOfficial(String termId, ContextInfo contextInfo);

    /**
     * Cascaded version of delete.  (We need to define this more formally).  We don't delete non-official terms
     * since they could have COs.
     * @param termId
     * @param context
     */
    void deleteTermCascaded(String termId, ContextInfo context) throws
        DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
        PermissionDeniedException;

    /**
     * deleteCalendarCascaded
     * @param academicCalendarKey ID for academic calendar
     * @param context
     */
    void deleteCalendarCascaded(String academicCalendarKey, ContextInfo context) throws
            DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;
}
