package org.kuali.student.common.ui.client.widgets.table.summary;

import com.google.gwt.user.client.ui.Widget;

public class SectionRow {
    private boolean isRequired = false;
    private String title ="";
    private int contentCellCount = 1;
    private Widget cell1;
    private Widget cell2;
    private String key;
    
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public boolean isRequired() {
        return isRequired;
    }
    public void setRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getContentCellCount() {
        return contentCellCount;
    }
    public void setContentCellCount(int contentCellCount) {
        this.contentCellCount = contentCellCount;
    }
    public Widget getCell1() {
        return cell1;
    }
    public void setCell1(Widget cell1) {
        this.cell1 = cell1;
    }
    public Widget getCell2() {
        return cell2;
    }
    public void setCell2(Widget cell2) {
        this.cell2 = cell2;
    }
    
}
