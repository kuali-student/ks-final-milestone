package org.kuali.student.common.assembly.client;

import java.util.List;

import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class SaveResult<T> {
	private T value;
	private List<ValidationResultInfo> validationResults;
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	public List<ValidationResultInfo> getValidationResults() {
		return validationResults;
	}
	public void setValidationResults(List<ValidationResultInfo> validationResults) {
		this.validationResults = validationResults;
	}
	
}
