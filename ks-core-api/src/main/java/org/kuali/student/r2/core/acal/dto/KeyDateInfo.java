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

import org.kuali.student.r2.core.acal.infc.KeyDate;
import org.kuali.student.r2.common.dto.IdEntityInfo;

import org.kuali.student.r2.core.acal.infc.KeyDate;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KeyDateInfo", propOrder = { 
                "id", "typeKey", "stateKey", "name", "descr", 
                "isAllDay", "isRelativeToKeyDate", "relativeAnchorKeyDateId", 
                "isDateRange", "startDate", "endDate",
		"meta", "attributes", "_futureElements" })

public class KeyDateInfo 
    extends IdEntityInfo 
    implements KeyDate, Serializable {

    private static final long serialVersionUID = 1L;
	
    @XmlElement
    private Boolean isAllDay;

    @XmlElement
    private Boolean isRelativeToKeyDate;
	
    @XmlElement
    private String relativeAnchorKeyDateId;

    @XmlElement
    private Boolean isDateRange;
	
    @XmlElement
    private Date startDate;
	
    @XmlElement
    private Date endDate;
	
    @XmlAnyElement
    private List<Element> _futureElements;

    /**
     * Constructs a new KeyDateInfo.
     */
    public KeyDateInfo() {
    }
    
    /**
     * Constructs a new KeyDateInfo from another KeyDate.
     * 
     * @param keyDate the KeyDate to copy
     */
    public KeyDateInfo(KeyDate keyDate) {
        super(keyDate);
        if (keyDate != null) {
            this.isAllDay = keyDate.getIsAllDay();
            this.isRelativeToKeyDate = keyDate.getIsRelativeToKeyDate();
            this.relativeAnchorKeyDateId = keyDate.getRelativeAnchorKeyDateId();

            this.isDateRange = keyDate.getIsDateRange();

            if (keyDate.getStartDate() != null) {
                this.startDate = new Date(keyDate.getStartDate().getTime());
            }

            if (keyDate.getEndDate() != null) {
                this.endDate = new Date(keyDate.getEndDate().getTime());
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
    public Boolean getIsRelativeToKeyDate() {
        return isRelativeToKeyDate;
    }
    
    public void setIsRelativeToKeyDate(Boolean isRelativeToKeyDate) {
        this.isRelativeToKeyDate = isRelativeToKeyDate;
    }

    @Override
    public String getRelativeAnchorKeyDateId() {
        return relativeAnchorKeyDateId;
    }
    
    public void setRelativeAnchorKeyDateId(String relativeAnchorKeyDateId) {
        this.relativeAnchorKeyDateId = relativeAnchorKeyDateId;
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
