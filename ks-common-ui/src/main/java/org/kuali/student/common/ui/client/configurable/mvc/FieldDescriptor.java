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

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.RichTextEditor;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a field descriptor widget that defines ui fields.  
 * 
 *   a form field.
 * 
 * @author Kuali Student Team
 *
 */
public class FieldDescriptor {
    private String fieldKey;
    private String fieldLabel;
    private ModelDTOValue.Type fieldType;
    private Widget fieldWidget;
    private PropertyBinding propertyBinding;
    private PropertyBinding widgetBinding;
    private Callback<Boolean> validationRequestCallback;
    private RequiredEnum requiredState = RequiredEnum.NOT_MARKED;
    private boolean dirty = false;
    private boolean hasHadFocus = false;
    
    /**
     * @param fieldKey
     * @param fieldLabel
     * @param fieldType
     * @param fieldWidget
     */
    public FieldDescriptor(String fieldKey, String fieldLabel, ModelDTOValue.Type fieldType, Widget fieldWidget) {
        this.fieldKey = fieldKey;
        this.fieldLabel = fieldLabel;
        this.fieldType = fieldType;
        this.fieldWidget = fieldWidget;
        
    }

	public FieldDescriptor(String fieldKey, String fieldLabel, ModelDTOValue.Type fieldType, Widget fieldWidget, RequiredEnum requiredState) {
        this.fieldKey = fieldKey;
        this.fieldLabel = fieldLabel;
        this.fieldType = fieldType;
        this.fieldWidget = fieldWidget;
        this.requiredState = requiredState;
    }
    
    public FieldDescriptor(String fieldKey, String fieldLabel, ModelDTOValue.Type fieldType, RequiredEnum requiredState) {
        this.fieldKey = fieldKey;
        this.fieldLabel = fieldLabel;
        this.fieldType = fieldType;
        this.requiredState = requiredState;
    }

    public FieldDescriptor(String fieldKey, String fieldLabel, ModelDTOValue.Type fieldType) {
        this.fieldKey = fieldKey;
        this.fieldLabel = fieldLabel;
        this.fieldType = fieldType;
    }
    
    
    private void setWidgetStyle() {
    	//TODO temporary style name assigning on only textboxes
    	if(fieldKey != null){
    		String style = this.fieldKey.replaceAll("/", "-");
    		if(fieldWidget instanceof KSTextBox){
    			this.fieldWidget.addStyleName(style);
    		}
    	}
    	
		
    }

    public RequiredEnum getRequiredState() {
		return requiredState;
	}

	public void setRequiredState(RequiredEnum requiredState) {
		this.requiredState = requiredState;
	}

	public String getFieldKey() {
        return fieldKey;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public ModelDTOValue.Type getFieldType() {
        //This could do a dictionary lookup for type if none specified
        return fieldType;
    }
    
    public Widget getFieldWidget(){
        if (fieldWidget == null){
            //This could possible do a dictionary lookup to determine widget to use if none specified.
            switch (fieldType){
                case STRING: fieldWidget = new KSTextBox(); break;
                case INTEGER: fieldWidget = new KSTextBox();break;
            }
        }
        setWidgetStyle();
        return fieldWidget;
    }
    
    public void setPropertyBinding(PropertyBinding binding){
        propertyBinding = binding;
    }
    
    public PropertyBinding getPropertyBinding(){   
        if(propertyBinding == null){
            propertyBinding = new ModelDTOBinding(fieldKey);         
        }
        return propertyBinding;
    }

    public PropertyBinding getWidgetBinding() {
        if(widgetBinding == null){
            if(fieldWidget instanceof RichTextEditor){
            	widgetBinding = RichTextBinding.INSTANCE;
            }else if(fieldWidget instanceof HasModelDTOValue){
        		widgetBinding = HasModelDTOValueBinding.INSTANCE;
        	}else if (fieldWidget instanceof HasText) {
                widgetBinding = new HasTextBinding(fieldType);
            } else if (fieldWidget instanceof KSSelectItemWidgetAbstract){
                widgetBinding = SelectItemWidgetBinding.INSTANCE;
            } else if (fieldWidget instanceof HasValue){
                widgetBinding = HasValueBinding.INSTANCE;
            }
        }
        return widgetBinding;
    }

    public void setWidgetBinding(PropertyBinding widgetBinding) {
        
        this.widgetBinding = widgetBinding;
    }
    public void setValidationCallBack(Callback<Boolean> callback){
        validationRequestCallback = callback;
    }
    public Callback<Boolean> getValidationRequestCallback(){
        return validationRequestCallback;
    }

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public boolean hasHadFocus() {
		return hasHadFocus;
	}

	public void setHasHadFocus(boolean hasHadFocus) {
		this.hasHadFocus = hasHadFocus;
	}
    
    
}
