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

    public ColumnDecorator(final String tableDataClass, final String spanClass,
            final String columnTitle) {
        super();
        this.tableDataClass = tableDataClass;
        this.spanClass = spanClass;
        this.columnTitle = columnTitle;
    }

    public String getTableDataClass() {
        return tableDataClass;
    }

    public void setTableDataClass(final String tableDataClass) {
        this.tableDataClass = tableDataClass;
    }

    public String getSpanClass() {
        return spanClass;
    }

    public void setSpanClass(final String spanClass) {
        this.spanClass = spanClass;
    }

    public String getColumnTitle() {
        return columnTitle;
    }

    public void setColumnTitle(final String columnTitle) {
        this.columnTitle = columnTitle;
    }
}
