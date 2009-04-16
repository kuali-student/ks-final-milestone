package org.kuali.student.lum.lu.ui.main.client;


import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class LUMMainEntryPoint implements EntryPoint{
    
    private final LUMApplicationManager manager = new LUMApplicationManager();
    
    @Override
    public void onModuleLoad() {
        RootPanel.get().add(manager);
        manager.showDefaultView();
        // TODO Bsmith - THIS METHOD NEEDS JAVADOCS
        
    }

}
