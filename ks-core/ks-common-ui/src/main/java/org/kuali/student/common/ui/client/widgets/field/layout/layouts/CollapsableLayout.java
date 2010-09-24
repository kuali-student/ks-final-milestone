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

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonLayout;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;
import org.kuali.student.common.ui.client.widgets.search.CollapsablePanel;

import com.google.gwt.user.client.ui.Widget;

public class CollapsableLayout extends FieldLayout{

	private VerticalFieldLayout verticalLayout = new VerticalFieldLayout();
	private CollapsablePanel panel;
	
	public CollapsableLayout(String title){
		panel = new CollapsablePanel(title, verticalLayout, false);
		this.add(panel);
	}
	
	public CollapsableLayout(String title, boolean isOpen){
		panel = new CollapsablePanel(title, verticalLayout, isOpen);
		this.add(panel);
	}
	
	@Override
	public void addFieldToLayout(FieldElement field) {
		verticalLayout.addField(field);
		
	}

	@Override
	public void addLayoutToLayout(FieldLayout layout) {
		verticalLayout.addLayoutToLayout(layout);
		
	}

	@Override
	public void addWidgetToLayout(Widget widget) {
		verticalLayout.addWidgetToLayout(widget);
	}

	@Override
	public void removeFieldLayoutComponentFromLayout(
			FieldLayoutComponent component) {
		verticalLayout.removeFieldLayoutComponentFromLayout(component);
		
	}

	@Override
	public void removeWidgetFromLayout(Widget widget) {
		verticalLayout.removeWidgetFromLayout(widget);
	}

	@Override
	public void setLayoutTitle(SectionTitle layoutTitle) {
		verticalLayout.setLayoutTitle(layoutTitle);
		
	}

	@Override
	public void addButtonLayoutToLayout(ButtonLayout buttonLayout) {
		verticalLayout.addButtonLayout(buttonLayout);
		
	}

}
