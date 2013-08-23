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
 * Created by mahtabme on 8/28/12
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

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleComponentDisplay;
import org.kuali.student.r2.core.scheduling.infc.ScheduleDisplay;

/**
 * This class represents a reusable display object in the Scheduling Service for Schedules.
 *
 * @Version 2.0
 * @Author Mezba Mahtab
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleDisplayInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr",
        "atp", "scheduleComponentDisplays",
        "meta", "attributes", "_futureElements" }) 
public class ScheduleDisplayInfo extends IdEntityInfo implements ScheduleDisplay, Serializable {

    ////////////////////////
    // DATA VARIABLES
    ////////////////////////

    @XmlElement
    private AtpInfo atp;

    @XmlElement
    private List<ScheduleComponentDisplayInfo> scheduleComponentDisplays;

    @XmlAnyElement
    private List<Object> _futureElements;  

    ////////////////////////
    // CONSTRUCTORS
    ////////////////////////

    public ScheduleDisplayInfo() {
    }

    public ScheduleDisplayInfo(ScheduleDisplay scheduleDisplay) {
        super (scheduleDisplay);
        if (null != scheduleDisplay) {
            this.atp= new AtpInfo(scheduleDisplay.getAtp());
            this.scheduleComponentDisplays = new ArrayList<ScheduleComponentDisplayInfo>();
            for (ScheduleComponentDisplay scheduleComponentDisplay : scheduleDisplay.getScheduleComponentDisplays()) {
                this.scheduleComponentDisplays.add(new ScheduleComponentDisplayInfo(scheduleComponentDisplay));
            }
        }
    }

    ////////////////////////////////////
    // ACCESSORS AND MODIFIERS
    ////////////////////////////////////

    @Override
    public AtpInfo getAtp() {
        return atp;
    }

    public void setAtp(AtpInfo atp) {
        this.atp = atp;
    }

    @Override
    public List<? extends ScheduleComponentDisplay> getScheduleComponentDisplays() {
        if (this.scheduleComponentDisplays == null) {
            return new ArrayList<ScheduleComponentDisplayInfo>();
        } else {
            return scheduleComponentDisplays;
        }
    }

    public void setScheduleComponentDisplays(List<ScheduleComponentDisplayInfo> scheduleComponentDisplays) {
        this.scheduleComponentDisplays = scheduleComponentDisplays;
    }
}
