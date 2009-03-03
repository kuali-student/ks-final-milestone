package org.kuali.student.common.ui.client.widgets.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.list.impl.KSSelectableTableListImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.visualization.client.AjaxLoader;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.Table.Options;

/**
 * This represent a selectable list of items in a table. User can select single item
 * or multiple items from list. 
 * 
 * @author Kuali Student Team
 *
 */
public class KSSelectableTableList extends KSSelectItemWidgetAbstract { 

    private KSSelectItemWidgetAbstract selectItemWidget = GWT.create(KSSelectableTableListImpl.class);
 
    public KSSelectableTableList(){
        initWidget(selectItemWidget);
    }
    
    public KSSelectableTableList(boolean isMultipleSelect){
        initWidget(selectItemWidget);
        setMultipleSelect(isMultipleSelect);
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

    /**
     * Sets if multi-select of items is enabled. By default enableMultiSelect is true.
     * 
     * @param b
     */
    public void setMultipleSelect(boolean b){
        selectItemWidget.setMultipleSelect(b);
    }
    
    public void onLoad() {               
        selectItemWidget.onLoad();
    }

    /**
     * This overridden method is not used
     * 
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#init(java.lang.String)
     */
    protected void init(String name) {}

    /**
     * This overridden method is not used
     * 
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#onClick(com.google.gwt.event.dom.client.ClickEvent)
     */
    public void onClick(ClickEvent event) {}


    
}
