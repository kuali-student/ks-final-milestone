package org.kuali.student.commons.ui.widgets.tables;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

/**
 * Callback to be invoked when an item within a ModelTable is selected
 * 
 * @param <T>
 *            the type of ModelObject
 */
public interface ModelTableSelectionListener<T extends ModelObject> {
    /**
     * Called when the selection changes
     * 
     * @param modelObject
     *            the newly selected ModelObject
     */
    public void onSelect(T modelObject);
}
