package org.kuali.student.lum.lu.ui.course.client.service;

import java.io.Serializable;
import java.util.List;

import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;


public class DataSaveResult<T extends Data> extends SaveResult<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	public DataSaveResult() {
		super();
	}
	public DataSaveResult(final List<ValidationResultInfo> validationResults, final T value) {
		super();
		super.setValidationResults(validationResults);
		super.setValue(value);
	}
}
