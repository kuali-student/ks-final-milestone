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


import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.core.appointment.infc.AppointmentSlot;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppointmentSlotInfo", propOrder = {"id", "typeKey", "stateKey",
        "startDateTime", "endDateTime", "windowId", "effectiveDate",
        "expirationDate", "meta", "attributes", "_futureElements"})
public class AppointmentSlotInfo extends IdNamelessEntityInfo implements AppointmentSlot {

    private Date startDateTime;
    private Date endDateTime;
    private String windowId;
    private List<Element> _futureElements;

    public AppointmentSlotInfo() {

    }

    public AppointmentSlotInfo(AppointmentSlot appointmentSlot) {
        super(appointmentSlot);
        if (null != appointmentSlot) {
            this.startDateTime = (null != appointmentSlot.getStartDateTime()) ? new Date(appointmentSlot.getStartDateTime().getTime()) : null;
            this.endDateTime = (null != appointmentSlot.getEndDateTime()) ? new Date(appointmentSlot.getEndDateTime().getTime()) : null;
            this.windowId = appointmentSlot.getWindowId();
        }
    }

    @Override
    public Date getStartDateTime() {
        return this.startDateTime;
    }

    @Override
    public Date getEndDateTime() {
        return this.endDateTime;
    }

    @Override
    public String getWindowId() {
        return this.windowId;
    }
}
