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

package org.kuali.student.r2.core.class1.scheduling.util;

import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Collection of utility methods for the Scheduling Service
 *
 * @author andrewlubbers
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

        StringBuilder testString = new StringBuilder(weekdaysString);

        while(testString.length() != 0) {
            checkStringForDayCode(SchedulingServiceConstants.MONDAY_TIMESLOT_DAY_CODE, Calendar.MONDAY, result, testString);
            checkStringForDayCode(SchedulingServiceConstants.TUESDAY_TIMESLOT_DAY_CODE, Calendar.TUESDAY, result, testString);
            checkStringForDayCode(SchedulingServiceConstants.WEDNESDAY_TIMESLOT_DAY_CODE, Calendar.WEDNESDAY, result, testString);
            checkStringForDayCode(SchedulingServiceConstants.THURSDAY_TIMESLOT_DAY_CODE, Calendar.THURSDAY, result, testString);
            checkStringForDayCode(SchedulingServiceConstants.FRIDAY_TIMESLOT_DAY_CODE, Calendar.FRIDAY, result, testString);
            checkStringForDayCode(SchedulingServiceConstants.SATURDAY_TIMESLOT_DAY_CODE, Calendar.SATURDAY, result, testString);
            checkStringForDayCode(SchedulingServiceConstants.SUNDAY_TIMESLOT_DAY_CODE, Calendar.SUNDAY, result, testString);
        }

        return result;
    }

    private static void checkStringForDayCode(String codeInString, Integer integerDayCode, List<Integer> result, StringBuilder testString) {
        if (testString.toString().startsWith(codeInString)) {
            testString.delete(0, codeInString.length());
            result.add(integerDayCode);
        }
    }

}
