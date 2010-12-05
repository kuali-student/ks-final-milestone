package org.kuali.maven.mojo.s3;

/**
 * Helper pojo for marking up the html for a column in an html table
 */
public class ColumnDecorator {

    String tableDataClass;
    String spanClass;
    String columnTitle;

    public ColumnDecorator() {
        this(null, null, null);
    }

    public ColumnDecorator(String tableDataClass, String spanClass, String columnTitle) {
        super();
        this.tableDataClass = tableDataClass;
        this.spanClass = spanClass;
        this.columnTitle = columnTitle;
    }

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
