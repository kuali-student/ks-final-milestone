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
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.RichTextEditor;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.MetadataInterrogator;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a field descriptor widget that defines ui fields.
 *
 * @author Kuali Student Team
 *
 */
public class FieldDescriptor {
    protected String fieldKey;
    protected Metadata metadata;
    @SuppressWarnings("unchecked")
	private ModelWidgetBinding modelWidgetBinding;
    private Callback<Boolean> validationRequestCallback;
    private boolean dirty = false;
    private boolean hasHadFocus = false;
    private FieldElement fieldElement;
    private String modelId;
    private MessageKeyInfo messageKey;

    public FieldDescriptor(String fieldKey, MessageKeyInfo messageKey, Metadata metadata) {
    	this.fieldKey = fieldKey;
    	this.metadata = metadata;
    	if(messageKey == null){
    		messageKey = new MessageKeyInfo("");
    	}
    	setMessageKey(messageKey);
    	fieldElement = new FieldElement(fieldKey, messageKey, createFieldWidget());
    	setupField();
    }

    public FieldDescriptor(String fieldKey, MessageKeyInfo messageKey, Metadata metadata, Widget fieldWidget){
    	this.fieldKey = fieldKey;
    	this.metadata = metadata;
    	if(messageKey == null){
    		messageKey = new MessageKeyInfo("");
    	}
        setMessageKey(messageKey);
    	addStyleToWidget(fieldWidget);
    	fieldElement = new FieldElement(fieldKey, messageKey, fieldWidget);
    	setupField();
    }
    
    protected void addStyleToWidget(Widget w){
    	if(fieldKey != null && !fieldKey.isEmpty() && w != null){
    		String style = this.fieldKey.replaceAll("/", "-");
    		w.addStyleName(style);
    	}
    }

    private void setupField(){
    	if(metadata != null){
	    	fieldElement.setRequired(MetadataInterrogator.isRequired(metadata));
	    	//TODO setup the constraint text here
    	}
    }
    
    public void showLabel(boolean show){
    	fieldElement.showLabel(show);
    }

    public FieldElement getFieldElement(){
    	return fieldElement;
    }

	public String getFieldKey() {
        return fieldKey;
    }

    public String getFieldLabel() {
        return fieldElement.getFieldName();
    }

    public Widget getFieldWidget(){
        if (fieldElement.getFieldWidget() == null){
            Widget w = createFieldWidget();
            fieldElement.setWidget(w);
        }
        return fieldElement.getFieldWidget();
    }

    protected Widget createFieldWidget() {
    	if (metadata == null) {
    		// backwards compatibility for old ModelDTO code
	    	// for now, default to textbox if not specified
	    	Widget result = new KSTextBox();
	    	addStyleToWidget(result);
	    	return result;
    	} else {
    		Widget result = DefaultWidgetFactory.getInstance().getWidget(this);
    		addStyleToWidget(result);
    		return result;
    	}
    }

    public ModelWidgetBinding<?> getModelWidgetBinding() {
        if(modelWidgetBinding == null){
            if(fieldElement.getFieldWidget() instanceof RichTextEditor){
            	modelWidgetBinding = org.kuali.student.common.ui.client.configurable.mvc.binding.RichTextBinding.INSTANCE;
            } else if (fieldElement.getFieldWidget() instanceof KSCheckBox){
            	modelWidgetBinding = org.kuali.student.common.ui.client.configurable.mvc.binding.HasValueBinding.INSTANCE;
            } else if(fieldElement.getFieldWidget() instanceof MultiplicityComposite){
        		modelWidgetBinding = MultiplicityCompositeBinding.INSTANCE;
        	} else if (fieldElement.getFieldWidget()instanceof HasText) {
        	    modelWidgetBinding = org.kuali.student.common.ui.client.configurable.mvc.binding.HasTextBinding.INSTANCE;
            } else if (fieldElement.getFieldWidget() instanceof KSSelectItemWidgetAbstract){
                modelWidgetBinding = org.kuali.student.common.ui.client.configurable.mvc.binding.SelectItemWidgetBinding.INSTANCE;
            } else if (fieldElement.getFieldWidget() instanceof HasDataValue){
            	modelWidgetBinding = org.kuali.student.common.ui.client.configurable.mvc.binding.HasDataValueBinding.INSTANCE;
            } else if (fieldElement.getFieldWidget() instanceof HasValue){
            	modelWidgetBinding = org.kuali.student.common.ui.client.configurable.mvc.binding.HasValueBinding.INSTANCE;
            } 
        }
        return modelWidgetBinding;
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
		this.fieldElement.setWidget(fieldWidget);
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

    public void setWidgetBinding(ModelWidgetBinding widgetBinding) {
        this.modelWidgetBinding = widgetBinding;
    }

    public MessageKeyInfo getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(MessageKeyInfo messageKey) {
        this.messageKey = messageKey;
    }

}
