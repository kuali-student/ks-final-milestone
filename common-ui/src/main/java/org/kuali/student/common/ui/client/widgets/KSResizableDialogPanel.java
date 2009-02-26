package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class KSResizableDialogPanel extends KSPopupPanel{
	 private final SimplePanel content = new SimplePanel();
	 
	 private final KSResizablePanel resizePanel = new KSResizablePanel();
	 
	 public KSResizableDialogPanel(){
			
	        resizePanel.add(content);
	        super.setWidget(resizePanel);

			setupDefaultStyle();
	}
	 
    public void setWidget(Widget w) {
        content.setWidget(w);
    }
	
	private void setupDefaultStyle(){
		
	}
}
