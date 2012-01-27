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

import java.util.List;

/**
 * Information about an appointment window associated with a population(s)
 *
 * @Version 1.0
 * @Author Sri komandur@uw.edu
 */
public interface AppointmentWindow extends IdEntity {

    /**
     * Appointment period. Note: This milestone may be mapped to one or more ATPs ("FALL2013")
     * e.g., Aug 01, 2012 - Aug 10, 2012
     *
     * @name Appointment Period Milestone
     */
    public String getAppointmentPeriodMilestoneId();

    /**
     * Appointment Window within the period
     * e.g, 8:00 am - 12:00pm, any day during the appointment period
     *
     * @name Appointment Window TimeSlot Id
     */
    public String getAppointmentWindowTimeSlotId();

    /**
     * Interval within the appointment window
     * e.g., 10 mins
     *
     * @name Interval
     */
    public TimeAmount getInterval();

    /**
     * Assign Order
     * e.g, "random", "last name",  "GPA"
     *
     * @name Assign Order
     */
    public String getAssignmentOrder();

    /**
     * The Population Ids to which the appointment window is assigned.
     *
     * @name Assigned Population Ids
     */
    public List<String> getAssignedPopulationIds();

}
