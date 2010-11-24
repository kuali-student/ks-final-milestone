package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.StylishDropDown;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.rice.StudentIdentityConstants;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
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
    private String courseId;
    
    private final BlockingTask processingTask = new BlockingTask("Processing State Change....");
    
    CourseRpcServiceAsync rpcServiceAsync = GWT.create(CourseRpcService.class);
    
    // storing this list at multiple layers: here and in StylishDropDown.menu.items.  We need it here to test for empty
    private List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();
    
    public CourseWorkflowActionList(String label) {
    	super(label);
    	
    	this.setVisible(false);
        this.addStyleName("KS-Workflow-DropDown");
    	
    }

	public CourseWorkflowActionList(String label, final ViewContext viewContext, final String modifyPath, DataModel model, final Callback<String> stateChangeCallback) {
    	super(label);
        
    	this.setVisible(false);
        this.addStyleName("KS-Workflow-DropDown");
        
        init(viewContext, modifyPath, model, stateChangeCallback);
	}
	
	public void init (final ViewContext viewContext, final String modifyPath, final DataModel model, final Callback<String> stateChangeCallback) {

		if (!this.isInitialized) {
	    	buildActivateDialog(stateChangeCallback);
	    	
	    	this.isCurrentVersion = CourseWorkflowActionList.isCurrentVersion(model);
	    	
	    	// TODO: use messages
	    	modifyCourseActionItem = new KSMenuItemData(this.getMessage("cluModifyItem"), new ClickHandler(){
	
				@Override
				public void onClick(ClickEvent event) {
			    	if(viewContext != null && viewContext.getId() != null && !viewContext.getId().isEmpty()){
						viewContext.setId((String)model.get(CreditCourseConstants.VERSION_INFO + QueryPath.getPathSeparator() + CreditCourseConstants.VERSION_IND_ID));
						viewContext.setIdType(IdType.COPY_OF_OBJECT_ID);
			            viewContext.setAttribute(StudentIdentityConstants.DOCUMENT_TYPE_NAME, "kuali.proposal.type.course.modify");
			        }

					HistoryManager.navigate(modifyPath, viewContext);
				}
			});
	    	activateCourseActionItem = new KSMenuItemData(this.getMessage("cluActivateItem"), new ClickHandler(){
	
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
    
    private void buildActivateDialog(final Callback<String> stateChangeCallback){
	    FlowPanel panel = new FlowPanel();
	    
	    activateDialog.setMaxHeight(200);
	    activateDialog.setMaxWidth(200);
	    
	    // TODO: use messages
	    activateSection = new VerticalSection(SectionTitle.generateH2Title("Activate Course"));
	    
        panel.add((Widget)activateSection);
	    
	    KSButton activate = new KSButton("Activate",new ClickHandler(){
            public void onClick(ClickEvent event) {
                //activateSection.updateModel(cluModel);
                //set previous active to superseded
                //set this version to active
                setCourseState(LUUIConstants.LU_STATE_ACTIVE, stateChangeCallback);
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
    
    // TODO: add Retire and Inactivate Dialogs
    
    
    // This depends heavily on updateCourseActionItems().  Changes there will 
    // affect assumptions made here
    private void setCourseState(final String newState, final Callback<String> stateChangeCallback) {
    	KSBlockingProgressIndicator.addTask(processingTask);
    	
    	rpcServiceAsync.changeState(courseId, newState, new KSAsyncCallback<StatusInfo>() {
    		
    		@Override
 	        public void handleFailure(Throwable caught) {
 	            Window.alert("Error Updating State: "+caught.getMessage());
 	            KSBlockingProgressIndicator.removeTask(processingTask);
 	            // defer to controller to notify
 	            //KSNotifier.add(new KSNotification("Course Activation Failed.", false, 5000));
 	            stateChangeCallback.exec(null);
 	        }
 	
 	        @Override
 	        public void onSuccess(StatusInfo result) { 	        	
 	        	KSBlockingProgressIndicator.removeTask(processingTask);
 	        	// defer to controller to notify
 	            //KSNotifier.add(new KSNotification("Course Activated.", false, 5000));
 	        	stateChangeCallback.exec(newState);
 	        }
    	});
    	
    }
    
    // This depends heavily on setCourseState().  Changes here will 
    // affect assumptions made there
    public void updateCourseActionItems(DataModel cluModel) {
    	String cluState = cluModel.get("state");
    	courseId = cluModel.get(CreditCourseConstants.ID);
    	
    	items.clear();      
    	
    	if (cluState.equals(LUUIConstants.LU_STATE_APPROVED)) {
    		items.add(modifyCourseActionItem);
    		items.add(activateCourseActionItem);
    		if (isCurrentVersion) {
    			items.add(retireCourseActionItem);
    		}
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
