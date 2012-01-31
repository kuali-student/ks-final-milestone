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

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.TimeAmount;

import java.util.Date;

/**
 * Information about an appointment window
 *
 * @Version 1.0
 * @Author Sri komandur@uw.edu
 */
public interface AppointmentWindow extends IdEntity {

    /**
     * The weekday codes
     *
     * @name Weekdays
     * @required
     */
    public Integer[] getWeekdays();

    /**
     * Window start date (impl hint: trucate time or use Joda LocalDate)
     *
     * @name Start Date
     */
    public Date getStartDate();

    /**
     * Window end date (impl hint: trucate time or use Joda LocalDate)
     *
     * @name End Date
     */
    public Date getEndDate();

    /**
     * Window start time (impl hint: truncate date or use Joda LocalTime)
     *
     * @name Start Time
     */
    public Date getStartTime();

    /**
     * Window end time (impl hint: truncate date or use Joda LocalTime)
     *
     * @name End Time
     */
    public Date getEndTime();

    /**
     * Appointment slot duration e.g., 20 mins
     *
     * @name Appointment Slot Interval
     */
    public TimeAmount getAppointmentSlotInterval();

    /**
     * Gap between appointment slots e.g., 10 mins
     *
     * @name Appointment Slot Gap
     */
    public TimeAmount getAppointmentSlotGap();

    /**
     * Appointment period milestone. Note: This milestone may be mapped to an
     * ATP ("FALL2013") e.g., Aug 01, 2012 - Aug 10, 2012
     *
     * @name Appointment Period Milestone Id
     */
    public String getAppointmentPeriodMilestoneId();

    /**
     * The Population Id to which the appointment window is assigned.
     *
     * @name Assigned Population Id
     */
    public String getAssignedPopulationId();

    /**
     * Ordering for appointment slots assignment e.g, "random", "last name",
     * "GPA"
     *
     * @name Assigned Order Type Key
     */
    public String getAssignedOrderTypeKey();


}
