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

import static org.kuali.student.core.workflow.ui.client.WorkflowConstants.ROUTE_HEADER_APPROVED_CD;
import static org.kuali.student.core.workflow.ui.client.WorkflowConstants.ROUTE_HEADER_APPROVED_LABEL_KEY;
import static org.kuali.student.core.workflow.ui.client.WorkflowConstants.ROUTE_HEADER_CANCEL_CD;
import static org.kuali.student.core.workflow.ui.client.WorkflowConstants.ROUTE_HEADER_CANCEL_LABEL_KEY;
import static org.kuali.student.core.workflow.ui.client.WorkflowConstants.ROUTE_HEADER_DISAPPROVED_CD;
import static org.kuali.student.core.workflow.ui.client.WorkflowConstants.ROUTE_HEADER_DISAPPROVED_LABEL_KEY;
import static org.kuali.student.core.workflow.ui.client.WorkflowConstants.ROUTE_HEADER_DISAPPROVE_CANCEL_CD;
import static org.kuali.student.core.workflow.ui.client.WorkflowConstants.ROUTE_HEADER_DISAPPROVE_CANCEL_LABEL_KEY;
import static org.kuali.student.core.workflow.ui.client.WorkflowConstants.ROUTE_HEADER_ENROUTE_CD;
import static org.kuali.student.core.workflow.ui.client.WorkflowConstants.ROUTE_HEADER_ENROUTE_LABEL_KEY;
import static org.kuali.student.core.workflow.ui.client.WorkflowConstants.ROUTE_HEADER_EXCEPTION_CD;
import static org.kuali.student.core.workflow.ui.client.WorkflowConstants.ROUTE_HEADER_EXCEPTION_LABEL_KEY;
import static org.kuali.student.core.workflow.ui.client.WorkflowConstants.ROUTE_HEADER_FINAL_CD;
import static org.kuali.student.core.workflow.ui.client.WorkflowConstants.ROUTE_HEADER_FINAL_LABEL_KEY;
import static org.kuali.student.core.workflow.ui.client.WorkflowConstants.ROUTE_HEADER_INITIATED_CD;
import static org.kuali.student.core.workflow.ui.client.WorkflowConstants.ROUTE_HEADER_INITIATED_LABEL_KEY;
import static org.kuali.student.core.workflow.ui.client.WorkflowConstants.ROUTE_HEADER_PROCESSED_CD;
import static org.kuali.student.core.workflow.ui.client.WorkflowConstants.ROUTE_HEADER_PROCESSED_LABEL_KEY;
import static org.kuali.student.core.workflow.ui.client.WorkflowConstants.ROUTE_HEADER_SAVED_CD;
import static org.kuali.student.core.workflow.ui.client.WorkflowConstants.ROUTE_HEADER_SAVED_LABEL_KEY;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.event.SubmitProposalEvent;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.service.WorkflowRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.StylishDropDown;
import org.kuali.student.common.ui.client.widgets.buttongroups.OkGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.OkEnum;
import org.kuali.student.common.ui.client.widgets.dialog.ConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

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
    
	private KSMenuItemData wfApproveItem;
	private KSMenuItemData wfDisApproveItem;
	private KSMenuItemData wfAcknowledgeItem;
	private KSMenuItemData wfStartWorkflowItem;
	private KSMenuItemData wfFYIWorkflowItem;
	private KSMenuItemData wfWithdrawItem;
	
	private List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();
	    
    SaveActionEvent approveSaveActionEvent;
    SaveActionEvent startWorkflowSaveActionEvent;
    
    WorkflowRpcServiceAsync workflowRpcServiceAsync;
    
    private String modelName;
    private String idPath;
    private String workflowId;
    
    private String[] requiredFieldPaths;
    
	private List<StylishDropDown> workflowWidgets = new ArrayList<StylishDropDown>();
    private CloseHandler<KSLightBox> onSubmitSuccessHandler;
	private ConfirmationDialog dialog = new ConfirmationDialog("Submit Proposal", "Are you sure you want to submit the proposal to workflow?", "Submit");
    
    private KSLabel workflowStatusLabel = new KSLabel("");
    private String workflowStatus;
    
    private LayoutController parentController;
    
	public WorkflowUtilities(WorkflowRpcServiceAsync service, LayoutController parentController, String idPath, CloseHandler<KSLightBox> onSubmitSuccessHandler) {
		
		this.parentController = parentController;
		this.onSubmitSuccessHandler = onSubmitSuccessHandler;
		this.idPath = idPath;
		this.workflowRpcServiceAsync = service;
		setupWFButtons();
		init();
		setupDialog();
	}
	
	protected void init() {
		
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
				workflowRpcServiceAsync.submitDocumentWithData(dataModel.getRoot(), new AsyncCallback<DataSaveResult>(){
					public void onFailure(
							Throwable caught) {
						Window.alert("Error starting Proposal workflow");
						dialog.getConfirmButton().setEnabled(true);
					}
					public void onSuccess(
							DataSaveResult result) {
						//Update the model with the saved data
						dataModel.setRoot(result.getValue());
						updateWorkflow(dataModel);						
						dialog.hide();
						dialog.getConfirmButton().setEnabled(true);
						//Notify the user that the document was submitted
						showSuccessDialog("Proposal has been routed to workflow");
					}
				});
				
			}
		});
	}
	
	public Widget getWorkflowActionsWidget(){
		StylishDropDown workflowActionsDropDown = new StylishDropDown("Workflow Actions");
		workflowActionsDropDown.addStyleName("KS-Workflow-DropDown");
		workflowWidgets.add(workflowActionsDropDown);
		refresh();
		return workflowActionsDropDown;
	}
	
	public KSLabel getWorkflowStatusLabel(){
		return workflowStatusLabel;
	}
	
	private String getProposalIdFromModel(DataModel model){
		String proposalId = "";
		if(model!=null){
			proposalId = model.get(QueryPath.parse(idPath));
		}
		return proposalId;
	}


	private void updateWorkflow(DataModel model){
		String proposalId = getProposalIdFromModel(model);
		
		//Determine which workflow actions are displayed in the drop down
		workflowRpcServiceAsync.getActionsRequested(proposalId, new AsyncCallback<String>(){

			public void onFailure(Throwable caught) {
				// TODO
			}

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
				}
			}
		});
		
		//Get and display workflow status and workflow nodes
		if (proposalId != null){
			workflowRpcServiceAsync.getWorkflowIdFromDataId(proposalId, new AsyncCallback<String>(){
	
				@Override
				public void onFailure(Throwable caught) {
					workflowId = null;
					workflowStatus = "Status: Unknown";
				}
	
				@Override
				public void onSuccess(String result) {
					workflowId = result;
					if (workflowId != null && !workflowId.isEmpty()){			
						workflowRpcServiceAsync.getDocumentStatus(workflowId, new AsyncCallback<String>(){
							@Override
							public void onFailure(Throwable caught) {
								workflowStatus = "Status: Unknown";
							}
		
							@Override
							public void onSuccess(String result) {
								getWorkflowStatus(result);
							}						
						});
					}				
				}			
			});
		} else {
			workflowStatus = "Status: Draft";
		}
		
		workflowStatusLabel.setText(workflowStatus);
		
	}
	
	private KSMenuItemData getFYIWorkflowItem() {
		KSMenuItemData wfFYIWorkflowItem;
		wfFYIWorkflowItem = new KSMenuItemData("FYI Proposal", new ClickHandler(){
	        public void onClick(ClickEvent event) {
	        	String proposalId = getProposalIdFromModel(dataModel);
	        	
				workflowRpcServiceAsync.fyiDocumentWithId(proposalId, new AsyncCallback<Boolean>(){
					public void onFailure(
							Throwable caught) {
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
	        	String proposalId = getProposalIdFromModel(dataModel);
	        	
				workflowRpcServiceAsync.acknowledgeDocumentWithId(proposalId, new AsyncCallback<Boolean>(){
					public void onFailure(
							Throwable caught) {
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
	        	String proposalId = getProposalIdFromModel(dataModel);
	        	
				workflowRpcServiceAsync.disapproveDocumentWithId(proposalId, new AsyncCallback<Boolean>(){
					public void onFailure(
							Throwable caught) {
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
				workflowRpcServiceAsync.approveDocumentWithData(dataModel.getRoot(), new AsyncCallback<DataSaveResult>(){
					public void onFailure(
							Throwable caught) {
						Window.alert("Error approving Proposal");
					}
					public void onSuccess(
							DataSaveResult result) {
						//Update the model with the saved data
						dataModel.setRoot(result.getValue());
						updateWorkflow(dataModel);
						//Notify the user that the document was approved
						showSuccessDialog("Proposal was approved");
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
	        	String proposalId = getProposalIdFromModel(dataModel);
	        	
				workflowRpcServiceAsync.withdrawDocumentWithId(proposalId, new AsyncCallback<Boolean>(){
					public void onFailure(Throwable caught) {
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
    			if(dataModel==null || (requiredFieldPaths != null && dataModel.get(QueryPath.parse(requiredFieldPaths[0])) == null)){
    				Window.alert("Administering Organization must be entered and saved before workflow can be started.");
    			}else{
                    //Make sure the entire data model is valid before submit
    				dataModel.validate(new Callback<List<ValidationResultInfo>>() {
                        @Override
                        public void exec(List<ValidationResultInfo> result) {
                        	
                        	boolean isValid = ((LayoutController)parentController).isValid(result, false);
                        	if(isValid){
                				dialog.show();
                        	}
                        	else{
                        		Window.alert("Unable to submit to workflow.  Please check sections for errors.");
                        	}                            
                        }
                    });
    			}
    		}
    	});
		return wfStartWorkflowItem;
	}
	

	private String getWorkflowStatus(String statusCd){
		String statusTranslation = "";
		if (ROUTE_HEADER_SAVED_CD.equals(statusCd)){
			statusTranslation = getLabel(ROUTE_HEADER_SAVED_LABEL_KEY);
		} else  if (ROUTE_HEADER_INITIATED_CD.equals(statusCd)){
			statusTranslation = getLabel(ROUTE_HEADER_INITIATED_LABEL_KEY);
		} else if (ROUTE_HEADER_ENROUTE_CD.equals(statusCd)){
			statusTranslation = getLabel(ROUTE_HEADER_ENROUTE_LABEL_KEY);
		} else if (ROUTE_HEADER_APPROVED_CD.equals(statusCd)){
			statusTranslation = getLabel(ROUTE_HEADER_APPROVED_LABEL_KEY);
		} else if (ROUTE_HEADER_CANCEL_CD.equals(statusCd)){
			statusTranslation = getLabel(ROUTE_HEADER_CANCEL_LABEL_KEY);
		} else if (ROUTE_HEADER_EXCEPTION_CD.equals(statusCd)){
			statusTranslation = getLabel(ROUTE_HEADER_EXCEPTION_LABEL_KEY);
		} else if (ROUTE_HEADER_DISAPPROVED_CD.equals(statusCd)){
			statusTranslation = getLabel(ROUTE_HEADER_DISAPPROVED_LABEL_KEY);
		} else if (ROUTE_HEADER_FINAL_CD.equals(statusCd)){
			statusTranslation = getLabel(ROUTE_HEADER_FINAL_LABEL_KEY);
		} else if (ROUTE_HEADER_DISAPPROVE_CANCEL_CD.equals(statusCd)){
			statusTranslation = getLabel(ROUTE_HEADER_DISAPPROVE_CANCEL_LABEL_KEY);
		} else if (ROUTE_HEADER_PROCESSED_CD.equals(statusCd)){
			statusTranslation = getLabel(ROUTE_HEADER_PROCESSED_LABEL_KEY);
		} else {
			statusTranslation = statusCd;
		}
		
		return "Status: " + statusTranslation;		
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
	 * Use to set the data model path to retrieve the id to use for this workflow. 
	 * @param idPath
	 */
	public void setIdPath(String idPath) {
		this.idPath = idPath;
	}
	
	/**
	 * Use to indicate which fields are required for workflow before workflow actions are allowed.
	 * 
	 * NOTE: We want this to be configurable via metadata rather than hardcoding a call to this in configurers or controllers.
	 * 
	 * @param requiredFieldPaths An array of paths to fields to check in the data model
	 */
	public void setRequiredFieldPaths(String[] requiredFieldPaths){
		this.requiredFieldPaths = requiredFieldPaths;
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
}
