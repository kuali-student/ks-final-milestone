package org.kuali.student.common.ui.client.widgets.list;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.list.impl.KSCheckBoxListImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;


/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team 
 *
 */
public class KSCheckBoxList extends KSSelectItemWidgetAbstract {
    private KSSelectItemWidgetAbstract selectItemWidget = GWT.create(KSCheckBoxListImpl.class);

    
	public KSCheckBoxList(String name) {
        initWidget(selectItemWidget);
        selectItemWidget.setName(name);
	}
	
    public KSCheckBoxList() {
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
     * Use to set number of columns to use when displaying list
     * 
     */
    public void setColumnSize(int cols){
        selectItemWidget.setColumnSize(cols);
    }
        
    public void setMultipleSelect(boolean isMultipleSelect) {}

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

    @Override
    public void setEnabled(boolean b) {
        selectItemWidget.setEnabled(b);
    }

    @Override
    public boolean isEnabled() {
        return selectItemWidget.isEnabled();
    }

    @Override
    public boolean isMultipleSelect() {
        return selectItemWidget.isMultipleSelect();
    }

    @Override
    public void redraw() {
        selectItemWidget.redraw();
    }
    
}


