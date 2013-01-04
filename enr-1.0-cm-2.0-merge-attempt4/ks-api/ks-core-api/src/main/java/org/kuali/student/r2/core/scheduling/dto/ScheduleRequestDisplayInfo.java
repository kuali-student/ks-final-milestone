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

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleDisplay;
import org.kuali.student.r2.core.scheduling.infc.ScheduleRequestDisplay;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a reusable display object in the Scheduling Service for Schedule Requests.
 *
 * @Version 2.0
 * @Author Mezba Mahtab
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleRequestDisplayInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr",
        "refObjectId", "refObjectTypeKey", "scheduleRequestComponentDisplays",
        "meta", "attributes"})//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code
public class ScheduleRequestDisplayInfo extends IdEntityInfo implements ScheduleRequestDisplay, Serializable {

    ////////////////////////
    // DATA VARIABLES
    ////////////////////////

    @XmlElement
    private String refObjectId;

    @XmlElement
    private String refObjectTypeKey;

    @XmlElement
    private List<ScheduleRequestComponentDisplayInfo> scheduleRequestComponentDisplays;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    ////////////////////////
    // CONSTRUCTORS
    ////////////////////////

    public ScheduleRequestDisplayInfo() {
    }

    public ScheduleRequestDisplayInfo(ScheduleRequestDisplay scheduleRequestDisplay) {
        super (scheduleRequestDisplay);
        if (null != scheduleRequestDisplay) {
            this.refObjectId= scheduleRequestDisplay.getRefObjectId();
            this.refObjectTypeKey= scheduleRequestDisplay.getRefObjectTypeKey();
            this.scheduleRequestComponentDisplays= new ArrayList<ScheduleRequestComponentDisplayInfo>();
            for (ScheduleRequestComponentDisplayInfo scheduleRequestComponentDisplayInfo: scheduleRequestDisplay.getScheduleRequestComponentDisplays()) {
                this.scheduleRequestComponentDisplays.add(new ScheduleRequestComponentDisplayInfo(scheduleRequestComponentDisplayInfo));
            }
        }
    }

    ////////////////////////////////////
    // ACCESSORS AND MODIFIERS
    ////////////////////////////////////

    @Override
    public String getRefObjectId() {
        return refObjectId;
    }

    public void setRefObjectId(String refObjectId) {
        this.refObjectId = refObjectId;
    }

    @Override
    public String getRefObjectTypeKey() {
        return refObjectTypeKey;
    }

    public void setRefObjectTypeKey(String refObjectTypeKey) {
        this.refObjectTypeKey = refObjectTypeKey;
    }

    @Override
    public List<ScheduleRequestComponentDisplayInfo> getScheduleRequestComponentDisplays() {
        return scheduleRequestComponentDisplays;
    }

    public void setScheduleRequestComponentDisplays(List<ScheduleRequestComponentDisplayInfo> scheduleRequestComponentDisplays) {
        this.scheduleRequestComponentDisplays = scheduleRequestComponentDisplays;
    }
}
