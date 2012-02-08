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
     * @required
     */
    public Date getStartDate();

    /**
     * Window end date ("Aug 07, 2012")
     *
     * @name End Date
     * @impl This field can only be specified in exclusion of Max Per Slot. Choosing this field expands number_of_persons_per_slot so all the persons in a population are assigned to the available slots.
     * @impl If there is no End Date, then use either end of Reg Period or end of Drop/Add milestone as End Date milestone (TBD during PDT impl)
     */
    public Date getEndDate();

    /**
     * Slot rules for appointment slot generation
     *
     * @name Slot Rules
     * @required
     */
    public AppointmentSlotRule getSlotRule();

    /**
     * Appointment period milestone
     *
     * @impl This milestone may be mapped to an ATP ("FALL2013")
     * @name Period Milestone Id
     * @readOnly
     * @required
     */
    public String getPeriodMilestoneId();

    /**
     * The Population Id to which the appointment window is assigned.
     *
     * @name Assigned Population Id
     * @readOnly
     * @required
     */
    public String getAssignedPopulationId();

    /**
     * Ordering for appointment slots assignment ("random"/"last name"/"GPA")
     *
     * @name Assigned Order Type Key
     * @readOnly
     * @required
     */
    public String getAssignedOrderTypeKey();

    /**
     * Maximum number of persons that can be assigned to an appointment slot
     *
     * @name Max Per Slot
     * @impl This field can only be specified in exclusion of End Date. Choosing this field expands the number_of_appointment_slots so all the persons in a population are assigned.
     */
    public Integer getMaxAppointmentsPerSlot();


}
