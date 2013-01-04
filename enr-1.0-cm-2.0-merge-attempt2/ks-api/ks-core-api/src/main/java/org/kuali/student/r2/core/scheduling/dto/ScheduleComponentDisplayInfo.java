/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by mahtabme on 8/28/12
 */
package org.kuali.student.r2.core.scheduling.dto;

import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleComponentDisplay;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a reusable display object in the Scheduling Service for Schedule Component.
 *
 * @Version 2.0
 * @Author Mezba Mahtab
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleComponentDisplayInfo", propOrder = {"id",
        "room", "building", "timeSlots"
        })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code
public class ScheduleComponentDisplayInfo implements ScheduleComponentDisplay, Serializable {

    ////////////////////////
    // DATA VARIABLES
    ////////////////////////

    @XmlElement
    private String id;

    @XmlElement
    private RoomInfo room;

    @XmlElement
    private BuildingInfo building;

    @XmlElement
    private List<TimeSlotInfo> timeSlots;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    ////////////////////////
    // CONSTRUCTORS
    ////////////////////////

    public ScheduleComponentDisplayInfo() {
    }

    public ScheduleComponentDisplayInfo(ScheduleComponentDisplay scheduleComponentDisplay) {
        if (null != scheduleComponentDisplay) {
            this.room= scheduleComponentDisplay.getRoom();
            this.building = scheduleComponentDisplay.getBuilding();
            this.timeSlots = new ArrayList<TimeSlotInfo>();
            for (TimeSlotInfo timeSlotInfo: scheduleComponentDisplay.getTimeSlots()) {
                this.timeSlots.add(new TimeSlotInfo(timeSlotInfo));
            }
        }
    }

    ////////////////////////////////////
    // ACCESSORS AND MODIFIERS
    ////////////////////////////////////

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public RoomInfo getRoom() {
        return room;
    }

    public void setRoom(RoomInfo room) {
        this.room = room;
    }

    @Override
    public BuildingInfo getBuilding() {
        return building;
    }

    public void setBuilding(BuildingInfo building) {
        this.building = building;
    }

    @Override
    public List<TimeSlotInfo> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlotInfo> timeSlots) {
        this.timeSlots = timeSlots;
    }
}
