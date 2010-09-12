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
package org.kuali.student.lum.lu.ui.course.client.configuration.course;

import java.util.List;

import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.ui.client.configurable.mvc.TabbedSectionLayout;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionHandler;
import org.kuali.student.common.ui.client.event.ValidateResultEvent;
import org.kuali.student.common.ui.client.event.ValidateResultHandler;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.WorkQueue;
import org.kuali.student.common.ui.client.mvc.WorkQueue.WorkItem;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicator;
import org.kuali.student.common.ui.client.widgets.buttongroups.OkGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.OkEnum;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.LuConfigurer;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.service.DataSaveResult;
import org.kuali.student.lum.lu.ui.course.client.widgets.Collaborators;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager.LUMViews;
import org.kuali.student.lum.lu.ui.main.client.events.ChangeViewStateEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class CourseProposalController extends TabbedSectionLayout { 
    private final DataModel cluProposalModel = new DataModel(); 
    private Collaborators.CollaboratorModel collaboratorModel;
    
    private WorkQueue modelRequestQueue;

	private String docId = null;
	private String proposalType = null;
	private String cluType = null;
	private SaveActionEvent currentSaveEvent = null;
    private boolean processingSave = false;
    private String proposalId = null;
	
	private final String CLU_PROPOSAL_ID_KEY   = "proposal/id";
	private final String CLU_PROPOSAL_NAME_KEY = "proposal/title";

	private String PROPOSAL_STATE = "draft.private";	
	private final String CLU_STATE = "draft";
	
	private final String REFERENCE_TYPE = "referenceType.clu";
	private boolean initialized = false;
	CluProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CluProposalRpcService.class);
	
	final KSLightBox progressWindow = new KSLightBox();

    
        
    public CourseProposalController(){
        super();
    }
    public CourseProposalController(String proposalType, String cluType){
        super();
    	this.proposalType = proposalType;
    	this.cluType = cluType;        
    }
    public CourseProposalController(String proposalType, String cluType, String docId) {
    	super();
    	this.docId = docId;   	
    	this.proposalType = proposalType;
    	this.cluType = cluType;
	}
    
    private KSButton getSaveButton(){
        return new KSButton("Save", new ClickHandler(){
                    public void onClick(ClickEvent event) {
                        fireApplicationEvent(new SaveActionEvent());
                    }
                });
    }
 
        
    private KSButton getQuitButton(){
        return new KSButton("Quit", new ClickHandler(){
                    public void onClick(ClickEvent event) {
                        Controller parentController = CourseProposalController.this.getParentController(); 
                        parentController.fireApplicationEvent(new ChangeViewStateEvent<LUMViews>(LUMViews.HOME_MENU, event));
                    }
                });       
    }
    
    private void init(final Callback<Boolean> onReadyCallback) {
    	KSProgressIndicator progressInd = new KSProgressIndicator();
    	progressInd.setText("Loading");
    	progressInd.show();
    	progressWindow.setWidget(progressInd);

    	if (initialized) {
    		onReadyCallback.exec(true);
    	} else {
    		progressWindow.show();
	        cluProposalRpcServiceAsync.getCreditCourseProposalMetadata( 
	                new AsyncCallback<Metadata>(){
	
	                    @Override
	                    public void onFailure(Throwable caught) {
	                    	onReadyCallback.exec(false);
	                    	progressWindow.hide();
	                        throw new RuntimeException("Failed to get model definition.", caught);                        
	                    }
	
	                    @Override
	                    public void onSuccess(Metadata result) {
	                    	DataModelDefinition def = new DataModelDefinition(result);
	                        cluProposalModel.setDefinition(def);
	                        init(def);
	                        initialized = true;
	                        onReadyCallback.exec(true);
	                        progressWindow.hide();
	                    }                
	            });
	        
    	}
    }
    private void init(DataModelDefinition modelDefinition){
        
        CourseConfigurer cfg = new CourseConfigurer();
        cfg.setModelDefinition(modelDefinition);
        cfg.configureCourseProposal(this);
        
/*
        else if (proposalType == LUConstants.PROPOSAL_TYPE_PROGRAM_CREATE) { 
        	proposalType = LUConstants.PROPOSAL_TYPE_COURSE_CREATE; //FIXME: remove when we have programs in dictionary...
        	cluType = LUConstants.CLU_TYPE_CREDIT_COURSE; //FIXME: remove when we have programs in dictionary...
        	CourseConfigurer.configureProgramProposal(this, objectKey, typeKey, stateKey);
        }
*/        
        
        if (!initialized){
	        addButton("Edit Proposal", getSaveButton());
	        addButton("Edit Proposal", getQuitButton());
	        addButton("Summary", getQuitButton());
	        
	        addApplicationEventHandler(SaveActionEvent.TYPE, new SaveActionHandler(){
	            public void doSave(SaveActionEvent saveAction) {
	                GWT.log("CluProposalController received save action request.", null);
	                doSaveAction(saveAction);
	            }            
	        });
	        
	        addApplicationEventHandler(ValidateResultEvent.TYPE, new ValidateResultHandler() {
	            @Override
	            public void onValidateResult(ValidateResultEvent event) {
	            	if(processingSave){
	            		List<ValidationResultContainer> list = event.getValidationResult();
	            		ErrorLevel errorLevel = checkForErrors(list);
	            		if(errorLevel.equals(ErrorLevel.ERROR)){
	            			//TODO replace with a ks modal
	            			Window.alert("Validation failed.  The proposal could not be saved.  Please check fields for errors.");
	            		}
	            		else if(errorLevel.equals(ErrorLevel.WARN)){
	            			//TODO do something else for warning level?
	            			saveProposalClu(currentSaveEvent);
	            		}
	            		else{
	            			saveProposalClu(currentSaveEvent);
	            		}
	            		processingSave = false;
	            		currentSaveEvent = null;
	            	}
	            }
	        });
        }
        
        initialized = true;
    }
        
    /**
     * @see org.kuali.student.common.ui.client.mvc.Controller#getViewsEnum()
     */
    @Override
    public Class<? extends Enum<?>> getViewsEnum() {
        return LuConfigurer.LuSections.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void requestModel(String modelId, final ModelRequestCallback modelRequestCallback) {
        if (modelRequestQueue == null){
            modelRequestQueue = new WorkQueue();
        }

        if (modelId.equals(CourseConfigurer.CLU_PROPOSAL_MODEL)){
            WorkItem workItem = new WorkItem(){
                @Override
                public void exec(Callback<Boolean> workCompleteCallback) {
                    if (cluProposalModel.getRoot() == null || cluProposalModel.getRoot().size() == 0){
                        if(docId!=null){
                            getCluProposalFromWorkflowId(modelRequestCallback, workCompleteCallback);
                        } else if (proposalId != null){
                            getCluProposalFromProposalId(modelRequestCallback, workCompleteCallback);
                        } else{
                            createNewCluProposalModel(modelRequestCallback, workCompleteCallback);
                        }                
                    } else {
                        modelRequestCallback.onModelReady(cluProposalModel);
                        workCompleteCallback.exec(true);
                    }
                }               
            };
            modelRequestQueue.submit(workItem);
        } else{
            super.requestModel(modelId, modelRequestCallback);
        }
    }

    
    @SuppressWarnings("unchecked")
    @Override
    public void requestModel(Class modelType, final ModelRequestCallback callback) {
        if(modelType == ReferenceModel.class){
        	if (cluProposalModel != null){
        		ReferenceModel ref = new ReferenceModel();

        		//FIXME: test code
        		if(cluProposalModel.get("proposal/id") != null){
            		ref.setReferenceId((String)cluProposalModel.get("proposal/id"));
        		}
        		else{
        			ref.setReferenceId(null);
        		}
        		
        		ref.setReferenceTypeKey(REFERENCE_TYPE);
        		ref.setReferenceType(cluType);
        		ref.setReferenceState(CLU_STATE);
        		
        		callback.onModelReady(ref);
        	}
        } else if(modelType == Collaborators.CollaboratorModel.class){
        	//Update the collabmodel with info from the CluProposal Model
        	//Create a new one if it does not yet exist
        	if(null==collaboratorModel){
        		collaboratorModel = new Collaborators.CollaboratorModel();
        	}
        	String proposalId="";
        	if(cluProposalModel!=null && cluProposalModel.get(CLU_PROPOSAL_ID_KEY)!=null){
        		proposalId=cluProposalModel.get(CLU_PROPOSAL_ID_KEY);
        	}
        	collaboratorModel.setProposalId(proposalId);    
        	callback.onModelReady(collaboratorModel);
        } else {
            super.requestModel(modelType, callback);
        }       
    }
    
    @SuppressWarnings("unchecked")        
    private void getCluProposalFromWorkflowId(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
       
        cluProposalRpcServiceAsync.getCluProposalFromWorkflowId(docId, new AsyncCallback<Data>(){

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Error loading Proposal from docId: "+docId+". "+caught.getMessage());
                createNewCluProposalModel(callback, workCompleteCallback);
                progressWindow.hide();

            }

            @Override
            public void onSuccess(Data result) {
				cluProposalModel.setRoot(result);
		        callback.onModelReady(cluProposalModel);
		        workCompleteCallback.exec(true);
		        progressWindow.hide();              
            }
            
        });

    }
    
    @SuppressWarnings("unchecked")    
    private void getCluProposalFromProposalId(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
    	progressWindow.show();
    	cluProposalRpcServiceAsync.getCreditCourseProposal(proposalId, new AsyncCallback<Data>(){

			@Override
			public void onFailure(Throwable caught) {
                Window.alert("Error loading Proposal: "+caught.getMessage());
                createNewCluProposalModel(callback, workCompleteCallback);
                progressWindow.hide();
			}

			@Override
			public void onSuccess(Data result) {
				cluProposalModel.setRoot(result);
		        callback.onModelReady(cluProposalModel);
		        workCompleteCallback.exec(true);
		        progressWindow.hide();
			}
    		
    	});
    }
    
    @SuppressWarnings("unchecked")
    private void createNewCluProposalModel(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
        cluProposalModel.setRoot(new Data());
        callback.onModelReady(cluProposalModel);
        workCompleteCallback.exec(true);            
    }

    
    public void doSaveAction(SaveActionEvent saveActionEvent){       
        String proposalName = cluProposalModel.get(CLU_PROPOSAL_NAME_KEY);
        currentSaveEvent = saveActionEvent;
        if (proposalName == null){
            showStartSection(NO_OP_CALLBACK);
        } else {
            getStartSection().updateModel();
            
            getCurrentView().updateModel();
            
            this.updateModel();
            
            saveProposalClu(saveActionEvent);
//            processingSave=true;
//            View v = getCurrentView();
//        	if(v instanceof SectionView){
//        		((SectionView) v).setFieldHasHadFocusFlags(true);
//        		this.validate((SectionView)v);
//        	}
        }       
    }
    
    public void saveProposalClu(final SaveActionEvent saveActionEvent){
        final KSLightBox saveWindow = new KSLightBox();
        final KSLabel saveMessage = new KSLabel(saveActionEvent.getMessage() + "...");
        final OkGroup buttonGroup = new OkGroup(new Callback<OkEnum>(){
                
                @Override
                public void exec(OkEnum result) {
                    saveWindow.hide();
                    saveActionEvent.doActionComplete();                
                }
            });

        buttonGroup.setWidth("250px");
        buttonGroup.getButton(OkEnum.Ok).setEnabled(false);
        buttonGroup.setContent(saveMessage);

        
        if (saveActionEvent.isAcknowledgeRequired()){
            saveWindow.setWidget(buttonGroup);
        } else {
            saveWindow.setWidget(saveMessage);
        }
        saveWindow.show();
        
        final Callback<Throwable> saveFailedCallback = new Callback<Throwable>() {

			@Override
			public void exec(Throwable caught) {
				 GWT.log("Save Failed.", caught);
                 saveWindow.setWidget(buttonGroup);
                 saveMessage.setText("Save Failed!  Please Try Again.");
                 buttonGroup.getButton(OkEnum.Ok).setEnabled(true);   
			}
        	
        };
        try {
//	        if(cluProposalModel.get().get("proposal/id") == null){
	        	// FIXME wilj: find out if/why curriculum oversight retrieving/saving wrong org and admin org is not saving at all
	            cluProposalRpcServiceAsync.saveCreditCourseProposal(cluProposalModel.getRoot(), new AsyncCallback<DataSaveResult>(){
	                public void onFailure(Throwable caught) {
	                   saveFailedCallback.exec(caught);                 
	                }
	
	                public void onSuccess(DataSaveResult result) {
	                	// FIXME needs to check validation results and display messages if validation failed
	    				cluProposalModel.setRoot(result.getValue());
	                    if (saveActionEvent.isAcknowledgeRequired()){
	                        saveMessage.setText("Save Successful");
	                        buttonGroup.getButton(OkEnum.Ok).setEnabled(true);
	                    } else {
	                        saveWindow.hide();
	                        saveActionEvent.doActionComplete();                        
	                    }                    
	                }
	            });
//	        }
        } catch (Exception e) {
        	saveFailedCallback.exec(e);
        }

    }

    

    public String getDocId() {
        return docId;
    }


    public void setDocId(String docId) {
        this.docId = docId;
        this.proposalId = null;
        this.cluProposalModel.setRoot(new Data());
    }


    public String getProposalId() {
        return proposalId;
    }


    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
        this.docId = null;
        this.cluProposalModel.setRoot(new Data());        
    }
    
    public void clear(String proposalType, String cluType){
        super.clear();
        this.proposalType = proposalType;
        this.cluType = cluType;
        if (cluProposalModel != null){
            this.cluProposalModel.setRoot(new Data());            
        }
        this.setModelDTO(null, null);
        this.docId = null;
        this.proposalId = null;
    }
    
	
	@Override
	public void showDefaultView(final Callback<Boolean> onReadyCallback) {
		init(new Callback<Boolean>() {

			@Override
			public void exec(Boolean result) {
				if (result) {
					doShowDefaultView(onReadyCallback);
				} else {
					onReadyCallback.exec(false);
				}
			}
		});
	}
	
	private void doShowDefaultView(final Callback<Boolean> onReadyCallback) {
		super.showDefaultView(onReadyCallback);
	}
    
    
}