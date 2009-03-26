package org.kuali.student.core.organization.ui.client.view;


import org.kuali.student.common.ui.client.application.ApplicationComposite;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class OrgEntryPoint implements EntryPoint{

    ApplicationComposite app = new ApplicationComposite();
    
    public void onModuleLoad() {
        if(DOM.getElementById("loadingSpinner") != null)
            DOM.removeChild(RootPanel.getBodyElement(), DOM.getElementById("loadingSpinner"));

        app.setContent(getContent());
        RootPanel.get().add(app);
    }
    
    public Widget getContent(){
        DockPanel mainPanel = new DockPanel();
        SimplePanel content = new SimplePanel();
        
        Label pageTitle = new Label("Organization Management");
        pageTitle.setStyleName("page-title");        
        mainPanel.setStyleName("ks-main");
        mainPanel.add(pageTitle, DockPanel.NORTH);

        OrgMenu orgMenu = new OrgMenu(content); 
        mainPanel.add(orgMenu, DockPanel.WEST);
        mainPanel.setCellWidth(orgMenu, "200px");
        mainPanel.add(content, DockPanel.CENTER);
        
        return mainPanel;
    }


}
