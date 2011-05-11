/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.grading.infc;

import org.kuali.student.enrollment.grading.dto.AssignedGradeInfo;
import org.kuali.student.enrollment.grading.dto.CalculatedGradeInfo;
import org.kuali.student.enrollment.grading.dto.CreditsEarnedInfo;
import org.kuali.student.r2.common.infc.HasAttributesAndMeta;
import org.kuali.student.r2.common.infc.HasId;
import org.kuali.student.r2.common.infc.HasPrimaryKey;


/**
 * Information about a Grading Roster Entry
 *
 * @author Kuali Student Team (Kamal)
 */ 

public interface GradeRosterEntry extends HasId, HasPrimaryKey, HasAttributesAndMeta {

    public String getStudentId();
    
    public String getActivityOfferingId();
        
    /**
     * Name: Grade Roster Id
     * Id of the grade roster to which this roster entry belongs
     * 
     * @return
     */
    public String getGradeRosterId();
    
    /**
     * Name: Assigned Grade
     * Grade assigned by the grader in a roster entry
     * 
     * @return
     */
    public AssignedGradeInfo getAssignedGrade();

    /**
     * Name: Calculated Grade
     * Grade calculated based on the assigned grade in the roster entry
     * 
     * @return
     */
    public CalculatedGradeInfo getCalculatedGrade();

    /**
     * Name: Credits Earned
     * Credits earned in the course offering. 
     * 
     * @return
     */
    public CreditsEarnedInfo getCreditsEarned();    
}
