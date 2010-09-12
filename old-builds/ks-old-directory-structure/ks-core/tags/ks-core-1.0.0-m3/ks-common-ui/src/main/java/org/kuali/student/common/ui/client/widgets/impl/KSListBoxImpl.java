/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.ModelListItems;
import org.kuali.student.core.dto.Idable;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ListBox;

public class KSListBoxImpl extends KSSelectItemWidgetAbstract{ 

	private ListBox listBox;

    /**
     * Creates a multi-select list box.
     * 
     * @param list the list of strings representing items in the list box
     */
    public KSListBoxImpl() {
        init();
    }
    
    public void redraw(){
        listBox.clear();
        
        for (String id: super.getListItems().getItemIds()){
            listBox.addItem(super.getListItems().getItemText(id),id);            
        }
        
    }

    protected void init() {
        listBox = new ListBox(true);
        this.initWidget(listBox);
        setupDefaultStyle();
        
        listBox.addChangeHandler(new ChangeHandler(){
            public void onChange(ChangeEvent event) {
                fireChangeEvent();                
            }       
        });
    }

    /**
     * This method sets the default style for the list box and list box events.
     * 
     */
    private void setupDefaultStyle() {
        listBox.addStyleName(KSStyles.KS_LISTBOX_STYLE);
        
        listBox.addBlurHandler(new BlurHandler(){
            public void onBlur(BlurEvent event) {
                listBox.removeStyleName(KSStyles.KS_LISTBOX_FOCUS_STYLE);
                
            }   
        }); 

        listBox.addFocusHandler(new FocusHandler(){
            public void onFocus(FocusEvent event) {
                listBox.addStyleName(KSStyles.KS_LISTBOX_FOCUS_STYLE);
            }       
        });
        
        listBox.addMouseOverHandler(new MouseOverHandler(){
            public void onMouseOver(MouseOverEvent event) {
                listBox.addStyleName(KSStyles.KS_LISTBOX_HOVER_STYLE);                
            }       
        });
        
        listBox.addMouseOutHandler(new MouseOutHandler(){
            public void onMouseOut(MouseOutEvent event) {
                listBox.removeStyleName(KSStyles.KS_LISTBOX_HOVER_STYLE);                
            }
            
        });
    }

	
	public void selectItem(String id){
		for(int i = 0; i < listBox.getItemCount(); i++){
			if(id.equals(listBox.getValue(i))){
				listBox.setItemSelected(i, true);
			}
		}
	}

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#deSelectItem(java.lang.String)
     */
    @Override
    public void deSelectItem(String id) {        
        for(int i = 0; i < listBox.getItemCount(); i++){
            if(id.equals(listBox.getValue(i))){
                listBox.setItemSelected(i, false);
            }
        }        
    }

    @Override
    public <T extends Idable> void setListItems(ListItems listItems) {
        if(listItems instanceof ModelListItems){
            Callback<T> redrawCallback = new Callback<T>(){
                
                @Override 
                public void exec(T result){
                    KSListBoxImpl.this.redraw();
                }
            };
            ((ModelListItems<T>)listItems).addOnAddCallback(redrawCallback);
            ((ModelListItems<T>)listItems).addOnRemoveCallback(redrawCallback);
            ((ModelListItems<T>)listItems).addOnUpdateCallback(redrawCallback);
            ((ModelListItems<T>)listItems).addOnBulkUpdateCallback(redrawCallback);
        }
        
        super.setListItems(listItems);
        
        listBox.clear();
        for (String id:listItems.getItemIds()){
            listBox.addItem(listItems.getItemText(id),id);            
        }        
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#getSelectedItems()
     */
    @Override
    public List<String> getSelectedItems() {
        List<String> result = new ArrayList<String>();
        for(int i = 0; i < listBox.getItemCount(); i++){
            if(listBox.isItemSelected(i)){
                result.add(listBox.getValue(i));
            }
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

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return listBox.addFocusHandler(handler);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return listBox.addBlurHandler(handler);
	}

}
