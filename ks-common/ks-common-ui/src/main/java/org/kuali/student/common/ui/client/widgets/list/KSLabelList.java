/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.widgets.list;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.list.impl.KSLabelListImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;


/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team 
 *
 */
public class KSLabelList extends KSSelectItemWidgetAbstract {
    private KSSelectItemWidgetAbstract selectItemWidget = GWT.create(KSLabelListImpl.class);

    
	public KSLabelList(String name) {
        initWidget(selectItemWidget);
        selectItemWidget.setName(name);
	}
	
    public KSLabelList() {
        initWidget(selectItemWidget);
    }

    public KSLabelList(boolean initialzed) {
        initWidget(selectItemWidget);
        selectItemWidget.setInitialized(initialzed);
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
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return selectItemWidget.addFocusHandler(handler);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return selectItemWidget.addBlurHandler(handler);
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
	
    public List<String> getSelectedItemsForExport() {
    	if (getWidget() != null && getWidget() instanceof KSLabelListImpl) {
    		KSLabelListImpl ksLabelImpl = (KSLabelListImpl) getWidget();
    		return ksLabelImpl.getSelectedItemsForExport();
    	}
    	return null;
    }
}


