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

import org.kuali.student.r2.common.infc.OperationStatus;

/**
 * A response to an individual RegistrationRequestItem.  A response
 * may result in a CourseRegistration or a position on a course
 * waitlist, or just return the error messages.
 * 
 * @author Kuali Student Team (sambit)
 */

public interface RegistrationResponseItem {
    
    /**
     * Gets the RegistrationRequestItem to which this item is a
     * response.
     * 
     * @name Registration Request Item
     * @readOnly
     * @required
     */
    public String getRegistrationRequestItemId();

    /// ??????
    /**
     * Get the operation status info for the registration item.
     * 
     * @name Operation Status
     */
    public OperationStatus getOperationStatus();


    // separate field for waitlists status

    // separate field for aync posting/??


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

    /**
     * Gets the hold until list entry (if any) that resulted from this
     * registration transaction.
     * 
     * @name Hold Until List Entry Id
     */
    public String getHoldUntilListEntryId();
}
