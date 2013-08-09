/**
 * Copyright 2012 The Kuali Foundation 
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

package org.kuali.student.enrollment.roster.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.roster.infc.LprRoster;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LprRosterInfo", propOrder = {
                "id", "typeKey", "stateKey", "name", "descr", 
                "associatedLuiIds", "maximumCapacity", "checkInRequired", 
                "checkInFrequency", "meta", "attributes", "_futureElements"})
        
public class LprRosterInfo 
    extends IdEntityInfo 
    implements LprRoster, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<String> associatedLuiIds;

    @XmlElement
    private Integer maximumCapacity;

    @XmlElement
    private Boolean checkInRequired;

    @XmlElement
    private TimeAmountInfo checkInFrequency;

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     *  Constructs a new LprRosterInfo.
     */
    public LprRosterInfo() {
    }

    /**
     *  Constructs a new LprRosterInfo from another LprRoster.
     *
     *  @param lprRoster the LprRoster to copy
     */    
    public LprRosterInfo(LprRoster lprRoster) {
        if (lprRoster != null) {
            this.associatedLuiIds = new ArrayList<String>(lprRoster.getAssociatedLuiIds());
            this.maximumCapacity = lprRoster.getMaximumCapacity();
            this.checkInRequired = lprRoster.getCheckInRequired();
            this.checkInFrequency = lprRoster.getCheckInFrequency();
        }
    }

    @Override
    public List<String> getAssociatedLuiIds() {
        if (this.associatedLuiIds == null) {
            this.associatedLuiIds = new ArrayList<String>();
        }

        return this.associatedLuiIds;
    }

    public void setAssociatedLuiIds(List<String> associatedLuiIds) {
        this.associatedLuiIds = associatedLuiIds;
    }

    @Override
    public Integer getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(Integer maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
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
}
