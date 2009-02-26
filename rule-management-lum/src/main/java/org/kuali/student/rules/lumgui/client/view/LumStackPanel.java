package org.kuali.student.rules.lumgui.client.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget;
import org.kuali.student.rules.lumgui.client.model.ILumModelObject;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.StackPanel;

public class LumStackPanel<T extends ILumModelObject> extends StackPanel implements ModelWidget<T> {

    private T modelObject;
    private String modelObjectFieldName;
    
    public LumStackPanel(String modelObjectFieldName) {
        this.modelObjectFieldName = modelObjectFieldName;
    }
    
    public void add(T modelObject) {
        this.modelObject = modelObject;
        showStack(((Integer)modelObject
                .getValue(modelObjectFieldName)).intValue());
    }

    public void addBulk(Collection<T> collection) {
        if (collection != null && !collection.isEmpty()) {
            this.modelObject = collection.iterator().next();
        }
    }

    public void clear() {
        modelObject = null;
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
    }

    public void select(T modelObject) {
    }

    public void update(T modelObject) {
        this.modelObject = modelObject;
        showStack(((Integer)modelObject
                .getValue(modelObjectFieldName)).intValue());
    }

}
