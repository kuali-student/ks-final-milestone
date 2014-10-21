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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.infc.Type;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.infc.Org;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.infc.Building;
import org.kuali.student.r2.core.room.infc.Room;
import org.kuali.student.r2.core.scheduling.infc.ScheduleRequestComponentDisplay;
import org.kuali.student.r2.core.scheduling.infc.TimeSlot;

/**
 * This class represents a reusable display object in the Scheduling Service for Schedule Request Component.
 *
 * @Version 2.0
 * @Author Mezba Mahtab
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleRequestComponentDisplayInfo", propOrder = {"id",
        "rooms", "buildings", "timeSlots", "orgs", "resourceTypes", "isTBA"
        , "_futureElements" }) 
public class ScheduleRequestComponentDisplayInfo implements ScheduleRequestComponentDisplay, Serializable {

    ////////////////////////
    // DATA VARIABLES
    ////////////////////////

    @XmlElement
    private String id;

    @XmlElement
    private List<RoomInfo> rooms;

    @XmlElement
    private List<BuildingInfo> buildings;

    @XmlElement
    private List<TimeSlotInfo> timeSlots;

    @XmlElement
    private List<OrgInfo> orgs;

    @XmlElement
    private List<TypeInfo> resourceTypes;

    @XmlElement
    private Boolean isTBA;

    @XmlAnyElement
    private List<Object> _futureElements;  

    ////////////////////////
    // CONSTRUCTORS
    ////////////////////////

    public ScheduleRequestComponentDisplayInfo() {
    }

    public ScheduleRequestComponentDisplayInfo(ScheduleRequestComponentDisplay scheduleRequestComponentDisplay) {
        if (null != scheduleRequestComponentDisplay) {
            this.rooms = new ArrayList<RoomInfo>();
            for (Room room: scheduleRequestComponentDisplay.getRooms()) {
                this.rooms.add(new RoomInfo(room));
            }
            this.buildings = new ArrayList<BuildingInfo>();
            for (Building building: scheduleRequestComponentDisplay.getBuildings()) {
                this.buildings.add(new BuildingInfo(building));
            }
            this.timeSlots = new ArrayList<TimeSlotInfo>();
            for (TimeSlot timeSlot: scheduleRequestComponentDisplay.getTimeSlots()) {
                this.timeSlots.add(new TimeSlotInfo(timeSlot));
            }
            this.orgs = new ArrayList<OrgInfo>();
            for (Org org: scheduleRequestComponentDisplay.getOrgs()) {
                this.orgs.add(new OrgInfo(org));
            }
            this.resourceTypes= new ArrayList<TypeInfo>();
            for (Type type: scheduleRequestComponentDisplay.getResourceTypes()) {
                this.resourceTypes.add(new TypeInfo(type));
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
    public List<? extends Room> getRooms() {
        if (this.rooms==null) {
            return new ArrayList<RoomInfo>();
        } else {
            return rooms;
        }
    }

    public void setRooms(List<RoomInfo> rooms) {
        this.rooms = rooms;
    }

    @Override
    public List<? extends Building> getBuildings() {
        if (this.buildings==null) {
            return new ArrayList<BuildingInfo>();
        } else {
            return buildings;
        }
    }

    public void setBuildings(List<BuildingInfo> buildings) {
        this.buildings = buildings;
    }

    @Override
    public List<? extends TimeSlot> getTimeSlots() {
        if (this.timeSlots==null) {
            return new ArrayList<TimeSlotInfo>();
        } else {
            return timeSlots;
        }
    }

    public void setTimeSlots(List<TimeSlotInfo> timeSlots) {
        this.timeSlots = timeSlots;
    }

    @Override
    public List<? extends Org> getOrgs() {
        if (this.orgs==null) {
            return new ArrayList<OrgInfo>();
        } else {
            return orgs;
        }
    }

    public void setOrgs(List<OrgInfo> orgs) {
        this.orgs = orgs;
    }

    @Override
    public List<? extends Type> getResourceTypes() {
        if (this.resourceTypes==null) {
            return new ArrayList<TypeInfo>();
        } else {
            return resourceTypes;
        }
    }

    public void setResourceTypes(List<TypeInfo> resourceTypes) {
        this.resourceTypes = resourceTypes;
    }

    @Override
    public Boolean getIsTBA() {
        return isTBA;
    }

    public void setIsTBA(Boolean isTBA) {
        this.isTBA = isTBA;
    }
}
