package org.kuali.student.common.ui.client.configurable;

public interface PropertyBinding<T extends Object> {
	public Object getValue(T object);
	public void setValue(T object, Object value);
}
