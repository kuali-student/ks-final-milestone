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

import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.core.appointment.infc.AppointmentSlotRules;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppointmentSlotRulesInfo", propOrder = {"weekdays", "startTimeOfDay", "endTimeOfDay",
        "interval", "gap", "_futureElements"})
public class AppointmentSlotRulesInfo implements AppointmentSlotRules {

    @XmlElement
    private List<Integer> weekdays;
    @XmlElement
    private TimeOfDayInfo startTimeOfDay;
    @XmlElement
    private TimeOfDayInfo endTimeOfDay;
    @XmlElement
    private TimeAmountInfo interval;
    @XmlElement
    private TimeAmountInfo gap;
    @XmlAnyElement
    private List<Element> _futureElements;

    public AppointmentSlotRulesInfo() {

    }

    public AppointmentSlotRulesInfo(AppointmentSlotRules appointmentSlotRules) {
        if (null != appointmentSlotRules) {
            this.weekdays = new ArrayList<Integer>(appointmentSlotRules.getWeekdays());
            this.startTimeOfDay = (null != appointmentSlotRules.getStartTimeOfDay()) ? new TimeOfDayInfo(appointmentSlotRules.getStartTimeOfDay()) : null;
            this.endTimeOfDay = (null != appointmentSlotRules.getEndTimeOfDay()) ? new TimeOfDayInfo(appointmentSlotRules.getEndTimeOfDay()) : null;
            this.interval = (null != appointmentSlotRules.getInterval()) ? new TimeAmountInfo(appointmentSlotRules.getInterval()) : null;
            this.gap = (null != appointmentSlotRules.getGap()) ? new TimeAmountInfo(appointmentSlotRules.getGap()) : null;
        }
    }

    @Override
    public List<Integer> getWeekdays() {
        return this.weekdays;
    }

    @Override
    public TimeOfDayInfo getStartTimeOfDay() {
        return this.startTimeOfDay;
    }

    @Override
    public TimeOfDayInfo getEndTimeOfDay() {
        return this.endTimeOfDay;
    }

    @Override
    public TimeAmountInfo getInterval() {
        return this.interval;
    }

    @Override
    public TimeAmountInfo getGap() {
        return this.gap;
    }
}
