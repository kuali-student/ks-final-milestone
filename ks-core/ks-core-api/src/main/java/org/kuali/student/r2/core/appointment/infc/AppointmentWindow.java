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

import java.util.Date;

/**
 * Information about an appointment window
 *
 * @Version 1.0
 * @Author Sri komandur@uw.edu
 */
public interface AppointmentWindow extends IdEntity {

    /**
     * Window start date ("Aug 05, 2012")
     *
     * @name Start Date
     */
    public Date getStartDate();

    /**
     * Window end date ("Aug 07, 2012"). Window end date does not constrain a slot duration. It is to ensure that the slot start time is not after the window end date.
     *
     * @name End Date
     * @impl Choosing this field expands number_of_persons_per_slot so all the persons in a population are assigned to the available slots.
     * @impl If there is no End Date, then use either end of Reg Period or end of Drop/Add milestone as End Date milestone (TBD during PDT impl)
     */
    public Date getEndDate();

    /**
     * Slot rules for appointment slot generation
     *
     * @name Slot Rules
     */
    public AppointmentSlotRule getSlotRule();

    /**
     * Appointment period milestone
     *
     * @impl This milestone may be mapped to an ATP ("FALL2013")
     * @name Period Milestone Id
     * @impl Based on AppointmentWindow type the dictionary may choose to constrain the periodMilestoneId requirement
     */
    public String getPeriodMilestoneId();

    /**
     * The Population Id to which the appointment window is assigned.
     *
     * @name Assigned Population Id

     */
    public String getAssignedPopulationId();

    /**
     * Ordering for appointment slots assignment ("random"/"last name"/"GPA")
     *
     * @name Assigned Order Type Key

     */
    public String getAssignedOrderTypeKey();

    /**
     * Maximum number of persons that can be assigned to an appointment slot
     *
     * @name Max Appointments Per Slot
     * @impl Choosing this field expands the number_of_appointment_slots so all the persons in a population are assigned.
     */
    public Integer getMaxAppointmentsPerSlot();

}
