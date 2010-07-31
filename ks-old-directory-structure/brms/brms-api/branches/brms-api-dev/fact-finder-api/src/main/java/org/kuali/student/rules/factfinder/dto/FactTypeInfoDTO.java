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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * This class defines a fact with information about the parameters required for evaluation 
 * and information about the result generated when the fact is evaluated 
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FactTypeInfoDTO implements Serializable {

    @XmlElement
    private String factTypeKey;
    
    @XmlElement
    private String name;
    
    @XmlElement
    private String description;

    @XmlElement
    private FactResultTypeInfoDTO factResultTypeInfo;
    
    @XmlElement    
    private FactCriteriaTypeInfoDTO factCriteriaTypeInfo;

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the factResultTypeInfo
     */
    public FactResultTypeInfoDTO getFactResultTypeInfo() {
        return factResultTypeInfo;
    }

    /**
     * @param factResultTypeInfo the factResultTypeInfo to set
     */
    public void setFactResultTypeInfo(FactResultTypeInfoDTO factResultTypeInfo) {
        this.factResultTypeInfo = factResultTypeInfo;
    }

    /**
     * @return the factCriteriaTypeInfo
     */
    public FactCriteriaTypeInfoDTO getFactCriteriaTypeInfo() {
        return factCriteriaTypeInfo;
    }

    /**
     * @param factCriteriaTypeInfo the factCriteriaTypeInfo to set
     */
    public void setFactCriteriaTypeInfo(FactCriteriaTypeInfoDTO factCriteriaTypeInfo) {
        this.factCriteriaTypeInfo = factCriteriaTypeInfo;
    }    
}
