package org.kuali.student.commons.ui.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;
import org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget;

import com.google.gwt.user.client.ui.ListBox;

/**
 * ListBox that can be bound directly to a Model
 * 
 * @param <T>
 *            the type of ModelObject
 */
public abstract class ModelListBox<T extends ModelObject> extends ListBox implements ModelWidget<T> {
    private List<T> index = new ArrayList<T>();

    /**
     * To be implemented by subclasses. Returns the String label to be used for the ModelObject in the ListBox
     * 
     * @param modelObject
     *            the ModelObject for which to generate a label
     * @return the label for the specified ModelObject
     */
    public abstract String getObjectLabel(T modelObject);

    /**
     * Adds a blank item such as "please select" to the beginning of the list. Only works on the first call, subsequent calls
     * simply return
     * 
     * @param text
     *            the text to use for the blank item
     */
    public void addBlankItem(String text) {
        if ((index.size() == 0) || (index.get(0) != null)) {
            super.insertItem(text, 0);
            index.add(0, null);
        }
    }

    /**
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#add(org.kuali.student.commons.ui.mvc.client.model.ModelObject)
     */
    public void add(T modelObject) {
        index.add(modelObject);
        super.addItem(getObjectLabel(modelObject));
    }

    /**
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#remove(org.kuali.student.commons.ui.mvc.client.model.ModelObject)
     */
    public void remove(T modelObject) {
        int i = index.indexOf(modelObject);
        if (i != -1) {
            super.removeItem(i);
            index.remove(i);
        }
    }

    /**
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#update(org.kuali.student.commons.ui.mvc.client.model.ModelObject)
     */
    public void update(T modelObject) {
        int i = index.indexOf(modelObject);
        if (i != -1) {
            super.setItemText(i, getObjectLabel(modelObject));
        }
    }

    /**
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#select(org.kuali.student.commons.ui.mvc.client.model.ModelObject)
     */
    public void select(T modelObject) {
        super.setSelectedIndex(index.indexOf(modelObject));
    }

    /**
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#getItems()
     */
    public List<T> getItems() {
        return new ArrayList<T>(index);
    }

    /**
     * Called by the ModelBinding to signal a reset. Clears the ListBox and any underlying index information.
     */
    public void clear() {
        index.clear();
        super.clear();
    }

    /**
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#getSelection()
     */
    public T getSelection() {
        return index.get(super.getSelectedIndex());
    }

	public void addBulk(Collection<T> collection) {
		for (T t : collection) {
			add(t);
		}
	}

}
