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

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleRequest;
import org.kuali.student.r2.core.scheduling.infc.ScheduleRequestComponent;
//import org.w3c.dom.Element;

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
@XmlType(name = "ScheduleRequestInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr",
        "scheduleRequestSetId", "scheduleId", "scheduleRequestComponents",
        "meta", "attributes", "_futureElements" }) 
public class ScheduleRequestInfo extends IdEntityInfo implements ScheduleRequest, Serializable {

    @XmlElement
    private String scheduleRequestSetId;
    @XmlElement
    private String scheduleId;
    @XmlElement
    private List<ScheduleRequestComponentInfo> scheduleRequestComponents;

    @XmlAnyElement
    private List<Object> _futureElements;  

    public ScheduleRequestInfo() {
    }

    public ScheduleRequestInfo(ScheduleRequest scheduleRequest) {
        super (scheduleRequest);
        if (null != scheduleRequest) {
            this.scheduleRequestSetId = scheduleRequest.getScheduleRequestSetId();
            this.scheduleId = scheduleRequest.getScheduleId();
            this.scheduleRequestComponents = new ArrayList<ScheduleRequestComponentInfo>();
            for(ScheduleRequestComponent component : scheduleRequest.getScheduleRequestComponents()) {
                this.scheduleRequestComponents.add(new ScheduleRequestComponentInfo(component));
            }
        }
    }

    @Override
    public String getScheduleRequestSetId() {
        return this.scheduleRequestSetId;
    }

    public void setScheduleRequestSetId(String scheduleRequestSetId)  {
        this.scheduleRequestSetId = scheduleRequestSetId;
    }

    @Override
    public String getScheduleId() {
        return this.scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Override
    public List<ScheduleRequestComponentInfo> getScheduleRequestComponents() {
        if (null == this.scheduleRequestComponents) {
            this.scheduleRequestComponents = new ArrayList<ScheduleRequestComponentInfo>();
        }
        return this.scheduleRequestComponents;
    }

    public void setScheduleRequestComponents(List<ScheduleRequestComponentInfo> scheduleRequestComponents) {
        this.scheduleRequestComponents = scheduleRequestComponents;
    }
}
