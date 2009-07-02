package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValueBinder;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.core.validation.dto.ValidationResult.ErrorLevel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class VerticalNestedSection extends NestedSection{
    
    protected final VerticalPanel panel = new VerticalPanel();
    private KSFormLayoutPanel form = null;
    
    public VerticalNestedSection(){
        super.initWidget(panel);
    }
    
    
    @Override
    public void updateModel(Model<ModelDTO> model) {
        ModelDTO modelDTO = model.get();
        for (int i=0; i < fields.size(); i++){
            FieldDescriptor field = (FieldDescriptor)fields.get(i);
            String fieldKey = field.getFieldKey();
            ModelDTOValue modelDTOValue = modelDTO.get(fieldKey);
            if (modelDTOValue  != null){
                ModelDTOValueBinder.copyValueToModelDTO(form.getFieldValue(fieldKey), modelDTOValue);
            } else {
                modelDTOValue = ModelDTOValueBinder.createModelDTOInstance(form.getFieldValue(fieldKey), field.getFieldType());
                modelDTO.put(fieldKey, modelDTOValue);
            }
        } 
        
    }

    @Override
    public void validate(Callback<ErrorLevel> callback) {

    }

    @Override
    public void clear() {
        // TODO bsmith - THIS METHOD NEEDS JAVADOCS
        
    }

    @Override
    public void redraw() {
        panel.clear();
        panel.add(sectionTitleLabel);
        panel.add(instructionsLabel);
        form = new KSFormLayoutPanel();
        panel.add(form);
        for(Object o: orderedLayoutList){
            if(o instanceof NestedSection){
                NestedSection ns = ((NestedSection) o);
                ns.redraw();
                panel.add(ns);
            }
            else if(o instanceof FieldDescriptor){
                FieldDescriptor field = (FieldDescriptor)o;
                KSFormField formField = new KSFormField(field.getFieldKey(), field.getFieldLabel());
                formField.setWidget(field.getFieldWidget());
                form.addFormField(formField);
            }
        }
    }

}
