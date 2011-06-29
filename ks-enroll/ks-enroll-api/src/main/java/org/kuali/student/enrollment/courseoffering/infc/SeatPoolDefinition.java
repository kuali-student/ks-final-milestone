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

/**
 * Information about a single seat pool that can be associated with either a course offering or registration group. Seat
 * pools provides a handle for statements that will be used to created enrollment restrictions based on capacity. Seat pools
 * are ordered using rank to enable the most restrictive pools to be filled first. 
 * 
 * @author Kamal
 */

public interface SeatPoolDefinition extends  IdEntity {

    /**
     * Course offering Id to which the seat pool is linked to. Course Offering Id should be set only if the seat 
     * pool applies across all the registration group in the course offering. 
     * @name Course Offering Id
     */
    public String getCourseOfferingId();
    
    /**
     * List of registration groups that seat pool applies to 
     * @name Registration Group Ids
     */
    public List<String> getRegistrationGroupIdList();
    
    /** 
     * Count Maximum seats available through this seat pool.
     * @name Maximum Seat
     */
    public Integer getMaximumSeatCount();

    /**
     * Indicates the order in which this seat pool will be processed during registration. During registration
     * students should be assigned to a seat pool with the lowest possible rank to which he or she meets the restriction
     * requirements.
     * @name Processing Priority
     */
    public Integer getProcessingPriority();
}
