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
 * Created by Mezba mahtab on 8/6/12
 */

package org.kuali.student.r2.core.scheduling.dto;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleRequest;
import org.kuali.student.r2.core.scheduling.infc.ScheduleRequestComponent;
import org.kuali.student.r2.core.scheduling.infc.ScheduleTransaction;
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
 * Represents information about a ScheduleTransactionInfo.
 *
 * @author Mezba Mahtab
 * @since Thu Aug 6 11:11:11 EDT 2012
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleTransactionInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr",
        "scheduleBatchId", "refObjectId", "refObjectTypeKey", "scheduleRequestComponents",
        "scheduleId", "statusMessage",
        "meta", "attributes", "_futureElements" }) 
public class ScheduleTransactionInfo extends IdEntityInfo implements ScheduleTransaction, Serializable {

    @XmlElement
    private String scheduleBatchId;
    @XmlElement
    private String refObjectId;
    @XmlElement
    private String refObjectTypeKey;
    @XmlElement
    private String scheduleId;
    @XmlElement
    private String statusMessage;
    @XmlElement
    private List<ScheduleRequestComponentInfo> scheduleRequestComponents;
    @XmlAnyElement
    private List<Object> _futureElements;  

    public ScheduleTransactionInfo() {
    }

    public ScheduleTransactionInfo(ScheduleTransaction scheduleTransaction) {
        super (scheduleTransaction);
        if (null != scheduleTransaction) {
            this.refObjectId = scheduleTransaction.getRefObjectId();
            this.refObjectTypeKey = scheduleTransaction.getRefObjectTypeKey();
            this.scheduleId = scheduleTransaction.getScheduleId();
            this.scheduleBatchId = scheduleTransaction.getScheduleBatchId();
            this.statusMessage = scheduleTransaction.getStatusMessage();
            this.scheduleRequestComponents = new ArrayList<ScheduleRequestComponentInfo>();
            for(ScheduleRequestComponent component : scheduleTransaction.getScheduleRequestComponents()) {
                this.scheduleRequestComponents.add(new ScheduleRequestComponentInfo(component));
            }
        }
    }

    @Override
    public String getRefObjectTypeKey() {
        return this.refObjectTypeKey;
    }

    public void setRefObjectTypeKey(String refObjectTypeKey) {
        this.refObjectTypeKey = refObjectTypeKey;
    }

    @Override
    public String getScheduleBatchId () {
        return scheduleBatchId;
    }

    public void setScheduleBatchId(String scheduleBatchId) {
        this.scheduleBatchId = scheduleBatchId;
    }

    @Override
    public String getRefObjectId() {
        return this.refObjectId;
    }

    public void setRefObjectId(String refObjectId) {
        this.refObjectId = refObjectId;
    }

    @Override
    public List<ScheduleRequestComponentInfo> getScheduleRequestComponents() {
        if (null == this.scheduleRequestComponents) {
            return new ArrayList<ScheduleRequestComponentInfo>();
        }
        else {
            return this.scheduleRequestComponents;
        }
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

    public void setScheduleRequestComponents(List<ScheduleRequestComponentInfo> scheduleRequestComponents) {
        this.scheduleRequestComponents = scheduleRequestComponents;
    }
}
