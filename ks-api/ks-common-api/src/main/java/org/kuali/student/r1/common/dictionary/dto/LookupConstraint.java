package org.kuali.student.r1.common.dictionary.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.r2.common.infc.ValidationResult.ErrorLevel;

@Deprecated
@XmlAccessorType(XmlAccessType.FIELD)
public class LookupConstraint extends CommonLookup{

	private static final long serialVersionUID = 1L;

	@XmlElement
	protected ErrorLevel errorLevel = ErrorLevel.ERROR;

	public ErrorLevel getErrorLevel() {
		return errorLevel;
	}

	public void setErrorLevel(ErrorLevel errorLevel) {
		this.errorLevel = errorLevel;
	}
		
}