package org.kuali.student.core.ui.client.widgets;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class KSPopupPanel{
	protected PopupPanel popup = new PopupPanel();
	protected boolean isShowing = false;
	
	private VerticalPanel popupPanel = new VerticalPanel();
	private HorizontalPanel headerPanel = new HorizontalPanel();
	
	public KSPopupPanel(){
		
		popup.add(popupPanel);
		setupDefaultStyle();
	}
	
	public abstract void show();
	
	public void hide(){
		popup.hide();
		isShowing = false;
	}
	
	public void addHeader(String headerText){
		headerPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		headerPanel.add(new KSLabel(headerText));
		popupPanel.insert(headerPanel, 0);
	}
	
	public boolean removeHeader(){
		return popupPanel.remove(headerPanel);
	}
	
	public void add(Widget w){
		popupPanel.add(w);
	}
	
	private void setupDefaultStyle(){
		headerPanel.addStyleName(KSStyles.KS_POPUP_HEADER);
		popup.addStyleName(KSStyles.KS_POPUP);
	}
}
