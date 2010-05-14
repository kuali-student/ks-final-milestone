package org.kuali.student.rules.lumgui.client.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;
import org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget;
import org.kuali.student.rules.lumgui.client.model.ILumModelObject;
import org.kuali.student.rules.lumgui.client.model.LumModelObject;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.Widget;

public class LumSwitchPanel<T extends ILumModelObject> extends SimplePanel implements ModelWidget<T> {

    private T modelObject;
    private String modelObjectFieldName;
    private List<Widget> switchableWidgets = new ArrayList<Widget>(7);
    
    public LumSwitchPanel(String modelObjectFieldName) {
        this.modelObjectFieldName = modelObjectFieldName;
    }
    
    public void add(Widget widget) {
        switchableWidgets.add(widget);
        showStack(switchableWidgets.size() - 1);
    }
    
    public void add(T modelObject) {
        this.modelObject = modelObject;
        showStack(((Integer)modelObject
                .getValue(modelObjectFieldName)).intValue());
    }
    
    public void showStack(int index) {
        Widget currentWidget = getWidget();
        if (currentWidget != null &&
                currentWidget != switchableWidgets.get(index)) {
            super.remove(getWidget());
            super.add(switchableWidgets.get(index));
        } else if (currentWidget == null) {
            super.add(switchableWidgets.get(index));
        }
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
        showStack(((Integer)modelObject.getValue(modelObjectFieldName)).intValue());
    }

}
