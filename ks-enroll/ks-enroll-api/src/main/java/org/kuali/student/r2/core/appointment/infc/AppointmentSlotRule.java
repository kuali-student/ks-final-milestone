/*
 * Copyright 2012 The Kuali Foundation Licensed under the
 *  Educational Community License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may
 *  obtain a copy of the License at
 *
 *   http://www.osedu.org/licenses/ECL-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS"
 *  BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package org.kuali.student.r2.core.appointment.infc;

import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.common.infc.TimeOfDay;

import java.util.List;

/**
 * Information about rules used in appointment slot generation
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface AppointmentSlotRule {
    /**
     * The weekday codes ("MWF = 1,3,5")
     *
     * @impl Java standard: Sunday=1 to Saturday=7
     * @name Weekdays
     * @required
     */
    List<Integer> getWeekdays();

    /**
     * Window start time ("9am")
     *
     * @name Start Time Of Day
     * @impl If the slot is open ended, then startTimeOfDay applies only for the first day and it is time portion of the window start date
     */
    TimeOfDay getStartTimeOfDay();

    /**
     * Window end time ("5pm")
     *
     * @name End Time Of Day
     * @impl If the slot is open ended, then endTimeOfDay applies only for the last day and it is time portion of the window end date
     */
    TimeOfDay getEndTimeOfDay();

    /**
     * Interval between start times of two consecutive appointment slots ("30 mins")
     *
     * @name Interval
     * @impl If the slot is open ended, then slotStartInterval is null
     */
    TimeAmount getSlotStartInterval();

    /**
     * Duration of the appointment slot ("20 mins") - advertised duration of the slot. (slotStartInterval - slotDuration) = some grace period complete activity
     *
     * @name Slot Duration
     * @impl If the slot is open ended, then slotDuration is null and actual duration is the time between window start and end dates
     */
    TimeAmount getSlotDuration();
}
