package org.kuali.student.ui.personidentity.client.view;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Garey
 *
 * This class contains a search result widget.  This will most
 * likely be added to a list.
 * 
 * the general outline is to have a check box and a widget
 */
public class SearchResultElementWidget extends Composite {

	final HorizontalPanel	basePanel	= new HorizontalPanel();	
	final CheckBox			cBox	= new CheckBox();
	Widget					w		= null;
	
	public SearchResultElementWidget(final Widget w, boolean displayCheckBox) {
		super();
				
		setup(w,displayCheckBox);
	}
	
	public SearchResultElementWidget(final Widget w) {
		super();
				
		setup(w, true);
	}
	
	protected void setup(final Widget w, boolean displayCheckBox){
		this.w = w;		
		
		if(!displayCheckBox){
			cBox.setVisible(false);
		}
		
		basePanel.add(cBox);
		basePanel.add(w);		
		initWidget(basePanel);
	}

	public Widget getW() {
		return w;
	}

	public void setW(Widget w) {
		this.w = w;
	}

	public HorizontalPanel getBasePanel() {
		return basePanel;
	}

	public CheckBox getCBox() {
		return cBox;
	}
	
	
	
	
}
