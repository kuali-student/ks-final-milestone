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

package org.kuali.student.common.ui.client.widgets.pagetable;

import java.util.Set;

import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.event.client.RowSelectionEvent;
import com.google.gwt.gen2.table.event.client.RowSelectionHandler;

/**
 * When this event handler is added to a table that extends 
 * @see class com.google.gwt.gen2.table.client.AbstractScrollTable
 * Where all columns contain text, the text of every selected row is copied
 * to a Label
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
@Deprecated
public class TableSelectionToLabelHandler implements RowSelectionHandler {
    private FixedWidthGrid dataTable;
    private KSLabel selection;
    private String labelPrefix;
    
    public TableSelectionToLabelHandler(FixedWidthGrid dataTable, KSLabel selection) {
        this.dataTable = dataTable;
        this.selection = selection;
        this.labelPrefix = selection.getText();
    }
    /**
     * Copy the text from every column in every selected row to a Label
     * 
     * @see com.google.gwt.gen2.table.event.client.RowSelectionHandler#onRowSelection(com.google.gwt.gen2.table.event.client.RowSelectionEvent)
     */
    @Override
    public void onRowSelection(RowSelectionEvent event) {
        Set<Integer>selectedRows = dataTable.getSelectedRows();
        //Set<Row>selectedRows = event.getSelectedRows();
        int colCount = dataTable.getColumnCount();
        StringBuilder sb = new StringBuilder(labelPrefix);
        sb.append('\n');
        for(Integer row:selectedRows){            
            selection.setText(labelPrefix);
            for(int column = 0; column < colCount; column++) {
               sb.append(dataTable.getText(row, column)).append(' '); 
            }
            sb.append('\n');
        }
        selection.setText(sb.toString());
    }
}
