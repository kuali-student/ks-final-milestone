/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.dto;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.r2.common.infc.TimeOfDay;

import java.util.Date;

/**
 * Unit test for TestOfDayInfo
 */
public class TestTimeOfDay {

    @Test
    public void testHourPrecisionAreEqual() {
        TimeOfDay timeOfDay1 = new TimeOfDayInfo(12);
        TimeOfDay timeOfDay2 = new TimeOfDayInfo(12);

        Assert.assertTrue("TimeOfDay hour equality failed", timeOfDay1.equals(timeOfDay2));
        Assert.assertTrue("TimeOfDay hour hashCode failed", timeOfDay1.hashCode() == timeOfDay2.hashCode());
    }

    @Test
    public void testHourPrecisionAreNotEqual() {
        TimeOfDay timeOfDay1 = new TimeOfDayInfo(13);
        TimeOfDay timeOfDay2 = new TimeOfDayInfo(12);

        Assert.assertFalse("TimeOfDay hour inequality failed", timeOfDay2.equals(timeOfDay1));
        Assert.assertTrue("TimeOfDay hashCode inequality failed", timeOfDay1.hashCode() != timeOfDay2.hashCode());
    }

    @Test
    public void testMinutePrecisionAreEqual() {
        TimeOfDay timeOfDay1 = new TimeOfDayInfo(13, 30);
        TimeOfDay timeOfDay2 = new TimeOfDayInfo(13, 30);

        Assert.assertTrue("TimeOfDay minute equality failed", timeOfDay2.equals(timeOfDay1));
        Assert.assertTrue("TimeOfDay minute hashCode inequality failed",
                timeOfDay1.hashCode() == timeOfDay2.hashCode());
    }

    @Test
    public void testMinutePrecisionAreNotEqual() {
        TimeOfDay timeOfDay1 = new TimeOfDayInfo(13, 30);
        TimeOfDay timeOfDay2 = new TimeOfDayInfo(12, 30);

        Assert.assertFalse("TimeOfDay minute inequality failed", timeOfDay2.equals(timeOfDay1));
        Assert.assertTrue("TimeOfDay minute hashCode inequality failed",
                timeOfDay1.hashCode() != timeOfDay2.hashCode());
    }

    @Test
    public void testSecondPrecisionAreEqual() {
        TimeOfDay timeOfDay1 = new TimeOfDayInfo(13, 30, 10);
        TimeOfDay timeOfDay2 = new TimeOfDayInfo(13, 30, 10);

        Assert.assertTrue("TimeOfDay second equality failed", timeOfDay2.equals(timeOfDay1));
        Assert.assertTrue("TimeOfDay second equality failed", timeOfDay1.hashCode() == timeOfDay2.hashCode());
    }

    @Test
    public void testSecondPrecisionAreNotEqual() {
        TimeOfDay timeOfDay1 = new TimeOfDayInfo(13, 30, 10);
        TimeOfDay timeOfDay2 = new TimeOfDayInfo(13, 30, 11);

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
        Date newDate = TimeOfDayInfo.getDateWithTimeOfDay(date, timeOfDay);
        int newDateHours = newDate.getHours();
        int newDateMinutes = newDate.getMinutes();
        int newDateSeconds = newDate.getSeconds();
        Assert.assertTrue("getDateWithTimeOfDay hours failed", newDateHours == 3);
        Assert.assertTrue("getDateWithTimeOfDay minutes failed", newDateMinutes == 30);
        Assert.assertTrue("getDateWithTimeOfDay seconds failed", newDateSeconds == 10);
    }

    @Test
    // Deprecated method throws exception (which is why this test is now ignored)
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
    // Deprecated method throws exception (which is why this test is now ignored)
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

    @Test
    public void testIsBefore() {
        TimeOfDay three = new TimeOfDayInfo(3);
        TimeOfDay four = new TimeOfDayInfo(4);
        Assert.assertTrue("TimeOfDay testIsBefore failed", three.isBefore(four));
        Assert.assertFalse("TimeOfDay testIsBefore failed", four.isBefore(three));

        TimeOfDay threeTen = new TimeOfDayInfo(3, 10);
        TimeOfDay threeThirty = new TimeOfDayInfo(3, 30);
        Assert.assertTrue("TimeOfDay testIsBefore failed", threeTen.isBefore(threeThirty));
        Assert.assertFalse("TimeOfDay testIsBefore failed", threeThirty.isBefore(threeTen));

        TimeOfDay threeThirtyAndTen = new TimeOfDayInfo(3, 30, 10);
        TimeOfDay threeThirtyAndTwenty = new TimeOfDayInfo(3, 30, 20);
        Assert.assertTrue("TimeOfDay testIsBefore failed", threeThirtyAndTen.isBefore(threeThirtyAndTwenty));
        Assert.assertFalse("TimeOfDay testIsBefore failed", threeThirtyAndTwenty.isBefore(threeThirtyAndTen));
    }

    @Test
    public void testIsAfter() {
        TimeOfDay three = new TimeOfDayInfo(3);
        TimeOfDay four = new TimeOfDayInfo(4);
        Assert.assertTrue("TimeOfDay testIsAfter failed", four.isAfter(three));
        Assert.assertFalse("TimeOfDay testIsAfter failed", three.isAfter(four));

        TimeOfDay threeTen = new TimeOfDayInfo(3, 10);
        TimeOfDay threeThirty = new TimeOfDayInfo(3, 30);
        Assert.assertTrue("TimeOfDay testIsAfter failed", threeThirty.isAfter(threeTen));
        Assert.assertFalse("TimeOfDay testIsAfter failed", threeTen.isAfter(threeThirty));

        TimeOfDay threeThirtyAndTen = new TimeOfDayInfo(3, 30, 10);
        TimeOfDay threeThirtyAndTwenty = new TimeOfDayInfo(3, 30, 20);
        Assert.assertTrue("TimeOfDay testIsAfter failed", threeThirtyAndTwenty.isAfter(threeThirtyAndTen));
        Assert.assertFalse("TimeOfDay testIsAfter failed", threeThirtyAndTen.isAfter(threeThirtyAndTwenty));
    }

    @Test
    public void testEquality() {
        TimeOfDay threeTen = new TimeOfDayInfo(3, 10);
        TimeOfDay threeThirty = new TimeOfDayInfo(3, 30);
        TimeOfDay threeThirtyAgain = new TimeOfDayInfo(3, 30, 0);
        Assert.assertTrue("TimeOfDay testEquality failed", threeThirty.equals(threeThirtyAgain));
        Assert.assertFalse("TimeOfDay testEquality failed", threeThirty.equals(threeTen));
        TimeOfDay threeThirtyAndTen = new TimeOfDayInfo(3, 30, 10);
        TimeOfDay threeThirtyAndTwenty = new TimeOfDayInfo(3, 30, 20);
        Assert.assertFalse("TimeOfDay testEquality failed", threeThirtyAndTwenty.equals(threeThirtyAndTen));
    }
}
