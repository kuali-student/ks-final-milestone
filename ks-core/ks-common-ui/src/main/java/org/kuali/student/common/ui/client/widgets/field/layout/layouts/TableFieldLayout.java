package org.kuali.student.common.ui.client.widgets.field.layout.layouts;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.ValidationMessagePanel;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonLayout;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class TableFieldLayout extends FieldLayout{
	
	private SpanPanel top = new SpanPanel();
	private SpanPanel buttonPanel = new SpanPanel();
	protected FlowPanel verticalLayout = new FlowPanel();
	private FlexTable table = new FlexTable();
	private int row = 0;
	
	public List<Widget> tableIndex = new ArrayList<Widget>();
	
	public TableFieldLayout(){
		this.hasValidation = false;
		table.setWidth("100%");
		table.setStyleName("ks-table-plain");
		verticalLayout.add(table);
		this.add(verticalLayout);
	}
	
	public TableFieldLayout(boolean hasValidation){
		this.hasValidation = hasValidation;
		table.setWidth("100%");
		table.setStyleName("ks-table-plain");
		verticalLayout.add(table);
		this.add(verticalLayout);
	}
	
	public TableFieldLayout(SectionTitle title, boolean hasValidation){
		this.hasValidation = hasValidation;
		table.setWidth("100%");
		table.setStyleName("ks-table-plain");
		this.setLayoutTitle(title);
		verticalLayout.add(table);
		this.add(verticalLayout);
	}
	
	@Override
	public void addFieldToLayout(FieldElement field) {
		FlowPanel fieldPanel = field.getFieldWidgetAreaLayout();
		table.setWidget(row, 0, field.getFieldDetailsLayout());
		table.setWidget(row, 1, fieldPanel);
		if(this.hasValidation){
			ValidationMessagePanel validationPanel = new ValidationMessagePanel(false);
			table.setWidget(row, 2, validationPanel);
			field.setValidationPanel(validationPanel);
			field.setParentElement(table.getRowFormatter().getElement(row));
			table.getColumnFormatter().setStyleName(0, "ks-table-title-column-width");
			table.getColumnFormatter().setStyleName(1, "ks-table-field-column-width");
			validationPanel.setStyleName("ks-form-module-validation-inline");
		}
		else{
			table.getColumnFormatter().setStyleName(0, "ks-table-title-column-width");
		}
		row++;
	}

	@Override
	public void addLayoutToLayout(FieldLayout layout) {
		verticalLayout.add(layout);
	}

	@Override
	public void addWidgetToLayout(Widget widget) {
		verticalLayout.add(widget);
	}

	@Override
	public void removeFieldLayoutComponentFromLayout(
			FieldLayoutComponent component) {
		if(component instanceof FieldElement){
			int index = tableIndex.indexOf(component);
			table.removeRow(index);
		}
		else if(component instanceof FieldLayout){
			verticalLayout.remove((FieldLayout)component);
		}
		
	}

	@Override
	public void removeWidgetFromLayout(Widget widget) {
		verticalLayout.remove(widget);
	}

	@Override
	public void setLayoutTitle(SectionTitle layoutTitle) {
		if(this.layoutTitle != null){
			verticalLayout.remove(this.layoutTitle);
		}
		this.layoutTitle = layoutTitle;
		verticalLayout.insert(layoutTitle, 0);
		layoutTitle.addStyleName("ks-layout-header");
		
	}

	@Override
	public void addButtonLayoutToLayout(ButtonLayout buttonLayout) {
		// TODO Auto-generated method stub
		
	}

}
