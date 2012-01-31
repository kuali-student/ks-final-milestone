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
import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.core.appointment.infc.AppointmentWindow;

import java.util.Date;

public class AppointmentWindowInfo extends IdEntityInfo implements AppointmentWindow {

    private Integer[] weekdays;
    private Date startDate;
    private Date endDate;
    private Date startTime;
    private Date endTime;
    private TimeAmount appointmentSlotInterval;
    private TimeAmount appointmentSlotGap;
    private String appointmentPeriodMilestoneId;
    private String assignedPopulationId;
    private String assignedOrderTypeKey;

    @Override
    public Integer[] getWeekdays() {
        return this.weekdays;
    }

    @Override
    public Date getStartDate() {
        return this.startDate;
    }

    @Override
    public Date getEndDate() {
        return this.endDate;
    }

    @Override
    public Date getStartTime() {
        return this.startTime;
    }

    @Override
    public Date getEndTime() {
        return this.endTime;
    }

    @Override
    public TimeAmount getAppointmentSlotInterval() {
        return this.appointmentSlotInterval;
    }

    @Override
    public TimeAmount getAppointmentSlotGap() {
        return this.appointmentSlotGap;
    }

    @Override
    public String getAppointmentPeriodMilestoneId() {
        return this.appointmentPeriodMilestoneId;
    }

    @Override
    public String getAssignedPopulationId() {
        return this.assignedPopulationId;
    }

    @Override
    public String getAssignedOrderTypeKey() {
        return this.assignedOrderTypeKey;
    }
}
