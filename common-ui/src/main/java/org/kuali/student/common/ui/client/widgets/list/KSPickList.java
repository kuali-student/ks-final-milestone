package org.kuali.student.common.ui.client.widgets.list;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.list.impl.KSPickListImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Skeleton for KSPickList
 * 
 * TODO clean it up and make it work
 *
 */

public class KSPickList extends KSSelectItemWidgetAbstract {
    private final KSSelectItemWidgetAbstract selectItemWidget = GWT.create(KSPickListImpl.class);

    
    
    protected void init(String name) {
        selectItemWidget.init(name);
    }


    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#deSelectItem(java.lang.String)
     */
    public void deSelectItem(String id) {
        selectItemWidget.deSelectItem(id);  
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#getSelectedItems()
     */
    public List<String> getSelectedItems() {
        return selectItemWidget.getSelectedItems();
    }


    
    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#selectItem(java.lang.String)
     */
    public void selectItem(String id) {
        selectItemWidget.selectItem(id);
    }

    public void setListItems(ListItems listItems) {
        selectItemWidget.setListItems(listItems);      
    }

    @Override
    public void onClick(ClickEvent event) {
        selectItemWidget.onClick(event);
        
    }

    public void setMultipleSelect(boolean isMultipleSelect) {}

    /**
     * This overridden method is not used
     * 
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#onLoad()
     */
    @Override
    public void onLoad() {}
   
    public void addSelectionChangeHandler(SelectionChangeHandler handler) {
        selectItemWidget.addSelectionChangeHandler(handler);
    }

    protected void fireChangeEvent() {
        selectItemWidget.fireChangeEvent();
    }

    public ListItems getListItems() {
        return selectItemWidget.getListItems();
    }

    public String getName() {
        return selectItemWidget.getName();
    }

    public void setName(String name) {
        selectItemWidget.setName(name);
    }


}
