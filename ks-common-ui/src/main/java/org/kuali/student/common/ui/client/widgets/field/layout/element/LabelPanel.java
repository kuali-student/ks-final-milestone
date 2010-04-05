package org.kuali.student.common.ui.client.widgets.field.layout.element;


import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class LabelPanel extends FieldTitle{
	public LabelPanel(String text, String forID){
		Element label = DOM.createLabel();
		label.setInnerText(text);
		label.setAttribute("for", forID);
		this.setElement(label);
	}
	
	public LabelPanel(String text){
		Element label = DOM.createLabel();
		label.setInnerText(text);
		this.setElement(label);
	}
	
}
