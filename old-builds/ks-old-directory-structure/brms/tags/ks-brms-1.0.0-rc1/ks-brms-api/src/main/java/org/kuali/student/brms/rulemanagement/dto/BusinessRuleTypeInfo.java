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
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * Contains information about a business rule type 
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BusinessRuleTypeInfo implements Serializable{

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String bussinessRuleTypeKey;
    
    @XmlElement
    private String anchorTypeKey;
    
    @XmlElement(name="factStructure")
    @XmlElementWrapper(name = "factStructureList")
    private List<String> factTypeKeyList;

    /**
     * @return the bussinessRuleTypeKey
     */
    public String getBussinessRuleTypeKey() {
        return bussinessRuleTypeKey;
    }

    /**
     * @param bussinessRuleTypeKey the bussinessRuleTypeKey to set
     */
    public void setBussinessRuleTypeKey(String bussinessRuleTypeKey) {
        this.bussinessRuleTypeKey = bussinessRuleTypeKey;
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
     * @return the factTypeKeyList
     */
    public List<String> getFactTypeKeyList() {
        if(null == factTypeKeyList) {
            return new ArrayList<String>();
        }
        
        return factTypeKeyList;
    }

    /**
     * @param factTypeKeyList the factTypeKeyList to set
     */
    public void setFactTypeKeyList(List<String> factTypeKeyList) {
        this.factTypeKeyList = factTypeKeyList;
    }
}
