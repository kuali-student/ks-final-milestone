/*
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.r2.common.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.MeetingSchedule;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeetingScheduleInfo", propOrder = {"spaceCode", "time"})
public class MeetingScheduleInfo  implements MeetingSchedule, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String spaceCode;

    @XmlElement
    private String time;

    public MeetingScheduleInfo() {
        super();
        spaceCode = null;
        time = null;
    }

    public MeetingScheduleInfo(MeetingSchedule entity) {
        if (null != entity) {
            this.spaceCode = entity.getSpaceCode();
            this.time = entity.getTime();
        }
    }

    @Override
    public String getSpaceCode() {
        return spaceCode;
    }

    @Override
    public String getTime() {
        return time;
    }

    public void setSpaceCode(String spaceCode) {
        this.spaceCode = spaceCode;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
