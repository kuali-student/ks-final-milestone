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

import java.util.Date;
import java.util.List;
import org.kuali.rice.core.api.util.type.KualiDecimal;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.ValidationResult;

/**
 * Represents a single transaction item for a Student and Registration
 * Group. 
 *
 * The possible types of RegistrationRequestItem are ADD, DROP, SWAP
 * or UPDATE. These types represent adding (waitlisting, holdlisting),
 * dropping courses, swapping sections, or changing the registration
 * options.
 *
 * The Transaction Item Type indicates if this is for an ADD, DROP,
 * SWAP or UPDATE. 
 *
 * 1. Register for course - RegistrationRequestItem Type is ADD,
 * newRegistrationGroupId is the RegistrationGroup.
 *
 * 2. Register for course but waitlist if seat not available OR
 * waitlist for course - same as above and okToWaitlist is true.
 *
 * 3. Swap between RegistrationGroup within same CourseOffering - Type
 * is SWAP, both new and existing RegistrationGroup Ids populated. New
 * RegistrationGroup is to be the one replaced with the old
 * one. RegistrationGroups are in the same CourseOffering.
 *
 * @author Kuali Student Team (sambit)
 */

public interface RegistrationRequestItem
        extends IdEntity {

    /**
     * The RegistrationRequest to which this item belongs.
     *
     * @name Registration Request Id
     * @readOnly
     */
    public String getRegistrationRequestId();

    /**
     * The Person to which this request item applies.
     *
     * @name Person Id
     * @required
     * @readOnly on update
     */
    public String getPersonId();

    /**
     * The RegistrationGroup to which the student will be registered
     * upon a successful submission of this item. 
     *
     * This is populated for ADD, DROP, UPDATE and SWAP types of
     * RegistrationRequestItems.
     *
     * @name Registration Group Id
     * @impl LprTransactionItem.newLuiId
     * @required
     */
    public String getRegistrationGroupId();

    /**
     * In the case of a DROP or SWAP or UPDATE, the "existing" course registration
     * Id in which the student is currently registered. 
     *
     * For an ADD, this field should be null.
     *
     * @name Existing Course Registration Id
     * @impl LprTransactionItem.existingLuiId
     */
    public String getExistingCourseRegistrationId();

    /**
     * The desired number of credits.  This value is a number
     * represented as a string.
     *
     * @name Credits
     * @impl LprTransactionItem.ResultValuesGroups filtering on the
     *       ResultValuesGroup Type for a credit option.
     */
    public KualiDecimal getCredits();

    /**
     * The requested grading scheme option (e.g. A-F or Pass/Fail).
     *
     * @name Grading Option Id
     * @impl LprTransactionItem.ResultValuesGroups filtering on the
     *       ResultValuesGroup Type for a grade system
     */
    public String getGradingOptionId();

    /**
     * The requested effective date of the registrationRequestItem
     *
     * @name Requested Effective Date
     */
    public Date getRequestedEffectiveDate();

    /**
     * If the course is full and there is a waitlist, is it okay to be
     * placed in the waitlist for the course.
     *
     * @name Ok To Waitlist
     */
    public Boolean getOkToWaitlist();

    /**
     * If the student does not meet one of the requirements for the
     * course but there is a list for such students because the
     * requirement ends at a future date, is it okay to be placed in
     * such a list.
     *
     * @name Ok To HoldUntillist
     */
    public Boolean getOkToHoldUntilList();

    /**
     * If the student has taken the course but is still capable of
     * registering for it, is it okay to proceed with the registration.
     *
     * @name Ok To Repeat
     */
    public Boolean getOkToRepeat();

    /**
     * Validation messages that happen when submitted.
     *
     * @return list of validation results
     */
    public List<? extends ValidationResult> getValidationResults();

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

    /**
     * Gets the course registration (if any) that resulted from this
     * registration transaction.
     *
     * @name Course Registration Id
     */
    public String getCourseRegistrationId();

    /**
     * Gets the waitlist entry (if any) that resulted from this
     * registration transaction.
     *
     * @name Course Waitlist Entry Id
     */
    public String getCourseWaitlistEntryId();

}
