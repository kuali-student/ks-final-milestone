package org.kuali.student.common.ui.client.widgets.field.layout.layouts;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;
import org.kuali.student.common.ui.client.widgets.search.CollapsablePanel;

import com.google.gwt.user.client.ui.Widget;

public class CollapsableLayout extends FieldLayout{

	private VerticalFieldLayout verticalLayout = new VerticalFieldLayout();
	private CollapsablePanel panel;
	
	public CollapsableLayout(String title, boolean isOpen){
		panel = new CollapsablePanel(title, verticalLayout, isOpen);
		this.initWidget(panel);
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

}
