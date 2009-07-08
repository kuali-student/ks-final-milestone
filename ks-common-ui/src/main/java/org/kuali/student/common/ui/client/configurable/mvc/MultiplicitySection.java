/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;

import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValueBinder;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is a section that can be added to a MultiplictyComposite. It is a configurable
 * section which allows the section to be defined by adding fields and nested sections.
 * 
 * Although this implements the HasModelDTOValue interface, it only supports
 * the ModelDTOValue type of MODELDTO.
 * 
 * 
 * @author Kuali Student Team
 * 
 */
public class MultiplicitySection extends Section implements HasModelDTOValue{

    private ModelDTOValue modelDTOValue; 
    protected final VerticalPanel panel;
    private KSFormLayoutPanel form = null;
    private String modelDtoClassName;

    public MultiplicitySection(String modelDtoClassName){
        fields = new ArrayList<FieldDescriptor>();
        panel = new VerticalPanel();
        this.modelDtoClassName = modelDtoClassName;
        initWidget(panel);
    }
    
    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.ConfigurableLayoutSection#addField(org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor)
     */
    @Override
    public void addField(FieldDescriptor fieldDescriptor) {
        super.addField(fieldDescriptor);
        
        if (form == null){            
            form = new KSFormLayoutPanel();
        }
        
        panel.add(form);           
        if (fieldDescriptor.getFieldWidget() instanceof MultiplicityComposite){
            MultiplicityComposite listField = (MultiplicityComposite)fieldDescriptor.getFieldWidget(); 
            listField.redraw();
            panel.add(listField);                   
        } else {
            KSFormField formField = new KSFormField(fieldDescriptor.getFieldKey(), fieldDescriptor.getFieldLabel());
            formField.setWidget(fieldDescriptor.getFieldWidget());
            
            //Need for some way to obtain the help info key
            formField.setHelpInfo(new HelpInfo(fieldDescriptor.getFieldKey()));
            form.addFormField(formField);
        }
        
    }
    
    @Override
    public void addSection(NestedSection section) {
        super.addSection(section);
        section.redraw();
        panel.add(section);
    }
    

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue#getModelDTOValue()
     */
    @Override
    public ModelDTOValue getModelDTOValue() {
        if (modelDTOValue == null){
            modelDTOValue = new ModelDTOValue.ModelDTOType();
            ((ModelDTOValue.ModelDTOType)modelDTOValue).set(new ModelDTO(modelDtoClassName));
        }
        
        return this.modelDTOValue;
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue#setModelDTOValue(org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue)
     * 
     * Set the ModelDTOValue for the multiplicity section. The ModelDTOValue must be of type MODELDTO.
     */
    @Override
    public void setModelDTOValue(ModelDTOValue modelDTOValue) {
        assert(modelDTOValue instanceof ModelDTOValue.ModelDTOType);
        this.modelDTOValue = modelDTOValue;

        redraw();
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue#updateModelDTOValue()
     */
    @Override
    public void updateModelDTOValue() {        
        ModelDTO modelDTO = ((ModelDTOValue.ModelDTOType)modelDTOValue).get();
        for (int i=0; i < fields.size(); i++){
            FieldDescriptor field = (FieldDescriptor)fields.get(i);
            
            if (field.getFieldWidget() instanceof MultiplicityComposite){
                ((HasModelDTOValue)field.getFieldWidget()).updateModelDTOValue();
            } else {
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
    }

    public void clear(){
        //TODO: How should a section be cleared, reset contained widgets or remove all widgets?
    }
    
    /**
     * 
     * This method redraws the widget
     *
     */
    public void redraw(){
        if (modelDTOValue != null){
            ModelDTO modelDTO = ((ModelDTOValue.ModelDTOType)modelDTOValue).get();
            
            for (int i=0; i < fields.size(); i++){
                FieldDescriptor field = fields.get(i);
                ModelDTOValue modelDTOValue = modelDTO.get(field.getFieldKey());
                ModelDTOValueBinder.copyValueFromModelDTO(modelDTOValue, field.getFieldWidget());
            }
        }
    }

	@Override
	public void validate(Callback<ValidationResultInfo.ErrorLevel> callback) {
		// TODO Auto-generated method stub
		
	}
}
