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
import org.kuali.student.r2.core.room.infc.RoomFixedResource;
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
 * Refer to interface javadoc
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoomInfo", propOrder = {"id", "typeKey", "stateKey",
        "name", "descr", "floorKey", "roomFixedResources", "roomUsages", "accessibilityTypeKeys",
        "meta", "attributes", "_futureElements"})
public class RoomInfo extends IdEntityInfo implements Room, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String floorKey;
    @XmlElement
    List<RoomFixedResource> roomFixedResources;
    @XmlElement
    List<RoomUsageInfo> roomUsages;
    @XmlElement
    List<String> accessibilityTypeKeys;
    @XmlAnyElement
    private List<Element> _futureElements;

    public RoomInfo() {

    }

    public RoomInfo(Room room) {
        super(room);
        if (null != room) {
            this.floorKey = room.getFloorKey();
            if (null != room.getRoomFixedResources()) {
                this.roomFixedResources = new ArrayList<RoomFixedResource>(room.getRoomFixedResources());
            }
            if (null != room.getRoomUsages()) {
                this.roomUsages = new ArrayList<RoomUsageInfo>(room.getRoomUsages());
            }
            if (null != room.getAccessibilityTypeKeys()) {
                this.accessibilityTypeKeys = new ArrayList<String>(room.getAccessibilityTypeKeys());
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
    public List<RoomFixedResource> getRoomFixedResources() {
        return this.roomFixedResources;
    }

    public void setRoomFixedResources(List<RoomFixedResource> roomFixedResources) {
        this.roomFixedResources = roomFixedResources;
    }

    @Override
    public List<RoomUsageInfo> getRoomUsages() {
        return this.roomUsages;
    }

    public void setRoomUsages(List<RoomUsageInfo> roomUsages) {
        this.roomUsages = roomUsages;
    }

    @Override
    public List<String> getAccessibilityTypeKeys() {
        return this.accessibilityTypeKeys;
    }

    public void setAccessibilityTypeKeys(List<String> accessibilityTypeKeys) {
        this.accessibilityTypeKeys = accessibilityTypeKeys;
    }

}
