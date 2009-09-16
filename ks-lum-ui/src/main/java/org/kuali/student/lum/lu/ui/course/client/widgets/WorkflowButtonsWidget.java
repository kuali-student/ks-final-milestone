package org.kuali.student.lum.lu.ui.course.client.widgets;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelChangeEvent;
import org.kuali.student.common.ui.client.mvc.ModelChangeHandler;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalModelDTO;
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
	Model<CluProposalModelDTO> cluProposalWorkflowModel=null;
	
	boolean loaded=false;
    
	private KSButton wfApproveButton;
    private KSButton wfDisApproveButton;
    private KSButton wfAcknowledgeButton;
    private KSButton wfStartWorkflowButton;
    
    CluProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CluProposalRpcService.class);
    
    private VerticalPanel rootPanel = new VerticalPanel();
    
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
		if(null==cluProposalWorkflowModel){
			
			//Get the Model from the controller and register a model change handler when the workflow model is updated
			Controller.findController(this).requestModel(CluProposalModelDTO.class, new ModelRequestCallback<CluProposalModelDTO>(){
			
				@Override
				public void onModelReady(Model<CluProposalModelDTO> model) {
					
					//After we get the model update immediately
					cluProposalWorkflowModel = model;
					updateWorkflow(cluProposalWorkflowModel.get());
					
					//Add a change listener for when the model changes
					model.addModelChangeHandler(new ModelChangeHandler<CluProposalModelDTO>(){
						@Override
						public void onModelChange(ModelChangeEvent<CluProposalModelDTO> event) {
							updateWorkflow(event.getValue());
						}
					});
				}
	
				@Override
				public void onRequestFail(Throwable cause) {
					Window.alert("Model Request Failed. "+cause.getMessage());
				}
			});
		}else{
			
			//If the model has been set don't waste time finding it again and don't register 
			//another change listener, just update
			updateWorkflow(cluProposalWorkflowModel.get());
		}
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
    			CluProposalModelDTO model = cluProposalWorkflowModel.get();
    			if(model==null||((StringType)model.get("adminOrg")).get()==null){
    				Window.alert("Administering Organization must be entered and saved before workflow can be started.");
    			}else{
        			cluProposalRpcServiceAsync.submitProposal(model, new AsyncCallback<Boolean>(){
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
    	wfStartWorkflowButton.setVisible(false);
    	
		wfApproveButton = new KSButton("Approve", new ClickHandler(){
			public void onClick(ClickEvent event) {
    			CluProposalModelDTO model = cluProposalWorkflowModel.get();
				cluProposalRpcServiceAsync.approveProposal(model, new AsyncCallback<Boolean>(){
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
		wfApproveButton.setVisible(false);

		wfDisApproveButton = new KSButton("Disapprove", new ClickHandler(){
	        public void onClick(ClickEvent event) {
    			CluProposalModelDTO model = cluProposalWorkflowModel.get();
				cluProposalRpcServiceAsync.disapproveProposal(model, new AsyncCallback<Boolean>(){
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
    			CluProposalModelDTO model = cluProposalWorkflowModel.get();
				cluProposalRpcServiceAsync.acknowledgeProposal(model, new AsyncCallback<Boolean>(){
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

	private void updateWorkflow(CluProposalModelDTO model){

		cluProposalRpcServiceAsync.getActionsRequested(model, new AsyncCallback<String>(){

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
