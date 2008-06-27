package org.kuali.student.commons.ui.mvc.client.widgets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;

public class ModelTable<T extends ModelObject> extends FlexTable implements ModelWidget<T> {
	private List<T> index = new ArrayList<T>();
	private List<ModelTableColumn<T>> columns = new ArrayList<ModelTableColumn<T>>();
	private Set<ModelTableSelectionListener<T>> listeners = new HashSet<ModelTableSelectionListener<T>>();
	private T selection = null;
	boolean loaded = false;
	
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
	public void addSelectionListener(ModelTableSelectionListener<T> listener) {
		listeners.add(listener);
	}
	
	public void removeSelectionListener(ModelTableSelectionListener<T> listener) {
		listeners.remove(listener);
	}
	
	public void addColumn(ModelTableColumn<T> column) {
		columns.add(column);
	}
	public List<ModelTableColumn<T>> getColumns() {
		return columns;
	}
	
	protected void render(T modelObject) {
		// TODO figure out why this doesn't redraw automatically in hosted mode
		int row = index.indexOf(modelObject) + 1;
		for (int col=0; col<columns.size(); col++) {
			ModelTableColumn<T> c = columns.get(col);
			super.setText(row, col, c.getColumnValue(modelObject));
		}
	}
	
	public void add(T modelObject) {
		index.add(modelObject);
		render(modelObject);
	}

	public List<T> getItems() {
		return new ArrayList<T>(index);
	}

	public T getSelection() {
		return selection;
	}

	public void remove(T modelObject) {
		int row = index.indexOf(modelObject) + 1;
		super.removeRow(row);
	}

	
	public void select(T modelObject) {
		selection = modelObject;
		fireSelection(modelObject);
	}
	

	public void update(T modelObject) {
		render(modelObject);
	}

	protected void fireSelection(T modelObject) {
		for (ModelTableSelectionListener<T> listener : listeners) {
			listener.onSelect(modelObject);
		}
	}
}
