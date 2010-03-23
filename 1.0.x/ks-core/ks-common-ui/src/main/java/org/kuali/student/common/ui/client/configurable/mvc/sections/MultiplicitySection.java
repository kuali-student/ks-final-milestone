package org.kuali.student.common.ui.client.configurable.mvc.sections;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection.FieldInfo;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class MultiplicitySection extends VerticalSection{
	private FlowPanel layout = new FlowPanel();
	private FlowPanel section = new FlowPanel();
	private FlowPanel fieldsPanel = new FlowPanel();
	private ValidationMessagePanel validationPanel = new ValidationMessagePanel();
	private List<List<FieldDescriptor>> lines = new ArrayList<List<FieldDescriptor>>();
	private List<FieldDescriptor> currentLine = new ArrayList<FieldDescriptor>();

	public MultiplicitySection(int level){
		lines.add(currentLine);
		layout.add(this.sectionTitle);
		this.instructions.setVisible(false);
		layout.add(this.instructions);
		layout.add(this.message);
		section.add(fieldsPanel);
		section.add(validationPanel);
		layout.add(section);
		
		this.initWidget(layout);
		section.setStyleName("ks-form-module-group");
		section.addStyleName("clearfix");
		fieldsPanel.setStyleName("ks-form-module-fields");
		fieldsPanel.addStyleName("clearfix");
		
	}
	
	public MultiplicitySection(SectionTitle title){
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
		section.setStyleName("ks-form-module-group");
		section.addStyleName("clearfix");
		fieldsPanel.setStyleName("ks-form-module-fields");
		fieldsPanel.addStyleName("clearfix");
	}
	
	public MultiplicitySection(SectionTitle title, String instructions){
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
		section.setStyleName("ks-form-module-group");
		section.addStyleName("clearfix");
		fieldsPanel.setStyleName("ks-form-module-fields");
		fieldsPanel.addStyleName("clearfix");
	}

	@Override
	protected void addFieldToLayout(FieldDescriptor fieldDescriptor) {
		currentLine.add(fieldDescriptor);
        FieldElement field = new FieldElement(fieldDescriptor);
        fieldsPanel.add(field);
        FieldInfo info = new FieldInfo(fieldDescriptor.getFieldLabel(), validationPanel, field, section);
        pathFieldInfoMap.put(fieldDescriptor.getFieldKey(), info);
	}
	
	public void nextLine(){
		fieldsPanel.add(new ClearBreak());
		currentLine = new ArrayList<FieldDescriptor>();
		lines.add(currentLine);
	}

	@Override
	protected void addSectionToLayout(BaseSection section) {
		//TODO do this or add it to fieldsPanel and have its child fields (and sections) point to this validation panel
		//BELOW DOES NOT WORK, unsure of cause
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
        for(List<FieldDescriptor> line : lines){
        	for(FieldDescriptor f: line){
                FieldElement field = new FieldElement(f);
                fieldsPanel.add(field);
        	}
        }
	}
}
