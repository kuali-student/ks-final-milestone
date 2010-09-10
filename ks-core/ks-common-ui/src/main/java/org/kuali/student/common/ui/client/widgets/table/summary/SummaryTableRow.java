package org.kuali.student.common.ui.client.widgets.table.summary;

import com.google.gwt.user.client.ui.Widget;

public class SummaryTableRow {
    private boolean isRequired = false;
    private String title ="";
    private int contentCellCount = 1;
    private Widget cell1;
    private Widget cell2;
    private String key;
    private String titleStyle;
    private String contentStyle;
    private boolean shown = true;
    
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
    
    public void addTitleCellStyleName(String style){
    	this.titleStyle = style;
    }
    
    public String getTitleCellStyleName(){
    	return titleStyle;
    }
    
    public void addContentCellStyleName(String style){
    	this.contentStyle = style;
    }
    
    public String getContentCellStyleName(){
    	return contentStyle;
    }
    
    /**
     * Rows that are set to not show will be set to display:none during the layout phase.
     * These flags should be reset and set again if needed during each data setup phase.
     * @param show
     */
    public void setShown(boolean show){
    	this.shown = show;
    }
    
    protected boolean isShown(){
    	return shown;
    }
}
