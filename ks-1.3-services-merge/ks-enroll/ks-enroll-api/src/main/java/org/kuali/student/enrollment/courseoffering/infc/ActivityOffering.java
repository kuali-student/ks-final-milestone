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

import java.util.Date;
import java.util.List;

import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Individual activity offerings correspond to events in a scheduling
 * system, each with a meeting pattern.
 *
 * @author Kamal
 */
public interface ActivityOffering extends IdEntity {

    /**
     * Canonical activity whose instance is this activity offering.
     *
     * @name Activity Id
     * @required
     * @readOnly
     */
    public String getActivityId();

    /**
     * Academic term the activity is being offered in. 
     * 
     * Same as course offering term or a nested term of course
     * offering.
     * 
     * @name Term Id
     * @required
     * @readOnly
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
     * @required
     * @readOnly
     * @impl map to Lui.getAtpCode
     */
    public String getTermCode();

    /**
     * Course Offering id the activity is being offered in.
     *
     * @name Course Offering Id
     * @required
     * @readOnly
     * @impl maps to the containing formatOffering's courseOfferingId
     */
    public String getCourseOfferingId();

    /**
     * Format offering Id used to create this activity
     *
     * @name Format Offering Id
     * @required
     * @readoOnly
     */
    public String getFormatOfferingId();

    /**
     * Format offering Name used to create this activity
     *
     * @name Format Offering Name
     * @required
     * @readOnly
     */
    public String getFormatOfferingName();

    /**
     * Course Offering title the activity is being offered in.
     *
     * @name Course Offering Title
     * @required
     * @readOnly
     * @impl maps to the containing formatOffering's courseOffering's title
     */
    public String getCourseOfferingTitle();

    /**
     * Course Offering code the activity is being offered in.
     *
     * @name Course Offering Code
     * @required
     * @readOnly
     * @impl maps to the containing formatOffering's courseOffering's code
     */
    public String getCourseOfferingCode();

    /**
     * The suffix of the identifier to append im making the
     * activity code.
     *
     * @name Activity Number Suffix
     */
    public String getActivityNumberSuffix();

    /**
     * Identifies the section of the course offering.
     *
     * @name Activity Code
     */
    public String getActivityCode();

    /**
     * Gets the schedule Id for this offering.
     *
     * @name Schedule Id
     * @impl maps to Scheduling service not implemented
     */
    public String getScheduleId();

    /**
     * Indicates that the course is an Honors Course.
     *
     * @name Is Honors Offering
     * @required
     */
    public Boolean getIsHonorsOffering();

    /**
     * Instructors for the activity. This list should be constrained
     * by the instructors listed on the course offering.
     *
     * @name Instructors
     * @impl maps to Lui.instructors
     */
    public List<? extends OfferingInstructor> getInstructors();

    /********************** Final Exam Information ******************/
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
     * Space code where final exam will be conducted
     * @name Final Exam Space Code
     */
    public String getFinalExamSpaceCode();

    /********************* Delivery Logistics **********************/
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
     * Indicates whether a RegistrationGroup has a waitlist.
     *
     * @name Has Waitlist
     * @required
     * @impl maps to Lui.hasWaitlist
     */
    public Boolean getHasWaitlist();

    /**
     * Indicates the type of waitlist as it relates to processing
     * students on and off.
     *
     * @name Waitlist Type
     * @impl maps to Lui.waitlistTypeKey
     */
    public String getWaitlistTypeKey();

    /**
     * Maximum number of students to be allowed on the wait list.
     *
     * @name Waitlist Maximum
     * @impl maps to Lui.waitlistMaximum
     */
    public Integer getWaitlistMaximum();    

    /**
     * Indicates if the waitlist requires checkin.
     *
     * @name Is Waitlist Checkin Required
     * @required
     * @impl maps to Lui.isWaitlistCheckinRequired
     */
    public Boolean getIsWaitlistCheckinRequired();
    
    /**
     * Frequency for the waitlist checkin.
     *
     * @name Waitlist Checkin Frequency 
     * @impl maps to Lui.waitlistCheckinFrequency
     */
    public TimeAmount getWaitlistCheckinFrequency();
}