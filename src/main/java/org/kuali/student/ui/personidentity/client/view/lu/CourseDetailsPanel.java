/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view.lu;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeEvent;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListener;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListenerProxy;
import org.kuali.student.ui.personidentity.client.controller.LearningUnitController;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;
import org.kuali.student.ui.personidentity.client.model.lu.LuModelState;
import org.kuali.student.ui.personidentity.client.view.AdminEditPanel;
import org.kuali.student.ui.personidentity.client.view.HidablePanel;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.TreeListener;

/**
 * @author Garey
 *
 */
public class CourseDetailsPanel extends HorizontalPanel {

	LuTreePanel	luTree = null;
	CourseDetailsRegPanel cDetails = null;
	HidablePanel hPanel = new HidablePanel();
    
	private PropertyChangeListener listener  
	= new PropertyChangeListenerProxy(
            "currLui",
            new PropertyChangeListener() {
                public void propertyChange(
                    PropertyChangeEvent propertyChangeEvent) {
                    if(cDetails == null){
                    	cDetails = new CourseDetailsRegPanel();                    	
                    }
                    cDetails.populate(LuModelState.getInstance().getCurrLui());
                }
            });
	
	
	private TreeListener DISPLAY_COURSE_DETAILS = new TreeListener(){				
		public void onTreeItemSelected(TreeItem item) {
			
			if(item instanceof LuiTreeItem){
				final LuiTreeItem tItem = (LuiTreeItem)item;		
				tItem.addStyleName("KS-Label");
				LearningUnitController.setCurrentLui(tItem.getLuiDisplay().getLuiId());							
			}			
			if(item instanceof CluDisplayItem){
				 
				 cDetails.populate(((CluDisplayItem)item).getCluDisplay());
				
			}
		}
		
		public void onTreeItemStateChanged(TreeItem item) {
		}
	};
	
	
	/**
	 * 
	 */
	public CourseDetailsPanel() {
		luTree = new LuTreePanel();
		cDetails = new CourseDetailsRegPanel();
		
		luTree.addTreeListener(DISPLAY_COURSE_DETAILS);
		LuModelState.getInstance().addPropertyChangeListener(listener);
		
		setup();
	}
	
	protected void setup(){
		hPanel.setTitle(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("availableCourses"));
		hPanel.setCenterWidget(luTree);
		hPanel.addStyleName("KS-PersonAdvancedSearch-Panel");
		
		this.add(hPanel);
		this.add(cDetails);
	}
	
	public void setCourseDetails(GwtLuiInfo in){
		cDetails.populate(in);
	}
	
	public void onLoad() {
	    cDetails.setStyleName("CourseDetailsRegPanel");
	    hPanel.setWidth("160px");
	    luTree.setWidth("160px");
	    luTree.addStyleName("LuCourseTree");
	    super.setCellWidth(hPanel, "160px");
	}

}
