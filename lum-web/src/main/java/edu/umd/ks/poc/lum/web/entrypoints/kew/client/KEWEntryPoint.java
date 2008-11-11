package edu.umd.ks.poc.lum.web.entrypoints.kew.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

public class KEWEntryPoint implements EntryPoint {

RootPanel rootPanel = null;

    public void onModuleLoad() {

        if(DOM.getElementById("loadingSpinner") != null)
            DOM.removeChild(RootPanel.getBodyElement(), DOM.getElementById("loadingSpinner"));
        RootPanel.get().add(new KEWMain());

    }

}
