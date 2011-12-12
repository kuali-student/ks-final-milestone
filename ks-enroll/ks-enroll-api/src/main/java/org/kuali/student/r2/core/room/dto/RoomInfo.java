/*
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.core.room.dto;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.room.infc.Room;
import org.kuali.student.r2.core.room.infc.RoomResource;
import org.kuali.student.r2.core.room.infc.RoomResponsibleOrg;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Refer to interface javadoc
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoomInfo", propOrder = {"id", "typeKey", "stateKey",
        "name", "descr", "floorKey", "softCapacity", "hardCapacity",
        "examCapacity", "roomResources", "usageTypeKeys", "accessibilityTypeKeys",
        "roomResponsibleOrgs", "meta", "attributes", "_futureElements"})
public class RoomInfo extends IdEntityInfo implements Room, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String floorKey;
    @XmlElement
    private Integer softCapacity;
    @XmlElement
    private Integer hardCapacity;
    @XmlElement
    private Integer examCapacity;
    @XmlElement
    List<RoomResource> roomResources;
    @XmlElement
    List<String> usageTypeKeys;
    @XmlElement
    List<String> accessibilityTypeKeys;
    @XmlElement
    List<RoomResponsibleOrg> roomResponsibleOrgs;
    @XmlAnyElement
    private List<Element> _futureElements;

    public RoomInfo() {

    }

    public RoomInfo(Room room) {
        super(room);
        if (null != room) {
            this.floorKey = room.getFloorKey();
            this.softCapacity = room.getSoftCapacity();
            this.hardCapacity = room.getHardCapacity();
            this.examCapacity = room.getExamCapacity();
            if (null != room.getRoomResources()) {
                this.roomResources = new ArrayList<RoomResource>(room.getRoomResources());
            }
            if (null != room.getUsageTypeKeys()) {
                this.usageTypeKeys = new ArrayList<String>(room.getUsageTypeKeys());
            }
            if (null != room.getAccessibilityTypeKeys()) {
                this.accessibilityTypeKeys = new ArrayList<String>(room.getAccessibilityTypeKeys());
            }
            if (null != room.getRoomResponsibleOrgs()) {
                this.roomResponsibleOrgs = new ArrayList<RoomResponsibleOrg>(room.getRoomResponsibleOrgs());
            }
        }
    }

    @Override
    public String getFloorKey() {
        return this.floorKey;
    }

    public void setFloorKey(String floorKey) {
        this.floorKey = floorKey;
    }

    @Override
    public Integer getSoftCapacity() {
        return this.softCapacity;
    }

    public void setSoftCapacity(Integer softCapacity) {
        this.softCapacity = softCapacity;
    }

    @Override
    public Integer getHardCapacity() {
        return this.hardCapacity;
    }

    public void setHardCapacity(Integer hardCapacity) {
        this.hardCapacity = hardCapacity;
    }

    @Override
    public Integer getExamCapacity() {
        return examCapacity;
    }

    public void setExamCapacity(Integer examCapacity) {
        this.examCapacity = examCapacity;
    }

    @Override
    public List<RoomResource> getRoomResources() {
        return this.roomResources;
    }

    public void setRoomResources(List<RoomResource> roomResources) {
        this.roomResources = roomResources;
    }

    @Override
    public List<String> getUsageTypeKeys() {
        return this.usageTypeKeys;
    }

    public void setUsageTypeKeys(List<String> usageTypeKeys) {
        this.usageTypeKeys = usageTypeKeys;
    }

    @Override
    public List<String> getAccessibilityTypeKeys() {
        return this.accessibilityTypeKeys;
    }

    public void setAccessibilityTypeKeys(List<String> accessibilityTypeKeys) {
        this.accessibilityTypeKeys = accessibilityTypeKeys;
    }

    @Override
    public List<RoomResponsibleOrg> getRoomResponsibleOrgs() {
        return this.roomResponsibleOrgs;
    }

    public void setRoomResponsibleOrgs(List<RoomResponsibleOrg> roomResponsibleOrgs) {
        this.roomResponsibleOrgs = roomResponsibleOrgs;
    }

}
