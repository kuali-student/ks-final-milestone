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
import org.kuali.student.r2.core.scheduling.infc.ScheduleResp;
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
@XmlType(name = "ScheduleRespInfo", propOrder = {"id", "typeKey", "stateKey",
        "batchRespId", "scheduleRequestId", "scheduleId", "statusMessage",
        "meta", "attributes", "_futureElements"})
public class ScheduleRespInfo extends IdNamelessEntityInfo implements ScheduleResp, Serializable {

    @XmlElement
    private String batchRespId;
    @XmlElement
    private String scheduleRequestId;
    @XmlElement
    private String scheduleId;
    @XmlElement
    private String statusMessage;
    @XmlAnyElement
    private List<Element> _futureElements;

    public ScheduleRespInfo() {
    }

    public ScheduleRespInfo(ScheduleResp scheduleResp) {
        super (scheduleResp);
        if (null != scheduleResp) {
            this.batchRespId = scheduleResp.getBatchRespId();
            this.scheduleRequestId = scheduleResp.getScheduleRequestId();
            this.scheduleId = scheduleResp.getScheduleId();
            this.statusMessage = scheduleResp.getStatusMessage();
        }
    }

    @Override
    public String getBatchRespId() {
        return this.batchRespId;
    }

    public void setBatchRespId(String batchRespId) {
        this.batchRespId = batchRespId;
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
