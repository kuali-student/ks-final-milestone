package org.kuali.student.core.organization.ui.client.service;

import java.io.Serializable;
import java.util.List;

import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;


public class DataSaveResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<ValidationResultInfo> validationResults;
	private Data value;
	
	public DataSaveResult() {
		super();
	}
	public DataSaveResult(final List<ValidationResultInfo> validationResults, final Data value) {
		super();
		setValidationResults(validationResults);
		setValue(value);
	}
	public List<ValidationResultInfo> getValidationResults() {
		return this.validationResults;
	}
	public Data getValue() {
		return this.value;
	}
	public void setValidationResults(
			List<ValidationResultInfo> validationResults) {
		this.validationResults = validationResults;
	}
	public void setValue(Data value) {
		this.value = value;
	}
	
}
