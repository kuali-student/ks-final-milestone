/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.enrollment.courseoffering.infc;

import java.util.List;

import org.kuali.student.enrollment.lui.infc.LuiInstructor;
import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.lum.course.dto.CourseExpenditureInfo;
import org.kuali.student.r2.lum.course.dto.CourseRevenueInfo;
import org.kuali.student.r2.lum.course.infc.CourseFee;
import org.kuali.student.r2.lum.lrc.dto.ResultComponentInfo;

/**
 * 
 * @author Kamal
 */

public interface CourseOffering extends IdEntity {

    /**
     * Activity Offerings for the course offering
     * @name ActivityOffering Ids
     */
    public List<String> getActivityOfferingIds();
    
    /**
     * Registration Groups for the course offering
     * @name RegistrationGroup Ids
     */
    public List<String> getRegistrationGroupIds();
    
    
    /**
     * Academic term of course offering
     * @name Term Key
     */
    public String getTermKey();
    
    /**
     * A unique identifier assigned to all approved course offerings that exist in the catalog
     * This should be non updatable once initialized 
     * @name Course Id
     */
    public String getCourseId();
                
    /**
     * Identifiers for formats from the canonical course that will be offered as part of the course offering
     * @name Format Ids
     */
    public List<String> getFormatIds();
    
    /**
     * Identifies the number of a course as reflected in the college catalog.
     * @name Course Code
     */
    public String getCourseCode();
    
    /**
     * A suffix of the course number as reflected in the college catalog.
     * @name Course Number Suffix
     */
    public String getCourseNumberSuffix();
        
    /**
     * Identifies the department and/subject code of the course as reflected in the course catalog.
     * This should correspond to the value in the canonical course and should not be updatable.
     * @name Subject Area
     * @impl mpas to Lui.studySubjectArea
     */
    public String getSubjectArea();
    
    /**
     * Name of the course used in the college catalog.
     * @name Course Title 
     */
    public String getCourseTitle();
           
    /**
     * Indicates that the entire course offering is an Honors Course
     * @name Is Honors Offering
     */
    public Boolean getIsHonorsOffering();

    /**
     * Total maximum number of "seats" or enrollment slots that can be filled for the offering. 
     * Calculated based on sum of all the maximum seats of primary activity type offerings
     * @name Maximum Enrollment
     * @impl maps to Lui.maximumEnrollment
     */
    public Integer getMaximumEnrollment();

    /** 
     * Total minimum number of seats that must be filled for the offering not to be canceled. 
     * Calculated based on sum of all the minimum seats of primary activity type offerings
     * @name Minimum  Enrollment
     * @impl maps to Lui.minimumEnrollment
     */
    public Integer getMinimumEnrollment();

    
    /**
     * The unique identifier of the other course offerings with which this offering is joint-listed
     */
    public List<String> getJointOfferingIds();
    
    /******** Assessment Information ***************/
    /**
     * The options/scales that indicate the allowable grades that can be awarded.
     * If the value is set here then the Clu must have a grading option set on the
     * canonical activity. 
     * 
     * ResultComponent will contain grade values valid for this course offering
     * 
     * @name: Grading Options
     */
    public List<String> getGradingOptions();
    
    /**
     * Type of credit of course offering. If fixed then single value is stored in the result component.
     * If multiple then individual values are stored in the result component in case of a range, the component
     * will store the legal range with the increment factor.
     */    
    public ResultComponentInfo getCreditOptions();
    
    /**
     * Level at which grade rosters should be generated - activity, format or course.
     * @name Grade Roster Level
     */
    public String getGradeRosterLevel();
    
        
    /******** Personnel Information *****************/
    
    /**
     * Instructors for the activity. This list should be constrained by the instructors listed on the course offering.
     * @name Instructors
     */
    public List<? extends LuiInstructor> getInstructors();
    
    
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
    public Boolean getHasWaitlist();

    /**
     * Indicates the type of waitlist as it relates to processing students on and off
     * @name Waitlist TypeKey
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
    
    
    /************* Finances ***************************/
    
    /**
     * The primary source of funding for the offering.
     * @name Funding Source
     */
    public String getFundingSource();
    
    /*
     * TODO: Change CourseFeeInfo, CourseRevenueInfo and CourseExpenditureInfo to interfaces 
     * after course service is migrated to 1.3
     */
    
    /**
     * Fees associated with the course offering. 
     * @name Fees
     */    
    public List<? extends CourseFee> getFees();
    
    /**
     * Organization that receives the revenue associated with the course
     * @name Revenues
     */
    public List<CourseRevenueInfo> getRevenues();
    
    
    /**
     * Organization that incurs the cost associated with the course
     * @name expenditure
     */
    public CourseExpenditureInfo getExpenditure();
        
    /**
     * Flag indicating whether a course is eligible for Financial Aid. Derived from course catalog (canonical)
     * @name Is FinancialAid Eligible
     */
    public Boolean getIsFinancialAidEligible();
    
    /************* Miscellaneous **********************/
    
    /**
     * Specifies whether the selection of RegistrationGroup that students register for will be done as a 
     * block (all activites together) or in a sequential order of activities
     * @name RegistartionOrder TypeKey 
     */
    public String getRegistrationOrderTypeKey();
        
}
