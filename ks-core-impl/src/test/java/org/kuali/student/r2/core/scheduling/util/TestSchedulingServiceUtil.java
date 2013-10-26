package org.kuali.student.r2.core.scheduling.util;

import org.junit.Test;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestSchedulingServiceUtil {

    private long hourMillis = 60 * 60 * 1000;

    @Test
    public void testDisplayTimeFormat() {
        List<Object[]> times = Arrays.asList(new Object[][] {
                { 0L, "12:00 AM" },
                { (long) (.5 * hourMillis), "12:30 AM" },
                { (long) (1 * hourMillis), "01:00 AM" },
                { (long) (1.5 * hourMillis), "01:30 AM" },
                { (long) (2 * hourMillis), "02:00 AM" },
                { (long) (2.25 * hourMillis), "02:15 AM" },
                { (long) (2.75 * hourMillis), "02:45 AM" },
                { (long) (3 * hourMillis), "03:00 AM" },
                { (long) (11 * hourMillis), "11:00 AM" },
                { (long) (11.5 * hourMillis), "11:30 AM" },
                { (long) (12 * hourMillis), "12:00 PM" },
                { (long) (12.5 * hourMillis), "12:30 PM" },
                { (long) (13 * hourMillis), "01:00 PM" },
                { (long) (13.5 * hourMillis), "01:30 PM" },
                { (long) (14 * hourMillis), "02:00 PM" },
                { (long) (20 * hourMillis), "08:00 PM" },
                { (long) (23.5 * hourMillis), "11:30 PM" },
        });

        for (Object t[] : times) {
            String time = SchedulingServiceUtil.makeFormattedTimeFromMillis((Long) t[0]);
            assertEquals(t[1], time);
        }
    }

    @Test
    public void testMakeTimeOfDayFromMilitaryTimeString() {
         List<Object[]> times = Arrays.asList(new Object[][] {
                { "00:00", 0L, "12:00 AM" },
                { "00:30", (long) (.5 * hourMillis)},
                { "01:00", (long) (1 * hourMillis)},
                { "01:30", (long) (1.5 * hourMillis)},
                { "02:00", (long) (2 * hourMillis)},
                { "02:15", (long) (2.25 * hourMillis)},
                { "02:45", (long) (2.75 * hourMillis)},
                { "03:00", (long) (3 * hourMillis)},
                { "11:00", (long) (11 * hourMillis)},
                { "11:30", (long) (11.5 * hourMillis)},
                { "12:00", (long) (12 * hourMillis)},
                { "12:30", (long) (12.5 * hourMillis)},
                { "13:00", (long) (13 * hourMillis)},
                { "14:30", (long) (14.5 * hourMillis)},
                { "14:00", (long) (14 * hourMillis)},
                { "17:30", (long) (17.5 * hourMillis)},
                { "20:00", (long) (20 * hourMillis)},
                { "21:00", (long) (21 * hourMillis)},
                { "23:30", (long) (23.5 * hourMillis)},
        });

        for (Object t[] : times) {
            TimeOfDayInfo tdi = SchedulingServiceUtil.makeTimeOfDayFromMilitaryTimeString((String) t[0]);
            assertEquals(t[1], tdi.getMilliSeconds());
        }
    }

    @Test
    public void t() {
        TimeOfDayInfo timeOfDayInfo1 = SchedulingServiceUtil.makeTimeOfDayFromMilitaryTimeString("10:00");
        System.err.println(SchedulingServiceUtil.makeFormattedTimeFromMillis(timeOfDayInfo1.getMilliSeconds()));
        TimeOfDayInfo timeOfDayInfo2 = SchedulingServiceUtil.makeTimeOfDayFromMilitaryTimeString("10:50");
        System.err.println(SchedulingServiceUtil.makeFormattedTimeFromMillis(timeOfDayInfo2.getMilliSeconds()));

        System.err.println(SchedulingServiceUtil.makeFormattedTimeFromMillis(64800000L));

        timeOfDayInfo1 = SchedulingServiceUtil.makeTimeOfDayInfoFromTimeString("01:00 PM");
        System.err.println(SchedulingServiceUtil.makeFormattedTimeFromMillis(timeOfDayInfo1.getMilliSeconds()));

        timeOfDayInfo1 = SchedulingServiceUtil.makeTimeOfDayInfoFromTimeString("08:00 PM");
        System.err.println(SchedulingServiceUtil.makeFormattedTimeFromMillis(timeOfDayInfo1.getMilliSeconds()));

        timeOfDayInfo1 = SchedulingServiceUtil.makeTimeOfDayInfoFromTimeString("12:01 AM");
        System.err.println(SchedulingServiceUtil.makeFormattedTimeFromMillis(timeOfDayInfo1.getMilliSeconds()));

        timeOfDayInfo1 = SchedulingServiceUtil.makeTimeOfDayInfoFromTimeString("10:00 AM");
        System.err.println(timeOfDayInfo1.getMilliSeconds());
        System.err.println(SchedulingServiceUtil.makeFormattedTimeFromMillis(timeOfDayInfo1.getMilliSeconds()));

        timeOfDayInfo1 = SchedulingServiceUtil.makeTimeOfDayInfoFromTimeString("11:00 AM");
        System.err.println(timeOfDayInfo1.getMilliSeconds());
        System.err.println(SchedulingServiceUtil.makeFormattedTimeFromMillis(timeOfDayInfo1.getMilliSeconds()));

        System.err.println(SchedulingServiceUtil.makeFormattedTimeFromMillis(39000000L));

    }
}
