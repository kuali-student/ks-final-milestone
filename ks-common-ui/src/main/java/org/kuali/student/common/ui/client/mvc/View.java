package org.kuali.student.common.ui.client.mvc;

/**
 * Interface defining the operations necessary to implement a view.
 * 
 * @author Kuali Student Team
 */
public interface View {
    /**
     * Called by controller before the view is displayed to allow lazy initialization or any other preparatory work to be
     * done.
     */
    public void beforeShow();

    /**
     * Called by the controller before the view is hidden to allow the view to perform cleanup or request confirmation from
     * the user, etc. Can cancel the action by returning false.
     * 
     * @return true if the view can be hidden, or false to cancel the action.
     */
    public boolean beforeHide();

    /**
     * Returns the controller associated with the view
     * 
     * @return
     */
    public Controller getController();

    /**
     * Returns the view's name
     * 
     * @return
     */
    public String getName();

    /** 
     * Can be called to reset a view to a cleared state.
     *
     */
    public void clear();
    
    public void updateModel();
    
}
