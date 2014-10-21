package org.kuali.student.r1.common.dictionary.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.r2.common.infc.ValidationResult.ErrorLevel;

@Deprecated
@XmlAccessorType(XmlAccessType.FIELD)
public class RequiredConstraint {
	@XmlElement
    protected String fieldPath;
	@XmlElement
    protected String fieldPathMessageKey;	
	@XmlElement
    protected ErrorLevel errorLevel = ErrorLevel.ERROR;

	public String getFieldPath() {
		return fieldPath;
	}

	public void setFieldPath(String fieldPath) {
		this.fieldPath = fieldPath;
	}

	public ErrorLevel getErrorLevel() {
		return errorLevel;
	}

	public void setErrorLevel(ErrorLevel errorLevel) {
		this.errorLevel = errorLevel;
	}

	public String getFieldPathMessageId() {
		return fieldPathMessageKey;
	}

	public void setFieldPathMessageId(String fieldPathMessageKey) {
		this.fieldPathMessageKey = fieldPathMessageKey;
	}
		
}
