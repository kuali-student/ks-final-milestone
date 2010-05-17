package org.kuali.student.core.dictionary.poc.dto;

public class ValidCharsConstraint extends BaseConstraint {
    protected String value; //(regex or list of valid chars)

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}