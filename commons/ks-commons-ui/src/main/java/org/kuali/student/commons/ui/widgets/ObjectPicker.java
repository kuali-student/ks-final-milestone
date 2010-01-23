package org.kuali.student.commons.ui.widgets;

import java.util.Collection;
import java.util.List;

import org.kuali.student.commons.ui.client.UICommonsConstants;
import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.mvc.client.model.ModelObject;
import org.kuali.student.commons.ui.mvc.client.widgets.ModelBinding;
import org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.commons.ui.widgets.tables.ModelTableColumn;
import org.kuali.student.commons.ui.widgets.tables.PagingModelTable;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ObjectPicker<T extends ModelObject> extends Composite implements ModelWidget<T> {
    final HorizontalPanel panel = new HorizontalPanel();
    final PagingModelTable<T> allItems = new PagingModelTable<T>();
    final PagingModelTable<T> selectedItems = new PagingModelTable<T>();
    
    final VerticalPanel buttonBar = new VerticalPanel();
    final Button add = new Button();
    final Button remove = new Button();
    
    final Model<T> selectedItemsModel = new Model<T>();
    
    ModelBinding<T> binding;
    ViewMetaData metadata;
    Messages messages;
    
    boolean loaded = false;
    
    public ObjectPicker() {
        panel.add(allItems);
        panel.add(buttonBar);
        buttonBar.add(add);
        buttonBar.add(remove);
        panel.add(selectedItems);
        super.initWidget(panel);
    }
    
    public void onLoad() {
        if (!loaded) {
            loaded = true;
            metadata = ApplicationContext.getViews().get(UICommonsConstants.BASE_VIEW_NAME);
            messages = metadata.getMessages();
            
            add.setText(messages.get("add"));
            add.addClickListener(new ClickListener() {
                public void onClick(Widget sender) {
                    addSelected();
                }
            });
            
            remove.setText(messages.get("remove"));
            remove.addClickListener(new ClickListener() {
                public void onClick(Widget sender) {
                    removeSelected();
                }
            });
            
            binding = new ModelBinding<T>(selectedItemsModel, selectedItems);
        }
    }
    
    protected void addColumn(ModelTableColumn<T> column) {
        allItems.addColumn(column);
        selectedItems.addColumn(column);
    }
    
    protected void addSelected() {
        T t = allItems.getSelection();
        if (t != null && !selectedItemsModel.contains(t)) {
            selectedItemsModel.add(t);
        }
    }
    protected void removeSelected() {
        T t = selectedItems.getSelection();
        if (t != null && selectedItemsModel.contains(t)) {
            selectedItemsModel.remove(t);
        }
    }

    /**
     * 
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#add(org.kuali.student.commons.ui.mvc.client.model.ModelObject)
     */
    public void add(T modelObject) {
        allItems.add(modelObject);
    }

    /**
     * 
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#addBulk(java.util.Collection)
     */
    public void addBulk(Collection<T> collection) {
        allItems.addBulk(collection);
    }

    /**
     * 
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#remove(org.kuali.student.commons.ui.mvc.client.model.ModelObject)
     */
    public void remove(T modelObject) {
        allItems.remove(modelObject);
        if (selectedItemsModel.contains(modelObject)) {
            selectedItemsModel.remove(modelObject);
        }
    }

    /**
     * Selects an item in the "all items" list, also automatically adds it to the selected items
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#select(org.kuali.student.commons.ui.mvc.client.model.ModelObject)
     */
    public void select(T modelObject) {
        allItems.select(modelObject);
        addSelected();
    }

    /**
     * 
     * 
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#update(org.kuali.student.commons.ui.mvc.client.model.ModelObject)
     */
    public void update(T modelObject) {
        allItems.update(modelObject);
    }

    /**
     * 
     * This overridden method ...
     * 
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#clear()
     */
    public void clear() {
        allItems.clear();
        selectedItemsModel.clear();
    }

    /**
     * 
     * This overridden method ...
     * 
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#getItems()
     */
    public List<T> getItems() {
        return allItems.getItems();
    }

    /**
     * 
     * Should not actually be used, as the "selection" doesn't really matter outside of this component.
     * Use getSelectedItems() instead.
     * 
     * @see org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget#getSelection()
     */
    public T getSelection() {
        return allItems.getSelection();
    }

    /**
     * Resets the selected items list/model
     *
     */
    public void reset() {
        selectedItemsModel.clear();
    }
    
    /**
     * 
     * Returns a List<T> of all the items selected
     * 
     * @return List<T> of all the items selected
     */
    public List<T> getSelectedItems() {
        return selectedItemsModel.items();
    }

    public int getRowsPerPage() {
        return allItems.getRowsPerPage();
    }

    public void setRowsPerPage(int rowsPerPage) {
        allItems.setRowsPerPage(rowsPerPage);
        selectedItems.setRowsPerPage(rowsPerPage);
    }
        
    
}
