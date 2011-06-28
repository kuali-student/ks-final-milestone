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
package org.kuali.student.r2.common.util.constants;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;

/**
 * Course Offering Service Constants
 * @see LuiServiceConstants
 *
 * @author nwright
 */
public class CourseOfferingServiceConstants {

    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "courseOffering";
    public static final String REF_OBJECT_URI_COURSE_OFFERING = NAMESPACE + "/" + CourseOfferingInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_ACTIVITY_OFFERING = NAMESPACE + "/" + ActivityOfferingInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_REGISTRATION_GROUP = NAMESPACE + "/" + RegistrationGroupInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_SEAT_POOL_DEFINITION = NAMESPACE + "/" + SeatPoolDefinitionInfo.class.getSimpleName();
    
    // The type/states are defined in LuiServiceConstants.java
}
