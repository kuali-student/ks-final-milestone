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
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.service.CommentRpcService;
import org.kuali.student.common.ui.client.service.CommentRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.StylishDropDown;
import org.kuali.student.common.ui.client.widgets.buttongroups.AcknowledgeCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmApprovalCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.RejectCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.AcknowledgeCancelEnum;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmApprovalCancelEnum;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.RejectCancelEnum;
import org.kuali.student.common.ui.client.widgets.dialog.ConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrPanel;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.comment.dto.CommentInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.DtoConstants.DtoState;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.workflow.ui.client.WorkflowConstants;
import org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService;
import org.kuali.student.core.workflow.ui.client.service.WorkflowRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class WorkflowUtilities{

	//TODO: This should come from the ReferenceModel like it does in CommentTool
    public static final String PROPOSAL_REF_TYPE_KEY = "referenceType.clu.proposal";
    
	public static enum DecisionRationaleDetail {
        APPROVE("kuali.comment.type.workflowDecisionRationale.approve", "Approved"),
        REJECT("kuali.comment.type.workflowDecisionRationale.reject", "Rejected"),
        RETURN_TO_PREVIOUS("kuali.comment.type.workflowDecisionRationale.return", "Sent for Revisions"),
        WITHDRAW("kuali.comment.type.workflowDecisionRationale.withdraw", "Withdrawn"),
        ACKNOWLEDGE("kuali.comment.type.workflowDecisionRationale.acknowledge", "Acknowledged"),
        FYI("kuali.comment.type.workflowDecisionRationale.fyi", "FYI"),
        CANCEL_WORKFLOW("kuali.comment.type.workflowDecisionRationale.cancelWorkflow", "Cancelled"),
        BLANKET_APPROVE("kuali.comment.type.workflowDecisionRationale.blanketApprove", "Blanket Approved")
        ;

        private String type = "";
        private String label = "";

        private DecisionRationaleDetail(String type, String label) {
            this.type = type;
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public String getLabel() {
            return label;
        }

        public static DecisionRationaleDetail getByType(String type) {
            for (DecisionRationaleDetail detail : DecisionRationaleDetail.values()) {
                if (detail.getType().equals(type)) {
                    return detail;
                }
            }
            return null;
        }

    }

	DataModel dataModel=null;
	
	boolean loaded=false;
    
	private boolean workflowWidgetsEnabled = true;
	
	private KSMenuItemData wfApproveItem;
	private KSMenuItemData wfDisApproveItem;
	private KSMenuItemData wfAcknowledgeItem;
	private KSMenuItemData wfStartWorkflowItem;
    private KSMenuItemData wfCancelWorkflowItem;
	private KSMenuItemData wfFYIWorkflowItem;
	private KSMenuItemData wfWithdrawItem;
    private KSMenuItemData wfReturnToPreviousItem;
    private KSMenuItemData wfBlanketApproveItem;
	
	private List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();
	    
    SaveActionEvent approveSaveActionEvent;
    SaveActionEvent startWorkflowSaveActionEvent;
    
    WorkflowRpcServiceAsync workflowRpcServiceAsync = GWT.create(WorkflowRpcService.class);
    private CommentRpcServiceAsync commentServiceAsync = GWT.create(CommentRpcService.class);
    
    private String modelName;
    private String proposalPath;
    private String proposalId = "";
    private String workflowId;
    private String proposalName="";
    private String workflowActions="";
        
	private List<StylishDropDown> workflowWidgets = new ArrayList<StylishDropDown>();
	private Callback<Boolean> submitCallback;
	private ConfirmationDialog dialog = new ConfirmationDialog("Submit Proposal", "Are you sure you want to submit the proposal to workflow?", "Submit");
	private AbbrPanel required; 
	private KSLightBox submitSuccessDialog;
	private VerticalPanel dialogPanel;
    
    private KSLabel workflowStatusLabel = new KSLabel("");
    
    private LayoutController parentController;
	
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
		wfCancelWorkflowItem = getCancelWorkflowItem();
		wfFYIWorkflowItem = getFYIWorkflowItem();
		wfWithdrawItem = getWithdrawItem();
		wfReturnToPreviousItem = getReturnToPreviousItem();
		wfBlanketApproveItem = getBlanketApproveItem();
	}
	
	private void setupSubmitSuccessDialog(){
		if(submitSuccessDialog==null){
			submitSuccessDialog= new KSLightBox();
			submitSuccessDialog.setSize(580, 400);
			dialogPanel = new VerticalPanel();
			submitSuccessDialog.setWidget(dialogPanel);
			
		}

	}
	
	private void setupDialog(){

		dialog.getConfirmButton().addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				dialog.getConfirmButton().setEnabled(false);
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
							if(submitCallback != null){
								submitCallback.exec(true);
							}
							//Notify the user that the document was submitted
							KSNotifier.add(new KSNotification("Proposal has been routed to workflow", false));
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
		updateWorkflowActionsWidget();
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
				proposalName = model.get(QueryPath.parse(proposalPath + "/name"));
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
					workflowActions = result;
					updateWorkflowActionsWidget();
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
	
	private void updateWorkflowActionsWidget(){
		items.clear();

		//Display all workflow actions if workflowWidgetsEnabled, otherwise just display
		//the cancel option.
		if (workflowWidgetsEnabled){
			if(workflowActions.contains("S")){
				items.add(wfStartWorkflowItem);
			}
            if(workflowActions.contains("C")){
                items.add(wfCancelWorkflowItem);
            }
			if(workflowActions.contains("A")){
				items.add(wfApproveItem);
				items.add(wfDisApproveItem);
			}
			if(workflowActions.contains("K")){
				items.add(wfAcknowledgeItem);
			}
			if(workflowActions.contains("F")){
				items.add(wfFYIWorkflowItem);
			}
            if(workflowActions.contains("R")){
                items.add(wfReturnToPreviousItem);
            }
            if(workflowActions.contains("B")){
                items.add(wfBlanketApproveItem);
            }
            if(workflowActions.contains("W")){
                items.add(wfWithdrawItem);
            }
		} else {
            if(workflowActions.contains("C")){
                items.add(wfCancelWorkflowItem);
            }
            if(workflowActions.contains("W")){
                items.add(wfWithdrawItem);
            }
		}
		for(StylishDropDown widget: workflowWidgets){
			
			widget.setItems(items);
			widget.setEnabled(true);
			if(items.isEmpty()){
				widget.setVisible(false);
			}
			else{
				widget.setVisible(true);
			}
		}		
	}
	
	private KSMenuItemData getFYIWorkflowItem() {
		KSMenuItemData wfFYIWorkflowItem;
		final KSRichEditor rationaleEditor = new KSRichEditor();
		wfFYIWorkflowItem = new KSMenuItemData("FYI Proposal", new ClickHandler(){
	        public void onClick(ClickEvent event) {	   
	        	addRationale(rationaleEditor,DecisionRationaleDetail.FYI.getType());
				workflowRpcServiceAsync.fyiDocumentWithId(workflowId, new KSAsyncCallback<Boolean>(){
					public void handleFailure(Throwable caught) {
						Window.alert("Error FYIing Proposal");
					}
					public void onSuccess(
							Boolean result) {
						if(result){
							updateWorkflow(dataModel);
							if(submitCallback != null){
								submitCallback.exec(true);
							}
							//Notify the user that the document was FYIed
							KSNotifier.add(new KSNotification("Proposal was FYIed", false));
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
	        	setupSubmitSuccessDialog();
				final KSRichEditor rationaleEditor = new KSRichEditor();
				AcknowledgeCancelGroup approvalButton = new AcknowledgeCancelGroup(new Callback<AcknowledgeCancelEnum>(){

					@Override
					public void exec(AcknowledgeCancelEnum result) {
						if(!result.name().equals("CANCEL")){
							addRationale(rationaleEditor,DecisionRationaleDetail.ACKNOWLEDGE.getType());
							workflowRpcServiceAsync.acknowledgeDocumentWithId(workflowId, new KSAsyncCallback<Boolean>(){
								public void handleFailure(Throwable caught) {
									submitSuccessDialog.hide();
									Window.alert("Error acknowledging Proposal");
								}
								public void onSuccess(Boolean result) {
									submitSuccessDialog.hide();
									if(result){
										updateWorkflow(dataModel);
										if(submitCallback != null){
											submitCallback.exec(true);
										}
										//Notify the user that the document was acknowledged
										KSNotifier.add(new KSNotification("Proposal was acknowledged", false));
									}else{
										Window.alert("Error acknowledging Proposal");
									}
								}
							});
						}
						else{
							submitSuccessDialog.hide();
						}
					}
				});
				
				SectionTitle headerTitle = SectionTitle.generateH3Title("Acknowledge Proposal");
				SectionTitle dialogLabel = SectionTitle.generateH4Title("You are acknowledging the " + proposalName +" proposal");
				SectionTitle fieldLabel = SectionTitle.generateH4Title("Decision Rationale");
				rationaleEditor.addStyleName("KS-Comment-Create-Editor");
				dialogPanel.clear();
				dialogPanel.add(headerTitle);	
				dialogPanel.add(dialogLabel);
				dialogPanel.add(fieldLabel);
				dialogPanel.add(rationaleEditor);
				dialogPanel.add(approvalButton);
				submitSuccessDialog.show();
	        }        
	    });
		return wfAcknowledgeItem;
	}

	private KSMenuItemData getDisApproveItem() {
		KSMenuItemData wfDisApproveItem;
		wfDisApproveItem = new KSMenuItemData("Reject Proposal", new ClickHandler(){
	        public void onClick(ClickEvent event) {   
	        	setupSubmitSuccessDialog();
				final KSRichEditor rationaleEditor = new KSRichEditor();
				RejectCancelGroup disapprovalButton = new RejectCancelGroup(new Callback<RejectCancelEnum>(){

					@Override
					public void exec(RejectCancelEnum result) {
						if(!result.name().equals("CANCEL")){
							if(rationaleEditor.getText().trim().equals("")){
								required.setText("Please enter the decision rationale");
							}
							else{
								addRationale(rationaleEditor,DecisionRationaleDetail.REJECT.getType());
								workflowRpcServiceAsync.disapproveDocumentWithId(workflowId, new KSAsyncCallback<Boolean>(){
									public void handleFailure(Throwable caught) {
										submitSuccessDialog.hide();
										Window.alert("Error rejecting Proposal");
									}
									public void onSuccess(Boolean result) {
										submitSuccessDialog.hide();
										if(submitCallback != null){
											submitCallback.exec(result);
										}
										if(result){
											KSNotifier.add(new KSNotification("Proposal was rejected", false));
											updateWorkflow(dataModel);
										}else{
											Window.alert("Error rejecting Proposal");
										}
									}
									
								});
							}

					}
					else{
						submitSuccessDialog.hide();
					}
					}
				});
				SectionTitle headerTitle = SectionTitle.generateH3Title("Reject Proposal");
				SectionTitle dialogLabel = SectionTitle.generateH4Title("You are rejecting the " + proposalName +" proposal");
				SectionTitle fieldLabel = SectionTitle.generateH4Title("Decision Rationale");
				required = new AbbrPanel("Required", "ks-form-module-elements-required", " * ");
				required.setVisible(true);
//				final KSRichEditor rationaleEditor = new KSRichEditor();
//				rationaleEditor.addStyleName("ks-textarea-width");
//				rationaleEditor.addStyleName("ks-textarea-large-height");
				rationaleEditor.addStyleName("KS-Comment-Create-Editor");
				dialogPanel.clear();
				dialogPanel.add(headerTitle);	
				dialogPanel.add(dialogLabel);
				dialogPanel.add(fieldLabel);
				dialogPanel.add(required);
				dialogPanel.add(rationaleEditor);
				dialogPanel.add(disapprovalButton);
				submitSuccessDialog.setWidget(dialogPanel);
				submitSuccessDialog.show();
	        }        
	    });
		return wfDisApproveItem;
	}

	private KSMenuItemData getApproveItem() {
		KSMenuItemData wfApproveItem;

		wfApproveItem= new KSMenuItemData("Approve Proposal", new ClickHandler(){
			public void onClick(ClickEvent event) {
				setupSubmitSuccessDialog();
				final KSRichEditor rationaleEditor = new KSRichEditor();
				ConfirmApprovalCancelGroup approvalButton = new ConfirmApprovalCancelGroup(new Callback<ConfirmApprovalCancelEnum>(){

					@Override
					public void exec(ConfirmApprovalCancelEnum result) {
						if(!result.name().equals("CANCEL")){
							if(rationaleEditor.getText().trim().equals("")){
								required.setText("Please enter the decision rationale");
							}
							else{
								addRationale(rationaleEditor,DecisionRationaleDetail.APPROVE.getType());
								
								workflowRpcServiceAsync.approveDocumentWithId(workflowId, new KSAsyncCallback<Boolean>(){
								public void handleFailure(Throwable caught) {
									submitSuccessDialog.hide();
									Window.alert("Error approving Proposal");
								}
								public void onSuccess(Boolean result) {
									submitSuccessDialog.hide();
									if (result){
										updateWorkflow(dataModel);
										if(submitCallback != null){
											submitCallback.exec(result);
										}
										//Notify the user that the document was approved
										KSNotifier.add(new KSNotification("Proposal was approved", false));
									} else {
										Window.alert("Error approving Proposal");
									}
								}
							});
							}

					}
					else{
						submitSuccessDialog.hide();
					}
					}
				});
				
				SectionTitle headerTitle = SectionTitle.generateH3Title("Approve Proposal");
				SectionTitle dialogLabel = SectionTitle.generateH4Title("You are approving the " + proposalName +" proposal");
				SectionTitle fieldLabel = SectionTitle.generateH4Title("Decision Rationale");
				required = new AbbrPanel("Required", "ks-form-module-elements-required", " * ");
				required.setVisible(true);
				rationaleEditor.addStyleName("KS-Comment-Create-Editor");
				dialogPanel.clear();
				dialogPanel.add(headerTitle);	
				dialogPanel.add(dialogLabel);
				dialogPanel.add(fieldLabel);
				dialogPanel.add(required);
				dialogPanel.add(rationaleEditor);
				dialogPanel.add(approvalButton);
				dialogPanel.setSize("580px", "400px");
//				submitSuccessDialog.setWidget(dialogPanel);
				submitSuccessDialog.show();
			}        
		});
		return wfApproveItem;
	}

    private KSMenuItemData getWithdrawItem() {
        KSMenuItemData wfWithdrawItem;

        wfWithdrawItem = new KSMenuItemData("Withdraw Proposal", new ClickHandler() {
            public void onClick(ClickEvent event) {
                setupSubmitSuccessDialog();
                final KSRichEditor rationaleEditor = new KSRichEditor();
                ConfirmCancelGroup withdrawButton = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>() {

                    @Override
                    public void exec(ConfirmCancelEnum result) {
                        if (!result.name().equals("CANCEL")) {
                            if (rationaleEditor.getText().trim().equals("")) {
                                required.setText("Please enter the decision rationale");
                            } else {
                                addRationale(rationaleEditor, DecisionRationaleDetail.WITHDRAW.getType());

                                workflowRpcServiceAsync.withdrawDocumentWithId(workflowId, new KSAsyncCallback<Boolean>() {
                                    public void handleFailure(Throwable caught) {
                                        submitSuccessDialog.hide();
                                        Window.alert("Error withdrawing Proposal");
                                    }

                                    public void onSuccess(Boolean result) {
                                        submitSuccessDialog.hide();
                                        if (result) {
                                            updateWorkflow(dataModel);
                                            if (submitCallback != null) {
                                                submitCallback.exec(result);
                                            }
                                            // Notify the user that the document was approved
                                            KSNotifier.add(new KSNotification("Proposal will be withdrawn", false));
                                        } else {
                                            Window.alert("Error withdrawing Proposal");
                                        }
                                    }
                                });
                            }

                        } else {
                            submitSuccessDialog.hide();
                        }
                    }
                });

                SectionTitle headerTitle = SectionTitle.generateH3Title("Withdraw Proposal");
                SectionTitle dialogLabel = SectionTitle.generateH4Title("You are withdrawing the " + proposalName + " proposal");
                SectionTitle fieldLabel = SectionTitle.generateH4Title("Decision Rationale");
                required = new AbbrPanel("Required", "ks-form-module-elements-required", " * ");
                required.setVisible(true);
                rationaleEditor.addStyleName("KS-Comment-Create-Editor");
                dialogPanel.clear();
                dialogPanel.add(headerTitle);
                dialogPanel.add(dialogLabel);
                dialogPanel.add(fieldLabel);
                dialogPanel.add(required);
                dialogPanel.add(rationaleEditor);
                dialogPanel.add(withdrawButton);
                dialogPanel.setSize("580px", "400px");
                // submitSuccessDialog.setWidget(dialogPanel);
                submitSuccessDialog.show();
            }
        });
        return wfWithdrawItem;
    }

    private KSMenuItemData getBlanketApproveItem() {
        KSMenuItemData wfBlanketApproveItem;

        wfBlanketApproveItem = new KSMenuItemData("Blanket Approve Proposal", new ClickHandler() {
            public void onClick(ClickEvent event) {
                setupSubmitSuccessDialog();
                final KSRichEditor rationaleEditor = new KSRichEditor();
                ConfirmCancelGroup blanketApprovalButton = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>() {

                    @Override
                    public void exec(ConfirmCancelEnum result) {
                        if (!result.name().equals("CANCEL")) {
                            if (rationaleEditor.getText().trim().equals("")) {
                                required.setText("Please enter the decision rationale");
                            } else {
                                addRationale(rationaleEditor, DecisionRationaleDetail.BLANKET_APPROVE.getType());

                                workflowRpcServiceAsync.blanketApproveDocumentWithId(workflowId, new KSAsyncCallback<Boolean>() {
                                    public void handleFailure(Throwable caught) {
                                        submitSuccessDialog.hide();
                                        Window.alert("Error blanket approving Proposal");
                                    }

                                    public void onSuccess(Boolean result) {
                                        submitSuccessDialog.hide();
                                        if (result) {
                                            updateWorkflow(dataModel);
                                            if (submitCallback != null) {
                                                submitCallback.exec(result);
                                            }
                                            // Notify the user that the document was approved
                                            KSNotifier.add(new KSNotification("Proposal will be blanket approved", false));
                                        } else {
                                            Window.alert("Error blanket approving Proposal");
                                        }
                                    }
                                });
                            }
                        } else {
                            submitSuccessDialog.hide();
                        }
                    }
                });

                SectionTitle headerTitle = SectionTitle.generateH3Title("Blanket Approve Proposal");
                SectionTitle dialogLabel = SectionTitle.generateH4Title("You are blanket approving the " + proposalName + " proposal");
                SectionTitle fieldLabel = SectionTitle.generateH4Title("Decision Rationale");
                required = new AbbrPanel("Required", "ks-form-module-elements-required", " * ");
                required.setVisible(true);
                rationaleEditor.addStyleName("KS-Comment-Create-Editor");
                dialogPanel.clear();
                dialogPanel.add(headerTitle);
                dialogPanel.add(dialogLabel);
                dialogPanel.add(fieldLabel);
                dialogPanel.add(required);
                dialogPanel.add(rationaleEditor);
                dialogPanel.add(blanketApprovalButton);
                dialogPanel.setSize("580px", "400px");
                // submitSuccessDialog.setWidget(dialogPanel);
                submitSuccessDialog.show();
            }
        });
        return wfBlanketApproveItem;
    }

    protected KSDropDown setUpReturnToPreviousDropDown(String workflowId) {
//        nodeNameList.clear();
        final KSDropDown nodeNameDropDown = new KSDropDown();
        nodeNameDropDown.setBlankFirstItem(true);
        workflowRpcServiceAsync.getPreviousRouteNodeNames(workflowId, new KSAsyncCallback<List<String>>() {
            public void handleFailure(Throwable caught) {
                Window.alert("Error getting previous node names for Proposal");
            }

            public void onSuccess(List<String> result) {
                SimpleListItems nodeNameList = new SimpleListItems();
                for (String nodeName : result) {
                    nodeNameList.addItem(nodeName, nodeName);
                }
                nodeNameDropDown.setListItems(nodeNameList);
            }
        });
        nodeNameDropDown.setInitialized(true);
        return nodeNameDropDown;
    }

    private KSMenuItemData getReturnToPreviousItem() {
        KSMenuItemData wfReturnToPreviousItem;

        wfReturnToPreviousItem = new KSMenuItemData("Return Proposal to Previous Node", new ClickHandler() {
            public void onClick(ClickEvent event) {
                setupSubmitSuccessDialog();
                final KSRichEditor rationaleEditor = new KSRichEditor();
                final KSDropDown nodeNameDropDown = setUpReturnToPreviousDropDown(workflowId);
                ConfirmCancelGroup returnButton = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>() {

                    @Override
                    public void exec(ConfirmCancelEnum result) {
                        if (!result.name().equals("CANCEL")) {
                            if ((rationaleEditor.getText().trim().equals("")) && (nodeNameDropDown.getSelectedItem().trim().equals(""))) {
                                required.setText("Please enter the decision rationale and select a node name to return to");
                            } else if (rationaleEditor.getText().trim().equals("")) {
                                required.setText("Please enter the decision rationale");
                            } else if (nodeNameDropDown.getSelectedItem().trim().equals("")) {
                                required.setText("Please select a node name to return to");
                            } else {
                                addRationale(rationaleEditor, DecisionRationaleDetail.RETURN_TO_PREVIOUS.getType());
                                String nodeName = nodeNameDropDown.getSelectedItem().trim();
                                workflowRpcServiceAsync.returnDocumentWithId(workflowId, nodeName, new KSAsyncCallback<Boolean>() {
                                    public void handleFailure(Throwable caught) {
                                        submitSuccessDialog.hide();
                                        Window.alert("Error returning the Proposal to a previous node");
                                    }

                                    public void onSuccess(Boolean result) {
                                        submitSuccessDialog.hide();
                                        if (result) {
                                            updateWorkflow(dataModel);
                                            if (submitCallback != null) {
                                                submitCallback.exec(result);
                                            }
                                            // Notify the user that the document was approved
                                            KSNotifier.add(new KSNotification("Proposal was returned", false));
                                        } else {
                                            Window.alert("Error returning the Proposal to a previous node");
                                        }
                                    }
                                });
                            }

                        } else {
                            submitSuccessDialog.hide();
                        }
                    }
                });

                SectionTitle headerTitle = SectionTitle.generateH3Title("Return Proposal to Previous Node");
                SectionTitle dialogLabel = SectionTitle.generateH4Title("You are returning the " + proposalName + " proposal to a previous node");
                SectionTitle nnFieldLabel = SectionTitle.generateH4Title("Workflow Node Name");
                SectionTitle drFieldLabel = SectionTitle.generateH4Title("Decision Rationale");
                required = new AbbrPanel("Required", "ks-form-module-elements-required", " * ");
                required.setVisible(true);
                rationaleEditor.addStyleName("KS-Comment-Create-Editor");
                dialogPanel.clear();
                dialogPanel.add(headerTitle);
                dialogPanel.add(dialogLabel);
                dialogPanel.add(nnFieldLabel);
                dialogPanel.add(nodeNameDropDown);
                dialogPanel.add(drFieldLabel);
                dialogPanel.add(required);
                dialogPanel.add(rationaleEditor);
                dialogPanel.add(returnButton);
                dialogPanel.setSize("580px", "400px");
                // submitSuccessDialog.setWidget(dialogPanel);
                submitSuccessDialog.show();
            }
        });
        return wfReturnToPreviousItem;
	}

    private void addRationale(KSRichEditor rationaleEditor, String rationaleType) {
        CommentInfo newDecisionRationale = new CommentInfo();
        RichTextInfo text = new RichTextInfo();
        text.setFormatted(rationaleEditor.getHTML());
        text.setPlain(rationaleEditor.getText());
        newDecisionRationale.setReferenceTypeKey(PROPOSAL_REF_TYPE_KEY);
        newDecisionRationale.setReferenceId(proposalId);
        newDecisionRationale.setState(DtoState.ACTIVE.toString());
        newDecisionRationale.setCommentText(text);
        newDecisionRationale.setType(rationaleType);

        try {
            commentServiceAsync.addComment(proposalId, PROPOSAL_REF_TYPE_KEY, newDecisionRationale, new KSAsyncCallback<CommentInfo>() {

                @Override
                public void handleFailure(Throwable caught) {
                    GWT.log("Add Comment Failed", caught);
                }

                @Override
                public void onSuccess(CommentInfo result) {
                    System.out.println("Rationale Added successfully");
                }
            });
        } catch (Exception e) {
            GWT.log("Add Comment Failed", e);
        }
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

    private KSMenuItemData getCancelWorkflowItem() {
        KSMenuItemData wfCancelWorkflowItem;
        final KSRichEditor rationaleEditor = new KSRichEditor();
        wfCancelWorkflowItem = new KSMenuItemData("Cancel Proposal", new ClickHandler() {
            public void onClick(ClickEvent event) {	
            	final ConfirmationDialog confirmationCancelProposal =
            		new ConfirmationDialog("Cancel Proposal","You are about to cancel the proposal. Are you sure?");
            	confirmationCancelProposal.getConfirmButton().addClickHandler(new ClickHandler(){
            		@Override
            		public void onClick(ClickEvent event) {
            			addRationale(rationaleEditor, DecisionRationaleDetail.CANCEL_WORKFLOW.getType());
            			workflowRpcServiceAsync.cancelDocumentWithId(workflowId, new KSAsyncCallback<Boolean>() {
            				public void handleFailure(Throwable caught) {
            					confirmationCancelProposal.hide();
            					Window.alert("Error Cancelling Proposal");
            				}
            				public void onSuccess(Boolean result) {
            					confirmationCancelProposal.hide();
            					if (result) {
            						updateWorkflow(dataModel);
            						if (submitCallback != null) {
            							submitCallback.exec(true);
            						}
            						// Notify the user that the document was canceled
            						KSNotifier.add(new KSNotification("Proposal will be Cancelled", false));
            					} else {
            						Window.alert("Error Cancelling Proposal");
            					}
            				}
            			});
            		
            		}

                });
            	confirmationCancelProposal.show();
            }
        });
        return wfCancelWorkflowItem;
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

	public void addSubmitCallback(Callback<Boolean> callback) {
		this.submitCallback = callback;
		
	}
}
