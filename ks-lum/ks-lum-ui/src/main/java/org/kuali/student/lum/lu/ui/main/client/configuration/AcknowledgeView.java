package org.kuali.student.lum.lu.ui.main.client.configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.service.ServerPropertiesRpcService;
import org.kuali.student.common.ui.client.service.ServerPropertiesRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class AcknowledgeView extends ViewComposite{
	private static AcknowledgeUiBinder uiBinder = GWT.create(AcknowledgeUiBinder.class);

	interface AcknowledgeUiBinder extends UiBinder<Widget, AcknowledgeView> {
	}
	
	@UiField
	Label verLabel;
	
	private ServerPropertiesRpcServiceAsync serverPropertiesRpcService = GWT.create(ServerPropertiesRpcService.class);
	private static final String APP_VERSION		= "ks.application.version"; 
	
	public AcknowledgeView(Controller controller, Enum<?> viewType) {
		super(controller, "Acknowledgements", viewType);
	    this.initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void beforeShow(final Callback<Boolean> onReadyCallback) {
		if(verLabel.getText().isEmpty()){
			List<String> serverPropertyList = Arrays.asList(APP_VERSION);
	
	        serverPropertiesRpcService.get(serverPropertyList, new KSAsyncCallback<Map<String,String>>() {
	            public void handleFailure(Throwable caught) {
	            	//ignoring, we'll use the default
	            }
	
	            public void onSuccess(Map<String,String> result) {
	                GWT.log("ServerProperties fetched: "+result.toString(), null);
	                if(result != null){
	                    String appVersion		= result.get(APP_VERSION);
	                    verLabel.setText("Version: " + appVersion);
	                }
	                onReadyCallback.exec(true);
	            }
	
	        });
		}
		else{
			onReadyCallback.exec(true);
		}
		
	}


}
