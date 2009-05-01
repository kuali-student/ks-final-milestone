package org.kuali.student.common.ui.client.configurable;

import org.kuali.student.core.dto.Idable;

import com.google.gwt.user.client.ui.Composite;

public abstract class ConfigurableLayout<T extends Idable> extends Composite {
	private T object = null;

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}
	
	public abstract ConfigurableLayout<T> addSection(String[] hierarchy, LayoutSection<T>section);
	public abstract void render();
	
}
