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
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.common.util.security.ContextUtils;

import java.util.Map;

/**
 * This class provides an Inquirable implementation for Activity Offerings
 *
 * @author Kuali Student Team
 */
public class ActivityOfferingInquirableImpl extends InquirableImpl {

    @Override
    public ActivityOfferingInfo retrieveDataObject(Map<String, String> parameters) {
        try {
            ActivityOfferingInfo activityOfferingInfo = getCourseOfferingService().getActivityOffering(parameters.get(ActivityOfferingConstants.ACTIVITYOFFERING_ID), ContextUtils.createDefaultContextInfo());
            return activityOfferingInfo;
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }

    public CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }
}
