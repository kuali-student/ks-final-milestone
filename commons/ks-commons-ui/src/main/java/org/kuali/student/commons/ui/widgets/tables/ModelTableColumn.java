package org.kuali.student.commons.ui.widgets.tables;

import java.util.Comparator;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

import com.google.gwt.user.client.ui.Widget;

/**
 * Interface defining a column in a ModelTable
 * 
 * @param <T>
 *            the type of ModelObject with which the table is associated
 */
public abstract class ModelTableColumn<T extends ModelObject> {
    /**
     * Returns the header text for the column
     * 
     * @return the header text for the column
     */
    public abstract String getColumnHeader();

    /**
     * Returns the column text value for the specified ModelObject
     * 
     * @param modelObject
     *            the ModelObject from which to generate the text value
     * @return the text value for the specified ModelObject
     */
    public abstract String getColumnValue(T modelObject);
    
    /**
     * 
     * Returns a Widget populated with the column value, if the column
     * should use a specific widget, otherwise returns null.
     * 
     * If this method returns null, then the table will use the text value
     * returned by getColumnValue
     * 
     * @param modelObject the object from which to populate the widget
     * @return Widget populated with the column value, if the column should use a specific widget, otherwise returns null
     */
    public abstract Widget getColumnWidget(T modelObject);
    
    /**
     * 
     * Returns a comparator used to sort the column.
     * Example usage are date columns, which may be displayed in a format that
     * does not work well with sorting.
     * 
     * Return null if the column is not sortable (e.g. images, etc)
     * 
     * 
     * @return a comparator used to sort the column
     */
    public abstract Comparator<T> getColumnSortComparator();
}
