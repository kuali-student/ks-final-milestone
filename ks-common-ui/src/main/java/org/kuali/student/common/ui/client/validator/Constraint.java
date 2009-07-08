package org.kuali.student.common.ui.client.validator;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.core.validation.dto.ValidationResultContainer;

@Deprecated
public interface Constraint {
	public void validate(Object value, Callback<ValidationResultContainer> callback);

	public void isRequired(Callback<Boolean> callback);
}
