/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.enrollment.courseoffering.infc;

import java.util.List;

import org.kuali.student.enrollment.acal.infc.Term;
import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.IdEntity;

/**
 * 
 * @author Kamal
 */

public interface CourseOffering extends HasEffectiveDates, IdEntity {

    /**
     * Academic term of course offering
     * @name Term
     */
    public Term getTerm();
    
    /**
     * A unique identifier assigned to all approved course offerings that exist in the catalog
     * @name Course Id
     */
    public String getCourseId();
    
    /**
     * Identifies the department and/subject code of the course as reflected in the course catalog.
     * This should correspond to the value in the canonical course and should not be updatable.
     * @name Subject Area
     */
    public String getSubjectArea();
    
    /**
     * Indicates that the entire course offering is an Honors Course
     * @name Is Honors Offering
     */
    public Boolean getIsHonorsOffering();
    
    /******** Assessment Information ***************/
    /**
     * The options/scales that indicate the allowable grades that can be awarded.
     * If the value is set here then the Clu must have a grading option set on the
     * canonical activity
     * 
     * @name: Grading Options
     */
    public List<String> getGradingOptions();
    
    /******** Personnel Information *****************/
    
    
    
    /********* Organization Information **************/
    
    /**
     * Organization(s) that is responsible for the delivery - and all associated logistics - of the course
     * @name Units Deployment
     */
    public List<String> getUnitsDeployment();
    
    /**
     * Organization(s) that is responsible for the academic content of the course as approved in its canonical form
     * @name Units Content Owner
     */
    public List<String> getUnitsContentOwner();
    
    
    /********** Final Exam Information *****************/
    
    /**
     * Indicates whether a final exam is to be given
     * @name Final Exam Status
     */
    public Boolean getFinalExamStatus();
    
    /*********** Waitlist *****************************/
    
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
