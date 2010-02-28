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
