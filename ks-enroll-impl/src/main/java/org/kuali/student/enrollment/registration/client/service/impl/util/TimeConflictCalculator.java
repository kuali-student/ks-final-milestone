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

    public Map<String, List<String>> calculateConflicts(TimeConflictDataContainer timeSlots, int overlapInMinutes){
        Map<String, List<String>> conflicts = new HashMap<String, List<String>>();
        List<String> ids = timeSlots.getIds();
        List<List<TimeSlotInfo>> timeSlotInfos = timeSlots.getTimeSlotInfos();


        String idKey,otherIdKey;
        for(int i=0; i < ids.size(); i++){
            idKey = ids.get(i);
            List<TimeSlotInfo> idValue = timeSlotInfos.get(i);
            //for each id(AO/RG), look at each other id
            for(int j=i+1; j < ids.size(); j++){
                otherIdKey = ids.get(j);
                List<TimeSlotInfo> otherIdValue = timeSlotInfos.get(j);
                //make sure it's not the same one or on the whitelist
                    for(TimeSlotInfo timeSlotInfo: idValue){
                        for(TimeSlotInfo otherTimeSlot: otherIdValue){
                            //for each original id timeslot, compare with each timeslot for the other id
                            for(Integer weekday: timeSlotInfo.getWeekdays()){
                                if(otherTimeSlot.getWeekdays().contains(weekday)){
                                    //if !( slot2.start >= slot1.end ||  slot1.end <= slot2.start)
                                    if(!((otherTimeSlot.getStartTime().isAfter(timeSlotInfo.getEndTime())
                                            || otherTimeSlot.getStartTime().equals(timeSlotInfo.getEndTime()))
                                            || (otherTimeSlot.getEndTime().isBefore(timeSlotInfo.getStartTime())
                                            || timeSlotInfo.getEndTime().equals(otherTimeSlot.getStartTime())))){
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
                    }
            }
        }
        return conflicts;
    }
}
