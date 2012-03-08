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

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.infc.Status;
import org.kuali.student.r2.core.scheduling.infc.ScheduleResponse;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleResponseInfo", propOrder = {"id", "typeKey", "stateKey",
        "batchResponseId", "scheduleRequestId", "scheduleId", "statusMessage",
        "meta", "attributes", "_futureElements"})
public class ScheduleResponseInfo extends IdNamelessEntityInfo implements ScheduleResponse, Serializable {

    @XmlElement
    private String batchResponseId;
    @XmlElement
    private String scheduleRequestId;
    @XmlElement
    private String scheduleId;
    @XmlElement
    private String statusMessage;
    @XmlAnyElement
    private List<Element> _futureElements;

    public ScheduleResponseInfo() {
    }

    public ScheduleResponseInfo(ScheduleResponse scheduleResponse) {
        if (null != scheduleResponse) {
            this.batchResponseId = scheduleResponse.getBatchResponseId();
            this.scheduleRequestId = scheduleResponse.getScheduleRequestId();
            this.scheduleId = scheduleResponse.getScheduleId();
            this.statusMessage = scheduleResponse.getStatusMessage();
        }
    }

    @Override
    public String getBatchResponseId() {
        return this.batchResponseId;
    }

    public void setBatchResponseId(String batchResponseId) {
        this.batchResponseId = batchResponseId;
    }

    @Override
    public String getScheduleRequestId() {
        return scheduleRequestId;
    }

    public void setScheduleRequestId(String scheduleRequestId) {
        this.scheduleRequestId = scheduleRequestId;
    }

    @Override
    public String getScheduleId() {
        return this.scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Override
    public String getStatusMessage() {
        return this.statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
