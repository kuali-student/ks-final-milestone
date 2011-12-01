/*
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.MeetingSchedule;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeetingScheduleInfo", propOrder = {"id", "spaceId", "timePeriods"})
public class MeetingScheduleInfo implements MeetingSchedule, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String id;
    @XmlElement
    private String spaceId;

    @XmlElement
    private String timePeriods;

    public MeetingScheduleInfo() {
        super();
        spaceId = null;
        timePeriods = null;
        id = null;
    }

    public MeetingScheduleInfo(MeetingSchedule entity) {
        if (null != entity) {
            this.spaceId = entity.getSpaceId();
            this.timePeriods = entity.getTimePeriods();
            this.id = entity.getId();
        }
    }

    @Override
    public String getSpaceId() {
        return spaceId;
    }

    @Override
    public String getTimePeriods() {
        return timePeriods;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public void setTimePeriods(String timePeriods) {
        this.timePeriods = timePeriods;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
