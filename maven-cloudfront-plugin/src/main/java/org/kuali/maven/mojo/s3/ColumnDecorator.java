package org.kuali.maven.mojo.s3;

public class ColumnDecorator {
    String tableDataClass;
    String spanClass;
    String columnTitle;

    public String getTableDataClass() {
        return tableDataClass;
    }

    public void setTableDataClass(String tableDataClass) {
        this.tableDataClass = tableDataClass;
    }

    public String getSpanClass() {
        return spanClass;
    }

    public void setSpanClass(String spanClass) {
        this.spanClass = spanClass;
    }

    public String getColumnTitle() {
        return columnTitle;
    }

    public void setColumnTitle(String columnTitle) {
        this.columnTitle = columnTitle;
    }
}
