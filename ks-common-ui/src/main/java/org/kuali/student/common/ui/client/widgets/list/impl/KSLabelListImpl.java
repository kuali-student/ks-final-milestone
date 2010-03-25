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
package org.kuali.student.common.ui.client.widgets.list.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.ModelListItems;
import org.kuali.student.core.dto.Idable;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlexTable;


/**
 * This class is a temporary hack to display a list of strings.
 * 
 * @author Kuali Student Team 
 *
 */
public class KSLabelListImpl extends KSSelectItemWidgetAbstract implements ClickHandler{
    
    private static final HandlerRegistration NO_OP_REGISTRATION = new HandlerRegistration() {
		@Override
		public void removeHandler() {
			// do nothing
		}
    };

    
	private FlexTable labels = new FlexTable();
    private List<String> selectedItems = new ArrayList<String>();

    private int maxCols = 1; //default max columns
    private boolean enabled = true;

    public KSLabelListImpl() {
        initWidget(labels);
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#deSelectItem(java.lang.String)
     */
    public void deSelectItem(String id) {
       this.selectedItems.remove(id);
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#getSelectedItems()
     */
    public List<String> getSelectedItems() {
        return selectedItems;
    }



    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#selectItem(java.lang.String)
     */
    public void selectItem(String id) {        
        if (!selectedItems.contains(id)){
             this.selectedItems.add(id);
        }
        
        //FIXME: Is there a better way to display selected item here without redrawing
        redraw();
    }
    
    public void redraw(){
        labels.clear();
        int itemCount = super.getListItems().getItemCount();
        int currCount = 0;
        int row = 0;
        int col = 0;

        if (maxCols <= 2){
            //Row flow - increment row faster than column
            int maxRows = (itemCount / maxCols) + (itemCount % 2);
            for (String id:selectedItems){
                currCount++;
                row = (currCount % maxRows);
                row = ((row == 0) ? maxRows:row) - 1;
                
                labels.setWidget(row, col, createLabel(super.getListItems().getItemText(id)));
                
                col += ((row + 1)/ maxRows) * 1;
            }
        } else {
            //Column flow - increment column faster than row
            for (String id:selectedItems){
                currCount++;
                col = currCount % maxCols;
                col = ((col == 0) ? maxCols:col) - 1;
                
                labels.setWidget(row, col, createLabel(super.getListItems().getItemText(id)));
                
                row += ((col + 1 )/ maxCols) * 1;
            }
        }
        
        super.setInitialized(true);
    }

    @Override
    public <T extends Idable> void setListItems(ListItems listItems) {
        if(listItems instanceof ModelListItems){
            Callback<T> redrawCallback = new Callback<T>(){
                
                @Override 
                public void exec(T result){
                    KSLabelListImpl.this.redraw();
                }
            };
            ((ModelListItems<T>)listItems).addOnAddCallback(redrawCallback);
            ((ModelListItems<T>)listItems).addOnRemoveCallback(redrawCallback);
            ((ModelListItems<T>)listItems).addOnUpdateCallback(redrawCallback);
            ((ModelListItems<T>)listItems).addOnBulkUpdateCallback(redrawCallback);
        }
        
        super.setListItems(listItems);

        redraw();
    }

    private KSLabel createLabel(String id){
        return new KSLabel(id);
    }


    @Override
    public void onClick(ClickEvent event) {
        KSLabel label = (KSLabel)(event.getSource());   
//        String value = label.getFormValue();
//        if (checkbox.getValue()){
//            if (!selectedItems.contains(value)){
//                selectedItems.add(value);
//            }
//        } else {
//            selectedItems.remove(value);
//        }
//        fireChangeEvent();
    }

    public void onLoad() {}

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#setMaxColumns(int)
     */
    @Override
    public void setColumnSize(int col) {
        maxCols = col;
    }

    @Override
    public void setEnabled(boolean b) {
//        enabled = b;
//        for (int i=0; i < labels.getRowCount(); i++){
//            for (int j=0; j < labels.getCellCount(i); j++){
//                ((KSLabel)labels.getWidget(i, j)).setEnabled(b);
//            }
//        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    @Override
    public boolean isMultipleSelect(){
        return true;
    }
    @Override
    public void clear(){
        selectedItems.clear();
        redraw();
    }

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return NO_OP_REGISTRATION;
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return NO_OP_REGISTRATION;
	}

}
