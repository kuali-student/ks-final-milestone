/*
 * Copyright 2013 The Kuali Foundation 
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

package org.kuali.student.lum.coursebundle.infc;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.HasEffectiveDates;

import java.util.List;


/**
 * A CourseBundle is a canonical course that represents a learning
 * unit spanning multiple Courses. The CourseBundle is offered as a
 * BundledOffering. This may be a "shell" that is offered as a
 * BundledOffering for a specific Term or this may define the set of
 * canonical Courses whose offerings are intended to be included as
 * part of the BundledOffering.
 */

public interface CourseBundle
    extends IdEntity, HasEffectiveDates {

    /**
     * Identifies the number of a course bundled as reflected in the
     * course catalog.  This typically must be unique across all
     * courses offered during that term. If the user wants to create
     * two separate offerings for the same course they must modify
     * this code to make it unique. For example: An on-line offering
     * of the course might have an "O" appended to it to distinguish
     * it from the face to face offering, i.e. ENG101 and ENG101O
     * Initially copied from the course catalog but then, depending on
     * the configuration it may be updatable. Often this field is
     * configured so that it is not not directly updatable but rather
     * is calculated from it's two constituent parts, the subject area
     * and the course number suffix. For example: Subject Area = "ENG"
     * and Suffix = "101" then code = "ENG101"
     *
     * @name Course Bundle Code
     * @impl clu.officialIdentifier.code
     */
    public String getCourseBundleCode();

    /**
     * A suffix of the course number as reflected in the course
     * catalog. This is the "number" portion of the course
     * code. Initially copied from the course catalog but then,
     * depending on the configuration it may be updatable. This field
     * is often configured to be updatable but the updates typically
     * simply append something like an "O" for on-line to it to
     * differentiate multiple course offerings for the same
     * course. Often used in the calculation of the course code.
     * 
     * @return the code suffix
     * @name Course Bundle Code Suffix
     * @impl clu.officialIdentifier.suffixCode
     */
    public String getCourseBundleCodeSuffix();

    /**
     * The first academic time period that this CourseBundle would be
     * effective.
     *
     * @return the start term Id
     * @name Start Term Id
     * @name clu.expectedFirstAtp
     */
    public String getStartTermId();

    /**
     * The last academic time period that this CourseBundle would be
     * effective.
     *
     * @return the end term Id
     * @name End Term
     * @name clu.LastAtp
     */
    public String getEndTermId();

    /**
     * Identifies the department and/subject code of the course bundle
     * as reflected in the course catalog. Initially copied from the
     * course catalog but then, depending on the configuration it may
     * be updatable. In most configurations this should not be
     * updatable. Often used in the calculation of the courseCode.
     * 
     * @return the org Id
     * @name Subject Area Org Id
     * @impl clu.studySubjectSrea
     */
    public String getSubjectAreaOrgId();
    
    /**
     * The identifier of the administrative organization for the
     * course bundle. This organization is typically the units
     * deployment organization as the content is managed within the
     * individual course offerings.
     *
     * @return the Ids of the administrative organizations
     * @name Admin Org Ids
     * @impl clu.adminOrgs
     */
    public List<String> getAdminOrgIds();

    /**
     * The identifiers for the Courses to be included in this course
     * bundle. 
     *
     * @return the Ids of the Courses
     * @name Course Ids
     * @impl stored as CluCluRelations
     */
    public List<String> getCourseIds();
}
