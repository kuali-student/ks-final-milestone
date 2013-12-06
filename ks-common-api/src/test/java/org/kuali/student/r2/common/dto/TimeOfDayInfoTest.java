package org.kuali.student.r2.common.dto;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Unit test for TestOfDayInfo
 */
public class TimeOfDayInfoTest {

    @Test
    public void testHourPrecisionAreEqual() {
        TimeOfDayInfo timeOfDay1 = new TimeOfDayInfo(12);
        TimeOfDayInfo timeOfDay2 = new TimeOfDayInfo(12);

        Assert.assertTrue("TimeOfDay hour equality failed", timeOfDay1.equals(timeOfDay2));
        Assert.assertTrue("TimeOfDay hour hashCode failed", timeOfDay1.hashCode() == timeOfDay2.hashCode());
    }

    @Test
    public void testHourPrecisionAreNotEqual() {
        TimeOfDayInfo timeOfDay1 = new TimeOfDayInfo(13);
        TimeOfDayInfo timeOfDay2 = new TimeOfDayInfo(12);

        Assert.assertFalse("TimeOfDay hour inequality failed", timeOfDay2.equals(timeOfDay1));
        Assert.assertTrue("TimeOfDay hashCode inequality failed", timeOfDay1.hashCode() != timeOfDay2.hashCode());
    }

    @Test
    public void testMinutePrecisionAreEqual() {
        TimeOfDayInfo timeOfDay1 = new TimeOfDayInfo(13, 30);
        TimeOfDayInfo timeOfDay2 = new TimeOfDayInfo(13, 30);

        Assert.assertTrue("TimeOfDay minute equality failed", timeOfDay2.equals(timeOfDay1));
        Assert.assertTrue("TimeOfDay minute hashCode inequality failed",
                timeOfDay1.hashCode() == timeOfDay2.hashCode());
    }

    @Test
    public void testMinutePrecisionAreNotEqual() {
        TimeOfDayInfo timeOfDay1 = new TimeOfDayInfo(13, 30);
        TimeOfDayInfo timeOfDay2 = new TimeOfDayInfo(12, 30);

        Assert.assertFalse("TimeOfDay minute inequality failed", timeOfDay2.equals(timeOfDay1));
        Assert.assertTrue("TimeOfDay minute hashCode inequality failed",
                timeOfDay1.hashCode() != timeOfDay2.hashCode());
    }

    @Test
    public void testSecondPrecisionAreEqual() {
        TimeOfDayInfo timeOfDay1 = new TimeOfDayInfo(13, 30, 10);
        TimeOfDayInfo timeOfDay2 = new TimeOfDayInfo(13, 30, 10);

        Assert.assertTrue("TimeOfDay second equality failed", timeOfDay2.equals(timeOfDay1));
        Assert.assertTrue("TimeOfDay second equality failed", timeOfDay1.hashCode() == timeOfDay2.hashCode());
    }

    @Test
    public void testSecondPrecisionAreNotEqual() {
        TimeOfDayInfo timeOfDay1 = new TimeOfDayInfo(13, 30, 10);
        TimeOfDayInfo timeOfDay2 = new TimeOfDayInfo(13, 30, 11);

        Assert.assertFalse("TimeOfDay second inequality failed", timeOfDay2.equals(timeOfDay1));
        Assert.assertTrue("TimeOfDay second hashCode inequality failed",timeOfDay1.hashCode() != timeOfDay2.hashCode());
    }

    @Test
    public void testGetDateWithTimeOfDay() {
        // 1:30:10
        java.util.Date date = new Date();
        date.setHours(1);
        date.setMinutes(30);
        date.setSeconds(10);

        // 3:30:10
        TimeOfDayInfo timeOfDay = new TimeOfDayInfo(3, 30, 10);
        Date newDate = timeOfDay.getDateWithTimeOfDay(date, timeOfDay);
        int newDateHours = newDate.getHours();
        int newDateMinutes = newDate.getMinutes();
        int newDateSeconds = newDate.getSeconds();
        Assert.assertTrue("getDateWithTimeOfDay hours failed", newDateHours == 5);
        Assert.assertTrue("getDateWithTimeOfDay minutes failed", newDateMinutes == 0);
        Assert.assertTrue("getDateWithTimeOfDay seconds failed", newDateSeconds == 20);
    }

    @Test
    public void testSetMilliSeconds() {
        TimeOfDayInfo timeOfDay = new TimeOfDayInfo();

        // 5:45:25
        timeOfDay.setMilliSeconds((long)(60 * 60 * 1000 * 5) + (60 * 1000 * 45) + (1000 * 25));

        Integer hour = timeOfDay.getHour();
        Integer minute = timeOfDay.getMinute();
        Integer second = timeOfDay.getSecond();

        Assert.assertTrue("TimeOfDay setMilliSeconds hour failed", hour == 5);
        Assert.assertTrue("TimeOfDay setMilliSeconds minute failed", minute == 45);
        Assert.assertTrue("TimeOfDay setMilliSeconds second failed", second == 25);
    }

    @Test
    public void testSetMilliSecondsTruncated() {
        TimeOfDayInfo timeOfDay = new TimeOfDayInfo();

        // 5:45:25.300 or 20725300 millis
        timeOfDay.setMilliSeconds((long)(60 * 60 * 1000 * 5) + (60 * 1000 * 45) + (1000 * 25) + 300);
        Long truncatedMillis = timeOfDay.getMilliSeconds();

        // 5:45:25 or 20725000 millis
        Assert.assertTrue(truncatedMillis == 20725000);

        Integer hour = timeOfDay.getHour();
        Integer minute = timeOfDay.getMinute();
        Integer second = timeOfDay.getSecond();

        Assert.assertTrue("TimeOfDay setMilliSecondsTruncated hour failed", hour == 5);
        Assert.assertTrue("TimeOfDay setMilliSecondsTruncated minute failed", minute == 45);
        Assert.assertTrue("TimeOfDay setMilliSecondsTruncated second failed", second == 25);
    }
}
