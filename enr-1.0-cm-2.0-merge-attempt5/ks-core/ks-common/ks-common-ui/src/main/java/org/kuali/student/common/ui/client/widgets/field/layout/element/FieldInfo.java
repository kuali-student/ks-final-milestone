package org.kuali.student.common.ui.client.widgets.field.layout.element;

import com.google.gwt.user.client.ui.Widget;

@Deprecated
public class FieldInfo {
	public String fieldTitle;
	public boolean required = false;
	public String helpText;
	public String instructionText;
	public String constraintText;
	public String watermarkText;
	public String key;
	public Widget widget;
	
	public FieldInfo(String fieldTitle, Widget widget){
		
	}
	
	public FieldInfo(String key, String fieldTitle, Widget widget){
		
	}

	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public String getInstructionText() {
		return instructionText;
	}
	public void setInstructionText(String instructionText) {
		this.instructionText = instructionText;
	}
	public String getConstraintText() {
		return constraintText;
	}
	public void setConstraintText(String constraintText) {
		this.constraintText = constraintText;
	}
	public String getWatermarkText() {
		return watermarkText;
	}
	public void setWatermarkText(String watermarkText) {
		this.watermarkText = watermarkText;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Widget getWidget() {
		return widget;
	}
	public void setWidget(Widget widget) {
		this.widget = widget;
	}
	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}
	public String getHelpText() {
		return helpText;
	}
	public void setFieldTitle(String fieldTitle) {
		this.fieldTitle = fieldTitle;
	}
	public String getFieldTitle() {
		return fieldTitle;
	}
}
