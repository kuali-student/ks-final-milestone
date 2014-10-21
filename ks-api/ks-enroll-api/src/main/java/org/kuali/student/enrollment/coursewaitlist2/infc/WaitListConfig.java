/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 *
 * Created by Charles on 9/24/2014
 */
package org.kuali.student.enrollment.coursewaitlist2.infc;

import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.IdNamelessEntity;
import org.kuali.student.r2.common.infc.TimeAmount;

/**
 * Represent waitlist configuration information.
 *
 * @author Kuali Student Team
 */
public interface WaitListConfig extends IdNamelessEntity, HasEffectiveDates {
    /**
     * Indicates if the CourseWaitListEntries associated with this CourseWaitList are automatically processed.
     * @name Is Automatically Processed
     */
    Boolean getAutomaticallyProcessed();

    /**
     * Indicates if the CourseWaitListEntries associated with this CourseWaitList must acknowledge/confirm that they
     * want to be added to the relevant course when they are processed.
     * @name Is Confirmation Required
     */
    Boolean getConfirmationRequired();


    /**
     * Indicates if a student is required to check in at some interval to remain on this course wait list.
     * @name Check-in Required
     */
    Boolean getCheckInRequired();

    /**
     *
     * The amount of time that a student is required to
     * check-in before they will be removed from this CourseWaitList.
     * @name Check-in Frequency
     */
    TimeAmount getCheckInFrequency();


    /**
     * Indicates if hold until entries are allowed on this CourseWaitList.
     * An entry is considered a hold until entry if it is in a specific state.
     * @name Allow Hold Until Entries
     */
    Boolean getAllowHoldUntilEntries();
}
