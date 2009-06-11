package org.kuali.student.common.ui.client.widgets.list;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.list.impl.KSPickListImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * Skeleton for KSPickList
 * 
 * TODO clean it up and make it work
 *
 */

public class KSPickList extends KSSelectItemWidgetAbstract {
    private final KSSelectItemWidgetAbstract selectItemWidget = GWT.create(KSPickListImpl.class);

    
    public KSPickList(String name){
        initWidget(selectItemWidget);
        selectItemWidget.setName(name);
    }

    public KSPickList(){
        initWidget(selectItemWidget);
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

    /**
     * This overridden method is not used
     * 
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#onLoad()
     */
    @Override
    public void onLoad() {}
   
    public HandlerRegistration addSelectionChangeHandler(SelectionChangeHandler handler) {
        return selectItemWidget.addSelectionChangeHandler(handler);
    }

    public void fireChangeEvent() {
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

    @Override
    public void setEnabled(boolean b) {
        selectItemWidget.setEnabled(b);
    }

    @Override
    public boolean isEnabled() {
        return selectItemWidget.isEnabled();
    }

    @Override
    public void redraw() {
        selectItemWidget.redraw();
    }


}
