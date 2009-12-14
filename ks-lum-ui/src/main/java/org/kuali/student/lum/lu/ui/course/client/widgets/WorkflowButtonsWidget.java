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

import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.ui.client.event.ActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.lum.lu.ui.course.client.configuration.course.CourseConfigurer;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcServiceAsync;

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
    
    CluProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CluProposalRpcService.class);
    
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
		if(model!=null&&((Data)model.getRoot().get("proposal")).get("id")!=null){
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

		startWorkflowSaveActionEvent = new SaveActionEvent("Submitting");
		startWorkflowSaveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
			public void onActionComplete(ActionEvent action) {
    			
    			if(cluProposalWorkflowModel==null||((Data)cluProposalWorkflowModel.getRoot().get("course")).get("department")==null){
    				Window.alert("Administering Organization must be entered and saved before workflow can be started.");
    			}else{
        			cluProposalRpcServiceAsync.submitProposal(cluProposalWorkflowModel.getRoot(), new AsyncCallback<Boolean>(){
						public void onFailure(
								Throwable caught) {
							Window.alert("Error starting Proposal workflow");
						}
						public void onSuccess(
								Boolean result) {
							Window.alert("Proposal has been routed to workflow");
							removeButton(wfStartWorkflowButton);
						}
					});
    			}
			}
		});
		
    	wfStartWorkflowButton = new KSButton("Submit", new ClickHandler(){
    		public void onClick(ClickEvent event) {
    			myController.fireApplicationEvent(startWorkflowSaveActionEvent);
    		}
    	});
    	wfStartWorkflowButton.setVisible(false);

		approveSaveActionEvent = new SaveActionEvent("Approving");
		approveSaveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
			public void onActionComplete(ActionEvent action) {
				cluProposalRpcServiceAsync.approveProposal(cluProposalWorkflowModel.getRoot(), new AsyncCallback<Boolean>(){
					public void onFailure(
							Throwable caught) {
						Window.alert("Error approving Proposal");
					}
					public void onSuccess(
							Boolean result) {
						Window.alert("Proposal was approved");
						removeButton(wfApproveButton);
						removeButton(wfDisApproveButton);
					}
				});
			}
		});
    	
		wfApproveButton = new KSButton("Approve", new ClickHandler(){
			public void onClick(ClickEvent event) {
				myController.fireApplicationEvent(approveSaveActionEvent);
			}        
		});
		wfApproveButton.setVisible(false);

		wfDisApproveButton = new KSButton("Disapprove", new ClickHandler(){
	        public void onClick(ClickEvent event) {
				cluProposalRpcServiceAsync.disapproveProposal(cluProposalWorkflowModel.getRoot(), new AsyncCallback<Boolean>(){
					public void onFailure(
							Throwable caught) {
						Window.alert("Error disapproving Proposal");
					}
					public void onSuccess(
							Boolean result) {
						Window.alert("Proposal was disapproved");
						removeButton(wfApproveButton);
						removeButton(wfDisApproveButton);
					}
					
				});
	        }        
	    });
		wfDisApproveButton.setVisible(false);
		
		wfAcknowledgeButton= new KSButton("Acknowledge", new ClickHandler(){
	        public void onClick(ClickEvent event) {
				cluProposalRpcServiceAsync.acknowledgeProposal(cluProposalWorkflowModel.getRoot(), new AsyncCallback<Boolean>(){
					public void onFailure(
							Throwable caught) {
						Window.alert("Error acknowledging Proposal");
					}
					public void onSuccess(
							Boolean result) {
						Window.alert("Proposal was acknowledged");
						removeButton(wfAcknowledgeButton);
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
