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

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.Section.FieldLabelType;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSRequiredMarker;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class RowDescriptor extends Composite{
    private HorizontalBlockFlowPanel rowPanel = new HorizontalBlockFlowPanel();
    private HorizontalBlockFlowPanel columnLayout =  new HorizontalBlockFlowPanel();
    private VerticalFlowPanel validationPanel = new VerticalFlowPanel();
    private List<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
    private List<Section> sections = new ArrayList<Section>();
    
    protected FieldLabelType currentFieldLabelType = FieldLabelType.LABEL_TOP;
    
    public RowDescriptor(){
    	rowPanel.addStyleName("KS-Field-Column");
    	validationPanel.addStyleName("KS-Validation-Column");
    	columnLayout.addStyleName("KS-Configurable-Row");
    	columnLayout.add(rowPanel);
    	columnLayout.add(validationPanel);
        this.initWidget(columnLayout);
    }
    
    public void addSection(Section section){
        sections.add(section);
        rowPanel.add(section);
    }
    
    public void addField(FieldDescriptor fieldDescriptor){
        fields.add(fieldDescriptor);
        if (fieldDescriptor.getFieldWidget() instanceof SimpleMultiplicityComposite){
            SimpleMultiplicityComposite listField = (SimpleMultiplicityComposite) fieldDescriptor.getFieldWidget(); 
            listField.redraw();
            rowPanel.add(listField);
        }
        else{   
            KSLabel label = new KSLabel(fieldDescriptor.getFieldLabel());
            //label.addStyleName(KSStyles.KS_FORMLAYOUT_LABEL);
            if(currentFieldLabelType == FieldLabelType.LABEL_LEFT){
                if(!(label.getText().equals("")) && label.getText() != null){
                    rowPanel.add(label);
                }
                rowPanel.add(fieldDescriptor.getFieldWidget());
            }
            else if(currentFieldLabelType == FieldLabelType.LABEL_TOP){
                if(!(label.getText().equals("")) && label.getText() != null){
                    FlowPanel vp = new FlowPanel();
                    vp.add(label);
                    vp.add(fieldDescriptor.getFieldWidget());
                    rowPanel.add(vp);
                }
                else{
                    rowPanel.add(fieldDescriptor.getFieldWidget());
                }
            }
            rowPanel.add(new KSRequiredMarker(fieldDescriptor.getRequiredState()));
        }
    }

    public void addWidget(Widget widget){
        rowPanel.add(widget);
    }
    public List<FieldDescriptor> getFields() {
        return fields;
    }

    public List<Section> getSections() {
        return sections;
    }

    public FieldLabelType getCurrentFieldLabelType() {
        return currentFieldLabelType;
    }

    public void setCurrentFieldLabelType(FieldLabelType currentFieldLabelType) {
        this.currentFieldLabelType = currentFieldLabelType;
    }   
    
    public void setValidationMessages(List<ValidationResultInfo> list){
    	for(ValidationResultInfo vr: list){
    		HorizontalBlockFlowPanel validationLine = new HorizontalBlockFlowPanel();
    		VerticalFlowPanel imagePanel = new VerticalFlowPanel();
    		KSLabel message = new KSLabel(vr.getMessage());
    		KSImage image;
    		message.setWordWrap(true);
    		if(vr.getLevel() == ErrorLevel.ERROR){
    			message.addStyleName("KS-Validation-Error-Message");
    			image = Theme.INSTANCE.getCommonImages().getErrorIcon();
    			
    		}
    		else if(vr.getLevel() == ErrorLevel.WARN){
    			message.addStyleName("KS-Validation-Warning-Message");
    			image = Theme.INSTANCE.getCommonImages().getWarningIcon();
    		}
    		else{
    			message.addStyleName("KS-Validation-Ok-Message");
    			image = Theme.INSTANCE.getCommonImages().getOkIcon();
    		}
    		imagePanel.add(image);
    		message.addStyleName("KS-Validation-Message");
    		validationLine.addStyleName("KS-Validation-Line");
    		image.addStyleName("KS-Validation-Image");
    		imagePanel.addStyleName("KS-Validation-Image-Panel");
    		validationLine.add(imagePanel);
    		validationLine.add(message);
    		validationPanel.add(validationLine);
        }
    }
    
    public void clearValidationMessages(){
    	validationPanel.clear();
    }
    
    public void clear(){
        clearValidationMessages();
        for (Section s:sections){
            s.clear();
        }
        for (FieldDescriptor fd:fields){
            //TODO: Only resettting multplicity composite, should approporiately reset clear all field widgets
            Widget field = fd.getFieldWidget();
            if (field instanceof SimpleMultiplicityComposite){
                ((SimpleMultiplicityComposite) field).clear();
            }
        }
    }
}
