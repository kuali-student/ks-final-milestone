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
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public abstract class NavigationHandler implements ClickHandler, KeyDownHandler {
	private final String url;
	 private final Controller controller;
	 private final String navigationKey;
	 private final Enum<?> navEnum;
	 
	 public NavigationHandler(String url) {
	  this.url = url;
	  this.controller = null;
	  this.navigationKey = null;
	  this.navEnum = null;
	 }
	 
	 public NavigationHandler(Controller controller, String navigationKey) {
	  this.url = null;
	  this.controller = controller;
	  this.navigationKey = navigationKey;
	  this.navEnum = null;
	 }
	 
	 public NavigationHandler(Controller controller, Enum<?> navigationEnum) {
		  this.url = null;
		  this.controller = controller;
		  this.navigationKey = null;
		  this.navEnum = navigationEnum;
	 }
	 
	 public void onClick(ClickEvent event) {
		 doNavigate();
	 }

	 public void onKeyDown(KeyDownEvent event) {
		 doNavigate();
	 }
	 
	 protected void doNavigate() {
		 beforeNavigate(new Callback<Boolean>() {

		   @Override
		   	public void exec(Boolean result) {
			    if (result) {
				     if (url != null) {
				    	 Window.Location.assign(url);
				     } else if(navEnum != null){
				    	 controller.fireApplicationEvent(new NavigationActionEvent(navEnum));
				     }
				     else{
				    	 controller.fireApplicationEvent(new NavigationActionEvent(navigationKey));
				     }
				}
			}
		   
		 });
	 }
		 
	 public abstract void beforeNavigate(Callback<Boolean> callback);
}