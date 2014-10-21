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

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
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
        "meta", "attributes", "_futureElements" }) 
public class TimeSlotInfo extends IdEntityInfo implements TimeSlot, Serializable {

    @XmlElement
    private List<Integer> weekdays;
    @XmlElement
    private TimeOfDayInfo startTime;
    @XmlElement
    private TimeOfDayInfo endTime;
    @XmlAnyElement
    private List<Object> _futureElements;  

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
     * TimeSlotInfo AND the type keys are the same AND the weekdays are the same (in the same order)
     * AND the start time is same AND the end time is same.
     * Weekdays, start time, and end time can all be null (for a TBA time slot), so we need to check that as well
     * if corresponding fields in both objects are both null, they are considered to be the same.
     */
    public boolean equals (Object obj) {
        if (!(obj instanceof TimeSlotInfo)) {
            return false;
        }
        TimeSlotInfo ts = (TimeSlotInfo) obj;
        //  Type keys must be equal
        if (!(StringUtils.equals(this.getTypeKey(), ts.getTypeKey()))) {
            return false;
        }
        //  Weekdays must be either both null or both not null (XOR)
        if ((this.weekdays == null) != (ts.weekdays == null)) {
            return false;
        }
        //  Check for null before comparing values
        if ((this.weekdays != null) && (ts.weekdays != null)) {
            //  To avoid IndexOutOfBoundsExceptions see if the weekdays collections have the same element count before trying to compare the elements.
            if (this.getWeekdays().size() != ts.getWeekdays().size()) {
                return false;
            }
            for (int i = 0; i < this.weekdays.size(); i++) {
                if (!this.weekdays.get(i).equals(ts.weekdays.get(i))) {
                    return false;
                }
            }
        }
        //  Start times must be either both null or both not null (XOR)
        if ((this.startTime == null) != (ts.startTime == null)) {
            return false;
        }
        //  Check for null before comparing values (if this.startTime != null, then by our previous test,
        //  ts.startTime is also != null, so we only need to test one)
        if ((this.startTime != null) && (!this.startTime.equals(ts.startTime))) {
            return false;
        }
        //  End times must be either both null or both not null (XOR)
        if ((this.endTime == null) != (ts.endTime == null)) {
            return false;
        }
        //  Check for null before comparing values (if this.endTime != null, then by our previous test,
        //  ts.endTime is also != null, so we only need to test one)
        if ((this.endTime != null) && (!this.endTime.equals(ts.endTime))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = weekdays != null ? weekdays.hashCode() : 0;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("TimeSlotInfo{type=%s,weekdays=%s,id=%s,startTime=%s,endTime=%s}",
                getTypeKey(), weekdays, getId(), startTime, endTime);
    }
}
