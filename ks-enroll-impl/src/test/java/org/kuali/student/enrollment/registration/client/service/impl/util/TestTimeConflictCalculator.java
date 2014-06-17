/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by cmuller on 6/2/14
 */
package org.kuali.student.enrollment.registration.client.service.impl.util;

import org.junit.Test;
import org.kuali.student.enrollment.registration.client.service.dto.TimeConflictDataContainer;
import org.kuali.student.enrollment.registration.client.service.dto.TimeConflictResult;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class test TimeConflictCalculator
 *
 * @author Kuali Student Team
 */

public class TestTimeConflictCalculator {

    @Test
    public void testTimeConflictCaculator() throws Exception{

        TimeConflictDataContainer timeSlots = new TimeConflictDataContainer();

        List<TimeSlotInfo> chem135TimeSlots = buildTimeSlotList("MW", "10:00", "12:00");
        List<TimeSlotInfo> engl101TimeSlots = buildTimeSlotList("MW", "10:00", "12:00");
        engl101TimeSlots.add(buildTimeSlot("UT", "11:00", "13:00"));

        List<TimeSlotInfo> phys161TimeSlots = buildTimeSlotList("UH", "16:00", "18:00");


        ArrayList<String> ids = new ArrayList<String>();
        ids.add("CHEM135");
        ids.add("ENGL101");
        ids.add("PHYS161");
        ids.add("PHYS161");
        ArrayList<List<TimeSlotInfo>> timeSlotInfos = new ArrayList<List<TimeSlotInfo>>();
        timeSlotInfos.add(chem135TimeSlots);
        timeSlotInfos.add(engl101TimeSlots);
        timeSlotInfos.add(phys161TimeSlots);
        timeSlotInfos.add(phys161TimeSlots);

        timeSlots.setIds(ids);
        timeSlots.setTimeSlotInfos(timeSlotInfos);


        TimeConflictCalculator timeConflictCalculator = new TimeConflictCalculator();
        TimeConflictResult conflicts = timeConflictCalculator.calculateConflicts(timeSlots, 0);

        assertTrue(conflicts.getIds().contains("CHEM135"));
        assertFalse(conflicts.getIds().contains("ENGL101"));
        assertTrue(conflicts.getIds().contains("PHYS161"));



    }

    protected static List<TimeSlotInfo> buildTimeSlotList(String daysOfWeek, String startTime, String endTime){
        List<TimeSlotInfo> timeSlotInfos = new ArrayList<TimeSlotInfo>();
        timeSlotInfos.add(buildTimeSlot(daysOfWeek, startTime,endTime));
        return timeSlotInfos;

    }
    protected static TimeSlotInfo buildTimeSlot(String daysOfWeek, String startTime, String endTime){
        List<Integer> intDays = buildDaysOfWeek(daysOfWeek);
        Date start = DateFormatters.HOUR_MINUTE_TIME_FORMATTER.parse(startTime);
        Date end =   DateFormatters.HOUR_MINUTE_TIME_FORMATTER.parse(endTime);


        TimeSlotInfo timeSlotInfo = new TimeSlotInfo();
        timeSlotInfo.setWeekdays(intDays);
        timeSlotInfo.setStartTime(new TimeOfDayInfo(start.getHours(), start.getMinutes(), start.getSeconds()));
        timeSlotInfo.setEndTime(new TimeOfDayInfo(end.getHours(), end.getMinutes(), end.getSeconds()));
        return timeSlotInfo;
    }

    protected static List<Integer> buildDaysOfWeek(String daysOfWeek){

        char[] days = daysOfWeek.toCharArray();
        List<Integer> intDays = new ArrayList<Integer>();

        for (char weekday : days)
            switch (weekday) {
                case 'M':
                    intDays.add(Calendar.MONDAY);
                    break;
                case 'T':
                    intDays.add(Calendar.TUESDAY);
                    break;
                case 'W':
                    intDays.add(Calendar.WEDNESDAY);
                    break;
                case 'H':
                    intDays.add(Calendar.THURSDAY);
                    break;
                case 'F':
                    intDays.add(Calendar.FRIDAY);
                    break;
                case 'S':
                    intDays.add(Calendar.SATURDAY);
                    break;
                case 'U':
                    intDays.add(Calendar.SUNDAY);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected day code "
                            + weekday);
            }
        return intDays;


    }
}
