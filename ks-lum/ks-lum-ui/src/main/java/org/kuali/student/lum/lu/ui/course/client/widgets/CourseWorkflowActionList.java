package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.StylishDropDown;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class CourseWorkflowActionList extends StylishDropDown {
    private static final String MSG_GROUP = "course";
    
	private KSMenuItemData modifyCourseActionItem;
	private KSMenuItemData activateCourseActionItem;
	private KSMenuItemData inactivateCourseActionItem;
	private KSMenuItemData retireCourseActionItem;
	
	private final KSLightBox activateDialog = new KSLightBox();
    private VerticalSection activateSection = new VerticalSection();
    
    private boolean isCurrentVersion;
    private boolean isInitialized = false;
    
    // storing this list at multiple layers: here and in StylishDropDown.menu.items.  We need it here to test for empty
    private List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();
    
    public CourseWorkflowActionList(String label) {
    	super(label);
    	
    	this.setVisible(false);
        this.addStyleName("KS-Workflow-DropDown");
    	
    }

	public CourseWorkflowActionList(String label, final ViewContext viewContext, final String modifyPath, boolean isCurrentVersion) {
    	super(label);
        
    	this.setVisible(false);
        this.addStyleName("KS-Workflow-DropDown");
        
        init(viewContext, modifyPath, isCurrentVersion);
	}
	
	public void init (final ViewContext viewContext, final String modifyPath, boolean isCurrentVersion) {
		
		if (!this.isInitialized) {
	    	buildActivateDialog();
	    	
	    	this.isCurrentVersion = isCurrentVersion;
	    	
	    	// TODO: use messages
	    	modifyCourseActionItem = new KSMenuItemData(this.getMessage("cluModifyItem"), new ClickHandler(){
	
				@Override
				public void onClick(ClickEvent event) {
					HistoryManager.navigate(modifyPath, viewContext);
				}
			});
	    	activateCourseActionItem = new KSMenuItemData(this.getMessage("cluActivateItem") + " (Not Yet Implemented)", new ClickHandler(){
	
				@Override
				public void onClick(ClickEvent event) {
					showStateDialog(LUUIConstants.LU_STATE_ACTIVE);
				}
			});
	    	inactivateCourseActionItem = new KSMenuItemData(this.getMessage("cluInactivateItem") + " (Not Yet Implemented)", new ClickHandler(){
	
				@Override
				public void onClick(ClickEvent event) {
						// TODO: Inactivate
				}
			});
	    	retireCourseActionItem = new KSMenuItemData(this.getMessage("cluRetireItem") + " (Not Yet Implemented)", new ClickHandler(){
	
				@Override
				public void onClick(ClickEvent event) {
						// TODO: Retire
				}
			});
		}
		
		this.isInitialized = true;
    }
    
    private void showStateDialog(String newState) {
    	if (newState.equals(LUUIConstants.LU_STATE_RETIRED)) {
    		// TODO: create Retire dialog
    	} else if (newState.equals(LUUIConstants.LU_STATE_ACTIVE)) {     		
    		// TODO: use message e.g. activateCurrentInstr, activateModificationInstr    		
    		activateSection.setInstructions(getInstructions(newState));    				
        	activateDialog.show();
    	} else if (newState.equals(LUUIConstants.LU_STATE_INACTIVE)) {
    		// TODO: create Inactivate dialog
    	}
    	
    }
    
    public static boolean isCurrentVersion(DataModel cluModel) {
    	Date curVerStartDt = cluModel.get(CreditCourseConstants.VERSION_INFO + QueryPath.getPathSeparator() + CreditCourseConstants.VERSION_CURRENT_VERSION_START);		
    	Date curVerEndDt = cluModel.get(CreditCourseConstants.VERSION_INFO + QueryPath.getPathSeparator() + CreditCourseConstants.VERSION_CURRENT_VERSION_END);
    	if (curVerStartDt != null && curVerEndDt == null) 
    		return true;
    	else
    		return false;
    	
    }
    
    private String getInstructions(String newState) {
    	
    	if (isCurrentVersion)
    		// TODO: message
    		return "Activating this course makes it viewable and available for scheduling.";
    	else 
    		// TODO: message
    		return "Activate this course makes it viewable and available for scheduling. The previous version will be inactivated, and available for reference in the version history.";
    }
    
    private void buildActivateDialog(){
	    FlowPanel panel = new FlowPanel();
	    
	    // TODO: use messages
	    activateSection = new VerticalSection(SectionTitle.generateH2Title("Activate Course"));
	    
        panel.add((Widget)activateSection);
	    
	    KSButton activate = new KSButton("Activate",new ClickHandler(){
            public void onClick(ClickEvent event) {
                //activateSection.updateModel(cluModel);
                //set previous active to superseded
                //set this version to active
                setCourseState(LUUIConstants.LU_STATE_ACTIVE);
                activateDialog.hide();
            }
	    });
	    activateDialog.addButton(activate);
	    
	    KSButton cancel = new KSButton("Cancel", ButtonStyle.ANCHOR_LARGE_CENTERED, new ClickHandler(){
            public void onClick(ClickEvent event) {
                activateDialog.hide();
            }
	    });
	    activateDialog.addButton(cancel);

	    activateDialog.setWidget(panel);
    }
    
    
    // This depends heavily on updateCourseActionItems().  Changes there will 
    // affect assumptions made here
    private void setCourseState(String newState) {    	
    	
    	if (newState.equals(LUUIConstants.LU_STATE_APPROVED)) {
    		
    	} else if (newState.equals(LUUIConstants.LU_STATE_ACTIVE)) {
/*    		
			String oldState = cluModel.get(CreditCourseConstants.STATE);
			QueryPath statePath = QueryPath.concat(CreditCourseConstants.STATE);
			cluModel.set(statePath, LUUIConstants.LU_STATE_ACTIVE);
	*/		
			// assumption A: since we are activating we can assume the current 
			//				state is approved or inactive
			// basis for A: only approved or inactive courses can be activated
			// assumption B: if this is the current version it is a new course
			//				(no previously active versions)
			// basis for B: modifications approved for active courses are not
			//				set to current until they are activated
			//				(we're about to do this)
			// assumption C: if this is not the current version it is a 
			//				modified or inactivated version of a previously 
			//              active course if there is a current, active version
			//				we need to supersede that and set it's end date
			// basis for C: assuming A we can narrow our scope of change to the
			//				version we're modifying and the current version
			if (!isCurrentVersion) {
				// set previously active version to superseded
				
			}

    		// set state to active
    		// set current version
    	} else if (newState.equals(LUUIConstants.LU_STATE_INACTIVE)) {
    		
    	}
    }
    
    // This depends heavily on setCourseState().  Changes here will 
    // affect assumptions made there
    public void updateCourseActionItems(String cluState) {
    	items.clear();      
    	
    	if (cluState.equals(LUUIConstants.LU_STATE_APPROVED)) {
    		items.add(modifyCourseActionItem);
    		items.add(activateCourseActionItem);
    		items.add(retireCourseActionItem);
    	} else if (cluState.equals(LUUIConstants.LU_STATE_ACTIVE)) {
    		items.add(modifyCourseActionItem);
    		items.add(inactivateCourseActionItem);
    		items.add(retireCourseActionItem);
    	} else if (cluState.equals(LUUIConstants.LU_STATE_INACTIVE)) {
    		items.add(activateCourseActionItem);
    	}
		
    	setItems(items);
    		
    }
    
    public boolean isEmpty() {
    	return (items.size() == 0);
    }
    
    public String getMessage(String courseMessageKey) {
    	String msg = Application.getApplicationContext().getMessage(MSG_GROUP, courseMessageKey);
    	if (msg == null) {
    		msg = courseMessageKey;
    	}
    	return msg;
    }

}
