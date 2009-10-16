/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
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
