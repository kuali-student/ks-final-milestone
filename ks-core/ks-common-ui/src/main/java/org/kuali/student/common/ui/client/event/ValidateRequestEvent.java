/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.common.ui.client.event;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.mvc.ApplicationEvent;
import org.kuali.student.common.ui.client.mvc.UncheckedApplicationEvent;

public class ValidateRequestEvent extends ApplicationEvent<ValidateRequestHandler> {
    public static final Type<ValidateRequestHandler> TYPE = new Type<ValidateRequestHandler>();
    private FieldDescriptor fieldDescriptor;
    private boolean validateSingleField = false;

    @Override
    protected void dispatch(ValidateRequestHandler handler) {
        handler.onValidateRequest(this);
    }

    @Override
    public Type<ValidateRequestHandler> getAssociatedType() {
        return TYPE;
    }

	public FieldDescriptor getFieldDescriptor() {
		return fieldDescriptor;
	}

	public void setFieldDescriptor(FieldDescriptor fieldDescriptor) {
		this.fieldDescriptor = fieldDescriptor;
	}

	public boolean validateSingleField() {
		return validateSingleField;
	}

	public void setValidateSingleField(boolean validateSingleField) {
		this.validateSingleField = validateSingleField;
	}
}
