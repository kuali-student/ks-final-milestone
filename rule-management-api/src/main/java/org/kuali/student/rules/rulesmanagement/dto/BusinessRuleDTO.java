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
package org.kuali.student.rules.rulesmanagement.dto;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class BusinessRuleDTO {

    @XmlAttribute
	private String id;

    @XmlAttribute
	private String identifier;

    @XmlElement
    private String name;

    @XmlElement
	private String description;

    @XmlElement
	private String successMessage;

    @XmlElement
	private String failureMessage;

    @XmlElement
	private String compiledID;

    @XmlElement
	private String anchor;

    @XmlElement
	private String businessRuleType;

    @XmlElement
	private BusinessRuleInfoDTO businessRuleInfo;

    @XmlElement
	private Collection<RuleElementDTO> elements;

    public BusinessRuleDTO() {}
    
	public BusinessRuleDTO(final String id, final String identifier, final String name, final String description) {
		super();
		this.id = id;
		this.identifier = identifier;
		this.name = name;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(final String successMessage) {
		this.successMessage = successMessage;
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(final String failureMessage) {
		this.failureMessage = failureMessage;
	}

	public String getCompiledID() {
		return compiledID;
	}

	public void setCompiledID(final String compiledID) {
		this.compiledID = compiledID;
	}

	public String getAnchor() {
		return anchor;
	}

	public void setAnchor(final String anchor) {
		this.anchor = anchor;
	}

	public String getBusinessRuleType() {
		return businessRuleType;
	}

	public void setBusinessRuleType(final String businessRuleType) {
		this.businessRuleType = businessRuleType;
	}

	public BusinessRuleInfoDTO getBusinessRuleInfo() {
		return businessRuleInfo;
	}

	public void setBusinessRuleInfo(final BusinessRuleInfoDTO businessRuleInfo) {
		this.businessRuleInfo = businessRuleInfo;
	}

	public Collection<RuleElementDTO> getElements() {
		return elements;
	}

	public void setElements(final Collection<RuleElementDTO> elements) {
		this.elements = elements;
	}

}
