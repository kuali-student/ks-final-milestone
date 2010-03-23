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
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.focus.FocusGroup;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.KSSelectableTableList;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.ModelListItems;
import org.kuali.student.core.dto.Idable;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Skeleton for KSPickListImpl
 * 
 * TODO clean it up and make it work
 *
 */
public class KSPickListImpl extends KSSelectItemWidgetAbstract {
    private final List<String> unselected = new ArrayList<String>();
    private final List<String> selected = new ArrayList<String>();
    
    private boolean enabled = true;
    
    
    private final HorizontalPanel panel = new HorizontalPanel();
    private final KSSelectableTableList unselectedTable = new KSSelectableTableList(true);
    private final KSSelectableTableList selectedTable = new KSSelectableTableList(true);
    private final VerticalPanel buttonPanel = new VerticalPanel();
    private final VerticalPanel unselectedPanel = new VerticalPanel();
    private final KSLabel unselectedLabel = new KSLabel();
    private final VerticalPanel selectedPanel = new VerticalPanel();
    private final KSLabel selectedLabel = new KSLabel();
    
    private final FlexTable pickListTable = new FlexTable();
    
    private final Button add = new Button(">", new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            for(String id: unselectedTable.getSelectedItems()){
                if(unselected.contains(id)){
                    selected.add(id);
                }
            }
//            selected.addAll(unselectedTable.getSelectedItems());
            unselected.removeAll(unselectedTable.getSelectedItems());
            selectedTable.onLoad();
            unselectedTable.onLoad();
        }
    });
    private final Button remove = new Button("&lt;", new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            for(String id: selectedTable.getSelectedItems()){
                if(selected.contains(id)){
                    unselected.add(id);
                }
            }
            //unselected.addAll(selectedTable.getSelectedItems());
            selected.removeAll(selectedTable.getSelectedItems());
            selectedTable.onLoad();
            unselectedTable.onLoad();
        }
    });
    
    private final Button addAll = new Button(">>", new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            if(unselected.size() == 0){
                return;
            }
            unselected.clear();
            selected.clear();
            selected.addAll(KSPickListImpl.this.getListItems().getItemIds());
            
            selectedTable.onLoad();
            unselectedTable.onLoad();
        }
    });
    private final Button removeAll = new Button("&lt;&lt;", new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            if(selected.size() == 0){
                return;
            }
            selected.clear();
            unselected.clear();
            unselected.addAll(KSPickListImpl.this.getListItems().getItemIds());
            selectedTable.onLoad();
            unselectedTable.onLoad();
        }
    });
    
    
    
    private final ListItems unselectedAdapter = new ListItems() {
        @Override
        public List<String> getAttrKeys() {
            return KSPickListImpl.this.getListItems().getAttrKeys();
        }

        @Override
        public String getItemAttribute(String id, String attrkey) {
            if (unselected.contains(id)) {
                return KSPickListImpl.this.getListItems().getItemAttribute(id, attrkey);
            } else {
                return null;
            }
        }

        @Override
        public int getItemCount() {
            return unselected.size();
        }

        @Override
        public List<String> getItemIds() {
            return unselected;
        }

        @Override
        public String getItemText(String id) {
            if (unselected.contains(id)) {
                return KSPickListImpl.this.getListItems().getItemText(id);
            } else {
                return null;
            }
        }
    };
    
    private final ListItems selectedAdapter = new ListItems() {
        @Override
        public List<String> getAttrKeys() {
            return KSPickListImpl.this.getListItems().getAttrKeys();
        }

        @Override
        public String getItemAttribute(String id, String attrkey) {
            if (selected.contains(id)) {
                return KSPickListImpl.this.getListItems().getItemAttribute(id, attrkey);
            } else {
                return null;
            }
        }

        @Override
        public int getItemCount() {
            return selected.size();
        }

        @Override
        public List<String> getItemIds() {
            return selected;
        }

        @Override
        public String getItemText(String id) {
            if (selected.contains(id)) {
                return KSPickListImpl.this.getListItems().getItemText(id);
            } else {
                return null;
            }
        }
    };
    
    private final FocusGroup focus = new FocusGroup(this);
    
    public KSPickListImpl() {
        initWidget(panel);
        setupDefaultStyle();
        
        unselectedLabel.setText("Unselected");
        selectedLabel.setText("Selected");
        pickListTable.setWidget(0, 0, unselectedLabel);
        pickListTable.setWidget(0, 2, selectedLabel);
        
        pickListTable.setWidget(1, 0, unselectedTable);
        pickListTable.setWidget(1, 1, buttonPanel);
        pickListTable.setWidget(1, 2, selectedTable);
        
        buttonPanel.add(addAll);
        buttonPanel.add(add);
        buttonPanel.add(remove);
        buttonPanel.add(removeAll);
        
        pickListTable.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
        pickListTable.getCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);
        pickListTable.getCellFormatter().setVerticalAlignment(1, 2, HasVerticalAlignment.ALIGN_TOP);
        
        panel.add(pickListTable);
        focus.addWidget(this.add);
        focus.addWidget(this.addAll);
        focus.addWidget(this.remove);
        focus.addWidget(this.removeAll);
        focus.addWidget(this.selectedTable);
        focus.addWidget(this.unselectedTable);

    }
    
    private void setupDefaultStyle(){
        panel.addStyleName(KSStyles.KS_PICK_LIST_WRAPPER_PANEL);
        buttonPanel.addStyleName(KSStyles.KS_PICK_LIST_BUTTON_PANEL);
        unselectedLabel.addStyleName(KSStyles.KS_PICK_LIST_UNSELECTED_LABEL);
        selectedLabel.addStyleName(KSStyles.KS_PICK_LIST_SELECTED_LABEL);
        pickListTable.addStyleName(KSStyles.KS_PICK_LIST_TABLE_LAYOUT);
    }
    
    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#deSelectItem(java.lang.String)
     */
    public void deSelectItem(String id) {
        if (selected.contains(id)) {
            selected.remove(id);
            unselected.add(id);
            // TODO refresh tables
        }
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#getSelectedItems()
     */
    public List<String> getSelectedItems() {
        return selectedTable.getSelectedItems();
    }


    
    /**
     * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#selectItem(java.lang.String)
     */
    public void selectItem(String id) {
        if (unselected.contains(id)) {
            unselected.remove(id);
            selected.add(id);
            // TODO refresh tables
        }
    }

    @Override
    public <T extends Idable> void setListItems(ListItems listItems) {
        if(listItems instanceof ModelListItems){
            Callback<T> redrawCallback = new Callback<T>(){
    
                @Override 
                public void exec(T result){
                    KSPickListImpl.this.redraw();
                }
            };
            ((ModelListItems<T>)listItems).addOnAddCallback(redrawCallback);
            ((ModelListItems<T>)listItems).addOnRemoveCallback(redrawCallback);
            ((ModelListItems<T>)listItems).addOnUpdateCallback(redrawCallback);
            ((ModelListItems<T>)listItems).addOnBulkUpdateCallback(redrawCallback);
        }
        
        super.setListItems(listItems);
        unselected.clear();
        unselected.addAll(listItems.getItemIds());
        unselectedTable.setListItems(unselectedAdapter);
        selectedTable.setListItems(selectedAdapter);
    }
   
    public void redraw() {
        unselected.clear();
        unselected.addAll(KSPickListImpl.this.getListItems().getItemIds());
        unselected.removeAll(selected);
        selected.clear();
        selected.addAll(KSPickListImpl.this.getListItems().getItemIds());
        selected.removeAll(unselected);
        KSPickListImpl.this.unselectedTable.onLoad();
        KSPickListImpl.this.selectedTable.onLoad();
    }

    public void setMultipleSelect(boolean isMultipleSelect) {}

    public void onLoad() {}

    @Override
    public void setEnabled(boolean b) {
        enabled = b;
        unselectedTable.setEnabled(b);
        selectedTable.setEnabled(b);
        add.setEnabled(b);
        remove.setEnabled(b);
        addAll.setEnabled(b);
        removeAll.setEnabled(b);
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

}
