package org.kuali.student.common.ui.client.validator;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.Section;
import org.kuali.student.common.validator.ConstraintDataProvider;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class SectionConstraintDataProvider {
    private ConstraintDataProvider model = null;
    private Section section = null;

    // TODO add constructors/setters for passing these in
    public SectionConstraintDataProvider(Section s,ConstraintDataProvider model){
        
    }
 // question
    public String getObjectId() {
        return "";
    }

    public Object getValue(String fieldKey) {
        Object result = getValueFromSectionIfExists(fieldKey);
        if (result == null) {
            result = model.getValue(fieldKey);
        }
        return result;
    }

    public Object getValueFromSectionIfExists(String fieldKey) {
        List<FieldDescriptor> fdList = section.getFields();
        for(FieldDescriptor fd: fdList){
            if(fd.getFieldKey() != null && fd.getFieldKey().equals(fieldKey)){
                Widget w = fd.getFieldWidget();
                if(w instanceof TextBox){
                    TextBox textBox = (TextBox)w;
                    return textBox.getValue();
                }
            }
        }
        
        return null;
    }

    public Boolean hasField(String fieldKey) {
        if(model.hasField(fieldKey)){
            return true;
        }
        List<FieldDescriptor> fdList = section.getFields();
        for(FieldDescriptor fd: fdList){
            if(fd.getFieldKey() != null && fd.getFieldKey().equals(fieldKey)){
               return true;
            }
        }
        
        return false;
    }
// question
    public void initialize(Object o) {
        model.initialize(o);
    }
}
