package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSDialogPanel extends KSPopupPanel{
	
    private final SimplePanel content = new SimplePanel();	
    private final VerticalPanel dialogContainer = new VerticalPanel();
	private final HorizontalPanel headerPanel = new HorizontalPanel();
	
	public KSDialogPanel(){
		
        dialogContainer.add(content);
        super.setWidget(dialogContainer);

		setupDefaultStyle();
	}
	
	
	public void addHeader(String headerText){
		headerPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		KSLabel kSLabel = new KSLabel(headerText, false);
		headerPanel.add(kSLabel);
		dialogContainer.insert(headerPanel, 0);
	}
	
	public boolean removeHeader(){
		return dialogContainer.remove(headerPanel);
	}
	
    public void setWidget(Widget w) {
        content.setWidget(w);
    }
	
	private void setupDefaultStyle(){
		headerPanel.addStyleName(KSStyles.KS_POPUP_HEADER);
	}
}
