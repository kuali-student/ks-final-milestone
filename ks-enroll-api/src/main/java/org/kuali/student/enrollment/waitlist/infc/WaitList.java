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
package org.kuali.student.enrollment.waitlist.infc;

import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.IdNamelessEntity;
import org.kuali.student.r2.common.infc.TimeAmount;

import java.util.List;

/**
 * Represents a wait list that is attached to a specific offering (AO, FO, PO...).
 * A set of WaitListEntries may be attached to this WaitList.
 * These entries represent the students that are on the wait list.
 *
 */
public interface WaitList extends IdNamelessEntity, HasEffectiveDates {

    /**
     * A unique identifier for the state of this object.
     * This value should not be changed directly.
     * Instead, it should be set using the appropriate change state method in service.
     *
     * @name State Key
     * @required
     */
    @Override
    String getStateKey();

    /**
     *
     * @return The Offering (Activity Offering, Format Offering, Program Offering...) Ids associated with this WaitList.
     * @name Associated Offering Ids
     */
    List<String> getAssociatedOfferingIds();


    /**
     * The types of offerings that may be attached to this WaitList are limited based on this property
     * @return The Offering type key associated with this WaitList
     * @name Offering Type Key
     */
    String getOfferingTypeKey();

    /**
     *
     * @return The maximum size of this WaitList.  This value will always be positive.  Zero represents an unlimited size.
     * @name Max Size
     */
    Integer getMaxSize();

    /**
     *
     * @return The processing type key for this WaitList. For example, automatic, semi-automatic, manual...
     * @name Wait List Processing Type Key
     */
    String getWaitListProcessingTypeKey();


    /**
     * Indicates that a student is required to check in at some interval to remain on this wait list.
     *
     * @return true if a check-in is required for the entries on this WaitList
     * @name Check-in Required
     */
    Boolean getCheckInRequired();

    /**
     *
     * @return the amount of time that a student is required to
     * check-in before they will be removed from this WaitList.
     * @name Check-in Frequency
     */
    TimeAmount getCheckInFrequency();


    /**
     * @return true if hold list entries are allowed on this wait list.  An entry is considered a hold list entry if it has any associated hold list rule ids attached to it.
     * @name Allow Hold List Entries
     */
    Boolean getAllowHoldListEntries();
}
