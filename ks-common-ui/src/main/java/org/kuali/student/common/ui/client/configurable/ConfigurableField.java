package org.kuali.student.common.ui.client.configurable;

import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.core.dto.Idable;

@Deprecated
public class ConfigurableField<T extends Idable> {
	private PropertyBinding<T> binding = null;
	private KSFormField formField = null;
	
	public PropertyBinding<T> getBinding() {
		return binding;
	}
	public ConfigurableField<T> setBinding(PropertyBinding<T> binding) {
		this.binding = binding;
		return this;
	}
	public KSFormField getFormField() {
		return formField;
	}
	public ConfigurableField<T> setFormField(KSFormField formField) {
		this.formField = formField;
		return this;
	}	
}
