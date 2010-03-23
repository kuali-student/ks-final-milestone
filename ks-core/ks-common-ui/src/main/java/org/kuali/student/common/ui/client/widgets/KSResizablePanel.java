/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
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
