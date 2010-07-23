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

package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.StylishDropDown;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.core.assembly.data.QueryPath;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;


// Skeleton for an action list for View Course.  Actions don't go anywhere yet as most functionality
// hasn't been coded yet.

public class ViewCourseActionList extends Composite {
	DataModel dataModel=null;
	
	private KSMenuItemData retireCourseAction;
	private KSMenuItemData copyCourseAction;
	private KSMenuItemData modifyCourseAction;
	
	List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();
    
    private String modelName;
    private String courseCodePath;
       
	private HorizontalBlockFlowPanel rootPanel = new HorizontalBlockFlowPanel();
	private StylishDropDown courseActionsDropDown = new StylishDropDown("Course Actions");
	
    Controller myController;
    
	public ViewCourseActionList(CloseHandler<KSLightBox> onSubmitSuccessHandler) {
		super();
		super.initWidget(rootPanel);
		
		setupButtons();

		rootPanel.add(courseActionsDropDown);
		
		courseActionsDropDown.addStyleName("KS-Workflow-DropDown");
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
					updateActions(dataModel);
					 
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
			updateActions(dataModel);
		}
	}
	
	private String getCourseCodeFromModel(DataModel model){
		String courseCode = "";
		if(model!=null){
			courseCode = model.get(QueryPath.parse(courseCodePath));
		}
		return courseCode;
	}
	
	private void setupButtons() {
//		final ConfirmationDialog dialog = new ConfirmationDialog("Submit Proposal", "Are you sure you want to submit the proposal to workflow?", "Submit");
//		dialog.getConfirmButton().addClickHandler(new ClickHandler(){
//
//			@Override
//			public void onClick(ClickEvent event) {
//				dialog.getConfirmButton().setEnabled(false);
//				rpcService.submitDocumentWithData(dataModel.getRoot(), new AsyncCallback<DataSaveResult>(){
//					public void onFailure(
//							Throwable caught) {
//						Window.alert("Error starting Proposal workflow");
//						dialog.getConfirmButton().setEnabled(true);
//					}
//					public void onSuccess(
//							DataSaveResult result) {
//						//Update the model with the saved data
//						dataModel.setRoot(result.getValue());
//						
//						items.remove(wfStartWorkflowItem);
//						courseActionsDropDown.setItems(items);
//						dialog.hide();
//						dialog.getConfirmButton().setEnabled(true);
//						//Notify the user that the document was submitted
//						showSuccessDialog();
//					}
//				});
//				
//			}
//		});
 
		copyCourseAction = new KSMenuItemData("Copy Course", new ClickHandler(){
	        public void onClick(ClickEvent event) {
				Window.alert("Function not yet implemented");
	        	
//				rpcService.acknowledgeDocumentWithId(proposalId, new AsyncCallback<Boolean>(){
//					public void onFailure(
//							Throwable caught) {
//						Window.alert("Error acknowledging Proposal");
//					}
//					public void onSuccess(
//							Boolean result) {
//						if(result){
//							Window.alert("Proposal was acknowledged");
//							items.remove(wfAcknowledgeItem);
//							courseActionsDropDown.setItems(items);
//						}else{
//							Window.alert("Error acknowledging Proposal");
//						}
//					}
//					
//				});
	        }        
	    });

		retireCourseAction = new KSMenuItemData("Retire Course", new ClickHandler(){
	        public void onClick(ClickEvent event) {
				Window.alert("Function not yet implemented");
			}        
	    });
		modifyCourseAction = new KSMenuItemData("Modify Course", new ClickHandler(){
	        public void onClick(ClickEvent event) {
				Window.alert("Function not yet implemented");
			}        
	    });
	}

	private void updateActions(DataModel model){
		String courseCode = getCourseCodeFromModel(model);
		items.clear();
		
		copyCourseAction.setLabel("Copy " + courseCode );
		modifyCourseAction.setLabel("Modify " + courseCode );
		retireCourseAction.setLabel("Retire " + courseCode );
		
		items.add(copyCourseAction);
		items.add(modifyCourseAction);
		items.add(retireCourseAction);
		courseActionsDropDown.setItems(items);

		//FIXME: apply permissions
//		rpcService.getActionsRequested(courseId, new AsyncCallback<String>(){
//
//			public void onFailure(Throwable caught) {
//			}
//
//			public void onSuccess(String result) {
//				Window.alert("Permissions string="+result);
//				items.clear();
//				if(result.contains("S")){
//					items.add(wfStartWorkflowItem);
//				}
//				if(result.contains("W")){
//					items.add(wfWithdrawItem);
//				}
//				if(result.contains("A")){
//
//					items.add(wfApproveItem);
//					items.add(wfDisApproveItem);
//
//				}
//				if(result.contains("K")){
//					items.add(wfAcknowledgeItem);
//				}
//				
//				if(result.contains("F")){
//					items.add(wfFYIWorkflowItem);
//				}
//				
//				courseActionsDropDown.setItems(items);
//			}
//			
//		});
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
	 * @param courseCodePath
	 */
	public void setCourseCodePath(String courseCodePath) {
		this.courseCodePath = courseCodePath;
	}
	
	public void refresh(){
		updateActions(dataModel);
	}
}
