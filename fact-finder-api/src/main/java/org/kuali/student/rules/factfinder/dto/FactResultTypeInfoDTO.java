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

import org.kuali.student.rules.factfinder.service.jaxws.adapter.FactResultColumnInfoMapAdapter;

/**
 * This class defines the properties of the result of a fetchFact call 
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FactResultTypeInfoDTO implements Serializable {

    @XmlAttribute
    private String key;
    
    @XmlElement
    private String name;
    
    @XmlElement
    private String description;
    
    @XmlElement
    @XmlJavaTypeAdapter(FactResultColumnInfoMapAdapter.class)
    private Map<String, FactResultColumnInfoDTO> resultColumnsMap;

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
     * @return the resultColumnsMap
     */
    public Map<String, FactResultColumnInfoDTO> getResultColumnsMap() {
        return resultColumnsMap;
    }

    /**
     * @param resultColumnsMap the resultColumnsMap to set
     */
    public void setResultColumnsMap(Map<String, FactResultColumnInfoDTO> resultColumnsMap) {
        this.resultColumnsMap = resultColumnsMap;
    }
}
