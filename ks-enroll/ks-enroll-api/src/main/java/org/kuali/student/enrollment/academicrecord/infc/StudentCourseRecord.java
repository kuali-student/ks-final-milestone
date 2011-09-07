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

package org.kuali.student.enrollment.academicrecord.infc;

import java.util.Date;

/**
 * Information about a Student Course Record. A Student Course Record
 * contains information on the courses a student has taken.
 *
 * @author tom
 * @since Tue Sep 06 14:22:34 EDT 2011
 */ 

public interface StudentCourseRecord {
    
    /**
     * The Id of the Student.
     *
     * @name Person Id
     * @readOnly
     * @required
     * @impl retrieved from the Course Registration
     */
    public String getPersonId();

    /**
     * The title of the course offering that was in effect at the time
     * the student took the course. 
     *
     * @name Course Offering Title
     * @readOnly
     * @required
     * @impl retrieved from the CourseOffering related to the Course 
     *       Registration
     */
    public String getCourseOfferingTitle();

    /**
     * The number of the course offering that was in effect at the time
     * the student took the course. 
     *
     * @name Course Offering Title
     * @readOnly
     * @required
     * @impl retrieved from the Course Offering related to the Course 
     *       Registration
     */
    public String getCourseOfferingNumber();

    /**
     * The Id of the Course Offering taken by the student.
     *
     * @name Course Offering Id
     * @readOnly
     * @impl retrieved from the Course Registration
     */
    public String getCourseOfferingId();

    /**
     * The Id of the Course Registration of the student in the course
     * offering.
     *
     * @name Course Registration Id
     * @readOnly
     */
    public String getCourseRegistrationId();

    /**
     * The Id of the canonical Course related to the Course Offering.
     *
     * @name Course Id
     * @readOnly
     * @impl retrieved from the Course Offering related to the Course 
     *       Registration.
     */
    public String getCourseId();
    
    /**
     * The name of the term in which the student took the offering.
     *
     * @name Term Name
     * @readonly
     * @impl retrieved from the Term related to the Course Offering 
     *       in the Course Registration
     */
    public String getTermName();

    /**
     * The start date of the term in which the student took the
     * offering.
     *
     * @name Term Start Date
     * @readonly
     * @impl retrieved from the Term related to the Course Offering 
     *       in the Course Registration
     */
    public Date getTermStartDate();

    /**
     * The end date of the term in which the student took the
     * offering.
     *
     * @name Term End Date
     * @readonly
     * @impl retrieved from the Term related to the Course Offering 
     *       in the Course Registration
     */
    public Date getTermEndDate();
    
    /**
     * The key of the term in which the student took the offering.
     *
     * @name Term Key
     * @readonly
     * @impl retrieved from the Course Offering in the Course Registration
     */
    public String getTermKey();

    /**
     * The grade the student was assigned for the course.
     *
     * @name Assigned Grade Value
     * @readonly
     */
    public String getAssignedGradeValue();

    /**
     * The key for the grading scale for the assigned grade.
     * (not sure what this is yet).
     *
     * @name Assigned Grade Scale
     * @readonly
     */
    public String getAssignedGradeScaleKey();

    /**
     * The calculated grade the student earned for the course.
     *
     * @name Calculated Grade Value
     * @readonly
     */
    public String getCalculatedGradeValue();

    /**
     * The key for the grading scale for the calculated grade.
     * (not sure what this is yet).
     *
     * @name Calculated Grade Scale
     * @readonly
     */
    public String getCalculatedGradeScaleKey();

    /**
     * The number of credits the student attempted for this course.
     *
     * @name Credits Attempted
     * @required
     * @readonly
     */
    public Float getCreditsAttempted();

    /**
     * The number of credits the student earned for this course.
     *
     * @name Credits Earned
     * @readonly
     */
    public Float getCreditsEarned();

    /**
     * If this student record counts toward the students GPA.
     *
     * @name Does Count Toward GPA
     */
    public Boolean doesCountTowardGPA();

    /**
     * If this student record counts toward the cumultive credits.
     *
     * @name Does Count Toward Credits
     */
    public Boolean doesCountTowardCredits();

    /**
     * If this course is a repeat of a previous offering. the student
     * took.
     *
     * @name Is Repeat
     */
    public Boolean isRepeat();
}
