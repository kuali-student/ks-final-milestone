package org.kuali.student.lum.lu.ui.main.client.view;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.service.ServerPropertiesRpcService;
import org.kuali.student.common.ui.client.service.ServerPropertiesRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Frame;

public class ActionListView extends ViewComposite {
    private final String ACTION_LIST_URL	= "ks.rice.actionList.serviceAddress";
	
    private ServerPropertiesRpcServiceAsync serverPropertiesRpcService = GWT.create(ServerPropertiesRpcService.class);
    
	private Frame actionList = new Frame();
	private boolean loaded = false;
	
	private String actionListUrl = null;
	
	public ActionListView(Controller controller, String name) {
		super(controller, name);
		actionList.addStyleName("KS-Action-List");
		initWidget(actionList);
	}

	@Override
	protected void onLoad() {
	    if (!loaded && actionListUrl == null){
			List<String> serverPropertyList = Arrays.asList(ACTION_LIST_URL);
			
	        serverPropertiesRpcService.get(serverPropertyList, new AsyncCallback<Map<String,String>>() {
	            public void onFailure(Throwable caught) {
	            	loaded = false;
	            }
	            
	            public void onSuccess(Map<String,String> result) {
	                if(result != null){
	                	String actionListUrl 	= result.get(ACTION_LIST_URL);
	            	    actionList.setUrl(actionListUrl);
	            	    loaded = true;
	                }
	            }	            
	        });	    	
	    } else {
	    	actionList.setUrl(actionListUrl);
	    }
	}

}
