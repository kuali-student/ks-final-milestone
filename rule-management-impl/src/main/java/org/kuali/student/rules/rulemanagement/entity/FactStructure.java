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
package org.kuali.student.rules.rulemanagement.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.poc.common.util.UUIDHelper;

/**
 * Contains meta data about the left hand side of a Rule Proposition. For example, in "completed any 2 of (MATH101, MATH102,
 * MATH103)" the left hand side is "completed set of (MATH101, MATH102, MATH103).
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@Entity
@Table(name="FactStructure_T")
public class FactStructure {

    @Id
    private String id;
    
    private String factStructureId;

    private String factTypeKey;
    
    private Boolean anchorFlag; 
 
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, mappedBy="factStructure")
    private Set<FactStructureVariable> paramValueSet;
        
    private Boolean staticFact;
    
    private String staticValue;
    
    private String staticValueDataType;
    
    @ManyToOne
    @JoinColumn(name = "fkYieldValueFunction")
    private YieldValueFunction yieldValueFunction; 
    
    /**
     * AutoGenerate the id
     */
    @PrePersist
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the factStructureId
     */
    public String getFactStructureId() {
        return factStructureId;
    }

    /**
     * @param factStructureId the factStructureId to set
     */
    public void setFactStructureId(String factStructureId) {
        this.factStructureId = factStructureId;
    }

    /**
     * @return the factTypeKey
     */
    public String getFactTypeKey() {
        return factTypeKey;
    }

    /**
     * @param factTypeKey the factTypeKey to set
     */
    public void setFactTypeKey(String factTypeKey) {
        this.factTypeKey = factTypeKey;
    }

    /**
     * @return the anchorFlag
     */
    public Boolean getAnchorFlag() {
        return anchorFlag;
    }

    /**
     * @param anchorFlag the anchorFlag to set
     */
    public void setAnchorFlag(Boolean anchorFlag) {
        this.anchorFlag = anchorFlag;
    }

    /**
     * @return the paramValueList
     */
    public Set<FactStructureVariable> getParamValueSet() {
        return paramValueSet;
    }

    /**
     * @param paramValueList the paramValueList to set
     */
    public void setParamValueSet(Set<FactStructureVariable> paramValueSet) {
        this.paramValueSet = paramValueSet;
    }

    /**
     * @return the staticFact
     */
    public Boolean getStaticFact() {
        return staticFact;
    }

    /**
     * @param staticFact the staticFact to set
     */
    public void setStaticFact(Boolean staticFact) {
        this.staticFact = staticFact;
    }

    /**
     * @return the staticValue
     */
    public String getStaticValue() {
        return staticValue;
    }

    /**
     * @param staticValue the staticValue to set
     */
    public void setStaticValue(String staticValue) {
        this.staticValue = staticValue;
    }

    /**
     * @return the yieldValueFunction
     */
    public YieldValueFunction getYieldValueFunction() {
        return yieldValueFunction;
    }

    /**
     * @param yieldValueFunction the yieldValueFunction to set
     */
    public void setYieldValueFunction(YieldValueFunction yieldValueFunction) {
        this.yieldValueFunction = yieldValueFunction;
    }

    /**
     * @return the staticValueDataType
     */
    public String getStaticValueDataType() {
        return staticValueDataType;
    }

    /**
     * @param staticValueDataType the staticValueDataType to set
     */
    public void setStaticValueDataType(String staticValueDataType) {
        this.staticValueDataType = staticValueDataType;
    }    
}
