/*
 * Copyright 2011 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.MeetingSchedule;
//import org.w3c.dom.Element;

/**
 * Information about a MeetingSchedule. This will move into the
 * Scheduling service.
 *
 * @author kamal
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeetingScheduleInfo", propOrder = {
                "id", "spaceId", "scheduleId" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code

public class MeetingScheduleInfo 
    implements MeetingSchedule, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String id;

    @XmlElement
    private String spaceId;

    @XmlElement
    private String scheduleId;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;    


    /**
     * Constructs a new MeetingScheduleInfo.
     */
    public MeetingScheduleInfo() {
    }

    /**
     * Constructs a new MeetingScheduleInfo from another
     * MeetingSchedule.
     *
     * @param meetingSchedule the MeetingSchedule to copy
     */
    public MeetingScheduleInfo(MeetingSchedule meetingSchedule) {
        if (null != meetingSchedule) {
            this.id = meetingSchedule.getId();
            this.spaceId = meetingSchedule.getSpaceId();
            this.scheduleId = meetingSchedule.getScheduleId();
        }
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    @Override
    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }
}
