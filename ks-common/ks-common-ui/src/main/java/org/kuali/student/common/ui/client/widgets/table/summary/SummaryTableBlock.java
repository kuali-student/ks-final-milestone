package org.kuali.student.common.ui.client.widgets.table.summary;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;

public class SummaryTableBlock {
    private String title = "";
    private List<SummaryTableRow> sectionRowList = new ArrayList<SummaryTableRow>();
    private ClickHandler editEventHandler;
    

    public void addEditingHandler(ClickHandler clickHandler){
        this.editEventHandler = clickHandler;
    }
    public ClickHandler getEditingHandler(){
        return editEventHandler;
    }
    
    public List<SummaryTableRow> getSectionRowList(){
        return sectionRowList;
    }

    protected void add(SummaryTableRow aRow){
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
