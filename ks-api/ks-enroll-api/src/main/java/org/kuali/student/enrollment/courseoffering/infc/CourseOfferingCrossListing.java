/*
 * Copyright 2010 The Kuali Foundation 
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

package org.kuali.student.enrollment.courseoffering.infc;

import org.kuali.student.r2.common.infc.IdNamelessEntity;

public interface CourseOfferingCrossListing 
    extends IdNamelessEntity {

    /**
     * The code for this cross listing.
     * 
     * @name Code
     */
    public String getCode();

    /**
     * The subject area for this cross listing.
     * 
     * @name Subject Area
     */
    public String getSubjectArea();
    
    /**
     * The department for this cross listing.
     * 
     * @name Department Org Id
     */
    public String getDepartmentOrgId();

    /**
     * The "extra" portion of the code, which usually corresponds with
     * the most detailed part of the number.
     *
     * @name Course Number Suffix
     */
    public String getCourseNumberSuffix();
}
