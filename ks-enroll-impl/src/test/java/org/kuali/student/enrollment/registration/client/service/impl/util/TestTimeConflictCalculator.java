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
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * This class test TimeConflictCalculator
 *
 * @author Kuali Student Team
 */

public class TestTimeConflictCalculator {


    @Test
    public void testTimeConflictCaculator_New_and_existing() throws Exception {
        TimeConflictCalculator timeConflictCalculator = new TimeConflictCalculator();

        // This is the positive case. ie. no conflicts
        TimeConflictDataContainer engl206 = new TimeConflictDataContainer();
        engl206.setId("RegReqId-ENGL206-1");
        engl206.setAoToTimeSlotMap(buildAoToTimeSlotMap(true, "ENGL206-Lec", null, null, null));

        TimeConflictDataContainer engl202 = new TimeConflictDataContainer();
        engl202.setId("RegReqId-ENGL202-1");
        engl202.setAoToTimeSlotMap(buildAoToTimeSlotMap("ENGL202-Lec", "MWF", "10:00", "10:50"));

        TimeConflictDataContainer engl212 = new TimeConflictDataContainer();
        engl212.setId("RegReqId-ENGL212-1");
        engl212.setAoToTimeSlotMap(buildAoToTimeSlotMap("ENGL212-Lec", "MWF", "12:00", "12:50"));

        TimeConflictDataContainer bsci105 = new TimeConflictDataContainer();
        bsci105.setId("RegReqId-BSCI105-1");
        bsci105.setAoToTimeSlotMap(buildAoToTimeSlotMap("BSCI105-Lec", "M", "14:00", "16:50"));
        bsci105.getAoToTimeSlotMap().put("BSCI105-Lab", buildTimeSlotList("MWF", "09:00", "09:50"));

        TimeConflictDataContainer engl201 = new TimeConflictDataContainer();
        engl201.setId("RegReqId-ENGL201-1");
        engl201.setAoToTimeSlotMap(buildAoToTimeSlotMap(true, "ENGL201-Lec", null, null, null));

        TimeConflictDataContainer bsci120 = new TimeConflictDataContainer();
        bsci120.setId("RegReqId-BSCI120-1");
        bsci120.setAoToTimeSlotMap(buildAoToTimeSlotMap("BSCI120-Lec", "M", "09:00", "09:50"));
        bsci120.getAoToTimeSlotMap().put("BSCI120-Lab", buildTimeSlotList("TH", "09:30", "10:20"));

        TimeConflictDataContainer engl101 = new TimeConflictDataContainer();
        engl101.setId("RegReqId-ENGL101-1");
        engl101.setAoToTimeSlotMap(buildAoToTimeSlotMap("ENGL101-Lec", "T", "12:30", "13:45"));


        List<TimeConflictDataContainer> newItems = new ArrayList<TimeConflictDataContainer>();
        List<TimeConflictDataContainer> existingItems = new ArrayList<TimeConflictDataContainer>();
        existingItems.add(engl101);
        existingItems.add(bsci120);
        existingItems.add(engl201);
        newItems.add(bsci105);

        List<TimeConflictResult> results = timeConflictCalculator.getTimeConflictResults(newItems,existingItems);

        assertTrue(results != null);
        assertTrue(results.size() == 1);
        TimeConflictResult  conflicts = results.get(0);
        assertTrue(conflicts.getId().equals("RegReqId-BSCI105-1"));  // saying that the primary is conflicting with something
        assertTrue(conflicts.getConflictingItemMap().size() == 1);  // Make sure there's only 1 conflict... ie. we didn't count ourselves
        assertTrue(conflicts.getConflictingItemMap().containsKey("RegReqId-BSCI120-1"));  // this is what we should be conflicting with
        assertTrue(conflicts.getConflictingItemMap().get("RegReqId-BSCI120-1").contains("BSCI120-Lec")); // This is the ao that's causing the issues

    }

    @Test
    public void testTimeConflictCaculator_BSCI_ERROR() throws Exception {
        TimeConflictCalculator timeConflictCalculator = new TimeConflictCalculator();

        // This is the positive case. ie. no conflicts
        TimeConflictDataContainer engl206 = new TimeConflictDataContainer();
        engl206.setId("RegReqId-ENGL206-1");
        engl206.setAoToTimeSlotMap(buildAoToTimeSlotMap(true, "ENGL206-Lec", null, null, null));

        TimeConflictDataContainer engl202 = new TimeConflictDataContainer();
        engl202.setId("RegReqId-ENGL202-1");
        engl202.setAoToTimeSlotMap(buildAoToTimeSlotMap("ENGL202-Lec", "MWF", "10:00", "10:50"));

        TimeConflictDataContainer engl212 = new TimeConflictDataContainer();
        engl212.setId("RegReqId-ENGL212-1");
        engl212.setAoToTimeSlotMap(buildAoToTimeSlotMap("ENGL212-Lec", "MWF", "12:00", "12:50"));

        TimeConflictDataContainer bsci105 = new TimeConflictDataContainer();
        bsci105.setId("RegReqId-BSCI105-1");
        bsci105.setAoToTimeSlotMap(buildAoToTimeSlotMap("BSCI105-Lec", "M", "14:00", "16:50"));
        bsci105.getAoToTimeSlotMap().put("BSCI105-Lab", buildTimeSlotList("MWF", "09:00", "09:50"));

        TimeConflictDataContainer engl201 = new TimeConflictDataContainer();
        engl201.setId("RegReqId-ENGL201-1");
        engl201.setAoToTimeSlotMap(buildAoToTimeSlotMap(true, "ENGL201-Lec", null, null, null));

        TimeConflictDataContainer bsci120 = new TimeConflictDataContainer();
        bsci120.setId("RegReqId-BSCI120-1");
        bsci120.setAoToTimeSlotMap(buildAoToTimeSlotMap("BSCI120-Lec", "M", "09:00", "09:50"));
        bsci120.getAoToTimeSlotMap().put("BSCI120-Lab", buildTimeSlotList("TH", "09:30", "10:20"));

        TimeConflictDataContainer engl101 = new TimeConflictDataContainer();
        engl101.setId("RegReqId-ENGL101-1");
        engl101.setAoToTimeSlotMap(buildAoToTimeSlotMap("ENGL101-Lec", "T", "12:30", "13:45"));


        List<TimeConflictDataContainer> newItems = new ArrayList<TimeConflictDataContainer>();
        List<TimeConflictDataContainer> existingItems = new ArrayList<TimeConflictDataContainer>();
        newItems.add(engl206);
        newItems.add(engl202);
        newItems.add(engl212);
        newItems.add(bsci105);
        newItems.add(engl201);
        newItems.add(bsci120);
        newItems.add(engl101);

        List<TimeConflictResult> results = timeConflictCalculator.getTimeConflictResults(newItems,existingItems);



        assertTrue(results != null);
        assertTrue(results.size() == 1);
        TimeConflictResult  conflicts = results.get(0);
        assertTrue(conflicts.getId().equals("RegReqId-BSCI120-1"));  // saying that the primary is conflicting with something
        assertTrue(conflicts.getConflictingItemMap().size() == 1);  // Make sure there's only 1 conflict... ie. we didn't count ourselves
        assertTrue(conflicts.getConflictingItemMap().containsKey("RegReqId-BSCI105-1"));  // this is what we should be conflicting with
        assertTrue(conflicts.getConflictingItemMap().get("RegReqId-BSCI105-1").contains("BSCI105-Lab")); // This is the ao that's causing the issues

    }

    @Test
    public void testTimeConflictCaculator_No_Conflicts_One_Item() throws Exception {
        TimeConflictCalculator timeConflictCalculator = new TimeConflictCalculator();

        // This is the positive case. ie. no conflicts
        TimeConflictDataContainer primaryContainer = new TimeConflictDataContainer();
        primaryContainer.setId("RegReqId-CHEM135-1");
        primaryContainer.setAoToTimeSlotMap(buildAoToTimeSlotMap("CHEM-135-A-Lec", "MW", "10:00", "12:00"));
        primaryContainer.getAoToTimeSlotMap().put("CHEM-135-A-Lab", buildTimeSlotList("TH", "09:00", "10:00"));



        List<TimeConflictDataContainer> newItems = new ArrayList<TimeConflictDataContainer>();
        List<TimeConflictDataContainer> existingItems = new ArrayList<TimeConflictDataContainer>();
        newItems.add(primaryContainer);

        List<TimeConflictResult> results = timeConflictCalculator.getTimeConflictResults(newItems,existingItems);



        assertTrue(results.isEmpty()); // there should be no conflicts

    }

    @Test
    public void testTimeConflictCaculator_No_Conflicts() throws Exception {
        TimeConflictCalculator timeConflictCalculator = new TimeConflictCalculator();

        // This is the positive case. ie. no conflicts
        TimeConflictDataContainer primaryContainer = new TimeConflictDataContainer();
        primaryContainer.setId("RegReqId-CHEM135-1");
        primaryContainer.setAoToTimeSlotMap(buildAoToTimeSlotMap("CHEM-135-A-Lec", "MW", "10:00", "12:00"));
        primaryContainer.getAoToTimeSlotMap().put("CHEM-135-A-Lab", buildTimeSlotList("TH", "09:00", "10:00"));

        TimeConflictDataContainer secondaryContainer = new TimeConflictDataContainer();
        secondaryContainer.setId("RegReqId-CHEM137-2");
        secondaryContainer.setAoToTimeSlotMap(buildAoToTimeSlotMap("CHEM-137-A-Lec", "F", "10:00", "12:00"));

        List<TimeConflictDataContainer> newItems = new ArrayList<TimeConflictDataContainer>();
        newItems.add(primaryContainer);
        newItems.add(secondaryContainer);

        TimeConflictResult conflicts = timeConflictCalculator.calculateConflicts(primaryContainer, newItems,0);

        assertTrue(conflicts == null); // there should be no conflicts

    }

    @Test
    public void testTimeConflictCaculator_Same_Reg_Group_In_Cart_Twice() throws Exception {
        TimeConflictCalculator timeConflictCalculator = new TimeConflictCalculator();

        // The first test we will be simulating what happens when a user adds the same
        // course to the cart twice. Note: they'll have unique reg req ids to tell them apart
        TimeConflictDataContainer primaryContainer = new TimeConflictDataContainer();
        primaryContainer.setId("RegReqId-CHEM135-1");
        primaryContainer.setAoToTimeSlotMap(buildAoToTimeSlotMap("CHEM-135-A-Lec", "MW", "10:00", "12:00"));
        //primaryContainer.getAoToTimeSlotMap().put("CHEM-135-A-Lab", buildTimeSlotList("TH", "09:00", "10:00"));

        TimeConflictDataContainer secondaryContainer = new TimeConflictDataContainer();
        secondaryContainer.setId("RegReqId-CHEM135-2");
        secondaryContainer.setAoToTimeSlotMap(buildAoToTimeSlotMap("CHEM-135-A-Lec", "MW", "10:00", "12:00"));

        List<TimeConflictDataContainer> newItems = new ArrayList<TimeConflictDataContainer>();
        newItems.add(primaryContainer);
        newItems.add(secondaryContainer);

        TimeConflictResult conflicts = timeConflictCalculator.calculateConflicts(primaryContainer, newItems,0);

        assertTrue(conflicts != null);
        assertTrue(conflicts.getId().equals("RegReqId-CHEM135-1"));  // saying that the primary is conflicting with something
        assertTrue(conflicts.getConflictingItemMap().size() == 1);  // Make sure there's only 1 conflict... ie. we didn't count ourselves
        assertTrue(conflicts.getConflictingItemMap().containsKey("RegReqId-CHEM135-2"));  // this is what we should be conflicting with
        assertTrue(conflicts.getConflictingItemMap().get("RegReqId-CHEM135-2").contains("CHEM-135-A-Lec")); // This is the ao that's causing the issues
    }

    @Test
    public void testTimeConflictCaculator_Target_The_Correct_AO() throws Exception {
        TimeConflictCalculator timeConflictCalculator = new TimeConflictCalculator();

        // The first test we will be simulating what happens when a user adds the same
        // course to the cart twice. Note: they'll have unique reg req ids to tell them apart
        TimeConflictDataContainer primaryContainer = new TimeConflictDataContainer();
        primaryContainer.setId("RegReqId-CHEM135-1");
        primaryContainer.setAoToTimeSlotMap(buildAoToTimeSlotMap("CHEM-135-A-Lec", "MW", "10:00", "12:00"));
        primaryContainer.getAoToTimeSlotMap().put("CHEM-135-A-Lab", buildTimeSlotList("TH", "09:00", "10:00"));

        TimeConflictDataContainer secondaryContainer = new TimeConflictDataContainer();
        secondaryContainer.setId("RegReqId-ENGL101-2");
        secondaryContainer.setAoToTimeSlotMap(buildAoToTimeSlotMap("ENGL-101-A-Lec", "TH", "09:50", "12:00"));

        List<TimeConflictDataContainer> newItems = new ArrayList<TimeConflictDataContainer>();
        newItems.add(primaryContainer);
        newItems.add(secondaryContainer);

        TimeConflictResult conflicts = timeConflictCalculator.calculateConflicts(primaryContainer, newItems,0);

        assertTrue(conflicts != null);
        assertTrue(conflicts.getId().equals("RegReqId-CHEM135-1"));  // saying that the primary is conflicting with something
        assertTrue(conflicts.getConflictingItemMap().size() == 1);  // Make sure there's only 1 conflict... ie. we didn't count ourselves
        assertTrue(conflicts.getConflictingItemMap().containsKey("RegReqId-ENGL101-2"));  // this is what we should be conflicting with
        assertTrue(conflicts.getConflictingItemMap().get("RegReqId-ENGL101-2").contains("ENGL-101-A-Lec")); // This is the ao that's causing the issues
    }

    private Map<String, List<TimeSlotInfo>>  buildAoToTimeSlotMap(String ao_id, String daysOfWeek, String startTime, String endTime){
        return buildAoToTimeSlotMap(false, ao_id, daysOfWeek, startTime,endTime);
    }


    private Map<String, List<TimeSlotInfo>>  buildAoToTimeSlotMap(boolean isTBA, String ao_id, String daysOfWeek, String startTime, String endTime){
        Map<String, List<TimeSlotInfo>> aoToTimeSlotMap = new HashMap<String, List<TimeSlotInfo>>();

        List l = new ArrayList<TimeSlotInfo>();
        l.add(buildTimeSlot(isTBA, daysOfWeek,startTime,endTime));
        aoToTimeSlotMap.put(ao_id,l  );
        return aoToTimeSlotMap;
    }


    protected static List<TimeSlotInfo> buildTimeSlotList(String daysOfWeek, String startTime, String endTime){
        return buildTimeSlotList(false, daysOfWeek, startTime,endTime);
    }

    protected static List<TimeSlotInfo> buildTimeSlotList(boolean isTBA, String daysOfWeek, String startTime, String endTime){
        List<TimeSlotInfo> timeSlotInfos = new ArrayList<TimeSlotInfo>();
        timeSlotInfos.add(buildTimeSlot(isTBA,daysOfWeek, startTime,endTime));
        return timeSlotInfos;

    }
    protected static TimeSlotInfo buildTimeSlot(boolean isTBA,String daysOfWeek, String startTime, String endTime){



        TimeSlotInfo timeSlotInfo = new TimeSlotInfo();

        if(isTBA) {
            timeSlotInfo.setTypeKey(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_TBA);
        }else {
            List<Integer> intDays = buildDaysOfWeek(daysOfWeek);
            Date start = DateFormatters.HOUR_MINUTE_TIME_FORMATTER.parse(startTime);
            Date end =   DateFormatters.HOUR_MINUTE_TIME_FORMATTER.parse(endTime);
            timeSlotInfo.setWeekdays(intDays);
            timeSlotInfo.setStartTime(new TimeOfDayInfo(start.getHours(), start.getMinutes(), start.getSeconds()));
            timeSlotInfo.setEndTime(new TimeOfDayInfo(end.getHours(), end.getMinutes(), end.getSeconds()));
        }
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
