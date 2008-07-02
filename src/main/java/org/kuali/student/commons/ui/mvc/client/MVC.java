package org.kuali.student.commons.ui.mvc.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point for the MVC module.
 * 
 * @author wilj
 */
public class MVC implements EntryPoint {

    /**
     * Does nothing
     */
    public void onModuleLoad() {
    // do nothing
    }

    /**
     * Utility method for finding a widget's parent controller.
     * 
     * @param widget
     *            widget for which to find the controller
     * @return the controller for the widget, or null if no controller exists in the widget hierarchy
     */
    public static ControllerComposite findParentController(Widget widget) {
        ControllerComposite result = null;
        while ((result == null) && (widget.getParent() != null)) {
            widget = widget.getParent();
            if (widget instanceof ControllerComposite) {
                result = (ControllerComposite) widget;
            }
        }
        return result;
    }

}
