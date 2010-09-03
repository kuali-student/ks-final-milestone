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

package org.kuali.student.lum.lu.ui.course.client.controllers;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.application.ViewContext.IdType;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.MenuEditableSectionController;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.event.ActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionHandler;
import org.kuali.student.common.ui.client.event.SubmitProposalEvent;
import org.kuali.student.common.ui.client.event.SubmitProposalHandler;
import org.kuali.student.common.ui.client.event.ValidateRequestEvent;
import org.kuali.student.common.ui.client.event.ValidateRequestHandler;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.ModelProvider;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.WorkQueue;
import org.kuali.student.common.ui.client.mvc.WorkQueue.WorkItem;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.security.AuthorizationCallback;
import org.kuali.student.common.ui.client.security.RequiresAuthorization;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.buttongroups.OkGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.OkEnum;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.YesNoCancelEnum;
import org.kuali.student.common.ui.client.widgets.dialog.ButtonMessageDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.button.YesNoCancelGroup;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.rice.authorization.PermissionType;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.workflow.ui.client.widgets.CollaboratorTool;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowEnhancedController;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowUtilities;
import org.kuali.student.lum.common.client.lo.LUConstants;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseConfigurer;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.views.CourseReqSummaryHolder;
import org.kuali.student.lum.lu.ui.main.client.AppLocations;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;

/**
 * Controller for course proposal screens
 *
 * @author Kuali Student Team
 *
 */

public class CourseProposalController extends MenuEditableSectionController implements RequiresAuthorization, WorkflowEnhancedController{

	//RPC Services
	CreditCourseProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CreditCourseProposalRpcService.class);

	//Models
	private DataModel cluProposalModel = new DataModel();

    private WorkQueue modelRequestQueue;

    private WorkflowUtilities workflowUtil;

	private boolean initialized = false;
	private boolean isNew = false;

	private static final String UPDATED_KEY = "metaInfo/updateTime";
	private String proposalPath = "";

	private DateFormat df = DateFormat.getInstance();

	private BlockingTask initializingTask = new BlockingTask("Loading");
	private BlockingTask loadDataTask = new BlockingTask("Retrieving Data");

    public CourseProposalController(){
        super(CourseProposalController.class.getName());
        initialize();
        addStyleName("courseProposal");
    }

    @Override
    public void setViewContext(ViewContext viewContext) {
    	super.setViewContext(viewContext);
    	if(viewContext.getId() != null && !viewContext.getId().isEmpty()){
    		viewContext.setPermissionType(PermissionType.OPEN);
    	}
    	else{
    		viewContext.setPermissionType(PermissionType.INITIATE);
    	}
    }

    private void initialize() {
    	//TODO get from messages

        super.setDefaultModelId(CourseConfigurer.CLU_PROPOSAL_MODEL);
        super.registerModel(CourseConfigurer.CLU_PROPOSAL_MODEL, new ModelProvider<DataModel>() {

            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                if (modelRequestQueue == null){
                    modelRequestQueue = new WorkQueue();
                }

                WorkItem workItem = new WorkItem(){
                    @Override
                    public void exec(Callback<Boolean> workCompleteCallback) {
                        if (cluProposalModel.getRoot() == null || initialized == false){
                            initModel(callback, workCompleteCallback);
                        } else {
                            callback.onModelReady(cluProposalModel);
                            workCompleteCallback.exec(true);
                        }
                    }
                };
                modelRequestQueue.submit(workItem);

            }

        });
        super.registerModel("ComparisonModel", new ModelProvider<DataModel>() {
            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                callback.onModelReady(null);
            }
        });
        super.addApplicationEventHandler(ValidateRequestEvent.TYPE, new ValidateRequestHandler() {
            @Override
            public void onValidateRequest(final ValidateRequestEvent event) {
            	if(event.getFieldDescriptor().isDirty()){
            		setContentWarning("You have unsaved changes");
            	}
            }
        });

        addApplicationEventHandler(SaveActionEvent.TYPE, new SaveActionHandler(){
            public void doSave(SaveActionEvent saveAction) {
                GWT.log("CluProposalController received save action request.", null);
                doSaveAction(saveAction);
            }
        });

        addApplicationEventHandler(SubmitProposalEvent.TYPE, new SubmitProposalHandler(){
            public void onSubmitProposal() {
                GWT.log("CluProposalController received submit proposal request.", null);
                CourseProposalController.this.updateModel();
            }
        });
    }

    private void initModel(final ModelRequestCallback<DataModel> callback, Callback<Boolean> workCompleteCallback){
    	if(getViewContext().getIdType() == IdType.DOCUMENT_ID){
            getCluProposalFromWorkflowId(callback, workCompleteCallback);
        } else if (getViewContext().getIdType() == IdType.KS_KEW_OBJECT_ID){
            getCluProposalFromProposalId(getViewContext().getId(), callback, workCompleteCallback);
        } else if (getViewContext().getIdType() == IdType.COPY_OF_OBJECT_ID){
            getNewProposalWithCopyOfClu(callback, workCompleteCallback);
        } else{
            createNewCluProposalModel(callback, workCompleteCallback);
        }
    }

    private void getCurrentModel(final ModelRequestCallback<DataModel> callback, Callback<Boolean> workCompleteCallback){
    	if (cluProposalModel.getRoot() != null && cluProposalModel.getRoot().size() > 0){
        	String id = cluProposalModel.get(CourseConfigurer.PROPOSAL_PATH+"/id");
        	if(id != null){
        		getCluProposalFromProposalId(id, callback, workCompleteCallback);
        	}
        	else{
        		initModel(callback, workCompleteCallback);
        	}
    	}
    	else{
    		initModel(callback, workCompleteCallback);
    	}
    }

    private KSButton getSaveButton(){
        return new KSButton("Save & Continue", new ClickHandler(){
                    public void onClick(ClickEvent event) {
                        fireApplicationEvent(new SaveActionEvent(true));
                    }
                });
    }

    private void init(final Callback<Boolean> onReadyCallback) {
    	if (initialized) {
    		onReadyCallback.exec(true);
    	} else {
    		initialized = true;
    		KSBlockingProgressIndicator.addTask(initializingTask);
    		this.setContentTitle("New Course (Proposal)");
    		this.setName("New Course (Proposal)");
    		setContentWarning("");
    		String idType = null;
    		String viewContextId = null;
    		// The switch was added due to the way permissions currently work.
    		// For a new Create Course Proposal or Modify Course we send nulls so that permissions are not checked.
    		if(getViewContext().getIdType() != null){
                idType = getViewContext().getIdType().toString();
                viewContextId = getViewContext().getId();
                if(getViewContext().getIdType()==ViewContext.IdType.COPY_OF_OBJECT_ID){
                	viewContextId = null;
                }

    		}
	        cluProposalRpcServiceAsync.getMetadata(idType, viewContextId, new KSAsyncCallback<Metadata>(){

	        	public void handleFailure(Throwable caught) {
	        		initialized = false;
                	onReadyCallback.exec(false);
                	KSBlockingProgressIndicator.removeTask(initializingTask);
                    throw new RuntimeException("Failed to get model definition.", caught);
                }

                public void onSuccess(Metadata result) {
                	DataModelDefinition def = new DataModelDefinition(result);
                    cluProposalModel.setDefinition(def);
                    init(def);
                    onReadyCallback.exec(true);
                    KSBlockingProgressIndicator.removeTask(initializingTask);
                }
	          });
    	}
    }

    private void init(DataModelDefinition modelDefinition){
    	CourseConfigurer cfg = GWT.create(CourseConfigurer.class);
    	proposalPath = cfg.getProposalPath();
        workflowUtil = new WorkflowUtilities(this,proposalPath, createOnWorkflowSubmitSuccessHandler());
        workflowUtil.setRequiredFieldPaths(cfg.getWorkflowRequiredFields());
        workflowUtil.requestAndSetupModel();

    	cfg.setModelDefinition(modelDefinition);
    	cfg.configure(this);

        addCommonButton(LUConstants.COURSE_SECTIONS, getSaveButton());
    }

    private CloseHandler<KSLightBox> createOnWorkflowSubmitSuccessHandler() {
    	CloseHandler<KSLightBox> handler = new CloseHandler<KSLightBox>(){
			@Override
			public void onClose(CloseEvent<KSLightBox> event) {
				//Reload the lum main entrypoint
				Application.navigate(AppLocations.Locations.CURRICULUM_MANAGEMENT.getLocation());
			}
    	};
		return handler;
	}

	/**
     * @see org.kuali.student.common.ui.client.mvc.Controller#getViewsEnum()
     */
    @Override
    public Class<? extends Enum<?>> getViewsEnum() {
        return CourseConfigurer.CourseSections.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void requestModel(Class modelType, final ModelRequestCallback callback) {
        if(modelType == ReferenceModel.class){
        	if (cluProposalModel != null){
        		ReferenceModel ref = new ReferenceModel();

        		if(cluProposalModel.get(CourseConfigurer.PROPOSAL_PATH) != null){
            		ref.setReferenceId((String)cluProposalModel.get(CourseConfigurer.PROPOSAL_PATH+"/id"));
        		} else {
        			ref.setReferenceId(null);
        		}

        		ref.setReferenceTypeKey(CourseConfigurer.PROPOSAL_REFERENCE_TYPE_KEY);
        		ref.setReferenceType(CourseConfigurer.PROPOSAL_REFERENCE_OBJECT_TYPE);
        		ref.setReferenceState(getViewContext().getState());

        		callback.onModelReady(ref);
        	}
        } else if(modelType == CollaboratorTool.CollaboratorModel.class){
        	CollaboratorTool.CollaboratorModel collaboratorModel = new CollaboratorTool.CollaboratorModel();
        	String proposalId=null;
        	if(cluProposalModel!=null && cluProposalModel.get(CourseConfigurer.PROPOSAL_PATH)!=null){
        		proposalId=cluProposalModel.get(CourseConfigurer.PROPOSAL_PATH+"/id" );
        	}
        	collaboratorModel.setDataId(proposalId);
        	callback.onModelReady(collaboratorModel);

        }else if (modelType == LuData.class){
        	requestModel(CourseConfigurer.CLU_PROPOSAL_MODEL, callback);
        } else {
            super.requestModel(modelType, callback);
        }

    }

    @SuppressWarnings("unchecked")
    private void getCluProposalFromWorkflowId(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
        KSBlockingProgressIndicator.addTask(loadDataTask);
        workflowUtil.getDataIdFromWorkflowId(getViewContext().getId(), new KSAsyncCallback<String>(){
			@Override
			public void handleFailure(Throwable caught) {
                Window.alert("Error loading Proposal from Workflow Document: "+caught.getMessage());
                createNewCluProposalModel(callback, workCompleteCallback);
                KSBlockingProgressIndicator.removeTask(loadDataTask);
			}

			@Override
			public void onSuccess(String proposalId) {
				KSBlockingProgressIndicator.removeTask(loadDataTask);
				getCluProposalFromProposalId(proposalId, callback, workCompleteCallback);
			}
        });
    }

    @SuppressWarnings("unchecked")
    private void getCluProposalFromProposalId(String id, final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
    	KSBlockingProgressIndicator.addTask(loadDataTask);
    	cluProposalRpcServiceAsync.getData(id, new KSAsyncCallback<Data>(){

			@Override
			public void handleFailure(Throwable caught) {
                Window.alert("Error loading Proposal: "+caught.getMessage());
                createNewCluProposalModel(callback, workCompleteCallback);
                KSBlockingProgressIndicator.removeTask(loadDataTask);
			}

			@Override
			public void onSuccess(Data result) {
				cluProposalModel.setRoot(result);
		        setProposalHeaderTitle();
		        setLastUpdated();
				callback.onModelReady(cluProposalModel);
		        workCompleteCallback.exec(true);
		        KSBlockingProgressIndicator.removeTask(loadDataTask);
			}

    	});
    }

    @SuppressWarnings("unchecked")
    private void getNewProposalWithCopyOfClu(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
    	KSBlockingProgressIndicator.addTask(loadDataTask);
        cluProposalRpcServiceAsync.getNewProposalWithCopyOfClu(getViewContext().getId(), new KSAsyncCallback<Data>(){

            @Override
            public void handleFailure(Throwable caught) {
                Window.alert("Error loading Proposal: "+caught.getMessage());
                createNewCluProposalModel(callback, workCompleteCallback);
                KSBlockingProgressIndicator.removeTask(loadDataTask);
            }

            @Override
            public void onSuccess(Data result) {
                cluProposalModel.setRoot(result);
		        setProposalHeaderTitle();
		        setLastUpdated();
                callback.onModelReady(cluProposalModel);
                workCompleteCallback.exec(true);
                KSBlockingProgressIndicator.removeTask(loadDataTask);
            }

        });
    }

    @SuppressWarnings("unchecked")
    private void createNewCluProposalModel(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
        cluProposalModel.setRoot(new LuData());
        isNew = true;
        setProposalHeaderTitle();
        setLastUpdated();
        callback.onModelReady(cluProposalModel);
        workCompleteCallback.exec(true);
    }


    public void doSaveAction(final SaveActionEvent saveActionEvent){
        requestModel(new ModelRequestCallback<DataModel>() {
            @Override
            public void onModelReady(DataModel model) {
        		/* This is to update model with data from current section only */
                //getCurrentView().updateModel();
                CourseProposalController.this.updateModel();

                if (isStartViewShowing()){
                	//This call required so fields in start section, which also appear in
                	//other sections don't get overridden from updateModel call above.
                	getStartPopupView().updateModel();
                }

            	model.validate(new Callback<List<ValidationResultInfo>>() {
                    @Override
                    public void exec(List<ValidationResultInfo> result) {

                    	boolean isSectionValid = isValid(result, true);

                    	if(isSectionValid){
                            if (startSectionRequired()){
                                showStartPopup(NO_OP_CALLBACK);
                                saveActionEvent.doActionComplete();
                            }
                            else{
	                            saveProposalClu(saveActionEvent);
                            }
                    	}
                    	else{
                    		saveActionEvent.doActionComplete();
                    		Window.alert("Save failed.  Please check fields for errors.");
                    	}

                    }
                });
            }

            @Override
            public void onRequestFail(Throwable cause) {
            	saveActionEvent.doActionComplete();
                GWT.log("Unable to retrieve model for validation and save", cause);
            }

        });

    }

    public boolean startSectionRequired(){
        String proposalId = cluProposalModel.get(CourseConfigurer.PROPOSAL_PATH+"/id");

        //Defaulting the proposalTitle to courseTitle, this way course data gets set and assembler doesn't
        //complain. This may not be the correct approach.
        String proposalTitle = cluProposalModel.get(CourseConfigurer.PROPOSAL_TITLE_PATH);
        if (proposalTitle == null || proposalTitle.isEmpty()){
            String courseTitle = cluProposalModel.get(CourseConfigurer.COURSE_TITLE_PATH);
            cluProposalModel.set(QueryPath.parse(CourseConfigurer.PROPOSAL_TITLE_PATH), courseTitle);
        }

    	return proposalId==null && !CourseProposalController.this.isStartViewShowing();
    }

    public void saveProposalClu(final SaveActionEvent saveActionEvent){
        final KSLightBox saveWindow = new KSLightBox();
        saveWindow.removeCloseLink();
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
               	 saveMessage.setText("Save Failed!  Please try again. ");
                 buttonGroup.getButton(OkEnum.Ok).setEnabled(true);
                 saveActionEvent.doActionComplete();
			}

        };
        try {
            cluProposalRpcServiceAsync.saveData(cluProposalModel.getRoot(), new KSAsyncCallback<DataSaveResult>(){
                public void handleFailure(Throwable caught) {
                   saveFailedCallback.exec(caught);
                }

                public void onSuccess(DataSaveResult result) {
                	// FIXME [KSCOR-225] needs to check validation results and display messages if validation failed
                	if(result.getValidationResults()!=null && !result.getValidationResults().isEmpty()){
                		isValid(result.getValidationResults(), false, true);
                	    saveActionEvent.setGotoNextView(false);
               	    	if (saveActionEvent.isAcknowledgeRequired()){
	                        saveMessage.setText("Save Unsuccessful. There were validation errors.");
	                        buttonGroup.getButton(OkEnum.Ok).setEnabled(true);
	                    } else {
	                        saveWindow.hide();
	                        saveActionEvent.doActionComplete();
	                    }
                	}else{
                		saveActionEvent.setSaveSuccessful(true);
                		isNew = false;
	    				cluProposalModel.setRoot(result.getValue());
	    	            View currentView = getCurrentView();
	    				if (currentView instanceof SectionView){
	    					((SectionView)currentView).updateView(cluProposalModel);
	    					((SectionView) currentView).resetDirtyFlags();
	    	            }
	    				if (saveActionEvent.isAcknowledgeRequired()){
	                        saveMessage.setText("Save Successful");
	                        buttonGroup.getButton(OkEnum.Ok).setEnabled(true);
	                    } else {
	                        saveWindow.hide();
	                        saveActionEvent.doActionComplete();
	                    }
	    				ViewContext context = CourseProposalController.this.getViewContext();
	    				context.setId((String)cluProposalModel.get(proposalPath+"/id"));
	    				context.setIdType(IdType.KS_KEW_OBJECT_ID);
	    				workflowUtil.refresh();
	    				
	    				setProposalHeaderTitle();
	    				setLastUpdated();
	    				HistoryManager.logHistoryChange();
	    				if(saveActionEvent.gotoNextView()){
	    					CourseProposalController.this.showNextViewOnMenu();
	    				}
                	}
                }
            });
        } catch (Exception e) {
        	saveFailedCallback.exec(e);
        }

    }

    public void setLastUpdated(){
    	Date lastUpdated = (Date)cluProposalModel.get(UPDATED_KEY);
    	if(lastUpdated != null){
    		setContentInfo("Last Updated: " + df.format(lastUpdated));
    	}
    	else{
    		setContentInfo("");
    	}
    }

    @Override
	public void beforeShow(final Callback<Boolean> onReadyCallback){
		init(onReadyCallback);
	}
    
   @Override
   public void showDefaultView(Callback<Boolean> onReadyCallback) {
	   if(isNew){
		   super.showFirstView(onReadyCallback);
	   }
	   else{
		   super.showDefaultView(onReadyCallback);
	   }
   }

	@Override
    public void setParentController(Controller controller) {
        super.setParentController(controller);
        if (CourseReqSummaryHolder.getView() != null) {
            CourseReqSummaryHolder.getView().setTheController(controller);
            CourseReqSummaryHolder.getView().redraw();
        }
    }

	@Override
	public void checkAuthorization(final PermissionType permissionType, final AuthorizationCallback authCallback) {
		Map<String,String> attributes = new HashMap<String,String>();
//		if (StringUtils.isNotBlank(getViewContext().getId())) {
		GWT.log("Attempting Auth Check.", null);
		if ( (getViewContext().getId() != null) && (!"".equals(getViewContext().getId())) ) {
			attributes.put(getViewContext().getIdType().toString(), getViewContext().getId());
		}
    	cluProposalRpcServiceAsync.isAuthorized(permissionType, attributes, new KSAsyncCallback<Boolean>(){

			@Override
			public void handleFailure(Throwable caught) {
				authCallback.isNotAuthorized("Error checking authorization.");
				GWT.log("Error checking proposal authorization.", caught);
                Window.alert("Error Checking Proposal Authorization: "+caught.getMessage());
			}

			@Override
			public void onSuccess(Boolean result) {
				GWT.log("Succeeded checking auth for permission type '" + permissionType + "' with result: " + result, null);
				if (Boolean.TRUE.equals(result)) {
					authCallback.isAuthorized();
				}
				else {
					authCallback.isNotAuthorized("User is not authorized: " + permissionType);
				}
			}
    	});
	}

	@Override
	public boolean isAuthorizationRequired() {
		return true;
	}

	@Override
	public void setAuthorizationRequired(boolean required) {
		throw new UnsupportedOperationException();
	}

    protected void setProposalHeaderTitle(){
    	StringBuffer sb = new StringBuffer();
    	if (cluProposalModel.get("course/copyOfCourseId") != null){
    		sb.append(cluProposalModel.get("course/courseCode"));
    		sb.append(" - ");
    		sb.append(cluProposalModel.get("course/transcriptTitle"));
    		sb.append(" (Proposed Modification)");
    	} else if (cluProposalModel.get(CourseConfigurer.PROPOSAL_TITLE_PATH) != null){
    		sb.append(cluProposalModel.get(CourseConfigurer.PROPOSAL_TITLE_PATH));
    		sb.append(" (Proposal)");
    	}
    	else{
    		sb.append("New Course (Proposal)");
    	}

    	this.setContentTitle(sb.toString());
    	this.setName(sb.toString());
    }

	@Override
	public WorkflowUtilities getWfUtilities() {
		return workflowUtil;
	}

	@Override
	public void beforeViewChange(final Callback<Boolean> okToChange) {
		//We do this check here because theoretically the subcontroller views
		//will display their own messages to the user to give them a reason why the view
		//change has been cancelled, otherwise continue to check for reasons not to change
		//with this controller
		super.beforeViewChange(new Callback<Boolean>(){

			@Override
			public void exec(Boolean result) {
				if(result){
					if(getCurrentView() instanceof SectionView && ((SectionView)getCurrentView()).isDirty()){
						ButtonGroup<YesNoCancelEnum> buttonGroup = new YesNoCancelGroup();
						final ButtonMessageDialog<YesNoCancelEnum> dialog = new ButtonMessageDialog<YesNoCancelEnum>("Warning", "You may have unsaved changes.  Save changes?", buttonGroup);
						buttonGroup.addCallback(new Callback<YesNoCancelEnum>(){

							@Override
							public void exec(YesNoCancelEnum result) {
								switch(result){
									case YES:
										dialog.hide();
										final SaveActionEvent e = new SaveActionEvent();
										e.setActionCompleteCallback(new ActionCompleteCallback(){

											@Override
											public void onActionComplete(
													ActionEvent action) {
												if(e.isSaveSuccessful()){
													okToChange.exec(true);
												}
												else{
													okToChange.exec(false);
												}
											}
											
										});
										fireApplicationEvent(e);
										break;
									case NO:
										//Force a model request from server
										getCurrentModel(new ModelRequestCallback<DataModel>(){

											@Override
											public void onModelReady(DataModel model) {
												if (getCurrentView()instanceof Section){
							    					((Section) getCurrentView()).resetFieldInteractionFlags();
												}
												okToChange.exec(true);
												dialog.hide();
											}

											@Override
											public void onRequestFail(Throwable cause) {
												//TODO Is this correct... do we want to stop view change if we can't restore the data?  Possibly traps the user
												//if we don't it messes up saves, possibly warn the user that it failed and continue?
												okToChange.exec(false);
												dialog.hide();
												GWT.log("Unable to retrieve model for data restore on view change with no save", cause);
											}},
											NO_OP_CALLBACK);

										break;
									case CANCEL:
										okToChange.exec(false);
										dialog.hide();
										break;
								}
							}
						});
						dialog.show();
					}
					else{
						okToChange.exec(true);
					}
				}
				else{
					okToChange.exec(false);
				}
			}
		});
	}
}