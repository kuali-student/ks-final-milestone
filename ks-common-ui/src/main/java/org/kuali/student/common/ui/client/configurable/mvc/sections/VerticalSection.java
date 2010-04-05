package org.kuali.student.common.ui.client.configurable.mvc.sections;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.field.layout.FieldElement;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class VerticalSection extends BaseSection{
	
	private FlowPanel layout = new FlowPanel();
	
	public VerticalSection(){
		this.initWidget(layout);
		layout.setStyleName("ks-form-module");
		sectionTitle.addStyleName("ks-heading-page-section");
	}
	
	public VerticalSection(SectionTitle title){
		this.sectionTitle = title;
		layout.add(this.sectionTitle);
		this.instructions.setVisible(false);
		layout.add(this.instructions);
		layout.add(this.message);
		this.initWidget(layout);
		layout.setStyleName("ks-form-module");
		sectionTitle.addStyleName("ks-heading-page-section");
	}
	
	public VerticalSection(SectionTitle title, String instructions){
		this.sectionTitle = title;
		layout.add(this.sectionTitle);
		this.setInstructions(instructions);
		layout.add(this.instructions);
		layout.add(this.message);
		this.initWidget(layout);
		layout.setStyleName("ks-form-module");
		sectionTitle.addStyleName("ks-heading-page-section");
	}
	
	
	@Override
	protected void addFieldToLayout(FieldDescriptor fieldDescriptor) {
        FieldElement field = new FieldElement(fieldDescriptor);
        FlowPanel fieldContainer = new FlowPanel();
        FlowPanel fieldLayout = new FlowPanel();
        fieldContainer.add(field);
        ValidationMessagePanel validationPanel = new ValidationMessagePanel();
        fieldLayout.add(fieldContainer);
        fieldLayout.add(validationPanel);
        FieldInfo info = new FieldInfo(fieldDescriptor.getFieldLabel(), validationPanel, field, fieldLayout);
        pathFieldInfoMap.put(fieldDescriptor.getFieldKey(), info);
        layout.add(fieldLayout);
        fieldLayout.setStyleName("ks-form-module-group");
        fieldLayout.addStyleName("clearfix");
        fieldContainer.setStyleName("ks-form-module-fields");
	}

	@Override
	protected void addSectionToLayout(BaseSection section) {
		layout.add(section);
	}
	
    @Override
    public void redraw() {
        layout.clear();
		layout.add(this.sectionTitle);
		layout.add(this.instructions);
		layout.add(this.message);
        for(Section ns: sections){
            ns.redraw();
            this.addSectionToLayout((BaseSection)ns);
        }
        for(FieldDescriptor f: fields){
            this.addFieldToLayout(f);
        }
        //Fire validation request here?
    }

	@Override
	public void clear() {
		
	}

	@Override
	protected void addWidgetToLayout(Widget w) {
		layout.add(w);
	}
	
	@Override
	protected void removeSectionFromLayout(BaseSection section) {
		layout.remove(section);
	}
}
