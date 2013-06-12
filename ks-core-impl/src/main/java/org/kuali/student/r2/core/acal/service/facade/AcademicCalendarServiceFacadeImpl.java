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

import org.apache.log4j.Logger;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;

import javax.annotation.Resource;

/**
 * Provides additional functionality on top of the Acal Service.
 *
 * @author Kuali Student Team
 */
public class AcademicCalendarServiceFacadeImpl implements AcademicCalendarServiceFacade {
    private static final Logger LOGGER = Logger.getLogger(AcademicCalendarServiceFacadeImpl.class);

    @Resource(name="acalService")
    private AcademicCalendarService acalService;

    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }

    @Override
    public void makeTermOfficial(String termId, ContextInfo contextInfo) {
        throw new UnsupportedOperationException("makeTermOfficial not yet implemented");
    }

    @Override
    public void deleteTermCascaded(String termId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("deleteTermCascaded not yet implemented");
    }

    @Override
    public void deleteCalendarCascaded(String academicCalendarKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("deleteCalendarCascaded not yet implemented");
    }
}
