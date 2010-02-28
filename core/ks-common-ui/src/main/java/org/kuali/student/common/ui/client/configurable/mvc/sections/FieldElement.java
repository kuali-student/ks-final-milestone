package org.kuali.student.common.ui.client.configurable.mvc.sections;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.widgets.KSTitleDescPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class FieldElement extends Composite{

	private KSTitleDescPanel titlePanel = new KSTitleDescPanel();
	private FlowPanel layout = new FlowPanel();

    public FieldElement(String title, Widget widget) {
        titlePanel.setTitleText(title);
        titlePanel.getDescWidget().addStyleName("ks-form-module-elements-instruction");
        layout.add(titlePanel);
        layout.add(widget);
        initWidget(layout);
        layout.addStyleName("ks-form-module-elements");
    }

	public FieldElement(FieldDescriptor fieldDescriptor, boolean topMargin){
		if(topMargin){
			if(fieldDescriptor.getFieldLabel() != null){
				this.titlePanel.setTitleText(fieldDescriptor.getFieldLabel());
				this.titlePanel.getTitleWidget().addStyleName("ks-form-module-single-line-margin");
			}
			else{
				fieldDescriptor.getFieldWidget().addStyleName("ks-form-module-double-line-margin");
			}

		}
		else{
			if(fieldDescriptor.getFieldLabel() != null){
				this.titlePanel.setTitleText(fieldDescriptor.getFieldLabel());
				this.titlePanel.getTitleWidget().addStyleName("ks-form-module-no-line-margin");
			}
			else{
				fieldDescriptor.getFieldWidget().addStyleName("ks-form-module-single-line-margin");
			}

		}

		titlePanel.getDescWidget().addStyleName("ks-form-module-elements-instruction");
		layout.add(titlePanel);
		layout.add(fieldDescriptor.getFieldWidget());
		this.initWidget(layout);
		layout.addStyleName("ks-form-module-elements");
	}

	public FieldElement(FieldDescriptor fieldDescriptor){
		this(fieldDescriptor, false);
	}

	public void setValidationError(boolean error){
		if(error){
			titlePanel.addStyleName("invalid");
		}
		else{
			titlePanel.removeStyleName("invalid");
		}

	}
}
