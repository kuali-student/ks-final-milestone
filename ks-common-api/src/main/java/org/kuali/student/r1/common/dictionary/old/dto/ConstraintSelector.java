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

package org.kuali.student.r1.common.dictionary.old.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;




@Deprecated
@XmlAccessorType(XmlAccessType.FIELD)
public class ConstraintSelector implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    protected String className;
    
    @XmlAttribute(required = true) 
    protected String key;
    
    @XmlAttribute
    protected String id;
    
    @XmlAttribute
    protected boolean serverSide;
    
    @XmlAttribute
    protected String locale;
    
    @XmlElement
    protected String minValue;

    @XmlElement
    protected String maxValue;

    @XmlElement
    protected Integer minLength;
    
    @XmlElement
    protected String maxLength;
    
    @XmlElement(name="validChars")
    protected ValidCharsConstraint validChars;
    
    @XmlElement
    protected Integer minOccurs;
    
    @XmlElement
    protected String maxOccurs;

    @XmlElement(name="require")
    protected List<RequireConstraint> requireConstraint;
    
    @XmlElement(name = "case")
    protected List<CaseConstraint> caseConstraint;

    @XmlElement(name = "typeStateCase")
    protected TypeStateCaseConstraint typeStateCaseConstraint;
        
    @XmlElement(name = "lookup")
    protected List<LookupConstraint> lookupConstraint;

    @XmlElement(name = "occurs")
    protected List<OccursConstraint> occursConstraint;
    
	/**
	 * @return the minValue
	 */
	public String getMinValue() {
		return minValue;
	}

	/**
	 * @param minValue the minValue to set
	 */
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	/**
	 * @return the maxValue
	 */
	public String getMaxValue() {
		return maxValue;
	}

	/**
	 * @param maxValue the maxValue to set
	 */
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
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
	public String getMaxLength() {
		return maxLength;
	}

	/**
	 * @param maxLength the maxLength to set
	 */
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
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
	public String getMaxOccurs() {
		return maxOccurs;
	}

	/**
	 * @param maxOccurs the maxOccurs to set
	 */
	public void setMaxOccurs(String maxOccurs) {
		this.maxOccurs = maxOccurs;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the serverSide
	 */
	public boolean isServerSide() {
		return serverSide;
	}

	/**
	 * @param serverSide the serverSide to set
	 */
	public void setServerSide(boolean serverSide) {
		this.serverSide = serverSide;
	}

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * @return the validChars
	 */
	public ValidCharsConstraint getValidChars() {
		return validChars;
	}

	/**
	 * @param validChars the validChars to set
	 */
	public void setValidChars(ValidCharsConstraint validChars) {
		this.validChars = validChars;
	}

	/**
	 * @return the requireConstraint
	 */
	public List<RequireConstraint> getRequireConstraint() {
		return requireConstraint;
	}

	/**
	 * @param requireConstraint the requireConstraint to set
	 */
	public void setRequireConstraint(List<RequireConstraint> requireConstraint) {
		this.requireConstraint = requireConstraint;
	}

	/**
	 * @return the typeStateCaseConstraint
	 */
	public TypeStateCaseConstraint getTypeStateCaseConstraint() {
		return typeStateCaseConstraint;
	}

	/**
	 * @param typeStateCaseConstraint the typeStateCaseConstraint to set
	 */
	public void setTypeStateCaseConstraint(
			TypeStateCaseConstraint typeStateCaseConstraint) {
		this.typeStateCaseConstraint = typeStateCaseConstraint;
	}

	/**
	 * @return the caseConstraint
	 */
	public List<CaseConstraint> getCaseConstraint() {
		return caseConstraint;
	}

	/**
	 * @param caseConstraint the caseConstraint to set
	 */
	public void setCaseConstraint(List<CaseConstraint> caseConstraint) {
		this.caseConstraint = caseConstraint;
	}

	/**
	 * @return the lookupConstraint
	 */
	public List<LookupConstraint> getLookupConstraint() {
		return lookupConstraint;
	}

	/**
	 * @param lookupConstraint the lookupConstraint to set
	 */
	public void setLookupConstraint(List<LookupConstraint> lookupConstraint) {
		this.lookupConstraint = lookupConstraint;
	}

	/**
	 * @return the occursConstraint
	 */
	public List<OccursConstraint> getOccursConstraint() {
		return occursConstraint;
	}

	/**
	 * @param occursConstraint the occursConstraint to set
	 */
	public void setOccursConstraint(List<OccursConstraint> occursConstraint) {
		this.occursConstraint = occursConstraint;
	}	
}
