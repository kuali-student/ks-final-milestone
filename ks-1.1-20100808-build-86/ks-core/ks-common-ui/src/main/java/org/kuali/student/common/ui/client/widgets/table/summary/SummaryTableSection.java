package org.kuali.student.common.ui.client.widgets.table.summary;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;

public class SummaryTableSection {
    private boolean isEditable = false;
    private String title = "";
    private List<SectionRow> sectionRowList = new ArrayList<SectionRow>();
    private ClickHandler editEventHandler;
    
    public void addEditingHandler(ClickHandler clickHandler){
        this.editEventHandler = clickHandler;
    }
    public ClickHandler getEditingHandler(){
        return editEventHandler;
    }
    
    public List<SectionRow> getSectionRowList(){
        return sectionRowList;
    }
    public boolean isEditable() {
        return isEditable;
    }
    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }
    public void add(SectionRow aRow){
        sectionRowList.add(aRow);
    }
    public void clear(){
        sectionRowList.clear();
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
}
