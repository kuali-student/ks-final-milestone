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

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.sections.ValidationMessagePanel;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTitleDescPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton.AbbrButtonType;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.FieldLayoutComponent;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
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
	private SpanPanel instructions = new SpanPanel();
	private SpanPanel constraintText = new SpanPanel();
	private AbbrPanel required = new AbbrPanel("Required", "ks-form-module-elements-required", " * ");
	private AbbrButton help = new AbbrButton(AbbrButtonType.HELP);
	private Widget fieldWidget;
	private SpanPanel widgetSpan = new SpanPanel();
	private String fieldHTMLId;
	private LineNum margin;
	public static enum LineNum{SINGLE, DOUBLE, TRIPLE}

	private ValidationMessagePanel validationPanel;
	private String fieldKey;

	public void setValidationPanel(ValidationMessagePanel validationPanel) {
		this.validationPanel = validationPanel;
	}

	private Panel parentPanel;
	private Element parentElement;
	public Panel getParentPanel() {
		return parentPanel;
	}

	public void setParentPanel(Panel parentPanel) {
		this.parentPanel = parentPanel;
		this.parentElement = parentPanel.getElement();
	}

	public void setParentElement(Element element){
		parentElement = element;
	}

	private String fieldName;
	private String instructionText;

    public String getInstructionText() {
		return instructionText;
	}

	public FieldElement(String title, Widget widget) {
    	generateLayout(null, title, null, null, widget);

    }

    public FieldElement(String key, String title, Widget widget){
    	generateLayout(key, title, null, null, widget);
    }

    public FieldElement(String key, MessageKeyInfo info){
    	String title = Application.getApplicationContext().getUILabel(info.getGroup(), info.getType(), info.getState(), info.getId());
    	String help = Application.getApplicationContext().getUILabel(info.getGroup(), info.getType(), info.getState(),
    			info.getId() + HELP_MESSAGE_KEY);
    	if(help.equals(info.getId() + HELP_MESSAGE_KEY)){
    		help = null;
    	}
    	String instructions = Application.getApplicationContext().getUILabel(info.getGroup(), info.getType(), info.getState(),
    			info.getId() + INSTRUCT_MESSAGE_KEY);
    	if(instructions.equals(info.getId() + INSTRUCT_MESSAGE_KEY)){
    		instructions = null;
    	}
    	generateLayout(key, title, help, instructions, null);
    }

    public FieldElement(String key, MessageKeyInfo info, Widget widget){
    	String title = Application.getApplicationContext().getUILabel(info.getGroup(), info.getType(), info.getState(), info.getId());
    	String help = Application.getApplicationContext().getUILabel(info.getGroup(), info.getType(), info.getState(),
    			info.getId() + HELP_MESSAGE_KEY);
    	if(help.equals(info.getId() + HELP_MESSAGE_KEY)){
    		help = null;
    	}
    	String instructions = Application.getApplicationContext().getUILabel(info.getGroup(), info.getType(), info.getState(),
    			info.getId() + INSTRUCT_MESSAGE_KEY);
    	if(instructions.equals(info.getId() + INSTRUCT_MESSAGE_KEY)){
    		instructions = null;
    	}
    	generateLayout(key, title, help, instructions, widget);
    }

    private void generateLayout(String key, String title, String helpText, String instructText, Widget widget){
    	this.setKey(key);
		fieldName = title;

		fieldHTMLId = HTMLPanel.createUniqueId();
		fieldTitle = new LabelPanel(title, fieldHTMLId);

		required.setVisible(false);
		fieldTitle.add(required);
		if(helpText != null){
			this.setHelp(helpText);
		}
		else{
			help.setVisible(false);
		}

		fieldTitle.add(help);
		layout.add(fieldTitle);
		if(instructText != null){
			this.setInstructions(instructText);
		}
		else{
			instructions.setVisible(false);
		}
		instructions.setStyleName("ks-form-module-elements-instruction");
		layout.add(instructions);
		layout.add(widgetSpan);
		if(widget != null){
			this.setWidget(widget);

		}
		constraintText.setVisible(false);
		constraintText.setStyleName("ks-form-module-elements-help-text");
		layout.add(constraintText);


        initWidget(layout);
        layout.addStyleName("ks-form-module-elements");
        layout.addStyleName("ks-form-module-single-line-margin");
    }

    public void setWidget(Widget w){
    	if(fieldWidget != null){
    		widgetSpan.remove(fieldWidget);
    	}
    	fieldWidget = w;
    	//TODO Do a check here to change the type of label based on widget type eventually
    	if(w != null){
    		widgetSpan.add(w);
    		w.getElement().setAttribute("id", fieldHTMLId);
    	}
    }

    public Widget getFieldWidget(){
    	return fieldWidget;
    }

    public FlowPanel getFieldDetailsLayout(){
    	FlowPanel div = new FlowPanel();
		div.add(fieldTitle);
		div.add(instructions);
		div.addStyleName("ks-form-module-elements");
		return div;
    }

    public FlowPanel getFieldWidgetAreaLayout(){
    	FlowPanel div = new FlowPanel();
    	div.add(fieldWidget);
    	div.add(constraintText);
    	div.addStyleName("ks-form-module-elements");
    	return div;
    }

    public void setRequired(boolean isRequired){
    	required.setVisible(isRequired);
    }

    public void setInstructions(String text){
    	instructionText = text;
    	if(instructionText != null && !instructionText.trim().equals("")){
    		instructions.setHTML(text);
    		instructions.setVisible(true);
    	}
    }

    public void setConstraintText(String text){
    	if(text != null && !text.trim().equals("")){
    		constraintText.setHTML(text);
    		constraintText.setVisible(true);
    	}
    }

    public void setHelp(final String html){
    	if(html != null && !html.trim().equals("")){
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
			fieldTitle.addStyleName("invalid");
			if(parentPanel != null){
				parentPanel.addStyleName("error");
			}
			else if(parentElement != null){
				parentElement.setClassName("error");
			}

		}
		else{
			fieldTitle.removeStyleName("invalid");
			if(parentPanel != null){
				parentPanel.removeStyleName("error");
			}
			else if(parentElement != null){
				parentElement.setClassName("");
			}
		}

	}

	public ErrorLevel processValidationResult(ValidationResultInfo vr) {
		ErrorLevel status = ErrorLevel.OK;

		if(vr.getLevel() == ErrorLevel.ERROR){
			this.addValidationErrorMessage(vr.getMessage());

			if(status.getLevel() < ErrorLevel.ERROR.getLevel()){
				status = vr.getLevel();
			}
		}
		else if(vr.getLevel() == ErrorLevel.WARN){
			if(status.getLevel() < ErrorLevel.WARN.getLevel()){
				status = vr.getLevel();
			}
			//TODO does nothing on warn, warn is not currently used
		}
		else{
			//TODO does nothing on ok, ok is not currently used
		}
		return status;
	}

	public void addValidationErrorMessage(String text){
		if(validationPanel != null){
			KSLabel message;
			if(fieldName != null && !fieldName.trim().equals("")){
				message = new KSLabel(fieldName + " - " + text);
			}
			else{
				message = new KSLabel(text);
			}
			message.setStyleName("ks-form-validation-label");
			this.setErrorState(true);
			this.validationPanel.addMessage(message);
		}
	}

	public void clearValidationPanel(){
		this.setErrorState(false);
		if(validationPanel != null){
			this.validationPanel.clear();
		}
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

	public void setTitleDescLineHeight(LineNum margin) {
		layout.removeStyleName("ks-form-module-single-line-margin");
		switch(margin){
			case TRIPLE:
				if(firstLineExists() && secondLineExists()){
					layout.addStyleName("ks-form-module-single-line-margin");
				}
				else if((firstLineExists() || secondLineExists())){
					layout.addStyleName("ks-form-module-double-line-margin");
				}
				else{
					layout.addStyleName("ks-form-module-triple-line-margin");
				}

				break;
			case DOUBLE:
				if((firstLineExists() || secondLineExists())){
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

	private boolean firstLineExists(){
		boolean exists = false;
		if((fieldName != null && !fieldName.equals("")) || required.isVisible() || help.isVisible()){
			exists = true;
		}
		return exists;
	}

	private boolean secondLineExists(){
		boolean exists = false;
		if(instructions.isVisible()){
			exists = true;
		}
		return exists;
	}
}
