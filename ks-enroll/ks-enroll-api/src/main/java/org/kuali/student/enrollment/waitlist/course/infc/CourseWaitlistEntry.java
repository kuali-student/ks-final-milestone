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

import java.util.Date;
import org.kuali.student.r2.common.infc.Relationship;

/**
 * Represents a single student in the waitlist. Contains information such as
 * student, the position in the waitlist. As more seats become available the
 * students in the waitlist are moved to the course. The waitlist entries are
 * LPRs which are in a state denoting that the student is in a waitlist. There
 * are three waitlist-clearing strategies - automatic, semi-automatic and
 * manual.
 * 
 * @Author tom
 * @Since Tue May 10 14:22:34 EDT 2011
 */

public interface CourseWaitlistEntry extends Relationship {

    /**
     * Returns the student id in waitlist entry waitlisted.
     */
    public String getStudentId();

    /**
     * Course waitlist option for this waitlist entry. Represents the reg group
     * the student is registering for.
     * 
     * @return
     */
    public String getCourseWaitlistOptionId();

    /**
     * Name: Date The time this entry was created.
     */
    public Date getDate();

    /**
     * Returns the position of this entry in the waitlist for a
     * particular waitlist option .
     */
    public Integer getPosition();

    /**
     * Returns true if the student has checked in to the waitlist. Used to track
     * if the student is still interested in being in the wailist.
     * 
     * @return
     */
    public Boolean getHasCheckedIn();

}
