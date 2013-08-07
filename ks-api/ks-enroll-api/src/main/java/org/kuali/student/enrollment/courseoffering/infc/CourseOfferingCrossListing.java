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
     * The course offering code of the cross listing (not the original
     * course it refers to).  This is a concatentation of the subject
     * area/code and the course number suffix
     * This is the alias
     * E.g., ENGL101A, CHEM250
     * 
     * @name Code
     */
    public String getCode();

    /**
     * The subject area/code for this cross listing.
     * Example: In ENGL101A, the subject area/code is ENGL
     * 
     * @name Subject Area
     */
    public String getSubjectArea();
    
    /**
     * Deprecated
     * The department for this cross listing.
     * This will return the same thing as getSubjectOrgId
     * @name Department Org Id
     */
    @Deprecated
    public String getDepartmentOrgId();

    /**
     * This is the ID for that subject code as an org within the Org Service.
     * Subject codes are stored in the Org table as an organization.
     * Note: subject org id and subject area/code should be kept aligned (in case
     * the subject code changes, but its ID stays the same).
     *
     * @impl In general, subject org ID should take precedence over subject area
     * since the subject area can be found as the short name of the org corresponding
     * to the subject org ID.
     *
     * @impl Reference implementation has the ID as ORGID-[subject area]
     * For example, ORGID-ENGL
     * This is not a requirement, but makes it easier to create the Org Id
     *
     * @name Subject Org Id
     */
    public String getSubjectOrgId();

    /**
     * The "extra" portion of the code, which usually corresponds with the most
     * detailed part of the number (i.e., everything besides the subject area/code).
     * See comments in getCode().
     * E.g., from ENGL101A, it's 101A.
     * E.g., from CHEM200, it's 200.
     *
     * @name Course Number Suffix
     */
    public String getCourseNumberSuffix();
}
