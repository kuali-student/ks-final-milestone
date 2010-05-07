/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.MultiplicityCompositeBinding;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityComposite;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.RichTextEditor;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.core.assembly.data.Metadata;

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
    private Metadata metadata;
    private Widget fieldWidget;
    private ModelWidgetBinding modelWidgetBinding;
    private Callback<Boolean> validationRequestCallback;
    private RequiredEnum requiredState = RequiredEnum.NOT_MARKED;
    private boolean dirty = false;
    private boolean hasHadFocus = false;
    private String modelId; 
    
    public FieldDescriptor(String fieldKey, String fieldLabel, Metadata metadata) {
    	this.fieldKey = fieldKey;
    	this.fieldLabel = fieldLabel;
    	this.metadata = metadata;
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
   
    public Widget getFieldWidget(){
        if (fieldWidget == null){
            fieldWidget = createFieldWidget();
        }
        return fieldWidget;
    }
    
    protected Widget createFieldWidget() {
    	if (metadata == null) {
    		// backwards compatibility for old ModelDTO code
	    	// for now, default to textbox if not specified
	    	Widget result = new KSTextBox();
	    	if(fieldKey != null){
	    		String style = this.fieldKey.replaceAll("/", "-");
	    		result.addStyleName(style);
	    	}
	    	return result;
    	} else {
    		return DefaultWidgetFactory.getInstance().getWidget(this);
    	}
    }
    
    public ModelWidgetBinding<?> getModelWidgetBinding() {
        if(modelWidgetBinding == null){
            if(fieldWidget instanceof RichTextEditor){
            	modelWidgetBinding = org.kuali.student.common.ui.client.configurable.mvc.binding.RichTextBinding.INSTANCE;
            }else if(fieldWidget instanceof MultiplicityComposite){
        		modelWidgetBinding = MultiplicityCompositeBinding.INSTANCE;
        	}else if (fieldWidget instanceof HasText) {
        	    modelWidgetBinding = org.kuali.student.common.ui.client.configurable.mvc.binding.HasTextBinding.INSTANCE;
            } else if (fieldWidget instanceof KSSelectItemWidgetAbstract){
                modelWidgetBinding = org.kuali.student.common.ui.client.configurable.mvc.binding.SelectItemWidgetBinding.INSTANCE;
            } else if (fieldWidget instanceof HasDataValue){
            	modelWidgetBinding = org.kuali.student.common.ui.client.configurable.mvc.binding.HasDataValueBinding.INSTANCE;
            } else if (fieldWidget instanceof HasValue){
            	modelWidgetBinding = org.kuali.student.common.ui.client.configurable.mvc.binding.HasValueBinding.INSTANCE;
            }
        }
        return modelWidgetBinding;
    }

    public void setWidgetBinding(ModelWidgetBinding widgetBinding) {        
        this.modelWidgetBinding = widgetBinding;
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
	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	public void setFieldWidget(Widget fieldWidget) {
		this.fieldWidget = fieldWidget;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
    
    
}
