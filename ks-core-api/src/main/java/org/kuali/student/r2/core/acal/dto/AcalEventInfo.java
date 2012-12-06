/*
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.r2.core.acal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.core.acal.infc.AcalEvent;
import org.kuali.student.r2.common.dto.IdEntityInfo;

import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcalEventInfo", propOrder = { 
                "id", "typeKey", "stateKey", "name", "descr", 
                "isAllDay", "isDateRange", "startDate", "endDate", 
                "meta", "attributes", "_futureElements" })

public class AcalEventInfo 
    extends IdEntityInfo 
    implements AcalEvent, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement
    private Boolean isAllDay;

    @XmlElement
    private Boolean isDateRange;

    @XmlElement
    private Date startDate;

    @XmlElement
    private Date endDate;

    @XmlAnyElement
    private List<Element> _futureElements;

    /**
     * Constructs a new AcalEvent.
     */
    public AcalEventInfo() {
        super();
    }

    /**
     * Constructs a new AcalEventInfo from another AcalEvent.
     * 
     * @param acalEvent the AcalEvent to copy
     */
    public AcalEventInfo(AcalEvent acalEvent) {
        super(acalEvent);
        if (acalEvent != null) {
            this.isAllDay = acalEvent.getIsAllDay();
            this.isDateRange = acalEvent.getIsDateRange();

            if (acalEvent.getStartDate() != null) {
                this.startDate = new Date(acalEvent.getStartDate().getTime());
            }

            if (acalEvent.getEndDate() != null) {
                this.endDate = new Date(acalEvent.getEndDate().getTime());
            }
        }
    }
    
    @Override
    public Boolean getIsAllDay() {
        return isAllDay;
    }

    public void setIsAllDay(Boolean isAllDay) {
        this.isAllDay = isAllDay;
    }
    
    @Override
    public Boolean getIsDateRange() {
        return isDateRange;
    }
    
    public void setIsDateRange(Boolean isDateRange) {
        this.isDateRange = isDateRange;
    }
    
    @Override
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    @Override
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
