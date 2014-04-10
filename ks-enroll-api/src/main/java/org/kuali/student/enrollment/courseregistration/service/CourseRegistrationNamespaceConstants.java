/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.enrollment.courseregistration.service;

import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

/**
 * Course registration Service Constants
 *
 * @author nwright
 * @see LuiServiceConstants
 */
public class CourseRegistrationNamespaceConstants {

    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "courseRegistration";
    public static final String SERVICE_NAME_LOCAL_PART = CourseRegistrationService.class.getSimpleName();

    public static final String REF_OBJECT_URI_COURSE_REGISTRATION = NAMESPACE + "/" + CourseRegistrationInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_ACTIVITY_REGISTRATION = NAMESPACE + "/" + ActivityRegistrationInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_REGISTRATION_REQUEST = NAMESPACE + "/" + RegistrationRequestInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_REGISTRATION_REQUEST_ITEM = NAMESPACE + "/" + RegistrationRequestItemInfo.class.getSimpleName();
   }
