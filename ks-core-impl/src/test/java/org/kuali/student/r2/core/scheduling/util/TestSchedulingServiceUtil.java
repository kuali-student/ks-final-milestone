package org.kuali.student.r2.core.scheduling.util;

import org.junit.Test;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.util.TimeOfDayHelper;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestSchedulingServiceUtil {

    private long hourMillis = 60 * 60 * 1000;

    private List<Object[]> times
        = Arrays.asList(new Object[][] {
            { "00:00", 0L, "12:00 AM" },
            { "00:30", (long) (.5 * hourMillis), "12:30 AM"},
            { "01:00", (long) (1 * hourMillis), "01:00 AM"},
            { "01:30", (long) (1.5 * hourMillis), "01:30 AM"},
            { "02:00", (long) (2 * hourMillis), "02:00 AM"},
            { "02:15", (long) (2.25 * hourMillis), "02:15 AM"},
            { "02:45", (long) (2.75 * hourMillis), "02:45 AM"},
            { "03:00", (long) (3 * hourMillis), "03:00 AM"},
            { "11:00", (long) (11 * hourMillis), "11:00 AM"},
            { "11:30", (long) (11.5 * hourMillis), "11:30 AM"},
            { "12:00", (long) (12 * hourMillis), "12:00 PM"},
            { "12:30", (long) (12.5 * hourMillis), "12:30 PM"},
            { "13:00", (long) (13 * hourMillis), "01:00 PM"},
            { "14:30", (long) (14.5 * hourMillis), "02:30 PM"},
            { "14:00", (long) (14 * hourMillis), "02:00 PM"},
            { "17:30", (long) (17.5 * hourMillis), "05:30 PM"},
            { "20:00", (long) (20 * hourMillis), "08:00 PM"},
            { "21:00", (long) (21 * hourMillis), "09:00 PM"},
            { "23:30", (long) (23.5 * hourMillis), "11:30 PM"},
        });

    @Test
    public void testMakeFormattedTimeFromMillis() {
        for (Object t[] : times) {
            String standardTime = (String) t[2];
            Long millis = (Long) t[1];
            String time = SchedulingServiceUtil.makeFormattedTimeFromTimeOfDay(TimeOfDayHelper.setMillis(millis));
            assertEquals(standardTime, time);
        }
    }

    @Test
    public void testMilitaryTimeToTimeOfDayInfo() {
        for (Object t[] : times) {
            String militaryTime =  (String) t[0];
            Long millis = (Long) t[1];
            TimeOfDayInfo tdi = SchedulingServiceUtil.makeTimeOfDayFromMilitaryTimeString(militaryTime);
            assertEquals(millis, TimeOfDayHelper.getMillis(tdi));
        }
    }

    @Test
    public void testStandardTimeStringToMilitaryTimeString() {
        for (Object t[] : times) {
            String militaryTime =  (String) t[0];
            String standardTime = (String) t[2];
            //  Convert a standard time string to a military time string.
            assertEquals(militaryTime, SchedulingServiceUtil.standardTimeStringToMilitaryTimeString(standardTime));
        }
    }
}
