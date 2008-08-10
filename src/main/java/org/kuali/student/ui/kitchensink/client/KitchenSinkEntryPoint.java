package org.kuali.student.ui.kitchensink.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class KitchenSinkEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        RootPanel.get().add(new KitchenSinkMain());
    }

}
