package org.kuali.student.core.dictionary.poc.dto;

public class WhenConstraint {
	protected Object value;
	protected String valuePath;
	protected Constraint constraint;
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getValuePath() {
		return valuePath;
	}
	public void setValuePath(String valuePath) {
		this.valuePath = valuePath;
	}
	public Constraint getConstraint() {
		return constraint;
	}
	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
	}
}
