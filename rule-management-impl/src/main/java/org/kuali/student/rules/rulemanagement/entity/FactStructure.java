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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
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
    
    private String dataType;
    
    private Boolean anchorFlag; 
 
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy="factStructure")
    private List<FactStructureVariable> executionVariableList;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy="factStructure")
    private List<FactStructureVariable> definitionVariableList;
    
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
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
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
     * @return the executionVariableList
     */
    public List<FactStructureVariable> getExecutionVariableList() {
        return executionVariableList;
    }

    /**
     * @param executionVariableList the executionVariableList to set
     */
    public void setExecutionVariableList(List<FactStructureVariable> executionVariableList) {
        this.executionVariableList = executionVariableList;
    }

    /**
     * @return the definitionVariableList
     */
    public List<FactStructureVariable> getDefinitionVariableList() {
        return definitionVariableList;
    }

    /**
     * @param definitionVariableList the definitionVariableList to set
     */
    public void setDefinitionVariableList(List<FactStructureVariable> definitionVariableList) {
        this.definitionVariableList = definitionVariableList;
    }
}
