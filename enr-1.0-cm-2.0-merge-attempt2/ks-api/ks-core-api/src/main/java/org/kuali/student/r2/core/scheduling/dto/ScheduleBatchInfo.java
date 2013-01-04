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
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleBatch;
//import org.w3c.dom.Element;

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
@XmlType(name = "ScheduleBatchInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr",
        "requestingPersonId", "orgId", "statusMessage",
        "meta", "attributes"})//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code
public class ScheduleBatchInfo extends IdEntityInfo implements ScheduleBatch, Serializable {

    @XmlElement
    private String requestingPersonId;
    @XmlElement
    private String orgId;
    @XmlElement
    private String statusMessage;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    public ScheduleBatchInfo() {
    }

    public ScheduleBatchInfo(ScheduleBatch scheduleBatch) {
        super(scheduleBatch);
        if (null != scheduleBatch) {
            this.requestingPersonId = scheduleBatch.getRequestingPersonId();
            this.orgId = scheduleBatch.getOrgId();
            this.statusMessage = scheduleBatch.getStatusMessage();
        }
    }

    @Override
    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public String getRequestingPersonId() {
        return this.requestingPersonId;
    }

    public void setRequestingPersonId(String requestingPersonId) {
        this.requestingPersonId = requestingPersonId;
    }

    @Override
    public String getStatusMessage() {
        return this.statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public String toString() {
        return "ScheduleBatchInfo{" +
                "requestingPersonId='" + requestingPersonId + '\'' +
                ", Id='" + getId() + '\'' +
                ", orgId='" + orgId + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                '}';
    }
}
