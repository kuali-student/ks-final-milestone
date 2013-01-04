package org.kuali.student.common.ui.client.widgets.list;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.list.impl.KSRadioButtonListImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

public class KSRadioButtonList extends KSSelectItemWidgetAbstract implements HasBlurHandlers, HasFocusHandlers{
	private KSRadioButtonListImpl selectItemWidget = GWT.create(KSRadioButtonListImpl.class);

    
	public KSRadioButtonList(String name) {
        initWidget(selectItemWidget);
        selectItemWidget.setName(name);
	}
	
    public KSRadioButtonList() {
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

    @Override
    public void clear() {
        selectItemWidget.clear();
    }

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return selectItemWidget.addBlurHandler(handler);
	}

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return selectItemWidget.addFocusHandler(handler);
	}

    public void addWidgetReadyCallback(Callback<Widget> callback) {
        selectItemWidget.addWidgetReadyCallback(callback);
    }

    public boolean isInitialized() {
        return selectItemWidget.isInitialized();
    }

    public void setInitialized(boolean initialized) {
        selectItemWidget.setInitialized(initialized);
    }
    
    /**
     * By default if the list items used by the radiobutton has multiple attributes, the radiobutton
     * generated will display all attributes as columns. Set this property to true if this
     * behavior is not desired.
     * 
     * @param ignoreMultiple
     */
    public void setIgnoreMultipleAttributes(boolean ignoreMultiple){
    	selectItemWidget.setIgnoreMultipleAttributes(ignoreMultiple);
    }
}
