package org.kuali.student.common.ui.client.configurableui;

import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.core.dto.Idable;

public class PropertyField <T extends Idable, R extends Object> {
    private PropertyBinder<T, R> binding = null;
    private KSFormField formField = null;
    
    public PropertyBinder<T,R> getBinding() {
        return binding;
    }
    public PropertyField<T,R> setBinding(PropertyBinder<T,R> binding) {
        this.binding = binding;
        return this;
    }
    public KSFormField getFormField() {
        return formField;
    }
    public PropertyField<T,R> setFormField(KSFormField formField) {
        this.formField = formField;
        return this;
    }   
}
