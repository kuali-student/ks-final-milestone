package org.kuali.student.enumeration.web.client.view;

import org.kuali.student.enumeration.dto.EnumerationMeta;
import org.kuali.student.enumeration.dto.EnumerationMetaList;
import org.kuali.student.enumeration.web.client.service.EnumerationGWTService;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class FetchEnumerationMetasPanel extends FlowPanel {
    Button fetchEnumerationMetasButton = new Button("Fetch Enumeration Metas");
    HTML messageHTML = new HTML();
    public FetchEnumerationMetasPanel(){
        add(fetchEnumerationMetasButton);
        fetchEnumerationMetasButton.addClickListener(new ClickListener(){

            public void onClick(Widget arg0) {
               
                EnumerationGWTService.Util.getInstance().fetchEnumerationMetas(new AsyncCallback<EnumerationMetaList>() {
                    public void onFailure(Throwable caught) {
                        System.out.println("error");
                        messageHTML.setHTML("Unable to load enum meta objects");
                        throw new RuntimeException("Unable to load enum meta objects", caught);
                    }
                    public void onSuccess(EnumerationMetaList metaList) {
                        System.out.println("success");
                        System.out.println("metaList.getEnumerationMeta():"+metaList.getEnumerationMeta().size());
                        
                        for (EnumerationMeta meta : metaList.getEnumerationMeta()) {
                            EnumerationMetaComposite composite = new EnumerationMetaComposite();
                           
                            composite.setEnumerationMeta(meta);
                            add(composite);
                        }
                    }
                });
                
            }});

    }
}
