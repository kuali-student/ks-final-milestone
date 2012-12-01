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
import java.util.HashSet;
import java.util.List;

import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.MetadataInterrogator;
import org.kuali.student.r1.common.assembly.data.ModelDefinition;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.r2.common.dto.DtoConstants.DtoState;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult.ErrorLevel;
import org.kuali.student.r1.core.comment.dto.CommentInfo;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.event.ActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.HasCrossConstraints;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.util.SearchUtils;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSLightBox.Size;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.StylishDropDown;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.buttongroups.AcknowledgeCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.RejectCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.AcknowledgeCancelEnum;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.RejectCancelEnum;
import org.kuali.student.common.ui.client.widgets.dialog.ConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ActionCancelGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.core.comments.ui.client.service.CommentRpcService;
import org.kuali.student.core.comments.ui.client.service.CommentRpcServiceAsync;
import org.kuali.student.r1.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.ui.client.service.ProposalRpcService;
import org.kuali.student.core.proposal.ui.client.service.ProposalRpcServiceAsync;
import org.kuali.student.core.workflow.ui.client.WorkflowConstants;
import org.kuali.student.core.workflow.ui.client.service.WorkflowRpcService;
import org.kuali.student.core.workflow.ui.client.service.WorkflowRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
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
    
    private final List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();
    private final List<KSMenuItemData> additionalItems = new ArrayList<KSMenuItemData>();

    
    SaveActionEvent approveSaveActionEvent;
    SaveActionEvent startWorkflowSaveActionEvent;
    
    WorkflowRpcServiceAsync workflowRpcServiceAsync = GWT.create(WorkflowRpcService.class);
    private final CommentRpcServiceAsync commentServiceAsync = GWT.create(CommentRpcService.class);
    private final ProposalRpcServiceAsync proposalServiceAsync = GWT.create(ProposalRpcService.class);
    
    private String modelName;
    private String proposalPath;
    private String proposalId = "";
    private String workflowId;
    private String proposalName="";
    private String workflowActions="";
        
    private final List<StylishDropDown> workflowWidgets = new ArrayList<StylishDropDown>();
    private Callback<Boolean> submitCallback;
    private final ConfirmationDialog dialog = new ConfirmationDialog("Submit Proposal", "Are you sure you want to submit the proposal to workflow?", "Submit");
    private AbbrPanel required; 
    private KSLightBox submitSuccessDialog;
    private VerticalPanel dialogPanel;
    private VerticalSectionView approveDialogView;
    private VerticalSectionView blanketApproveDialogView;
    private HashSet<String> ignoredApproveDialogFields = new HashSet<String>();
    
    private final KSLabel workflowStatusLabel = new KSLabel("");
    
    private final KSLabel proposalStatusLabel = new KSLabel("");

    private final LayoutController parentController;

    private String dropDownLabel = "Workflow Actions";
    
    ActionCancelGroup approveCancelButtons = new ActionCancelGroup(ButtonEnumerations.ApproveCancelEnum.APPROVE, ButtonEnumerations.ApproveCancelEnum.CANCEL); 
    
    public WorkflowUtilities(LayoutController parentController, String proposalPath) {
        this.parentController = parentController;
        this.proposalPath = proposalPath;
        setupWFButtons();
        setupDialog();
    }
    
    public WorkflowUtilities(LayoutController parentController, String proposalPath, String dropDownLabel) {
        this.dropDownLabel = dropDownLabel;
        this.parentController = parentController;
        this.proposalPath = proposalPath;
        setupWFButtons();
        setupDialog();
    }
    
    public WorkflowUtilities(LayoutController parentController, String proposalPath, String dropDownLabel, Enum<?> viewEnum, String name, String modelId) {
        this.dropDownLabel = dropDownLabel;
        this.parentController = parentController;
        this.proposalPath = proposalPath;

        approveDialogView = new VerticalSectionView(viewEnum, name, modelId);
        approveDialogView.setController(parentController);
        
        blanketApproveDialogView = new VerticalSectionView(viewEnum, name, modelId);
        blanketApproveDialogView.setController(parentController);
        
        setupWFButtons();
        setupDialog();
    }
    
    public void addApproveDialogMsg(String messageText) {
        KSLabel textArea = new KSLabel();
        textArea.setText(messageText);
        
        approveDialogView.addWidget(textArea);
    }
    
    public FieldDescriptor addApproveDialogField(String parentPath, String fieldKey, MessageKeyInfo messageKey, ModelDefinition modelDefinition, boolean forceAdd){
        return addApproveDialogField(parentPath, fieldKey, messageKey, modelDefinition, forceAdd, false, null);

    }
    
       public FieldDescriptor addApproveDialogField(String parentPath, String fieldKey, MessageKeyInfo messageKey, ModelDefinition modelDefinition, boolean forceAdd, boolean readOnly){
            return addApproveDialogField(parentPath, fieldKey, messageKey, modelDefinition, forceAdd, readOnly, null);

        }

    public FieldDescriptor addApproveDialogField(String parentPath, String fieldKey, MessageKeyInfo messageKey, ModelDefinition modelDefinition, boolean forceAdd, boolean readOnly, Widget widget){

        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = modelDefinition.getMetadata(path);
        
        FieldDescriptor blanketApproveFd;
        FieldDescriptor approveFd;
        
        //Always add to blanket approve 
        if(blanketApproveDialogView != null){
            if(!readOnly){
                blanketApproveFd = new FieldDescriptor(path.toString(), messageKey, meta);
            } else {
                blanketApproveFd = new FieldDescriptorReadOnly(path.toString(), messageKey, meta);
            }
            blanketApproveFd.setHasHadFocus(true);
            blanketApproveDialogView.addField(blanketApproveFd);
        }
        
        //Add a new field to the workflow widget
        if (meta != null){
            if(approveDialogView != null){
                if(forceAdd || 
                        (meta.isCanEdit() && 
                                (MetadataInterrogator.isRequiredForNextState(meta) || 
                                        (meta.getConstraints() != null && meta.getConstraints().get(0)!=null && meta.getConstraints().get(0).getMinOccurs()!= null && meta.getConstraints().get(0).getMinOccurs()>0)))){
                    if(!readOnly){
                        approveFd = new FieldDescriptor(path.toString(), messageKey, meta);
                    } else {
                        approveFd = new FieldDescriptorReadOnly(path.toString(), messageKey, meta);
                    }
                    approveFd.setHasHadFocus(true);
                    approveDialogView.addField(approveFd);
                    return approveFd;
                }
            }
        }
        return null;
    }
    
    public void updateApproveFields(){
        parentController.requestModel(new ModelRequestCallback<DataModel>(){
            public void onModelReady(final DataModel model) {
                if(approveDialogView!=null){
                    approveDialogView.updateView(model);
                    for (final FieldDescriptor fd:approveDialogView.getFields()){
                        updateCrossField(fd,model);
                    }
                }
                if(blanketApproveDialogView!=null){
                    blanketApproveDialogView.updateView(model);
                    for (final FieldDescriptor fd:blanketApproveDialogView.getFields()){
                        updateCrossField(fd,model);
                    }
                }
            }
            public void onRequestFail(Throwable cause) {
            }
            
        });
    }
    
    /**
     * Updates and binds all of the dependent fields for the field passed in as a parameter. 
     * Then reprocesses constraints on the field passed in so it is correctly constrained.
     * This is needed since the data values won't be bound to the fields until those sections are displayed
     *
     * TODO This static class should be moved to a utility at some point
     *   
     * @param fd the field with cross constraints that needs updating
     * @param dataModel
     */
    public static void updateCrossField(final FieldDescriptor fd, final DataModel dataModel){
        // Update the widgets of any cross constraints so the values are there and can be reprocessed.
        if (fd.getFieldWidget() instanceof KSPicker && ((KSPicker) fd.getFieldWidget()).getInputWidget() instanceof KSSelectItemWidgetAbstract) {
            if (fd.getFieldWidget() instanceof HasCrossConstraints) {
                HashSet<String> constraints = ((HasCrossConstraints) fd.getFieldWidget()).getCrossConstraints();
                if (constraints != null) {
                    for (String path : constraints) {
                        final String finalPath = SearchUtils.resolvePath(path);
                        final FieldDescriptor crossField = Application.getApplicationContext().getPathToFieldMapping(null, finalPath);
                        if (crossField != null) {
                            final ModelWidgetBinding mwb = crossField.getModelWidgetBinding();
                            if (mwb != null) {
                                // This insanity is needed because setting a widget value can be asynchronous.
                                // Adds a callback and reprocesses constraints after the value has actually been set
                                if (crossField.getFieldWidget() instanceof KSPicker && ((KSPicker) crossField.getFieldWidget()).getInputWidget() instanceof KSSelectItemWidgetAbstract) {
                                    ((KSSelectItemWidgetAbstract) ((KSPicker) crossField.getFieldWidget()).getInputWidget()).addWidgetReadyCallback(new Callback<Widget>() {
                                        public void exec(Widget result) {
                                            mwb.setWidgetValue(crossField.getFieldWidget(), dataModel, finalPath);
                                            ((HasCrossConstraints) fd.getFieldWidget()).reprocessWithUpdatedConstraints();
                                        }
                                    });
                                } else {
                                    mwb.setWidgetValue(crossField.getFieldWidget(), dataModel, finalPath);
                                }
                            }
                        }
                    }
                }
                ((HasCrossConstraints) fd.getFieldWidget()).reprocessWithUpdatedConstraints();
            }
        }
    }
    public void requestAndSetupModel(final Callback<Boolean> onModelReadyCallback) {
        
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
                    onModelReadyCallback.exec(true);
                }
            });
        }else{
            //If the model has been set don't waste time finding it again and don't register 
            //another change listener, just update
            updateWorkflow(dataModel);
            onModelReadyCallback.exec(true);
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
            submitSuccessDialog.setSize(580, 480);
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
                    @Override
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
        StylishDropDown workflowActionsDropDown = GWT.create(StylishDropDown.class);
        workflowActionsDropDown.initialise(dropDownLabel );
        workflowActionsDropDown.makeAButtonWhenOneItem(true);
        workflowActionsDropDown.addStyleName("KS-Workflow-DropDown");
        workflowWidgets.add(workflowActionsDropDown);
        workflowActionsDropDown.setVisible(false);
        updateWorkflow(dataModel);
/*      infoContainer.add(workflowActionsDropDown);
        infoContainer.showWarnStyling(false);
        infoContainer.setVisible(true);*/
        //workflowActionsDropDown
        return workflowActionsDropDown;
    }
    
    public void enableWorkflowActionsWidgets(boolean enable) {
        workflowWidgetsEnabled = enable;
        updateWorkflowActionsWidget();
    }
    
    //callback is returned a List<ValidationResultInfo> result through the callback's exec
    public void doValidationCheck(Callback<List<ValidationResultInfo>> callback){
        dataModel.validateNextState(callback);
    }
    
    public KSLabel getWorkflowStatusLabel(){
        return workflowStatusLabel;
    }
    
    public KSLabel getProposalStatusLabel() {
        return proposalStatusLabel;
    }

    private void updateWorkflowIdFromModel(final DataModel model) {
        if(model!=null){
            String modelProposalId = model.get(QueryPath.parse(proposalPath + "/id"));
            
            //If proposalId in model has been set or changed, get new workflowId and update workflow widget
            if (modelProposalId != null && !modelProposalId.isEmpty() && !modelProposalId.equals(proposalId)){
                proposalId = modelProposalId;
                workflowId = model.get(QueryPath.parse(proposalPath + "/workflowId"));
                proposalName = model.get(QueryPath.parse(proposalPath + "/name"));
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
                    setWorkflowStatus("Unknown");
                }

                @Override
                public void onSuccess(String result) {
                    setWorkflowStatus(result);
                }                       
            });
            proposalServiceAsync.getProposalByWorkflowId(workflowId, new KSAsyncCallback<ProposalInfo>() {
                @Override
                public void handleFailure(Throwable caught) {
                    setProposalStatus("Unknown");
                }

                @Override
                public void onSuccess(ProposalInfo result) {
                    setProposalStatus(result.getState());
                }
            });
        } else {
            setWorkflowStatus("Draft");
            setProposalStatus("Draft");
        }           
    }
    
    private void updateWorkflowActionsWidget(){
        items.clear();

        //When workflow widgets enabled display all available actions, otherwise only
        //display Cancel, Acknowledge, FYI and Withdraw actions.
        if (workflowWidgetsEnabled){
            //Get the workflowNode from the proposal
            String workflowNode = null;
            if(dataModel!=null){
                workflowNode = dataModel.get("proposal/workflowNode");
            }
            
            if(workflowActions.contains("S")){
                items.add(wfStartWorkflowItem);
            }
            if(workflowActions.contains("C")){
                items.add(wfCancelWorkflowItem);
            }
            if(workflowActions.contains("A")){
                items.add(wfApproveItem);
                //Change the approve label  to resubmit if it is the first node
                if("PreRoute".equals(workflowNode)){
                    wfApproveItem.setLabel("Resubmit Proposal");
                }else{
                    wfApproveItem.setLabel("Approve Proposal");
                    //Only add disapprove if it is not the first node
                    items.add(wfDisApproveItem);
                }
            }
            if(workflowActions.contains("K")){
                items.add(wfAcknowledgeItem);
            }
            if(workflowActions.contains("F")){
                items.add(wfFYIWorkflowItem);
            }
            if(workflowActions.contains("R")){
                //Don't show the return to previous if this is already in preroute.
                //Why is this showing up in WF actions?
                if(!"PreRoute".equals(workflowNode)){
                    items.add(wfReturnToPreviousItem);
                }
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
            if(workflowActions.contains("K")){
                items.add(wfAcknowledgeItem);
            }
            if(workflowActions.contains("F")){
                items.add(wfFYIWorkflowItem);
            }            
            if(workflowActions.contains("W")){
                items.add(wfWithdrawItem);
            }
            if(workflowActions.contains("A")){
                items.add(wfDisApproveItem);               
            }
        }

        //Add in any other custom items you want in this dropdown.
        items.addAll(additionalItems);
        
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
                    @Override
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
                final AcknowledgeCancelGroup approvalButton = new AcknowledgeCancelGroup(new Callback<AcknowledgeCancelEnum>(){

                    @Override
                    public void exec(AcknowledgeCancelEnum result) {
                        if(!result.name().equals("CANCEL")){
                            addRationale(rationaleEditor,DecisionRationaleDetail.ACKNOWLEDGE.getType());
                            workflowRpcServiceAsync.acknowledgeDocumentWithId(workflowId, new KSAsyncCallback<Boolean>(){
                                @Override
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
                
                approvalButton.getButton(ButtonEnumerations.AcknowledgeCancelEnum.ACKNOWLEDGE).setEnabled(false);
                
                rationaleEditor.getRichTextArea().addKeyUpHandler(new KeyUpHandler(){

                    @Override
                    public void onKeyUp(KeyUpEvent event) {
                        if(!rationaleEditor.getText().trim().isEmpty()){
                            approvalButton.getButton(ButtonEnumerations.AcknowledgeCancelEnum.ACKNOWLEDGE).setEnabled(true);
                        } else {
                            approvalButton.getButton(ButtonEnumerations.AcknowledgeCancelEnum.ACKNOWLEDGE).setEnabled(false);
                        }                            
                    }
                    
                });
                
                SectionTitle headerTitle = SectionTitle.generateH3Title("Acknowledge Proposal");
                SectionTitle dialogLabel = SectionTitle.generateH4Title("You are acknowledging the " + proposalName +" proposal");
                SectionTitle fieldLabel = SectionTitle.generateH4Title("Decision Rationale");
                rationaleEditor.addStyleName("KS-Comment-Create-Editor");
                dialogPanel.clear();
                submitSuccessDialog.clearButtons();
                submitSuccessDialog.setNonCaptionHeader(headerTitle);
                dialogPanel.add(dialogLabel);
                dialogPanel.add(fieldLabel);
                dialogPanel.add(rationaleEditor);
                submitSuccessDialog.addButtonGroup(approvalButton);
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
                
                final RejectCancelGroup disapprovalButton = new RejectCancelGroup(new Callback<RejectCancelEnum>(){

                    @Override
                    public void exec(RejectCancelEnum result) {
                        if(!result.name().equals("CANCEL")){
                            if(rationaleEditor.getText().trim().equals("")){
                                required.setText("Please enter the decision rationale");
                            }
                            else{
                                addRationale(rationaleEditor,DecisionRationaleDetail.REJECT.getType());
                                workflowRpcServiceAsync.disapproveDocumentWithId(workflowId, new KSAsyncCallback<Boolean>(){
                                    @Override
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
                
                disapprovalButton.getButton(ButtonEnumerations.RejectCancelEnum.REJECT).setEnabled(false);
                
                rationaleEditor.getRichTextArea().addKeyUpHandler(new KeyUpHandler(){

                    @Override
                    public void onKeyUp(KeyUpEvent event) {
                        if(!rationaleEditor.getText().trim().isEmpty()){
                            disapprovalButton.getButton(ButtonEnumerations.RejectCancelEnum.REJECT).setEnabled(true);
                        } else {
                            disapprovalButton.getButton(ButtonEnumerations.RejectCancelEnum.REJECT).setEnabled(false);
                        }                            
                    }
                    
                });
                
                SectionTitle headerTitle = SectionTitle.generateH3Title("Reject Proposal");
                SectionTitle dialogLabel = SectionTitle.generateH4Title("You are rejecting the " + proposalName +" proposal");
                SectionTitle fieldLabel = SectionTitle.generateH4Title("Decision Rationale");
                required = new AbbrPanel("Required", "ks-form-module-elements-required", " * ");
                required.setVisible(true);
//              final KSRichEditor rationaleEditor = new KSRichEditor();
//              rationaleEditor.addStyleName("ks-textarea-width");
//              rationaleEditor.addStyleName("ks-textarea-large-height");
                rationaleEditor.addStyleName("KS-Comment-Create-Editor");
                dialogPanel.clear();
                submitSuccessDialog.clearButtons();
                submitSuccessDialog.setNonCaptionHeader(headerTitle);
                dialogPanel.add(dialogLabel);
                dialogPanel.add(fieldLabel);
                dialogPanel.add(required);
                dialogPanel.add(rationaleEditor);
                submitSuccessDialog.addButtonGroup(disapprovalButton);
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
                
//              final ActionCancelGroup approveCancelButtons = new ActionCancelGroup(ButtonEnumerations.ApproveCancelEnum.APPROVE, ButtonEnumerations.ApproveCancelEnum.CANCEL);                
                                  
                approveCancelButtons.getButton(ButtonEnumerations.ApproveCancelEnum.APPROVE).setEnabled(true);
                
                approveCancelButtons.addCallback(new Callback<ButtonEnumerations.ButtonEnum>() {
                    @Override
                    public void exec(ButtonEnumerations.ButtonEnum result) {
                        if (result != ButtonEnumerations.ApproveCancelEnum.CANCEL) {
                            {
                                // - Removed check here to make Rational for Approvals not required.
                                if (approveDialogView != null) {
                                    // Validate all the fields on the current section (the additional required fields)
                                    parentController.requestModel(new ModelRequestCallback<DataModel>() {

                                        @Override
                                        public void onModelReady(DataModel model) {
                                            approveDialogView.updateModel();
                                            model.validateNextState(new Callback<List<ValidationResultInfo>>() {
                                                @Override
                                                public void exec(List<ValidationResultInfo> results) {
                                                    // Process the results on the additional fields view
                                                    if (ErrorLevel.OK.equals(approveDialogView.processValidationResults(results))) {
                                                        // Save first and then do the workflow actions later
                                                        SaveActionEvent saveActionEvent = new SaveActionEvent();
                                                        saveActionEvent.setActionCompleteCallback(new ActionCompleteCallback() {
                                                            public void onActionComplete(ActionEvent action) {
                                                                doWorkflowApprove();
                                                            }
                                                        });
                                                        parentController.fireApplicationEvent(saveActionEvent);
                                                    } else {
                                                        KSNotifier.add(new KSNotification("Unable to save, please check fields for errors.", false, true, 5000));
                                                    }
                                                }
                                            });
                                        }

                                        @Override
                                        public void onRequestFail(Throwable cause) {
                                            KSNotifier.add(new KSNotification("Error requesting data model.", false, true, 5000));
                                        }

                                    });

                                } else {
                                    doWorkflowApprove();
                                }

                            }

                        } else {
                            submitSuccessDialog.hide();
                        }
                    }

                    private void doWorkflowApprove() {
                        addRationale(rationaleEditor, DecisionRationaleDetail.APPROVE.getType());

                        workflowRpcServiceAsync.approveDocumentWithId(workflowId, new KSAsyncCallback<Boolean>() {
                            @Override
                            public void handleFailure(Throwable caught) {
                                submitSuccessDialog.hide();
                                Window.alert("Error approving Proposal");
                            }

                            public void onSuccess(Boolean result) {
                                submitSuccessDialog.hide();
                                if (result) {
                                    updateWorkflow(dataModel);
                                    if (submitCallback != null) {
                                        submitCallback.exec(result);
                                    }
                                    // Notify the user that the document was approved
                                    KSNotifier.add(new KSNotification("Proposal was approved", false));
                                } else {
                                    Window.alert("Error approving Proposal");
                                }
                            }
                        });

                    }
                });                     

                if(approveDialogView.getField("proposal/prevEndTerm") != null){                    
                    
                    approveCancelButtons.getButton(ButtonEnumerations.ApproveCancelEnum.APPROVE).setEnabled(false);                    
                    rationaleEditor.getRichTextArea().addKeyUpHandler(new KeyUpHandler(){

                        @Override
                        public void onKeyUp(KeyUpEvent event) {
                            if(!rationaleEditor.getText().trim().isEmpty() && !(((KSPicker) (approveDialogView.getField("proposal/prevEndTerm").getFieldWidget())).getDisplayValue() == "") ){
                                approveCancelButtons.getButton(ButtonEnumerations.ApproveCancelEnum.APPROVE).setEnabled(true);
                            } else {
                                approveCancelButtons.getButton(ButtonEnumerations.ApproveCancelEnum.APPROVE).setEnabled(false);
                            }                            
                        }
                        
                    });     
                    
                    if (approveDialogView.getField("proposal/prevEndTerm").getFieldWidget() instanceof KSPicker) {
                            ((KSPicker) (approveDialogView.getField("proposal/prevEndTerm").getFieldWidget())).addSelectionChangeHandler(new SelectionChangeHandler(){

                            @Override
                            public void onSelectionChange(SelectionChangeEvent event) {
                                if(!rationaleEditor.getText().trim().isEmpty() && !(((KSPicker) (approveDialogView.getField("proposal/prevEndTerm").getFieldWidget())).getDisplayValue() == "")  ){
                                    approveCancelButtons.getButton(ButtonEnumerations.ApproveCancelEnum.APPROVE).setEnabled(true);
                                } else {
                                    approveCancelButtons.getButton(ButtonEnumerations.ApproveCancelEnum.APPROVE).setEnabled(false);
                                }
                            }

                        });
                    }
                } else {
                    
                    rationaleEditor.getRichTextArea().addKeyUpHandler(new KeyUpHandler(){

                        @Override
                        public void onKeyUp(KeyUpEvent event) {
                            if(!rationaleEditor.getText().trim().isEmpty()){
                                approveCancelButtons.getButton(ButtonEnumerations.ApproveCancelEnum.APPROVE).setEnabled(true);
                            } else {
                                approveCancelButtons.getButton(ButtonEnumerations.ApproveCancelEnum.APPROVE).setEnabled(false);
                            }                            
                        }
                        
                    }); 
                    
                }
                
                SectionTitle headerTitle = SectionTitle.generateH3Title("Approve Proposal");
                SectionTitle dialogLabel = SectionTitle.generateH4Title("You are approving the " + proposalName +" proposal");
                SectionTitle fieldLabel = SectionTitle.generateH4Title("Decision Rationale");               
                required = new AbbrPanel("Required", "ks-form-module-elements-required", "  ");
                required.setVisible(true);              
                HorizontalPanel rationalePanel = new HorizontalPanel();                
                rationalePanel.add(fieldLabel);
                rationalePanel.add(required);               
                rationaleEditor.addStyleName("KS-Comment-Create-Editor");
                rationaleEditor.setPixelSize( 520,180);
                dialogPanel.clear();
                submitSuccessDialog.clearButtons();
                submitSuccessDialog.setNonCaptionHeader(headerTitle);
                dialogPanel.add(dialogLabel);
                dialogPanel.add(rationalePanel);
                dialogPanel.add(rationaleEditor);
                if(approveDialogView!=null && !approveDialogView.getFields().isEmpty()){
                    dialogPanel.add(approveDialogView.asWidget());
                }
                submitSuccessDialog.addButtonGroup(approveCancelButtons);               
                dialogPanel.setWidth("380px");              
                submitSuccessDialog.setSize(Size.MEDIUM);
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
                final ConfirmCancelGroup withdrawButton = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>() {

                    @Override
                    public void exec(ConfirmCancelEnum result) {
                        if (!result.name().equals("CANCEL")) {
                            if (rationaleEditor.getText().trim().equals("")) {
                                required.setText("Please enter the decision rationale");
                            } else {
                                addRationale(rationaleEditor, DecisionRationaleDetail.WITHDRAW.getType());

                                workflowRpcServiceAsync.withdrawDocumentWithId(workflowId, new KSAsyncCallback<Boolean>() {
                                    @Override
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
                
                withdrawButton.getButton(ButtonEnumerations.ConfirmCancelEnum.CONFIRM).setEnabled(false);
                
                rationaleEditor.getRichTextArea().addKeyUpHandler(new KeyUpHandler(){

                    @Override
                    public void onKeyUp(KeyUpEvent event) {
                        if(!rationaleEditor.getText().trim().isEmpty()){
                            withdrawButton.getButton(ButtonEnumerations.ConfirmCancelEnum.CONFIRM).setEnabled(true);
                        } else {
                            withdrawButton.getButton(ButtonEnumerations.ConfirmCancelEnum.CONFIRM).setEnabled(false);
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
                submitSuccessDialog.clearButtons();
                submitSuccessDialog.setNonCaptionHeader(headerTitle);
                dialogPanel.add(dialogLabel);
                dialogPanel.add(fieldLabel);
                dialogPanel.add(required);
                dialogPanel.add(rationaleEditor);
                submitSuccessDialog.addButtonGroup(withdrawButton);
                //dialogPanel.setSize("580px", "400px");
                dialogPanel.setHeight("380px");
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
                final ConfirmCancelGroup blanketApprovalButton = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>() {

                    @Override
                    public void exec(ConfirmCancelEnum result) {
                        if (!result.name().equals("CANCEL")) {
                            if (rationaleEditor.getText().trim().equals("")) {
                                required.setText("Please enter the decision rationale");
                            } else {
                                
                                if(blanketApproveDialogView!=null){
                                    //Validate all the fields on the current section (the additional required fields)
                                    parentController.requestModel(new ModelRequestCallback<DataModel>(){

                                        @Override
                                        public void onModelReady(final DataModel model) {
                                            blanketApproveDialogView.updateModel();
                                            if(parentController instanceof WorkflowEnhancedNavController){
                                                ((WorkflowEnhancedNavController)parentController).getMetadataForFinalState(new KSAsyncCallback<Metadata>(){

                                                    @Override
                                                    public void onSuccess(
                                                            Metadata metadata) {
                                                        model.validateForMetadata(metadata, new Callback<List<ValidationResultInfo>>() {
                                                            @Override
                                                            public void exec(List<ValidationResultInfo> results) {
                                                                //first validate the dialog section
                                                                
                                                                //Process the results on the additional fields view
                                                                
                                                                if(ErrorLevel.OK.equals(blanketApproveDialogView.processValidationResults(results))){
                                                                    List<String> ignoreFields = new ArrayList<String>(ignoredApproveDialogFields);
                                                                    for(FieldDescriptor fd:blanketApproveDialogView.getFields()){
                                                                        ignoreFields.add(fd.getFieldKey());
                                                                    }
                                                                    if(!ValidationResultInfo.hasValidationErrors(results,ErrorLevel.WARN,ignoreFields)){
                                                                        //Save first and then do the workflow actions later
                                                                        SaveActionEvent saveActionEvent = new SaveActionEvent();
                                                                        saveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
                                                                            public void onActionComplete(ActionEvent action) {
                                                                                doBlanketApprove();
                                                                            }
                                                                        });
                                                                        parentController.fireApplicationEvent(saveActionEvent);
                                                                    }else{
                                                                        submitSuccessDialog.hide();
                                                                        KSNotifier.add(new KSNotification("Unable to blanket approve, please enter all data required for final approval.", false, true, 5000));
                                                                    }
                                                                }else{
                                                                    KSNotifier.add(new KSNotification("Unable to blanket approve, please enter all data required for final approval.", false, true, 5000));
                                                                }
                                                            }
                                                        });
                                                    }
                                                    
                                                });
                                            }
                                        }

                                        @Override
                                        public void onRequestFail(
                                                Throwable cause) {
                                            KSNotifier.add(new KSNotification("Error requesting data model.", false, true, 5000));
                                        }
                                        
                                    });

                                }else{
                                    doBlanketApprove();
                                }
                                
    
                            }
                        } else {
                            submitSuccessDialog.hide();
                            updateWorkflowActionsWidget();
                        }
                    }

                    private void doBlanketApprove() {
                        addRationale(rationaleEditor, DecisionRationaleDetail.BLANKET_APPROVE.getType());
                        workflowRpcServiceAsync.blanketApproveDocumentWithId(workflowId, new KSAsyncCallback<Boolean>() {
                            @Override
                            public void handleFailure(Throwable caught) {
                                    submitSuccessDialog.hide();
                                Window.alert("Error blanket approving Proposal");
                            }

                            public void onSuccess(Boolean result) {
                                    submitSuccessDialog.hide();
                                if (result) {
                                    // KSLAB-1828; we get "B" back from workflowRpcServiceAsync.getActionsRequested()
                                    // even though we just successfully submitted blanket approval to workflow,
                                    // because the workflow action hasn't been completed yet.
                                    enableWorkflowActionsWidgets(false);

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
                });
                
                blanketApprovalButton.getButton(ButtonEnumerations.ConfirmCancelEnum.CONFIRM).setEnabled(false);
                
                rationaleEditor.getRichTextArea().addKeyUpHandler(new KeyUpHandler(){

                    @Override
                    public void onKeyUp(KeyUpEvent event) {
                        if(!rationaleEditor.getText().trim().isEmpty()){
                            blanketApprovalButton.getButton(ButtonEnumerations.ConfirmCancelEnum.CONFIRM).setEnabled(true);
                        } else {
                            blanketApprovalButton.getButton(ButtonEnumerations.ConfirmCancelEnum.CONFIRM).setEnabled(false);
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
                submitSuccessDialog.clearButtons();
                submitSuccessDialog.setNonCaptionHeader(headerTitle);
                dialogPanel.add(dialogLabel);
                dialogPanel.add(fieldLabel);
                dialogPanel.add(required);
                dialogPanel.add(rationaleEditor);
                if(blanketApproveDialogView!=null && !blanketApproveDialogView.getFields().isEmpty()){
                    dialogPanel.add(blanketApproveDialogView.asWidget());
                }
                submitSuccessDialog.addButtonGroup(blanketApprovalButton);
                //dialogPanel.setSize("580px", "400px");
                dialogPanel.setHeight("380px");
                // submitSuccessDialog.setWidget(dialogPanel);
                submitSuccessDialog.show();
            }
        });
        return wfBlanketApproveItem;
    }

    /**
     * Call this method to blanked approve the workflow document associated with dataModel
     * 
     * 
     * @param onSuccessCallback
     */
    public void blanketApprove(final Callback<Boolean> onSuccessCallback){
        updateWorkflowIdFromModel(dataModel);
        workflowRpcServiceAsync.blanketApproveDocumentWithId(workflowId, new KSAsyncCallback<Boolean>() {
            @Override
            public void handleFailure(Throwable caught) {
                Window.alert("Error blanket approving Proposal");
            }

            public void onSuccess(Boolean result) {
                if (result) {
                    onSuccessCallback.exec(true);
                } else {
                    Window.alert("Error blanket approving Proposal");
                }
            }
        });     
    }

    /**
     * Call this method to cancel the workflow document associated with dataModel. User will be presented 
     * with a confirmation dialog to confirm the cancellation of the proposal.
     * 
     * @param onSuccessCallback
     */
    public void cancel(final Callback<Boolean> onSuccessCallback){
        ConfirmationDialog confirmCancelDialog = getConfirmationCancelProposalDialog(onSuccessCallback);
        confirmCancelDialog.show();
    }

    
    protected KSDropDown setUpReturnToPreviousDropDown(String workflowId) {
//        nodeNameList.clear();
        final KSDropDown nodeNameDropDown = new KSDropDown();
        nodeNameDropDown.setBlankFirstItem(true);
        workflowRpcServiceAsync.getPreviousRouteNodeNames(workflowId, new KSAsyncCallback<List<String>>() {
            @Override
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
                final ConfirmCancelGroup returnButton = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>() {

                    @Override
                    public void exec(ConfirmCancelEnum result) {
                        if (!result.name().equals("CANCEL")) {
                            if ((rationaleEditor.getText().trim().equals("")) && (nodeNameDropDown.getSelectedItem()==null || nodeNameDropDown.getSelectedItem().trim().equals(""))) {
                                required.setText("Please enter the decision rationale and select a node name to return to");
                            } else if (rationaleEditor.getText().trim().equals("")) {
                                required.setText("Please enter the decision rationale");
                            } else if (nodeNameDropDown.getSelectedItem()==null || nodeNameDropDown.getSelectedItem().trim().equals("")) {
                                required.setText("Please select a node name to return to");
                            } else {
                                addRationale(rationaleEditor, DecisionRationaleDetail.RETURN_TO_PREVIOUS.getType());
                                String nodeName = nodeNameDropDown.getSelectedItem().trim();
                                workflowRpcServiceAsync.returnDocumentWithId(workflowId, nodeName, new KSAsyncCallback<Boolean>() {
                                    @Override
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
                
                returnButton.getButton(ButtonEnumerations.ConfirmCancelEnum.CONFIRM).setEnabled(false);
                
                rationaleEditor.getRichTextArea().addKeyUpHandler(new KeyUpHandler(){

                    @Override
                    public void onKeyUp(KeyUpEvent event) {
                        if(!rationaleEditor.getText().trim().isEmpty() && !(nodeNameDropDown.getSelectedItem() == null)){
                            returnButton.getButton(ButtonEnumerations.ConfirmCancelEnum.CONFIRM).setEnabled(true);
                        } else {
                            returnButton.getButton(ButtonEnumerations.ConfirmCancelEnum.CONFIRM).setEnabled(false);
                        }                            
                    }
                    
                }); 
                
                nodeNameDropDown.addSelectionChangeHandler(new SelectionChangeHandler(){

                    @Override
                    public void onSelectionChange(SelectionChangeEvent event) {
                        if(!rationaleEditor.getText().trim().isEmpty() && !(nodeNameDropDown.getSelectedItem() == null)){
                            returnButton.getButton(ButtonEnumerations.ConfirmCancelEnum.CONFIRM).setEnabled(true);
                        } else {
                            returnButton.getButton(ButtonEnumerations.ConfirmCancelEnum.CONFIRM).setEnabled(false);
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
                submitSuccessDialog.clearButtons();
                submitSuccessDialog.setNonCaptionHeader(headerTitle);
                dialogPanel.add(dialogLabel);
                dialogPanel.add(nnFieldLabel);
                dialogPanel.add(nodeNameDropDown);
                dialogPanel.add(drFieldLabel);
                dialogPanel.add(required);
                dialogPanel.add(rationaleEditor);
                submitSuccessDialog.addButtonGroup(returnButton);
                //dialogPanel.setSize("580px", "400px");
                dialogPanel.setHeight("380px");
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
                        
                        boolean isValid = parentController.isValid(result, false);
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
        wfCancelWorkflowItem = new KSMenuItemData("Cancel Proposal", new ClickHandler() {
            public void onClick(ClickEvent event) { 
                ConfirmationDialog confirmationCancelProposal = getConfirmationCancelProposalDialog(null);
                confirmationCancelProposal.show();
            }
        });
        return wfCancelWorkflowItem;
    }

    protected ConfirmationDialog getConfirmationCancelProposalDialog (final Callback<Boolean> onSuccessCallback){
        final ConfirmationDialog confirmationCancelProposal = new ConfirmationDialog("Cancel Proposal","This action is not reversible and all data will be lost. Do you wish to cancel this proposal?");
        
        confirmationCancelProposal.getConfirmButton().setText("Yes, cancel proposal");
        confirmationCancelProposal.getCancelButton().setStyleName(ButtonStyle.PRIMARY_SMALL.getStyle());
        confirmationCancelProposal.getCancelButton().setText("No, return to proposal");
           
        confirmationCancelProposal.getConfirmButton().addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
                workflowRpcServiceAsync.cancelDocumentWithId(workflowId, new KSAsyncCallback<Boolean>() {
                    @Override
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
                            if (onSuccessCallback != null){
                                onSuccessCallback.exec(true);
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
        
        return confirmationCancelProposal;
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
            statusTranslation = getLabel(WorkflowConstants.ROUTE_HEADER_APPROVED_LABEL_KEY);//getLabel(WorkflowConstants.ROUTE_HEADER_FINAL_LABEL_KEY);
        } else if (WorkflowConstants.ROUTE_HEADER_DISAPPROVE_CANCEL_CD.equals(statusCd)){
            statusTranslation = getLabel(WorkflowConstants.ROUTE_HEADER_DISAPPROVE_CANCEL_LABEL_KEY);
        } else if (WorkflowConstants.ROUTE_HEADER_PROCESSED_CD.equals(statusCd)){
            statusTranslation = getLabel(WorkflowConstants.ROUTE_HEADER_APPROVED_LABEL_KEY);//getLabel(WorkflowConstants.ROUTE_HEADER_PROCESSED_LABEL_KEY);
        } else {
            statusTranslation = statusCd;
        }
        
        workflowStatusLabel.setText("Status: " + statusTranslation);    
    }
    
    private void setProposalStatus(String statusCode) {
        String statusLabel = Application.getApplicationContext()
                .getUILabel("common", null, null, "proposalStatusLabel");
        String status = Application.getApplicationContext().getUILabel("common", null, null, statusCode);

        proposalStatusLabel.setText(statusLabel + ": " + status);
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

    //TODO: currently causing a lot of duplicate calls and has to be used carefully. Commented out for now.
    public void refresh(){
        updateApproveFields();
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

    public List<KSMenuItemData> getAdditionalItems() {
        return additionalItems;
    }

    public void addIgnoreDialogField(String string) {
        ignoredApproveDialogFields.add(string);
    }
    
    public VerticalSectionView getApproveDialogue() {
        return approveDialogView;
    }
    
    public void progressiveEnableFields() {
        if (getApproveDialogue() != null) {
            
            FieldDescriptor prevEndTerm = approveDialogView.getField("proposal/prevEndTerm");

            if (prevEndTerm != null) {
                approveDialogView.getWidget(0).setVisible(true);
                approveDialogView.getField("proposal/prevEndTerm").getMetadata().getConstraints().get(0).setMinOccurs(1);
                approveDialogView.getField("proposal/prevEndTerm").getFieldElement().setRequiredString("requiredMarker", "ks-form-module-elements-required");
                approveDialogView.getField("startTerm").getFieldWidget().setVisible(true);
            } else {
                approveDialogView.getWidget(0).setVisible(false);
                approveDialogView.getField("startTerm").getFieldWidget().setVisible(false);
            }
        }
    }
    
}
