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

import java.util.List;

import com.google.gwt.gen2.table.client.FixedWidthFlexTable;
import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.client.SelectionGrid.SelectionPolicy;
import com.google.gwt.gen2.table.override.client.FlexTable.FlexCellFormatter;

/**
 * This is a description of what this class does - Gary Struthers don't forget to fill this in. 
 * 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
public class ScrollTableHelper {
    
    public ScrollTableHelper() {
        super();
    }

    /**
     * This method creates a header without most options
     * All cells have a column span of 1 and a row span of 2.  
     * 
     * @param columnHeaders the number of HTML strings determines the number of columns
     * @return headerTable
     */
    public FixedWidthFlexTable createStringListHeader(String description, List<String> columnHeaders){
        
        FixedWidthFlexTable headerTable = new FixedWidthFlexTable();
        int row = 0;
        int column = 0;
        FlexCellFormatter headerFormatter = headerTable.getFlexCellFormatter();
        headerTable.setHTML(row, column, description);
        headerFormatter.setColSpan(0, 0, columnHeaders.size());
        row++;
        String html;
        for(String header :columnHeaders  ) {
            html = header;
            column = columnHeaders.indexOf(header);
            headerTable.setHTML(row, column, html);
        }
        return headerTable;
    }
    
    public FixedWidthGrid createDataTable(SelectionPolicy selectionPolicy, int numColumns) {
        FixedWidthGrid dataTable = new FixedWidthGrid(0,numColumns);
        dataTable.setSelectionPolicy(selectionPolicy);
        return dataTable;
      }
    
    public void insertStringListRow(int beforeRow, List<String> rowText, FixedWidthGrid dataTable) {
        dataTable.insertRow(beforeRow);
        int column = 0;
        for(String text :rowText) {
            column = rowText.indexOf(text);
            dataTable.setText(beforeRow, column, text);
        }
    }
}
