package org.kuali.student.common.ui.client.widgets.field.layout.element;

import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult.ErrorLevel;

public interface ValidationProcessable {
	public ErrorLevel processValidationResult(ValidationResultInfo vr);

	public ErrorLevel processValidationResult(ValidationResultInfo vr, String fieldName);

	public boolean shouldProcessValidationResult(ValidationResultInfo vr);
	
	public void clearValidationWarnings();
	
	public void clearValidationErrors();
}
