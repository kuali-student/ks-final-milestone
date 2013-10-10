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

package org.kuali.student.enrollment.bundledoffering.infc;

import org.kuali.student.r2.common.infc.IdEntity;

import java.util.List;


/**
 * Bundled Offering is a set of Registration Groups for which a
 * student is registered together. A Bundled Offering is an offering
 * of a canonical CourseBundle.
 */

public interface BundledOffering
    extends IdEntity {

    /**
     * The CourseBundle identifier from which this BundledOffering is
     * offered.
     * 
     * @return the course bundle Id
     * @name Course Bundle Id
     * @required
     * @readOnly
     */
    public String getCourseBundleId();

    /**
     * The Term identifier in which this BundledOffering is offered.
     *
     * @return the term Id
     * @name Term Id
     * @required
     * @readOnly
     */
    public String getTermId();

    /**
     * Identifies the number of a bundled offering as reflected in the
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
     * @name Bundled Offering Code
     * @impl initialially this is copied from the course catalog code but then
     *       is subsequently stored in the lui as lui.officialIdentifier.code
     */
    public String getBundledOfferingCode();

    /**
     * Identifies the department and/subject code of the course as
     * reflected in the course catalog. Initially copied from the
     * course catalog but then, depending on the configuration it may
     * be updatable. In most configurations this should not be
     * updatable. Often used in the calculation of the courseCode.
     * 
     * @name Subject Area Org Id
     * @impl initially copied from the canonical course bundle but
     *       then stored in the Lui as lui.officialIdentifier.division
     */
    public String getSubjectAreaOrgId();

    /**
     * A suffix of the course number as reflected in the college
     * catalog. This is the "number" portion of the course
     * code. Initially copied from the course catalog but then,
     * depending on the configuration it may be updatable. This field
     * is often configured to be updatable but the updates typically
     * simply append something like an "O" for on-line to it to
     * differentiate multiple course offerings for the same
     * course. Often used in the calculation of the courseCode.
     * 
     * @name Bundled Offering Code Suffix
     * @impl initially copied from the canonical course bundle but
     *       then stored in the Lui as
     *       lui.officialIdentifier.suffixCode
     */
    public String getBundledOfferingCodeSuffix();

    /**
     * The identifiers of the administrative organizations for the
     * bundled offering. This organization is typically the units
     * deployment organization as the content is managed within the
     * individual course offerings.
     *
     * @return the Id of the administrative organization
     * @name Admin Org Ids
     * @impl initalized from canonical course bundle units deployment
     *       but then stored in lui.unitsDeployment
     */
    public List<String> getAdminOrgIds();

    /**
     * The identifiers for the FormatOfferings to be included in this
     * bundled offering. These determine the valid RegistrationGroups
     * and may be specified earlier in the offering process.
     *
     * @return the Ids of the format offerings
     * @name Format Offering Ids
     * @impl stored as LuiLuiRelations
     */
    public List<String> getFormatOfferingIds();

    /**
     * The identifiers for the RegistrationGroups included in this
     * bundled offering. The RegistrationGroups are constrained by the
     * FormatOfferings.
     *
     * @return the Ids of the registration groups
     * @name Registration Group Ids
     * @impl stored as LuiLuiRelations
     */
    public List<String> getRegistrationGroupIds();
}
