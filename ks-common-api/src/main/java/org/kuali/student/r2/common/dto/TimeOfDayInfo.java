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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.TimeOfDay;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeOfDayInfo", propOrder = {"hour", "minute", "second", "_futureElements" })
public class TimeOfDayInfo implements TimeOfDay, Comparable<TimeOfDay>, Serializable {

    @XmlElement
    private Integer hour;
    @XmlElement
    private Integer minute;
    @XmlElement
    private Integer second;

    @XmlAnyElement
    private List<Object> _futureElements;

    public TimeOfDayInfo() {

    }

    public TimeOfDayInfo(TimeOfDay timeOfDay) {
        if(null != timeOfDay) {
            hour = timeOfDay.getHour();
            minute = timeOfDay.getMinute();
            second = timeOfDay.getSecond();
        }
    }

    public TimeOfDayInfo(Integer hour) {
        this(hour, null, null);
    }

    public TimeOfDayInfo(Integer hour, Integer minute) {
        this(hour, minute, null);
    }

    public TimeOfDayInfo(Integer hour, Integer minute, Integer second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    /**
     *
     * @return hour of the day in military time (14 is 2pm)
     */
    @Override
    public Integer getHour() {
        return this.hour == null ? 0 : this.hour;
    }

    /**
     *
     * @return minute of the hour
     */
    @Override
    public Integer getMinute() {
        return this.minute == null ? 0 : this.minute;
    }

    /**
     *
     * @return second of the minute
     */
    @Override
    public Integer getSecond() {
        return this.second == null ? 0 : this.second;
    }

    /**
     *
     * @param hour of the day in military time (14 is 2pm)
     */
    public void setHour(Integer hour) {
        this.hour = hour;
    }

    /**
     *
     * @param minute of the hour
     */
    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    /**
     *
     * @param second of the minute
     */
    public void setSecond(Integer second) {
        this.second = second;
    }

    /**
     *
     * @param date a java.util.Date to which timeOfDay is added
     * @param timeOfDay the TimeOfDay that is added to the date parameter
     * @return a java.util.Date that is the sum of date and timeOfDay
     */
    public static Date getDateWithTimeOfDay(Date date, TimeOfDay timeOfDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, timeOfDay.getHour());
        cal.set(Calendar.MINUTE, (timeOfDay.getMinute() == null ? 0 : timeOfDay.getMinute()));
        cal.set(Calendar.SECOND, (timeOfDay.getSecond() == null ? 0 : timeOfDay.getSecond()));
        return cal.getTime();
    }

    /**
     * @return the Milli Seconds since midnight
     * @deprecated instead use getHour/getMinute/getSecond
     */
    @Override
    @Deprecated
    public Long getMilliSeconds() {
        // Use TimeOfDayHelper.getMillis(timeOfDayObj) instead
        throw new RuntimeException("getMilliSeconds is deprecated");
    }

    /**
     * used to set the time since midnight, however precision will
     * be stored only to the second.
     * @param milliSeconds  the Milli Seconds since midnight
     * @deprecated instead use setHour/setMinute/setSecond
     */
    @Deprecated
    public void setMilliSeconds(Long milliSeconds) {
        // Use TimeOfDayHelper.setMillis to create a new TimeOfDayHelper
        throw new RuntimeException("setMilliSeconds: Method is deprecated");
    }

    /**
     * Tests if this TimeOfDay is after the specified TimeOfDay. The assumption is
     * that timeOfDay.hour is military time (14 is 2pm)
     * @param other the specified TimeOfDay
     * @return true if this TimeOfDay is after the specified TimeOfDay, false otherwise.
     */
    @Override
    public boolean isAfter(TimeOfDay other) {
        return compareTo(other) > 0;
    }

    /**
     * Tests if this TimeOfDay is before the specified TimeOfDay. The assumption is
     * that timeOfDay.hour is military time (14 is 2pm)
     * @param other the specified TimeOfDay
     * @return true if this TimeOfDay is before the specified TimeOfDay, false otherwise.
     */
    @Override
    public boolean isBefore(TimeOfDay other) {
        return compareTo(other) < 0;
    }

    /**
     * Compares two TimeOfDays for equality. The result is true if and
     * only if the argument is not null and is a TimeOfDay object that represents the same
     * time of day as this object.
     * @param obj the object to compare with
     * @return true if the objects are the same; false otherwise.
     */
    public boolean equals (Object obj) {
        if(obj == null || !(obj instanceof TimeOfDayInfo))
            return false;
        TimeOfDay other = (TimeOfDay) obj;
        if(!getHour().equals(other.getHour())) {
            return false;
        }
        if(!getMinute().equals(other.getMinute())) {
            return false;
        }
        return getSecond().equals(other.getSecond());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getHour() == null ? 0 : getHour());
        result = 31 * result + (getMinute() == null ? 0 : getMinute());
        result = 31 * result + (getSecond() == null ? 0 : getSecond());
        return result;
    }

    @Override
    public String toString() {
        return "TimeOfDayInfo{" + getHour()
                + ":" + (getMinute() != 0 ? getMinute() : "00") + ":"
                + (getSecond() != 0 ? getSecond() : "00") +"}";
    }

    /**
     *
     * @param other a TimeOfDay to check against
     * @return negative if this is less, zero if equal, positive if greater
     * @throws NullPointerException if the TimeOfDay is null
     */
    public int compareTo(TimeOfDay other) {
        if(this.getHour() > other.getHour()) {
            return 1;
        }
        if(this.getHour() < other.getHour()) {
            return -1;
        }
        // this.hour and other.hour are equal so check the minutes
        if(this.getMinute() > other.getMinute())
            return 1;
        // if this.minute is less than other.minute then we know it is less
        if(this.getMinute() < other.getMinute())
            return -1;
        // this.minute and other.minute are equal so check the seconds
        if(this.getSecond() > other.getSecond())
            return 1;
        // if this.second is less than other.second then we know it is less
        if(this.getSecond() < other.getSecond())
            return -1;

        return 0;
    }
}
