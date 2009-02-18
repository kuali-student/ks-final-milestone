package org.kuali.student.common.ui.client.widgets.progress;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class ModalPopupPanel {
	private PopupPanel popup = new PopupPanel();
	private PopupPanel glass = new PopupPanel();
	private boolean isShowing = false;
	
	public ModalPopupPanel(){
		glass.addStyleName("KS-Modal-Dialog-Glass");
		popup.addStyleName("KS-Blocking-Task-Window");
		
		popup.setModal(true);
		
		Window.addResizeHandler(new ResizeHandler(){

			public void onResize(ResizeEvent event) {
				if(isShowing){
					ModalPopupPanel.this.show();
				}
			}
		});
	}
	
	public ModalPopupPanel(Widget content){
		glass.addStyleName("KS-Modal-Dialog-Glass");
		popup.addStyleName("KS-Blocking-Task-Window");
		
		popup.setModal(true);
		
		Window.addResizeHandler(new ResizeHandler(){

			public void onResize(ResizeEvent event) {
				if(isShowing){
					ModalPopupPanel.this.show();
				}
			}
		});
		
		popup.add(content);
	}
	
	public void show(){
		glass.addStyleName("KS-Mouse-Wait");
		glass.show();
		popup.center();
		isShowing = true;
	}
	
	public void hide(){
		glass.removeStyleName("KS-Mouse-Wait");
		popup.hide();
		glass.hide();
		isShowing = false;
	}
	
	public void add(Widget w){
		popup.add(w);
	}
	
}
