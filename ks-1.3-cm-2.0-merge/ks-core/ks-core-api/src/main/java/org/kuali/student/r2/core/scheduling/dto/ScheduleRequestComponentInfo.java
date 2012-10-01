/*
 * Copyright 2012 The Kuali Foundation Licensed under the
 *  Educational Community License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may
 *  obtain a copy of the License at
 *
 *   http://www.osedu.org/licenses/ECL-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS"
 *  BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package org.kuali.student.r2.core.scheduling.dto;

import org.kuali.student.r2.core.scheduling.infc.ScheduleRequestComponent;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleRequestComponentInfo", propOrder = {"id", "buildingIds", "campusIds", "orgIds", "resourceTypeKeys", "roomIds", "timeSlotIds", "_futureElements"})
public class ScheduleRequestComponentInfo implements ScheduleRequestComponent, Serializable {

    @XmlElement
    private String id;
    @XmlElement
    private List<String> buildingIds;
    @XmlElement
    private List<String> campusIds;
    @XmlElement
    private List<String> orgIds;
    @XmlElement
    private List<String> resourceTypeKeys;
    @XmlElement
    private List<String> roomIds;
    @XmlElement
    private List<String> timeSlotIds;
    @XmlAnyElement
    private List<Element> _futureElements;

    public ScheduleRequestComponentInfo() {
    }

    public ScheduleRequestComponentInfo(ScheduleRequestComponent scheduleRequestComponent) {
        if (null != scheduleRequestComponent) {
            this.id = scheduleRequestComponent.getId();
            this.buildingIds = new ArrayList<String>(scheduleRequestComponent.getBuildingIds());
            this.campusIds = new ArrayList<String>(scheduleRequestComponent.getCampusIds());
            this.orgIds = new ArrayList<String>(scheduleRequestComponent.getOrgIds());
            this.resourceTypeKeys = scheduleRequestComponent.getResourceTypeKeys();
            this.roomIds = scheduleRequestComponent.getRoomIds();
            this.timeSlotIds = new ArrayList<String>(scheduleRequestComponent.getTimeSlotIds());
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public List<String> getBuildingIds() {
        if (null == this.buildingIds) {
            return new ArrayList<String>();
        }
        else {
            return this.buildingIds;
        }
    }

    public void setBuildingIds(List<String> buildingIds) {
        this.buildingIds = buildingIds;
    }

    @Override
    public List<String> getCampusIds() {
        if (null == this.campusIds) {
            return new ArrayList<String>();
        }
        else {
            return this.campusIds;
        }
    }

    public void setCampusIds(List<String> campusIds) {
        this.campusIds = campusIds;
    }

    @Override
    public List<String> getOrgIds() {
        if (null == this.orgIds) {
            return new ArrayList<String>();
        }
        else {
            return this.orgIds;
        }
    }

    public void setOrgIds(List<String> orgIds) {
        this.orgIds = orgIds;
    }

    @Override
    public List<String> getResourceTypeKeys() {
        if (null == this.resourceTypeKeys) {
            return new ArrayList<String>();
        }
        else {
            return this.resourceTypeKeys;
        }
    }

    public void setResourceTypeKeys(List<String> resourceTypeKeys) {
        this.resourceTypeKeys = resourceTypeKeys;
    }

    @Override
    public List<String> getRoomIds() {
        if (null == this.roomIds) {
            return new ArrayList<String>();
        }
        else {
            return this.roomIds;
        }
    }

    public void setRoomIds(List<String> roomIds) {
        this.roomIds = roomIds;
    }

    @Override
    public List<String> getTimeSlotIds() {
        if (null == this.timeSlotIds) {
            return new ArrayList<String>();
        }
        else {
            return timeSlotIds;
        }
    }

    public void setTimeSlotIds(List<String> timeSlotIds) {
        this.timeSlotIds = timeSlotIds;
    }
}
