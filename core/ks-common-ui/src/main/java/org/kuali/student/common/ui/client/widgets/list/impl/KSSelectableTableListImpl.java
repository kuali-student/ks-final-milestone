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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.ModelListItems;
import org.kuali.student.core.dto.Idable;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.shared.HandlerRegistration;
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
public class KSSelectableTableListImpl extends KSSelectItemWidgetAbstract { 
    Table table;
    SimplePanel root = new SimplePanel();
    Map<Integer, String> idMapping = new HashMap<Integer, String>();
    Map<String, Integer> rowMapping = new HashMap<String, Integer>();
    boolean loaded = false;
    boolean isMultipleSelect = true;
    int selRow = -1;
    int pageSize = 0;
    
    public KSSelectableTableListImpl(){
        initWidget(root);
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#deSelectItem(java.lang.String)
     */
    public void deSelectItem(String id) {
        JsArray<Selection> selections = table.getSelections();

        JsArray<Selection> newSelections = (JsArray<Selection>)JsArray.createArray();
        int j = 0;
        for (int i=0; i < selections.length(); i++){
            if (selections.get(i).getRow() != rowMapping.get(id).intValue()){
                newSelections.set(j, selections.get(i));
                j++;
            }
        }       

        table.setSelections(newSelections);
        //table.
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#getSelectedItems()
     */
    public List<String> getSelectedItems() {

        List<String> selected = new ArrayList<String>();
        
        if (table != null){
            JsArray<Selection> selections = table.getSelections();
            for (int i=0; i < selections.length(); i++){
                selected.add(idMapping.get(new Integer(selections.get(i).getRow())));
            }
        }
        
        return selected;
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#selectItem(java.lang.String)
     */
    public void selectItem(String id) {
        Selection sel = Selection.createRowSelection(rowMapping.get(id).intValue());
        
        JsArray<Selection> selections;
        if (isMultipleSelect){
            selections = table.getSelections();
        } else {
            selections = (JsArray<Selection>)JsArray.createArray();
            selRow = sel.getRow();
        }
        
        selections.set(selections.length(),sel);
        table.setSelections(selections);
    }

    /**
     * Sets if multi-select of items is enabled. By default enableMultiSelect is true.
     * 
     * @param b
     */
    @Override
    public void setMultipleSelect(boolean b){
        this.isMultipleSelect = b;
    }
    
    @Override
    public void onLoad() {               
        Runnable onLoadCallback = new Runnable() {
              public void run() {
                  if (getListItems() != null){
                      redraw();
                  }
            };
        };
        AjaxLoader.loadVisualizationApi(onLoadCallback, Table.PACKAGE);
    }
    
    @Override
    public <T extends Idable> void setListItems(ListItems listItems) {
        if(listItems instanceof ModelListItems){
            Callback<T> redrawCallback = new Callback<T>(){
                
                @Override 
                public void exec(T result){
                    KSSelectableTableListImpl.this.redraw();
                }
            };
            ((ModelListItems<T>)listItems).addOnAddCallback(redrawCallback);
            ((ModelListItems<T>)listItems).addOnRemoveCallback(redrawCallback);
            ((ModelListItems<T>)listItems).addOnUpdateCallback(redrawCallback);
            ((ModelListItems<T>)listItems).addOnBulkUpdateCallback(redrawCallback);
        }
        
         
         super.setListItems(listItems);
    }
    
    public void redraw() {
        DataTable data = DataTable.create(); 

        ListItems listItems = getListItems();
        List<String> attrKeys = listItems.getAttrKeys();

        for (String attr:attrKeys){
            data.addColumn(ColumnType.STRING, attr);
        }

        int row = 0;
        int col = 0;

        
        for (String id: listItems.getItemIds()){
            data.addRow();
            idMapping.put(row,id);
            rowMapping.put(id,row);
            for (String attr:attrKeys){
                String value = listItems.getItemAttribute(id, attr);
                data.setCell(row, col, value, value, null);
                col++;
            }
            row ++;
            col=0;
        }
        
        if (table == null){
            table = new Table();                
            table.addSelectHandler(new SelectHandler(){
                public void onSelect(SelectEvent event) {
                    if (!isMultipleSelect){
                        //Make it to only select one row
                        JsArray<Selection> selections = table.getSelections();
                        if (selections.length() > 1){
                            Selection sel = (selections.get(0).getRow() == selRow ? selections.get(1):selections.get(0));
                            selections = (JsArray<Selection>)JsArray.createArray();
                            selections.set(0,sel);
                            selRow = sel.getRow();
                            table.setSelections(selections);
                        } else if (selections.length() == 1){
                            selRow = selections.get(0).getRow();
                        } else {
                            selRow = -1;
                        }
                    }
                    fireChangeEvent();
                }                   
            });
        }

        Options options = Options.create();
        if (pageSize > 0){
            options.setOption("page", "enable");
            options.setOption("pageSize", pageSize);
        }
        table.draw(data,options);
        table.addStyleName(KSStyles.KS_SELECT_TABLE_PANEL);
        root.setWidget(table);
        
    }

    public void addStyleName(String style){
        table.addStyleName(style);
    }

    protected void init(String name) {}

    public void onClick(ClickEvent event) {}

    @Override
    public void setEnabled(boolean b) {
        // TODO figure out how to disable this sucker
        
    }

    @Override
    public boolean isEnabled() {
        return true; //TODO can't disable currently
    }
    
    public void setPageSize(int pageSize){
        this.pageSize = pageSize;
    }

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		// TODO fill in focus handling once this table is replaced with DynamicTable, which supports focus
		return new HandlerRegistration() {
			@Override
			public void removeHandler() {
				// do nothing, just here to prevent outside code from throwing an exception
			}
		};
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		// TODO fill in focus handling once this table is replaced with DynamicTable, which supports focus
		return new HandlerRegistration() {
			@Override
			public void removeHandler() {
				// do nothing, just here to prevent outside code from throwing an exception
			}
		};
	}
}
