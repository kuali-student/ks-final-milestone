package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.List;

import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

public interface CanProcessValidationResults {

    public abstract ErrorLevel processValidationResults(FieldDescriptor fd, List<ValidationResultInfo> results);

    public abstract ErrorLevel processValidationResults(FieldDescriptor fd, List<ValidationResultInfo> results, boolean clearAllValidation);

}