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
package org.kuali.student.lum.lu.ui.course.client.widgets;

import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalHelper;
import org.kuali.student.lum.lu.ui.course.client.configuration.course.CourseConfigurer;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WorkflowButtonsWidget extends Composite {
	DataModel cluProposalWorkflowModel=null;
	
	boolean loaded=false;
    
	private KSButton wfApproveButton;
    private KSButton wfDisApproveButton;
    private KSButton wfAcknowledgeButton;
    private KSButton wfStartWorkflowButton;
    
    SaveActionEvent approveSaveActionEvent;
    SaveActionEvent startWorkflowSaveActionEvent;
    
    CreditCourseProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CreditCourseProposalRpcService.class);
    
    private VerticalPanel rootPanel = new VerticalPanel();
    
    Controller myController;
    
	protected WorkflowButtonsWidget() {
		super();
		super.initWidget(rootPanel);
			
		//Make the wf buttons
		setupWFButtons();

		rootPanel.add(wfApproveButton);
		rootPanel.add(wfDisApproveButton);
		rootPanel.add(wfAcknowledgeButton);
		rootPanel.add(wfStartWorkflowButton);
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	@Override
	protected void onLoad() {
		super.onLoad();
		myController = Controller.findController(this);
		
		if(null==cluProposalWorkflowModel){
			//Get the Model from the controller and register a model change handler when the workflow model is updated
			myController.requestModel(CourseConfigurer.CLU_PROPOSAL_MODEL, new ModelRequestCallback<DataModel>(){
			
				@Override
				public void onRequestFail(Throwable cause) {
					Window.alert("Model Request Failed. "+cause.getMessage());
				}

				@Override
				public void onModelReady(DataModel model) {
					//After we get the model update immediately
					cluProposalWorkflowModel = model;
					updateWorkflow(cluProposalWorkflowModel);
				}
			});
		}else{
			
			//If the model has been set don't waste time finding it again and don't register 
			//another change listener, just update
			updateWorkflow(cluProposalWorkflowModel);
		}
	}
	
	private String getProposalIdFromModel(DataModel model){
		String proposalId = "";
		if(model!=null&&model.getRoot()!=null&&((Data)model.getRoot().get("proposal"))!=null&&((Data)model.getRoot().get("proposal")).get("id")!=null){
			proposalId = ((Data)model.getRoot().get("proposal")).get("id");
		}
		return proposalId;
	}
	
	private void removeButton(KSButton button) {
		button.setVisible(false);
	}
	private void addButton(KSButton button) {
		button.setVisible(true);
	}
	
	private void setupWFButtons() {

    	wfStartWorkflowButton = new KSButton("Submit", new ClickHandler(){
    		public void onClick(ClickEvent event) {
    			if(cluProposalWorkflowModel==null||((Data)cluProposalWorkflowModel.getRoot().get("course")).get("department")==null){
    				Window.alert("Administering Organization must be entered and saved before workflow can be started.");
    			}else{
        			cluProposalRpcServiceAsync.submitDocumentWithData(cluProposalWorkflowModel.getRoot(), new AsyncCallback<DataSaveResult>(){
						public void onFailure(
								Throwable caught) {
							Window.alert("Error starting Proposal workflow");
						}
						public void onSuccess(
								DataSaveResult result) {
							//Update the model with the saved data
							cluProposalWorkflowModel.setRoot(result.getValue());
							Window.alert("Proposal has been routed to workflow");
							removeButton(wfStartWorkflowButton);
						}
					});
    			}
    		}
    	});
    	wfStartWorkflowButton.setVisible(false);

		wfApproveButton = new KSButton("Approve", new ClickHandler(){
			public void onClick(ClickEvent event) {
				cluProposalRpcServiceAsync.approveDocumentWithData(cluProposalWorkflowModel.getRoot(), new AsyncCallback<DataSaveResult>(){
					public void onFailure(
							Throwable caught) {
						Window.alert("Error approving Proposal");
					}
					public void onSuccess(
							DataSaveResult result) {
						//Update the model with the saved data
						cluProposalWorkflowModel.setRoot(result.getValue());
						Window.alert("Proposal was approved");
						removeButton(wfApproveButton);
						removeButton(wfDisApproveButton);
					}
				});
			}        
		});
		wfApproveButton.setVisible(false);

		wfDisApproveButton = new KSButton("Disapprove", new ClickHandler(){
	        public void onClick(ClickEvent event) {
	        	CreditCourseProposalHelper data = CreditCourseProposalHelper.wrap(cluProposalWorkflowModel.getRoot());
	        	
				cluProposalRpcServiceAsync.disapproveDocumentWithId(data.getProposal().getId(), new AsyncCallback<Boolean>(){
					public void onFailure(
							Throwable caught) {
						Window.alert("Error disapproving Proposal");
					}
					public void onSuccess(
							Boolean result) {
						if(result){
							Window.alert("Proposal was disapproved");
							removeButton(wfApproveButton);
							removeButton(wfDisApproveButton);
						}else{
							Window.alert("Error disapproving Proposal");
						}
					}
					
				});
	        }        
	    });
		wfDisApproveButton.setVisible(false);
		
		wfAcknowledgeButton= new KSButton("Acknowledge", new ClickHandler(){
	        public void onClick(ClickEvent event) {
	        	CreditCourseProposalHelper data = CreditCourseProposalHelper.wrap(cluProposalWorkflowModel.getRoot());
	        	
				cluProposalRpcServiceAsync.acknowledgeDocumentWithId(data.getProposal().getId(), new AsyncCallback<Boolean>(){
					public void onFailure(
							Throwable caught) {
						Window.alert("Error acknowledging Proposal");
					}
					public void onSuccess(
							Boolean result) {
						if(result){
							Window.alert("Proposal was acknowledged");
							removeButton(wfAcknowledgeButton);
						}else{
							Window.alert("Error acknowledging Proposal");
						}
					}
					
				});
	        }        
	    });
		wfAcknowledgeButton.setVisible(false);
	}

	private void updateWorkflow(DataModel model){
		String proposalId = getProposalIdFromModel(model);
		cluProposalRpcServiceAsync.getActionsRequested(proposalId, new AsyncCallback<String>(){

			public void onFailure(Throwable caught) {
				// TODO
			}

			public void onSuccess(String result) {
				if(result.contains("A")){
					addButton(wfApproveButton);
					addButton(wfDisApproveButton);
				}
				if(result.contains("K")){
					addButton(wfAcknowledgeButton);
				}
				if(result.contains("S")){
					addButton(wfStartWorkflowButton);
				}
			}
			
		});
	}
}
