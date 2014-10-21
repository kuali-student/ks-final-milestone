/*
 * Copyright 2011 The Kuali Foundation 
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

package org.kuali.student.enrollment.courseregistration.infc;

import org.kuali.rice.core.api.util.type.KualiDecimal;

import org.kuali.student.r2.common.infc.Relationship;


/**
 * Course Registration is the consolidated view of the details of a
 * student's relation with a course.
 *
 * The CourseRegistration is created only upon the successful
 * registration of the student into the course as a result of a
 * RegistrationRequest, and not after waitlist or any other type of
 * relation with the course.
 */

public interface CourseRegistration 
    extends Relationship {

    /**
     * The person Id for the course registration.
     * 
     * @name Person Id
     * @readOnly
     * @required
     * @impl Lpr.personId
     */
    public String getPersonId();

    /**
     * The Id of the term that governs this registration.
     * 
     * Note: this typically is the same as the termId on the
     * associated Course Offering but may be different.  This is
     * especially true if the Course Offering term is a mini within
     * the main or non-standard term.
     * 
     * @name Term Id
     * @readOnly
     * @required
     * @impl Lpr.atpId
     */
    public String getTermId();

    /**
     * The Course Offering Id for this course registration.
     * 
     * @name Course Offering Id
     * @readOnly
     * @required
     * @impl Lpr.LuiId
     */
    public String getCourseOfferingId();

    /**
     * The registration group Id for this course registration.
     * 
     * @name Registration Group Id
     * @readOnly
     * @required
     * @impl Lpr.LuiId
     */
    public String getRegistrationGroupId();
    
    /**
     * The number of credits for which the student
     * registered. Typically, this number should be in the range
     * defined at the CourseOffering or RegistrationGroup.
     *
     * This value is a number represented as a string.
     * 
     * @name Credits
     * @impl Lpr.ResultValuesGroup.ResultValuesKeys there should be a
     *       single predefined ResultValuesKey for every valid credit
     *       value within a range of allowable credits. Consult the
     *       ResultValuesGroup Type for credit value types.
     */
    public KualiDecimal getCredits();

    /**
     * The Id for the Grading Option for which the student
     * registered. Typically, this option should be in the allowable
     * range defined at the CourseOffering or RegistrationGroup.
     * 
     * @name Grading Option Id
     * @impl Lpr.ResultValuesGroup consult the ResultValuesGroup Type
     *       for grading system options
     */
    public String getGradingOptionId();

    /**
     * The code of the crosslisted course.
     *
     * For example if ENGL255 is actual course crosslisted with WMST255,
     * when WMST255 is added id will be the one of ENGL255, and code
     * WMST255 will be saved in crossList field (for displaying purpose)
     *
     * @name Cross Listed Course Code
     */
    public String getCrossListedCode();
}
