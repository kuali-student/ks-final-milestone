package org.kuali.student.common.ui.client.widgets.field.layout.layouts;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;

import com.google.gwt.user.client.ui.Widget;

public class CollapsableLayout extends FieldLayout{

	private VerticalFieldLayout verticalLayout = new VerticalFieldLayout();
	
	@Override
	protected void addFieldToLayout(FieldElement field) {
		verticalLayout.addField(field);
		
	}

	@Override
	protected void addLayoutToLayout(FieldLayout layout) {
		verticalLayout.addLayoutToLayout(layout);
		
	}

	@Override
	protected void addWidgetToLayout(Widget widget) {
		verticalLayout.addWidgetToLayout(widget);
	}

	@Override
	protected void removeFieldLayoutComponentFromLayout(
			FieldLayoutComponent component) {
		verticalLayout.removeFieldLayoutComponentFromLayout(component);
		
	}

	@Override
	protected void removeWidgetFromLayout(Widget widget) {
		verticalLayout.removeWidgetFromLayout(widget);
	}

	@Override
	public void setLayoutTitle(SectionTitle layoutTitle) {
		//TODO
		
	}

}
