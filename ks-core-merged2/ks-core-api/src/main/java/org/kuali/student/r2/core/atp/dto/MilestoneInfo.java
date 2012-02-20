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
package org.kuali.student.r2.core.atp.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.core.atp.infc.Milestone;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MilestoneInfo", propOrder = { 
                "id", "typeKey", "stateKey", "name", "descr", 
                "isAllDay", "isInstructionalDay", 
                "isRelative", "relativeAnchorMilestoneId",
                "isDateRange", "startDate", "endDate",
		"meta", "attributes", "_futureElements" })

public class MilestoneInfo 
    extends IdEntityInfo 
    implements Milestone, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private Boolean isAllDay;

    @XmlElement
    private Boolean isInstructionalDay;

    @XmlElement
    private Boolean isRelative;

    @XmlElement
    private String relativeAnchorMilestoneId;
	
    @XmlElement
    private Boolean isDateRange;
	
    @XmlElement
    private Date startDate;
	
    @XmlElement
    private Date endDate;
	
    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new MilestoneInfo.
     */
    public MilestoneInfo() {
    }

    /**
     * Constructs a new MilestoneInfo from another Milestone.
     * 
     * @param milestone the Milestone to copy
     */
    public MilestoneInfo(Milestone milestone) {
        super(milestone);

        if (milestone != null) {
            this.isAllDay = milestone.getIsAllDay();
            this.isInstructionalDay = milestone.getIsInstructionalDay();
            this.isRelative = milestone.getIsRelative();
            this.relativeAnchorMilestoneId = milestone.getRelativeAnchorMilestoneId();
            this.isDateRange = milestone.getIsDateRange();
            
            if (milestone.getStartDate() != null) {
                this.startDate = new Date(milestone.getStartDate().getTime());
            }

            if (milestone.getEndDate() != null) {
                this.endDate = new Date(milestone.getEndDate().getTime());
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
    public Boolean getIsInstructionalDay() {
        return isInstructionalDay;
    }
    
    public void setIsInstructionalDay(Boolean isInstructionalDay) {
        this.isInstructionalDay = isInstructionalDay;
    }

    @Override
    public Boolean getIsRelative() {
        return isRelative;
    }
    
    public void setIsRelative(Boolean isRelative) {
        this.isRelative = isRelative;
    }

    @Override
    public String getRelativeAnchorMilestoneId() {
        return relativeAnchorMilestoneId;
    }
    
    public void setRelativeAnchorMilestoneId(String relativeAnchorMilestoneId) {
        this.relativeAnchorMilestoneId = relativeAnchorMilestoneId;
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
