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
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.ModelListItems;
import org.kuali.student.r1.common.dto.Idable;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;


/**
 * This class is a temporary hack to display a list of strings.
 *
 * @author Kuali Student Team
 *
 */
public class KSLabelListImpl extends KSSelectItemWidgetAbstract {

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
     * This is done this way, as Enumerated types are sometimes in the droplist and normal getSelectedItems return "kuali.atp.fall" and not just "Fall"
     * @return
     */
    public List<String> getSelectedItemsForExport() {
    	List<String> selectedList = new ArrayList<String>();
    	int rowCnt = this.labels.getRowCount();
    	for (int i = 0; i < rowCnt; i++) {
			Widget theWidget = this.labels.getWidget(i, 0);
			if (theWidget instanceof KSLabel) {
				KSLabel ksLbl = (KSLabel) theWidget;
				String label = ksLbl.getText();
				selectedList.add(label);
			}
		}
        return selectedList;
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
        int currCount = 0;
        int row = 0;
        int col = 0;

        int itemCount = 0;
        if (super.getListItems() != null){
            itemCount = super.getListItems().getItemCount();
            if (itemCount == 0){
                // Fix for divide by zero error by getting the proper count back from the select items box.
                itemCount = selectedItems.size();
            }
        } else {
        	itemCount = selectedItems.size();
        }

        if (maxCols <= 2){
            //Row flow - increment row faster than column
            int maxRows = (itemCount / maxCols) + (itemCount % 2);
            for (String id:selectedItems){
                currCount++;
                row = (currCount % maxRows);
                row = ((row == 0) ? maxRows:row) - 1;

                labels.setWidget(row, col, createLabel(id));

                col += ((row + 1)/ maxRows) * 1;
            }
        } else {
            //Column flow - increment column faster than row
            for (String id:selectedItems){
                currCount++;
                col = currCount % maxCols;
                col = ((col == 0) ? maxCols:col) - 1;

                labels.setWidget(row, col, createLabel(id));

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
    	if (super.getListItems() == null || super.getListItems().getItemCount() == 0){
    		return new KSLabel(id);
    	} else {
    		return new KSLabel(super.getListItems().getItemText(id));
    	}
    }

    public void onLoad() {}

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
