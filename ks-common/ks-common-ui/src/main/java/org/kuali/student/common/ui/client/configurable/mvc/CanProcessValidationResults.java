package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.List;

import org.kuali.student.common.ui.client.event.ValidateRequestEvent;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult.ErrorLevel;

public interface CanProcessValidationResults {

    public abstract ErrorLevel processValidationResults(FieldDescriptor fd, List<ValidationResultInfo> results);

    public abstract ErrorLevel processValidationResults(FieldDescriptor fd, List<ValidationResultInfo> results, boolean clearErrors);
    
    public boolean doesOnTheFlyValidation();
    
    public void Validate(ValidateRequestEvent event, List<ValidationResultInfo> result);

}