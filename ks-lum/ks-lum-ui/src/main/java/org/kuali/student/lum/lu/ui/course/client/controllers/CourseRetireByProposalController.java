/**
 * 
 */
package org.kuali.student.lum.lu.ui.course.client.controllers;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.dto.DtoConstants;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.event.ActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcServiceAsync;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.util.WindowTitleUtils;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.r1.lum.lu.LUConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseRetireByProposalConfigurer;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseRetireProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.widgets.CourseWorkflowActionList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * New controller for Retire Course screen.
 * 
 *
 */

public class CourseRetireByProposalController extends CourseProposalController {

	/**
	 * Override the intitailzeController method to use CourseAdminConfigurer 
	 */
	@Override
	protected void initializeController() {
		cluProposalRpcServiceAsync = GWT.create(CreditCourseRetireProposalRpcService.class);   		
		super.cfg = GWT.create(CourseRetireByProposalConfigurer.class);	
		proposalPath = cfg.getProposalPath();
   		cfg.setState(DtoConstants.STATE_DRAFT);   		
   		cfg.setNextState(DtoConstants.STATE_RETIRED);
   		super.setDefaultModelId(cfg.getModelId());
   		super.registerModelsAndHandlers();
   		super.addStyleName("ks-course-admin");  
   		currentDocType = LUConstants.PROPOSAL_TYPE_COURSE_RETIRE;	 
   	    // setViewContext(getViewContext());  // do i need to do this here?
    }
	
	// Overriding this method to make things a little cleaner.
	@Override
    protected void populateModel(final ModelRequestCallback<DataModel> callback, Callback<Boolean> workCompleteCallback){
    	if(getViewContext().getIdType() == IdType.DOCUMENT_ID){
            getCluProposalFromWorkflowId(callback, workCompleteCallback);
        } else if (getViewContext().getIdType() == IdType.KS_KEW_OBJECT_ID || getViewContext().getIdType() == IdType.OBJECT_ID){
        	// Admin Retire goes here
            getCluProposalFromProposalId(getViewContext().getId(), callback, workCompleteCallback);
        } else if (getViewContext().getIdType() == IdType.COPY_OF_OBJECT_ID){        	
        	 if (LUConstants.PROPOSAL_TYPE_COURSE_RETIRE.equals(getViewContext().getAttribute(StudentIdentityConstants.DOCUMENT_TYPE_NAME)))
        	    { // Retire By Proposal
        		createRetireCluProposalModel(callback, workCompleteCallback);
        	}
      }
	}
	
	protected void createRetireCluProposalModel(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
        Data data = new Data();
        cluProposalModel.setRoot(data);        
        
     //   this.currentDocType = getViewContext().getAttribute(StudentIdentityConstants.DOCUMENT_TYPE_NAME);
       
        Data proposalData = new Data();
        proposalData.set(new Data.StringKey("type"), LUConstants.PROPOSAL_TYPE_COURSE_RETIRE);
        data.set(new Data.StringKey("proposal"), proposalData);
        if (cfg.getNextState() == null && cfg.getNextState().isEmpty()){
        	proposalData.set(new Data.StringKey("workflowNode"), "PreRoute");
        }
                
        data.set(new Data.StringKey("id"), getViewContext().getId());
        
        cluProposalRpcServiceAsync.saveData(cluProposalModel.getRoot(), new AsyncCallback<DataSaveResult>() {
			public void onSuccess(DataSaveResult result) {
				
				cluProposalModel.setRoot(result.getValue());
				setHeaderTitle();
		        setLastUpdated();
		        //add to recently viewed now that we know the id of proposal
		        ViewContext docContext = new ViewContext();
		        docContext.setId((String) cluProposalModel.get(cfg.getProposalPath()+"/id"));
		        docContext.setIdType(IdType.KS_KEW_OBJECT_ID);
		        //RecentlyViewedHelper.addDocument(getProposalTitle(), 
		        //	HistoryManager.appendContext(AppLocations.Locations.COURSE_PROPOSAL.getLocation(), docContext)
		        //		+ "/SUMMARY");
		        //getCourseComparisonModelAndReqs(callback, workCompleteCallback);
		        
		        // We need to update the current view context so that if the user clicks the back button it doesn't 
		        // create a duplicate course proposal. 
		        getViewContext().setIdType(docContext.getIdType());
		        getViewContext().setId(docContext.getId());
		        callback.onModelReady(cluProposalModel);
		        workCompleteCallback.exec(true);
		        
			}
			
			public void onFailure(Throwable caught) {
                Window.alert("Error loading Proposal: "+caught.getMessage());
                createNewCluProposalModel(callback, workCompleteCallback);
                KSBlockingProgressIndicator.removeTask(loadDataTask);
			}
		});	
    	
    	

    }
	
	
	
	/**
     * Override the setHeaderTitle to display proper header title for admin screens
     */
	@Override
	protected void setHeaderTitle(){
		StringBuffer sb = new StringBuffer();
		sb.append(cluProposalModel.get(cfg.getCourseTitlePath()));
		sb.append(" (Proposed Retirement)");
				
		currentTitle = sb.toString();
		
		this.setContentTitle(currentTitle);
    	this.setName(currentTitle);
    	WindowTitleUtils.setContextTitle(currentTitle);		
    }

	@Override
	protected  BaseDataOrchestrationRpcServiceAsync getCourseProposalRpcService(){
    	return cluProposalRpcServiceAsync;
    }
    	
    public boolean startSectionRequired(){
    	//There is no start section for retire screen
    	return false;
    }

    @Override
	public boolean isAuthorizationRequired() {
		//FIXME: Need to add proper authorization checks for admin modify.
		return false;
	}

	@Override
	protected void progressiveEnableFields() {
		//Does nothing, there are no progressive enabled fields on retire screens.
	}
	
	/**
	 * Override the getStateforSaveAction since the save action for retire course will change state to 
	 * retired.  
	 */
	@Override
    protected String getStateforSaveAction(DataModel model){
    	return cfg.getState();
    }
	
}
