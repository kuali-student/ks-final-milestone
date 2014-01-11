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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.infc.TimeOfDay;

import java.util.ArrayList;
import java.util.List;

/**
 * Formats and validates time (only accurate to the minute)
 * Jan 10, 2014: Cut down the size of this since TimeOfDayInfo was altered to make it easier to use.
 *
 * @author Kuali Student Team
 */
public class TimeOfDayHelper {
    public static Logger LOGGER = Logger.getLogger(TimeOfDayHelper.class);

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
        return new TimeOfDayInfo(milHours, minutes);
    }

    public static Long getMillis(TimeOfDay tod) {
        if (tod == null) {
            return null;
        }
        long hourInMillis = 0;
        if (tod.getHour() != null) {
            hourInMillis = tod.getHour() * 60L * 60L * 1000L;
        }
        long minuteInMillis = 0;
        if (tod.getMinute() != null) {
            minuteInMillis = tod.getMinute() * 60L * 1000L;
        }
        long secondInMillis = 0;
        if (tod.getHour() != null) {
            secondInMillis = tod.getSecond() * 1000L;
        }
        Long total = hourInMillis + minuteInMillis + secondInMillis;
        return total;
    }

    public static TimeOfDayInfo setMillis(Long millis) {
        if (millis == null) {
            return null;
        }
        if (millis > 24L * 60L * 60L * 1000L || millis < 0) {
            return null; // Not the best solution
        }
        long totalSeconds = millis / 1000L;
        long totalMinutes = totalSeconds / 60;
        int seconds = (int) (totalSeconds % 60);
        int minutes = (int) (totalMinutes % 60);
        int hours = (int) (totalMinutes / 60);
        return new TimeOfDayInfo(hours, minutes, seconds);
    }


    /**
     * Creates a time string in hh:mm aa format. Does not suffer from DST issues.
     * Used to display times in Manage CO screens for each of the activity offerings
     * (This was refactored from SchedulingServiceUtil::makeFormattedTimeFromMillis
     * which is why there are two versions of formatting
     * time of day).
     *
     * @param tod Time of day
     * @return  A time string.
     */
    public static String makeFormattedTimeForAOSchedules(TimeOfDayInfo tod) {
        List<TimeOfDayFormattingEnum> options = new ArrayList<TimeOfDayFormattingEnum>();
        String result = "error";
        try {
            options.add(TimeOfDayFormattingEnum.USE_ALL_CAPS_AM_PM);
            options.add(TimeOfDayFormattingEnum.USE_TWO_DIGITS_FOR_HOURS);
            result = formatTimeOfDay(tod, options);
        } catch (InvalidParameterException e) {
            LOGGER.warn("Unable to format: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static String formatTimeOfDay(TimeOfDay timeOfDay) throws InvalidParameterException {
        return formatTimeOfDay(timeOfDay, null);
    }

    public static String formatTimeOfDay(TimeOfDay timeOfDay,
                                         List<TimeOfDayFormattingEnum> options) throws InvalidParameterException {
        int hours = 0;
        boolean isMilitary = false;
        boolean skip = false;
        if (options == null) {
            skip = true;
        }
        if (!skip && options.contains(TimeOfDayFormattingEnum.USE_MILITARY_TIME)) {
            isMilitary = true;
            hours = timeOfDay.getHour();
        } else {
            hours = getHours(timeOfDay);
        }
        String hoursStr = hours + "";
        if (!skip && options.contains(TimeOfDayFormattingEnum.USE_TWO_DIGITS_FOR_HOURS) && hoursStr.length() < 2) {
            hoursStr = "0" + hoursStr;
        }
        int minutes = timeOfDay.getMinute();
        String minutesStr = minutes + "";
        if (minutesStr.length() < 2) {
            minutesStr = "0" + minutesStr;
        }
        boolean isAM = isAM(timeOfDay);
        String amOrPm = "";
        if (!isMilitary) {
            if (!skip && options.contains(TimeOfDayFormattingEnum.USE_ALL_CAPS_AM_PM)) {
                amOrPm = isAM ? "AM" : "PM";
            } else if (!skip && options.contains(TimeOfDayFormattingEnum.CAPITALIZE_A_OR_P_ONLY)) {
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
     * Returns hour of day in standard time (e.g. from 1-12).  Note that 1 PM and 1 AM would both return 1.
     * @param timeOfDay A TimeOfDayInfo object
     * @return Hours of day in standard time
     * @throws InvalidParameterException If millis is null or negative or too big, then this exception is thrown.
     */

    public static int getHours(TimeOfDay timeOfDay) throws InvalidParameterException {
        int militaryHours = timeOfDay.getHour(); // Guarantees between 0-23 (or exception thrown)
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
     * Returns true if time of day is AM, that is between midnight and 11:59 AM
     * @param timeOfDay A TimeOfDayInfo object
     * @return true, if time is AM.
     * @throws InvalidParameterException If millis is null or negative or too big, then this exception is thrown.
     */
    public static boolean isAM(TimeOfDay timeOfDay) throws InvalidParameterException {
        validateTimeOfDayInfo(timeOfDay);
        return timeOfDay.getHour() < 12; // 0-11 is AM
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
        if (timeOfDay.getHour() == null || timeOfDay.getMinute() == null || timeOfDay.getSecond() == null) {
            throw new InvalidParameterException("Fields in TimeOfDay are null");
        }
        if (timeOfDay.getHour() < 0 || timeOfDay.getHour() > 23) {
            throw new InvalidParameterException("getHour() has out of range values");
        }
        if (timeOfDay.getMinute() < 0 || timeOfDay.getMinute() > 59) {
            throw new InvalidParameterException("getMinute() has out of range values");
        }
        if (timeOfDay.getSecond() < 0 || timeOfDay.getSecond() > 59) {
            throw new InvalidParameterException("getSecond() has out of range values");
        }
    }

    /**
     * Creates a new TimeOfDayInfo given a time in standard format.
     * @param time A time string of format HH:MM AM or HH:MM PM
     * @return  A new TimeOfDayInfo.
     */
    public static TimeOfDayInfo makeTimeOfDayInfoFromTimeString(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        }

        boolean isPM = true;
        if (time.endsWith("AM")) {
            isPM = false;
        }

        String t[] = time.split(":");
        int hour = Integer.valueOf(t[0]);
        int min = Integer.valueOf(t[1].substring(0, 2));

        if (isPM) {
            //  For PM times just add 12
            hour = (hour % 12) + 12;
        } else {
            // For AM times if the hour is 12 then it becomes zero. Otherwise, noop.
            if (hour == 12) {
                hour = 0;
            }
        }

        return new TimeOfDayInfo(hour, min);
    }
}
