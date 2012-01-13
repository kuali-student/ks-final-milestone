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
import java.util.Iterator;
import java.util.Map;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.breadcrumb.BreadcrumbManager;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Hidden;

/**
 * The HistoryManager is responsible for handling history events and navigating within the application.  Leverages
 * GWT history class.
 * <br><br>
 * History tokens which appear in the url follow this format: 
 * <br>/HOME/CURRICULUM_HOME/VIEW_COURSE/VIEW&docId=<id>&idType=<type>/BRIEF
 * <br>In this example, the location is app -> home controller -> curriculum home controller -> view course controller ->
 * view controller with docId and idType specified for use in its view context - > brief view
 * 
 * 
 * @author Kuali Student Team
 * @see History
 */
/**
 * @author Kuali Student Team
 *
 */
public class HistoryManager {
    private static final NavigationEventMonitor monitor = new NavigationEventMonitor();
	public static String VIEW_ATR = "view";
    private static Controller root;
    private static boolean logNavigationHistory = true;
    private static Locations locations;
    
    /**
     * Binds a controller to this HistoryManager that specifies the top level controller for the application.
     * The locations passed in are used for determining navigable locations.
     * 
     * @param controller
     * @param views
     */
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
    
    /**
     * Gets the property map from the history token passed in.  Properties specified by 
     * "&propertyName=propertyValue" in the url token.
     * 
     * @param token
     * @return
     */
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
    
    /**
     * Gets the next piece of the history token to be interpreted split on "/"
     * 
     * @param historyStack
     * @return
     */
    public static String nextHistoryStack(String historyStack){
    	String[] arr= historyStack.split("/", 2);
    	if(arr.length == 2){
    		return arr[1];
    	}
    	else{
    		return "";
    	}
    }
    
    /**
     * Reads the url history token and navigates to the appropriate location.  History following the
     * "#" takes precedence. Url can also be formatted in a parameter format following the "?" symbol using
     * the "view" parameter for the view path to navigate to, but must exist in Locations passed into this
     * HistoryManager.
     */
    public static void processWindowLocation(){
    	boolean navigateSuccess = false;
    	Element loc = DOM.getElementById("locationHash");
    	//String value = loc.getValue().trim();
    	String value = loc.getAttribute("value");
    	if(value != null && !value.equals("") && value.startsWith("/")){
    		navigate(value);
    	}
    	else if(Window.Location.getQueryString() != null && 
    			!Window.Location.getQueryString().isEmpty()){
    		String view = Window.Location.getParameter(VIEW_ATR);
    		String docId = Window.Location.getParameter(ViewContext.ID_ATR);
    		String idType = Window.Location.getParameter(ViewContext.ID_TYPE_ATR);
    		if (view != null){
    		    String path = locations.getLocation(view);
    		    if(docId != null){
    		        if(path != null){
    		            ViewContext context = new ViewContext();
    		            if (idType != null){
    		                context.setIdType(idType);
    		            }
    		            context.setId(docId);   
    		            setAttributes(context);
    		            navigate(path, context);
    		            navigateSuccess = true;
    		        }
    		    } else {
    		        navigate(path);
    		        navigateSuccess = true;
    		    }
    		}
    	}
    	if(!navigateSuccess){
    		navigate(Window.Location.getHash().trim());
    	}
    }
    
    /**
     * Add the parameters from the request on the context.
     * @param context
     */
    private static void setAttributes(ViewContext context){ 
        for (String key : Window.Location.getParameterMap().keySet()){
            if ((!key.equals(VIEW_ATR)) && (!key.equals(ViewContext.ID_ATR)) && (!key.equals(ViewContext.ID_TYPE_ATR))){
                context.setAttribute(key, Window.Location.getParameter(key));
            }
        }
    }
    
    /**
     * Navigate to the path specified including any parameters needed by the views.  Paths must begin
     * with / and parameters following view enum names within that path in the format "&name=value"
     * @param path
     */
    public static void navigate(String path){
    	if(path != null && !path.isEmpty() && path.startsWith("/")){
	    	logNavigationHistory = false;
	    	root.onHistoryEvent(path);
	    	logHistoryChange();
	    	logNavigationHistory = true;
    	}
    }
    
    /**
     * Collects the current history stack based on user location in the app
     * 
     * @return
     */
    public static String collectHistoryStack() {
        String result = null;
        if (root != null){
            result = root.collectHistory("");
        }
		if(result == null){
			result = "";
		}
        return result;
    }
    
    /**
     * Logs the current location to the history stack and updates the breadcrumb manager as appropriate
     * 
     */
    public static void logHistoryChange() {
        String historyStack = collectHistoryStack();
        if(historyStack.endsWith("/")){
        	historyStack = historyStack.substring(0, historyStack.length()-1);
        }
        String currentToken = History.getToken();
        if(!currentToken.equals(historyStack)){
        	History.newItem(historyStack, false);
        }
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
        	else{
        		String historyStack = collectHistoryStack();
        		BreadcrumbManager.updateLinks(historyStack);
        		HistoryManager.setLogNavigationHistory(true);
        	}
        }
    }
    
    /**
     * Passing in false, turns off history tracking.  History tracking is turned back on by the default 
     * controller implementation after a navigation is completed.
     * 
     * @param log
     */
    public static void setLogNavigationHistory(boolean log){
    	logNavigationHistory = log;
    }

	/**
	 * Navigate to the path passed in with the context passed in appended to it appropriately.  The view context
	 * passed in will be appended to the url in the history token/address bar
	 * 
	 * @param path
	 * @param context
	 */
	public static void navigate(String path, ViewContext context) {
		path = appendContext(path, context);
		navigate(path);
		
	}

	/**
	 * Appends the context to the path passed safely in a format the navigate command will recognize
	 * @param path
	 * @param context
	 * @return
	 */
	public static String appendContext(String path, ViewContext context) {
		if(context.getId() != null && !context.getId().isEmpty()){
			path = path + "&" + ViewContext.ID_ATR + "=" + context.getId();
		}
		if(context.getIdType() != null){
			path = path + "&" + ViewContext.ID_TYPE_ATR + "=" + context.getIdType();
		}
		if(!context.getAttributes().isEmpty()){
			Map<String,String> attributes = context.getAttributes();
			Iterator<String> it = attributes.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				String value = attributes.get(key);
				path = path + "&" + key + "=" + value;
			}
		}
		//TODO add the ability for view context to add a variety of additional attributes
		return path;
	}
}
