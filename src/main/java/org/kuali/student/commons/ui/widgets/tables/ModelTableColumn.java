package org.kuali.student.commons.ui.widgets.tables;

import java.util.Comparator;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

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
     * Returns a comparator used to sort the column.
     * Example usage are date columns, which may be displayed in a format that
     * does not work well with sorting.
     * 
     * 
     * @return a comparator used to sort the column
     */
    public abstract Comparator<T> getColumnSortComparator();
}
