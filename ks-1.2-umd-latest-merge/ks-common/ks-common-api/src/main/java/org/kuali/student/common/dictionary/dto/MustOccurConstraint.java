package org.kuali.student.common.dictionary.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.common.validation.dto.ValidationResultInfo.ErrorLevel;

@XmlAccessorType(XmlAccessType.FIELD)
public class MustOccurConstraint {
    @XmlElement
    private List<RequiredConstraint> requiredFields;
	@XmlElement
    private List<MustOccurConstraint> occurs;
	@XmlElement
	private Integer min;
	@XmlElement
	private Integer max;
	@XmlElement
	private ErrorLevel errorLevel = ErrorLevel.ERROR;

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

	public ErrorLevel getErrorLevel() {
		return errorLevel;
	}

	public void setErrorLevel(ErrorLevel errorLevel) {
		this.errorLevel = errorLevel;
	}	
}
