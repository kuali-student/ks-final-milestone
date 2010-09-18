/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.workflow.ui.client.widgets;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.sections.InfoMessage;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.event.SubmitProposalEvent;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.StylishDropDown;
import org.kuali.student.common.ui.client.widgets.buttongroups.OkGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.OkEnum;
import org.kuali.student.common.ui.client.widgets.dialog.ConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.workflow.ui.client.WorkflowConstants;
import org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService;
import org.kuali.student.core.workflow.ui.client.service.WorkflowRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WorkflowUtilities{
	DataModel dataModel=null;
	
	boolean loaded=false;
    
	private boolean workflowWidgetsEnabled = true;
	
	private KSMenuItemData wfApproveItem;
	private KSMenuItemData wfDisApproveItem;
	private KSMenuItemData wfAcknowledgeItem;
	private KSMenuItemData wfStartWorkflowItem;
	private KSMenuItemData wfFYIWorkflowItem;
	private KSMenuItemData wfWithdrawItem;
	
	private List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();
	    
    SaveActionEvent approveSaveActionEvent;
    SaveActionEvent startWorkflowSaveActionEvent;
    
    WorkflowRpcServiceAsync workflowRpcServiceAsync = GWT.create(WorkflowRpcService.class);
    
    private String modelName;
    private String proposalPath;
    private String proposalId = "";
    private String workflowId;
        
	private List<StylishDropDown> workflowWidgets = new ArrayList<StylishDropDown>();
    private CloseHandler<KSLightBox> onSubmitSuccessHandler;
	private ConfirmationDialog dialog = new ConfirmationDialog("Submit Proposal", "Are you sure you want to submit the proposal to workflow?", "Submit");
    
    private KSLabel workflowStatusLabel = new KSLabel("");
    
    private LayoutController parentController;
    
	public WorkflowUtilities(LayoutController parentController, String proposalPath, CloseHandler<KSLightBox> onSubmitSuccessHandler) {
		
		this.parentController = parentController;
		this.onSubmitSuccessHandler = onSubmitSuccessHandler;
		this.proposalPath = proposalPath;
		setupWFButtons();
		setupDialog();
	}
	
	public WorkflowUtilities(LayoutController parentController, String proposalPath) {
		this.parentController = parentController;
		this.proposalPath = proposalPath;
		setupWFButtons();
		setupDialog();
	}
	
	public void requestAndSetupModel() {
		
		if(null==dataModel){
			//Get the Model from the controller and register a model change handler when the workflow model is updated
			parentController.requestModel(modelName, new ModelRequestCallback<DataModel>() {
			
				@Override
				public void onRequestFail(Throwable cause) {
					Window.alert("Model Request Failed. "+cause.getMessage());
				}

				@Override
				public void onModelReady(DataModel model) {
					//After we get the model update immediately
					dataModel = model;
					updateWorkflow(dataModel);
				}
			});
		}else{
			//If the model has been set don't waste time finding it again and don't register 
			//another change listener, just update
			updateWorkflow(dataModel);
		}
	}
	
	private void setupWFButtons() {
		wfApproveItem = getApproveItem();
		wfDisApproveItem = getDisApproveItem();
		wfAcknowledgeItem = getAcknowledgeItem();
		wfStartWorkflowItem = getStartItem();
		wfFYIWorkflowItem = getFYIWorkflowItem();
		wfWithdrawItem = getWithdrawItem();
	}
	
	private void setupDialog(){

		dialog.getConfirmButton().addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				dialog.getConfirmButton().setEnabled(false);
				parentController.fireApplicationEvent(new SubmitProposalEvent());
				workflowRpcServiceAsync.submitDocumentWithId(workflowId, new KSAsyncCallback<Boolean>(){
					public void handleFailure(Throwable caught) {
						Window.alert("Error starting Proposal workflow");
						dialog.getConfirmButton().setEnabled(true);
					}
					public void onSuccess(Boolean result) {
						if (result){
							updateWorkflow(dataModel);						
							dialog.hide();
							dialog.getConfirmButton().setEnabled(true);
							//Notify the user that the document was submitted
							showSuccessDialog("Proposal has been routed to workflow");
						} else {
							Window.alert("Error starting Proposal workflow");
						}
					}
				});
				
			}
		});
	}
	
	public Widget getWorkflowActionsWidget(){
		//InfoMessage infoContainer = new InfoMessage();
		StylishDropDown workflowActionsDropDown = new StylishDropDown("Workflow Actions");
		workflowActionsDropDown.makeAButtonWhenOneItem(true);
		workflowActionsDropDown.addStyleName("KS-Workflow-DropDown");
		workflowWidgets.add(workflowActionsDropDown);
		workflowActionsDropDown.setVisible(false);
		refresh();
/*		infoContainer.add(workflowActionsDropDown);
		infoContainer.showWarnStyling(false);
		infoContainer.setVisible(true);*/
		//workflowActionsDropDown
		return workflowActionsDropDown;
	}
	
	public void enableWorkflowActionsWidgets(boolean enable){
		workflowWidgetsEnabled = enable;
		for(StylishDropDown widget: workflowWidgets){	
			widget.setEnabled(enable);
		}
	}
	
	public void doValidationCheck(Callback<List<ValidationResultInfo>> callback){
		dataModel.validateNextState(callback);
	}
	
	public KSLabel getWorkflowStatusLabel(){
		return workflowStatusLabel;
	}
	
	private void updateWorkflowIdFromModel(final DataModel model){
		if(model!=null){
			String modelProposalId = model.get(QueryPath.parse(proposalPath + "/id"));
			
			//If proposalId in model has been set or changed, get new workflowId and update workfow widget
			if (modelProposalId != null && !modelProposalId.isEmpty() && !modelProposalId.equals(proposalId)){
				proposalId = modelProposalId;
				workflowId = model.get(QueryPath.parse(proposalPath + "/workflowId"));
				updateWorkflow(model);
			}
		}
	}

	private void updateWorkflow(DataModel model){
		updateWorkflowIdFromModel(model);
		
		if (workflowId != null && !workflowId.isEmpty()){
			//Determine which workflow actions are displayed in the drop down
			workflowRpcServiceAsync.getActionsRequested(workflowId, new KSAsyncCallback<String>(){
		
				public void onSuccess(String result) {
					items.clear();
					if(result.contains("S")){
						items.add(wfStartWorkflowItem);
					}
					if(result.contains("W")){
						items.add(wfWithdrawItem);
					}
					if(result.contains("A")){
	
						items.add(wfApproveItem);
						items.add(wfDisApproveItem);
	
					}
					if(result.contains("K")){
						items.add(wfAcknowledgeItem);
					}
					
					if(result.contains("F")){
						items.add(wfFYIWorkflowItem);
					}
					for(StylishDropDown widget: workflowWidgets){
						
						widget.setItems(items);
						widget.setEnabled(workflowWidgetsEnabled);
						if(items.isEmpty()){
							widget.setVisible(false);
						}
						else{
							widget.setVisible(true);
						}
					}
				}
			});
		
			workflowRpcServiceAsync.getDocumentStatus(workflowId, new KSAsyncCallback<String>(){
				@Override
				public void handleFailure(Throwable caught) {
					workflowStatusLabel.setText("Status: Unknown");
				}

				@Override
				public void onSuccess(String result) {
					setWorkflowStatus(result);
				}						
			});
		} else {
			workflowStatusLabel.setText("Status: Draft");
		}			
	}
	
	private KSMenuItemData getFYIWorkflowItem() {
		KSMenuItemData wfFYIWorkflowItem;
		wfFYIWorkflowItem = new KSMenuItemData("FYI Proposal", new ClickHandler(){
	        public void onClick(ClickEvent event) {	        	
				workflowRpcServiceAsync.fyiDocumentWithId(workflowId, new KSAsyncCallback<Boolean>(){
					public void handleFailure(Throwable caught) {
						Window.alert("Error FYIing Proposal");
					}
					public void onSuccess(
							Boolean result) {
						if(result){
							updateWorkflow(dataModel);
							//Notify the user that the document was FYIed
							showSuccessDialog("Proposal was FYIed");
						}else{
							Window.alert("Error FYIing Proposal");
						}
					}
					
				});
	        }        
	    });
		return wfFYIWorkflowItem;
	}

	private KSMenuItemData getAcknowledgeItem() {
		KSMenuItemData wfAcknowledgeItem;
		wfAcknowledgeItem = new KSMenuItemData("Acknowledge Proposal", new ClickHandler(){
	        public void onClick(ClickEvent event) {
				workflowRpcServiceAsync.acknowledgeDocumentWithId(workflowId, new KSAsyncCallback<Boolean>(){
					public void handleFailure(Throwable caught) {
						Window.alert("Error acknowledging Proposal");
					}
					public void onSuccess(
							Boolean result) {
						if(result){
							updateWorkflow(dataModel);
							//Notify the user that the document was acknowledged
							showSuccessDialog("Proposal was acknowledged");
						}else{
							Window.alert("Error acknowledging Proposal");
						}
					}
					
				});
	        }        
	    });
		return wfAcknowledgeItem;
	}

	private KSMenuItemData getDisApproveItem() {
		KSMenuItemData wfDisApproveItem;
		wfDisApproveItem = new KSMenuItemData("Disapprove Proposal", new ClickHandler(){
	        public void onClick(ClickEvent event) {        	
				workflowRpcServiceAsync.disapproveDocumentWithId(workflowId, new KSAsyncCallback<Boolean>(){
					public void handleFailure(Throwable caught) {
						Window.alert("Error disapproving Proposal");
					}
					public void onSuccess(
							Boolean result) {
						if(result){
							Window.alert("Proposal was disapproved");
							updateWorkflow(dataModel);
						}else{
							Window.alert("Error disapproving Proposal");
						}
					}
					
				});
	        }        
	    });
		return wfDisApproveItem;
	}

	private KSMenuItemData getApproveItem() {
		KSMenuItemData wfApproveItem;
		wfApproveItem= new KSMenuItemData("Approve Proposal", new ClickHandler(){
			public void onClick(ClickEvent event) {
				workflowRpcServiceAsync.approveDocumentWithId(workflowId, new KSAsyncCallback<Boolean>(){
					public void handleFailure(Throwable caught) {
						Window.alert("Error approving Proposal");
					}
					public void onSuccess(Boolean result) {
						if (result){
							updateWorkflow(dataModel);
							//Notify the user that the document was approved
							showSuccessDialog("Proposal was approved");
						} else {
							Window.alert("Error approving Proposal");
						}
					}
				});
			}        
		});
		return wfApproveItem;
	}

	private KSMenuItemData getWithdrawItem() {
		KSMenuItemData wfWithdrawItem;
    	wfWithdrawItem = new KSMenuItemData("Withdraw Proposal", new ClickHandler(){
	        public void onClick(ClickEvent event) {
	        	
				workflowRpcServiceAsync.withdrawDocumentWithId(workflowId, new KSAsyncCallback<Boolean>(){
					public void handleFailure(Throwable caught) {
						GWT.log("Error Withdrawing Proposal", caught);
						Window.alert("Error Withdrawing Proposal");
					}
					public void onSuccess(Boolean result) {
						if(result){
							updateWorkflow(dataModel);
							for(StylishDropDown widget: workflowWidgets){
								List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();
								widget.setItems(items);
							}
							//Notify the user that the document was Withdrawn
							showSuccessDialog("Proposal was Withdrawn");
						}else{
							Window.alert("Error Withdrawing Proposal");
						}
					}
				});
	        }        
    	});
		return wfWithdrawItem;
	}

	private KSMenuItemData getStartItem() {
		KSMenuItemData wfStartWorkflowItem;
    	wfStartWorkflowItem = new KSMenuItemData("Submit Proposal", new ClickHandler(){
    		public void onClick(ClickEvent event) {
                //Make sure the entire data model is valid before submit
				dataModel.validateNextState(new Callback<List<ValidationResultInfo>>() {
                    @Override
                    public void exec(List<ValidationResultInfo> result) {
                    	
                    	boolean isValid = ((LayoutController)parentController).isValid(result, false);
                    	if(isValid){
            				dialog.show();
                    	}
                    	else{
                    		Window.alert("Unable to submit to workflow.  Please check sections for missing fields.");
                    	}                            
                    }
                });
    		}

    	});
		return wfStartWorkflowItem;
	}
		
	private void setWorkflowStatus(String statusCd){
		String statusTranslation = "";
		if (WorkflowConstants.ROUTE_HEADER_SAVED_CD.equals(statusCd)){
			statusTranslation = getLabel(WorkflowConstants.ROUTE_HEADER_SAVED_LABEL_KEY);
		} else  if (WorkflowConstants.ROUTE_HEADER_INITIATED_CD.equals(statusCd)){
			statusTranslation = getLabel(WorkflowConstants.ROUTE_HEADER_INITIATED_LABEL_KEY);
		} else if (WorkflowConstants.ROUTE_HEADER_ENROUTE_CD.equals(statusCd)){
			statusTranslation = getLabel(WorkflowConstants.ROUTE_HEADER_ENROUTE_LABEL_KEY);
		} else if (WorkflowConstants.ROUTE_HEADER_APPROVED_CD.equals(statusCd)){
			statusTranslation = getLabel(WorkflowConstants.ROUTE_HEADER_APPROVED_LABEL_KEY);
		} else if (WorkflowConstants.ROUTE_HEADER_CANCEL_CD.equals(statusCd)){
			statusTranslation = getLabel(WorkflowConstants.ROUTE_HEADER_CANCEL_LABEL_KEY);
		} else if (WorkflowConstants.ROUTE_HEADER_EXCEPTION_CD.equals(statusCd)){
			statusTranslation = getLabel(WorkflowConstants.ROUTE_HEADER_EXCEPTION_LABEL_KEY);
		} else if (WorkflowConstants.ROUTE_HEADER_DISAPPROVED_CD.equals(statusCd)){
			statusTranslation = getLabel(WorkflowConstants.ROUTE_HEADER_DISAPPROVED_LABEL_KEY);
		} else if (WorkflowConstants.ROUTE_HEADER_FINAL_CD.equals(statusCd)){
			statusTranslation = getLabel(WorkflowConstants.ROUTE_HEADER_FINAL_LABEL_KEY);
		} else if (WorkflowConstants.ROUTE_HEADER_DISAPPROVE_CANCEL_CD.equals(statusCd)){
			statusTranslation = getLabel(WorkflowConstants.ROUTE_HEADER_DISAPPROVE_CANCEL_LABEL_KEY);
		} else if (WorkflowConstants.ROUTE_HEADER_PROCESSED_CD.equals(statusCd)){
			statusTranslation = getLabel(WorkflowConstants.ROUTE_HEADER_PROCESSED_LABEL_KEY);
		} else {
			statusTranslation = statusCd;
		}
		
		workflowStatusLabel.setText("Status: " + statusTranslation);	
	}
	
	private void showSuccessDialog(String successMessage) {
	
		final KSLightBox submitSuccessDialog = new KSLightBox();
		VerticalPanel dialogPanel = new VerticalPanel();
		KSLabel dialogLabel = new KSLabel(successMessage);
		dialogPanel.add(dialogLabel);

		//Add an OK button that closes (hides) the dialog which will in turn call the onSubmitSuccessHandler
		OkGroup okButton = new OkGroup(new Callback<OkEnum>(){
			@Override
			public void exec(OkEnum result) {
				submitSuccessDialog.hide();
			}
		});
		dialogPanel.add(okButton);
		
		submitSuccessDialog.setWidget(dialogPanel);
		//Add in the onSubmitSuccessHandler so when the dialog is closed, the handler code is executed. This allows
		// a hook into performing UI actions after a successful submit 
		submitSuccessDialog.addCloseHandler(onSubmitSuccessHandler);
		submitSuccessDialog.show();
	}
	
	/**
	 * Use to set the modelName to use when this widget requests the data model.
	 * 
	 * @param modelName
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	/**
	 * Use to set the data model path to retrieve the propsal data to use for this workflow. 
	 * @param idPath
	 */
	public void setProposalPath(String proposalPath) {
		this.proposalPath = proposalPath;
	}
		
	public void setWorkflowRpcService(WorkflowRpcServiceAsync workflowRpcServiceAsync){
		this.workflowRpcServiceAsync = workflowRpcServiceAsync;
	}

	public void refresh(){
		updateWorkflow(dataModel);
	}
	
    private String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel("common", null, null, labelKey);
    }
    
    public void getDataIdFromWorkflowId(String workflowId, AsyncCallback<String> callback){
    	workflowRpcServiceAsync.getDataIdFromWorkflowId(workflowId, callback);
    }
}
