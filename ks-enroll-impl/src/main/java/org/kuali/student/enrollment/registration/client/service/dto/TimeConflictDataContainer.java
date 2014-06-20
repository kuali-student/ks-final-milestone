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

import java.util.List;
import java.util.Map;

/**
 * This class is a data structure containing necessary data for time conflict checks
 *
 * @author Kuali Student Team
 */
public class TimeConflictDataContainer {

    private String id; // unique id. can be regReqItemId or lprId
    private Map<String, List<TimeSlotInfo>> aoToTimeSlotMap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, List<TimeSlotInfo>> getAoToTimeSlotMap() {
        return aoToTimeSlotMap;
    }

    public void setAoToTimeSlotMap(Map<String, List<TimeSlotInfo>> aoToTimeSlotMap) {
        this.aoToTimeSlotMap = aoToTimeSlotMap;
    }
}
