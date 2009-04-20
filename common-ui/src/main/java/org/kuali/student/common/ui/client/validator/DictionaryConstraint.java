package org.kuali.student.common.ui.client.validator;

import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.validation.dto.ValidationResult;

public class DictionaryConstraint implements Constraint {
    private final Validator validator;
    private final FieldDescriptor descriptor;
    private final Map<String, Object> descriptorMap;
    
    public DictionaryConstraint(Validator validator, FieldDescriptor descriptor) {
        this.validator = validator;
        this.descriptor = descriptor;
        this.descriptorMap = descriptor.toMap();
    }
    
    public void isRequired(Callback<Boolean> callback) {
        callback.exec(descriptor.getMinOccurs() != null && descriptor.getMinOccurs() > 0);
    }
    public void validate(Object value, Callback<ValidationResult> callback) {
        callback.exec(validator.validate(descriptor.getName(), value, descriptorMap));
    }
}
