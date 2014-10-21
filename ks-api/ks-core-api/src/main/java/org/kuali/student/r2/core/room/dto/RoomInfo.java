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
 * Created by Gordon on 11/01/12
 */

package org.kuali.student.r2.core.room.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.room.infc.Room;
import org.kuali.student.r2.core.room.infc.RoomFixedResource;
import org.kuali.student.r2.core.room.infc.RoomUsage;

/**
 * @version 2.0
 * @author Kuali Student Team
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoomInfo", propOrder = {"id", "typeKey", "stateKey",
        "name", "descr", "roomCode", "buildingId", "floor", "roomFixedResources", "roomUsages", "accessibilityTypeKeys",
        "meta", "attributes", "_futureElements" }) 

public class RoomInfo extends IdEntityInfo implements Room, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement
    private String roomCode;

    @XmlElement
    private String buildingId;

    @XmlElement
    private String floor;

    @XmlElement
    List<RoomFixedResourceInfo> roomFixedResources;

    @XmlElement
    List<RoomUsageInfo> roomUsages;

    @XmlElement
    List<String> accessibilityTypeKeys;

    @XmlAnyElement
    private List<Object> _futureElements;  

    public RoomInfo() {
    }

    public RoomInfo(Room room) {
        super(room);
        if (null != room) {
            this.roomCode = room.getRoomCode();
            this.buildingId = room.getBuildingId();
            this.floor = room.getFloor();
            
            this.roomFixedResources = new ArrayList<RoomFixedResourceInfo>();
            for (RoomFixedResource resource : room.getRoomFixedResources()) {
                this.roomFixedResources.add(new RoomFixedResourceInfo(resource));
            }

            this.roomUsages = new ArrayList<RoomUsageInfo>();
            for (RoomUsage usage : room.getRoomUsages()) {
                this.roomUsages.add(new RoomUsageInfo(usage));
            }

            if (null != room.getAccessibilityTypeKeys()) {
                this.accessibilityTypeKeys = new ArrayList<String>(room.getAccessibilityTypeKeys());
            }
        }
    }

    @Override
    public String getRoomCode() {
        return this.roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    @Override
    public String getBuildingId() {
        return this.buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    @Override
    public String getFloor() {
        return this.floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    @Override
    public List<RoomFixedResourceInfo> getRoomFixedResources() {
        if(roomFixedResources == null){
            roomFixedResources = new ArrayList<RoomFixedResourceInfo>();
        }
        return this.roomFixedResources;
    }

    public void setRoomFixedResources(List<RoomFixedResourceInfo> roomFixedResources) {
        this.roomFixedResources = roomFixedResources;
    }

    @Override
    public List<RoomUsageInfo> getRoomUsages() {
        if(roomUsages == null){
            roomUsages = new ArrayList<RoomUsageInfo>();
        }
        return this.roomUsages;
    }

    public void setRoomUsages(List<RoomUsageInfo> roomUsages) {
        this.roomUsages = roomUsages;
    }

    @Override
    public List<String> getAccessibilityTypeKeys() {
        if(accessibilityTypeKeys == null){
            accessibilityTypeKeys = new ArrayList<String>();
        }
        return this.accessibilityTypeKeys;
    }

    public void setAccessibilityTypeKeys(List<String> accessibilityTypeKeys) {
        this.accessibilityTypeKeys = accessibilityTypeKeys;
    }

}
