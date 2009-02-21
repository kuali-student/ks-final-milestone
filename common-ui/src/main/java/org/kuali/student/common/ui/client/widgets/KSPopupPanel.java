package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class KSPopupPanel{
	private PopupPanel popup = new PopupPanel();
	private boolean isShowing = false;
	
	private VerticalPanel popupPanel = new VerticalPanel();
	private HorizontalPanel headerPanel = new HorizontalPanel();
	
	public KSPopupPanel(){
		
		popup.add(popupPanel);
		
		setupDefaultStyle();
	}
	
	public void show(){
		popup.show();
		isShowing = true;
	}
	
	public void center(){
		popup.center();
		isShowing = true;
	}
	
	public void hide(){
		popup.hide();
		isShowing = false;
	}
	
	public void addHeader(String headerText){
		headerPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		KSLabel kSLabel = GWT.create(KSLabel.class);
		kSLabel.init(headerText, false);
		headerPanel.add(kSLabel);
		popupPanel.insert(headerPanel, 0);
	}
	
	public boolean removeHeader(){
		return popupPanel.remove(headerPanel);
	}
	
	public void add(Widget w){
		popupPanel.add(w);
	}
	
	private void setupDefaultStyle(){
		popupPanel.setWidth("100%");
		headerPanel.addStyleName(KSStyles.KS_POPUP_HEADER);
		popup.addStyleName(KSStyles.KS_POPUP);	
	}
	
	public void addStyleName(String style){
		popup.addStyleName(style);
	}

	public void setModal(boolean modal) {
		popup.setModal(modal);
		
	}

	public boolean isShowing() {
		return isShowing;
	}
}
