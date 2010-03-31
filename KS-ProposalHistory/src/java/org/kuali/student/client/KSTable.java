/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kuali.student.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author simstu
 */

public class KSTable extends FlexTable {

    protected static final int HEADER_ROW = 0;
    protected static final int FIRST_DATA_ROW = 1;
    protected static final int FIRST_COLUMN = 0;
    protected static final int DATA_INDEX_ZERO = 0;
    protected static final int DEFAULT_TABLE_CELL_SPACING = 0;

    private int nextRow =1 ;
    private Object[][] rowData = null;

   
    public KSTable(Object[][] rowData) {
        this();
        this.rowData = rowData;
        createRows(DATA_INDEX_ZERO);

    }


    protected KSTable() {
        insertRow(HEADER_ROW);
        getRowFormatter().addStyleName(HEADER_ROW, "ks-table-th");
        setCellSpacing(DEFAULT_TABLE_CELL_SPACING);
        addStyleName("ks-table-container");
        addStyleName("ks-table");
    }

    public void createRows(int rowIndex) {
        if (rowData == null)
            return;

        for (int row = rowIndex; row < rowData.length; row++) {
            addRow(rowData[row]);
        }
    }

    public void addRow(Object[] cellObjects) {
        for (int cell = FIRST_COLUMN; cell < cellObjects.length; cell++) {
            addCell(nextRow, cell, cellObjects[cell]);
        }
        nextRow++;
    }

    public void addCell(int row, int cell, Object cellObject) {
        Widget widget = createCellWidget(cellObject);
        setWidget(row, cell, widget);

        // Removed until we know the styling formats
        // getCellFormatter().addStyleName(row, cell, cell == 0 ? "": "");

    }

    /**
    *
    * Do some fancy formatting of rows
    */
    public void applyDataRowStyles() {
        HTMLTable.RowFormatter rf = getRowFormatter();

    }

    public void addColumn(Object columnHeading) {
        Widget widget = createCellWidget(columnHeading);
        int columnIndex = getColumnCount();

        widget.setWidth("100%");
        // widget.addStyleName("");

        setWidget(HEADER_ROW, columnIndex, widget);

        // getCellFormatter().setStyleName(HEADER_ROW, columnIndex, "ks-table-th");
        // getCellFormatter().setStyleName(HEADER_ROW, columnIndex, "");

    }

    public void addSection(Object sectionHeading) {
        
        Object[] sectionRow = {sectionHeading};

        for (int cell = FIRST_COLUMN+1; cell < getColumnCount(); cell++) {
            addCell(nextRow, cell, "");
        }

        addRow(sectionRow);

        // Style the row we just created
        getRowFormatter().addStyleName(nextRow-1, "td-subhead");
        
    }


    protected Widget createCellWidget(Object cellObject) {
        Widget widget = null;

        if (cellObject instanceof Widget)
            widget = (Widget) cellObject;
        else
            widget = new Label(cellObject.toString());

            // widget.setHorizontalAlignment(Label.ALIGN_CENTER);


        return widget;

    }

    public int getColumnCount() {
        return getCellCount(HEADER_ROW);
    }

    
}
