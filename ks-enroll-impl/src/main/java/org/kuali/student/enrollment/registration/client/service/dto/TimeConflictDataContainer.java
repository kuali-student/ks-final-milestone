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
 * Created by cmuller on 6/13/14
 */
package org.kuali.student.enrollment.registration.client.service.dto;

import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a data structure containing necessary data for time conflict checks
 *
 * @author Kuali Student Team
 */
public class TimeConflictDataContainer {

    private List<String> ids;
    private List<List<TimeSlotInfo>> timeSlotInfos;

    public TimeConflictDataContainer(){
        ids = new ArrayList<String>();
        timeSlotInfos = new ArrayList<List<TimeSlotInfo>>();
    }

    public TimeConflictDataContainer(List<String> ids, List<List<TimeSlotInfo>> timeSlots){
        this.ids = ids;
        this.timeSlotInfos = timeSlots;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public List<List<TimeSlotInfo>> getTimeSlotInfos() {
        return timeSlotInfos;
    }

    public void setTimeSlotInfos(List<List<TimeSlotInfo>> timeSlotInfos) {
        this.timeSlotInfos = timeSlotInfos;
    }


}
