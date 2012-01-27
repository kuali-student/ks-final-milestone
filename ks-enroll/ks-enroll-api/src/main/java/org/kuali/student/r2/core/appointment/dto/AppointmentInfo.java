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

package org.kuali.student.r2.core.appointment.dto;

import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.kuali.student.r2.core.appointment.infc.Appointment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppointmentInfo", propOrder = { "id", "typeKey",
		"stateKey", "personId", "appointmentWindowId", "appointmentTimeSlotId",
        "effectiveDate", "expirationDate",
        "meta", "attributes", "_futureElements" })
public class AppointmentInfo extends RelationshipInfo implements Appointment {

    private String personId;
    private String appointmentWindowId;
    private String appointmentTimeSlotId;

    @Override
    public String getPersonId() {
        return this.personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public String getAppointmentWindowId() {
        return this.appointmentWindowId;
    }

    public void setAppointmentWindowId(String appointmentWindowId) {
        this.appointmentWindowId = appointmentWindowId;
    }

    @Override
    public String getAppointmentTimeSlotId() {
        return this.appointmentTimeSlotId;
    }

    public void setAppointmentTimeSlotId(String appointmentTimeSlotId) {
        this.appointmentTimeSlotId = appointmentTimeSlotId;
    }
}
