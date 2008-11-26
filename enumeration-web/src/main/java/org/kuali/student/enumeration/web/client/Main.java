package org.kuali.student.enumeration.web.client;


import org.kuali.student.enumeration.dto.EnumerationMeta;
import org.kuali.student.enumeration.dto.EnumerationMetaList;
import org.kuali.student.enumeration.web.client.service.EnumerationGWTClientService;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class Main implements EntryPoint {
    public Main(){
   
    }
    public void onModuleLoad() {
        
        
        EnumerationGWTClientService.Util.getInstance().fetchEnumerationMetas(new AsyncCallback<EnumerationMetaList>() {
            public void onFailure(Throwable caught) {
                throw new RuntimeException("Unable to load enum meta  objects", caught);
            }
            public void onSuccess(EnumerationMetaList metaList) {
                System.out.println("LOading info");
                for (EnumerationMeta meta : metaList.getEnumerationMeta()) {
                    RootPanel.get().add(new HTML(meta.getName()));
                }
            }
        });
    }
}