/**
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.util.constants;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;

import javax.xml.namespace.QName;

public class CourseRegistrationServiceConstants {
    
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "courseRegistrationService";
    public static final String SERVICE_NAME_LOCAL_PART = CourseRegistrationService.class.getSimpleName();
    // Called as
    // CourseRegistrationService service =
    //     (CourseRegistrationService) GlobalResourceLoader.getService(CourseRegistrationServiceConstants.Q_NAME);
    public static final QName Q_NAME = new QName(NAMESPACE, SERVICE_NAME_LOCAL_PART);

    //dynamic attribute keys
    public static final String ELIGIBILITY_OVERRIDE_TYPE_KEY_ATTR = "kuali.attribute.admin.override.type.key";

}
