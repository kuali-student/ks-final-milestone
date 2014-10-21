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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.object.KSObjectUtils;
import org.kuali.student.r2.core.scheduling.infc.ScheduleRequestComponent;

//import org.w3c.dom.Element;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleRequestComponentInfo", propOrder = {"id", "buildingIds", "campusIds", "orgIds", "resourceTypeKeys", "roomIds",
        "timeSlotIds", "partitionIds", "isTBA", "roomFeatureTypeKeys", "roomTypeKeys", "capacity", "ignoreConflicts" , "_futureElements" })
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
    private List<String> partitionIds;
    @XmlElement
    private Boolean isTBA;
    @XmlElement
    private List<String> roomFeatureTypeKeys;
    @XmlElement
    private List<String> roomTypeKeys;
    @XmlElement
    private Integer capacity;
    @XmlElement
    private Boolean ignoreConflicts;

    @XmlAnyElement
    private List<Object> _futureElements;  

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
            this.partitionIds = new ArrayList<String>(scheduleRequestComponent.getPartitionIds());
            this.isTBA = scheduleRequestComponent.getIsTBA();
            this.roomFeatureTypeKeys = scheduleRequestComponent.getRoomFeatureTypeKeys();
            this.roomTypeKeys = scheduleRequestComponent.getRoomTypeKeys();
            this.capacity = scheduleRequestComponent.getCapacity();
            this.ignoreConflicts = scheduleRequestComponent.getIgnoreConflicts();
        }
    }

    /*
     * The given object is equal to this ScheduleRequestObject only if given object is also
     * a ScheduleRequestComponentInfo
     */
    public boolean equals (Object obj) {
        ScheduleRequestComponentInfo srci = (ScheduleRequestComponentInfo) obj; // will throw ClassCastException
        
        if (!this.id.equals(srci.getId())) return false;
        
        if (!KSCollectionUtils.areCollectionContentsEqual(buildingIds, srci.buildingIds)) return false;

        if (!KSCollectionUtils.areCollectionContentsEqual(campusIds, srci.campusIds)) return false;
        
        if (!KSCollectionUtils.areCollectionContentsEqual(orgIds, srci.orgIds)) return false;
        
        if (!KSCollectionUtils.areCollectionContentsEqual(resourceTypeKeys, srci.resourceTypeKeys)) return false;
        
        if (!KSCollectionUtils.areCollectionContentsEqual(roomIds, srci.roomIds)) return false;
        
        if (!KSCollectionUtils.areCollectionContentsEqual(timeSlotIds, srci.timeSlotIds)) return false;
        
        if (!KSCollectionUtils.areCollectionContentsEqual(partitionIds, srci.partitionIds)) return false;
        
        if (!KSObjectUtils.nullSafeBooleanEquals(this.isTBA, srci.getIsTBA())) return false;
        
        if (!KSCollectionUtils.areCollectionContentsEqual(roomFeatureTypeKeys, srci.roomFeatureTypeKeys)) return false;
        
        if (!KSCollectionUtils.areCollectionContentsEqual(roomTypeKeys, srci.roomTypeKeys)) return false;
        
        if (!KSObjectUtils.nullSafeIntegerEquals(this.capacity, srci.getCapacity())) return false;
        
        if (!KSObjectUtils.nullSafeBooleanEquals(this.ignoreConflicts, srci.getIgnoreConflicts())) return false;
        
        // at this point all of the checks have passed.
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (buildingIds != null ? buildingIds.hashCode() : 0);
        result = 31 * result + (campusIds != null ? campusIds.hashCode() : 0);
        result = 31 * result + (orgIds != null ? orgIds.hashCode() : 0);
        result = 31 * result + (resourceTypeKeys != null ? resourceTypeKeys.hashCode() : 0);
        result = 31 * result + (roomIds != null ? roomIds.hashCode() : 0);
        result = 31 * result + (timeSlotIds != null ? timeSlotIds.hashCode() : 0);
        result = 31 * result + (partitionIds != null ? partitionIds.hashCode() : 0);
        result = 31 * result + (isTBA != null ? isTBA.hashCode() : 0);
        result = 31 * result + (roomFeatureTypeKeys != null ? roomFeatureTypeKeys.hashCode() : 0);
        result = 31 * result + (roomTypeKeys != null ? roomTypeKeys.hashCode() : 0);
        result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
        result = 31 * result + (ignoreConflicts != null ? ignoreConflicts.hashCode() : 0);
        return result;
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
    public List<String> getPartitionIds() {
        if (null == this.partitionIds) {
            this.partitionIds = new ArrayList<String>();
        }
        return this.partitionIds;
    }

    public void setPartitionIds(List<String> partitionIds) {
        this.partitionIds = partitionIds;
    }

    @Override
    public Boolean getIsTBA() {
        return isTBA;
    }

    public void setIsTBA(Boolean isTBA) {
        this.isTBA = isTBA;
    }

    @Override
    public List<String> getRoomFeatureTypeKeys() {
        return roomFeatureTypeKeys;
    }

    @Override
    public List<String> getRoomTypeKeys() {
        return roomTypeKeys;
    }

    @Override
    public Integer getCapacity() {
        return capacity;
    }

    @Override
    public Boolean getIgnoreConflicts() {
        return ignoreConflicts;
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
                ", partitionIds=" + partitionIds +
                ", isTBA=" + isTBA +
                ", roomFeatureTypeKeys=" + roomFeatureTypeKeys +
                ", roomTypeKeys=" + roomTypeKeys +
                ", capacity=" + capacity +
                ", ignoreConflicts=" + ignoreConflicts +
                '}';
    }
}
