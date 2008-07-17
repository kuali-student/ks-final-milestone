package org.kuali.student.commons.ui.widgets.tables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;
import org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;

/**
 * Table class that binds directly to a Model
 * Should use PagingModelTable instead
 * @param <T>
 *            the type of ModelObject
 */
@Deprecated
public class ModelTable<T extends ModelObject> extends Composite implements ModelWidget<T> {

    public enum ExposedStyles {
        MODELTABLE("KS-ModelTable"),
        MODELTABLE_COLUMN_HEADER("KS-ModelTable-Column-Header"),
        MODELTABLE_ROW("KS-ModelTable-Row"),
        MODELTABLE_ROW_ALTERNATE("KS-ModelTable-Row-Alternate");
        
        private String styleName;
        private ExposedStyles(String styleName) {
            this.styleName = styleName;
        }
        public String toString() {
            return this.styleName;
        }
    }
    
    private final FlexTable table = new FlexTable();
    
    private List<T> index = new ArrayList<T>();
    private List<ModelTableColumn<T>> columns = new ArrayList<ModelTableColumn<T>>();
    private List<ModelTableHeaderLabel> headers = new ArrayList<ModelTableHeaderLabel>();
    private Set<ModelTableSelectionListener<T>> listeners = new HashSet<ModelTableSelectionListener<T>>();
    private T selection = null;
    boolean loaded = false;

    private int sortColumn = 0;
    private boolean sortAscending = true;
    
    public ModelTable() {
        super.initWidget(table);
    }
    /**
     * Called by the container to initialize the table. Do not call directly.
     */
    public void onLoad() {
        if (!loaded) {
            loaded = true;
            table.addStyleName(ExposedStyles.MODELTABLE.toString());
            table.addTableListener(new TableListener() {
                public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
                    if (row == 0) {
                        if (cell == sortColumn) {
                            setSortColumn(cell, !sortAscending);
                        } else {
                            setSortColumn(cell, true);
                        }
                        
                    } else {
                        selection = index.get(row - 1);
                        fireSelection(selection);
                    }
                }
            });
            for (int col = 0; col < columns.size(); col++) {
                ModelTableHeaderLabel label = new ModelTableHeaderLabel(columns.get(col).getColumnHeader());
                headers.add(label);
                table.setWidget(0, col, label);
            }
            table.getRowFormatter().addStyleName(0, ExposedStyles.MODELTABLE_COLUMN_HEADER.toString());
        }
    }

    /**
     * Sets the column on which to sort.
     * 
     * @param sortColumn
     * @param sortAscending boolean indicating whether the sort should be ascending or descending
     */
    public void setSortColumn(final int sortColumn, final boolean sortAscending) {
        this.sortColumn = sortColumn;
        this.sortAscending = sortAscending;
        Comparator<T> c = columns.get(sortColumn).getColumnSortComparator();
        Collections.sort(index, c);
        if (sortAscending == false) {
            Collections.reverse(index);
        }
        for (int i=0; i<headers.size(); i++) {
            ModelTableHeaderLabel label = headers.get(i);
            if (i == sortColumn) {
                label.setSorted(sortAscending);
            } else {
                label.clearSorted();
            }
        }
        redraw();
    }
    /**
     * 
     * Returns the index of the column that the table is sorted on
     * 
     * @return the index of the column that the table is sorted on
     */
    public int getSortColumn() {
        return this.sortColumn;
    }
    /**
     * Returns true if the table is sorted in ascending order based on the sort column
     * 
     * @return true if the table is sorted in ascending order based on the sort column
     */
    public boolean isSortAscending() {
        return this.sortAscending;
    }
    /**
     * Adds a ModelTableSelectionListener to be fired when the selected item changes
     * 
     * @param listener
     *            the listener to add
     */
    public void addSelectionListener(ModelTableSelectionListener<T> listener) {
        listeners.add(listener);
    }

    /**
     * Removes a selection change listener
     * 
     * @param listener
     *            the listener to remove
     */
    public void removeSelectionListener(ModelTableSelectionListener<T> listener) {
        listeners.remove(listener);
    }

    /**
     * Adds a column descriptor to the table
     * 
     * @param column
     *            the descriptor to add
     */
    public void addColumn(ModelTableColumn<T> column) {
        columns.add(column);
    }

    /**
     * Returns the list of columns defined for the tabel
     * 
     * @return the list of columns
     */
    public List<ModelTableColumn<T>> getColumns() {
        return columns;
    }

    /**
     * 
     * Forces the table to redraw completely.
     *
     */
    public void redraw() {
        clearTable();
        for (T t : index) {
            render(t);
        }
    }
    /**
     * Directs the table to render/re-render the specified ModelObject
     * 
     * @param modelObject
     *            the ModelObject to render
     */
    protected void render(T modelObject) {
        // note, a bug in GWT-1.5-RC1 prevents redraw in hosted mode until another subsequent browser event fires
        // bug is fixed in nightly builds of GWT
        int row = index.indexOf(modelObject) + 1;
        ExposedStyles style = (row % 2 == 0) ? ExposedStyles.MODELTABLE_ROW_ALTERNATE : ExposedStyles.MODELTABLE_ROW;
        for (int col = 0; col < columns.size(); col++) {
            ModelTableColumn<T> c = columns.get(col);
            table.setText(row, col, c.getColumnValue(modelObject));
        }
        table.getRowFormatter().setStyleName(row, style.toString());
    }

    /**
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#add(org.kuali.student.commons.ui.mvc.client.model.ModelObject)
     */
    public void add(T modelObject) {
        index.add(modelObject);
        render(modelObject);
    }
    /**
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#addBulk(java.util.Collection)
     */
    public void addBulk(Collection<T> collection) {
		index.addAll(collection);
		redraw();
	}
    /**
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#getItems()
     */
    public List<T> getItems() {
        return new ArrayList<T>(index);
    }

    /**
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#getSelection()
     */
    public T getSelection() {
        return selection;
    }

    /**
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#remove(org.kuali.student.commons.ui.mvc.client.model.ModelObject)
     */
    public void remove(T modelObject) {
        int row = index.indexOf(modelObject) + 1;
        table.removeRow(row);
    }

    /**
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#select(org.kuali.student.commons.ui.mvc.client.model.ModelObject)
     */
    public void select(T modelObject) {
        selection = modelObject;
        fireSelection(modelObject);
    }

    /**
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#update(org.kuali.student.commons.ui.mvc.client.model.ModelObject)
     */
    public void update(T modelObject) {
        render(modelObject);
    }
    
    /**
     * 
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#clear()
     */
    public void clear() {
       this.index.clear();
       clearTable();
    }
    
    private void clearTable() {
        while (table.getRowCount() > 1) {
            table.removeRow(table.getRowCount()-1);
        } 
    }

    /**
     * Fires the selection change event for the specified ModelObject
     * 
     * @param modelObject
     *            the ModelObject that was selected
     */
    protected void fireSelection(T modelObject) {
        for (ModelTableSelectionListener<T> listener : listeners) {
            listener.onSelect(modelObject);
        }
    }
}
