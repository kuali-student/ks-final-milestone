/**
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Charles on 4/17/13
 */
package org.kuali.student.r2.common.util;

import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.infc.TimeOfDay;

import java.util.List;

/**
 * Formats and validates time (only accurate to the minute)
 *
 * @author Kuali Student Team
 */
public class TimeOfDayHelper {
    public static final long MILLIS_PER_MINUTE = 60000L;
    public static final int MINUTES_PER_HOUR = 60;

    public static TimeOfDay createTimeOfDay(int normalHours, int minutes, TimeOfDayAmPmEnum amOrPm) throws InvalidParameterException {
        if (normalHours < 1 || normalHours > 12 || minutes < 0 || minutes > 59) {
            throw new InvalidParameterException("Invalid values for hours or minutes");
        }
       // Convert to military hours (0-23)
        int milHours = normalHours;
        if (amOrPm == TimeOfDayAmPmEnum.AM) {
            if (normalHours == 12) {
                milHours = 0;
            }
        } else {
            // is PM
            if (normalHours != 12) {
                milHours += 12;
            }
        }
        return createTimeOfDayInMilitary(milHours, minutes);
    }

    public static TimeOfDay createTimeOfDayInMilitary(int milHours, int minutes) throws InvalidParameterException {
        if (milHours < 0 || milHours > 23 || minutes < 0 || minutes > 59) {
            throw new InvalidParameterException("Invalid values for military hours or minutes");
        }

        // Now compute milliseconds
        long millis = (milHours * MINUTES_PER_HOUR + minutes) * MILLIS_PER_MINUTE;
        TimeOfDayInfo info = new TimeOfDayInfo();
        info.setMilliSeconds(millis);
        return info;
    }

    public static String formatTimeOfDay(TimeOfDay timeOfDay) throws InvalidParameterException {
        return formatTimeOfDay(timeOfDay, null);
    }

    public static String formatTimeOfDay(TimeOfDay timeOfDay, List<TimeOfDayFormattingEnum> options) throws InvalidParameterException {
        int hours = 0;
        boolean isMilitary = false;
        boolean skip = false;
        if (options == null) {
            skip = true;
        }
        if (!skip && options.contains(TimeOfDayFormattingEnum.USE_MILITARY_TIME)) {
            isMilitary = true;
            hours = getHoursInMilitaryTime(timeOfDay);
        } else {
            hours = getHours(timeOfDay);
        }
        String hoursStr = hours + "";
        if (!skip && options.contains(TimeOfDayFormattingEnum.USE_TWO_DIGITS_FOR_HOURS) && hoursStr.length() < 2) {
            hoursStr = "0" + hoursStr;
        }
        int minutes = getMinutes(timeOfDay);
        String minutesStr = minutes + "";
        if (minutesStr.length() < 2) {
            minutesStr = "0" + minutesStr;
        }
        boolean isAM = isAM(timeOfDay);
        String amOrPm = "";
        if (!isMilitary) {
            if (!skip && options.contains(TimeOfDayFormattingEnum.USE_ALL_CAPS_AM_PM)) {
                amOrPm = isAM ? "AM" : "PM";
            } else if (!skip && options.contains(TimeOfDayFormattingEnum.USE_ALL_CAPS_AM_PM)) {
                amOrPm = isAM ? "Am" : "Pm";
            } else {
                amOrPm = isAM ? "am" : "pm";
            }
            if (!skip && options.contains(TimeOfDayFormattingEnum.USE_ONLY_FIRST_LETTER_AM_PM)) {
                // Extract out the first letter
                amOrPm = amOrPm.substring(0, 1);
            }
        }
        String formattedTime = hoursStr + ":" + minutesStr;
        if (!isMilitary) {
            formattedTime += " " + amOrPm;
        }
        return formattedTime;
    }

    /**
     * A non-mutating version that converts time of day to nearest minute (since it stores milliseconds).
     * Note that it does truncation, not rounding.
     * @param input A TimeOfDayInfo object
     * @return A new TimeOfDayInfo object rounded to nearest minute
     */
    public static TimeOfDayInfo roundToNearestMinute(TimeOfDay input) {
        if (input == null) {
            return null;
        }
        long newMillis = (input.getMilliSeconds() / MILLIS_PER_MINUTE) * MILLIS_PER_MINUTE;
        TimeOfDayInfo newInfo = new TimeOfDayInfo();
        newInfo.setMilliSeconds(newMillis);
        return newInfo;
    }

    /**
     * Returns hour of day in standard time (e.g. from 1-12).  Note that 1 PM and 1 AM would both return 1.
     * @param timeOfDay A TimeOfDayInfo object
     * @return Hours of day in standard time
     * @throws InvalidParameterException If millis is null or negative or too big, then this exception is thrown.
     */

    public static int getHours(TimeOfDay timeOfDay) throws InvalidParameterException {
        int militaryHours = getHoursInMilitaryTime(timeOfDay); // Guarantees between 0-23 (or exception thrown)
        int normalHours = 0;
        if (militaryHours == 0) {
            normalHours = 12; // midnight
        } else if (militaryHours <= 12) {
            normalHours = militaryHours;
        } else {
            normalHours = militaryHours - 12;
        }
        return normalHours;
    }

    /**
     * Returns hours from 0-23 in military time.
     * @param timeOfDay A TimeOfDayInfo object
     * @return Hours of day in military time
     * @throws InvalidParameterException If millis is null or negative or too big, then this exception is thrown.
     */
    public static int getHoursInMilitaryTime(TimeOfDay timeOfDay) throws InvalidParameterException {
        validateTimeOfDayInfo(timeOfDay);
        long timeInMillis = timeOfDay.getMilliSeconds();
        long hours = timeInMillis / (MILLIS_PER_MINUTE * MINUTES_PER_HOUR);
        if (hours < 0 || hours > 23) {
            throw new InvalidParameterException("Contains an invalid time of day");
        }
        return (int) hours;
    }

    /**
     * Returns hours from 0-59 representing the number of minutes after the hour (thus, 1:24 PM would return
     * 24).
     * @param timeOfDay A TimeOfDayInfo object
     * @return Minutes after the hour
     * @throws InvalidParameterException If millis is null or negative or too big, then this exception is thrown.
     */
    public static int getMinutes(TimeOfDay timeOfDay) throws InvalidParameterException {
        validateTimeOfDayInfo(timeOfDay);
        long timeInMillis = timeOfDay.getMilliSeconds();
        int minutesInDay = (int) (timeInMillis / MILLIS_PER_MINUTE);
        int minuteOffset = minutesInDay % MINUTES_PER_HOUR;
        return minuteOffset;
    }

    /**
     * Returns true if time of day is AM, that is between midnight and 11:59 AM
     * @param timeOfDay A TimeOfDayInfo object
     * @return true, if time is AM.
     * @throws InvalidParameterException If millis is null or negative or too big, then this exception is thrown.
     */
    public static boolean isAM(TimeOfDay timeOfDay) throws InvalidParameterException {
        validateTimeOfDayInfo(timeOfDay);
        int milHours = getHoursInMilitaryTime(timeOfDay);
        return milHours < 12; // 0-11 is AM
    }

    /**
     * Returns true if time of day is PM, that is between noon and 11:59 PM
     * @param timeOfDay A TimeOfDayInfo object
     * @return true, if time is PM.
     * @throws InvalidParameterException If millis is null or negative or too big, then this exception is thrown.
     */
    public static boolean isPM(TimeOfDay timeOfDay) throws InvalidParameterException {
        return !isAM(timeOfDay);
    }

    /**
     * Validates by not throwing an exception
     * @param timeOfDay A TimeOfDayInfo object
     * @throws InvalidParameterException
     */
    public static void validateTimeOfDayInfo(TimeOfDay timeOfDay) throws InvalidParameterException {
        if (timeOfDay == null) {
            throw new InvalidParameterException("getMilliSeconds() is null");
        }
        Long timeInMillis = timeOfDay.getMilliSeconds();
        if (timeInMillis < 0) {
            throw new InvalidParameterException("getMilliSeconds() is negative");
        }
        Long threshold = 24 * MINUTES_PER_HOUR * MILLIS_PER_MINUTE; // Number of milliseconds in a day
        threshold -= MILLIS_PER_MINUTE; // Remove one minute
        if (timeInMillis > threshold) {
            throw new InvalidParameterException("getMilliSeconds() is larger than 11:59 PM");
        }
    }
}
