package org.kuali.student.rules.rulemanagement.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class RuntimeAgendaDTO {

	@XmlElement
    private Boolean executionLogging = Boolean.FALSE;
    
	@XmlElement
	private List<BusinessRuleInfoDTO> businessRules;

	public List<BusinessRuleInfoDTO> getBusinessRules() {
		return businessRules;
	}

	public void setBusinessRules(List<BusinessRuleInfoDTO> businessRules) {
		this.businessRules = businessRules;
	}

	public Boolean getExecutionLogging() {
		return executionLogging;
	}

	public void setExecutionLogging(Boolean executionLogging) {
		this.executionLogging = executionLogging;
	}
	
}
