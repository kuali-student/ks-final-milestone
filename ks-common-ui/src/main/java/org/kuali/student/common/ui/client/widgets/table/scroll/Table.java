package org.kuali.student.common.ui.client.widgets.table.scroll;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
/**
 * A table with UiBinder.
 * 
 * Steps to create a table.
 * <BR>
 * 1) 
 * */
public class Table extends Composite {

	private static TableUiBinder uiBinder = GWT.create(TableUiBinder.class);

	interface TableUiBinder extends UiBinder<Widget, Table> {
	}

	interface SelectionStyle extends CssResource {
		String selectedRow();
		String columnAscending();
		String columnDescending();
	}

	@UiField
	FlexTable header;
	@UiField
	FlexTable table;
	@UiField
	SelectionStyle selectionStyle;
	@UiField
	ScrollPanel scrollPanel;

	private TableModel tableModel;

	public Table() {
		initWidget(uiBinder.createAndBindUi(this));
	}
    public FlexTable getHeader(){
        return header;
    }
    public FlexTable getContent(){
        return table;
    }
	public ScrollPanel getScrollPanel() {
		return scrollPanel;
	}

	public void setTableModel(TableModel m) {
		tableModel = m;
		if(m instanceof AbstractTableModel){
			((AbstractTableModel)tableModel).addTableModelListener(new TableModelListener() {
				@Override
				public void tableChanged(TableModelEvent e) {
					updateTable(e);
				}
			});
			((AbstractTableModel)tableModel).fireTableStructureChanged();
		}
	}
	@UiHandler("table")
	void onTableClicked(ClickEvent event) {
		Cell cell = table.getCellForEvent(event);

		if (cell == null) {
			return;
		}
		int cellIndex = cell.getCellIndex();
		if(cellIndex == 0){
			return;
		}
		int rowIndex = cell.getRowIndex();
		Row row = tableModel.getRow(rowIndex);
		
		row.setSelected(!row.isSelected());
    	updateTableSelection();
    	updateTableCell(rowIndex, 0);
	}

	@UiHandler("header")
	void onTableHeaderClicked(ClickEvent event) {
		Cell cell = header.getCellForEvent(event);
		if (cell != null) {
			Column col = tableModel.getColumn(cell.getCellIndex());
			tableModel.sort(col);
		}
	}

	private void onTableClicked(int row, String columnId,
			TableCellWidget cellWidget) {
		onTableCellChanged(row, columnId, cellWidget);
	}

	private void onTableCellChanged(int rowIndex, String columnId,
			TableCellWidget cellWidget) {
		Row row =tableModel.getRow(rowIndex); 
		if ("RowHeader".equals(columnId)) {
			row.setSelected(!row.isSelected());
			updateTableSelection();
		}
		row.setCellData(columnId,
				cellWidget.getCellEditorValue());
	}

	private void updateTableSelection() {
		String style = selectionStyle.selectedRow();
		int count = tableModel.getRowCount();
		for (int i = 0; i < count; i++) {
			table.getRowFormatter().removeStyleName(i, style);
		}
		for (int r = 0; r < count; r++) {
			if (tableModel.getRow(r).isSelected()) {
				table.getRowFormatter().addStyleName(r, style);
			}
		}
	}

	public void updateTable(TableModelEvent event) {
		if (event.getType() == TableModelEvent.TableStructure) {
			updateTableStructure();
			updateTableData();
		} else if (event.getType() == TableModelEvent.TableData) {
			updateTableData();
		} else if (event.getType() == TableModelEvent.CellUpdate) {
			updateTableCell(event.getFirstRow(), event.getColumn());
		}
	}

	private void updateTableData() {
		for (int r = 0; r < tableModel.getRowCount(); r++) {
			int columnCount = tableModel.getColumnCount();
			for (int c = 0; c < columnCount; c++) {
				updateTableCell(r, c);
			}
		}
		updateTableSelection();
	}

	private void updateTableCell(final int r, final int c) {
		int columnCount = tableModel.getColumnCount();
		for (int i = 0; i < columnCount - 1; i++) {
			Column col = tableModel.getColumn(i);
			table.getColumnFormatter().setWidth(i, col.getWidth());
		}
		final String columnId = tableModel.getColumn(c).getId();
		Row row = tableModel.getRow(r );
		Object v = null;
		if("RowHeader".equals(columnId)){
		  v = row.isSelected() ;	
		}else{
			v = row.getCellData(columnId);			
		}
		if(v == null){
			v = "";
		}
		if("RowHeader".equals(columnId) == false){
		//if(row.isCellEditable(columnId)==false){
			table.setText(r, c, v.toString());
			return;
		}
		final TableCellWidget widget = new TableCellWidget(v);
		widget.setCellEditorValue(v);
		if (widget instanceof HasClickHandlers) {
			((HasClickHandlers) widget)
					.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							onTableClicked(r, columnId, widget);
						}
					});
		}
		if (widget instanceof HasChangeHandlers) {
			((HasChangeHandlers) widget)
					.addChangeHandler(new ChangeHandler() {
						@Override
						public void onChange(ChangeEvent event) {
							onTableCellChanged(r, columnId, widget);

						}
					});
		}
		table.setWidget(r, c, widget);
	}

	private void updateTableStructure() {
		int columnCount = tableModel.getColumnCount();
		for (int i = 0; i < columnCount; i++) {
			Column col = tableModel.getColumn(i);
			header.setWidget(0, i, col.getColumnTitleWidget());
		}
		for (int i = 0; i < columnCount - 1; i++) {
			Column col = tableModel.getColumn(i);
			header.getColumnFormatter().setWidth(i, col.getWidth());
		}
		for (int i = 0; i < columnCount - 1; i++) {
			Column col = tableModel.getColumn(i);

			header.getCellFormatter().removeStyleName(0, i, selectionStyle.columnAscending());
			header.getCellFormatter().removeStyleName(0, i, selectionStyle.columnDescending());

			if(col.getSortDirection() == Column.Ascending){
				header.getCellFormatter().addStyleName(0, i, selectionStyle.columnAscending());
			}else if(col.getSortDirection() == Column.Descending){
				header.getCellFormatter().addStyleName(0, i, selectionStyle.columnDescending());
			}else{
				
			}
			header.getColumnFormatter().setWidth(i, col.getWidth());
		}
	}
}