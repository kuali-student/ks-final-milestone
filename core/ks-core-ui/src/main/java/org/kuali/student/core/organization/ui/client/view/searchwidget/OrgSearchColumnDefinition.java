/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.organization.ui.client.view.searchwidget;


import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;

import com.google.gwt.gen2.table.client.AbstractColumnDefinition;
import com.google.gwt.gen2.table.client.CellRenderer;
import com.google.gwt.gen2.table.client.ColumnDefinition;
import com.google.gwt.gen2.table.client.DefaultCellRenderer;
import com.google.gwt.gen2.table.client.TableDefinition.AbstractCellView;
import com.google.gwt.user.client.ui.Widget;

public class OrgSearchColumnDefinition extends AbstractColumnDefinition<ResultRow, String> {

    private String columnKey;
    
    private void init(String colHeader, String resultKey, CellRenderer<ResultRow, String> cellRenderer) {
        this.columnKey = resultKey;
        if (cellRenderer != null) {
            setCellRenderer(cellRenderer);
//            setCellRenderer(new SearchCellRenderer<ResultRow, String>());
        }
        setHeader(0,colHeader);
        setMinimumColumnWidth(colHeader.length());
        setColumnSortable(true);
    }
    
    public OrgSearchColumnDefinition(String colHeader, String resultKey,
            CellRenderer<ResultRow, String> cellRenderer){
        init(colHeader, resultKey, cellRenderer);
    }
    
    public OrgSearchColumnDefinition(String colHeader, String resultKey){
        init(colHeader, resultKey, null);
    }
    
    @Override
    public String getCellValue(ResultRow rowValue) {
        return rowValue.getValue(columnKey);
    }

    @Override
    public void setCellValue(ResultRow rowValue, String cellValue) {
        rowValue.setValue(columnKey, cellValue);
    }
}

//class SearchCellRenderer<RowType, ColType> extends DefaultCellRenderer<RowType, ColType> {
//    @Override
//    public void renderRowValue(RowType rowValue,
//            ColumnDefinition<RowType, ColType> columnDef,
//            AbstractCellView<RowType> view) {
//        Object cellValue = columnDef.getCellValue(rowValue);
//        if (cellValue == null) {
//            view.setText("");
//        } else if (cellValue instanceof Widget) {
//            view.setWidget((Widget) cellValue);
//        } else {
//            view.setText(cellValue.toString());
//            view.setStyleAttribute("border", "0px");
//            view.setStyleAttribute("borderRight", "5px solid white");
//        }
//    }
//}
