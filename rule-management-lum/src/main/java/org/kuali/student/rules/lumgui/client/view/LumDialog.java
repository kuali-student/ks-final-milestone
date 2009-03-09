package org.kuali.student.rules.lumgui.client.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget;
import org.kuali.student.rules.lumgui.client.model.ILumModelObject;
import org.kuali.student.rules.lumgui.client.model.LumModelObject;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class LumDialog<T extends ILumModelObject> implements ModelWidget<T> {

    private T modelObject;
    private String modelObjectFieldName;
    
    public LumDialog(String modelObjectFieldName) {
        this.modelObjectFieldName = modelObjectFieldName;
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
        /*
        this.modelObject = modelObject;
        super.center();
        boolean show = ((LumModelObject)modelObject).isShowRequirementDialog();
        if (show) {
            System.out.println("Showing dialog...");
            super.show();
        } else {
            super.hide();
        }  */     
    }
}
