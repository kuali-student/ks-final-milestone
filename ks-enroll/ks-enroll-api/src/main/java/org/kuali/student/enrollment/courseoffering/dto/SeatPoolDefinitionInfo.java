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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseoffering.infc.SeatPoolDefinition;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SeatPoolDefinitionInfo", propOrder = {
                "id", "typeKey", "stateKey", "name", "descr", 
                "expirationMilestoneTypeKey",
                "isPercentage", "seatLimit", "processingPriority", 
                "populationId",
                "meta", "attributes", "_futureElements"})

public class SeatPoolDefinitionInfo 
    extends IdEntityInfo 
    implements SeatPoolDefinition {

    private static final long serialVersionUID = 1L;

    @XmlElement    
    private String expirationMilestoneTypeKey;

    @XmlElement    
    private Boolean isPercentage;
    
    @XmlElement
    private Integer seatLimit;
    
    @XmlElement
    private Integer processingPriority;
    
    @XmlElement
    private String populationId;
    
    @XmlAnyElement
    private List<Element> _futureElements;
    
    
    /**
     * Constructs a new SeatPoolDefinition.
     */
    public SeatPoolDefinitionInfo() {
    }

    /**
     * Constructs a new SeatPoolDefinition.
     *
     * @param pool the seat pool definition to copy
     */    
    public SeatPoolDefinitionInfo(SeatPoolDefinition pool) {
        super(pool);
        
        if (pool == null) {
            return;
        }
        
        this.expirationMilestoneTypeKey = pool.getExpirationMilestoneTypeKey();
        this.isPercentage = pool.getIsPercentage();
        this.seatLimit = pool.getSeatLimit();
        this.processingPriority = pool.getProcessingPriority();
        this.populationId = pool.getPopulationId();
    }
   

    @Override
    public String getExpirationMilestoneTypeKey() {
        return this.expirationMilestoneTypeKey;
    }

    public void setExpirationMilestoneTypeKey(String expirationMilestoneTypeKey) {
        this.expirationMilestoneTypeKey = expirationMilestoneTypeKey;
    }

    @Override
    public Boolean getIsPercentage() {
        return this.isPercentage;
    }

    public void setIsPercentage(Boolean isPercentage) {
        this.isPercentage = isPercentage;;
    }

    @Override
    public Integer getSeatLimit() {
        return this.seatLimit;
    }

    public void setSeatLimit(Integer seatLimit) {
        this.seatLimit = seatLimit;
    }

    @Override
    public Integer getProcessingPriority() {
        return this.processingPriority;
    }

    public void setProcessingPriority(Integer processingPriority) {
        this.processingPriority = processingPriority;
    }

    @Override
    public String getPopulationId() {
        return this.populationId;
    }

    public void setPopulationId(String populationId) {
        this.populationId = populationId;
    }        
}
