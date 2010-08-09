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

package org.kuali.student.common.ui.client.mvc.history;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.breadcrumb.BreadcrumbManager;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

public class HistoryManager {
    private static final NavigationEventMonitor monitor = new NavigationEventMonitor();
	public static String VIEW_ATR = "view";
    private static Controller root;
    private static boolean logNavigationHistory = true;
    private static Locations locations;
    
    public static void bind(Controller controller, Locations views) {
    	locations = views;
        root = controller;
        root.addApplicationEventHandler(NavigationEvent.TYPE, monitor);
        History.addValueChangeHandler(new ValueChangeHandler<String>() {

            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                String token = event.getValue();
                if (token != null) {
                    root.onHistoryEvent(token);
                }
            }
            
        });
    }
    
    public static String[] splitHistoryStack(String historyStack){
    	return historyStack.split("/");
    }
    
    public static Map<String, String> getTokenMap(String token){
    	Map<String, String> pairs = new HashMap<String, String>();
    	String[] arr = token.split("&");
    	for (String s : arr) {
    		if(s.contains("=")){
	    		String[] tmp = s.split("=");
	    		if(tmp.length == 2){
	    			pairs.put(tmp[0], tmp[1]);
	    		}
    		}
    		else{
    			//view name do not have = sign required; for better readability
    			//putting a token without = sign as the view key
    			pairs.put("view", s);
    		}
    	}
    	return pairs;
    }
    
    public static String nextHistoryStack(String historyStack){
    	String[] arr= historyStack.split("/", 2);
    	if(arr.length == 2){
    		return arr[1];
    	}
    	else{
    		return "";
    	}
    }
    
    public static void processWindowLocation(){
    	boolean navigateSuccess = false;
    	if(Window.Location.getQueryString() != null && 
    			!Window.Location.getQueryString().isEmpty()){
    		String view = Window.Location.getParameter(VIEW_ATR);
    		String docId = Window.Location.getParameter(ViewContext.ID_ATR);
    		String idType = Window.Location.getParameter(ViewContext.ID_TYPE_ATR);
    		if(view != null && docId != null && idType != null){
    			String path = locations.getLocation(view);
    			if(path != null){
    				ViewContext context = new ViewContext();
    				context.setIdType(idType);
    				context.setId(docId);
    				navigate(path, context);
    				navigateSuccess = true;
    			}
    		}
    	}
    	if(!navigateSuccess){
    		navigate(Window.Location.getHash().trim());
    	}
    }
    
    public static void navigate(String path){
    	if(path != null && !path.isEmpty() && path.startsWith("/")){
	    	logNavigationHistory = false;
	    	root.onHistoryEvent(path);
	    	logHistoryChange();
	    	logNavigationHistory = true;
    	}
    }
    
    public static String collectHistoryStack() {
        String result = root.collectHistory("");
		if(result == null){
			result = "";
		}
        return result;
    }
    
    public static void logHistoryChange() {
        String historyStack = collectHistoryStack();
        History.newItem(historyStack, false);
        BreadcrumbManager.updateLinks(historyStack);
    }
    
    private static class NavigationEventMonitor implements NavigationEventHandler{
        private static final int EVENT_DELAY = 100;
        private long lastEvent = -1;
        
        private final Timer timer = new Timer() {

            @Override
            public void run() {
                if (lastEvent < (System.currentTimeMillis()-EVENT_DELAY)) {
                    this.cancel();
                    lastEvent = -1;
                    logHistoryChange();
                }
            }
            
        };
        
        @Override
        public void onNavigationEvent(NavigationEvent event) {
        	if(logNavigationHistory){
        		logHistoryChange();
        	}
        }
    }
    
    public static void setLogNavigationHistory(boolean log){
    	logNavigationHistory = log;
    }

	public static void navigate(String path, ViewContext context) {
		path = appendContext(path, context);
		navigate(path);
		
	}

	public static String appendContext(String path, ViewContext context) {
		if(context.getId() != null && !context.getId().isEmpty()){
			path = path + "&" + ViewContext.ID_ATR + "=" + context.getId();
		}
		if(context.getIdType() != null){
			path = path + "&" + ViewContext.ID_TYPE_ATR + "=" + context.getIdType();
		}
		//TODO add the ability for view context to add a variety of additional attributes
		return path;
	}
}
