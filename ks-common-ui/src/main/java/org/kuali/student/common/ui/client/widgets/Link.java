package org.kuali.student.common.ui.client.widgets;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.event.ActionEvent;
import org.kuali.student.common.ui.client.event.NavigationActionEvent;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class Link extends Composite{
	private boolean fireNavEvents = true;
	private List<Callback<String>> callbacks = new ArrayList<Callback<String>>();
	private String url;
	private String text;
	private String navKey;
	private Controller controller;
	
	private ClickHandler handler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			if(fireNavEvents && controller != null){
				NavigationActionEvent navEvent = new NavigationActionEvent(navKey);
				navEvent.setActionCompleteCallback(new ActionCompleteCallback(){
	
					@Override
					public void onActionComplete(ActionEvent action) {
						doLinkActions();
					}
				});
				
				controller.fireApplicationEvent(navEvent);
			}
			else{
				doLinkActions();
			}
		}
	};
	
	public Link(String text, String url){
		this.navKey = text;
		KSLabel label = new KSLabel();
		label.setText(text);
		label.addClickHandler(handler);
	}
	
	public Link(String text, Callback<String> callback){
		//HTMLPanel.c
		this.navKey = text;
		KSLabel label = new KSLabel();
		label.setText(text);
		label.addClickHandler(handler);
	}
	
	public Link(String navKey, HasClickHandlers widget, String url){
		this.navKey = navKey;
		widget.addClickHandler(handler);
	}
	
	public Link(String navKey, HasClickHandlers widget, Callback<String> callback){
		this.navKey = navKey;
		widget.addClickHandler(handler);
	}
	
	private void doLinkActions(){
		for(Callback<String> callback: callbacks){
			callback.exec(navKey);
		}
		
		if(url != null && !(url.isEmpty())){
			redirectToUrl(url);
		}
	}
	
	private static native void redirectToUrl(String url)/*-{
		$wnd.location = url;
	}-*/;
	
	public String getNavKey() {
		return navKey;
	}

	public void setNavKey(String navKey) {
		this.navKey = navKey;
	}

	public void addCallback(Callback<String> callback){
		callbacks.add(callback);
	}
	
	public void removeCallback(Callback<String> callback){
		callbacks.remove(callback);
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public void setFireNavEvents(boolean fireEvents){
		fireNavEvents = fireEvents;
	}
	
	public void setController(Controller controller){
		this.controller = controller;
	}
	
}
