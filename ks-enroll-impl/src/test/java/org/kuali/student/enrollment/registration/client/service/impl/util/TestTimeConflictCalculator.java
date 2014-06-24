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

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.enrollment.registration.client.service.dto.TimeSlotCalculationContainer;
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


    TimeSlotCalculationContainer engl206 = null;
    TimeSlotCalculationContainer engl202 = null;
    TimeSlotCalculationContainer engl212 = null;
    TimeSlotCalculationContainer bsci105 = null;
    TimeSlotCalculationContainer engl201 = null;
    TimeSlotCalculationContainer bsci120 = null;
    TimeSlotCalculationContainer engl101 = null;

    public static final String MON_WED_FRI = "MWF";
    public static final String MON_WED = "MW";
    public static final String TUE_THU = "TH";
    public static final String MON = "M";
    public static final String TUE = "T";
    public static final String FRI = "F";

    public static final String TIME_9_AM = "09:00";
    public static final String TIME_9_30AM = "09:30";
    public static final String TIME_9_50AM = "09:50";
    public static final String TIME_10AM = "10:00";
    public static final String TIME_10_20AM = "10:20";
    public static final String TIME_10_50AM = "10:50";
    public static final String TIME_NOON = "12:00";
    public static final String TIME_12_30PM = "12:30";
    public static final String TIME_12_50PM = "12:50";
    public static final String TIME_1_45PM = "13:45";
    public static final String TIME_2_PM = "14:00";
    public static final String TIME_4_50PM = "16:50";

    public static final String REG_REQ_ID_BSCI105_1 = "RegReqId-BSCI105-1";
    public static final String REG_REQ_ID_BSCI120_1 = "RegReqId-BSCI120-1";
    public static final String REG_REQ_ID_CHEM135_1 = "RegReqId-CHEM135-1";
    public static final String REG_REQ_ID_CHEM135_2 = "RegReqId-CHEM135-2";
    public static final String REG_REQ_ID_CHEM137_2 = "RegReqId-CHEM137-2";
    public static final String REG_REQ_ID_ENGL101_1 = "RegReqId-ENGL101-1";
    public static final String REG_REQ_ID_ENGL101_2 = "RegReqId-ENGL101-2";
    public static final String REG_REQ_ID_ENGL201_1 = "RegReqId-ENGL201-1";
    public static final String REG_REQ_ID_ENGL202_1 = "RegReqId-ENGL202-1";
    public static final String REG_REQ_ID_ENGL206_1 = "RegReqId-ENGL206-1";
    public static final String REG_REQ_ID_ENGL212_1 = "RegReqId-ENGL212-1";

    public static final String BSCI105_LAB = "BSCI105-Lab";
    public static final String BSCI105_LEC = "BSCI105-Lec";

    public static final String BSCI120_LEC = "BSCI120-Lec";
    public static final String BSCI120_LAB = "BSCI120-Lab";

    public static final String CHEM_135_A_LEC = "CHEM-135-A-Lec";
    public static final String CHEM_135_A_LAB = "CHEM-135-A-Lab";
    public static final String CHEM_137_A_LEC = "CHEM-137-A-Lec";

    public static final String ENGL101_LEC = "ENGL101-Lec";
    public static final String ENGL_101_A_LEC = "ENGL-101-A-Lec";
    public static final String ENGL201_LEC = "ENGL201-Lec";

    public static final String ENGL202_LEC = "ENGL202-Lec";
    public static final String ENGL206_LEC = "ENGL206-Lec";
    public static final String ENGL212_LEC = "ENGL212-Lec";


    /**
     * this just sets up some of the base courses
     */
    @Before
    public void init(){

        engl206 = new TimeSlotCalculationContainer();
        engl206.setId(REG_REQ_ID_ENGL206_1);
        engl206.setAoToTimeSlotMap(buildAoToTimeSlotMap(true, ENGL206_LEC, null, null, null));

        engl202 = new TimeSlotCalculationContainer();
        engl202.setId(REG_REQ_ID_ENGL202_1);
        engl202.setAoToTimeSlotMap(buildAoToTimeSlotMap(ENGL202_LEC, MON_WED_FRI, TIME_10AM, TIME_10_50AM));

        engl212 = new TimeSlotCalculationContainer();
        engl212.setId(REG_REQ_ID_ENGL212_1);
        engl212.setAoToTimeSlotMap(buildAoToTimeSlotMap(ENGL212_LEC, MON_WED_FRI, TIME_NOON, TIME_12_50PM));

        bsci105 = new TimeSlotCalculationContainer();
        bsci105.setId(REG_REQ_ID_BSCI105_1);
        bsci105.setAoToTimeSlotMap(buildAoToTimeSlotMap(BSCI105_LEC, MON, TIME_2_PM, TestTimeConflictCalculator.TIME_4_50PM));
        bsci105.getAoToTimeSlotMap().put(BSCI105_LAB, buildTimeSlotList(MON_WED_FRI, TIME_9_AM, TIME_9_50AM));

        engl201 = new TimeSlotCalculationContainer();
        engl201.setId(REG_REQ_ID_ENGL201_1);
        engl201.setAoToTimeSlotMap(buildAoToTimeSlotMap(true, ENGL201_LEC, null, null, null));

        bsci120 = new TimeSlotCalculationContainer();
        bsci120.setId(REG_REQ_ID_BSCI120_1);
        bsci120.setAoToTimeSlotMap(buildAoToTimeSlotMap(BSCI120_LEC, MON, TIME_9_AM, TIME_9_50AM));
        bsci120.getAoToTimeSlotMap().put(BSCI120_LAB, buildTimeSlotList(TUE_THU, TIME_9_30AM, TIME_10_20AM));

        engl101 = new TimeSlotCalculationContainer();
        engl101.setId(REG_REQ_ID_ENGL101_1);
        engl101.setAoToTimeSlotMap(buildAoToTimeSlotMap(ENGL101_LEC, TUE, TIME_12_30PM, TIME_1_45PM));
    }


    @Test
    public void testTimeConflictCaculator_New_and_existing_InOrder() throws Exception {

        List<TimeSlotCalculationContainer> newItems = new ArrayList<TimeSlotCalculationContainer>();
        List<TimeSlotCalculationContainer> existingItems = new ArrayList<TimeSlotCalculationContainer>();
        existingItems.add(engl101);
        existingItems.add(bsci120);
        existingItems.add(engl201);
        newItems.add(bsci105);

        List<TimeConflictResult> results = TimeConflictCalculator.getTimeConflictInOrderResults(newItems,existingItems);

        assertTrue(results != null);
        assertTrue(results.size() == 1);
        TimeConflictResult  conflicts = results.get(0);
        assertTrue(conflicts.getId().equals(REG_REQ_ID_BSCI105_1));  // saying that the primary is conflicting with something
        assertTrue(conflicts.getConflictingItemMap().size() == 1);  // Make sure there's only 1 conflict... ie. we didn't count ourselves
        assertTrue(conflicts.getConflictingItemMap().containsKey(REG_REQ_ID_BSCI120_1));  // this is what we should be conflicting with
        assertTrue(conflicts.getConflictingItemMap().get(REG_REQ_ID_BSCI120_1).contains(BSCI120_LEC)); // This is the ao that's causing the issues

    }

    private TimeConflictResult findTimeConflictInList(String id, List<TimeConflictResult> timeConflicts){
        for(TimeConflictResult timeConflictResult : timeConflicts){
            if(timeConflictResult.getId().equals(id)){
                return timeConflictResult;
            }
        }
        return null;
    }

    @Test
    public void testTimeConflictCaculator_ConflictTest() throws Exception {
        TimeConflictCalculator timeConflictCalculator = new TimeConflictCalculator();

        List<TimeSlotCalculationContainer> newItems = new ArrayList<TimeSlotCalculationContainer>();
        newItems.add(engl206);
        newItems.add(engl202);
        newItems.add(engl212);
        newItems.add(bsci105);
        newItems.add(engl201);
        newItems.add(bsci120);
        newItems.add(engl101);

        List<TimeConflictResult> results = timeConflictCalculator.getTimeConflictResults(newItems);



        assertTrue(results != null);
        assertTrue(results.size() == 2);
        TimeConflictResult  conflicts = findTimeConflictInList(REG_REQ_ID_BSCI105_1, results);
        assertTrue(conflicts.getId().equals(REG_REQ_ID_BSCI105_1));  // saying that the primary is conflicting with something
        assertTrue(conflicts.getConflictingItemMap().size() == 1);  // Make sure there's only 1 conflict... ie. we didn't count ourselves
        assertTrue(conflicts.getConflictingItemMap().containsKey(REG_REQ_ID_BSCI120_1));  // this is what we should be conflicting with
        assertTrue(conflicts.getConflictingItemMap().get(REG_REQ_ID_BSCI120_1).contains(BSCI120_LEC)); // This is the ao that's causing the issues

        conflicts = findTimeConflictInList(REG_REQ_ID_BSCI120_1, results);
        assertTrue(conflicts.getId().equals(REG_REQ_ID_BSCI120_1));  // saying that the primary is conflicting with something
        assertTrue(conflicts.getConflictingItemMap().size() == 1);  // Make sure there's only 1 conflict... ie. we didn't count ourselves
        assertTrue(conflicts.getConflictingItemMap().containsKey(REG_REQ_ID_BSCI105_1));  // this is what we should be conflicting with
        assertTrue(conflicts.getConflictingItemMap().get(REG_REQ_ID_BSCI105_1).contains(BSCI105_LAB)); // This is the ao that's causing the issues

    }

    @Test
    public void testTimeConflictCaculator_EmptyTest() throws Exception {

        List<TimeSlotCalculationContainer> newItems = new ArrayList<TimeSlotCalculationContainer>();
        List<TimeConflictResult> results = TimeConflictCalculator.getTimeConflictResults(newItems);

        assertTrue(results.isEmpty()); // there should be no conflicts

    }

    @Test
    public void testTimeConflictCaculator_No_Conflicts_One_Item() throws Exception {
        TimeConflictCalculator timeConflictCalculator = new TimeConflictCalculator();

        // This is the positive case. ie. no conflicts
        TimeSlotCalculationContainer primaryContainer = new TimeSlotCalculationContainer();
        primaryContainer.setId(REG_REQ_ID_CHEM135_1);
        primaryContainer.setAoToTimeSlotMap(buildAoToTimeSlotMap(CHEM_135_A_LEC, MON_WED, TIME_10AM, TIME_NOON));
        primaryContainer.getAoToTimeSlotMap().put(CHEM_135_A_LAB, buildTimeSlotList(TUE_THU, TIME_9_AM, TIME_10AM));



        List<TimeSlotCalculationContainer> newItems = new ArrayList<TimeSlotCalculationContainer>();
        newItems.add(primaryContainer);

        List<TimeConflictResult> results = timeConflictCalculator.getTimeConflictResults(newItems);



        assertTrue(results.isEmpty()); // there should be no conflicts

    }

    @Test
    public void testTimeConflictCaculator_No_Conflicts() throws Exception {
        TimeConflictCalculator timeConflictCalculator = new TimeConflictCalculator();

        // This is the positive case. ie. no conflicts
        TimeSlotCalculationContainer primaryContainer = new TimeSlotCalculationContainer();
        primaryContainer.setId(REG_REQ_ID_CHEM135_1);
        primaryContainer.setAoToTimeSlotMap(buildAoToTimeSlotMap(CHEM_135_A_LEC, MON_WED, TIME_10AM, TIME_NOON));
        primaryContainer.getAoToTimeSlotMap().put(CHEM_135_A_LAB, buildTimeSlotList(TUE_THU, TIME_9_AM, TIME_10AM));

        TimeSlotCalculationContainer secondaryContainer = new TimeSlotCalculationContainer();
        secondaryContainer.setId(REG_REQ_ID_CHEM137_2);
        secondaryContainer.setAoToTimeSlotMap(buildAoToTimeSlotMap(CHEM_137_A_LEC, FRI, TIME_10AM, TIME_NOON));

        List<TimeSlotCalculationContainer> newItems = new ArrayList<TimeSlotCalculationContainer>();
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
        TimeSlotCalculationContainer primaryContainer = new TimeSlotCalculationContainer();
        primaryContainer.setId(REG_REQ_ID_CHEM135_1);
        primaryContainer.setAoToTimeSlotMap(buildAoToTimeSlotMap(CHEM_135_A_LEC, MON_WED, TIME_10AM, TIME_NOON));
        //primaryContainer.getAoToTimeSlotMap().put("CHEM-135-A-Lab", buildTimeSlotList("TH", "09:00", TIME_10AM));

        TimeSlotCalculationContainer secondaryContainer = new TimeSlotCalculationContainer();
        secondaryContainer.setId(REG_REQ_ID_CHEM135_2);
        secondaryContainer.setAoToTimeSlotMap(buildAoToTimeSlotMap(CHEM_135_A_LEC, MON_WED, TIME_10AM, TIME_NOON));

        List<TimeSlotCalculationContainer> newItems = new ArrayList<TimeSlotCalculationContainer>();
        newItems.add(primaryContainer);
        newItems.add(secondaryContainer);

        TimeConflictResult conflicts = timeConflictCalculator.calculateConflicts(primaryContainer, newItems,0);

        assertTrue(conflicts != null);
        assertTrue(conflicts.getId().equals(REG_REQ_ID_CHEM135_1));  // saying that the primary is conflicting with something
        assertTrue(conflicts.getConflictingItemMap().size() == 1);  // Make sure there's only 1 conflict... ie. we didn't count ourselves
        assertTrue(conflicts.getConflictingItemMap().containsKey(REG_REQ_ID_CHEM135_2));  // this is what we should be conflicting with
        assertTrue(conflicts.getConflictingItemMap().get(REG_REQ_ID_CHEM135_2).contains(CHEM_135_A_LEC)); // This is the ao that's causing the issues
    }

    @Test
    public void testTimeConflictCaculator_Target_The_Correct_AO() throws Exception {
        TimeConflictCalculator timeConflictCalculator = new TimeConflictCalculator();

        // The first test we will be simulating what happens when a user adds the same
        // course to the cart twice. Note: they'll have unique reg req ids to tell them apart
        TimeSlotCalculationContainer primaryContainer = new TimeSlotCalculationContainer();
        primaryContainer.setId(REG_REQ_ID_CHEM135_1);
        primaryContainer.setAoToTimeSlotMap(buildAoToTimeSlotMap(CHEM_135_A_LEC, MON_WED, TIME_10AM, TIME_NOON));
        primaryContainer.getAoToTimeSlotMap().put(CHEM_135_A_LAB, buildTimeSlotList(TUE_THU, TIME_9_AM, TIME_10AM));

        TimeSlotCalculationContainer secondaryContainer = new TimeSlotCalculationContainer();
        secondaryContainer.setId(REG_REQ_ID_ENGL101_2);
        secondaryContainer.setAoToTimeSlotMap(buildAoToTimeSlotMap(ENGL_101_A_LEC, TUE_THU, TIME_9_50AM, TIME_NOON));

        List<TimeSlotCalculationContainer> newItems = new ArrayList<TimeSlotCalculationContainer>();
        newItems.add(primaryContainer);
        newItems.add(secondaryContainer);

        TimeConflictResult conflicts = timeConflictCalculator.calculateConflicts(primaryContainer, newItems,0);

        assertTrue(conflicts != null);
        assertTrue(conflicts.getId().equals(REG_REQ_ID_CHEM135_1));  // saying that the primary is conflicting with something
        assertTrue(conflicts.getConflictingItemMap().size() == 1);  // Make sure there's only 1 conflict... ie. we didn't count ourselves
        assertTrue(conflicts.getConflictingItemMap().containsKey(REG_REQ_ID_ENGL101_2));  // this is what we should be conflicting with
        assertTrue(conflicts.getConflictingItemMap().get(REG_REQ_ID_ENGL101_2).contains(ENGL_101_A_LEC)); // This is the ao that's causing the issues
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
