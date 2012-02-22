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

package org.kuali.student.core.appointment.dto;

import org.kuali.student.core.appointment.infc.AppointmentSlotRule;
import org.kuali.student.common.dto.TimeAmountInfo;
import org.kuali.student.common.dto.TimeOfDayInfo;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppointmentSlotRuleInfo", propOrder = {"weekdays", "startTimeOfDay", "endTimeOfDay",
        "interval", "gap", "_futureElements"})
public class AppointmentSlotRuleInfo implements AppointmentSlotRule {

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

    public AppointmentSlotRuleInfo() {

    }

    public AppointmentSlotRuleInfo(AppointmentSlotRule appointmentSlotRule) {
        if (null != appointmentSlotRule) {
            this.weekdays = new ArrayList<Integer>(appointmentSlotRule.getWeekdays());
            this.startTimeOfDay = (null != appointmentSlotRule.getStartTimeOfDay()) ? new TimeOfDayInfo(appointmentSlotRule.getStartTimeOfDay()) : null;
            this.endTimeOfDay = (null != appointmentSlotRule.getEndTimeOfDay()) ? new TimeOfDayInfo(appointmentSlotRule.getEndTimeOfDay()) : null;
            this.interval = (null != appointmentSlotRule.getInterval()) ? new TimeAmountInfo(appointmentSlotRule.getInterval()) : null;
            this.gap = (null != appointmentSlotRule.getGap()) ? new TimeAmountInfo(appointmentSlotRule.getGap()) : null;
        }
    }

    public void setWeekdays(List<Integer> weekdays) {
        this.weekdays = weekdays;
    }

    @Override
    public List<Integer> getWeekdays() {
        if (null == this.weekdays) {
            this.weekdays = new ArrayList<Integer>();
        }
        return this.weekdays;
    }

    public void setStartTimeOfDay(TimeOfDayInfo startTimeOfDay) {
        this.startTimeOfDay = startTimeOfDay;
    }

    @Override
    public TimeOfDayInfo getStartTimeOfDay() {
        return this.startTimeOfDay;
    }

    public void setEndTimeOfDay(TimeOfDayInfo endTimeOfDay) {
        this.endTimeOfDay = endTimeOfDay;
    }

    @Override
    public TimeOfDayInfo getEndTimeOfDay() {
        return this.endTimeOfDay;
    }

    public void setInterval(TimeAmountInfo interval) {
        this.interval = interval;
    }

    @Override
    public TimeAmountInfo getInterval() {
        return this.interval;
    }

    public void setGap(TimeAmountInfo gap) {
        this.gap = gap;
    }

    @Override
    public TimeAmountInfo getGap() {
        return this.gap;
    }
}
