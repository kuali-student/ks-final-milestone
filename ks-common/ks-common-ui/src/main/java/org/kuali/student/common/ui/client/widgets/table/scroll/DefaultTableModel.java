package org.kuali.student.common.ui.client.widgets.table.scroll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.CheckBox;

public class DefaultTableModel extends AbstractTableModel {
	private ArrayList<Column> columnList = new ArrayList<Column>();
	private ArrayList<Row> rowList = new ArrayList<Row>();
	private ArrayList<Column> sortedColumnList = new ArrayList<Column>();
	private int sortableColumnCount = 1;
	private boolean moreData = true;
	private boolean multipleSelectable = true;
	
	Column rowHeader = new Column();
    public void installCheckBoxRowHeaderColumn(){
        columnList.remove(rowHeader);	
        
		rowHeader.setId("RowHeader");
		rowHeader.setName("RowHeader");
		final CheckBox checkBox = new CheckBox();
	    DOM.setStyleAttribute(checkBox.getElement(), "style", "padding-right: 0.8em");		
		checkBox.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				int count = getRowCount();
				for(int i=0;i<count;i++){
					getRow(i).setSelected(checkBox.getValue());
				}
				fireTableDataChanged();	
			}
		});
		rowHeader.setColumnTitleWidget(checkBox);
		rowHeader.setWidth("40px");
		rowHeader.setVisible(true);
		
		columnList.add(0,rowHeader);
    }
	public void addRow(Row row){
		rowList.add(row);
	}
	public void insertRow(int index,Row row){
		rowList.add(index,row);
	}
	
	public void addColumn(Column col){
		columnList.add(col);
	}
	public void insertColumn(int index, Column col){
		columnList.add(index, col);
	}
	public boolean isMultipleSelectable(){
	    return multipleSelectable;	    
	}
    public void setMultipleSelectable(boolean value){
        multipleSelectable = value;      
    }
	public void setMoreData(boolean hasMoreData){
		moreData	 = hasMoreData;
	}
	public boolean getMoreData(){
	  return moreData;	
	}

	public int getRowCount() {
		return rowList.size();
	}

	public void sort(Column column) {
		if(column.isSortable() == false){
			return;
		}
		if(sortedColumnList.contains(column) == false){
			if(sortedColumnList.size()>=sortableColumnCount){
				Column removed = sortedColumnList.remove(0);
				removed.setSortDirection(Column.NotOrdered);
			}
			sortedColumnList.add(column);
			
		}
		
		column.changeSortDirection();
		RowComparator comparator = column.getComparator();
		if(comparator != null){
			Collections.sort(rowList,comparator);
		}
		super.fireTableStructureChanged();
	}

	public Row getRow(int rowIndex) {
		return rowList.get(rowIndex);
	}
	
    public List<Row> getSelectedRows() {
        ArrayList<Row> selectedRows = new ArrayList<Row>();
        for (Row row: rowList) {
            if (row.isSelected()) {
                selectedRows.add(row);
            }
        }
        return selectedRows;
    }	
	
	public Column getColumn(int columnIndex) {
		ArrayList<Column> list = new ArrayList<Column>();
		for(Column col:columnList){
			if(col.isVisible()){
				list.add(col);
			}	
		}
		return list.get(columnIndex);		
	}
	public int getColumnCount(){
		int count = 0;
		for(Column col:columnList){
			if(col.isVisible()){
				count++;
			}	
		}
		return count;
	}

	public void clearRows(){
	     rowList = new ArrayList<Row>();
	     super.fireTableDataChanged();
	 }	
}
