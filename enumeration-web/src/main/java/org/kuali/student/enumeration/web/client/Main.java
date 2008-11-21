package org.kuali.student.enumeration.web.client;


import java.util.List;

import org.kuali.student.enumeration.web.client.service.EnumerationGWTClientService;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class Main implements EntryPoint {
    public Main(){
   
    }
    public void onModuleLoad() {
        
        
        EnumerationGWTClientService.Util.getInstance().fetchEnumertionMeta(new AsyncCallback<List<String>>() {
            public void onFailure(Throwable caught) {
           
                throw new RuntimeException("Unable to load enum meta  objects", caught);
            }

            public void onSuccess(List<String> stringList) {
                System.out.println("LOading info");
                // add the results to the model
                for (String rInfo : stringList) {
                    RootPanel.get().add(new HTML(rInfo));
                }
            }
        });
     
    }
}