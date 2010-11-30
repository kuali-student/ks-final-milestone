package org.kuali.student.common.ui.client.widgets.field.layout.layouts;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.ValidationMessagePanel;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonLayout;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Widget;

public class HorizontalLayout extends FieldLayout{
	private FlexTable horizontalArea = new FlexTable();
	private ValidationMessagePanel validationPanel = new ValidationMessagePanel();
	private SpanPanel fieldArea = new SpanPanel();
	private SpanPanel buttonArea;
	private int currentRow = 0;
	private int currentColumn = 0;
	
	public HorizontalLayout(){
		super();
		hasValidation = true;
		init();
	}
	
	public HorizontalLayout(boolean hasValidation){
		super();
		this.hasValidation = hasValidation;
		init();
	}
	
	public HorizontalLayout(SectionTitle title){
		super();
		this.setLayoutTitle(title);
		hasValidation = true;
		init();
	}
	
	public HorizontalLayout(SectionTitle title, boolean hasValidation){
		super();
		this.setLayoutTitle(title);
		this.hasValidation = hasValidation;
		init();

	}
	
	private void init(){
		instructions.setVisible(false);
		this.add(instructions);
		this.add(message);
		if(hasValidation){
			this.add(validationPanel);
		}
		this.add(horizontalArea);
	}

	@Override
	public void addButtonLayoutToLayout(ButtonLayout buttonLayout) {
		if(buttonArea == null){
			buttonArea = new SpanPanel();
			this.add(buttonArea);
		}
		buttonArea.add(buttonLayout);
	}

	@Override
	public void addFieldToLayout(FieldElement field) {
		horizontalArea.setWidget(currentRow, currentColumn, field);
		horizontalArea.getFlexCellFormatter().setVerticalAlignment(currentRow, currentColumn, HasVerticalAlignment.ALIGN_TOP);

		if(hasValidation){
			field.setValidationPanel(validationPanel);
		}
		currentColumn++;
	}

	@Override
	public void addLayoutToLayout(FieldLayout layout) {
		horizontalArea.setWidget(currentRow, currentColumn, layout);
		horizontalArea.getFlexCellFormatter().setVerticalAlignment(currentRow, currentColumn, HasVerticalAlignment.ALIGN_TOP);
		currentColumn++;
	}

	@Override
	public void addWidgetToLayout(Widget widget) {
		horizontalArea.setWidget(currentRow, currentColumn, widget);
		horizontalArea.getFlexCellFormatter().setVerticalAlignment(currentRow, currentColumn, HasVerticalAlignment.ALIGN_TOP);
		currentColumn++;
	}
	
	public void nextRow(){
		currentRow++;
		currentColumn = 0;
	}

	@Override
	public void removeFieldLayoutComponentFromLayout(
			FieldLayoutComponent component) {
		if(component instanceof FieldElement){
			horizontalArea.remove((FieldElement)component);
		}
		else if(component instanceof FieldLayout){
			horizontalArea.remove((FieldLayout)component);
		}
		
	}

	@Override
	public void removeWidgetFromLayout(Widget widget) {
		horizontalArea.remove(widget);	
	}

	@Override
	public void setLayoutTitle(SectionTitle layoutTitle) {
		if(this.layoutTitle != null){
			this.remove(this.layoutTitle);
		}
		if(layoutTitle != null){
			this.layoutTitle = layoutTitle;
			this.insert(layoutTitle, 0);
			layoutTitle.addStyleName("ks-layout-header");
		}
		
	}

}
