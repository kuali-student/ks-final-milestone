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

package org.kuali.student.common.ui.client.widgets.field.layout.layouts;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.ClearBreak;
import org.kuali.student.common.ui.client.configurable.mvc.sections.ValidationMessagePanel;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonLayout;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement.LineNum;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class GroupFieldLayout extends FieldLayout{
	private SpanPanel top = new SpanPanel();
	private FlowPanel layout = new FlowPanel();
	private FlowPanel fieldContainer = new FlowPanel();
	private FlowPanel fieldsPanel = new FlowPanel();
	private SpanPanel buttonArea = new SpanPanel();
	private ValidationMessagePanel validationPanel = new ValidationMessagePanel();
	private List<FieldElement> currentLine = new ArrayList<FieldElement>();
	private int lineCount = 0;
	public static enum ValidationLocation{NONE, TOP, RIGHT};
	private ValidationLocation loc;
	private boolean lineHasTitles = false;
	private boolean lineHasDescriptions = false;
	
	public GroupFieldLayout(){
		super();
		this.loc = ValidationLocation.RIGHT;
		init();
	}
	
	public GroupFieldLayout(ValidationLocation loc){
		super();
		this.loc = loc;
		init();
	}
	
	public GroupFieldLayout(SectionTitle title){
		super();
		this.loc = ValidationLocation.RIGHT;
		this.setLayoutTitle(title);
		init();
	}
	
	public GroupFieldLayout(SectionTitle title, ValidationLocation loc){
		super();
		this.loc = loc;
		this.setLayoutTitle(title);
		init();
	}
	
	private void init(){
		//lines.add(currentLine);
		this.instructions.setVisible(false);
		layout.add(this.instructions);
		layout.add(this.message);
		if(loc == ValidationLocation.RIGHT){
			this.hasValidation = true;
			fieldContainer.add(fieldsPanel);
			fieldContainer.add(validationPanel);
			layout.add(fieldContainer);
			fieldContainer.setStyleName("ks-form-module-group");
			fieldContainer.addStyleName("clearfix");
			fieldsPanel.setStyleName("ks-form-module-fields");
			fieldsPanel.addStyleName("clearfix");
			validationPanel.setStyleName("ks-form-module-validation");
		}
		else if(loc == ValidationLocation.TOP){
			this.hasValidation = true;
			fieldContainer.add(validationPanel);
			fieldContainer.add(fieldsPanel);
			layout.add(fieldContainer);
			fieldContainer.setStyleName("ks-form-module-group");
			fieldContainer.addStyleName("clearfix");
			fieldsPanel.setStyleName("ks-form-module-fields");
			fieldsPanel.addStyleName("clearfix");
			validationPanel.setStyleName("ks-form-module-validation");
		}
		else{
			this.hasValidation = false;
			fieldContainer.add(fieldsPanel);
			layout.add(fieldContainer);
			fieldContainer.setStyleName("ks-form-module-group");
			fieldContainer.addStyleName("clearfix");
			fieldsPanel.setStyleName("ks-form-module-fields");
			fieldsPanel.addStyleName("clearfix");
		}
		
		top.add(layout);
		top.add(buttonArea);
		this.add(top);
		
	}
	
	public void nextLine(){
		fieldsPanel.add(new ClearBreak());
		currentLine = new ArrayList<FieldElement>();
		//lines.add(currentLine);
		lineCount++;
		lineHasTitles = false;
		lineHasDescriptions = false;
	}
	
	@Override
	public void addFieldToLayout(FieldElement field) {
		currentLine.add(field);
		
		if(field.getFieldName() != null && !field.getFieldName().equals("")){
			lineHasTitles = true;
		}
		
		if(field.getInstructionText() != null && !field.getInstructionText().equals("")){
			lineHasDescriptions = true;
		}
		
		for(FieldElement f: currentLine){
			if(lineHasTitles && lineHasDescriptions){
				f.setTitleDescLineHeight(LineNum.TRIPLE);
			}
			else if(lineHasTitles || lineHasDescriptions){
				f.setTitleDescLineHeight(LineNum.DOUBLE);
			}
			else{
				f.setTitleDescLineHeight(LineNum.SINGLE);
			}
		}
        
        fieldsPanel.add(field);
        field.setValidationPanel(validationPanel);
        field.setParentPanel(fieldContainer);
		
	}

	@Override
	public void addLayoutToLayout(FieldLayout fieldLayout) {
		layout.add(fieldLayout);
		fieldLayout.setParentLayout(this);
	}

	@Override
	public void addWidgetToLayout(Widget widget) {
		layout.add(widget);
		
	}

	@Override
	public void removeFieldLayoutComponentFromLayout(
			FieldLayoutComponent component) {
		if(component instanceof FieldElement){
			fieldsPanel.remove((FieldElement)component);
		}
		else if(component instanceof FieldLayout){
			layout.remove((FieldLayout)component);
		}
		
	}

	@Override
	public void removeWidgetFromLayout(Widget widget) {
		layout.remove(widget);
		
	}

	@Override
	public void setLayoutTitle(SectionTitle layoutTitle) {
		if(this.layoutTitle != null){
			layout.remove(this.layoutTitle);
		}
		this.layoutTitle = layoutTitle;
		layout.insert(layoutTitle, 0);
		layoutTitle.addStyleName("ks-layout-header");
	}

	@Override
	public void addButtonLayoutToLayout(ButtonLayout buttonLayout) {
		buttonArea.add(buttonLayout);
	}

}
