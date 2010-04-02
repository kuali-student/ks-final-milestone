package org.kuali.student.common.ui.client.configurable.mvc.views;

import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;


import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.common.ui.client.mvc.history.HistoryStackFrame;


public abstract class SectionView extends BaseSection implements View{

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
        sectionTitle = SectionTitle.generateH2Title(getName());
    }

    public SectionView(Enum<?> viewEnum, String viewName) {
        this.viewEnum = viewEnum;
        this.viewName = viewName;
        sectionTitle = SectionTitle.generateH2Title(getName());
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
    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {
    	this.resetFieldInteractionFlags();
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
    	//This update model call was added due to KSCOR-162
    	this.updateModel();
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
    
    @Override
    public void collectHistory(HistoryStackFrame frame) {
        // do nothing
    }

    @Override
    public void onHistoryEvent(HistoryStackFrame frame) {
        // do nothing
    }

}
