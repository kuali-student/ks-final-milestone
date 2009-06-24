package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.core.validation.dto.ValidationResult.ErrorLevel;

import com.google.gwt.user.client.ui.Composite;

public abstract class LayoutSectionView extends Composite implements View{
    private Controller controller;
    private String sectionTitle = null;
    private String instructions = null;
    private ArrayList<LayoutSectionView> layoutSectionList = new ArrayList<LayoutSectionView>();    
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
    public LayoutSectionView(Controller controller, Enum<?> viewEnum, String viewName) {
        this.controller = controller;
        this.viewEnum = viewEnum;
        this.viewName = viewName;
    }

    public LayoutSectionView(Enum<?> viewEnum, String viewName) {
        this.controller = null;
        this.viewEnum = viewEnum;
        this.viewName = viewName;
    }

    public ArrayList<LayoutSectionView> getLayoutSectionList(){ 
      return layoutSectionList;
    }
    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void addChildSection(ConfigurableLayoutSection section) {
        //layoutSectionList.add(section);
    }
    
    public void validateChildSection(Callback<ErrorLevel> callback) {
        for (LayoutSectionView layoutSection : layoutSectionList) {
            layoutSection.validate(callback);
        }
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
    // do nothing
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
        return viewName;
    }

    /**
     * Used to clear view 
     * 
     * @see org.kuali.student.common.ui.client.mvc.View#clear()
     */
    public void clear(){
        //do nothing
    }
    
    
    public abstract void validate(Callback<ErrorLevel> callback);
    
    public void setController(Controller controller) {
        this.controller = controller;
    }
    
}
