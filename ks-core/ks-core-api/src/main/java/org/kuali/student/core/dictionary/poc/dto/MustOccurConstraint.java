package org.kuali.student.core.dictionary.poc.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class MustOccurConstraint {
	private List<RequiredConstraint> requiredFields;
	private List<MustOccurConstraint> occurs;
	private Integer min;
	private Integer max;

	public List<RequiredConstraint> getRequiredFields() {
		return requiredFields;
	}

	public void setRequiredFields(List<RequiredConstraint> requiredFields) {
		this.requiredFields = requiredFields;
	}

	public List<MustOccurConstraint> getOccurs() {
		return occurs;
	}

	public void setOccurs(List<MustOccurConstraint> occurs) {
		this.occurs = occurs;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}
}
