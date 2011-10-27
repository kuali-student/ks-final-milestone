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

package org.kuali.student.enrollment.grading.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Information about a Grading Roster
 * 
 * @author Kuali Student Team (Kamal)
 */

public interface GradeRoster extends IdEntity {

    /**
     * This method gets the list of grade roster entries which are associated
     * with this grade roster.
     * 
     * @return
     */
    List<String> getGradeRosterEntryIds();

    /**
     * This method gets the list of graders that are associated with this grade
     * roster
     * 
     * @return
     */
    List<String> getGraderIds();

    /**
     * This method gets the course offering that the roster belongs to. A course
     * offering might contain one-many rosters
     * 
     * @return
     */
    public String getCourseOfferingId();

    /**
     * This method returns a list of activity offering ids associated with this
     * roster.
     * 
     * @return
     */
    public List<String> getActivityOfferingIds();

}
