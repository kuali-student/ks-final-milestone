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
