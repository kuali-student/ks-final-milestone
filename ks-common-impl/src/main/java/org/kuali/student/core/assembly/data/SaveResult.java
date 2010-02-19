package org.kuali.student.core.assembly.data;

import java.io.Serializable;
import java.util.List;

import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class SaveResult<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	
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
	
	public void addValidationResults(List<ValidationResultInfo> validationResults){
		if (this.validationResults == null){
			setValidationResults(this.validationResults);
		} else{
			this.validationResults.addAll(validationResults);
		}
	}
	
}
