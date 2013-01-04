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

package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.TimeOfDay;

//import javax.xml.bind.Element;
//import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeOfDayInfo", propOrder = {"milliSeconds", "_futureElements" }) 
public class TimeOfDayInfo implements TimeOfDay, Serializable {

    @XmlElement
    private Long milliSeconds;
    
    @XmlAnyElement
    private List<Object> _futureElements;  

    public TimeOfDayInfo() {

    }

    public TimeOfDayInfo(TimeOfDay timeOfDay) {
        if(null != timeOfDay) {
            this.milliSeconds = timeOfDay.getMilliSeconds();
        }
    }

    @Override
    public Long getMilliSeconds() {
        return this.milliSeconds;
    }

    public void setMilliSeconds(Long milliSeconds) {
        this.milliSeconds = milliSeconds;
    }

    /**
     * Tests if this TimeOfDay is after the specified TimeOfDay.
     * @param timeOfDay the specified TimeOfDay
     * @return true if this TimeOfDay is after the specified TimeOfDay, false otherwise.
     */
    public boolean isAfter(TimeOfDay timeOfDay) {
        return this.milliSeconds>timeOfDay.getMilliSeconds();
    }

    /**
     * Tests if this TimeOfDay is before the specified TimeOfDay.
     * @param timeOfDay the specified TimeOfDay
     * @return true if this TimeOfDay is before the specified TimeOfDay, false otherwise.
     */
    public boolean isBefore(TimeOfDay timeOfDay) {
        return this.milliSeconds<timeOfDay.getMilliSeconds();
    }

    /**
     * Compares two TimeOfDays for equality. The result is true if and
     * only if the argument is not null and is a TimeOfDay object that represents the same
     * point in time, to the millisecond, as this object.
     * @param obj the object to compare with
     * @return true if the objects are the same; false otherwise.
     */
    public boolean equals (Object obj) {
        TimeOfDay timeOfDay = (TimeOfDay) obj;
        return this.milliSeconds==timeOfDay.getMilliSeconds();
    }

    @Override
    public int hashCode() {
        return milliSeconds != null ? milliSeconds.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TimeOfDayInfo{" +
                "milliSeconds=" + milliSeconds +
                '}';
    }
}
