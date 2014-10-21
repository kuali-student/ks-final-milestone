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
 */
package org.kuali.student.enrollment.class1.state.impl;


import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.class1.state.service.StateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class SOCStateHelperImpl implements StateHelper {
	
	private static final Logger log = LoggerFactory.getLogger(SOCStateHelperImpl.class);
	
    private CourseOfferingSetService courseOfferingSetService;

    public SOCStateHelperImpl(){}

    protected CourseOfferingSetService getCourseOfferingSetService(){
        if (courseOfferingSetService == null){
             courseOfferingSetService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE, CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return  courseOfferingSetService;
    }

    @Override
    public StatusInfo updateState(String entityId, String nextStateKey, ContextInfo context) {
        StatusInfo si = new StatusInfo();
        try {
            si = getCourseOfferingSetService().changeSocState(entityId, nextStateKey, context);
            si.setSuccess(true);
        } catch (Exception e) {
        	String message = String.format("Failed to updateState for (entityId=%s, nextStateKey=%s", entityId, nextStateKey);
        	log.warn (message, e);
        	si.setMessage(message);
            si.setSuccess(false);
        }
        return si;
    }

    @Override
    public String getStateKey(String entityId, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
        SocInfo soc = getCourseOfferingSetService().getSoc(entityId, context);
        return soc.getStateKey();
    }
}
