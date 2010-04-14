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

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.field.layout.FieldElement;
import org.kuali.student.common.ui.client.widgets.search.CollapsablePanel;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class CollapsableSection extends BaseSection{
	
	private FlowPanel layout = new FlowPanel();
	private CollapsablePanel collapsablePanel;
	private String showLabel = "Details";

	public CollapsableSection(){
		initialize(null, showLabel, false);		
	}

	public CollapsableSection(String showLabel){
		initialize(null, showLabel, false);		
	}

	public CollapsableSection(SectionTitle title){
		initialize(title, showLabel, false);	
	}

	public CollapsableSection(SectionTitle title, String showLabel, boolean isOpen){
		initialize(title, showLabel, isOpen);
	}

	private void initialize(SectionTitle title, String showLabel, boolean isOpen) {
		if (title != null) {
  		    this.sectionTitle = title;
	    	layout.add(this.sectionTitle);			
		}
		this.showLabel = showLabel;
		layout.add(this.message);
		collapsablePanel = new CollapsablePanel(showLabel, layout, isOpen);
		this.initWidget(collapsablePanel);
		layout.setStyleName("ks-form-module");
		sectionTitle.addStyleName("ks-heading-page-section");		
	}
	
	@Override
	protected void addFieldToLayout(FieldDescriptor f) {
        FieldElement field = new FieldElement(f);
        FlowPanel fieldContainer = new FlowPanel();
        FlowPanel fieldLayout = new FlowPanel();
        fieldContainer.add(field);
        ValidationMessagePanel validationPanel = new ValidationMessagePanel();
        fieldLayout.add(fieldContainer);
        fieldLayout.add(validationPanel);
        FieldInfo info = new FieldInfo(f.getFieldLabel(), validationPanel, field, fieldLayout);
        pathFieldInfoMap.put(f.getFieldKey(), info);
        layout.add(fieldLayout);
        fieldLayout.setStyleName("ks-form-module-group");
        fieldLayout.addStyleName("clearfix");
        fieldContainer.setStyleName("ks-form-module-fields");
	}

	@Override
	protected void addSectionToLayout(BaseSection s) {
		layout.add(s);
	}

	@Override
	protected void addWidgetToLayout(Widget w) {
		layout.add(w);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
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
	protected void removeSectionFromLayout(BaseSection section) {
		layout.remove(section);
		
	}
}
