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

import org.kuali.student.enrollment.registration.client.service.dto.TimeConflictDataContainer;
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

    public List<TimeConflictResult> getTimeConflictResults( List<TimeConflictDataContainer> newCoursesTimeConflictContainers, List<TimeConflictDataContainer> existingCoursesTimeConflictContainers)  {

        List<TimeConflictResult> conflictsExistingCourses = new ArrayList<TimeConflictResult>();

        for (TimeConflictDataContainer newTimeConflictDataContainer : newCoursesTimeConflictContainers) {
            if(existingCoursesTimeConflictContainers.isEmpty()){
                existingCoursesTimeConflictContainers.add(newTimeConflictDataContainer);
            }else {
                TimeConflictResult timeConflictResult = calculateConflicts(newTimeConflictDataContainer, existingCoursesTimeConflictContainers, 0);
                if (timeConflictResult != null) {
                    conflictsExistingCourses.add(timeConflictResult);
                } else {
                    existingCoursesTimeConflictContainers.add(newTimeConflictDataContainer);
                }
            }
        }

        return conflictsExistingCourses;
    }

    /**
     *
     * @param timeSlotContainer
     * @param containersToCompare
     * @param overlapInMinutes
     * @return Will return an object that represents the time conflict or null
     */
    public TimeConflictResult calculateConflicts(TimeConflictDataContainer timeSlotContainer, List<TimeConflictDataContainer> containersToCompare,
                                                 int overlapInMinutes) {
        TimeConflictResult tcResult = new TimeConflictResult();
        tcResult.setId(timeSlotContainer.getId());

        for(TimeConflictDataContainer compareAgainst : containersToCompare){
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

    public void computeConflictsBetweenContainers(TimeConflictResult conflict,
                                                  TimeConflictDataContainer primaryContainer,
                                                  TimeConflictDataContainer otherContainer) {
        Map<String, List<TimeSlotInfo>> primaryTimeSlotMap = primaryContainer.getAoToTimeSlotMap();
        Map<String, List<TimeSlotInfo>> otherTimeSlotMap = otherContainer.getAoToTimeSlotMap();

        for(String primaryAoId : primaryTimeSlotMap.keySet()){
            List<TimeSlotInfo> primaryAoTimeSlots = primaryTimeSlotMap.get(primaryAoId);

            for(String otherAoId : otherTimeSlotMap.keySet()){
                List<TimeSlotInfo> otherAoTimeSlots = otherTimeSlotMap.get(otherAoId);

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
                            if(!conflictingOtherAoIds.contains(otherAoId)){
                                conflictingOtherAoIds.add(otherAoId);
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
    private boolean doWeekdaysOverlap(List<Integer> weekdays, List<Integer> otherWeekdays) {
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
    private boolean doTimeSlotsConflict(TimeSlotInfo timeSlot, TimeSlotInfo otherTimeSlot) {
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
