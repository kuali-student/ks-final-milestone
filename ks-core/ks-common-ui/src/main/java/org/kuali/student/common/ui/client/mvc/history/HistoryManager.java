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
    private static Controller root;
    private static boolean logNavigationHistory = true;
    
    public static void bind(Controller controller) {
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
    	navigate(Window.Location.getHash().trim());
    }
    
    public static void navigate(String path){
    	if(path != null && !path.isEmpty() && path.startsWith("/")){
	    	logNavigationHistory = false;
	    	root.onHistoryEvent(path);
	    	logHistoryChange();
	    	logNavigationHistory = true;
    	}
    }
    
/*    public static boolean processHistoryQuerystring() {
        return processHistoryQuerystring(Window.Location.getHash()) || processHistoryQuerystring(Window.Location.getQueryString());
    }
    
    private static boolean processHistoryQuerystring(String queryString) {
        boolean result = false;
        
        try {
            HistoryStackFrame frame = HistoryStackFrame.fromSerializedForm(queryString);
            if (frame != null) {
                root.onHistoryEvent(frame);
                result = true;
            }
        } catch (Exception e) {
            GWT.log("error processing history tokens: " + queryString, e);
        }
        
        return result;
    }*/
    
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
/*	            boolean start = (lastEvent == -1);
	            lastEvent = System.currentTimeMillis();
	            if (start) {
	                timer.scheduleRepeating(EVENT_DELAY);
	            }*/
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
