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

import org.kuali.student.r2.core.scheduling.infc.ScheduleRequestComponents;
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
@XmlType(name = "ScheduleRequestComponentsInfo", propOrder = {"id", "buildingIds", "campusIds", "orgIds", "referenceId", "referenceTypeKey", "resourceTypeKeys", "roomIds", "scheduleRequestId", "timeSlotIds", "_futureElements"})
public class ScheduleRequestComponentsInfo implements ScheduleRequestComponents, Serializable {

    @XmlElement
    private String id;
    @XmlElement
    private List<String> buildingIds;
    @XmlElement
    private List<String> campusIds;
    @XmlElement
    private List<String> orgIds;
    @XmlElement
    private String referenceId;
    @XmlElement
    private String referenceTypeKey;
    @XmlElement
    private List<String> resourceTypeKeys;
    @XmlElement
    private List<String> roomIds;
    @XmlElement
    private String scheduleRequestId;
    @XmlElement
    private List<String> timeSlotIds;
    @XmlAnyElement
    private List<Element> _futureElements;

    public ScheduleRequestComponentsInfo() {
    }

    public ScheduleRequestComponentsInfo(ScheduleRequestComponents scheduleRequestComponents) {
        if (null != scheduleRequestComponents) {
            this.id = scheduleRequestComponents.getId();
            this.buildingIds = new ArrayList<String>(scheduleRequestComponents.getBuildingIds());
            this.campusIds = new ArrayList<String>(scheduleRequestComponents.getCampusIds());
            this.orgIds = new ArrayList<String>(scheduleRequestComponents.getOrgIds());
            this.resourceTypeKeys = scheduleRequestComponents.getResourceTypeKeys();
            this.roomIds = scheduleRequestComponents.getRoomIds();
            this.timeSlotIds = new ArrayList<String>(scheduleRequestComponents.getTimeSlotIds());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public List<String> getBuildingIds() {
        return buildingIds;
    }

    public void setBuildingIds(List<String> buildingIds) {
        this.buildingIds = buildingIds;
    }

    @Override
    public List<String> getCampusIds() {
        return campusIds;
    }

    public void setCampusIds(List<String> campusIds) {
        this.campusIds = campusIds;
    }

    @Override
    public List<String> getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(List<String> orgIds) {
        this.orgIds = orgIds;
    }

    @Override
    public List<String> getResourceTypeKeys() {
        return resourceTypeKeys;
    }

    public void setResourceTypeKeys(List<String> resourceTypeKeys) {
        this.resourceTypeKeys = resourceTypeKeys;
    }

    @Override
    public List<String> getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(List<String> roomIds) {
        this.roomIds = roomIds;
    }

    @Override
    public List<String> getTimeSlotIds() {
        return timeSlotIds;
    }

    public void setTimeSlotIds(List<String> timeSlotIds) {
        this.timeSlotIds = timeSlotIds;
    }
}
