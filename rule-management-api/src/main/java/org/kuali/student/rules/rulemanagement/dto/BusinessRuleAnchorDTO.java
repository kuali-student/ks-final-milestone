/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.rulemanagement.dto;

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.poc.common.ws.binding.JaxbAttributeMapListAdapter;

/**
 * Maps a set of Business Rule Types to form an AgendaInfoDTO and a specif instance of Business Rules form an Agenda for this
 * AgendaInfoDTO
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BusinessRuleAnchorDTO implements Serializable {

    @XmlElement
    private String businessRuleTypeKey;
    
    @XmlElement
    private String anchorTypeKey;
    
    @XmlElement
    private String anchorValue;

    /**
     * @return the businessRuleTypeKey
     */
    public String getBusinessRuleTypeKey() {
        return businessRuleTypeKey;
    }

    /**
     * @param businessRuleTypeKey the businessRuleTypeKey to set
     */
    public void setBusinessRuleTypeKey(String businessRuleTypeKey) {
        this.businessRuleTypeKey = businessRuleTypeKey;
    }

    /**
     * @return the anchorTypeKey
     */
    public String getAnchorTypeKey() {
        return anchorTypeKey;
    }

    /**
     * @param anchorTypeKey the anchorTypeKey to set
     */
    public void setAnchorTypeKey(String anchorTypeKey) {
        this.anchorTypeKey = anchorTypeKey;
    }

    /**
     * @return the anchorValue
     */
    public String getAnchorValue() {
        return anchorValue;
    }

    /**
     * @param anchorValue the anchorValue to set
     */
    public void setAnchorValue(String anchorValue) {
        this.anchorValue = anchorValue;
    }
    
}
