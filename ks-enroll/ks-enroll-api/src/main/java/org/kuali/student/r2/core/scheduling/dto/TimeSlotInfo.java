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

package org.kuali.student.r2.core.scheduling.dto;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.scheduling.infc.TimeSlot;
import org.w3c.dom.Element;

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
@XmlType(name = "TimeSlotInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr",
        "weekdays", "startHour", "startMinute", "endHour", "endMinute",
        "meta", "attributes", "_futureElements"})
public class TimeSlotInfo extends IdEntityInfo implements TimeSlot {

    @XmlElement
    private List<Integer> weekdays;
    @XmlElement
    private Integer startHour;
    @XmlElement
    private Integer startMinute;
    @XmlElement
    private Integer endHour;
    @XmlElement
    private Integer endMinute;
    @XmlAnyElement
    private List<Element> _futureElements;

    public TimeSlotInfo() {
    }

    public TimeSlotInfo(TimeSlot timeSlot) {
        if (null != timeSlot) {
            this.weekdays = new ArrayList<Integer>(timeSlot.getWeekdays());
            this.startHour = timeSlot.getStartHour();
            this.startMinute = timeSlot.getStartMinute();
            this.endHour = timeSlot.getEndHour();
            this.endMinute = timeSlot.getEndMinute();
        }
    }

    @Override
    public List<Integer> getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(List<Integer> weekdays) {
        this.weekdays = weekdays;
    }

    @Override
    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    @Override
    public Integer getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(Integer endMinute) {
        this.endMinute = endMinute;
    }

    @Override
    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    @Override
    public Integer getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(Integer startMinute) {
        this.startMinute = startMinute;
    }

}
