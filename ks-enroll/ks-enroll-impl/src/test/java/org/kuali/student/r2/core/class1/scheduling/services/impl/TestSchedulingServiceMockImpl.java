/*
 * Copyright 2012 The Kuali Foundation Licensed under the
 *  Educational Community License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may
 *  obtain a copy of the License at
 *
 *   http://www.osedu.org/licenses/ECL-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS"
 *  BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.kuali.student.r2.core.class1.scheduling.services.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.class1.scheduling.service.impl.SchedulingServiceDataLoader;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.infc.TimeSlot;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Version 1.0
 * @Author Mezba Mahtab
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:scheduling-mock-impl-test-context.xml"})
public class TestSchedulingServiceMockImpl {

    @Autowired
    private SchedulingService schedulingService;

    public static String principalId = "123";
    public ContextInfo contextInfo = null;

    @Before
    public void setUp() {
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
        try {
            loadData();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void loadData() throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        SchedulingServiceDataLoader loader = new SchedulingServiceDataLoader (this.schedulingService);
        loader.loadData();
    }

    @Test
    public void testSchedulingServiceSetup() {
        assertNotNull(schedulingService);
    }

    @Test(expected=DoesNotExistException.class)
    public void testDoesNotExist() throws Exception {
        schedulingService.getTimeSlot("100", contextInfo);
    }

    @Test
    public void testgetTimeSlot() throws Exception {
        // test get by id for all records
        for (int i = 1; i<= 16; i++) {
            TimeSlot ts = schedulingService.getTimeSlot("" + i, contextInfo);
            assertNotNull(ts);
            assertEquals("" + i, ts.getId());
        }

        // test specific records - 2
        TimeSlot ts = schedulingService.getTimeSlot("2", contextInfo);
        List<Integer> dow = ts.getWeekdays();
        // should contain Monday, Wednesday, Friday
        assertTrue(dow.contains(Calendar.MONDAY));
        assertTrue(dow.contains(Calendar.WEDNESDAY));
        assertTrue(dow.contains(Calendar.FRIDAY));
        // should not contain Tuesday or Thursday
        assertFalse(dow.contains(Calendar.TUESDAY));
        assertFalse(dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getMilliSeconds(), SchedulingServiceDataLoader.START_TIME_MILLIS_8_00_AM);
        assertEquals(ts.getEndTime().getMilliSeconds(), SchedulingServiceDataLoader.END_TIME_MILLIS_9_10_AM);

        // test specific records - 3
        ts = schedulingService.getTimeSlot("3", contextInfo);
        dow = ts.getWeekdays();
        // should not contain Monday, Wednesday, Friday
        assertFalse(dow.contains(Calendar.MONDAY));
        assertFalse(dow.contains(Calendar.WEDNESDAY));
        assertFalse(dow.contains(Calendar.FRIDAY));
        // should contain Tuesday or Thursday
        assertTrue(dow.contains(Calendar.TUESDAY));
        assertTrue(dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getMilliSeconds(), SchedulingServiceDataLoader.START_TIME_MILLIS_8_00_AM);
        assertEquals(ts.getEndTime().getMilliSeconds(), SchedulingServiceDataLoader.END_TIME_MILLIS_8_50_AM);

        // test specific records - 10
        ts = schedulingService.getTimeSlot("10", contextInfo);
        dow = ts.getWeekdays();
        // should contain Monday, Wednesday, Friday
        assertTrue(dow.contains(Calendar.MONDAY));
        assertTrue(dow.contains(Calendar.WEDNESDAY));
        assertTrue(dow.contains(Calendar.FRIDAY));
        // should not contain Tuesday or Thursday
        assertFalse(dow.contains(Calendar.TUESDAY));
        assertFalse(dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getMilliSeconds(), SchedulingServiceDataLoader.START_TIME_MILLIS_1_00_PM);
        assertEquals(ts.getEndTime().getMilliSeconds(), SchedulingServiceDataLoader.END_TIME_MILLIS_2_10_PM);
    }

    @Test
    public void testgetTimeSlotIdsByType() throws Exception {
        List<String> l_actoff = schedulingService.getTimeSlotIdsByType(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, contextInfo);
        assertEquals(16, l_actoff.size());
        for (int i=1; i<=16; i++) {
            assertEquals("" + i, l_actoff.get(i - 1));
        }
        List l_final = schedulingService.getTimeSlotIdsByType(SchedulingServiceConstants.TIME_SLOT_TYPE_FINAL_EXAM_KEY, contextInfo);
        assertEquals(0, l_final.size());
    }

    @Test
    public void testgetTimeSlotsByIds() throws Exception {
        // test case: all valid ids
        List<String> valid_ids = new ArrayList<String>();
        valid_ids.add("2");
        valid_ids.add("15");
        List<TimeSlotInfo> l_valid_ts = schedulingService.getTimeSlotsByIds(valid_ids, contextInfo);
        assertEquals(2, valid_ids.size());

        assertEquals("2", l_valid_ts.get(0).getId());
        TimeSlot ts = l_valid_ts.get(0);
        List<Integer> dow = ts.getWeekdays();
        // should contain Monday, Wednesday, Friday
        assertTrue(dow.contains(Calendar.MONDAY));
        assertTrue(dow.contains(Calendar.WEDNESDAY));
        assertTrue(dow.contains(Calendar.FRIDAY));
        // should not contain Tuesday or Thursday
        assertFalse(dow.contains(Calendar.TUESDAY));
        assertFalse(dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getMilliSeconds(), SchedulingServiceDataLoader.START_TIME_MILLIS_8_00_AM);
        assertEquals(ts.getEndTime().getMilliSeconds(), SchedulingServiceDataLoader.END_TIME_MILLIS_9_10_AM);

        assertEquals("15", l_valid_ts.get(1).getId());
        ts = l_valid_ts.get(1);
        dow = ts.getWeekdays();
        // should not contain Monday, Wednesday, Friday
        assertFalse(dow.contains(Calendar.MONDAY));
        assertFalse(dow.contains(Calendar.WEDNESDAY));
        assertFalse(dow.contains(Calendar.FRIDAY));
        // should contain Tuesday or Thursday
        assertTrue(dow.contains(Calendar.TUESDAY));
        assertTrue(dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getMilliSeconds(), SchedulingServiceDataLoader.START_TIME_MILLIS_3_00_PM);
        assertEquals(ts.getEndTime().getMilliSeconds(), SchedulingServiceDataLoader.END_TIME_MILLIS_3_50_PM);

        // test case: all invalid ids
        List<String> invalid_ids = new ArrayList<String>();
        invalid_ids.add("100");
        invalid_ids.add("300");
        try {
            List<TimeSlotInfo> l_invalid_ts = schedulingService.getTimeSlotsByIds(invalid_ids, contextInfo);
            fail("Should not be here - test invalid_ids");
        } catch (DoesNotExistException e) {}
        catch (Exception e) { fail("Should throw DoesNotExistException - invalid_ids"); }

        // test case: mixture of valid and invalid
        List<String> mix_ids = new ArrayList<String>();
        mix_ids.add("10");
        mix_ids.add("1000");
        try {
            List<TimeSlotInfo> l_mix_ts = schedulingService.getTimeSlotsByIds(mix_ids, contextInfo);
            fail("Should not be here - test mix_ids");
        } catch (DoesNotExistException e) {}
        catch (Exception e) { fail("Should throw DoesNotExistException - mix_ids"); }
    }

    @Test
    public void getValidDaysOfWeekByTimeSlotType() throws Exception {
        List<Integer> valid_days_act_off = schedulingService.getValidDaysOfWeekByTimeSlotType(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, contextInfo);
        // should return days Monday through Friday
        assertTrue(valid_days_act_off.contains(Calendar.MONDAY));
        assertTrue(valid_days_act_off.contains(Calendar.TUESDAY));
        assertTrue(valid_days_act_off.contains(Calendar.WEDNESDAY));
        assertTrue(valid_days_act_off.contains(Calendar.THURSDAY));
        assertTrue(valid_days_act_off.contains(Calendar.FRIDAY));
        // should not contain Sat or Sun
        assertFalse(valid_days_act_off.contains(Calendar.SATURDAY));
        assertFalse(valid_days_act_off.contains(Calendar.SUNDAY));

        List<Integer> valid_days_final = schedulingService.getValidDaysOfWeekByTimeSlotType(SchedulingServiceConstants.TIME_SLOT_TYPE_FINAL_EXAM_KEY, contextInfo);
        // should not return any days
        assertFalse(valid_days_final.contains(Calendar.MONDAY));
        assertFalse(valid_days_final.contains(Calendar.TUESDAY));
        assertFalse(valid_days_final.contains(Calendar.WEDNESDAY));
        assertFalse(valid_days_final.contains(Calendar.THURSDAY));
        assertFalse(valid_days_final.contains(Calendar.FRIDAY));
        assertFalse(valid_days_final.contains(Calendar.SATURDAY));
        assertFalse(valid_days_final.contains(Calendar.SUNDAY));
    }

    @Test
    public void testgetTimeSlotsByDaysAndStartTime () throws Exception {
        // should return records 3 and 4
        List<Integer> dow = new ArrayList<Integer>();
        dow.add(Calendar.TUESDAY);
        dow.add(Calendar.THURSDAY);
        TimeOfDayInfo startTime = new TimeOfDayInfo();
        startTime.setMilliSeconds(SchedulingServiceDataLoader.START_TIME_MILLIS_8_00_AM);
        List<TimeSlotInfo> tsi = schedulingService.getTimeSlotsByDaysAndStartTime(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, dow, startTime, contextInfo);
        assertEquals(2, tsi.size());

        assertEquals("3", tsi.get(0).getId());
        TimeSlot ts = tsi.get(0);
        List<Integer> ts_dow = ts.getWeekdays();
        // should not contain Monday, Wednesday, Friday
        assertFalse(ts_dow.contains(Calendar.MONDAY));
        assertFalse(ts_dow.contains(Calendar.WEDNESDAY));
        assertFalse(ts_dow.contains(Calendar.FRIDAY));
        // should contain Tuesday or Thursday
        assertTrue(ts_dow.contains(Calendar.TUESDAY));
        assertTrue(ts_dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getMilliSeconds(), SchedulingServiceDataLoader.START_TIME_MILLIS_8_00_AM);

        assertEquals("4", tsi.get(1).getId());
        ts = tsi.get(1);
        ts_dow = ts.getWeekdays();
        // should not contain Monday, Wednesday, Friday
        assertFalse(ts_dow.contains(Calendar.MONDAY));
        assertFalse(ts_dow.contains(Calendar.WEDNESDAY));
        assertFalse(ts_dow.contains(Calendar.FRIDAY));
        // should contain Tuesday or Thursday
        assertTrue(ts_dow.contains(Calendar.TUESDAY));
        assertTrue(ts_dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getMilliSeconds(), SchedulingServiceDataLoader.START_TIME_MILLIS_8_00_AM);

    }

    @Test
    public void testgetTimeSlotsByDaysAndStartTimeAndEndTime () throws Exception {
        // should return record 3
        List<Integer> dow = new ArrayList<Integer>();
        dow.add(Calendar.TUESDAY);
        dow.add(Calendar.THURSDAY);
        TimeOfDayInfo startTime = new TimeOfDayInfo();
        startTime.setMilliSeconds(SchedulingServiceDataLoader.START_TIME_MILLIS_8_00_AM);
        TimeOfDayInfo endTime = new TimeOfDayInfo();
        endTime.setMilliSeconds(SchedulingServiceDataLoader.END_TIME_MILLIS_8_50_AM);
        List<TimeSlotInfo> tsi = schedulingService.getTimeSlotsByDaysAndStartTimeAndEndTime(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, dow, startTime, endTime, contextInfo);
        assertEquals(1, tsi.size());
        assertEquals("3", tsi.get(0).getId());
        TimeSlot ts = tsi.get(0);
        List<Integer> ts_dow = ts.getWeekdays();
        // should not contain Monday, Wednesday, Friday
        assertFalse(ts_dow.contains(Calendar.MONDAY));
        assertFalse(ts_dow.contains(Calendar.WEDNESDAY));
        assertFalse(ts_dow.contains(Calendar.FRIDAY));
        // should contain Tuesday or Thursday
        assertTrue(ts_dow.contains(Calendar.TUESDAY));
        assertTrue(ts_dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getMilliSeconds(), SchedulingServiceDataLoader.START_TIME_MILLIS_8_00_AM);
        assertEquals(ts.getEndTime().getMilliSeconds(), SchedulingServiceDataLoader.END_TIME_MILLIS_8_50_AM);

        // should return record 10
        dow = new ArrayList<Integer>();
        dow.add(Calendar.MONDAY);
        dow.add(Calendar.WEDNESDAY);
        dow.add(Calendar.FRIDAY);
        startTime = new TimeOfDayInfo();
        startTime.setMilliSeconds(SchedulingServiceDataLoader.START_TIME_MILLIS_1_00_PM);
        endTime = new TimeOfDayInfo();
        endTime.setMilliSeconds(SchedulingServiceDataLoader.END_TIME_MILLIS_2_10_PM);
        tsi = schedulingService.getTimeSlotsByDaysAndStartTimeAndEndTime(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, dow, startTime, endTime, contextInfo);
        assertEquals(1, tsi.size());
        assertEquals("10", tsi.get(0).getId());
        ts = tsi.get(0);
        ts_dow = ts.getWeekdays();
        // should contain Monday, Wednesday, Friday
        assertTrue(ts_dow.contains(Calendar.MONDAY));
        assertTrue(ts_dow.contains(Calendar.WEDNESDAY));
        assertTrue(ts_dow.contains(Calendar.FRIDAY));
        // should not contain Tuesday or Thursday
        assertFalse(ts_dow.contains(Calendar.TUESDAY));
        assertFalse(ts_dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getMilliSeconds(), SchedulingServiceDataLoader.START_TIME_MILLIS_1_00_PM);
        assertEquals(ts.getEndTime().getMilliSeconds(), SchedulingServiceDataLoader.END_TIME_MILLIS_2_10_PM);

    }
}
