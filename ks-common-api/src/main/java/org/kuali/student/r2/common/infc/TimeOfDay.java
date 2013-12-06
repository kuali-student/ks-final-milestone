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

package org.kuali.student.r2.common.infc;

import java.util.Date;

/**
 * Maintains just the time portion of a day
 * any fractional seconds are effectively truncated and
 * not accessible, e.g. 9:30:12.53 (where .53 is 53/100 of
 * a second) would be treated as 9:30:12
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface TimeOfDay {
    /**
     * Hour of day offset from midnight
     * @name Hour
     */
    public Integer getHour();

    /**
     * Minute offset from hour of day
     * @name Minute
     */
    public Integer getMinute();

    /**
     * Second offset from Minute of Hour
     * @name Second
     */
    public Integer getSecond();

    /**
     *
     * @param date a java.util.Date to which timeOfDay is added
     * @param timeOfDay the TimeOfDay that is added to the date parameter
     * @return a java.util.Date that is the sum of date and timeOfDay
     */
    public Date getDateWithTimeOfDay(Date date, TimeOfDay timeOfDay);

    /**
     * Offset from midnight in milliseconds, representing the time portion of a day
     * @name Milli Seconds
     * @deprecated use getHour(),getMinute(),getSecond() instead.
     */
    @Deprecated
    public Long getMilliSeconds();
}
