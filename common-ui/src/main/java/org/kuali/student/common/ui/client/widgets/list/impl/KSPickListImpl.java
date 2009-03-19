package org.kuali.student.common.ui.client.widgets.list.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.KSSelectableTableList;
import org.kuali.student.common.ui.client.widgets.list.ListItems;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
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
    
    
    private final HorizontalPanel panel = new HorizontalPanel();
    private final KSSelectableTableList unselectedTable = new KSSelectableTableList(true);
    private final KSSelectableTableList selectedTable = new KSSelectableTableList(true);
    private final VerticalPanel buttonPanel = new VerticalPanel();
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
    
    public KSPickListImpl() {
        initWidget(panel);
        panel.add(unselectedTable);
        panel.add(buttonPanel);
        panel.add(selectedTable);
        
        buttonPanel.add(addAll);
        buttonPanel.add(add);
        buttonPanel.add(remove);
        buttonPanel.add(removeAll);
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

    public void setListItems(ListItems listItems) {
        super.setListItems(listItems);
        unselected.addAll(listItems.getItemIds());
        unselectedTable.setListItems(unselectedAdapter);
        selectedTable.setListItems(selectedAdapter);
    }
   
    public void setMultipleSelect(boolean isMultipleSelect) {}

    public void onLoad() {}

}
