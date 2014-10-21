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

import org.kuali.student.r2.core.scheduling.infc.ScheduleComponent;

//import org.w3c.dom.Element;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleComponentInfo", propOrder = {"id", "roomId", "timeSlotIds", "isTBA" , "_futureElements" }) 
public class ScheduleComponentInfo implements ScheduleComponent, Serializable {

    @XmlElement
    private String id;
    @XmlElement
    private String roomId;
    @XmlElement
    private List<String> timeSlotIds;
    @XmlAnyElement
    private List<Object> _futureElements;  

    @XmlElement
    private Boolean isTBA;

    public ScheduleComponentInfo() {}

    public ScheduleComponentInfo(ScheduleComponent scheduleComponent) {
        if (null != scheduleComponent) {
            this.id = scheduleComponent.getId();
            this.roomId = scheduleComponent.getRoomId();
            this.timeSlotIds = new ArrayList<String>();
            for (String timeSlotId: scheduleComponent.getTimeSlotIds()) {
                this.timeSlotIds.add(timeSlotId);
            }
            this.isTBA = scheduleComponent.getIsTBA();
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

    /*
     * Compares given object for equality to this ScheduleComponentInfo. The
     * given object will be equal only if it is also a ScheduleComponentInfo and its
     * id and room id are equal to this object's id and room id, and the time slot ids
     * are same in the same order.
     */
    public boolean equals (Object e) {
        ScheduleComponentInfo sci = (ScheduleComponentInfo) e; // throws ClassCastException if not the case
        if ((this.id==null && sci.id!=null) || (this.id!=null && sci.id==null)) return false;
        if ((this.roomId==null && sci.roomId!=null) || (this.roomId!=null && sci.roomId==null)) return false;
        if ((this.timeSlotIds==null && sci.timeSlotIds!=null) || (this.timeSlotIds!=null && sci.timeSlotIds==null)) return false;
        if (this.id.equals(sci.getId()) && this.roomId.equals(sci.getRoomId())) {
            // compare time slot ids
            if (this.timeSlotIds.size() != sci.getTimeSlotIds().size()) return false;
            for (int i=0; i<this.getTimeSlotIds().size(); i++) {
                if (!(this.timeSlotIds.get(i).equals(sci.getTimeSlotIds().get(i)))) {
                    return false;
                }
            }
            if (this.isTBA==null && sci.getIsTBA()==null) return true;
            if (this.isTBA.equals(sci.getIsTBA())) { return true; }
            return false;
        } else {
            return false;
        }

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (roomId != null ? roomId.hashCode() : 0);
        result = 31 * result + (timeSlotIds != null ? timeSlotIds.hashCode() : 0);
        result = 31 * result + (isTBA != null ? isTBA.hashCode() : 0);
        return result;
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
        return "ScheduleComponentInfo{" +
                "id='" + id + '\'' +
                ", roomId='" + roomId + '\'' +
                ", timeSlotIds=" + timeSlotIds +
                '}';
    }
}
