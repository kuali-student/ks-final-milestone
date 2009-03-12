package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.impl.KSResizablePanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

/**
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
     * This overridden method ...
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSResizablePanelAbstract#setNewSize(int, int)
     */
    @Override
    public void setNewSize(int w, int h) {
        panel.setNewSize(w, h);
        
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSResizablePanelAbstract#setWidget(com.google.gwt.user.client.ui.Widget)
     */
    @Override
    public void setWidget(Widget w) {
        panel.setWidget(w);
        
    }

}
