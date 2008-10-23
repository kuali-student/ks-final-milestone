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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Stores properties about a single fact parameter  
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FactParamDTO implements Serializable {

    public enum FactParamDefTime {DEFINITION, EXECUTION};
    
    @XmlAttribute
    private String key;
    
    @XmlElement
    private String name;
    
    @XmlElement
    private String description;
    
    @XmlElement
    private String dataType;
    
    @XmlElement
    private Integer minLength;
    
    @XmlElement
    private Integer maxLength;
    
    @XmlElement
    private String validCharacters;
    
    @XmlElement
    private Integer minOccurs;
    
    @XmlElement
    private Integer maxOccurs;
    
    @XmlElement
    private FactParamDefTime defTime;

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
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
     * @return the minLength
     */
    public Integer getMinLength() {
        return minLength;
    }

    /**
     * @param minLength the minLength to set
     */
    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    /**
     * @return the maxLength
     */
    public Integer getMaxLength() {
        return maxLength;
    }

    /**
     * @param maxLength the maxLength to set
     */
    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * @return the validCharacters
     */
    public String getValidCharacters() {
        return validCharacters;
    }

    /**
     * @param validCharacters the validCharacters to set
     */
    public void setValidCharacters(String validCharacters) {
        this.validCharacters = validCharacters;
    }

    /**
     * @return the minOccurs
     */
    public Integer getMinOccurs() {
        return minOccurs;
    }

    /**
     * @param minOccurs the minOccurs to set
     */
    public void setMinOccurs(Integer minOccurs) {
        this.minOccurs = minOccurs;
    }

    /**
     * @return the maxOccurs
     */
    public Integer getMaxOccurs() {
        return maxOccurs;
    }

    /**
     * @param maxOccurs the maxOccurs to set
     */
    public void setMaxOccurs(Integer maxOccurs) {
        this.maxOccurs = maxOccurs;
    }

    /**
     * @return the defTime
     */
    public FactParamDefTime getDefTime() {
        return defTime;
    }

    /**
     * @param defTime the defTime to set
     */
    public void setDefTime(FactParamDefTime defTime) {
        this.defTime = defTime;
    }
    
    /**
     * @return Key, name, definitionTime and dataType.
     */
    public String toString() {
    	return 
    		"(key=" + this.key + 
    		", name=" + this.name + 
    		", definitionTime=" + this.defTime + 
    		", dataType=" + this.dataType + ")";
    }
}
