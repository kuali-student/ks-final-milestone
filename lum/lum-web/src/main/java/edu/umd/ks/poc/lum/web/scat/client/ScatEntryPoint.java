package edu.umd.ks.poc.lum.web.scat.client;

import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.umd.ks.poc.lum.scat.dto.ScatTableInfo;
import edu.umd.ks.poc.lum.web.core.client.GlobalEventDispatcher;
import edu.umd.ks.poc.lum.web.scat.client.CreateScat.CreateScatEvent;
import edu.umd.ks.poc.lum.web.scat.client.SearchScatTables.ScatDetailsEvent;
import edu.umd.ks.poc.lum.web.scat.client.service.ScatRpcService;

public class ScatEntryPoint implements EntryPoint{

    
    public void onModuleLoad() {
        this.addListeners();
        
    }
    
    protected void addListeners(){
        GlobalEventDispatcher.getInstance().addListener(CreateScatEvent.class, new MVCEventListener(){            
            public void onEvent(Class<? extends MVCEvent> event, Object data) {
                ScatTableInfo sInfo = (ScatTableInfo)data;
                if(sInfo!=null){
	                ScatRpcService.Util.getInstance().createScatTable(sInfo, new AsyncCallback<ScatTableInfo>(){
	
						public void onFailure(Throwable caught) {
			                Window.alert(caught.getMessage());						
						}
	
						public void onSuccess(ScatTableInfo scatTableInfo) {
							GlobalEventDispatcher.getInstance().fireEvent(ScatDetailsEvent.class, scatTableInfo);
						}
	                	
	                });
                }

            }});
    }

}
