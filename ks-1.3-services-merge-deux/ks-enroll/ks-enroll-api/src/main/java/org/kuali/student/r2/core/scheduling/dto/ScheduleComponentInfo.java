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

import org.kuali.student.r2.core.scheduling.infc.ScheduleComponent;
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
@XmlType(name = "ScheduleComponentInfo", propOrder = {"id", "roomId", "timeSlotIds", "_futureElements"})
public class ScheduleComponentInfo implements ScheduleComponent, Serializable {

    @XmlElement
    private String id;
    @XmlElement
    private String roomId;
    @XmlElement
    private List<String> timeSlotIds;
    @XmlAnyElement
    private List<Element> _futureElements;

    public ScheduleComponentInfo() {
    }

    public ScheduleComponentInfo(ScheduleComponent scheduleComponent) {
        if (null != scheduleComponent) {
            this.id = scheduleComponent.getId();
            this.roomId = scheduleComponent.getRoomId();
            this.timeSlotIds = new ArrayList<String>(scheduleComponent.getTimeSlotIds());
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public List<String> getTimeSlotIds() {
        if (null == timeSlotIds) {
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
