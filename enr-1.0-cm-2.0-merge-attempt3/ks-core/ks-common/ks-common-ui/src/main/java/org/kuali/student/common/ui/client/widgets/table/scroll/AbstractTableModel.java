package org.kuali.student.common.ui.client.widgets.table.scroll;

import java.util.ArrayList;

public abstract class AbstractTableModel implements TableModel{
	ArrayList<TableModelListener> tableModelListenerList = new ArrayList<TableModelListener>();

	public AbstractTableModel(){

	}
	

	public void addTableModelListener(TableModelListener lis) {
		tableModelListenerList.add(lis);
	}

	public void removeTableModelListener(TableModelListener l) {
		tableModelListenerList.remove(l);
	}

	public void fireTableStructureChanged() {
		TableModelEvent event = new TableModelEvent();
		event.setType(TableModelEvent.TableStructure);
		fireTableChanged(event);
	}

	public void fireTableDataChanged() {
		TableModelEvent event = new TableModelEvent();
		event.setType(TableModelEvent.TableData);
		fireTableChanged(event);
	}

	public void fireTableRowsDeleted(int firstRow, int lastRow) {
		TableModelEvent event = new TableModelEvent();
		event.setFirstRow(firstRow);
		event.setLastRow(lastRow);
		event.setType(TableModelEvent.RowDelete);
		fireTableChanged(event);
	}

	public void fireTableRowsUpdated(int firstRow, int lastRow) {
		TableModelEvent event = new TableModelEvent();
		event.setFirstRow(firstRow);
		event.setLastRow(lastRow);
		event.setType(TableModelEvent.RowUpdate);
		fireTableChanged(event);
	}

	public void fireTableRowsInsert(int firstRow, int lastRow) {
		TableModelEvent event = new TableModelEvent();
		event.setFirstRow(firstRow);
		event.setLastRow(lastRow);
		event.setType(TableModelEvent.RowInsert);
		fireTableChanged(event);
	}

	public void fireTableCellUpdated(int row, int column) {
		TableModelEvent event = new TableModelEvent();
		event.setColumn(column);
		event.setFirstRow(row);
		event.setLastRow(row);
		event.setType(TableModelEvent.CellUpdate);
		fireTableChanged(event);
	}

	public void fireTableChanged(TableModelEvent e) {
		for (TableModelListener lis : tableModelListenerList) {
			lis.tableChanged(e);
		}
	}

}
