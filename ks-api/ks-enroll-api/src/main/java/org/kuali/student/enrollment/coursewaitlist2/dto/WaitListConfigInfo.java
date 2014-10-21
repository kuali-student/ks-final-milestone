/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by Charles on 9/24/2014
 */
package org.kuali.student.enrollment.coursewaitlist2.dto;

import org.kuali.student.enrollment.coursewaitlist2.infc.WaitListConfig;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.infc.TimeAmount;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Represents a global config of waitlist information
 *
 * @author Kuali Student Team
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WaitListConfigInfo", propOrder = {
        "id", "typeKey", "stateKey",
        "automaticallyProcessed", "confirmationRequired",
        "checkInRequired", "checkInFrequency", "allowHoldUntilEntries", "effectiveDate", "expirationDate",
        "meta", "attributes", "_futureElements"})
public class WaitListConfigInfo extends RelationshipInfo implements WaitListConfig, Serializable {
    @XmlElement
    private Boolean automaticallyProcessed;
    @XmlElement
    private Boolean confirmationRequired;
    @XmlElement
    private Boolean checkInRequired;
    @XmlElement
    private TimeAmountInfo checkInFrequency;
    @XmlElement
    private Boolean allowHoldUntilEntries;
    @XmlElement
    private Date effectiveDate;
    @XmlElement
    private Date expirationDate;
    @XmlAnyElement
    private List<Object> _futureElements;

    public WaitListConfigInfo() {

    }

    public void setConfirmationRequired(Boolean confirmationRequired) {
        this.confirmationRequired = confirmationRequired;
    }

    public void setCheckInRequired(Boolean checkInRequired) {
        this.checkInRequired = checkInRequired;
    }

    public void setCheckInFrequency(TimeAmountInfo checkInFrequency) {
        this.checkInFrequency = checkInFrequency;
    }

    public void setAllowHoldUntilEntries(Boolean allowHoldUntilEntries) {
        this.allowHoldUntilEntries = allowHoldUntilEntries;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setAutomaticallyProcessed(Boolean automaticallyProcessed) {
        this.automaticallyProcessed = automaticallyProcessed;
    }

    @Override
    public Boolean getAutomaticallyProcessed() {
        return automaticallyProcessed;
    }

    @Override
    public Boolean getConfirmationRequired() {
        return confirmationRequired;
    }

    @Override
    public Boolean getCheckInRequired() {
        return checkInRequired;
    }

    @Override
    public TimeAmount getCheckInFrequency() {
        return checkInFrequency;
    }

    @Override
    public Boolean getAllowHoldUntilEntries() {
        return allowHoldUntilEntries;
    }
}
