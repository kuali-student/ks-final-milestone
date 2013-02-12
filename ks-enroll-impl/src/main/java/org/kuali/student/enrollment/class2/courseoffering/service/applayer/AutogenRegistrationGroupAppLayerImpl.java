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
 * Created by Charles on 2/5/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.applayer;

import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceImpl;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
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
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class AutogenRegistrationGroupAppLayerImpl implements AutogenRegistrationGroupAppLayer {
    CourseOfferingService coService;
    ContextInfo contextInfo;

    @Override
    public Map<String, Object> addActivityOfferingFirstTime(ActivityOfferingInfo aoInfo, String foId)
            throws PermissionDeniedException, MissingParameterException,
            InvalidParameterException, OperationFailedException,
            DoesNotExistException, ReadOnlyException, DataValidationErrorException {
        List<ActivityOfferingInfo> aoInfos
                = coService.getActivityOfferingsByFormatOffering(foId, contextInfo);
        if (!aoInfos.isEmpty()) {
            // Should only apply when there are no AOs, otherwise, there should be an AOC to use
            throw new OperationFailedException("Format offering, " + foId + ", has AOs already attached");
        }
        // Create the AO
        ActivityOfferingInfo created =
                coService.createActivityOffering(foId, aoInfo.getActivityId(), aoInfo.getTypeKey(), aoInfo, contextInfo);
        // Create a default AOC

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
