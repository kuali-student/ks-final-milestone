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

import org.kuali.student.r2.core.scheduling.infc.ScheduleRequestItem;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleRequestItemInfo", propOrder = {"id", "buildingIds", "campusIds", "isRequirement", "orgIds", "referenceId", "referenceTypeKey", "resourceTypeKeys", "roomIds", "scheduleRequestId", "timeSlotIds", "_futureElements"})
public class ScheduleRequestItemInfo implements ScheduleRequestItem {

    @XmlElement
    private String id;
    @XmlElement
    private List<String> buildingIds;
    @XmlElement
    private List<String> campusIds;
    @XmlElement
    private Boolean isRequirement;
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

    public ScheduleRequestItemInfo() {
    }

    public ScheduleRequestItemInfo(ScheduleRequestItem scheduleRequestItem) {
        if (null != scheduleRequestItem) {
            this.id = scheduleRequestItem.getId();
            this.buildingIds = new ArrayList<String>(scheduleRequestItem.getBuildingIds());
            this.campusIds = new ArrayList<String>(scheduleRequestItem.getCampusIds());
            this.isRequirement  = scheduleRequestItem.getIsRequirement();
            this.orgIds = new ArrayList<String>(scheduleRequestItem.getOrgIds());
            this.referenceId = scheduleRequestItem.getReferenceId();
            this.referenceTypeKey = scheduleRequestItem.getReferenceTypeKey();
            this.resourceTypeKeys = scheduleRequestItem.getResourceTypeKeys();
            this.roomIds = scheduleRequestItem.getRoomIds();
            this.scheduleRequestId = scheduleRequestItem.getScheduleRequestId();
            this.timeSlotIds = new ArrayList<String>(scheduleRequestItem.getTimeSlotIds());
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
    public Boolean getIsRequirement() {
        return isRequirement;
    }

    public void setIsRequirement(Boolean requirement) {
        isRequirement = requirement;
    }

    @Override
    public List<String> getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(List<String> orgIds) {
        this.orgIds = orgIds;
    }

    @Override
    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    @Override
    public String getReferenceTypeKey() {
        return referenceTypeKey;
    }

    public void setReferenceTypeKey(String referenceTypeKey) {
        this.referenceTypeKey = referenceTypeKey;
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
    public String getScheduleRequestId() {
        return scheduleRequestId;
    }

    public void setScheduleRequestId(String scheduleRequestId) {
        this.scheduleRequestId = scheduleRequestId;
    }

    @Override
    public List<String> getTimeSlotIds() {
        return timeSlotIds;
    }

    public void setTimeSlotIds(List<String> timeSlotIds) {
        this.timeSlotIds = timeSlotIds;
    }
}
