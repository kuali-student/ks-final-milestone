/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.courseoffering.infc;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.TimeAmount;

import java.util.Date;
import java.util.List;

/**
 * Individual activity offerings correspond to events in a scheduling
 * system, each with a meeting pattern.
 *
 * @author Kamal
 */
public interface ActivityOffering extends IdEntity {

    /**
     * Format offering Id used to create this activity
     *
     * @name Format Offering Id
     * @required
     * @readonly
     */
    public String getFormatOfferingId();

    /**
     * Format offering Name used to create this activity
     *
     * @name Format Offering Name
     * @readonly
     */
    public String getFormatOfferingName();

    /**
     * Canonical activity whose instance is this activity offering.
     *
     * @name Activity Id
     * @required
     * @readonly
     */
    public String getActivityId();

    /**
     * Academic term the activity is being offered in. 
     * 
     * Same as course offering term or a nested term of course
     * offering.
     * 
     * @name Term Id
     * @readonly
     * @impl map to Lui.getAtpId
     */
    public String getTermId();

    /**
     * Academic term code the activity is being offered in.
     *
     * Same as course offering term or a nested term of course
     * offering.
     *
     * @name Term Code
     * @readonly
     * @impl map to Lui.getAtpCode
     */
    public String getTermCode();

    /**
     * Alphanumeric character that identifies the section of the
     * course offering.
     *
     * @name Activity Code
     */
    public String getActivityCode();

    /**
     * Gets the schedule Ids for this activity offering.
     * 
     * No value will exist until the scheduling process has been completed for this activity offering.
     *
     * @name Schedule Ids
     */
    public List<String> getScheduleIds();

    /**
     * Indicates where this activity offering is in the scheduling process.
     * 
     * @return the scheduling state type key
     * @readonly
     * @impl The scheduling state is a calculated field
     */
    public String getSchedulingStateKey();
    
    /**
     * Indicates that the course is an Honors Course.
     *
     * @name Is Honors Offering
     *
     */
    public Boolean getIsHonorsOffering();

    /**
     * The options/scales that indicate the allowable grades that can
     * be awarded.  If the value is set here then the canonical course
     * must have a grading option set on the canonical activity.
     * 
     * @name Grading Option Keys
     * @impl maps to Lui.gradingOptions
     */
    public List<String> getGradingOptionKeys();

    /**
     * Instructors for the activity. This list should be constrained
     * by the instructors listed on the course offering.
     *
     * @name Instructors
     * @impl maps to Lui.instructors
     */
    public List<? extends OfferingInstructor> getInstructors();

    /**
     * Course Offering id the activity is being offered in.
     *
     * @name Course Offering Id
     * @required
     * @readonly
     * @impl maps to the containing formatOffering's courseOfferingId
     */
    public String getCourseOfferingId();

    /**
     * Course Offering code the activity is being offered in.
     *
     * @name Course Offering Code
     * @readonly
     * @impl maps to the containing formatOffering's courseOffering's code
     */
    public String getCourseOfferingCode();

    /**
     * Course Offering title the activity is being offered in.
     *
     * @name Course Offering Title
     * @readonly
     * @impl maps to the containing formatOffering's courseOffering's title
     */
    public String getCourseOfferingTitle();

    /********************* Scheduling Information **********************/
    /**
     * When/for how long does the offering meet in class.  Calculated
     * by system based on meeting times; may be validated against
     * canonical.  The unit is hours.
     *
     * @name Weekly Inclass Contact Hours
     */
    public String getWeeklyInclassContactHours();

    /**
     * When/for how long does the offering meet out of class.  Entered
     * by Scheduler. The unit is hours.
     *
     * @name Weekly Outofclass Contact Hours
     */
    public String getWeeklyOutofclassContactHours();

    /**
     * When/for how long does the offering meet in total.  Calculated
     * by system based as sum of In Class and Out of Class hours.  The
     * unit is hours.
     *
     * @name Weekly Total Contac Hours
     */
    public String getWeeklyTotalContactHours();

    /**
     * Total maximum number of "seats" or enrollment slots that can be
     * filled for the offering.
     *
     * @name Maximum Enrollment
     * @impl maps to Lui.maximumEnrollment
     */
    public Integer getMaximumEnrollment();

    /** 
     * Total minimum number of seats that must be filled for the
     * offering not to be canceled.
     *
     * @name Minimum  Enrollment
     * @impl maps to Lui.minimumEnrollment
     */
    public Integer getMinimumEnrollment();

    /**
     * Is maximum enrollment estimate flag
     *
     * @name Is Maximum Enrollment Estimate Flag
     */
    public Boolean getIsMaxEnrollmentEstimate();

    /**
     * Is there an instructor evaluation for this activity offering.
     *
     * @name Instructor Evaluation Flag
     */
    public Boolean getIsEvaluated();

    /**
     * Gets the URL for this offering.
     *
     * @name Activity Offering URL
     */
    public String getActivityOfferingURL();

    /**
     * Indicates that the activity offering has one or more shared scheduling information.
     * This is a derived flag, managed without persistence
     *
     * @name Is Colocated
     * @readOnly
     */
    public Boolean getIsColocated();

    /**
     * Indicates that the activity offering has been approved for non-standard time-slots.
     *
     * @name Is Approved For Non Standard TimeSlots flag
     */
    public Boolean getIsApprovedForNonStandardTimeSlots();

}
