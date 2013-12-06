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
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.kuali.student.r2.common.infc.TimeOfDay;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeOfDayInfo", propOrder = {"hour", "minute", "second", "_futureElements" })
public class TimeOfDayInfo implements TimeOfDay, Serializable {

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
     * @return hour of the day
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
     * @param hour
     */
    public void setHour(Integer hour) {
        this.hour = hour;
    }

    /**
     *
     * @param minute
     */
    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    /**
     *
     * @param second
     */
    public void setSecond(Integer second) {
        this.second = second;
    }

    /**
     *
     * @param date the Date to add the timeOfDay
     * @param timeOfDay an offset from midnight to add to date
     * @return the Date result of adding timeOfDay to date
     */
    @Override
    public Date getDateWithTimeOfDay(Date date, TimeOfDay timeOfDay) {
        LocalDateTime localDateTime = new LocalDateTime(date);
        localDateTime = localDateTime.plusHours(timeOfDay.getHour());
        localDateTime = localDateTime.plusMinutes(timeOfDay.getMinute() == null ? 0 : timeOfDay.getMinute());
        localDateTime = localDateTime.plusSeconds(timeOfDay.getSecond() == null ? 0 : timeOfDay.getSecond());
        return localDateTime.toDate();
    }

    /**
     * @return the Milli Seconds since midnight
     * @deprecated instead use getHour/getMinute/getSecond
     */
    @Override
    @Deprecated
    public Long getMilliSeconds() {
        LocalTime localTime = new LocalTime(hour, minute, second);
        return (long)localTime.getMillisOfDay();
    }

    /**
     * used to set the time since midnight, however precision will
     * be stored only to the second.
     * @param milliSeconds  the Milli Seconds since midnight
     * @deprecated instead use setHour/setMinute/setSecond
     */
    @Deprecated
    public void setMilliSeconds(Long milliSeconds) {
        long hours = TimeUnit.MILLISECONDS.toHours(milliSeconds);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliSeconds) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliSeconds) % 60;

        LocalTime localTime = new LocalTime((int)hours, (int)minutes, (int)seconds);
        setHour(localTime.getHourOfDay());
        setMinute(localTime.getMinuteOfHour());
        setSecond(localTime.getSecondOfMinute());
    }

    /**
     * Tests if this TimeOfDay is after the specified TimeOfDay.
     * @param timeOfDay the specified TimeOfDay
     * @return true if this TimeOfDay is after the specified TimeOfDay, false otherwise.
     */
    public boolean isAfter(TimeOfDay timeOfDay) {
        LocalTime otherTimeOfDay = new LocalTime(timeOfDay.getHour(), timeOfDay.getMinute(), timeOfDay.getSecond());
        LocalTime thisTimeOfDay = new LocalTime(this.getHour(), this.getMinute(), this.getSecond());
        return thisTimeOfDay.isAfter(otherTimeOfDay);
    }

    /**
     * Tests if this TimeOfDay is before the specified TimeOfDay.
     * @param timeOfDay the specified TimeOfDay
     * @return true if this TimeOfDay is before the specified TimeOfDay, false otherwise.
     */
    public boolean isBefore(TimeOfDay timeOfDay) {
        LocalTime otherTimeOfDay = new LocalTime(timeOfDay.getHour(), timeOfDay.getMinute(), timeOfDay.getSecond());
        LocalTime thisTimeOfDay = new LocalTime(this.getHour(), this.getMinute(), this.getSecond());
        return thisTimeOfDay.isBefore(otherTimeOfDay);
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
        StringBuilder timeOfDay = new StringBuilder("TimeOfDayInfo{");
        timeOfDay.append(getHour());
        timeOfDay.append(":").append((getMinute() != 0 ? getMinute() : "00"));
        timeOfDay.append(":").append(getSecond());
        timeOfDay.append("}");
        return timeOfDay.toString();
    }
}
