package org.kuali.student.commons.ui.widgets.trees;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

public interface TreeMouseOverListener<T extends ModelObject> {
    /**
     * Called when mouse cursor is positioned above a tree item
     * @param item
     * @param x
     * @param y
     */
    void onTreeItemMouseOver(T item, int x, int y);
    
    /**
     * Called when mouse cursor has left a tree item
     * @param item
     * @param x
     * @param y
     */
    void onMouseOut();

}
