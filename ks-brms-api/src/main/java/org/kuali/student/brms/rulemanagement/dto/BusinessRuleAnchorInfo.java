/**
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

package org.kuali.student.brms.rulemanagement.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Maps a set of Business Rule Types to form an AgendaInfo and a specify instance of Business Rules form an Agenda for this
 * AgendaInfo
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BusinessRuleAnchorInfo implements Serializable {

    private static final long serialVersionUID = 1L;

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
