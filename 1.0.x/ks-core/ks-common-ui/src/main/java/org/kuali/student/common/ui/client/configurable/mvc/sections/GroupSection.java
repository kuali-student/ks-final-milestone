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

package org.kuali.student.common.ui.client.configurable.mvc.sections;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection.FieldInfo;
import org.kuali.student.common.ui.client.widgets.field.layout.FieldElement;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class GroupSection extends BaseSection{
	private FlowPanel layout = new FlowPanel();
	private FlowPanel section = new FlowPanel();
	private FlowPanel fieldsPanel = new FlowPanel();
	private ValidationMessagePanel validationPanel = new ValidationMessagePanel();
	private List<List<FieldDescriptor>> lines = new ArrayList<List<FieldDescriptor>>();
	private List<FieldDescriptor> currentLine = new ArrayList<FieldDescriptor>();
	private int lineCount = 0;

	public GroupSection(){
		lines.add(currentLine);
		layout.add(this.sectionTitle);
		this.instructions.setVisible(false);
		layout.add(this.instructions);
		layout.add(this.message);
		section.add(fieldsPanel);
		section.add(validationPanel);
		layout.add(section);
		
		this.initWidget(layout);
		sectionTitle.addStyleName("ks-heading-page-section");
		section.setStyleName("ks-form-module-group");
		section.addStyleName("clearfix");
		fieldsPanel.setStyleName("ks-form-module-fields");
		fieldsPanel.addStyleName("clearfix");
		
	}
	
	public GroupSection(SectionTitle title){
		lines.add(currentLine);
		this.sectionTitle = title;
		layout.add(this.sectionTitle);
		this.instructions.setVisible(false);
		layout.add(this.instructions);
		layout.add(this.message);
		section.add(fieldsPanel);
		section.add(validationPanel);
		layout.add(section);
		
		this.initWidget(layout);
		sectionTitle.addStyleName("ks-heading-page-section");
		section.setStyleName("ks-form-module-group");
		section.addStyleName("clearfix");
		fieldsPanel.setStyleName("ks-form-module-fields");
		fieldsPanel.addStyleName("clearfix");
	}
	
	public GroupSection(SectionTitle title, String instructions){
		lines.add(currentLine);
		this.sectionTitle = title;
		layout.add(this.sectionTitle);
		this.setInstructions(instructions);
		layout.add(this.instructions);
		layout.add(this.message);
		section.add(fieldsPanel);
		section.add(validationPanel);
		layout.add(section);
		this.initWidget(layout);
		
		sectionTitle.addStyleName("ks-heading-page-section");
		section.setStyleName("ks-form-module-group");
		section.addStyleName("clearfix");
		fieldsPanel.setStyleName("ks-form-module-fields");
		fieldsPanel.addStyleName("clearfix");
	}

	@Override
	protected void addFieldToLayout(FieldDescriptor fieldDescriptor) {
		currentLine.add(fieldDescriptor);
		FieldElement field;
		if(lineCount == 0){
			field = new FieldElement(fieldDescriptor, false);
		}
		else{
			field = new FieldElement(fieldDescriptor, true);
		}
        
        fieldsPanel.add(field);
        FieldInfo info = new FieldInfo(fieldDescriptor.getFieldLabel(), validationPanel, field, section);
        pathFieldInfoMap.put(fieldDescriptor.getFieldKey(), info);
	}
	
	public void nextLine(){
		fieldsPanel.add(new ClearBreak());
		currentLine = new ArrayList<FieldDescriptor>();
		lines.add(currentLine);
		lineCount++;
	}

	@Override
	protected void addSectionToLayout(BaseSection section) {
		//TODO do this or add it to fieldsPanel and have its child fields (and sections) point to this validation panel
		//BELOW DOES NOT WORK, unsure of cause
		//FIXME for now we dont allow sections inline with fields, the will always appear below group sections
		//section.setSectionValidationPanel(this.validationPanel);
		layout.add(section);
		
	}

	@Override
	protected void addWidgetToLayout(Widget w) {
		fieldsPanel.add(w);
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redraw() {
		fieldsPanel.clear();
		section.clear();
        layout.clear();
        section.add(fieldsPanel);
		section.add(validationPanel);
		layout.add(this.sectionTitle);
		layout.add(this.instructions);
		layout.add(this.message);
		layout.add(section);

        for(Section ns: sections){
            ns.redraw();
            this.addSectionToLayout((BaseSection) ns);
        }
        int curLine = 0;
        for(List<FieldDescriptor> line : lines){
        	
        	for(FieldDescriptor f: line){
        		FieldElement field;
        		if(curLine == 0){
        			field = new FieldElement(f, false);
        		}
        		else{
        			field = new FieldElement(f, true);
        		}
                fieldsPanel.add(field);
                FieldInfo info = new FieldInfo(f.getFieldLabel(), validationPanel, field, section);
                pathFieldInfoMap.put(f.getFieldKey(), info);
        	}
        	fieldsPanel.add(new ClearBreak());
        	curLine++;
        }
	}

	@Override
	protected void removeSectionFromLayout(BaseSection section) {
		layout.remove(section);
	}
	
}
