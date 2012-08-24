package org.kuali.student.common.ui.client.widgets.table.scroll;

@Deprecated
public class TableModelEvent {
	/** Identifies the addition of new rows or columns. */
	public static final int RowInsert = 1;
	/** Identifies a change to existing data. */
	public static final int RowUpdate = 2;
	/** Identifies the removal of rows or columns. */
	public static final int RowDelete = 3;
	public static final int CellUpdate = 4;
	
	public static final int TableStructure = 5;
	public static final int TableData = 6;

	protected int type;
	protected int firstRow;
	protected int lastRow;
	protected int column;

	public TableModelEvent(){

	}

	public int getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	public int getLastRow() {
		return lastRow;
	}

	public void setLastRow(int lastRow) {
		this.lastRow = lastRow;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
