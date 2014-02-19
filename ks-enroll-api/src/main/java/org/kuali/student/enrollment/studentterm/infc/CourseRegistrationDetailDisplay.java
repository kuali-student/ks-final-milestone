/*
 * Copyright 2014 The Kuali Foundation 
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

package org.kuali.student.enrollment.studentterm.infc;

import org.kuali.rice.core.api.util.type.KualiDecimal;

import org.kuali.student.r2.common.infc.Relationship;
import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.common.infc.RichText;

import java.util.Date;
import java.util.List;

/**
 * This is denormalized view of a student's course registrations in a
 * term.
 *
 * For now, this is scoped to information derived from Course
 * Registration and related entities. Cumulatives and other cross-Term
 * progress is in the Academic Record.
 */

public interface CourseRegistrationDetailDisplay
    extends Relationship {

    /**
     * Student Id to which this record pertains.
     *
     * @name Student Id
     * @required
     * @readonly
     */
    public String getPersonId();

    /**
     * Term Id to which this record pertains. It may differ from the
     * reporting term in StudentTermDetail.
     *
     * @name Term Id
     * @required
     * @readonly
     */
    public String getTermId();

    /**
     * The Term Name.
     *
     * @name Term Name
     * @readonly
     */
    public RichText getTermName();

    /**
     * The Term Code.
     *
     * @name Term Code
     * @readonly
     */
    public RichText getTermCode();

    /**
     * The Term Start Date.
     *
     * @name Term Start Date.
     * @readonly
     */
    public Date getTermStartDate();

    /**
     * The Term End Date.
     *
     * @name Term End Date.
     * @readonly
     */
    public Date getTermEndDate();

    /**
     * A placeholder for messages to the student such as exceeded
     * credit limit or ineligible to register.
     *
     * @name Messages
     * @readonly
     */
    public List<RichText> getMessages();

    /**
     * Course Registration Id
     *
     * @name Course Registration Id
     * @readonly
     */
    public String getCourseRegistrationId();

    /**
     * Tests if this is an actual registration.
     *
     * @return true if actual, false if student is not registered
     * because this is w what-if scenario or student is on waitlist
     * @name Is Actual
     * @readonly
     */
    public Boolean isActual();

    /**
     * Tests if student is on waitlist.
     *
     * @name Is Actual
     * @readonly
     */
    public Boolean isOnWaitlist();

    /**
     * The credits for which the student registered.
     *
     * @name Credits
     * @readonly
     */
    public KualiDecimal getCredits();

    /**
     * The student registration/grading option.
     *
     * @name Student Registration Option
     * @readonly
     */
    public String getStudentRegsitrationOption();

    /**
     * The Course Offering Id.
     *
     * @name Course Offering Id
     * @readonly
     */
    public String getCourseOfferingId();

    /**
     * The Course Offering type.
     *
     * @name Course Offering Type
     * @readonly
     */
    public String getCourseOfferingType();

    /**
     * The Course Offering name or title.
     *
     * @name Course Offering Name
     * @readonly
     */
    public RichText getCourseOfferingName();

    /**
     * The Course Offering code.
     *
     * @name Course Offering Code
     * @readonly
     */
    public RichText getCourseOfferingCode();

    /**
     * Instructor Id
     *
     * @name Instructor Id
     * @readonly
     */
    public String getInstructorPersonId();

    /**
     * Instructor Name
     *
     * @name Instructor Name
     * @readonly
     */
    public RichText getInstructorPersonName();

    /**
     * Activity Registration Detail.
     *
     * @name Activity Registration Detail
     * @readonly
     */
    public List<ActivityRegistrationDetailDisplay> getActivityRegsitrationDetailDisplay();
}
