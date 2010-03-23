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
		layoutTitle.addStyleName("ks-form-bordered-header-title");
		header.setHeaderTitle(layoutTitle);
		
	}
}
