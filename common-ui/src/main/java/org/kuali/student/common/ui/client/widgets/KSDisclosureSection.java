package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public abstract class KSDisclosureSection extends Composite{
	
	public abstract void init(String headerText, Widget headerWidget, boolean isOpen); 

}
