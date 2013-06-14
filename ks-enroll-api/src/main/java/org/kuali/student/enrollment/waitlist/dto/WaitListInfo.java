/**
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.waitlist.dto;


import org.kuali.student.enrollment.waitlist.infc.WaitList;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrgInfo", propOrder = {
        "id", "typeKey", "stateKey","associatedLuiIds",
        "luiRefObjectUri", "waitListProcessingType", "maxSize",
        "checkInRequired", "checkInFrequency", "allowBlockedEntries", "effectiveDate", "expirationDate",
        "meta", "attributes", "_futureElements" })
public class WaitListInfo extends IdNamelessEntityInfo implements WaitList, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<String> associatedLuiIds;
    @XmlElement
    private String luiRefObjectUri;
    @XmlElement
    private String waitListProcessingType;
    @XmlElement
    private Integer maxSize;
    @XmlElement
    private Boolean checkInRequired;
    @XmlElement
    private TimeAmountInfo checkInFrequency;
    @XmlElement
    private Boolean allowBlockedEntries;
    @XmlElement
    private Date effectiveDate;
    @XmlElement
    private Date expirationDate;
    @XmlAnyElement
    private List<Object> _futureElements;


    public WaitListInfo() {
    }

    public WaitListInfo(WaitList waitList) {
        super(waitList);

        if(waitList != null) {
            if(waitList.getAssociatedLuiIds() != null) {
                setAssociatedLuiIds(new ArrayList<String>(waitList.getAssociatedLuiIds()));
            }
            setLuiRefObjectUri(waitList.getLuiRefObjectUri());
            setWaitListProcessingType(waitList.getWaitListProcessingType());
            setMaxSize(waitList.getMaxSize());
            setCheckInRequired(waitList.getCheckInRequired());
            if(waitList.getCheckInFrequency() != null) {
                setCheckInFrequency(new TimeAmountInfo(waitList.getCheckInFrequency()));
            }
            setAllowBlockedEntries(waitList.getAllowBlockedEntries());
            if(waitList.getEffectiveDate() != null) {
                setEffectiveDate(new Date(waitList.getEffectiveDate().getTime()));
            }
            if(waitList.getExpirationDate() != null) {
                setExpirationDate(new Date(waitList.getExpirationDate().getTime()));
            }
        }
    }

    @Override
    public List<String> getAssociatedLuiIds() {
        if(associatedLuiIds == null) {
            associatedLuiIds = new ArrayList<String>();
        }
        return associatedLuiIds;
    }

    public void setAssociatedLuiIds(List<String> associatedLuiIds) {
        this.associatedLuiIds = associatedLuiIds;
    }

    @Override
    public String getLuiRefObjectUri() {
        return luiRefObjectUri;
    }

    public void setLuiRefObjectUri(String luiRefObjectUri) {
        this.luiRefObjectUri = luiRefObjectUri;
    }

    @Override
    public String getWaitListProcessingType() {
        return waitListProcessingType;
    }

    public void setWaitListProcessingType(String waitListProcessingType) {
        this.waitListProcessingType = waitListProcessingType;
    }

    @Override
    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public Boolean getCheckInRequired() {
        return checkInRequired;
    }

    public void setCheckInRequired(Boolean checkInRequired) {
        this.checkInRequired = checkInRequired;
    }

    @Override
    public TimeAmountInfo getCheckInFrequency() {
        return checkInFrequency;
    }

    public void setCheckInFrequency(TimeAmountInfo checkInFrequency) {
        this.checkInFrequency = checkInFrequency;
    }

    @Override
    public Boolean getAllowBlockedEntries() {
        return allowBlockedEntries;
    }

    public void setAllowBlockedEntries(Boolean allowBlockedEntries) {
        this.allowBlockedEntries = allowBlockedEntries;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
