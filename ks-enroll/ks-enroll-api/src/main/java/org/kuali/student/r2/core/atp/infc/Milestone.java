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

package org.kuali.student.r2.core.atp.infc;

import java.util.Date;

import org.kuali.student.r2.common.infc.KeyEntity;


/**
 * Information about a milestone. A milestone may represent a single
 * point in time or a range depending on the value of isDateRange().
 *
 * @Author tom
 * @Since Tue Apr 05 14:22:34 EDT 2011
 */ 

public interface Milestone extends KeyEntity {

    /**
     * Name: Is All Day
     * Tests if this Milestone is an all day event. An all-day event
     * does not have a meaningful time component in the date.
     *
     * @return true if this is an all-day event, false otherwise
     */
    public Boolean getIsAllDay();

    /**
     * Name: Is Date Range
     * Tests if this milestone has a date range. 
     *
     * @return true if the end date is different than the start date,
     *         false if the start end end date are the same
     */
    public Boolean getIsDateRange();

    /**
     * Name: Start Date
     * The start date and time of the milestone.
     *
     * @return the milestone start date 
     */
    public Date getStartDate();

    /**
     * Name: End Date 
     * The end date and time of the milestone. The end
     * date must be equal to or greater that the start.
     *
     * @return the milestone end date
     */
    public Date getEndDate();
}
