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
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValueBinder;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is a list item to be used for ListField. A list item must be associated
 * with a ModelDTO 
 * 
 * @author Kuali Student Team
 *
 */
public class MultiplicityItem extends Composite implements ConfigurableLayoutSection, HasModelDTOValue{

    private ModelDTOValue modelDTOValue; 
    protected final VerticalPanel panel;
    private KSFormLayoutPanel form = null;
    private ArrayList<FieldDescriptor> fields;
    private String modelDtoClassName;

    public MultiplicityItem(String modelDtoClassName){
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
        fields.add(fieldDescriptor);
        if (form == null){            
            form = new KSFormLayoutPanel();
        }
        
        panel.add(form);           
        if (fieldDescriptor.getFieldWidget() instanceof MultiplicityComposite){
            MultiplicityComposite listField = (MultiplicityComposite)fieldDescriptor.getFieldWidget(); 
            listField.init();
            panel.add(listField);                   
        } else {
            KSFormField formField = new KSFormField(fieldDescriptor.getFieldKey(), fieldDescriptor.getFieldLabel());
            formField.setWidget(fieldDescriptor.getFieldWidget());
            
            //Need for some way to obtain the help info key
            formField.setHelpInfo(new HelpInfo(fieldDescriptor.getFieldKey()));
            form.addFormField(formField);
        }
        
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.ConfigurableLayoutSection#addSection(java.lang.String, org.kuali.student.common.ui.client.configurable.mvc.ConfigurableLayoutSection)
     */
    @Override
    public void addSection(String fieldKey, ConfigurableLayoutSection section) {
        // TODO Ability to add grouping of fields        
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
     */
    @Override
    public void setModelDTOValue(ModelDTOValue modelDTOValue) {
        assert(modelDTOValue instanceof ModelDTOValue.ModelDTOType);
        this.modelDTOValue = modelDTOValue;

        refresh();
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

    private void refresh(){
        ModelDTO modelDTO = ((ModelDTOValue.ModelDTOType)modelDTOValue).get();
        
        for (int i=0; i < fields.size(); i++){
            FieldDescriptor field = fields.get(i);
            ModelDTOValue modelDTOValue = modelDTO.get(field.getFieldKey());
            ModelDTOValueBinder.copyValueFromModelDTO(modelDTOValue, field.getFieldWidget());
        }                                   
    }
}
