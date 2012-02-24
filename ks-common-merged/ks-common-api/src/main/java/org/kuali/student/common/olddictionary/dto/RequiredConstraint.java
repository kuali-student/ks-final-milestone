package org.kuali.student.common.olddictionary.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

// TODO KSCM I am being used

@Deprecated
@XmlAccessorType(XmlAccessType.FIELD)
public class RequiredConstraint {
	@XmlElement
    protected String fieldPath;

	public String getFieldPath() {
		return fieldPath;
	}

	public void setFieldPath(String fieldPath) {
		this.fieldPath = fieldPath;
	}
}
