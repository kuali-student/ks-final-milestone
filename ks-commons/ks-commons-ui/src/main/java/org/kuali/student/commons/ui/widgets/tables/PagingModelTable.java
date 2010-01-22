package org.kuali.student.commons.ui.widgets.tables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.student.commons.ui.client.UICommonsConstants;
import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.model.ModelObject;
import org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Paging table class that binds directly to a Model
 * 
 * @param <T>
 *            the type of ModelObject
 */
public class PagingModelTable<T extends ModelObject> extends Composite implements ModelWidget<T> {
    public static final int DEFAULT_ROWS_PER_PAGE = 50;
    
    public enum ExposedStyles {
        MODELTABLE("KS-ModelTable"),
        MODELTABLE_COLUMN_HEADER("KS-ModelTable-Column-Header"),
        MODELTABLE_ROW("KS-ModelTable-Row"),
        MODELTABLE_ROW_ALTERNATE("KS-ModelTable-Row-Alternate"),
        MODELTABLE_ROW_SELECTED("KS-ModelTable-Row-Selected"),
        MODELTABLE_SORTIMAGE("KS-ModelTable-SortImage"),
        MODELTABLE_PAGE_BAR("KS-ModelTable-PageBar"),
        MODELTABLE_PAGE_BAR_NEXT("KS-ModelTable-PageBar-Next"),
        MODELTABLE_PAGE_BAR_PREVIOUS("KS-ModelTable-PageBar-Previous"),
        MODELTABLE_PAGE_BAR_PAGE_COUNT("KS-ModelTable-PageBar-Count");
        
        private String styleName;
        private ExposedStyles(String styleName) {
            this.styleName = styleName;
        }
        public String toString() {
            return this.styleName;
        }
    }
    
    ViewMetaData metadata = ApplicationContext.getViews().get(UICommonsConstants.BASE_VIEW_NAME);
    Messages messages = metadata.getMessages();
    
    private final VerticalPanel panel = new VerticalPanel();
    
    private final HorizontalPanel pageBar = new HorizontalPanel();
    private final Image previous = new Image("images/left-arrow.png");
    private final Image next = new Image("images/right-arrow.png");
    private final Label pageCount = new Label();
    
    private final FlexTable table = new FlexTable();
    
    private List<T> index = new ArrayList<T>();
    private List<ModelTableColumn<T>> columns = new ArrayList<ModelTableColumn<T>>();
    private List<ModelTableHeaderLabel> headers = new ArrayList<ModelTableHeaderLabel>();
    private Set<ModelTableSelectionListener<T>> listeners = new HashSet<ModelTableSelectionListener<T>>();
    private T selection = null;
    boolean loaded = false;

    private int sortColumn = -1;
    private boolean sortAscending = true;
    
    private int rowsPerPage = DEFAULT_ROWS_PER_PAGE;
    private int currentPage = 0;
    
    public PagingModelTable() {
        panel.add(pageBar);
        pageBar.add(previous);
        pageBar.add(pageCount);
        pageBar.add(next);
        panel.add(table);
        super.initWidget(panel);
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
                        if (columns.get(cell).getColumnSortComparator() != null) {
                            if (cell == sortColumn) {
                                setSortColumn(cell, !sortAscending);
                            } else {
                                setSortColumn(cell, true);
                            }
                        }
                    } else {
                        select(index.get((currentPage * rowsPerPage) + row - 1));
                    }
                }
            });
            for (int col = 0; col < columns.size(); col++) {
                ModelTableHeaderLabel label = new ModelTableHeaderLabel(columns.get(col).getColumnHeader());
                headers.add(label);
                table.setWidget(0, col, label);
            }
            table.getRowFormatter().addStyleName(0, ExposedStyles.MODELTABLE_COLUMN_HEADER.toString());
            previous.setTitle(messages.get("previous"));
            previous.addClickListener(new ClickListener() {
                public void onClick(Widget sender) {
                    if (getCurrentPage() > 0) {
                        setCurrentPage(getCurrentPage()-1);
                    }
                }
            });
            next.setTitle(messages.get("next"));
            next.addClickListener(new ClickListener() {
                public void onClick(Widget sender) {
                    int i = (getCurrentPage()+1) * rowsPerPage;
                    if (i < index.size()) {
                        setCurrentPage(getCurrentPage()+1);
                    }
                }
            });
            
            pageBar.addStyleName(ExposedStyles.MODELTABLE_PAGE_BAR.toString());
            previous.addStyleName(ExposedStyles.MODELTABLE_PAGE_BAR_PREVIOUS.toString());
            next.addStyleName(ExposedStyles.MODELTABLE_PAGE_BAR_NEXT.toString());
            pageCount.addStyleName(ExposedStyles.MODELTABLE_PAGE_BAR_PAGE_COUNT.toString());
            
            recalcPageCountLabel();
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
        reSort();
    }
    
    protected void reSort() {
        if (sortColumn != -1) {
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
        recalcPageCountLabel();

        int offset = currentPage * rowsPerPage;
        for (int i=offset; i<offset+rowsPerPage && i<index.size(); i++) {
            render(index.get(i));
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
        if (isObjectVisible(modelObject)) {
            int row = getSelectionRow(modelObject);
            ExposedStyles style;
            if (selection != null && modelObject.getUniqueId().equals(selection.getUniqueId())) {
                style = ExposedStyles.MODELTABLE_ROW_SELECTED;
            } else if (row % 2 == 0) {
                style = ExposedStyles.MODELTABLE_ROW_ALTERNATE;
            } else {
                style= ExposedStyles.MODELTABLE_ROW;
            }
            for (int col = 0; col < columns.size(); col++) {
                ModelTableColumn<T> c = columns.get(col);
                Widget w = c.getColumnWidget(modelObject);
                if (w == null) {
                    table.setText(row, col, c.getColumnValue(modelObject)); 
                } else {
                    table.setWidget(row, col, w);
                }
                
            }
            table.getRowFormatter().setStyleName(row, style.toString());
        }
    }

    /**
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#add(org.kuali.student.commons.ui.mvc.client.model.ModelObject)
     */
    public void add(T modelObject) {
        index.add(modelObject);
        reSort();
        render(modelObject);
        recalcPageCountLabel();
    }
    
    /**
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#addBulk(java.util.Collection)
     */
    public void addBulk(Collection<T> collection) {
		index.addAll(collection);
		reSort();
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
        if (isObjectVisible(modelObject)) {
            int row = getSelectionRow(modelObject);
            table.removeRow(row);
        }
        // trying a hack to work around a new instance coming back from server with the same unique id
        // if this works, then need to readdress how indices are updated inside modelwidgets
        for (T t : index) {
            if (t.getUniqueId().equals(modelObject.getUniqueId())) {
                index.remove(t);
                break;
            }
        }
        if (selection != null && selection.getUniqueId().equals(modelObject.getUniqueId())) {
            select(null);
        }
        reSort();
        redraw();
        recalcPageCountLabel();
    }

    public boolean isObjectVisible(T modelObject) {
        int row = index.indexOf(modelObject);
        int offset = currentPage * rowsPerPage;
        return (row >= offset && row < (offset + rowsPerPage));
    }
    
    /**
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#select(org.kuali.student.commons.ui.mvc.client.model.ModelObject)
     */
    public void select(T modelObject) {
        selection = modelObject;
        redraw();
        fireSelection(modelObject);
    }

    /**
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#update(org.kuali.student.commons.ui.mvc.client.model.ModelObject)
     */
    public void update(T modelObject) {
        // trying a hack to work around a new instance coming back from server with the same unique id
        // if this works, then need to readdress how indices are updated inside modelwidgets
        for (T t : index) {
            if (t.getUniqueId().equals(modelObject.getUniqueId())) {
                index.remove(t);
                index.add(modelObject);
                break;
            }
        }
        reSort();
        redraw();
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
    
    private int getSelectionRow(T modelObject) {
        if (isObjectVisible(modelObject)) { 
            return (index.indexOf(modelObject) % rowsPerPage) + 1;
        } else {
            return -1;
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

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        redraw();
    }
    
    private int calculateNumberOfPages() {
        return (int) Math.ceil((double)index.size() / (double) rowsPerPage);
    }
    private String getPageCountText() {
        String x = "" + (currentPage + 1);
        String y = "" + calculateNumberOfPages();
        return messages.get("pageXofY", x, y);
    }
    
    private void recalcPageCountLabel() {
        pageCount.setText(getPageCountText());
    }
}
