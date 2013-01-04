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

package org.kuali.student.enrollment.acal.infc;

import java.util.Date;

import org.kuali.student.r2.common.infc.IdEntity;


/**
 * Information about a key date. A key date may represent a single
 * point in time or a range depending on the value of isDateRange().
 *
 * @impl maps to a Milestone with a type that is one of the key dates
 * @author tom
 * @since Tue Apr 05 14:22:34 EDT 2011
 */ 

public interface KeyDate 
    extends IdEntity {

    /**
     * Tests if this key date is an all day event. An all-day event
     * does not have a meaningful time component in the date.
     *
     * @name Is All Day
     * @required
     */
    public Boolean getIsAllDay();
  
    /**
     * Tests if this KeyDate is relative to another KeyDate.
     *
     * @name Is Relative To Key Date
     * @required
     * @impl maps to isRelative
     */
    public Boolean getIsRelativeToKeyDate();

    /**
     * Gets the anchor KeyDate to which this KeyDate is relative.
     *
     * @name Relative Anchor KeyDate Id
     * @impl maps to relativeAnchorMilestoneId
     */
    public String getRelativeAnchorKeyDateId();

    /**
     * Tests if this key date has a date range. 
     *
     * @return true if the end date is different than the start
     *         date, false if the start end end date are the same
     * @name Is Date Range
     * @required
     */
    public Boolean getIsDateRange();    

    /**
     * The start date and time of the key date.
     *
     * @name Start Date
     */
    public Date getStartDate();
    
    /**
     * The end date and time of the key date. The end
     * date must be equal to or greater that the start.
     *
     * @name End Date
     */
    public Date getEndDate();
}
