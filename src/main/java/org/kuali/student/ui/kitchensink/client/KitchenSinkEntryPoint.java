package org.kuali.student.ui.kitchensink.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class KitchenSinkEntryPoint implements EntryPoint {

    
    public void onModuleLoad() {
        KitchenSinkMain ksm = new KitchenSinkMain();
        String exampleClass = Window.Location.getParameter("exampleClass");
        exampleClass = (exampleClass == null) ? "" : exampleClass.trim();
        if (exampleClass.equals("")) {
            RootPanel.get().add(ksm);
        } else {
        try {
            KitchenSinkExample kse = ksm.getExample(exampleClass);
            LiveCSSComposite liveCSS = new LiveCSSComposite();
            liveCSS.setExample(kse);
            RootPanel.get().add(liveCSS);
        } catch (Exception e) {
            HTML message = new HTML("Unable to load class: " + exampleClass + "<br/>Reason:<br/>" + dumpException(e));
            RootPanel.get().add(message);
        }
        }
    }
    
    private String dumpException(Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.getMessage());
        for (StackTraceElement ste : e.getStackTrace()) {
            sb.append("<br/>");
            sb.append(ste.toString());
        }
        return sb.toString();
    }

}
