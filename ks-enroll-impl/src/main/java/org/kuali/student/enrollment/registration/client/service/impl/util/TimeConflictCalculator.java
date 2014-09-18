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
 * Created by cmuller on 5/29/14
 */
package org.kuali.student.enrollment.registration.client.service.impl.util;

import org.kuali.student.enrollment.registration.client.service.dto.TimeSlotCalculationContainer;
import org.kuali.student.enrollment.registration.client.service.dto.TimeConflictResult;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class checks sets of AOs or RGs and their corresponding timeslots for time conflicts
 *
 * @author Kuali Student Team
 */
public class TimeConflictCalculator {

    public static final Logger LOGGER = LoggerFactory.getLogger(TimeConflictCalculator.class);

    /**
     * Process the newCoursesTimeConflictContainers list in-order. Find any conflicts between each item in the new list
     * and itself, as well as each item in new list and each item in existingCoursesTimeConflictContainers.
     * @param newCoursesTimeConflictContainers
     * @param existingCoursesTimeConflictContainers
     * @return
     */
    public static List<TimeConflictResult> getTimeConflictInOrderResults( List<TimeSlotCalculationContainer> newCoursesTimeConflictContainers, List<TimeSlotCalculationContainer> existingCoursesTimeConflictContainers)  {

        List<TimeConflictResult> conflictsExistingCourses = new ArrayList<TimeConflictResult>();

        for (TimeSlotCalculationContainer newTimeSlotCalculationContainer : newCoursesTimeConflictContainers) {
            if(existingCoursesTimeConflictContainers.isEmpty()){
                existingCoursesTimeConflictContainers.add(newTimeSlotCalculationContainer);
            }else {
                TimeConflictResult timeConflictResult = calculateConflicts(newTimeSlotCalculationContainer, existingCoursesTimeConflictContainers, 0);
                if (timeConflictResult != null) {
                    conflictsExistingCourses.add(timeConflictResult);
                } else {
                    existingCoursesTimeConflictContainers.add(newTimeSlotCalculationContainer);
                }
            }
        }

        return conflictsExistingCourses;
    }

    /**
     * This method returns all conflicts between each data container.
     * So, given the set of N containers such that a & b are items in N.
     * Return conflicts between a->b, b->a where a != b.
     *
     * For example, there are containers for ENGL101 and CHEM235. If ENGL101 and CHEM235 conflict then there will be
     * a TimeConflictResult for each in the returned List.
     *
     * @param dataContainers
     * @return
     */
    public static List<TimeConflictResult> getTimeConflictResults( List<TimeSlotCalculationContainer> dataContainers)  {
        List<TimeConflictResult> conflicts = new ArrayList<TimeConflictResult>();

        for(TimeSlotCalculationContainer container : dataContainers){
            TimeConflictResult tcr = calculateConflicts(container, dataContainers, 0);
            if(tcr != null) {
                conflicts.add(tcr);
            }
        }

        return conflicts;
    }

    /**
     *
     * @param timeSlotContainer
     * @param containersToCompare
     * @param overlapInMinutes
     * @return Will return an object that represents the time conflict or null
     */
    public static TimeConflictResult calculateConflicts(TimeSlotCalculationContainer timeSlotContainer, List<TimeSlotCalculationContainer> containersToCompare,
                                                 int overlapInMinutes) {
        TimeConflictResult tcResult = new TimeConflictResult();
        tcResult.setId(timeSlotContainer.getId());

        for(TimeSlotCalculationContainer compareAgainst : containersToCompare){
            if(timeSlotContainer.getId().equals(compareAgainst.getId())){
                continue; // don't compare against yourself
            }

            computeConflictsBetweenContainers(tcResult, timeSlotContainer, compareAgainst);
        }

        // If there are no conflicts, return null
        if(tcResult.getConflictingItemMap().isEmpty()){
            tcResult = null;
        }

        return tcResult;

    }

    public static void computeConflictsBetweenContainers(TimeConflictResult conflict,
                                                  TimeSlotCalculationContainer primaryContainer,
                                                  TimeSlotCalculationContainer otherContainer) {
        Map<String, List<TimeSlotInfo>> primaryTimeSlotMap = primaryContainer.getAoToTimeSlotMap();
        Map<String, List<TimeSlotInfo>> otherTimeSlotMap = otherContainer.getAoToTimeSlotMap();

        for(Map.Entry<String, List<TimeSlotInfo>> entry: primaryTimeSlotMap.entrySet()){
            List<TimeSlotInfo> primaryAoTimeSlots = entry.getValue();

            for(Map.Entry<String, List<TimeSlotInfo>> otherEntry: otherTimeSlotMap.entrySet()){
                List<TimeSlotInfo> otherAoTimeSlots = otherEntry.getValue();

                for (TimeSlotInfo timeSlotInfo: primaryAoTimeSlots){
                    for (TimeSlotInfo otherTimeSlot: otherAoTimeSlots) {
                        //for each original id timeslot, compare with each timeslot for the other id
                        if (!doWeekdaysOverlap(timeSlotInfo.getWeekdays(), otherTimeSlot.getWeekdays())) {
                            // If there's no overlap in weekdays, skip
                            continue;
                        }
                        //if !( slot2.start >= slot1.end ||  slot1.end <= slot2.start)
                        if (doTimeSlotsConflict(timeSlotInfo, otherTimeSlot)){
                            //Conflict
                            if(!conflict.getConflictingItemMap().containsKey(otherContainer.getId())){
                                conflict.getConflictingItemMap().put(otherContainer.getId(), new ArrayList<String>());
                            }
                            List<String> conflictingOtherAoIds = conflict.getConflictingItemMap().get(otherContainer.getId());
                            if(!conflictingOtherAoIds.contains(otherEntry.getKey())){
                                conflictingOtherAoIds.add(otherEntry.getKey());
                            }
                        }
                    }
                }

            }
        }


    }



    /**
     * Determines if two lists of weekdays (represented by a list of integers) overlap
     * @param weekdays One set of weekdays
     * @param otherWeekdays Another set of weekdays
     * @return true, if there is at least one weekday in common
     */
    private static boolean doWeekdaysOverlap(List<Integer> weekdays, List<Integer> otherWeekdays) {
        for (int day: weekdays) {
            if (otherWeekdays.contains(day)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if timeSlot conflicts (overlaps by a minute or more) with otherTimeSlot.
     * @param timeSlot Time slot
     * @param otherTimeSlot A different time slot
     * @return true, if timeSlot overlaps with otherTimeSlot
     */
    private static boolean doTimeSlotsConflict(TimeSlotInfo timeSlot, TimeSlotInfo otherTimeSlot) {
        boolean bRet =false;
        try {
            if(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_TBA.equals(timeSlot.getTypeKey()) ||
                    SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_TBA.equals(otherTimeSlot.getTypeKey())  ) {
                bRet = false; // don't compare tba time slots
            } else {
                bRet = !((otherTimeSlot.getStartTime().isAfter(timeSlot.getEndTime())
                        || otherTimeSlot.getStartTime().equals(timeSlot.getEndTime()))
                        || (otherTimeSlot.getEndTime().isBefore(timeSlot.getStartTime())
                        || timeSlot.getEndTime().equals(otherTimeSlot.getStartTime())));
            }
        } catch (NullPointerException ex){
            String err = "Null Pointer Exception in time conflict check: " + timeSlot.toString() + "/n" + otherTimeSlot.toString();
            NullPointerException newEx = new NullPointerException(err);
            LOGGER.error(err, newEx);
            throw newEx;
        }

        return  bRet;
    }
}
