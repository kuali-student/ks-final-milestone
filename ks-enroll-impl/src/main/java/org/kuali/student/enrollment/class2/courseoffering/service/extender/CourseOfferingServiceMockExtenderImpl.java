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
 * Created by Charles on 11/13/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.extender;

import org.kuali.student.enrollment.class2.courseofferingset.service.facade.RolloverAssist;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;

import java.util.List;
import java.util.Map;

/**
 * For purposes of unit tests which insist that CourseOfferingServiceBusinessLogic must have all its
 * resources wired in even if it doesn't use it, this mock (which does nothing) helps to allow the
 * wiring to succeed and the unit tests to pass.
 *
 * Note: copyActivityOffering had to be partly implemented to pass the tests
 *
 * @author cclin
 */
public class CourseOfferingServiceMockExtenderImpl implements CourseOfferingServiceExtender {

    @Override
    public List<String> getActivityTypesForFormatId(String id, ContextInfo context) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        throw new UnsupportedOperationException("getActivityTypesForFormatId");
    }

    @Override
    public List<ValidationResultInfo> verifyRegistrationGroup(String registrationGroupId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("verifyRegistrationGroup");
    }

    @Override
    public List<ValidationResultInfo> verifyRegistrationGroup(List<String> aoIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("verifyRegistrationGroup2");
    }

    @Override
    public Map<String, String> computeAoIdToAoCodeMapByCourseOffering(String courseOfferingId, ContextInfo context) throws OperationFailedException {
        throw new UnsupportedOperationException("computeAoIdToAoCodeMapByCourseOffering");
    }

    @Override
    public ActivityOfferingInfo copyActivityOffering(String operation,
                                                     ActivityOfferingInfo sourceAo,
                                                     CourseOfferingService coService,
                                                     FormatOfferingInfo targetFo,
                                                     String targetTermIdCustom,
                                                     ContextInfo context,
                                                     List<String> optionKeys) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {
        ActivityOfferingInfo targetAo = new ActivityOfferingInfo(sourceAo);
        targetAo.setId(null);
        if (targetFo != null) {
            targetAo.setFormatOfferingId(targetFo.getId());
        }
        if (targetTermIdCustom != null) {
            targetAo.setTermId(targetTermIdCustom);
        }
        targetAo.getScheduleIds().clear();
        ActivityOfferingInfo resultAo = coService.createActivityOffering(targetAo.getFormatOfferingId(), targetAo.getActivityId(), targetAo.getTypeKey(), targetAo, context);
        return resultAo;
    }

    @Override
    public ActivityOfferingInfo copyActivityOffering(ActivityOfferingInfo sourceAo, CourseOfferingService coService, ContextInfo context) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {
        throw new UnsupportedOperationException("copyActivityOffering2");
    }

    @Override
    public ActivityOfferingInfo createTargetActivityOfferingForRollover(ActivityOfferingInfo sourceAo, FormatOfferingInfo targetFo, String targetTermIdCustom, RolloverAssist rolloverAssist, String rolloverId, List<String> optionKeys, CourseOfferingService coService, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException {
        throw new UnsupportedOperationException("createTargetActivityOfferingForRollover");
    }
}
