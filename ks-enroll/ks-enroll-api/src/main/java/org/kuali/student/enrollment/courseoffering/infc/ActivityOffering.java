/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.enrollment.courseoffering.infc;

import java.util.Date;
import java.util.List;

import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.lum.lrc.infc.ResultComponent;

/**
 * Individual activity offerings correspond to events in a scheduling system, each with a meeting pattern.
 * ActivityOffering map to 
 * @author Kamal
 */

public interface ActivityOffering extends IdEntity {

    /**
     * Course Oferrings to which this activity offering belongs to.
     * @name CourseOffering Ids
     */
    public List<String> getCourseOfferingIds();
        
    /**
     * Registration Groups to which this activity offering belongs to
     * @name RegistrationGroup ids
     */
    public List<String> getRegistrationGroupIds();
    
    /**
     * Canonical activity whose instance is this activity offering  
     * @name Activity Id
     */
    public String getActivityId();
        
    /**
     * Alphanumeric character that identifies the section of the course offering
     * @name Activity Code
     */
    public String getActivityCode();   
    
    /**
     * Academic term the activity is being offered in. Should be same as CourseOffering unless changed, then must 
     * be nested term of courseOffering
     */
    public String getTermKey();
        
    /**
     * Indicates that the course is an Honors Course
     * @name Is Honors Offering
     */
    public Boolean getIsHonorsOffering();
    
    /**
     * The options/scales that indicate the allowable grades that can be awarded.
     * If the value is set here then the Clu must have a grading option set on the
     * canonical activity
     * 
     * @name: Grading Options
     */
    public List<String> getGradingOptions();
    

    /**
     * Instructors for the activity. This list should be constrained by the instructors listed on the course offering.
     * @name Instructors
     */
    public List<CluInstructorInfo> getInstructors();
       
    /********** Final Exam Information *****************/
    
    /**
     * Start time of final exam
     * @name Final Exam StartTime
     */
    public Date getFinalExamStartTime();
    
    /**
     * End time of final exam.
     * @name Final Exam EndTime
     */
    public Date getFinalExamEndTime();
    
    /**
     * Building code where final exam will be conducted
     * @name Final Exam Building
     */
    public String getFinalExamBuilding();
    
    /**
     * Room number where final exam will be conducted
     * @name Final Exam Room
     */
    public String getFinalExamRoom();


    /********************* Delivery Logistics ************************/
    
    /**
     * When/for how long does the offering meet in class. 
     * Calculated by system based on meeting times; may be validated against canonical.
     * The unit is hours
     * @name Weekly Inclass Contact Hours
     */    
    public Float getWeeklyInclassContactHours();
    
    /**
     * When/for how long does the offering meet out of class.
     * Entered by Scheduler.
     * The unit is hours
     * @name Weekly Outofclass Contact Hours
     */
    public Float getWeeklyOutofclassContactHours();

    /**
     * When/for how long does the offering meet in total.
     * Calculated by system based as sum of In Class and Out of Class hours.
     * The unit is hours
     * @name Weekly Total Contac Hours
     */
    public Float getWeeklyTotalContactHours();

    /**
     * Total maximum number of "seats" or enrollment slots that can be filled for the offering. 
     * @name Maximum Enrollment
     */
    public Integer getMaximumEnrollment();

    /** 
     * Total minimum number of seats that must be filled for the offering not to be canceled. 
     * @name Minimum  Enrollment
     */
    public Integer getMinimumEnrollment();    
}
