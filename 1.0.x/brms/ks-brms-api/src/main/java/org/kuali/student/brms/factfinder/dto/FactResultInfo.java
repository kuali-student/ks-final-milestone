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

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;



/**
 * 
 * This class contains the result of a fetchFact call 
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FactResultInfo implements Serializable{

    private static final long serialVersionUID = 1L;

    @XmlElement
    FactResultTypeInfo factResultTypeInfo;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)    
    List<Map<String, String>> resultList;

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
     * @return the resultList
     */
    public List<Map<String, String>> getResultList() {
        return resultList;
    }

    /**
     * @param resultList the resultList to set
     */
    public void setResultList(List<Map<String, String>> resultList) {
        this.resultList = resultList;
    }
    
    /**
     * Returns fact result type key and the result list.
     */
    public String toString() {
    	return "(factResultTypeKey=" + this.factResultTypeInfo.getId() +
    		", resultList={" + this.resultList + "})";
    }
}
