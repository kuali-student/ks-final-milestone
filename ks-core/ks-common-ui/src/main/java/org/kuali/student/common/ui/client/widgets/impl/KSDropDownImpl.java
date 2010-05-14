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

package org.kuali.student.common.ui.client.widgets.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.ModelListItems;
import org.kuali.student.core.dto.Idable;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ListBox;

public class KSDropDownImpl extends KSSelectItemWidgetAbstract implements HasFocusHandlers, HasBlurHandlers{

	private ListBox listBox;
	private boolean blankFirstItem = true;
	private final String EMPTY_ITEM = "";

	public KSDropDownImpl() {
	    init();
	}

	public void redraw(){
	    String selectedItem = getSelectedItem();

        listBox.clear();

        if(blankFirstItem){
            listBox.addItem(EMPTY_ITEM);
        }
        for (String id: super.getListItems().getItemIds()){
            listBox.addItem(super.getListItems().getItemText(id),id);
        }

        selectItem(selectedItem);
        setInitialized(true);
	}

    protected void init() {
		listBox = new ListBox(false);
        this.initWidget(listBox);
		setupDefaultStyle();

		listBox.addChangeHandler(new ChangeHandler(){
            public void onChange(ChangeEvent event) {
                fireChangeEvent();
            }
		});
	}

	private void setupDefaultStyle() {
	    listBox.addStyleName("KS-Dropdown");

		listBox.addBlurHandler(new BlurHandler(){
			public void onBlur(BlurEvent event) {
			    listBox.removeStyleName("KS-Dropdown-Focus");
			}
		});

		listBox.addFocusHandler(new FocusHandler(){
			public void onFocus(FocusEvent event) {
			    listBox.addStyleName("KS-Dropdown-Focus");
			}
		});

		listBox.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
			    listBox.addStyleName("KS-Dropdown-Hover");
			}
		});

		listBox.addMouseOutHandler(new MouseOutHandler(){
			public void onMouseOut(MouseOutEvent event) {
			    listBox.removeStyleName("KS-Dropdown-Hover");
			}
		});

		listBox.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				if(listBox.getSelectedIndex() != -1){
				    listBox.addStyleName("KS-Dropdown-Selected");
				}
				else{
				    listBox.removeStyleName("KS-Dropdown-Selected");
				}
			}
		});
	}

	public void selectItem(String id){
	    if (id != null){
    		for(int i = 0; i < listBox.getItemCount(); i++){
    			if(id.equals(listBox.getValue(i))){
    				listBox.setSelectedIndex(i);
    			}
    		}
	    }
	}

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#deSelectItem(java.lang.String)
     */
    @Override
    public void deSelectItem(String id) {
        int i = listBox.getSelectedIndex();
        if (i >= 0 && listBox.getValue(i).equals(id)){
            listBox.setItemSelected(i, false);
            listBox.setItemSelected(0, true);
        }
    }

    @Override
    public <T extends Idable> void setListItems(ListItems listItems) {
        if(listItems instanceof ModelListItems){
            Callback<T> redrawCallback = new Callback<T>(){

                @Override
                public void exec(T result){
                    KSDropDownImpl.this.redraw();
                }
            };
            ((ModelListItems<T>)listItems).addOnAddCallback(redrawCallback);
            ((ModelListItems<T>)listItems).addOnRemoveCallback(redrawCallback);
            ((ModelListItems<T>)listItems).addOnUpdateCallback(redrawCallback);
            ((ModelListItems<T>)listItems).addOnBulkUpdateCallback(redrawCallback);
        }

        super.setListItems(listItems);

        this.redraw();
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#getSelectedItems()
     */
    @Override
    public List<String> getSelectedItems() {
        List<String> result = new ArrayList<String>();

        int selectedIdx = listBox.getSelectedIndex();

        if((blankFirstItem && selectedIdx > 0) || (!blankFirstItem && selectedIdx >= 0)){
            String id = listBox.getValue(selectedIdx);
            result.add(id);
        }
        return result;
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#onLoad()
     */
    @Override
    public void onLoad() {

    }

    @Override
    public void setEnabled(boolean b) {
        listBox.setEnabled(b);
    }

    @Override
    public boolean isEnabled() {
        return listBox.isEnabled();
    }

    public boolean isBlankFirstItem() {
        return blankFirstItem;
    }

    public void setBlankFirstItem(boolean blankFirstItem) {
        this.blankFirstItem = blankFirstItem;
    }

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return listBox.addFocusHandler(handler);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return listBox.addBlurHandler(handler);
	}

    @Override
    public void clear() {
        //need to select the default item
        if (super.getListItems() != null) {
            listBox.clear();

            if(blankFirstItem){
                listBox.addItem(EMPTY_ITEM);
            }
            for (String id: super.getListItems().getItemIds()){
                listBox.addItem(super.getListItems().getItemText(id),id);
            }
        }
    }
}
