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
import org.kuali.student.common.ui.client.widgets.KSRadioButton;
import org.kuali.student.common.ui.client.widgets.KSRadioButtonGroup;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.focus.FocusGroup;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.ModelListItems;
import org.kuali.student.core.dto.Idable;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlexTable;


/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team 
 *
 */
public class KSRadioButtonListImpl extends KSSelectItemWidgetAbstract implements ClickHandler{
    private FlexTable radioButtons = new FlexTable();
    private String selectedValue = null;
    private KSRadioButtonGroup radioGroup = new KSRadioButtonGroup();

    private int maxCols = 1; //default max columns
    
    private boolean enabled = true;
    private final FocusGroup focus = new FocusGroup(this);
    
    public KSRadioButtonListImpl() {
        radioGroup.addValueChangeHandler(new ValueChangeHandler<Boolean>(){

            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
               List<KSRadioButton> rbs = radioGroup.getRadioButtons();
               for(KSRadioButton rb: rbs){
                   if(!rb.getValue()){
                       rb.removeStyleName(KSStyles.KS_RADIO_CHECKED_STYLE);
                   }
               }
                
            }
        });
        initWidget(radioButtons);
    }
    
    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#deSelectItem(java.lang.String)
     */
    public void deSelectItem(String id) {
        for (int i=0; i < radioButtons.getRowCount(); i++){
            for (int j=0; j < radioButtons.getCellCount(i); j++){
                KSRadioButton radioButton = (KSRadioButton)radioButtons.getWidget(i,j);
                if (radioButton.getFormValue().equals(id)){
                    this.selectedValue = null;
                    radioButton.setValue(false);
                    break;
                }
            }
        }
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#getSelectedItems()
     */
    public List<String> getSelectedItems() {
        List<String> items = new ArrayList<String>();
        if (selectedValue != null){
            items.add(selectedValue);
        }
        return items;
    }



    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#selectItem(java.lang.String)
     */
    public void selectItem(String id) {
        deSelectItem(selectedValue);
        for (int i=0; i < radioButtons.getRowCount(); i++){
            for (int j=0; j < radioButtons.getCellCount(i); j++){
                KSRadioButton radioButton = (KSRadioButton)radioButtons.getWidget(i,j);
                if (radioButton.getFormValue().equals(id)){
                    this.selectedValue = id;
                    radioButton.setValue(true);
                    break;
                }

            }
        }
    }
    
    public void redraw(){
        radioButtons.clear();
        int itemCount = super.getListItems().getItemCount();
        int currCount = 0;
        int row = 0;
        int col = 0;

        if (maxCols <= 2){
            //Row flow - increment row faster than column
            int maxRows = (itemCount / maxCols) + (itemCount % 2);
            for (String id:super.getListItems().getItemIds()){
                currCount++;
                row = (currCount % maxRows);
                row = ((row == 0) ? maxRows:row) - 1;

                radioButtons.setWidget(row, col, createRadioButton(id));

                col += ((row + 1)/ maxRows) * 1;
            }
        } else {
            //Column flow - increment column faster than row
            for (String id:super.getListItems().getItemIds()){
                currCount++;
                col = currCount % maxCols;
                col = ((col == 0) ? maxCols:col) - 1;

                radioButtons.setWidget(row, col, createRadioButton(id));

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
                    KSRadioButtonListImpl.this.redraw();
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

    private KSRadioButton createRadioButton(String id){
        KSRadioButton radioButton = new KSRadioButton(this.getName(), getListItems().getItemText(id));
        radioButton.setFormValue(id);
        radioButton.addClickHandler(this);
        radioGroup.addRadioButton(radioButton);
        focus.addWidget(radioButton);
        return radioButton;
    }

    @Override
    public void onClick(ClickEvent event) {
        KSRadioButton radioButton = (KSRadioButton)(event.getSource());   
        if (radioButton.getValue() && !radioButton.getFormValue().equals(selectedValue)){
            selectedValue = radioButton.getFormValue();
            fireChangeEvent();
        }
    }

    public void setMultipleSelect(boolean isMultipleSelect) {
        throw new UnsupportedOperationException("KSRadioButtonList doesn't support multiple select");
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
        enabled = b;
        for (int i=0; i < radioButtons.getRowCount(); i++){
            for (int j=0; j < radioButtons.getCellCount(i); j++){
                ((KSRadioButton)radioButtons.getWidget(i,j)).setEnabled(b);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return focus.addFocusHandler(handler);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return focus.addBlurHandler(handler);
	}

    @Override
    public void clear() {
        // TODO Auto-generated method stub        
    }    
}
