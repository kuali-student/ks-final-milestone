/**
 * Copyright 2013 The Kuali Foundation
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
package org.kuali.student.enrollment.coursewaitlist.infc;

import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.IdNamelessEntity;
import org.kuali.student.r2.common.infc.TimeAmount;

import java.util.List;

/**
 * Represents a course wait list that is attached to a set of FOs and related AOs.
 * A set of CourseWaitListEntries may be attached to this CourseWaitList.
 * These entries represent the students that are on the course wait list.
 *
 */
public interface CourseWaitList extends IdNamelessEntity, HasEffectiveDates {

    /**
     * A unique identifier for the state of this object.
     * This value should not be changed directly.
     * Instead, it should be set using the appropriate change state method in service.
     *
     * @name State Key
     * @readOnly
     * @required
     */
    @Override
    String getStateKey();

    /**
     * The format offering Ids associated with this CourseWaitList.
     * @name Format Offering Ids
     */
    List<String> getFormatOfferingIds();

    /**
     * The activity offering Ids associated with this CourseWaitList.
     * @name Activity Offering Ids
     */
    List<String> getActivityOfferingIds();

    /**
     * The maximum size of this CourseWaitList.  This value will always be positive.
     * Zero represents an unlimited size.
     * @name Max Size
     */
    Integer getMaxSize();

    /**
     * Indicates if the entries in this wait list may be added using any activity offering
     * associated with this wait list.  If set to false only the registration group associated
     * with the entry should be used.
     * @name Register In First Available Activity Offering
     */
    Boolean getRegisterInFirstAvailableActivityOffering();

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
