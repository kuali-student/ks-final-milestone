/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;

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
				     }
				}
			}
		   
		 });
	 }
	 
	 
		 
	 public String getUrl() {
		return url;
	}

	public Controller getController() {
		return controller;
	}

	public String getNavigationKey() {
		return navigationKey;
	}

	public Enum<?> getNavEnum() {
		return navEnum;
	}

	public abstract void beforeNavigate(Callback<Boolean> callback);
}
