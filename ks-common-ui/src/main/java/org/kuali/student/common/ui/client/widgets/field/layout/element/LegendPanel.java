package org.kuali.student.common.ui.client.widgets.field.layout.element;


import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class LegendPanel extends FieldTitle{
	
	public LegendPanel(String text, String forID){
		Element legend = DOM.createLegend();
		legend.setInnerText(text);
		legend.setAttribute("for", forID);
		this.setElement(legend);
	}

}
