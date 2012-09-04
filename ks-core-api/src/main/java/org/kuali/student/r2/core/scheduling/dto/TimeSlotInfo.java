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
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.core.scheduling.infc.TimeSlot;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeSlotInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr",
        "weekdays", "startTime", "endTime",
        "meta", "attributes"})//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code
public class TimeSlotInfo extends IdEntityInfo implements TimeSlot, Serializable {

    @XmlElement
    private List<Integer> weekdays;
    @XmlElement
    private TimeOfDayInfo startTime;
    @XmlElement
    private TimeOfDayInfo endTime;
//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    public TimeSlotInfo() {
    }

    public TimeSlotInfo(TimeSlot timeSlot) {
        super (timeSlot);
        if (null != timeSlot) {
            this.weekdays = new ArrayList<Integer>(timeSlot.getWeekdays());
            this.startTime = (null != timeSlot.getStartTime()) ? new TimeOfDayInfo(timeSlot.getStartTime()) : null;
            this.endTime = (null != timeSlot.getEndTime()) ? new TimeOfDayInfo(timeSlot.getEndTime()) : null;
        }
    }

    @Override
    public List<Integer> getWeekdays() {
        if (null == this.weekdays) {
            return new ArrayList<Integer>();
        }
        else {
            return this.weekdays;
        }
    }

    public void setWeekdays(List<Integer> weekdays) {
        this.weekdays = weekdays;
    }

    @Override
    public TimeOfDayInfo getStartTime() {
        return startTime;
    }

    public void setStartTime(TimeOfDayInfo startTime) {
        this.startTime = startTime;
    }

    @Override
    public TimeOfDayInfo getEndTime() {
        return endTime;
    }

    public void setEndTime(TimeOfDayInfo endTime) {
        this.endTime = endTime;
    }

    /*
     * An object is equal to this TimeSlotInfo if it's also a
     * TimeSlotInfo AND the weekdays are the same (in the same order)
     * AND the start time is same AND the end time is same.
     */
    public boolean equals (Object obj) {
        TimeSlotInfo ts = (TimeSlotInfo) obj; // will throw a ClassCastException
        for (int i=0; i<this.weekdays.size(); i++)  {
            if (!this.weekdays.get(i).equals(ts.weekdays.get(i))) { return false; }
        }
        if (!this.startTime.equals(ts.startTime)) { return false; }
        if (!this.endTime.equals(ts.endTime)) { return false; }
        return true;
    }

    @Override
    public String toString() {
        return "TimeSlotInfo{" +
                "weekdays=" + weekdays +
                ", id=" + getId() +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
