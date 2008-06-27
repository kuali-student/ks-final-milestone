package org.kuali.student.commons.ui.mvc.client.widgets;

import java.util.List;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

public interface ModelWidget<T extends ModelObject> {
	public void add(T modelObject);
	
	public void remove(T modelObject);
	
	public void update(T modelObject);
	
	public void select(T modelObject);
	
	public List<T> getItems();
	
	public void clear();
	
	
	public T getSelection();
}
