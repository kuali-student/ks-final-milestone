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

import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.core.scheduling.infc.MeetingTime;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeetingTimeInfo", propOrder = {"id", "startDate", "duration", "roomId", "_futureElements"})
public class MeetingTimeInfo implements MeetingTime, Serializable {

    @XmlElement
    private String id;
    @XmlElement
    private Date startDate;
    @XmlElement
    private TimeAmountInfo duration;
    @XmlElement
    private String roomId;
    @XmlAnyElement
    private List<Element> _futureElements;


    public MeetingTimeInfo() {
    }
    
    public MeetingTimeInfo(MeetingTime meetingTime) {
        if (null != meetingTime) {
            this.id = meetingTime.getId();
            this.startDate = (null != meetingTime.getStartDate()) ? new Date(meetingTime.getStartDate().getTime()) : null;
            this.duration = (null != meetingTime.getDuration()) ? new TimeAmountInfo(meetingTime.getDuration()) : null;
            this.roomId = meetingTime.getRoomId();
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
    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public TimeAmount getDuration() {
        return this.duration;
    }

    public void setDuration(TimeAmountInfo duration) {
        this.duration = duration;
    }

    @Override
    public String getRoomId() {
        return this.roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
