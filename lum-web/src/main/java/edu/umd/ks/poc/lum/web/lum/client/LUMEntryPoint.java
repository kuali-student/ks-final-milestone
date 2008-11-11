package edu.umd.ks.poc.lum.web.lum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

public class LUMEntryPoint implements EntryPoint {

    RootPanel rootPanel = null;

    public void onModuleLoad() {
        if(DOM.getElementById("loadingSpinner") != null)
            DOM.removeChild(RootPanel.getBodyElement(), DOM.getElementById("loadingSpinner"));
        RootPanel.get().add(new LUMMain());

    }

}
