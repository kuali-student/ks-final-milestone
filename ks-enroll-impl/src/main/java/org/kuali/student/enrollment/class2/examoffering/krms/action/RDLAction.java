/**
 * Copyright 2005-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class2.examoffering.krms.action;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.framework.engine.Action;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import java.util.List;

/**
 * This action is executed on a successful evaluation of a rule that is part of the final exam matrix.
 *
 * It contains parameters to build the schedule request component and timeslot for the exam offering.
 *
 * @author Kuali Student Team
 */
public class RDLAction implements Action {

    private List<Integer> weekdays;
    private String startTime;
    private String endTime;
    private String buildingId;
    private String roomId;
    private boolean tba;

    @Override
    public void execute(ExecutionEnvironment environment) {

        ScheduleRequestComponentInfo componentInfo = new ScheduleRequestComponentInfo();
        componentInfo.setIsTBA(this.isTba());
        componentInfo.getBuildingIds().add(this.getBuildingId());
        componentInfo.getRoomIds().add(this.getRoomId());

        TimeSlotInfo timeSlot = new TimeSlotInfo();
        timeSlot.setTypeKey(SchedulingServiceConstants.TIME_SLOT_TYPE_EXAM);
        timeSlot.setStateKey(SchedulingServiceConstants.TIME_SLOT_STATE_ACTIVE);
        timeSlot.setWeekdays(this.getWeekdays());

        TimeOfDayInfo startTimeOfDayInfo = new TimeOfDayInfo();
        startTimeOfDayInfo.setMilliSeconds(Long.valueOf(this.getStartTime()));
        timeSlot.setStartTime(startTimeOfDayInfo);

        TimeOfDayInfo endTimeOfDayInfo = new TimeOfDayInfo();
        endTimeOfDayInfo.setMilliSeconds(Long.valueOf(this.getEndTime()));
        timeSlot.setEndTime(endTimeOfDayInfo);

        // set our attribute on the engine results
        environment.getEngineResults().setAttribute("scheduleRequestComponentInfo", componentInfo);
        environment.getEngineResults().setAttribute("timeslotInfo", timeSlot);
    }

    /**
     * @see org.kuali.rice.krms.framework.engine.Action#executeSimulation(org.kuali.rice.krms.api.engine.ExecutionEnvironment)
     */
    @Override
    public void executeSimulation(ExecutionEnvironment environment) {
        throw new UnsupportedOperationException();
    }

    public List<Integer> getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(List<Integer> weekdays) {
        this.weekdays = weekdays;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public boolean isTba() {
        return tba;
    }

    public void setTba(boolean tba) {
        this.tba = tba;
    }
}
