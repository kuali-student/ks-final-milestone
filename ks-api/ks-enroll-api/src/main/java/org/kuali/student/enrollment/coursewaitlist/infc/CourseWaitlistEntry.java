/*
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.coursewaitlist.infc;

import java.util.Date;

import org.kuali.student.r2.common.infc.Relationship;

/**
 * Represents a single student in the course waitlist. Contains information such
 * as student id, the waitlist option (reg group id and clearing strategy) and
 * the student's position in the waitlist. As more seats become available the
 * students in the waitlist are moved to the course.
 * <P>
 * Implementation Notes: 
 * It is represented by an LPR at Class I level, when a student actually
 * registers for the course the LPR is a RegGroupRegistration
 * 
 * @Author sambit
 * @Since Tue May 10 14:22:34 EDT 2011
 */

public interface CourseWaitlistEntry extends Relationship {

    /**
     * Returns the student id in waitlist entry waitlisted.
     * @readOnly on updates
     */
    public String getStudentId();
    
    /**
     * Returns the position of this entry in the waitlist for a particular
     * waitlist option.
     * 
     * This is not directly updatable on the data object, use service operations
     * to change the student's position in the list
     * 
     * @readOnly
     */
    public Integer getPosition();
    
    /**
     * Returns true if the student has checked in to the waitlist. Used to track
     * if the student is still interested in being in the wailist.
     * 
     */
    public Date getLastCheckedIn();
    
    /**
     * Returns the reg group id for this waitlist entry. A waitlist entry should
     * always be on tied to a single reg group. 
     * 
     */
    public String getRegistrationGroupId();
    
    /**
     * 
     * Course offering id that contains the 
     * 
     * @readOnly on updates
     */
    public String getCourseOfferingId();
    

    //  activity offering blocked Id
}
