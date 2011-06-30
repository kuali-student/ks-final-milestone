/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.enrollment.courseoffering.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.TimeAmount;

/**
 * Registration group are the physical entities that students will try to register into. A RegistrationGroup is used to group
 * individual activity offerings based on the canonical format.
 * 
 * @author Kamal
 */

public interface RegistrationGroup extends IdEntity {

    /**
     * Activity Offerings for the registration group. This list should be constrained by the canonical 
     * format and the activity offerings listed in the course offering
     * @name ActivityOffering Ids
     */
    public List<String> getActivityOfferingIds();
    
    /**
     * Course offering for this registration group
     * @name CourseOffering Id
     */    
    public String getCourseOfferingId();
    
    /**
     * Canonical format to which this registration group belong to.  
     * @name Format Id
     */
    public String getFormatId();
    
    /**
     * Uniquely identifies an instance of the course for the purposes of registration
     * @name Registration Code
     */
    public String getRegistrationCode();
    
    /**
     * Indicates that the entire course offering is an Honors Course
     * @name Is Honors Offering
     */
    public Boolean getIsHonorsOffering();
    
    /**
     * Total maximum number of "seats" or enrollment slots that can be filled for the offering. Constrained by smallest activity enrollment
     * @name Maximum Enrollment
     */
    public Integer getMaximumEnrollment();

    /** 
     * Total minimum number of seats that must be filled for the offering not to be canceled. Constrained by smallest activity enrollment
     * @name Minimum  Enrollment
     */
    public Integer getMinimumEnrollment();
    
    /**
     * Indicates whether a RegistrationGroup has a waitlist
     * @name Has Waitlist
     */
    public Boolean getHasWaitlist();

    /**
     * Indicates the type of waitlist as it relates to processing students on and off
     * @name Waitlist Type
     */
    public String getWaitlistTypeKey();

    /**
     * Maximum number of students to be allowed on the wait list
     * @name Waitlist Maximum
     */
    public Integer getWaitlistMaximum();    

    /**
     * Indicates if the waitlist requires checkin
     * @name Is Waitlist Checkin Required
     */
    public Boolean getIsWaitlistCheckinRequired();
    
    /**
     * Frequency for the waitlist checkin
     * @name Waitlist Checkin Frequency 
     */
    public TimeAmount getWaitlistCheckinFrequency();

}
