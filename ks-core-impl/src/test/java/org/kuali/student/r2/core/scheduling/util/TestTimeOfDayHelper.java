package org.kuali.student.r2.core.scheduling.util;

import org.junit.Test;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.util.TimeOfDayHelper;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestTimeOfDayHelper {
    // Refactored as part of KSENROLL-11492 --cclin

    private List<Object[]> times
        = Arrays.asList(new Object[][] {
            { new TimeOfDayInfo(0, 0), "12:00 AM" },
            { new TimeOfDayInfo(0, 30), "12:30 AM"},
            { new TimeOfDayInfo(1, 0), "01:00 AM"},
            { new TimeOfDayInfo(1, 30), "01:30 AM"},
            { new TimeOfDayInfo(2, 0), "02:00 AM"},
            { new TimeOfDayInfo(2, 15), "02:15 AM"},
            { new TimeOfDayInfo(2, 45), "02:45 AM"},
            { new TimeOfDayInfo(3, 0), "03:00 AM"},
            { new TimeOfDayInfo(11, 0), "11:00 AM"},
            { new TimeOfDayInfo(11, 30), "11:30 AM"},
            { new TimeOfDayInfo(12, 0), "12:00 PM"},
            { new TimeOfDayInfo(12, 30), "12:30 PM"},
            { new TimeOfDayInfo(13, 0), "01:00 PM"},
            { new TimeOfDayInfo(14, 30), "02:30 PM"},
            { new TimeOfDayInfo(14, 0), "02:00 PM"},
            { new TimeOfDayInfo(17, 30), "05:30 PM"},
            { new TimeOfDayInfo(20, 0), "08:00 PM"},
            { new TimeOfDayInfo(21, 0), "09:00 PM"},
            { new TimeOfDayInfo(23, 30), "11:30 PM"},
        });

    @Test
    public void testMakeFormattedTimeFromMillis() {
        for (Object t[] : times) {
            String standardTime = (String) t[1];
            TimeOfDayInfo tod = (TimeOfDayInfo) t[0];
            String time = TimeOfDayHelper.makeFormattedTimeForAOSchedules(tod);
            assertEquals(standardTime, time);
        }
    }

    @Test
    public void testStandardTimeStringToMilitaryTimeString() {
        for (Object t[] : times) {
            TimeOfDayInfo militaryTime =  (TimeOfDayInfo) t[0];
            String standardTime = (String) t[1];
            //  Convert a standard time string to a military time string.
            assertEquals(militaryTime, TimeOfDayHelper.makeTimeOfDayInfoFromTimeString(standardTime));
        }
    }
}
