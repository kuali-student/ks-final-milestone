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

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
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
            InvalidParameterException, 
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

	public List<BulkStatusInfo> generateRegistrationGroupsForFormatOffering(
			String formatOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, DataValidationErrorException;
	
    public List<BulkStatusInfo> generateRegistrationGroupsForCluster(String activityOfferingClusterId, ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

}