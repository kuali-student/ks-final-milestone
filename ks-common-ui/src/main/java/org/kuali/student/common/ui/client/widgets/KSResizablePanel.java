package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.impl.KSResizablePanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

/**
 * The KSResizablePanel is a resizable panel which can be resized by the user during runtime.  It provides
 * a "handle" in the bottom right corner of the panel which a user can click and drag to resize the panel.
 * 
 * Wrapper class allows replacment of impl class 
 * FIXME KSDialogPanelImpl uses KSResizablePanelImpl, this class 
 * only displays the header kitchensink infoDialogPanel example 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
public class KSResizablePanel  extends KSResizablePanelAbstract { 


    private final KSResizablePanelAbstract panel = GWT.create(KSResizablePanelImpl.class);
    
    public KSResizablePanel() {}
    /**
     * Sets the new size of the resizable panel to the width and height specified.
     * 
     * @param w the new width
     * @param h the new height
     */
    @Override
    public void setNewSize(int w, int h) {
        panel.setNewSize(w, h);
        
    }

    /**
     * Sets the widget to be used as this resizable panel's content
     * 
     * @param w the Widget to be used as the resizable panel's content
     */
    @Override
    public void setWidget(Widget w) {
        panel.setWidget(w);
        
    }

}
