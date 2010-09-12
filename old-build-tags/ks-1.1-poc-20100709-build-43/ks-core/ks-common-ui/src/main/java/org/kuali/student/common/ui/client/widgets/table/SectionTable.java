/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.widgets.table;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a generic table for assembling non-sortable tables with sections
 *
 */

public class SectionTable extends FlexTable {

    protected static final int HEADER_ROW = 0;
    protected static final int FIRST_DATA_ROW = 1;
    protected static final int FIRST_COLUMN = 0;
    protected static final int DATA_INDEX_ZERO = 0;
    protected static final int DEFAULT_TABLE_CELL_SPACING = 0;

    private int nextRow =1 ;
    private Object[][] rowData = null;


    public SectionTable(Object[][] rowData) {
        this();
        this.rowData = rowData;
        createRows(DATA_INDEX_ZERO);

    }


    public SectionTable() {
        insertRow(HEADER_ROW);
        setCellSpacing(DEFAULT_TABLE_CELL_SPACING);
        addStyleName("ks-table-container");
        addStyleName("ks-table");
        getRowFormatter().addStyleName(HEADER_ROW, "ks-table th");
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
    * Nothing for now
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

        // getCellFormatter().addStyleName(HEADER_ROW, columnIndex, "ks-table-th");
        // getRowFormatter().addStyleName(HEADER_ROW, "ks-table-th");
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

    /**
    *
    * Returns the number of Coulmns in the Table
    */
    public int getColumnCount() {
        return getCellCount(HEADER_ROW);
    }


}