package org.kuali.student.common.ui.client.widgets.table.scroll;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlexTable;

public class MouseHoverFlexTable extends FlexTable {
    private TableModel tableModel;
    ClickHandler clickHandler;
    public MouseHoverFlexTable(){
        sinkEvents(Event.ONMOUSEOVER |Event.ONMOUSEOUT);
        
    }
    public void setModel(TableModel tableModel){
        this.tableModel = tableModel;
    }
    
    public void onBrowserEvent(Event event) {
        super.onBrowserEvent(event);
        Element td = getEventTargetCell(event);
        if (td == null)
            return;
       
        switch (DOM.eventGetType(event)) {
            case Event.ONMOUSEOVER: {
                Element tr = DOM.getParent(td);
                DOM.setStyleAttribute(tr, "backgroundColor", "#2b60ec");

                break;
            }
            case Event.ONMOUSEOUT: {
                int count = tableModel.getRowCount();
                for (int r = 0; r < count; r++) {
                    Element tr = getRowFormatter().getElement(r);
                    if (tableModel.getRow(r).isSelected()) {
                        DOM.setStyleAttribute(tr, "backgroundColor", "#C6D9FF");
                    }else{
                        DOM.setStyleAttribute(tr, "backgroundColor", "#FFFFFF");
                    }
                }
                break;
            }
        }

    }

}