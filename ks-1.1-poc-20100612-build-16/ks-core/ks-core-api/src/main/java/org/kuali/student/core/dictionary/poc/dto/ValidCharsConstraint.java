package org.kuali.student.core.dictionary.poc.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ValidCharsConstraint extends BaseConstraint {
	protected String value; // (regex or list of valid chars)

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}