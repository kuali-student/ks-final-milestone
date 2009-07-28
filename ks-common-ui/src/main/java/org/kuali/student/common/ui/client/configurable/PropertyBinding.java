package org.kuali.student.common.ui.client.configurable;

import org.kuali.student.core.dto.Idable;

@Deprecated
public interface PropertyBinding<T extends Idable> {
	public Object getValue(T object);
	public void setValue(T object, Object value);
}
