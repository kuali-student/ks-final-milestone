package org.kuali.student.commons.ui.mvc.client.widgets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;

/**
 * Table class that binds directly to a Model
 *
 * @param <T> the type of ModelObject
 */
public class ModelTable<T extends ModelObject> extends FlexTable implements ModelWidget<T> {
	private List<T> index = new ArrayList<T>();
	private List<ModelTableColumn<T>> columns = new ArrayList<ModelTableColumn<T>>();
	private Set<ModelTableSelectionListener<T>> listeners = new HashSet<ModelTableSelectionListener<T>>();
	private T selection = null;
	boolean loaded = false;
	
	/**
	 * Called by the container to initialize the table.  Do not call directly.
	 */
	public void onLoad() {
		if (!loaded) {
			loaded = true;
			super.addTableListener(new TableListener() {
				public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
					if (row > 0) {
						selection = index.get(row-1);
						fireSelection(selection);
					}
				}
			});
			for (int col=0; col<columns.size(); col++) {
				super.setText(0, col, columns.get(col).getColumnHeader());
			}
		}
	}
	/**
	 * Adds a ModelTableSelectionListener to be fired when the selected item changes
	 * @param listener the listener to add
	 */
	public void addSelectionListener(ModelTableSelectionListener<T> listener) {
		listeners.add(listener);
	}
	
	/**
	 * Removes a selection change listener
	 * @param listener the listener to remove
	 */
	public void removeSelectionListener(ModelTableSelectionListener<T> listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Adds a column descriptor to the table
	 * @param column the descriptor to add
	 */
	public void addColumn(ModelTableColumn<T> column) {
		columns.add(column);
	}
	/**
	 * Returns the list of columns defined for the tabel
	 * @return the list of columns
	 */
	public List<ModelTableColumn<T>> getColumns() {
		return columns;
	}
	
	/**
	 * Directs the table to render/re-render the specified ModelObject
	 * @param modelObject the ModelObject to render
	 */
	protected void render(T modelObject) {
		// note, a bug in GWT-1.5-RC1 prevents redraw in hosted mode until another subsequent browser event fires
		// bug is fixed in nightly builds of GWT
		int row = index.indexOf(modelObject) + 1;
		for (int col=0; col<columns.size(); col++) {
			ModelTableColumn<T> c = columns.get(col);
			super.setText(row, col, c.getColumnValue(modelObject));
		}
	}
	
	/**
	 * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#add(org.kuali.student.commons.ui.mvc.client.model.ModelObject)
	 */
	public void add(T modelObject) {
		index.add(modelObject);
		render(modelObject);
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
		super.removeRow(row);
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
	 * Fires the selection change event for the specified ModelObject
	 * @param modelObject the ModelObject that was selected
	 */
	protected void fireSelection(T modelObject) {
		for (ModelTableSelectionListener<T> listener : listeners) {
			listener.onSelect(modelObject);
		}
	}
}
