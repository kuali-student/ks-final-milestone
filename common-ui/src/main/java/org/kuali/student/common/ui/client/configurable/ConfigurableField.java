package org.kuali.student.common.ui.client.configurable;

import org.kuali.student.common.ui.client.widgets.forms.KSFormField;

public class ConfigurableField<T extends Object> {
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
