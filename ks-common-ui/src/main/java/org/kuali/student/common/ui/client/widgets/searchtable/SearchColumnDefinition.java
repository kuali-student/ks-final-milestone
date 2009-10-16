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
package org.kuali.student.common.ui.client.widgets.searchtable;


import com.google.gwt.gen2.table.client.AbstractColumnDefinition;

public class SearchColumnDefinition extends AbstractColumnDefinition<ResultRow, String> {

    private String columnKey;
    public SearchColumnDefinition(String colHeader, String resultKey){
        this.columnKey = resultKey;
        setHeader(0,colHeader);
        setMinimumColumnWidth(colHeader.length());
        setColumnSortable(true);
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
