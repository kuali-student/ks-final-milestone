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
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;


/**
 * Contains meta data about the left hand side of a Rule Proposition. For example, in "completed any 2 of (MATH101, MATH102,
 * MATH103)" the left hand side is "completed set of (MATH101, MATH102, MATH103).
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FactStructureInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String factStructureId;
    
    @XmlAttribute
    private String factTypeKey;
        
    @XmlElement
    private FactCriteriaTypeInfo criteriaTypeInfo;
                    
    @XmlElement
    private Boolean anchorFlag; 

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)    
    Map<String, String> resultColumnKeyTranslations;
        
    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)    
    Map<String, String> paramValueMap;

    @XmlAttribute
    private Boolean staticFact = false;
    
    @XmlElement
    private String staticValueDataType;

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
     * @return the criteriaTypeInfo
     */
    public FactCriteriaTypeInfo getCriteriaTypeInfo() {
        return criteriaTypeInfo;
    }

    /**
     * @param criteriaTypeInfo the criteriaTypeInfo to set
     */
    public void setCriteriaTypeInfo(FactCriteriaTypeInfo criteriaTypeInfo) {
        this.criteriaTypeInfo = criteriaTypeInfo;
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
     * @return the paramValueMap
     */
    public Map<String, String> getParamValueMap() {
        return paramValueMap;
    }

    /**
     * @param paramValuemap the paramValueMap to set
     */
    public void setParamValueMap(Map<String, String> paramValueMap) {
        this.paramValueMap = paramValueMap;
    }

    /**
     * @return the staticFact
     */
    public Boolean isStaticFact() {
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

	public String getStaticValueDataType() {
		return staticValueDataType;
	}

	public void setStaticValueDataType(String staticValueDataType) {
		this.staticValueDataType = staticValueDataType;
	}

    /**
     * @return the resultColumnKeyTranslations
     */
    public Map<String, String> getResultColumnKeyTranslations() {
        return resultColumnKeyTranslations;
    }

    /**
     * @param resultColumnKeyTranslations the resultColumnKeyTranslations to set
     */
    public void setResultColumnKeyTranslations(Map<String, String> resultColumnKeyTranslation) {
        this.resultColumnKeyTranslations = resultColumnKeyTranslation;
    }
    
    public String toString() {
    	return 
    		"(factStructureId=" + this.factStructureId +
    		"factTypeKey=" + this.factTypeKey + ")";
    }
}
