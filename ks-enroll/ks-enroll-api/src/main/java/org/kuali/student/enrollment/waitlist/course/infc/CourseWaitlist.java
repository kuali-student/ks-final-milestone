/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.waitlist.course.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Represents a list of students who wish to register to the reg group but
 * cannot because of seat unavailability. A waitlist is a Queue at the class I
 * level. As more seats become available the students in the waitlist are moved
 * to the course. The waitlist entries are LPRs which are in a state denoting
 * that the student is in a waitlist. There are three waitlist-clearing
 * strategies - automatic, semi-automatic and manual which are part of the
 * CourseWaitlistOption. A single waitlist is created for a particular course
 * offering. The waitlist may vary by each reg group in the course offering;
 * this is captured by the CourseWaitlistOption.
 * 
 * @Author tom
 * @Since Tue May 10 14:22:34 EDT 2011
 */

public interface CourseWaitlist extends IdEntity {

    /**
     * Name: Offering Id The Id of the related offering. Note: don't yet know
     * what kind of offering this is, but it does map to a LUI Id.
     */
    public String getCourseOfferingId();

    /**
     * Gets all the course waitlist entries in the wailtist.
     * 
     * @return
     */
    public List<String> getCourseWailtistEntryIds();


}
