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

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.appointment.infc.AppointmentWindow;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppointmentWindowInfo", propOrder = {
        "id", "typeKey", "stateKey", "name", "descr",
        "startDate", "endDate", "slotRule", "periodMilestoneId", "assignedPopulationId", "assignedOrderTypeKey", "maxAppointmentsPerSlot",
        "meta", "attributes", "_futureElements"})
public class AppointmentWindowInfo extends IdEntityInfo implements AppointmentWindow {

    @XmlElement
    private Date startDate;
    @XmlElement
    private Date endDate;
    @XmlElement
    private AppointmentSlotRuleInfo slotRule;
    @XmlElement
    private String periodMilestoneId;
    @XmlElement
    private String assignedPopulationId;
    @XmlElement
    private String assignedOrderTypeKey;
    @XmlElement
    private Integer maxAppointmentsPerSlot;
    @XmlAnyElement
    private List<Element> _futureElements;


    public AppointmentWindowInfo() {

    }

    public AppointmentWindowInfo(AppointmentWindow appointmentWindow) {
        super(appointmentWindow);
        if (null != appointmentWindow) {
            this.startDate = (null != appointmentWindow.getStartDate()) ? new Date(appointmentWindow.getStartDate().getTime()) : null;
            this.endDate = (null != appointmentWindow.getEndDate()) ? new Date(appointmentWindow.getEndDate().getTime()) : null;
            this.slotRule = (null != appointmentWindow.getSlotRule()) ? new AppointmentSlotRuleInfo(appointmentWindow.getSlotRule()) : null;
            this.periodMilestoneId = appointmentWindow.getPeriodMilestoneId();
            this.assignedPopulationId = appointmentWindow.getAssignedPopulationId();
            this.assignedOrderTypeKey = appointmentWindow.getAssignedOrderTypeKey();
            this.maxAppointmentsPerSlot = appointmentWindow.getMaxAppointmentsPerSlot();
        }
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getStartDate() {
        return this.startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public Date getEndDate() {
        return this.endDate;
    }

    public void setSlotRule(AppointmentSlotRuleInfo slotRule) {
        this.slotRule = slotRule;
    }

    @Override
    public AppointmentSlotRuleInfo getSlotRule() {
        return this.slotRule;
    }

    public void setPeriodMilestoneId(String periodMilestoneId) {
        this.periodMilestoneId = periodMilestoneId;
    }

    @Override
    public String getPeriodMilestoneId() {
        return this.periodMilestoneId;
    }

    public void setAssignedPopulationId(String assignedPopulationId) {
        this.assignedPopulationId = assignedPopulationId;
    }

    @Override
    public String getAssignedPopulationId() {
        return this.assignedPopulationId;
    }

    public void setAssignedOrderTypeKey(String assignedOrderTypeKey) {
        this.assignedOrderTypeKey = assignedOrderTypeKey;
    }

    @Override
    public String getAssignedOrderTypeKey() {
        return this.assignedOrderTypeKey;
    }


    public void setMaxAppointmentsPerSlot(Integer maxPerSlot) {
        this.maxAppointmentsPerSlot = maxPerSlot;
    }

    @Override
    public Integer getMaxAppointmentsPerSlot() {
        return this.maxAppointmentsPerSlot;
    }
}
