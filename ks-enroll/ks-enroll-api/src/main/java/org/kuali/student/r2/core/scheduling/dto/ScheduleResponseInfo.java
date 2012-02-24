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
import org.kuali.student.r2.core.scheduling.infc.ScheduleResponse;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleResponseInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr",
        "scheduleRequestId",
        "meta", "attributes", "_futureElements"})
public class ScheduleResponseInfo extends IdEntityInfo implements ScheduleResponse{

    @XmlElement
    private String scheduleRequestId;
    @XmlAnyElement
    private List<Element> _futureElements;

    public ScheduleResponseInfo() {
    }

    public ScheduleResponseInfo(ScheduleResponse scheduleResponse) {
        if (null != scheduleResponse) {
            this.scheduleRequestId = scheduleResponse.getScheduleRequestId();
        }
    }

    @Override
    public String getScheduleRequestId() {
        return scheduleRequestId;
    }

    public void setScheduleRequestId(String scheduleRequestId) {
        this.scheduleRequestId = scheduleRequestId;
    }
}
