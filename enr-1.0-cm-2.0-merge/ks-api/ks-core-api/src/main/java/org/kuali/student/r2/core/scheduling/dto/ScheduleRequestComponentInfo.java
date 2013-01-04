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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//import org.w3c.dom.Element;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleRequestComponentInfo", propOrder = {"id", "buildingIds", "campusIds", "orgIds", "resourceTypeKeys", "roomIds", "timeSlotIds", "isTBA" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code
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
    @XmlElement
    private Boolean isTBA;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

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
            this.isTBA = scheduleRequestComponent.getIsTBA();
        }
    }

    /*
     * The given object is equal to this ScheduleRequestObject only if given object is also
     * a ScheduleRequestComponentInfo
     */
    public boolean equals (Object obj) {
        ScheduleRequestComponentInfo srci = (ScheduleRequestComponentInfo) obj; // will throw ClassCastException
        if (!this.id.equals(srci.getId())) return false;
        if (this.buildingIds.size()!=srci.buildingIds.size()) return false;
        for (int i=0; i<this.buildingIds.size(); i++) { if (!this.buildingIds.get(i).equals(srci.buildingIds.get(i))) { return false; }}
        if (this.campusIds.size()!=srci.campusIds.size()) return false;
        for (int i=0; i<this.campusIds.size(); i++) { if (!this.campusIds.get(i).equals(srci.campusIds.get(i))) { return false; }}
        if (this.orgIds.size()!=srci.orgIds.size()) return false;
        for (int i=0; i<this.orgIds.size(); i++) { if (!this.orgIds.get(i).equals(srci.orgIds.get(i))) { return false; }}
        if (this.resourceTypeKeys.size()!=srci.resourceTypeKeys.size()) return false;
        for (int i=0; i<this.resourceTypeKeys.size(); i++) { if (!this.resourceTypeKeys.get(i).equals(srci.resourceTypeKeys.get(i))) { return false; }}
        if (this.roomIds.size()!=srci.roomIds.size()) return false;
        for (int i=0; i<this.roomIds.size(); i++) { if (!this.roomIds.get(i).equals(srci.roomIds.get(i))) { return false; }}
        if (this.timeSlotIds.size()!=srci.timeSlotIds.size()) return false;
        for (int i=0; i<this.timeSlotIds.size(); i++) { if (!this.timeSlotIds.get(i).equals(srci.timeSlotIds.get(i))) { return false; }}
        if (this.isTBA==null && srci.getIsTBA()==null) return true;
        if (this.isTBA.equals(srci.getIsTBA())) { return true; }
        return false;
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
            this.buildingIds = new ArrayList<String>();
        }
        return this.buildingIds;
    }

    public void setBuildingIds(List<String> buildingIds) {
        this.buildingIds = buildingIds;
    }

    @Override
    public List<String> getCampusIds() {
        if (null == this.campusIds) {
            this.campusIds = new ArrayList<String>();
        }
        return this.campusIds;
    }

    public void setCampusIds(List<String> campusIds) {
        this.campusIds = campusIds;
    }

    @Override
    public List<String> getOrgIds() {
        if (null == this.orgIds) {
            this.orgIds = new ArrayList<String>();
        }
        return this.orgIds;
    }

    public void setOrgIds(List<String> orgIds) {
        this.orgIds = orgIds;
    }

    @Override
    public List<String> getResourceTypeKeys() {
        if (null == this.resourceTypeKeys) {
            this.resourceTypeKeys = new ArrayList<String>();
        }
        return this.resourceTypeKeys;
    }

    public void setResourceTypeKeys(List<String> resourceTypeKeys) {
        this.resourceTypeKeys = resourceTypeKeys;
    }

    @Override
    public List<String> getRoomIds() {
        if (null == this.roomIds) {
            this.roomIds = new ArrayList<String>();
        }
        return this.roomIds;
    }

    public void setRoomIds(List<String> roomIds) {
        this.roomIds = roomIds;
    }

    @Override
    public List<String> getTimeSlotIds() {
        if (null == this.timeSlotIds) {
            this.timeSlotIds = new ArrayList<String>();
        }
        return timeSlotIds;
    }

    public void setTimeSlotIds(List<String> timeSlotIds) {
        this.timeSlotIds = timeSlotIds;
    }

    @Override
    public Boolean getIsTBA() {
        return isTBA;
    }

    public void setIsTBA(Boolean isTBA) {
        this.isTBA = isTBA;
    }

    @Override
    public String toString() {
        return "ScheduleRequestComponentInfo{" +
                "id='" + id + '\'' +
                ", buildingIds=" + buildingIds +
                ", campusIds=" + campusIds +
                ", orgIds=" + orgIds +
                ", resourceTypeKeys=" + resourceTypeKeys +
                ", roomIds=" + roomIds +
                ", timeSlotIds=" + timeSlotIds +
                ", isTBA=" + isTBA +
                '}';
    }
}
