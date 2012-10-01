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

import java.util.List;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Registration group are the physical entities that students will try
 * to register into. A RegistrationGroup is used to group individual
 * activity offerings based on the canonical format.
 * 
 * @author Kamal
 */

public interface RegistrationGroup 
    extends IdEntity {

    /**
     * Canonical format to which this registration group belong to.  
     *
     * @name Format Id
     * @impl This maps the the version dependent id of the format
     *       clu. Stored as cluId in Lui.
     * @required
     * @readOnly
     */
    public String getFormatOfferingId();

    /**
     * Course offering for this registration group.
     *
     * @name CourseOffering Id
     * @required
     * @readOnly
     * @impl Maps to the lui Id of the courseOffering retrieved from
     *       luiluirelation of type courseoffering to registration
     *       group.
     */    
    public String getCourseOfferingId();

    /**
     * Academic term the registration group is being offered
     * in. Should be same as CourseOffering unless changed, then must
     * be nested term of courseOffering.
     *
     * @name Term id
     * @readOnly
     * @required
     * @impl maps to Lui.getAtpId()
     */
    public String getTermId();

    /**
     * Uniquely identifies an instance of the course for the purposes
     * of registration.
     *
     * @name Registration Code
     * @impl maps to lui code in Lui
     */
    public String getRegistrationCode();

    /**
     * Activity Offerings for the registration group. This list should
     * be constrained by the canonical format and the activity
     * offerings listed in the course offering.
     *
     * @name ActivityOffering Ids
     * @impl Maps to the lui Ids of the activityOffering retrieved
     *       from luiluirelation of type activityOffering to
     *       registration group.
     */
    public List<String> getActivityOfferingIds();            
    
    /**
     * Indicates that the entire course offering is an Honors Course.
     *
     * @name Is Honors Offering
     * @readOnly
     */
    public Boolean getIsHonorsOffering();

    /**
     * Total maximum number of "seats" or enrollment slots that can be
     * filled for the offering. Constrained by smallest activity.
     *
     * @name Maximum Enrollment
     * @readOnly
     * @impl maps to Lui.maximumEnrollment
     */
    public Integer getMaximumEnrollment();

    /**
     * Tests if this registration group wa sthe product of an
     * automatic generation. manually created registration groups
     * return false for this.
     *
     * @name Is Generated
     */
    public Boolean getIsGenerated();
}
