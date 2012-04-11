/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.courseoffering.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseoffering.infc.SeatPoolDefinition;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

/**
 * 
 * @author Kuali Student Team (Kamal)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SeatPoolDefinitionInfo", propOrder = {"registrationGroupIds", "courseOfferingId", 
        "maximumSeatCount", "processingPriority", "capacityRestrictionStatementId",
        "id", "typeKey", "stateKey", "name", "descr", "meta", "attributes", "_futureElements"})
public class SeatPoolDefinitionInfo extends IdEntityInfo implements SeatPoolDefinition {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String courseOfferingId;

    @XmlElement    
    private List<String> registrationGroupIds;
    
    @XmlElement
    private Integer maximumSeatCount;
    
    @XmlElement
    private Integer processingPriority;
    
    @XmlElement
    private String capacityRestrictionStatementId;
    
    @XmlAnyElement
    private List<Element> _futureElements;
    
    
    public SeatPoolDefinitionInfo() {
    }
    
    public SeatPoolDefinitionInfo(SeatPoolDefinition seatpool) {
        super(seatpool);
        
        if(null == seatpool) return;
        
        this.courseOfferingId = seatpool.getCourseOfferingId();
        this.maximumSeatCount = (null != seatpool.getMaximumSeatCount()) ? new Integer(seatpool.getMaximumSeatCount()) : null;
        this.registrationGroupIds = (null != seatpool.getRegistrationGroupIds()) ? new ArrayList<String>(seatpool.getRegistrationGroupIds()) : null;
        this.processingPriority = (null != seatpool.getProcessingPriority()) ? new Integer(seatpool.getProcessingPriority()) : null;
        this.capacityRestrictionStatementId = seatpool.getCapacityRestrictionStatementId();
    }
   
        
    @Override
    public String getCourseOfferingId() {
        return this.courseOfferingId;
    }

    @Override
    public List<String> getRegistrationGroupIds() {
        if(null == this.registrationGroupIds) {
            this.registrationGroupIds = new ArrayList<String>();
        }
        return this.registrationGroupIds;
    }

    @Override
    public Integer getMaximumSeatCount() {
        return this.maximumSeatCount;
    }

    @Override
    public Integer getProcessingPriority() {
        return this.processingPriority;
    }

    @Override
    public String getCapacityRestrictionStatementId() {
        return this.capacityRestrictionStatementId;        
    }
    
    public void setRegistrationGroupIds(List<String> registrationGroupIds) {
        this.registrationGroupIds = registrationGroupIds;
    }

    public void setMaximumSeatCount(Integer maximumSeatCount) {
        this.maximumSeatCount = maximumSeatCount;
    }

    public void setProcessingPriority(Integer processingPriority) {
        this.processingPriority = processingPriority;
    }

    public void setCapacityRestrictionStatementId(String capacityRestrictionStatementId) {
        this.capacityRestrictionStatementId = capacityRestrictionStatementId;
    }        
}
