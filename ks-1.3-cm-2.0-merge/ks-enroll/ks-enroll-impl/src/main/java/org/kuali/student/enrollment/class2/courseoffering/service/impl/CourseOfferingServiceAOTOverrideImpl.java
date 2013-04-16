/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by Charles on 5/29/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * We are missing AO types in the DB, so this mocks it out.
 *
 * @author Kuali Student Team
 */
public class CourseOfferingServiceAOTOverrideImpl extends CourseOfferingServiceImpl {
    @Override
    @Transactional(readOnly = true)
    public List<TypeInfo> getActivityOfferingTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<TypeInfo> activityOfferingTypes = new ArrayList<TypeInfo>();
        for (String key: LuiServiceConstants.ALL_ACTIVITY_TYPES) {
            TypeInfo typeInfo = new TypeInfo();
            typeInfo.setKey(key);
            activityOfferingTypes.add(typeInfo);
        }
        return activityOfferingTypes;
    }
}
