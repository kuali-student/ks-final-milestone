package org.kuali.student.commons.ui.widgets;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class BusyWidgetShade extends Composite {
    static Map<Widget, BusyWidgetShade> shades = new HashMap<Widget, BusyWidgetShade>();
    
    public static void shade(Widget w) {
        if (!shades.containsKey(w)) {
            BusyWidgetShade bws = new BusyWidgetShade(w);
            RootPanel.get().add(bws, w.getAbsoluteLeft(), w.getAbsoluteTop());
            shades.put(w, bws);
        }
    }
    public static void unshade(Widget w) {
        BusyWidgetShade bws = shades.get(w);
        if (bws != null) {
            RootPanel.get().remove(bws);
            shades.remove(w);
        }
    }
    
    final AbsolutePanel panel = new AbsolutePanel(); 
    final Image spinner = new Image("images/busy-spinner.gif");
    
    private BusyWidgetShade(Widget w) {
        panel.setHeight(w.getOffsetHeight() + "px");
        panel.setWidth(w.getOffsetWidth() + "px");
        panel.setStyleName("KS-BusyWidgetShade");
        int top = (int) (w.getOffsetHeight() / 2) - 16;
        int left = (int) (w.getOffsetWidth() / 2) - 16;
        if (top < 0) {
            top = 0;
        }
        if (left < 0) {
            left = 0;
        }
        panel.add(spinner, left, top);
        super.initWidget(panel);
    }
    
}
