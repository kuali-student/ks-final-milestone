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

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.brms.factfinder.service.jaxws.adapter.FactParamMapAdapter;
import org.kuali.student.core.dto.TypeInfo;

/**
 * Contains the criteria definition associated with a Fact. Criteria definition
 * provides information about the parameters needed to evaluate a fact. 
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FactCriteriaTypeInfo extends TypeInfo {
        
    private static final long serialVersionUID = 1L;
    
    @XmlElement
    @XmlJavaTypeAdapter(FactParamMapAdapter.class)
    private Map<String, FactParamInfo> factParamMap;

    /**
     * @return the factParamMap
     */
    public Map<String, FactParamInfo> getFactParamMap() {
        return factParamMap;
    }

    /**
     * @param factParamMap the factParamMap to set
     */
    public void setFactParamMap(Map<String, FactParamInfo> factParamMap) {
        this.factParamMap = factParamMap;
    }   
}
