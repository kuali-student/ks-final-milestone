/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.r2.core.scheduling.util;

import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Collection of utility methods for the Scheduling Service
 *
 * @author andrewlubbers
 * @author Mezba Mahtab
 */
public class SchedulingServiceUtil {

    /**
     * Converts a list of Calendar constants (i.e. Calendar.MONDAY, Calendar.FRIDAY) into a string of characters representing those days
     * Used in DTO/Entity translations for TimeSlot
     *
     * @param weekdaysList
     * @return
     */
    public static String weekdaysList2WeekdaysString(List<Integer> weekdaysList) {
        StringBuilder result = new StringBuilder();

        for(Integer day : weekdaysList) {
            switch (day) {
                case Calendar.MONDAY: {
                    result.append(SchedulingServiceConstants.MONDAY_TIMESLOT_DAY_CODE);
                    break;
                }
                case Calendar.TUESDAY: {
                    result.append(SchedulingServiceConstants.TUESDAY_TIMESLOT_DAY_CODE);
                    break;
                }
                case Calendar.WEDNESDAY: {
                    result.append(SchedulingServiceConstants.WEDNESDAY_TIMESLOT_DAY_CODE);
                    break;
                }
                case Calendar.THURSDAY: {
                    result.append(SchedulingServiceConstants.THURSDAY_TIMESLOT_DAY_CODE);
                    break;
                }
                case Calendar.FRIDAY: {
                    result.append(SchedulingServiceConstants.FRIDAY_TIMESLOT_DAY_CODE);
                    break;
                }
                case Calendar.SATURDAY: {
                    result.append(SchedulingServiceConstants.SATURDAY_TIMESLOT_DAY_CODE);
                    break;
                }
                case Calendar.SUNDAY: {
                    result.append(SchedulingServiceConstants.SUNDAY_TIMESLOT_DAY_CODE);
                    break;
                }
            }
        }

        return result.toString();
    }

    public static List<Integer> weekdaysString2WeekdaysList(String weekdaysString) {
        List<Integer> result = new ArrayList<Integer>();

        checkStringForDayCode(SchedulingServiceConstants.MONDAY_TIMESLOT_DAY_CODE, Calendar.MONDAY, result, weekdaysString);
        checkStringForDayCode(SchedulingServiceConstants.TUESDAY_TIMESLOT_DAY_CODE, Calendar.TUESDAY, result, weekdaysString);
        checkStringForDayCode(SchedulingServiceConstants.WEDNESDAY_TIMESLOT_DAY_CODE, Calendar.WEDNESDAY, result, weekdaysString);
        checkStringForDayCode(SchedulingServiceConstants.THURSDAY_TIMESLOT_DAY_CODE, Calendar.THURSDAY, result, weekdaysString);
        checkStringForDayCode(SchedulingServiceConstants.FRIDAY_TIMESLOT_DAY_CODE, Calendar.FRIDAY, result, weekdaysString);
        checkStringForDayCode(SchedulingServiceConstants.SATURDAY_TIMESLOT_DAY_CODE, Calendar.SATURDAY, result, weekdaysString);
        checkStringForDayCode(SchedulingServiceConstants.SUNDAY_TIMESLOT_DAY_CODE, Calendar.SUNDAY, result, weekdaysString);

        return result;
    }

    private static void checkStringForDayCode(String codeInString, Integer integerDayCode, List<Integer> result, String testString) {
        if (testString.contains(codeInString)) {
            result.add(integerDayCode);
        }
    }

    /**
     * Compares two time slots and sees if they 'overlap'. Will return true even if there is a millisecond overlap.
     * @author Mezba Mahtab
     * @param timeSlotInfo1 the first time slot
     * @param timeSlotInfo2 the second time slot
     * @return true if the timeslots share at least one common weekday and on that day the start and and end times of one time slot is within
     * the start and end times of the other timeslot.
     */
    public static boolean areTimeSlotsInConflict(TimeSlotInfo timeSlotInfo1, TimeSlotInfo timeSlotInfo2) {

        // Check if any of weekdays is common. If the two TimeSlots are on different weekdays,
        // no need to check for start/end times.
        boolean hasCommonWeekday = false;
        for (Integer dayOfWeekTs1: timeSlotInfo1.getWeekdays()) {
            if (timeSlotInfo2.getWeekdays().contains(dayOfWeekTs1)) {
                hasCommonWeekday = true;
                break;
            }
        }
        if (!hasCommonWeekday) return false;
        // there is a common weekday, so now check if there is an overlap of time.
        // Check if the start times or end times are same, then they overlap.
        if ((timeSlotInfo1.getStartTime().equals(timeSlotInfo2.getStartTime()))
                || (timeSlotInfo1.getEndTime().equals(timeSlotInfo2.getEndTime()))) {
            return true;
        }
        // now they have differing start times or end times.

        // if first timeslot starts before second time slot, it must end before second timeslot starts
        if (timeSlotInfo1.getStartTime().isBefore(timeSlotInfo2.getStartTime())) {
            if (timeSlotInfo1.getEndTime().isBefore(timeSlotInfo2.getStartTime())) {
                return false;
            } else {
                return true;
            }
        }

        // if first timeslot starts after second time slot, it must start after second timeslot ends
        if (timeSlotInfo1.getStartTime().isAfter(timeSlotInfo2.getStartTime())) {
            if (timeSlotInfo1.getStartTime().isAfter(timeSlotInfo2.getEndTime())) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
