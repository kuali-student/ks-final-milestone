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
@XmlType(name = "ScheduleRequestInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr",
        "refObjectId", "refObjectTypeKey", "scheduleRequestComponentIds",
        "meta", "attributes", "_futureElements"})
public class ScheduleRequestInfo extends IdEntityInfo implements ScheduleRequest, Serializable {

    @XmlElement
    private String refObjectId;
    @XmlElement
    private String refObjectTypeKey;
    @XmlElement
    private List<String> scheduleRequestComponentIds;
    @XmlAnyElement
    private List<Element> _futureElements;

    public ScheduleRequestInfo() {
    }

    public ScheduleRequestInfo(ScheduleRequest scheduleRequest) {
        if (null != scheduleRequest) {
            this.refObjectId = scheduleRequest.getRefObjectId();
            this.refObjectTypeKey = scheduleRequest.getRefObjectTypeKey();
            this.scheduleRequestComponentIds = new ArrayList<String>(scheduleRequest.getScheduleRequestComponentIds());
        }
    }

    @Override
    public String getRefObjectTypeKey() {
        return refObjectTypeKey;
    }

    public void setRefObjectTypeKey(String refObjectTypeKey) {
        this.refObjectTypeKey = refObjectTypeKey;
    }

    @Override
    public String getRefObjectId() {
        return refObjectId;
    }

    public void setRefObjectId(String refObjectId) {
        this.refObjectId = refObjectId;
    }

    @Override
    public List<String> getScheduleRequestComponentIds() {
        return this.scheduleRequestComponentIds;
    }

    public void setScheduleRequestComponentIds(List<String> scheduleRequestComponentIds) {
        this.scheduleRequestComponentIds = scheduleRequestComponentIds;
    }
}
