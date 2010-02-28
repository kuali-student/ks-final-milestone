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


/**
 * This is a simple view composite that delegates all view operations to nested
 * controller. Use of this view allows you to nest controllers in the view
 * hierarchy.
 * 
 * @author Kuali Student Team
 *
 */
public class DelegatingViewComposite extends ViewComposite {
    Controller childController;
    
    /**
     * @param controller
     * @param name
     */
    public DelegatingViewComposite(Controller parentController, Controller childController) {
        super(parentController, "DelegatingViewComposite");
        initWidget(childController);
        this.childController = childController;
    }

    /**
     * Delegates beforeHide to the nested controllers current view
     * 
     * @see org.kuali.student.common.ui.client.mvc.View#beforeHide()
     */
    @Override
    public boolean beforeHide() {
        return childController.getCurrentView().beforeHide();
    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.View#beforeShow()
     */
    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {
        if (childController.getCurrentView() == null){
            childController.showDefaultView(onReadyCallback);
        } else {
            childController.getCurrentView().beforeShow(onReadyCallback);
        }
    }
    
    public Controller getChildController(){
        return childController;
    }
    
    public void setChildController(Controller controller){
        this.childController = controller;
    }

    @Override
    public void collectHistory(HistoryStackFrame frame) {
        childController.collectHistory(frame);
    }

    @Override
    public void onHistoryEvent(HistoryStackFrame frame) {
        childController.onHistoryEvent(frame);
    }

}
