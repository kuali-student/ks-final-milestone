package org.kuali.student.commons.ui.mvc.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Widget;

public class MVC implements EntryPoint {

	public void onModuleLoad() {
		// TODO Auto-generated method stub 
	}
	
	public static ControllerComposite findParentController(Widget widget) {
		ControllerComposite result = null;
		while (result == null && widget.getParent() != null) {
			widget = widget.getParent();
			if (widget instanceof ControllerComposite) {
				result = (ControllerComposite) widget;
			}
		}
		return result;
	}
	
}
