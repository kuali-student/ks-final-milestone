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

import java.util.Date;

/**
 * Appointment slot information belonging to a person
 *
 * @Version 1.0
 * @Author Sri komandur@uw.edu
 */
public interface AppointmentSlot {
    /**
     * Appointment start time
     *
     * @name Appointment Start Time
     */
    public Date getAppointmentStartTime();

    /**
     * Appointment end time (Duration can be used here instead. However, since
     * duration is in AppointmentWindow, it is more useful to have actual end
     * time on an individual appointment
     *
     * @name Appointment End Time
     */
    public Date getAppointmentEndTime();

    /**
     * Appointment window from which this appointment was generated
     *
     * @name Appointment Window Id
     */
    public String getAppointmentWindowId();
}
