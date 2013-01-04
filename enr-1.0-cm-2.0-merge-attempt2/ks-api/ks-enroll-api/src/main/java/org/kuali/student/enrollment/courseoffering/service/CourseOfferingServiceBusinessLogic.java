/*
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.student.enrollment.courseoffering.service;

import java.util.List;

import javax.jws.WebParam;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

/**
 * Service Logic that is common regardless of how the implementation is realized. 
 * <p/>
 * i.e. this functionality can be used in both the mock and real implementations.
 * 
 * @author nwright
 */
public interface CourseOfferingServiceBusinessLogic {

    public SocRolloverResultItemInfo rolloverCourseOffering(String sourceCoId, String targetTermId, List<String> optionKeys, ContextInfo context)
            throws AlreadyExistsException,
            DataValidationErrorException, DoesNotExistException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    public CourseOfferingInfo updateCourseOfferingFromCanonical(String courseOfferingId, List<String> optionKeys, ContextInfo context)
            throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException;

    public List<ValidationResultInfo> validateCourseOfferingFromCanonical(CourseOfferingInfo courseOfferingInfo, List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This is a bulk create method for generateRegistrationGroupsForAOC().  Instead of working on a single Activity Offering Cluster it will
     * work on all of the AOC's of the format offering specified.
     * 
     * @param formatOfferingId The identifier of the format offering to generate registration groups for.
     * @param contextInfo Context information containing the principalId and locale information about the caller of service operation 
     * @return status of the operation (success, failed) 
     * @throws DoesNotExistException The formatOfferingId does not refer to an existing FormatOffering.
     * @throws InvalidParameterException The formatOfferingId or context is invalid.
     * @throws MissingParameterException the formatOfferingId or context is missing.
     * @throws OperationFailedException unable to complete request, can also occur when verification of any AOC in the format offering fails.
     * @throws PermissionDeniedException authorization failure
     * @throws DataValidationErrorException verification of any of the underlying Activity Offering Cluster's failed.
     */
	public StatusInfo generateRegistrationGroupsForFormatOffering(
			String formatOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, DataValidationErrorException;
	
	/**
     * Generates all possible registration groups for the Activity Offering
     * Cluster
     *
     * @param activityOfferingClusterId identifier of the Activity Offering
     *                                  Cluster
     * @param contextInfo               Context information containing the
     *                                  principalId and locale information about
     *                                  the caller of service operation
     * @return status of the operation (success, failed)
	 * @throws DoesNotExistException     activityOfferingClusterId does not
     *                                   exist
	 * @throws DataValidationErrorException verification of the Activity Offering Cluster failed.
	 * @throws InvalidParameterException invalid contextInfo
	 * @throws MissingParameterException activityOfferingClusterId or
     *                                   contextInfo is missing or null
	 * @throws OperationFailedException  unable to complete request
	 * @throws PermissionDeniedException an authorization failure has occurred
     * @impl Does 'delta' generation: Creates only new RGs
     */
    public StatusInfo generateRegistrationGroupsForCluster(@WebParam(name = "activityOfferingClusterId") String activityOfferingClusterId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

}