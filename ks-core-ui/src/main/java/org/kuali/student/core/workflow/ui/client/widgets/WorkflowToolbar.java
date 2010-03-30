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
package org.kuali.student.core.workflow.ui.client.widgets;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.service.WorkflowRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.StylishDropDown;
import org.kuali.student.common.ui.client.widgets.buttongroups.OkGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.OkEnum;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.validation.dto.ValidationResultContainer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WorkflowToolbar extends Composite {
	DataModel dataModel=null;
	
	boolean loaded=false;
    
	private KSMenuItemData wfApproveItem;
	private KSMenuItemData wfDisApproveItem;
	private KSMenuItemData wfAcknowledgeItem;
	private KSMenuItemData wfStartWorkflowItem;
	private KSMenuItemData wfFYIWorkflowItem;
	private KSMenuItemData wfWithdrawItem;
	
	List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();
	    
    SaveActionEvent approveSaveActionEvent;
    SaveActionEvent startWorkflowSaveActionEvent;
    
    WorkflowRpcServiceAsync workflowRpcServiceAsync;
    
    private String modelName;
    private String idPath;
    
    //FIXME: This should be determined from metadata/dictionary
    private String[] requiredFieldPaths;
    
	private HorizontalBlockFlowPanel rootPanel = new HorizontalBlockFlowPanel();
	private StylishDropDown workflowActionsDropDown = new StylishDropDown("Workflow Actions");
    private CloseHandler<KSLightBox> onSubmitSuccessHandler;
	
    Controller myController;
    
	public WorkflowToolbar(CloseHandler<KSLightBox> onSubmitSuccessHandler) {
		super();
		super.initWidget(rootPanel);
		
		this.onSubmitSuccessHandler = onSubmitSuccessHandler;
		
		//Make the wf buttons
		setupWFButtons();

		rootPanel.add(workflowActionsDropDown);
		
		workflowActionsDropDown.addStyleName("KS-Workflow-DropDown");
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	@Override
	protected void onLoad() {
		super.onLoad();
		myController = Controller.findController(this);
		
		if(null==dataModel){
			//Get the Model from the controller and register a model change handler when the workflow model is updated
			myController.requestModel(modelName, new ModelRequestCallback<DataModel>(){
			
				@Override
				public void onRequestFail(Throwable cause) {
					Window.alert("Model Request Failed. "+cause.getMessage());
				}

				@Override
				public void onModelReady(DataModel model) {
					//After we get the model update immediately
					dataModel = model;
					updateWorkflow(dataModel);
					 
					/*
					model.addModelChangeHandler(new ModelChangeHandler(){
						public void onModelChange(ModelChangeEvent event) {
							updateWorkflow(dataModel);							
						}						
					});
					*/
				}
			});
		}else{
			
			//If the model has been set don't waste time finding it again and don't register 
			//another change listener, just update
			updateWorkflow(dataModel);
		}
	}
	
	private String getProposalIdFromModel(DataModel model){
		String proposalId = "";
		if(model!=null){
			proposalId = model.get(QueryPath.parse(idPath));
		}
		return proposalId;
	}
	
	private void setupWFButtons() {

    	wfStartWorkflowItem = new KSMenuItemData("Submit Proposal", new ClickHandler(){
    		public void onClick(ClickEvent event) {
    			if(dataModel==null || (requiredFieldPaths != null && dataModel.get(QueryPath.parse(requiredFieldPaths[0])) == null)){
    				Window.alert("Administering Organization must be entered and saved before workflow can be started.");
    			}else{
                    //Make sure the entire data model is valid before submit
    				dataModel.validate(new Callback<List<ValidationResultContainer>>() {
                        @Override
                        public void exec(List<ValidationResultContainer> result) {
                        	
                        	boolean isValid = ((LayoutController)myController).isValid(result, false);
                        	if(isValid){
                				workflowRpcServiceAsync.submitDocumentWithData(dataModel.getRoot(), new AsyncCallback<DataSaveResult>(){
            						public void onFailure(
            								Throwable caught) {
            							Window.alert("Error starting Proposal workflow");
            						}
            						public void onSuccess(
            								DataSaveResult result) {
            							//Update the model with the saved data
            							dataModel.setRoot(result.getValue());
            							
            							items.remove(wfStartWorkflowItem);
            							workflowActionsDropDown.setItems(items);
            							
            							//Notify the user that the document was submitted
            							showSuccessDialog();            							
            						}
            					});
                        	}
                        	else{
                        		Window.alert("Unable to submit to workflow.  Please check sections for errors.");
                        	}                            
                        }
                    });
    			}
    		}
    	});

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
						Window.alert("Proposal was approved");
						items.remove(wfApproveItem);
						items.remove(wfDisApproveItem);
						workflowActionsDropDown.setItems(items);
					}
				});
			}        
		});
		
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
							items.remove(wfApproveItem);
							items.remove(wfDisApproveItem);
							workflowActionsDropDown.setItems(items);
						}else{
							Window.alert("Error disapproving Proposal");
						}
					}
					
				});
	        }        
	    });
				
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
							Window.alert("Proposal was acknowledged");
							items.remove(wfAcknowledgeItem);
							workflowActionsDropDown.setItems(items);
						}else{
							Window.alert("Error acknowledging Proposal");
						}
					}
					
				});
	        }        
	    });

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
							Window.alert("Proposal was FYIed");
							items.remove(wfFYIWorkflowItem);
							workflowActionsDropDown.setItems(items);
						}else{
							Window.alert("Error FYIing Proposal");
						}
					}
					
				});
	        }        
	    });

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
							Window.alert("Proposal will be Withdrawn");
							items.clear();
							workflowActionsDropDown.setItems(items);
						}else{
							Window.alert("Error Withdrawing Proposal");
						}
					}
				});
	        }        
    	});
	}

	private void updateWorkflow(DataModel model){
		String proposalId = getProposalIdFromModel(model);
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
				
				workflowActionsDropDown.setItems(items);
			}
			
		});
	}

	private void showSuccessDialog() {
		
		final KSLightBox submitSuccessDialog = new KSLightBox();
		VerticalPanel dialogPanel = new VerticalPanel();
		KSLabel dialogLabel = new KSLabel("Proposal has been routed to workflow");
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
	
	public void setRequiredFieldPaths(String[] requiredFieldPaths){
		this.requiredFieldPaths = requiredFieldPaths;
	}
	
	public void setWorkflowRpcService(WorkflowRpcServiceAsync workflowRpcServiceAsync){
		this.workflowRpcServiceAsync = workflowRpcServiceAsync;
	}

	public void refresh(){
		updateWorkflow(dataModel);
	}
}
