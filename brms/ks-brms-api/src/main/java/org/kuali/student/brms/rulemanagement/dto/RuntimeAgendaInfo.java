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

@XmlAccessorType(XmlAccessType.FIELD)
public class RuntimeAgendaInfo implements Serializable {

	private static final long serialVersionUID = 1L;

    @XmlElement
    private Boolean executionLogging = Boolean.FALSE;
    
	@XmlElement
	private List<BusinessRuleInfo> businessRules;

	public List<BusinessRuleInfo> getBusinessRules() {
	    if(null == businessRules) {
	        return new ArrayList<BusinessRuleInfo>();
	    }
	    
		return businessRules;
	}

	public void setBusinessRules(List<BusinessRuleInfo> businessRules) {
		this.businessRules = businessRules;
	}

	public Boolean getExecutionLogging() {
		return executionLogging;
	}

	public void setExecutionLogging(Boolean executionLogging) {
		this.executionLogging = executionLogging;
	}
	
}
