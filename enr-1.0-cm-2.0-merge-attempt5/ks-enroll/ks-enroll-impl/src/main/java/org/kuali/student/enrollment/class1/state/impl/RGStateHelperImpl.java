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
 * Created by chongzhu on 12/4/12
 */
package org.kuali.student.enrollment.class1.state.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.core.class1.state.service.StateHelper;

import javax.xml.namespace.QName;

/**
 * @version 2.0
 *
 * @author Kuali Student Team
 */
public class RGStateHelperImpl  implements StateHelper {
    CourseOfferingService courseOfferingService;

    protected CourseOfferingService getCourseOfferingService(){
        if (courseOfferingService == null){
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return  courseOfferingService;
    }

    @Override
    public StatusInfo updateState(String id, String nextStateKey, ContextInfo contextInfo) {
        StatusInfo si = null;
        try {
            si = getCourseOfferingService().updateRegistrationGroupState(id, nextStateKey, contextInfo);
            si.setSuccess(true);
        } catch (Exception e) {
            si.setSuccess(false);
        }
        return si;
    }

    @Override
    public String getStateKey(String entityId, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
        RegistrationGroupInfo rg = getCourseOfferingService().getRegistrationGroup(entityId, context);
        return rg.getStateKey();
    }

}
