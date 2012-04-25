/**
 * Copyright 2011 The Kuali Foundation Licensed under the Educational
 * Community License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.scheduling.infc;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.TimeAmount;


/**
 * Information about a Time Slot.
 *
 * @author tom
 * @since Thu Nov 3 14:22:34 EDT 2011
 */ 

public interface TimeSlot extends IdEntity {

    /**
     * The weekday codes. 
     *
     * @name Weekdays
     * @required
     */
    public Integer[] getWeekdays();

    /**
     * The starting hour of the day (0-23).
     *
     * @name Start Hour
     * @required
     */
    public Integer getStartHour();

    /**
     * The starting minute of the hour (0-59).
     *
     * @name Start Minute
     * @required
     */
    public Integer getStartMinute();

    /**
     * The ending hour of the day (0-23).
     *
     * @name End Hour
     * @required
     */
    public Integer getEndHour();

    /**
     * The ending minute of the hour (0-59).
     *
     * @name End Minute
     * @required
     */
    public Integer getEndMinute();
}
