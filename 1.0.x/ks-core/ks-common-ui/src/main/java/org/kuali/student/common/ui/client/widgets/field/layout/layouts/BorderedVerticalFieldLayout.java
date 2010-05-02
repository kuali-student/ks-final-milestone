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
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class BorderedVerticalFieldLayout extends FieldLayout{
	private FlowPanel body = new FlowPanel();
	private VerticalFieldLayout verticalLayout = new VerticalFieldLayout();
	private Header header;
	public BorderedVerticalFieldLayout(){
		super();
		this.hasValidation = true;
		verticalLayout.addStyleName("ks-form-bordered-body");
		body.setStyleName("ks-form-bordered");
		body.add(verticalLayout);
		this.initWidget(body);
	}
	
	public BorderedVerticalFieldLayout(SectionTitle title){
		super();
		this.hasValidation = true;
		verticalLayout.addStyleName("ks-form-bordered-body");
		body.setStyleName("ks-form-bordered");
		header = new Header(title);
		header.setStyleName("ks-form-bordered-header");
		title.addStyleName("ks-form-bordered-header-title");
		body.add(header);
		body.add(verticalLayout);
		this.initWidget(body);
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
		layoutTitle.addStyleName("ks-form-bordered-header-title");
		header.setHeaderTitle(layoutTitle);
		
	}
}
