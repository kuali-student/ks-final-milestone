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
package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;

/**
 * 
 * An abstract class that extends a section to implement the view interface. Extend 
 * this class if you wish to create view that implements all functionality of
 * a section layout. 
 * 
 * @author Kuali Student Team
 *
 */
public abstract class SectionView extends Section implements View{
    private Enum<?> viewEnum;
    private String viewName;

    /**
     * Constructs a new view with an associated controller and view name
     * 
     * @param controller
     *            the controller associated with the view
     * @param name
     *            the view name
     */
    public SectionView(LayoutController controller, Enum<?> viewEnum, String viewName) {
    	super.setLayoutController(controller);
        this.viewEnum = viewEnum;
        this.viewName = viewName;
        sectionTitle = SectionTitle.generateH1Title(getName());
    }

    public SectionView(Enum<?> viewEnum, String viewName) {
        this.viewEnum = viewEnum;
        this.viewName = viewName;
        sectionTitle = SectionTitle.generateH1Title(getName());
    }
        
    /** 
     * This method gets view name enumeration
     * 
     * @return
     */
    public Enum<?> getViewEnum() {
        return viewEnum;
    }
    
    /**
     * Called by controller before the view is displayed to allow lazy initialization or any other preparatory work to be
     * done.
     */
    public void beforeShow() {
    	this.resetFieldInteractionFlags();
    }

	/**
     * Called by the controller before the view is hidden to allow the view to perform cleanup or request confirmation from
     * the user, etc. Can cancel the action by returning false.
     * 
     * @return true if the view can be hidden, or false to cancel the action.
     */
    @Override
    public boolean beforeHide() {
    	//if()
        return true;
    }

    /**
     * Returns the controller associated with the view
     * 
     * @see org.kuali.student.common.ui.client.mvc.View#getController()
     */
    @Override
    public Controller getController() {
        return super.getLayoutController();
    }

    /**
     * Returns the view's name
     * 
     * @see org.kuali.student.common.ui.client.mvc.View#getName()
     */
    @Override
    public String getName() {
        return viewName;
    }
    
    public void setController(Controller controller) {
    	if (controller instanceof LayoutController) {
    		super.setLayoutController((LayoutController) controller);
    	} else {
    		throw new IllegalArgumentException("Configurable UI sections require a LayoutController, not a base MVC controller");
    	}
    }


    
}
