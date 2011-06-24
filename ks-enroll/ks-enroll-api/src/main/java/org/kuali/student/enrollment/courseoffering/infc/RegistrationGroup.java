/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.enrollment.courseoffering.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Registration group are the physical entities that students will try to register into. A RegistrationGroup is used to group
 * individual activity offerings based on the canonical format.
 * 
 * @author Kamal
 */

public interface RegistrationGroup extends HasEffectiveDates, IdEntity {

    /**
     * Canonical format to which this registration group belong to.  
     * @name Format Id
     */
    public String getFormatId();
    
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
    public Boolean hasWaitlist();

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
}
