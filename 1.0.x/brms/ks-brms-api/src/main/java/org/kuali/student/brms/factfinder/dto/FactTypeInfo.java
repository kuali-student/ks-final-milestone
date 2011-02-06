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

package org.kuali.student.brms.factfinder.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.dto.TypeInfo;

/**
 * 
 * This class defines a fact with information about the parameters required for evaluation 
 * and information about the result generated when the fact is evaluated 
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FactTypeInfo extends TypeInfo {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private FactResultTypeInfo factResultTypeInfo;
    
    @XmlElement    
    private FactCriteriaTypeInfo factCriteriaTypeInfo;

    /**
     * @return the factResultTypeInfo
     */
    public FactResultTypeInfo getFactResultTypeInfo() {
        return factResultTypeInfo;
    }

    /**
     * @param factResultTypeInfo the factResultTypeInfo to set
     */
    public void setFactResultTypeInfo(FactResultTypeInfo factResultTypeInfo) {
        this.factResultTypeInfo = factResultTypeInfo;
    }

    /**
     * @return the factCriteriaTypeInfo
     */
    public FactCriteriaTypeInfo getFactCriteriaTypeInfo() {
        return factCriteriaTypeInfo;
    }

    /**
     * @param factCriteriaTypeInfo the factCriteriaTypeInfo to set
     */
    public void setFactCriteriaTypeInfo(FactCriteriaTypeInfo factCriteriaTypeInfo) {
        this.factCriteriaTypeInfo = factCriteriaTypeInfo;
    }
    
    public String toString() {
    	return 
    		"(factCriteriaTypeInfo.id=" + this.factCriteriaTypeInfo.getId() +
    		", factResultTypeInfo.id=" + this.factResultTypeInfo.getId() + ")";
    }
}
