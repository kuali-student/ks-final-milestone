/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Garey
 *
 */
public class LabelWidget extends Composite {

	HorizontalPanel	root	= new HorizontalPanel();
	Label	lbl	= null;
	Widget	w	= null;
	
	/**
	 * 
	 */
	public LabelWidget(String lblStr, Widget w) {
		this.lbl = new Label(lblStr);
		this.w = w;
		
		root.add(this.lbl);
		root.add(this.w);
		
		initWidget(root);
		
	}

	public Label getLabel() {
		return lbl;
	}

	public void setLabel(Label lbl) {
		this.lbl = lbl;
	}

	public void setLabelStyleName(String styleName) {
		this.lbl.addStyleName(styleName);
	}

	public Widget getWidget() {
		return w;
	}

	public void setWidget(Widget w) {
		this.w = w;
	}
	
	public void setWidgetStyleName(String styleName) {
		this.w.addStyleName(styleName);
	}
	
}
