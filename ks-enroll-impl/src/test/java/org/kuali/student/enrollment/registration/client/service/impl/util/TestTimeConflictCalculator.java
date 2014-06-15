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
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.registration.client.service.dto.TimeConflictDataContainer;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class test TimeConflictCalculator
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:courseregistration-test-context.xml" })
public class TestTimeConflictCalculator {

    private ContextInfo CONTEXT;

    @Test
    public void testTimeConflictCaculator() throws Exception{

        TimeConflictDataContainer timeSlots = new TimeConflictDataContainer();

        List<TimeSlotInfo> chem135TimeSlots = new ArrayList<TimeSlotInfo>();
        TimeSlotInfo chemTimeSlot1 = new TimeSlotInfo();
        ArrayList<Integer> chemWeekdays1 = new ArrayList<Integer>();
        chemWeekdays1.add(2);
        chemWeekdays1.add(4);
        TimeOfDayInfo chemStartTime1 = new TimeOfDayInfo(10, 0, 0);
        TimeOfDayInfo chemEndTime1 = new TimeOfDayInfo(12, 0, 0);
        chemTimeSlot1.setWeekdays(chemWeekdays1);
        chemTimeSlot1.setStartTime(chemStartTime1);
        chemTimeSlot1.setEndTime(chemEndTime1);
        chem135TimeSlots.add(chemTimeSlot1);

        List<TimeSlotInfo> engl101TimeSlots = new ArrayList<TimeSlotInfo>();
        TimeSlotInfo englTimeSlot1 = new TimeSlotInfo();
        ArrayList<Integer> englWeekdays1 = new ArrayList<Integer>();
        englWeekdays1.add(2);
        englWeekdays1.add(4);
        TimeOfDayInfo englStartTime1 = new TimeOfDayInfo(10, 0, 0);
        TimeOfDayInfo englEndTime1 = new TimeOfDayInfo(12, 0, 0);
        englTimeSlot1.setWeekdays(englWeekdays1);
        englTimeSlot1.setStartTime(englStartTime1);
        englTimeSlot1.setEndTime(englEndTime1);
        engl101TimeSlots.add(englTimeSlot1);

        TimeSlotInfo englTimeSlot2 = new TimeSlotInfo();
        ArrayList<Integer> englWeekdays2 = new ArrayList<Integer>();
        englWeekdays2.add(1);
        englWeekdays2.add(3);
        TimeOfDayInfo englStartTime2 = new TimeOfDayInfo(11, 0, 0);
        TimeOfDayInfo englEndTime2 = new TimeOfDayInfo(13, 0, 0);
        englTimeSlot2.setWeekdays(englWeekdays2);
        englTimeSlot2.setStartTime(englStartTime2);
        englTimeSlot2.setEndTime(englEndTime2);
        engl101TimeSlots.add(englTimeSlot2);

        List<TimeSlotInfo> phys161TimeSlots = new ArrayList<TimeSlotInfo>();
        TimeSlotInfo physTimeSlot1 = new TimeSlotInfo();
        ArrayList<Integer> physWeekdays1 = new ArrayList<Integer>();
        physWeekdays1.add(1);
        physWeekdays1.add(5);
        TimeOfDayInfo physStartTime1 = new TimeOfDayInfo(16, 0, 0);
        TimeOfDayInfo physEndTime1 = new TimeOfDayInfo(18, 0, 0);
        physTimeSlot1.setWeekdays(physWeekdays1);
        physTimeSlot1.setStartTime(physStartTime1);
        physTimeSlot1.setEndTime(physEndTime1);
        phys161TimeSlots.add(physTimeSlot1);

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
        Map<String, List<String>> conflicts = timeConflictCalculator.calculateConflicts(timeSlots, 0);

        assertTrue(conflicts.containsKey("CHEM135"));
        assertFalse(conflicts.containsKey("ENGL101"));
        assertTrue(conflicts.containsKey("PHYS161"));



    }
}
