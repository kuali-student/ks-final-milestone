package org.kuali.student.core.entity;

import java.util.List;

public interface AttributeOwner<T extends Attribute<?>> {
	public void setAttributes(List<T> attributes);
	public List<T> getAttributes();
}
