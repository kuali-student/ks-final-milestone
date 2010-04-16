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
package org.kuali.student.brms.rulemanagement.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class BusinessRuleContainerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
	private String namespace;

    @XmlElement
    private String description;

    @XmlElement
    private final List<BusinessRuleInfo> businessRuleList = new ArrayList<BusinessRuleInfo>();

    public BusinessRuleContainerInfo() {}
    
    public BusinessRuleContainerInfo(final String namespace, final String description) {
		super();
		this.namespace = namespace;
		this.description = description;
	}

	public String getNamespace() {
		return namespace;
	}

	public String getDescription() {
		return description;
	}

	public List<BusinessRuleInfo> getBusinessRules() {
	    if(null == businessRuleList) {
	        return new ArrayList<BusinessRuleInfo> ();
	    }
	    
		return businessRuleList;
	}
}
