package org.kuali.student.common.ui.client.widgets.table.scroll;

public interface TableModel {
	public int getRowCount() ;
	public void sort(Column column) ;

	public Row getRow(int rowIndex) ;
	public Column getColumn(int columnIndex);
	public int getColumnCount();
	
	public boolean isMultipleSelectable();

    void setCurrentIndex(int index);
    int getCurrentIndex();
}