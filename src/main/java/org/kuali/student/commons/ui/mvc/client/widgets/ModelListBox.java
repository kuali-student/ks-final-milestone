package org.kuali.student.commons.ui.mvc.client.widgets;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

import com.google.gwt.user.client.ui.ListBox;

public abstract class ModelListBox<T extends ModelObject> extends ListBox implements ModelWidget<T> {
	private List<T> index = new ArrayList<T>();

	public abstract String getObjectLabel(T modelObject);
	
	/**
	 * Adds a blank item such as "please select" to the beginning of the list.
	 * Only works on the first call, subsequent calls simply return
	 * @param text
	 */
	public void addBlankItem(String text) {
		if (index.size() == 0 || index.get(0) != null) {
			super.insertItem(text, 0);
			index.add(0, null);
		}
	}
	
	public void add(T modelObject) {
		index.add(modelObject);
		super.addItem(getObjectLabel(modelObject));
	}
	
	public void remove(T modelObject) {
		int i = index.indexOf(modelObject);
		if (i != -1) {
			super.removeItem(i);
			index.remove(i);
		}
	}
	
	public void update(T modelObject) {
		int i = index.indexOf(modelObject);
		if (i != -1) {
			super.setItemText(i, getObjectLabel(modelObject));
		}
	}
	public void select(T modelObject) {
		super.setSelectedIndex(index.indexOf(modelObject));
	}

	public List<T> getItems() {
		return new ArrayList<T>(index);
	}
	
	public void clear() {
		index.clear();
		super.clear();
	}
	
	
	
	public T getSelection() {
		return index.get(super.getSelectedIndex());
	}
	
}
