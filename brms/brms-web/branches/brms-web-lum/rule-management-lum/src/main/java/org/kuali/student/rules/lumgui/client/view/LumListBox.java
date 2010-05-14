package org.kuali.student.rules.lumgui.client.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget;
import org.kuali.student.rules.lumgui.client.model.ILumModelObject;
import org.kuali.student.rules.lumgui.client.model.LumModelObject;
import org.kuali.student.rules.lumgui.client.model.ReqComponentTypeInfo;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class LumListBox<T extends ILumModelObject> extends ListBox implements ModelWidget<T> {
    
    private T modelObject;
    private String modelObjectFieldName;
    private TextArea exampleText;
    private TextArea requirementCompositionText;
    
    public LumListBox(String modelObjectfieldName, boolean multipleSelectionEnabled, TextArea exampleText, TextArea requirementCompositionText) {
        this.modelObjectFieldName = modelObjectfieldName; 
        this.setMultipleSelect(multipleSelectionEnabled);
        this.exampleText = exampleText;
        this.requirementCompositionText = requirementCompositionText;
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
        for (int i = 0; i < this.getItemCount(); i++) {
            this.removeItem(i);
        }
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
        if (this.getSelectedIndex() != -1) {
            exampleText.setText(this.getValue(this.getSelectedIndex()));
            requirementCompositionText.setText(exampleText.getText() + "\n\n" + "Minimum GPA = [  ] for course [  ]");            
        }
    }
}
