package org.kuali.student.rules.repository.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class RuleSetContainerDTO implements java.io.Serializable {

	/** Class serial version uid */
    private static final long serialVersionUID = 1L;

    @XmlElement
	private List<RuleSetDTO> ruleSetList = new ArrayList<RuleSetDTO>();

	public List<RuleSetDTO> getRuleSetList() {
		return this.ruleSetList;
	}

	public void addRuleSet(RuleSetDTO ruleSet) {
		this.ruleSetList.add(ruleSet);
	}

	public void setRuleSetList(List<RuleSetDTO> ruleSetList) {
		this.ruleSetList = ruleSetList;
	}
}
