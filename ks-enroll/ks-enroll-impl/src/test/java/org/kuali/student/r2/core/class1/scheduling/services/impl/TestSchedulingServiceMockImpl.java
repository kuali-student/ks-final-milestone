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
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.core.class1.scheduling.service.impl.SchedulingServiceMockImpl;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
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

    @Resource(name = "schedulingService")
    private SchedulingService schedulingService;
    public static String principalId = "123";
    public ContextInfo contextInfo = null;
    boolean loadedData = false;

    @Before
    public void setUp() {
        principalId = "123";
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
        loadedData = ((SchedulingServiceMockImpl)(schedulingService)).loadData();
    }

    @Test
    public void testSchedulingServiceSetup() {
        assertNotNull(schedulingService);
        assertTrue(loadedData);
    }

    @Test(expected=DoesNotExistException.class)
    public void testDoesNotExist() throws Exception {
        schedulingService.getTimeSlot("100", contextInfo);
    }

    @Test
    public void testgetTimeSlot() throws Exception {
        assertNotNull(schedulingService.getTimeSlot("1", contextInfo));
        assertNotNull(schedulingService.getTimeSlot("2", contextInfo));
        assertNotNull(schedulingService.getTimeSlot("3", contextInfo));
        assertNotNull(schedulingService.getTimeSlot("4", contextInfo));
        assertNotNull(schedulingService.getTimeSlot("5", contextInfo));
        assertNotNull(schedulingService.getTimeSlot("6", contextInfo));
    }

    @Test
    public void testgetTimeSlotIdsByType() throws Exception {
        assertTrue(loadedData);
        List l_actoff = schedulingService.getTimeSlotIdsByType(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, contextInfo);
        assertEquals(6, l_actoff.size());
        List l_final = schedulingService.getTimeSlotIdsByType(SchedulingServiceConstants.TIME_SLOT_TYPE_FINAL_EXAM_KEY, contextInfo);
        assertEquals(0, l_final.size());
    }

    @Test
    public void testgetTimeSlotsByIds() throws Exception {
        List<String> valid_ids = new ArrayList<String>();
        valid_ids.add("2");
        valid_ids.add("3");
        List<TimeSlotInfo> l_valid_ts = schedulingService.getTimeSlotsByIds(valid_ids, contextInfo);
        assertEquals(2, valid_ids.size());
        List<String> invalid_ids = new ArrayList<String>();
        invalid_ids.add("100");
        invalid_ids.add("300");
        try {
            List<TimeSlotInfo> l_invalid_ts = schedulingService.getTimeSlotsByIds(invalid_ids, contextInfo);
            fail("Should not be here - test 2");
        } catch (DoesNotExistException e) {}
        catch (Exception e) { fail("Should throw DoesNotExistException"); }
        List<String> mix_ids = new ArrayList<String>();
        mix_ids.add("1");
        mix_ids.add("1000");
        try {
            List<TimeSlotInfo> l_mix_ts = schedulingService.getTimeSlotsByIds(mix_ids, contextInfo);
            fail("Should not be here - test 3");
        } catch (DoesNotExistException e) {}
        catch (Exception e) { fail("Should throw DoesNotExistException"); }
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
}
