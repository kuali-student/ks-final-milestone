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
import org.kuali.student.r2.core.scheduling.infc.ScheduleBatchResp;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleBatchRespInfo", propOrder = {"id", "typeKey", "stateKey",
        "submittedDate", "statusMessage", "finalStatus",
        "meta", "attributes", "_futureElements"})
public class ScheduleBatchRespInfo extends IdNamelessEntityInfo implements ScheduleBatchResp, Serializable {
    
    @XmlElement 
    private Date submittedDate;
    @XmlElement
    private String statusMessage;
    @XmlElement
    private StatusInfo finalStatus;
    @XmlAnyElement
    private List<Element> _futureElements;

    public ScheduleBatchRespInfo() {

    }

    public ScheduleBatchRespInfo(ScheduleBatchResp scheduleBatchResp) {
        super (scheduleBatchResp);
        if (null != scheduleBatchResp) {
            this.submittedDate = scheduleBatchResp.getSubmittedDate();
            this.statusMessage = scheduleBatchResp.getStatusMessage();
            this.finalStatus = new StatusInfo(scheduleBatchResp.getFinalStatus());
        }
    }
    
    @Override
    public Date getSubmittedDate() {
        return this.submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    @Override
    public String getStatusMessage() {
        return this.statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public StatusInfo getFinalStatus() {
        return this.finalStatus;
    }

    public void setFinalStatus(StatusInfo finalStatus) {
        this.finalStatus = finalStatus;
    }

}
