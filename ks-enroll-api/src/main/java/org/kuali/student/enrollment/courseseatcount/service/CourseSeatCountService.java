/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by Charles on 2/11/14
 */
package org.kuali.student.enrollment.courseseatcount.service;

import org.kuali.student.enrollment.courseseatcount.infc.SeatCount;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import java.util.List;

/**
 * Methods for obtaining seat counts for certain LUIs
 *
 * @author Kuali Student Team
 */
public interface CourseSeatCountService {
    /**
     *
     * @param activityOfferingId The ID of an activity offering where you want to obtain
     *                           a seat count
     * @param context The context info
     * @return A seat count object with the relevant seat counts
     * @throws DoesNotExistException if the AO id does not exist
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    SeatCount getSeatCountForActivityOffering(String activityOfferingId,
                                              ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;
    /**
     * A bulk operation to return the seat counts of zero or more activity offering ids
     * @param activityOfferingIds A list of activity offering IDs
     * @param context The context info
     * @return A seat count object with the relevant seat counts
     * @throws DoesNotExistException if any one of the AO id does not exist, then this
     *                               exception is thrown
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    List<SeatCount> getSeatCountsForActivityOfferings(List<String> activityOfferingIds,
                                                      ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;
}
