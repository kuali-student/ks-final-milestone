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
 * Represents a wait list that is attached to a specific LUI.
 * A set of WaitListEntries may be attached to this WaitList.
 * These entries represent the students that are on the wait list.
 *
 */
public interface WaitList extends IdNamelessEntity, HasEffectiveDates {

    /**
     *
     * @return The LUI Ids associated with this WaitList.
     * @name Associated Lui Ids
     */
    List<String> getAssociatedLuiIds();


    /**
     *
     * @return The ref object URI of LUI that this WaitList is attached to.
     * @name Lui Reference Object Uri
     */
    String getLuiRefObjectUri();

    /**
     *
     * @return The maximum size of this WaitList.  This value will always be positive.  Zero represents an unlimited size.
     * @name Max Size
     */
    Integer getMaxSize();

    /**
     *
     * @return The processing type for this WaitList. For example, automatic, semi-automatic, manual...
     * @name Wait List Processing Type
     */
    String getWaitListProcessingType();


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
     * @return true if blocked entries are allowed on this wait list.  An entry is considered blocked if it has any associated blocking rule ids attached to it.
     * @name Allow Blocked Entries
     */
    Boolean getAllowBlockedEntries();
}
