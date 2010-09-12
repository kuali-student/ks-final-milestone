package org.kuali.student.rules.lumgui.client.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget;
import org.kuali.student.rules.lumgui.client.model.ILumModelObject;

import com.google.gwt.user.client.ui.TextBox;

public class LumTextBox<T extends ILumModelObject> extends TextBox implements ModelWidget<T>{
    
    private T modelObject;
    private String modelObjectFieldName;
    
    public LumTextBox(String modelObjectfieldName) {
        this.modelObjectFieldName = modelObjectfieldName; 
    }
    
    public void add(T modelObject) {
        this.modelObject = modelObject;
    }

    public void addBulk(Collection<T> collection) {
        if (collection != null && !collection.isEmpty()) {
            this.modelObject = collection.iterator().next();
        }
    }

    public void clear() {
        modelObject = null;
        setText("");
    }

    public List<T> getItems() {
        List<T> items = new ArrayList<T>();
        items.add(modelObject);
        return items;
    }

    public T getSelection() {
        return modelObject;
    }

    public void remove(T modelObject) {
        clear();
    }

    public void select(T modelObject) {
    }

    public void update(T modelObject) {
        this.modelObject = modelObject;
        setText((String)modelObject.getValue(modelObjectFieldName));
    }

}
