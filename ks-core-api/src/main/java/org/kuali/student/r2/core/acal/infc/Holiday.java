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

package org.kuali.student.r2.core.acal.infc;

import java.util.Date;

import org.kuali.student.r2.common.infc.IdEntity;


/**
 * Information about a holiday. Holdays are used to mark holidays and
 * other non-instructional days on a HolidayCalendar.
 *
 * @author tom
 * @since Tue Apr 05 14:22:34 EDT 2011
 */ 

public interface Holiday 
    extends IdEntity {

    /**
     * Tests if this holiday is an instructional day.
     *
     * @name Is Instructional Day
     * @required
     */
    public Boolean getIsInstructionalDay();

    /**
     * Tests if this key date is an all day event. An all-day event
     * does not have a meaningful time component in the date.
     *
     * @name Is All Day
     * @required
     */
    public Boolean getIsAllDay();
  
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
