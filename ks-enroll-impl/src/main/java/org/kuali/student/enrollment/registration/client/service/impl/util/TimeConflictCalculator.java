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

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

import javax.xml.namespace.QName;
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

    private static  SchedulingService schedulingService;

    Map<String, List<String>> calculateConflicts(Map<String, List<TimeSlotInfo>> timeSlots, int overlapInMinutes){
        Map<String, List<String>> conflicts = new HashMap<String, List<String>>();
        ArrayList<String> nonConflicts = new ArrayList<String>();

        for(String id: timeSlots.keySet()){
            //for each id(AO/RG), look at each other id
            for(String otherId: timeSlots.keySet()){
                //make sure it's not the same one or on the whitelist
                if(!(otherId.equals(id)) && nonConflicts.contains(otherId)){
                    for(TimeSlotInfo timeSlotInfo: timeSlots.get(id)){
                        for(TimeSlotInfo otherTimeSlot: timeSlots.get(otherId)){
                            //for each original id timeslot, compare with each timeslot for the other id
                            for(Integer weekday: timeSlotInfo.getWeekdays()){
                                if(otherTimeSlot.getWeekdays().contains(weekday)){
                                    //if !( slot2.start >= slot1.end ||  slot1.end <= slot2.start)
                                    if(!((otherTimeSlot.getStartTime().isAfter(timeSlotInfo.getEndTime())
                                            || otherTimeSlot.getStartTime().equals(timeSlotInfo.getEndTime()))
                                        || (timeSlotInfo.getEndTime().isBefore(otherTimeSlot.getStartTime())
                                            || timeSlotInfo.getEndTime().equals(otherTimeSlot.getStartTime())))){
                                        //Conflict
                                        if(conflicts.containsKey(id)){
                                            if(!(conflicts.get(id).contains(otherId))){
                                               conflicts.get(id).add(otherId);
                                            }
                                        } else {
                                            ArrayList<String> conflictList = new ArrayList<String>();
                                            conflictList.add(otherId);
                                            conflicts.put(id, conflictList);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //Whitelist if no conflicts are found to prevent repeating checks
            if(!(conflicts.containsKey(id))){
                nonConflicts.add(id);
            }
        }
        return conflicts;
    }

    private static SchedulingService getSchedulingService() {
        if (schedulingService == null) {
            QName qname = new QName(SchedulingServiceConstants.NAMESPACE,
                    SchedulingServiceConstants.SERVICE_NAME_LOCAL_PART);
            schedulingService =  GlobalResourceLoader.getService(qname);
        }
        return schedulingService;
    }
}
