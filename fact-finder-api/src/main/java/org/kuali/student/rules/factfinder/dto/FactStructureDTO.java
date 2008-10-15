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
package org.kuali.student.rules.factfinder.dto;

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.poc.common.ws.binding.JaxbAttributeMapListAdapter;

/**
 * Contains meta data about the left hand side of a Rule Proposition. For example, in "completed any 2 of (MATH101, MATH102,
 * MATH103)" the left hand side is "completed set of (MATH101, MATH102, MATH103).
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FactStructureDTO implements Serializable {

    private Boolean staticFact = false;
    
    @XmlAttribute
    private String factStructureId;
    
    @XmlElement
    private String dataType;
    
    @XmlElement
    private Boolean anchorFlag; 
 
    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)    
    Map<String, String> executionVariableList;
    
    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)    
    Map<String, String> definitionVariableList;

    @XmlElement
    private String staticValue;
    

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
    public Map<String, String> getExecutionVariableList() {
        return executionVariableList;
    }

    /**
     * @param executionVariableList the executionVariableList to set
     */
    public void setExecutionVariableList(Map<String, String> executionVariableList) {
        this.executionVariableList = executionVariableList;
    }

    /**
     * @return the definitionVariableList
     */
    public Map<String, String> getDefinitionVariableList() {
        return definitionVariableList;
    }

    /**
     * @param definitionVariableList the definitionVariableList to set
     */
    public void setDefinitionVariableList(Map<String, String> definitionVariableList) {
        this.definitionVariableList = definitionVariableList;
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
}
