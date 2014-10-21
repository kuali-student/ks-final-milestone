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

package org.kuali.student.common.ui.client.widgets.list.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.focus.FocusGroup;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.ModelListItems;
import org.kuali.student.r1.common.dto.Idable;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlexTable;


/**
 *  This class will generate a checkbox list from a ListItems instance.
 *  For a ListItems instance with just one attribute it will be a list of checkboxes which
 *  may be horizontal or vertical as controlled by the maxCols attribute 
 *  If maxCols <=2 checkboxes will be vertical, otherwise horizontal.
 * 
 *  For ListItems with > 1 attribute a table with a header row will be generated 
 *  with each column being a ListItems attribute. This can be turned off using setIgnoreMultipleAttributes
 * 
 * @author Kuali Student Team 
 *
 */
public class KSCheckBoxListImpl extends KSSelectItemWidgetAbstract implements ValueChangeHandler<Boolean>, HasBlurHandlers, HasFocusHandlers {
    private final FocusGroup focus = new FocusGroup(this);
    private FlexTable layout = new FlexTable();
    private List<String> selectedItems = new ArrayList<String>();

    private int maxCols = 1; //default max columns
    private boolean enabled = true;
    private boolean ignoreMultipleAttributes = false;

    public KSCheckBoxListImpl() {
        initWidget(layout);
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#deSelectItem(java.lang.String)
     */
    public void deSelectItem(String id) {
        for (int i=0; i < layout.getRowCount(); i++){
            for (int j=0; j < layout.getCellCount(i); j++){
                KSCheckBox checkbox = (KSCheckBox)layout.getWidget(i, j);
                if (checkbox.getFormValue().equals(id)){
                    this.selectedItems.remove(id);
                    checkbox.setValue(false);
                    fireChangeEvent(false);
                    break;
                }
            }
        }		
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
            for (int i=0; i < layout.getRowCount(); i++){
                for (int j=0; j < layout.getCellCount(i); j++){
                    KSCheckBox checkbox = (KSCheckBox)layout.getWidget(i, j);
                    if (checkbox.getFormValue().equals(id)){
                        this.selectedItems.add(id);
                        checkbox.setValue(true);
                        fireChangeEvent(false);
                        break;
                    }
                }
            }
        }
    }

    public void redraw(){
        layout.clear();
        int itemCount = super.getListItems().getItemCount();
        int currCount = 0;
        int row = 0;
        int col = 0;

        // If ListItems has more than one attribute create a table with each attribute in its own column
        if (!ignoreMultipleAttributes && super.getListItems().getAttrKeys() != null && super.getListItems().getAttrKeys().size() > 1) {
            layout.addStyleName("KS-Checkbox-Table");
            layout.setWidget(row, col++, new KSLabel("Select"));
            for (String attr:super.getListItems().getAttrKeys()){
                layout.setWidget(row, col++, new KSLabel(attr));
            }
            row++;
            col=0;

            for (String id:super.getListItems().getItemIds()){

                layout.setWidget(row, col, createCheckbox(id));
                for (String attr:super.getListItems().getAttrKeys()){
                    String value = super.getListItems().getItemAttribute(id, attr);
                    layout.setWidget(row, ++col, new KSLabel(value));
                }                    

                row++;
                col = 0;
            }

        }
        else if (maxCols <= 2){
            //Row flow - increment row faster than column
            int maxRows = (itemCount / maxCols) + (itemCount % 2);
            for (String id:super.getListItems().getItemIds()){
                currCount++;
                row = (currCount % maxRows);
                row = ((row == 0) ? maxRows:row) - 1;

                layout.setWidget(row, col, createCheckboxWithLabel(id));                    

                col += ((row + 1)/ maxRows) * 1;

            }
        } else {
            //Column flow - increment column faster than row
            for (String id:super.getListItems().getItemIds()){
                currCount++;
                col = currCount % maxCols;
                col = ((col == 0) ? maxCols:col) - 1;

                layout.setWidget(row, col, createCheckboxWithLabel(id));

                row += ((col + 1 )/ maxCols) * 1;
            }
        }
        
        setInitialized(true);
    }

    @Override
    public <T extends Idable> void setListItems(ListItems listItems) {
        if(listItems instanceof ModelListItems){
            Callback<T> redrawCallback = new Callback<T>(){

                @Override 
                public void exec(T result){
                    KSCheckBoxListImpl.this.redraw();
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

    private KSCheckBox createCheckbox(String id){
        KSCheckBox checkbox = new KSCheckBox();
        checkbox.setFormValue(id);
        checkbox.addValueChangeHandler(this);
        focus.addWidget(checkbox);
        return checkbox;
    }

    private KSCheckBox createCheckboxWithLabel(String id){
        KSCheckBox checkbox = new KSCheckBox(getListItems().getItemText(id));
        checkbox.setFormValue(id);
        checkbox.addValueChangeHandler(this);
        focus.addWidget(checkbox);
        return checkbox;
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
        for (int i=0; i < layout.getRowCount(); i++){
            for (int j=0; j < layout.getCellCount(i); j++){
                ((KSCheckBox)layout.getWidget(i, j)).setEnabled(b);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isMultipleSelect(){
        return true;
    }
    
    public void setIgnoreMultipleAttributes(boolean ignoreMultiple){
    	this.ignoreMultipleAttributes = ignoreMultiple;
    }


    @Override
    public void clear(){
        selectedItems.clear();
        fireChangeEvent(false);
        redraw();
    }

    @Override
    public HandlerRegistration addBlurHandler(BlurHandler handler) {
        return focus.addBlurHandler(handler);
    }

    @Override
    public HandlerRegistration addFocusHandler(FocusHandler handler) {
        return focus.addFocusHandler(handler);
    }

	@Override
	public void onValueChange(ValueChangeEvent<Boolean> event) {
        KSCheckBox checkbox = (KSCheckBox)(event.getSource());   
        String value = checkbox.getFormValue();
        if (event.getValue()){
            if (!selectedItems.contains(value)){
                selectedItems.add(value);
            }
        } else {
            selectedItems.remove(value);
        }
        fireChangeEvent(true);
		
	}
}
