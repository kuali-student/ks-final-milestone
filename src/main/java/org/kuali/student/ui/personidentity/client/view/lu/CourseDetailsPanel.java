/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view.lu;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeEvent;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListener;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListenerProxy;
import org.kuali.student.ui.personidentity.client.controller.LearningUnitController;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiDisplay;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;
import org.kuali.student.ui.personidentity.client.model.lu.LuModelState;
import org.kuali.student.ui.personidentity.client.view.AdminEditPanel;
import org.kuali.student.ui.personidentity.client.view.HidablePanel;
import org.kuali.student.ui.personidentity.client.view.lu.fastTree.CluDisplayFastItem;
import org.kuali.student.ui.personidentity.client.view.lu.fastTree.LuFastTreePanel;
import org.kuali.student.ui.personidentity.client.view.lu.fastTree.LuiFastTreeItem;

import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * @author Garey
 *
 */
public class CourseDetailsPanel extends HorizontalPanel {

	LuFastTreePanel	luTree = null;
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
	
	/**
	 * 
	 */
	public CourseDetailsPanel() {
		luTree = new LuFastTreePanel();
		cDetails = new CourseDetailsRegPanel();
				
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
	     
	    final Controller c = MVC.findParentController(this);
	    c.getEventDispatcher().addListener(CluDisplayFastItem.CluDisplayFastItemSelect.class, new MVCEventListener() {            
            public void onEvent(Class<? extends MVCEvent> event, Object data) {
                GwtCluInfo item = (GwtCluInfo)data;
                cDetails.populate(item);                
            }
	        
	    });
	    
	    c.getEventDispatcher().addListener(LuiFastTreeItem.LuiFastTreeItemSelect.class, new MVCEventListener() {            
            public void onEvent(Class<? extends MVCEvent> event, Object data) {
                GwtLuiDisplay item = (GwtLuiDisplay)data;
                LearningUnitController.setCurrentLui(item.getLuiId());
                
            }
            
        });
	       
	}

}
