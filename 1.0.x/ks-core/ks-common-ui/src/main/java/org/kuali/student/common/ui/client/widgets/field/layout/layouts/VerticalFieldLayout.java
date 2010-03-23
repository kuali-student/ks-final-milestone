package org.kuali.student.common.ui.client.widgets.field.layout.layouts;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.ValidationMessagePanel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class VerticalFieldLayout extends FieldLayout{
	private Map<String, FlowPanel> fieldContainers = new HashMap<String, FlowPanel>();
	
	protected FlowPanel verticalLayout = new FlowPanel();
	
	public VerticalFieldLayout(){
		super();
		this.initWidget(verticalLayout);
		hasValidation = true;
		verticalLayout.setStyleName("ks-form-module");
	}
	
	public VerticalFieldLayout(boolean hasValidation){
		super();
		this.hasValidation = hasValidation;
		this.initWidget(verticalLayout);
		verticalLayout.setStyleName("ks-form-module");
	}
	
	public VerticalFieldLayout(SectionTitle title){
		super();
		this.setLayoutTitle(title);
		this.initWidget(verticalLayout);
		verticalLayout.setStyleName("ks-form-module");
	}
	
	public VerticalFieldLayout(SectionTitle title, boolean hasValidation){
		super();
		this.setLayoutTitle(title);
		this.initWidget(verticalLayout);
		this.hasValidation = hasValidation;
		verticalLayout.setStyleName("ks-form-module");
	}
	
	
	
	@Override
	protected void addFieldToLayout(FieldElement field) {
        FlowPanel fieldContainer = new FlowPanel();
        FlowPanel fieldLayout = new FlowPanel();
        fieldContainer.add(field);
        fieldLayout.add(fieldContainer);
        if(hasValidation){
	        ValidationMessagePanel validationPanel = new ValidationMessagePanel();
	        fieldLayout.add(validationPanel);
	        field.setValidationPanel(validationPanel);
	        validationPanel.setStyleName("ks-form-module-validation-inline");
        }
        field.setParentPanel(fieldLayout);
        verticalLayout.add(fieldLayout);
        fieldContainers.put(field.getKey(), fieldLayout);
        fieldLayout.setStyleName("ks-form-module-group");
        fieldLayout.addStyleName("clearfix");
        //field.addStyleName("ks-form-module-single-line-margin");
        fieldContainer.setStyleName("ks-form-module-fields");
	}

	@Override
	protected void addLayoutToLayout(FieldLayout layout) {
		verticalLayout.add(layout);
		layout.setParentLayout(this);
	}

	@Override
	protected void addWidgetToLayout(Widget widget) {
		widget.addStyleName("ks-section-widget");
		verticalLayout.add(widget);
	}

	@Override
	protected void removeWidgetFromLayout(Widget widget) {
		verticalLayout.remove(widget);
	}
	
	@Override
	protected void removeFieldLayoutComponentFromLayout(FieldLayoutComponent component){
		if(component instanceof FieldElement){
			FlowPanel panel = fieldContainers.get(component.getKey());
			verticalLayout.remove(panel);
			fieldContainers.remove(component.getKey());
		}
		else if(component instanceof FieldLayout){
			verticalLayout.remove((FieldLayout)component);
		}
	}

	@Override
	public void setLayoutTitle(SectionTitle layoutTitle) {
		if(this.layoutTitle != null){
			verticalLayout.remove(this.layoutTitle);
		}
		this.layoutTitle = layoutTitle;
		verticalLayout.insert(layoutTitle, 0);
		layoutTitle.addStyleName("ks-heading-page-section");
	}

}
