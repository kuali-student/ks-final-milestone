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
package org.kuali.student.common.ui.client.mvc;

import org.kuali.student.common.ui.client.mvc.history.HistoryStackFrame;

import com.google.gwt.user.client.ui.Composite;

/**
 * Abstract class implementing the View interface, which has a handle to it's controller.
 * 
 * @author Kuali Student Team
 */
public abstract class ViewComposite extends Composite implements View {
    private final Controller controller;
    private final String name;

    /**
     * Constructs a new view with an associated controller and view name
     * 
     * @param controller
     *            the controller associated with the view
     * @param name
     *            the view name
     */
    public ViewComposite(Controller controller, String name) {
        this.controller = controller;
        this.name = name;
    }

    /**
     * Called by controller before the view is displayed to allow lazy initialization or any other preparatory work to be
     * done.
     */
    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {
    	// do nothing
    	onReadyCallback.exec(true);
    }

    /**
     * Called by the controller before the view is hidden to allow the view to perform cleanup or request confirmation from
     * the user, etc. Can cancel the action by returning false.
     * 
     * @return true if the view can be hidden, or false to cancel the action.
     */
    @Override
    public boolean beforeHide() {
        return true;
    }

    /**
     * Returns the controller associated with the view
     * 
     * @see org.kuali.student.common.ui.client.mvc.View#getController()
     */
    @Override
    public Controller getController() {
        return controller;
    }

    /**
     * Returns the view's name
     * 
     * @see org.kuali.student.common.ui.client.mvc.View#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Used to clear view 
     * 
     * @see org.kuali.student.common.ui.client.mvc.View#clear()
     */
    public void clear(){
        //do nothing
    }
    
    
    /**
     * Update the model that is associated with this view. This will normally be
     * called by the controller.
     *  
     * @see org.kuali.student.common.ui.client.mvc.View#updateModel()
     */
    public void updateModel(){        
    }
    
    @Override
    public void collectHistory(HistoryStackFrame frame) {
        // do nothing
    }

    @Override
    public void onHistoryEvent(HistoryStackFrame frame) {
        // do nothing
    }
}
