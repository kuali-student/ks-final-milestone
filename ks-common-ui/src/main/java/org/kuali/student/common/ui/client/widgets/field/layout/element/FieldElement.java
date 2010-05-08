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

package org.kuali.student.common.ui.client.widgets.field.layout.element;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.sections.ValidationMessagePanel;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTitleDescPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton.AbbrButtonType;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.FieldLayoutComponent;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class FieldElement extends Composite implements FieldLayoutComponent{
	
	//Layout
	private KSTitleDescPanel titlePanel = new KSTitleDescPanel();
	private FlowPanel layout = new FlowPanel();
	private FieldTitle fieldTitle;
	private SpanPanel description = new SpanPanel();
	private SpanPanel instructions = new SpanPanel();
	private AbbrPanel required = new AbbrPanel("Required", "ks-form-module-elements-required", "*");
	private AbbrButton help = new AbbrButton(AbbrButtonType.HELP);
	public static enum TopMargin{SINGLE, DOUBLE, TRIPLE}
	
	private ValidationMessagePanel validationPanel;
	private String fieldKey;
	
	public void setValidationPanel(ValidationMessagePanel validationPanel) {
		this.validationPanel = validationPanel;
	}

	private Panel parentPanel;
	public Panel getParentPanel() {
		return parentPanel;
	}

	public void setParentPanel(Panel parentPanel) {
		this.parentPanel = parentPanel;
	}

	private String fieldName;
	private String descriptionText;

    public String getDescriptionText() {
		return descriptionText;
	}

	public FieldElement(String title, Widget widget) {
    	generateLayout(null, title, widget);

    }
    
    public FieldElement(String key, String title, Widget widget){
    	generateLayout(key, title, widget);
    }
    
    private void generateLayout(String key, String title, Widget widget){
    	this.setKey(key);
    	if(widget != null){
    		fieldName = title;
    		//TODO do widget type check here
    		String widgetId = HTMLPanel.createUniqueId();
    		fieldTitle = new LabelPanel(title, widgetId);
    		widget.getElement().setAttribute("id", widgetId);
    		
    		required.setVisible(false);
    		fieldTitle.add(required);
    		help.setVisible(false);
    		fieldTitle.add(help);
    		layout.add(fieldTitle);
    		description.setVisible(false);
    		description.setStyleName("ks-form-module-elements-instruction");
    		layout.add(description);
    		layout.add(widget);
    		instructions.setVisible(false);
    		instructions.setStyleName("ks-form-module-elements-help-text");
    		layout.add(instructions);
    	}
        
        initWidget(layout);
        layout.addStyleName("ks-form-module-elements");
    }
    
    public void setRequired(boolean isRequired){
    	required.setVisible(isRequired);
    }
    
    public void setDescription(String text){
    	descriptionText = text;
    	if(text != null && !text.equals("")){
    		description.setText(text);
    		description.setVisible(true);
    	}
    }
    
    public void setInstructions(String text){
    	if(text != null && !text.equals("")){
    		instructions.setText(text);
    		instructions.setVisible(true);
    	}
    }
    
    public void setHelp(final String html){
    	if(html != null && !html.equals("")){
    		help.setVisible(true);
    		help.setHoverHTML(html);
    		help.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					//TODO show actual help window
					Window.alert(html);
					
				}
			});
    	}
    	else{
    		help.setVisible(false);
    	}
    }

/*	public FieldElement(FieldDescriptor fieldDescriptor, boolean topMargin){
		if(topMargin){
			if(fieldDescriptor.getFieldLabel() != null){
				this.titlePanel.setTitleText(fieldDescriptor.getFieldLabel());
				this.titlePanel.getTitleWidget().addStyleName("ks-form-module-single-line-margin");
			}
			else{
				fieldDescriptor.getFieldWidget().addStyleName("ks-form-module-double-line-margin");
			}

		}
		else{
			if(fieldDescriptor.getFieldLabel() != null){
				this.titlePanel.setTitleText(fieldDescriptor.getFieldLabel());
				this.titlePanel.getTitleWidget().addStyleName("ks-form-module-no-line-margin");
			}
			else{
				fieldDescriptor.getFieldWidget().addStyleName("ks-form-module-single-line-margin");
			}

		}
		this.setKey(fieldDescriptor.getFieldKey());

		titlePanel.getDescWidget().addStyleName("ks-form-module-elements-instruction");
		layout.add(titlePanel);
		layout.add(fieldDescriptor.getFieldWidget());
		this.initWidget(layout);
		layout.addStyleName("ks-form-module-elements");
	}

	public FieldElement(FieldDescriptor fieldDescriptor){
		this(fieldDescriptor, false);
	}*/
	
	public Widget getTitleWidget() {
	    return titlePanel.getTitleWidget();
	}
	
	public ValidationMessagePanel getValidationPanel() {
		return validationPanel;
	}
	
	public Panel getEncapsulatingPanel() {
		return parentPanel;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setErrorState(boolean error){
		if(error){
			layout.addStyleName("invalid");
			parentPanel.addStyleName("error");
		}
		else{
			layout.removeStyleName("invalid");
			parentPanel.removeStyleName("error");
		}
		
	}
	
	public ErrorLevel processValidationResults(List<ValidationResultInfo> validationResults) {
		
		ErrorLevel status = ErrorLevel.OK;
		for(ValidationResultInfo vr: validationResults){
			KSLabel message;
			if(fieldName != null){
				message = new KSLabel(fieldName + " - " + vr.getMessage());
			}
			else{
				message = new KSLabel(vr.getMessage());
			}
			
    		if(vr.getLevel() == ErrorLevel.ERROR){
    			message.setStyleName("ks-form-validation-label");
    			
    			this.validationPanel.addMessage(message);
    			this.setErrorState(true);
    			if(status.getLevel() < ErrorLevel.ERROR.getLevel()){
    				status = vr.getLevel();
    			}
    			
    		}
    		else if(vr.getLevel() == ErrorLevel.WARN){
    			if(status.getLevel() < ErrorLevel.WARN.getLevel()){
    				status = vr.getLevel();
    			}
    			//message.addStyleName("KS-Validation-Warning-Message");
    		}
    		else{
    			//message.addStyleName("KS-Validation-Ok-Message");
    		}
        }
		
		return status;
	}
	
	public void addValidationErrorMessage(String text){
		KSLabel message;
		if(fieldName != null){
			message = new KSLabel(fieldName + " - " + text);
		}
		else{
			message = new KSLabel(text);
		}
		message.setStyleName("ks-form-validation-label");
		this.setErrorState(true);
		this.validationPanel.addMessage(message);
	}
	
	public void clearValidationPanel(){
		this.setErrorState(false);
		this.validationPanel.clear();
	}

	@Override
	public String getKey() {
		return fieldKey;
	}

	@Override
	public void setKey(String key) {
    	if(key == null){
			//TODO better way to generate unique id here?
			key = HTMLPanel.createUniqueId();
		}
		this.fieldKey = key;		
	}

	public void setTopMargin(TopMargin margin) {
		switch(margin){
			case TRIPLE:
				if((fieldTitle != null && !fieldTitle.equals("")) && 
						(descriptionText != null && !descriptionText.equals(""))){
					layout.addStyleName("ks-form-module-single-line-margin");
				}
				else if((fieldTitle != null && !fieldTitle.equals("")) || 
						(descriptionText != null && !descriptionText.equals(""))){
					layout.addStyleName("ks-form-module-double-line-margin");
				}
				else{
					layout.addStyleName("ks-form-module-triple-line-margin");
				}
	
				break;
			case DOUBLE:
				if((fieldTitle != null && !fieldTitle.equals("")) || 
						(descriptionText != null && !descriptionText.equals(""))){
					layout.addStyleName("ks-form-module-single-line-margin");
				}
				else{
					layout.addStyleName("ks-form-module-double-line-margin");
				}
				break;
			case SINGLE:
				layout.addStyleName("ks-form-module-single-line-margin");
				break;
		}

	}
}
