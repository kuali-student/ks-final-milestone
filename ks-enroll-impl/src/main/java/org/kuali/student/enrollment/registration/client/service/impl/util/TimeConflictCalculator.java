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
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class checks sets of AOs or RGs and their corresponding timeslots for time conflicts
 *
 * @author Kuali Student Team
 */
public class TimeConflictCalculator {

    /**
     * Computes conflicts between id's (of RGs or AOs, etc)
     * @param timeSlotContainer Contains
     * @param overlapInMinutes 0 indicates that an overlap of at least one minute must occur (currently, ignored)
     * @return A map between ids (RGs and AOs) and a list of ids indicating that that id conflicts with.  Each
     * id is associated with a list of time slots.
     */
    public Map<String, List<String>> calculateConflicts(TimeConflictDataContainer timeSlotContainer,
                                                        int overlapInMinutes) {
        List<String> ids = timeSlotContainer.getIds();
        List<List<TimeSlotInfo>> timeSlotInfosList = timeSlotContainer.getTimeSlotInfos();

        String idKey, otherIdKey;
        Map<String, List<String>> conflicts = new HashMap<String, List<String>>();
        for(int i = 0; i < ids.size(); i++) {
            idKey = ids.get(i); // Fetch id (e.g. RG id or AO id)
            List<TimeSlotInfo> idTimeSlots = timeSlotInfosList.get(i); // And fetch corresponding time slots
            //for each id(AO/RG), look at each other id, start at i + 1 to avoid revisiting same time slots
            for (int j = i + 1; j < ids.size(); j++) {
                otherIdKey = ids.get(j);
                List<TimeSlotInfo> otherIdTimeSlots = timeSlotInfosList.get(j);
                //make sure it's not the same one or on the whitelist
                computeConflictsBetweenTimeSlots(conflicts, idTimeSlots, otherIdTimeSlots, idKey, otherIdKey);
            }
        }
        return conflicts;
    }

    /**
     * Given one list of time slots and another list of time slots, attempts to find if there is a conflict
     * with one time slot in the first list with a time slot in the second list.  If so, it adds it to conflicts
     * (see first parameter).
     * @param conflicts A map between ids and a list of ids, indicating what the id conflicts with.  This
     *                  stores the result of the call (thus, a void return value)
     * @param idTimeSlots The list of time slots associated with one id
     * @param otherIdTimeSlots The list of time slots associated with another id
     * @param idKey The id that is associated with idTimeSlots (can be a RG id or an AO id, etc)
     * @param otherIdKey The other id that is associated with otherIdTimeSlots
     */
    private void computeConflictsBetweenTimeSlots(Map<String, List<String>> conflicts,
                                                  List<TimeSlotInfo> idTimeSlots,
                                                  List<TimeSlotInfo> otherIdTimeSlots,
                                                  String idKey,
                                                  String otherIdKey) {
        for (TimeSlotInfo timeSlotInfo: idTimeSlots){
            for (TimeSlotInfo otherTimeSlot: otherIdTimeSlots) {
                //for each original id timeslot, compare with each timeslot for the other id
                if (!doWeekdaysOverlap(timeSlotInfo.getWeekdays(), otherTimeSlot.getWeekdays())) {
                    // If there's no overlap in weekdays, skip
                    continue;
                }
                //if !( slot2.start >= slot1.end ||  slot1.end <= slot2.start)
                if (doTimeSlotsConflict(timeSlotInfo, otherTimeSlot)){
                    //Conflict
                    if(conflicts.containsKey(idKey)){
                        if(!(conflicts.get(idKey).contains(otherIdKey))){
                            conflicts.get(idKey).add(otherIdKey);
                        }
                    } else {
                        ArrayList<String> conflictList = new ArrayList<String>();
                        conflictList.add(otherIdKey);
                        conflicts.put(idKey, conflictList);
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
        return !((otherTimeSlot.getStartTime().isAfter(timeSlot.getEndTime())
                || otherTimeSlot.getStartTime().equals(timeSlot.getEndTime()))
                || (otherTimeSlot.getEndTime().isBefore(timeSlot.getStartTime())
                || timeSlot.getEndTime().equals(otherTimeSlot.getStartTime())));
    }
}
