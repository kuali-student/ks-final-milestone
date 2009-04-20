package org.kuali.student.common.ui.validator;


import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.core.validation.dto.ValidationResult;

public interface Constraint {
    public void validate(Object value, Callback<ValidationResult> callback);
    public void isRequired(Callback<Boolean> callback);
}
