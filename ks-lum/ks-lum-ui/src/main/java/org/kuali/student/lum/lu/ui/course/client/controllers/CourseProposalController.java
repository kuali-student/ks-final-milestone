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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r1.common.rice.authorization.PermissionType;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r1.core.statement.dto.StatementTypeInfo;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.MenuEditableSectionController;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.event.ActionEvent;
import org.kuali.student.common.ui.client.event.ContentDirtyEvent;
import org.kuali.student.common.ui.client.event.ContentDirtyEventHandler;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionHandler;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.HasCrossConstraints;
import org.kuali.student.common.ui.client.mvc.ModelProvider;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.WorkQueue;
import org.kuali.student.common.ui.client.mvc.WorkQueue.WorkItem;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.security.AuthorizationCallback;
import org.kuali.student.common.ui.client.security.RequiresAuthorization;
import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcServiceAsync;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.common.ui.client.util.ExportUtils;
import org.kuali.student.common.ui.client.util.WindowTitleUtils;
import org.kuali.student.common.ui.client.validator.ValidatorClientUtils;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.YesNoCancelEnum;
import org.kuali.student.common.ui.client.widgets.dialog.ButtonMessageDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.button.YesNoCancelGroup;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableSection;
import org.kuali.student.common.ui.shared.IdAttributes;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowEnhancedNavController;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowUtilities;
import org.kuali.student.lum.common.client.helpers.RecentlyViewedHelper;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.r1.lum.lu.LUConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseProposalConfigurer;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseProposalConfigurer.CourseSections;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsDataModel;
import org.kuali.student.lum.lu.ui.course.client.requirements.HasRequirements;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Controller for course proposal screens.  This controller controls all functions of the course proposal process
 * and contains the data model and is responsible for retrieving its data and metadata from the server. In
 * addition, this controller is responsible for course proposal save events and updating its ui accordingly.
 * 
 *
 * @author Kuali Student Team
 *
 */

public class CourseProposalController extends MenuEditableSectionController implements RequiresAuthorization, WorkflowEnhancedNavController, HasRequirements {

	//RPC Services
	protected CreditCourseProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CreditCourseProposalRpcService.class);
	protected CourseRpcServiceAsync courseServiceAsync = GWT.create(CourseRpcService.class);
	//Models
	protected final DataModel cluProposalModel = new DataModel("Proposal");
	protected final DataModel comparisonModel = new DataModel("Original Course");

	protected CourseProposalConfigurer cfg;
	
	protected WorkQueue modelRequestQueue;

    protected WorkflowUtilities workflowUtil;

    protected boolean initialized = false;
	protected boolean isNew = false;

	private static final String UPDATED_KEY = "metaInfo/updateTime";
	private static final String VERSION_KEY  = "versionInfo/versionedFromId";
    private static final String MSG_GROUP = "course";
	
	protected String currentDocType = LUConstants.PROPOSAL_TYPE_COURSE_CREATE;
	protected String proposalPath = "";
	protected String currentTitle;

	private final DateFormat df = DateFormat.getInstance();

	private final BlockingTask initializingTask = new BlockingTask("Loading");
	protected final BlockingTask loadDataTask = new BlockingTask("Retrieving Data");
	private final BlockingTask saving = new BlockingTask("Saving");

	protected CourseRequirementsDataModel reqDataModel;
	protected CourseRequirementsDataModel reqDataModelComp;
   
    public CourseProposalController(){
        super();
        initializeController();
    }

    protected void initializeController() {
    	cfg = GWT.create(CourseProposalConfigurer.class);
   		proposalPath = cfg.getProposalPath();
   		workflowUtil = new WorkflowUtilities(CourseProposalController.this, proposalPath, "Proposal Actions",
   				CourseProposalConfigurer.CourseSections.WF_APPROVE_DIALOG,"", cfg.getModelId());//TODO make msg
   		cfg.setState(DtoConstants.STATE_DRAFT);
   		
   		//Add an extra menu item to copy the proposal to a new proposal.
        workflowUtil.getAdditionalItems().add(new KSMenuItemData(this.getMessage("cluCopyItem"), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (getViewContext() != null && getViewContext().getId() != null && !getViewContext().getId().isEmpty()) {
                    getViewContext().setId((String) cluProposalModel.get(cfg.getProposalPath() + "/id"));
                    getViewContext().setIdType(IdType.COPY_OF_KS_KEW_OBJECT_ID);
                    getViewContext().getAttributes().remove(StudentIdentityConstants.DOCUMENT_TYPE_NAME);
                    cluProposalModel.resetRoot(); // Reset the root so that the model can be reloaded from the copied proposal.
                }
                HistoryManager.navigate("/HOME/CURRICULUM_HOME/COURSE_PROPOSAL", getViewContext());
            }
        }));
   		
   		super.setDefaultModelId(cfg.getModelId());
   		registerModelsAndHandlers();
   		
        addStyleName("courseProposal");
        setViewContext(getViewContext());
    }
    
    protected void registerModelsAndHandlers(){
        reqDataModel = new CourseRequirementsDataModel(this);
        reqDataModelComp = new CourseRequirementsDataModel(this);
    	
        super.registerModel(super.getDefaultModelId(), new ModelProvider<DataModel>() {

            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                if (modelRequestQueue == null){
                    modelRequestQueue = new WorkQueue();
                }

                WorkItem workItem = new WorkItem(){
                    @Override
                    public void exec(Callback<Boolean> workCompleteCallback) {
                        if (cluProposalModel.getRoot() == null || initialized == false){
                            populateModel(callback, workCompleteCallback);
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
            	if(comparisonModel.getRoot() != null && comparisonModel.getRoot().size() != 0){
            		callback.onModelReady(comparisonModel);
            		
            	}
            	else{
            		callback.onModelReady(null);
            	}
                
            }
        });
        super.addApplicationEventHandler(ContentDirtyEvent.TYPE, new ContentDirtyEventHandler(){
			public void onContentDirty(ContentDirtyEvent event) {
        		setContentWarning("You have unsaved changes");				
			}        	
        });

        super.addApplicationEventHandler(SaveActionEvent.TYPE, new SaveActionHandler(){
            public void doSave(SaveActionEvent saveAction) {
                GWT.log("CluProposalController received save action request.", null);
                doSaveAction(saveAction);
            }
        }); 
            }
    
    /**
     * Used to populate the proposal model based on the view context.  
     * 
     * @param callback
     * @param workCompleteCallback
     */
    protected void populateModel(final ModelRequestCallback<DataModel> callback, Callback<Boolean> workCompleteCallback){
    	if(getViewContext().getIdType() == IdType.DOCUMENT_ID){
            getCluProposalFromWorkflowId(callback, workCompleteCallback);
        } else if (getViewContext().getIdType() == IdType.KS_KEW_OBJECT_ID || getViewContext().getIdType() == IdType.OBJECT_ID){
            // Admin Retire and Loading an approved Proposal go here
            getCluProposalFromProposalId(getViewContext().getId(), callback, workCompleteCallback);
        } else if (getViewContext().getIdType() == IdType.COPY_OF_OBJECT_ID){
        	if(LUConstants.PROPOSAL_TYPE_COURSE_MODIFY.equals(getViewContext().getAttribute(StudentIdentityConstants.DOCUMENT_TYPE_NAME))||
       			LUConstants.PROPOSAL_TYPE_COURSE_MODIFY_ADMIN.equals(getViewContext().getAttribute(StudentIdentityConstants.DOCUMENT_TYPE_NAME))){
        		createModifyCluProposalModel("versionComment", callback, workCompleteCallback);
        	}else{
        		createCopyCourseModel(getViewContext().getId(), callback, workCompleteCallback);
        	}
        } else if (getViewContext().getIdType() == IdType.COPY_OF_KS_KEW_OBJECT_ID){
            // Copy to New Proposal Button goes here.
        	createCopyCourseProposalModel(getViewContext().getId(), callback, workCompleteCallback);
        } else{
            createNewCluProposalModel(callback, workCompleteCallback);
        }
    }

    protected void getCurrentModel(final ModelRequestCallback<DataModel> callback, Callback<Boolean> workCompleteCallback){
    	if (cluProposalModel.getRoot() != null && cluProposalModel.getRoot().size() > 0){
        	String id = cluProposalModel.get(cfg.getProposalPath()+"/id");
        	if(id != null){
        		getCluProposalFromProposalId(id, callback, workCompleteCallback);
        	}
        	else{
        		populateModel(callback, workCompleteCallback);
        	}
    	}
    	else{
    		populateModel(callback, workCompleteCallback);
    	}
    }

    private void intializeView(final Callback<Boolean> onReadyCallback) {
    	if (initialized) {
    		onReadyCallback.exec(true);
    	} else {
    		initialized = true;
    		KSBlockingProgressIndicator.addTask(initializingTask);
    		setContentWarning("");
            this.requestModel(new ModelRequestCallback<DataModel>(){

				@Override
				public void onModelReady(DataModel model) {
					
					//Setup View Context & determine id type
					String idType = null;
		    		String viewContextId = "";
		    		if(getViewContext().getIdType() != null){
		                idType = getViewContext().getIdType().toString();
		                viewContextId = getViewContext().getId();
		                if(getViewContext().getIdType()==IdAttributes.IdType.COPY_OF_OBJECT_ID){
		                	viewContextId = null;
		                }

		    		}

		    		//  Change to Modify if we're coming in as create (not retire or other future doctypes)
                    //  and the version_key has a value
                    if ((currentDocType.equals(LUConstants.PROPOSAL_TYPE_COURSE_CREATE)) && 
                            (cluProposalModel.get(VERSION_KEY) != null) && (!(cluProposalModel.get(VERSION_KEY).equals(""))))
                    {
		    			currentDocType = LUConstants.PROPOSAL_TYPE_COURSE_MODIFY;
		    		}
		    		//Check for admin modify type
		    		if(LUConstants.PROPOSAL_TYPE_COURSE_MODIFY_ADMIN.equals(cluProposalModel.get(cfg.getProposalPath()+"/type"))){
		    			currentDocType = LUConstants.PROPOSAL_TYPE_COURSE_MODIFY_ADMIN;
		    		}
		    		
		    		//Get the state for save action
		    		String dtoState = getStateforSaveAction(cluProposalModel); 
		    		
		    		//Get the current workflow node for proposal
		    		String workflowNode = cluProposalModel.get(cfg.getProposalPath()+"/workflowNode");
		    		
		    		//Add properties to an id attributes map so metadata service can get correct metadata
					HashMap<String, String> idAttributes = new HashMap<String, String>();
		    		if(idType != null){
		    			idAttributes.put(IdAttributes.ID_TYPE, idType);
		    		}
		    		idAttributes.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, currentDocType);		    				    		
		    		idAttributes.put(DtoConstants.DTO_STATE, dtoState);		    		
		    		idAttributes.put(DtoConstants.DTO_NEXT_STATE, cfg.getNextState());
		    		if (LUConstants.PROPOSAL_TYPE_COURSE_MODIFY.equalsIgnoreCase(currentDocType) ||
		    			LUConstants.PROPOSAL_TYPE_COURSE_CREATE.equals(currentDocType)){		    			
		    			idAttributes.put(DtoConstants.DTO_WORKFLOW_NODE, workflowNode);
		    		}

		    		
		    		//Get metadata and complete initializing the screen
		    		getCourseProposalRpcService().getMetadata(viewContextId, idAttributes, new KSAsyncCallback<Metadata>(){
						@Override
                        public void handleTimeout(Throwable caught) {
		                	initializeFailed(); 
						}

						@Override
                        public void handleFailure(Throwable caught) {
							initializeFailed();
		                    throw new RuntimeException("Failed to get model definition.", caught);
		                }

						public void initializeFailed(){
			        		initialized = false;
		                	onReadyCallback.exec(false);
		                	KSBlockingProgressIndicator.removeTask(initializingTask);							
						}
						
		                public void onSuccess(Metadata result) {
		                	DataModelDefinition def = new DataModelDefinition(result);
		                    cluProposalModel.setDefinition(def);
		                    comparisonModel.setDefinition(def);

		                    configureScreens(def, onReadyCallback);

		                }
			          });
					
				}

				@Override
				public void onRequestFail(Throwable cause) {
					GWT.log("Failed to get modeld for proposal controller init");
					onReadyCallback.exec(false);
				}
			});
                		
    	}
    }

    @Override
    public void getMetadataForFinalState(final KSAsyncCallback<Metadata> callback){
		//Setup View Context
		String idType = null;
		String viewContextId = "";
		if(getViewContext().getIdType() != null){
            idType = getViewContext().getIdType().toString();
            viewContextId = getViewContext().getId();
            if(getViewContext().getIdType()==IdAttributes.IdType.COPY_OF_OBJECT_ID){
            	viewContextId = null;
            }
		}
		HashMap<String, String> idAttributes = new HashMap<String, String>();
		if(idType != null){
			idAttributes.put(IdAttributes.ID_TYPE, idType);
		}

		idAttributes.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, currentDocType);
		idAttributes.put(DtoConstants.DTO_STATE, cfg.getState());		    		
		idAttributes.put(DtoConstants.DTO_NEXT_STATE, cfg.getNextState());
		idAttributes.put(DtoConstants.DTO_WORKFLOW_NODE, "Publication Review");
		
		//Get metadata and complete initializing the screen
		getCourseProposalRpcService().getMetadata(viewContextId, idAttributes, new KSAsyncCallback<Metadata>(){
			@Override
			public void onSuccess(Metadata result) {
				callback.onSuccess(result);
			}
		});
    }
    
    protected void configureScreens(final DataModelDefinition modelDefinition, final Callback<Boolean> onReadyCallback){
    	if (workflowUtil != null){
    		workflowUtil.requestAndSetupModel(NO_OP_CALLBACK);	
    	}

        CourseRequirementsDataModel.getStatementTypes(new Callback<List<StatementTypeInfo>>() {

            @Override
            public void exec(List<StatementTypeInfo> stmtTypes) {
                List<StatementTypeInfo> stmtTypesOut = new ArrayList<StatementTypeInfo>();
                if (stmtTypes != null) {
                    for (StatementTypeInfo stmtType : stmtTypes) {
                        if (stmtType.getId().contains("kuali.statement.type.course.enrollmentEligibility") ||
                            stmtType.getId().contains("kuali.statement.type.course.creditConstraints")) {
                            continue;
                        }
                        stmtTypesOut.add(stmtType);
                    }
                }

                cfg.setStatementTypes(stmtTypesOut);
                cfg.setModelDefinition(modelDefinition);
                cfg.configure(CourseProposalController.this);
                
                // Add fields to workflow utils screens (such as blanket approve popup)
                // For e.g. the approval popup is different for modifications than other proposals
                if(workflowUtil!=null){
                	requestModel(new ModelRequestCallback<DataModel>(){
						public void onModelReady(DataModel model) {
							//Only display if this is a modification
						    String proposalType = model.get("proposal/type");
                            if ((proposalType!=null) && (proposalType.equals(LUConstants.PROPOSAL_TYPE_COURSE_MODIFY))){	
							    KSLabel descLabel = new KSLabel();
							    descLabel.setText(Application.getApplicationContext().getUILabel("course", LUUIConstants.FINAL_APPROVAL_DIALOG));
							    if (workflowUtil.getApproveDialogue() != null) {

							    	workflowUtil.getApproveDialogue().addWidget(descLabel);
							    }
							    workflowUtil.addApproveDialogField("", "startTerm", cfg.generateMessageInfo(LUUIConstants.PROPOSAL_START_TERM), modelDefinition, true, true);
							    workflowUtil.addApproveDialogField("proposal", "prevEndTerm", cfg.generateMessageInfo(LUUIConstants.PROPOSAL_PREV_END_TERM), modelDefinition, false);
                                
							    workflowUtil.updateApproveFields();							    
							    workflowUtil.progressiveEnableFields();							    
							}else{
							    // All other types of proposals need to go here
                                // Ignore this field (so blanket approve works if this is a new course proposal 
                                // and not a modification)
								workflowUtil.addIgnoreDialogField("proposal/prevEndTerm");
							}
						}
						public void onRequestFail(Throwable cause) {
						}
                	});
                }

                progressiveEnableFields();
                
                onReadyCallback.exec(true);
                KSBlockingProgressIndicator.removeTask(initializingTask);
            }
        });
    }
    
    /**
     * This progressively enables/disables screen fields based on other fields present in the screens.
     *  
     * NOTE: This metod must be caled after cfg.configure() is called, otherwise path to field mappings won't exist in ApplicationContext
     */
    protected void progressiveEnableFields(){
		final FieldDescriptor endTerm = Application.getApplicationContext().getPathToFieldMapping(null,CreditCourseConstants.END_TERM);
		final FieldDescriptor pilotCourse = Application.getApplicationContext().getPathToFieldMapping(null,CreditCourseConstants.PILOT_COURSE);
		
		if (pilotCourse != null && endTerm != null){
	    	//Enable and require end term field based on pilot course value in model loaded		
	        Boolean enableEndTerm = Boolean.TRUE.equals(cluProposalModel.get(CreditCourseConstants.PILOT_COURSE)) 
	        	|| DtoConstants.STATE_RETIRED.equalsIgnoreCase((String)cluProposalModel.get(CreditCourseConstants.STATE));
			
            BaseSection.progressiveEnableAndRequireFields(enableEndTerm, endTerm);

	        //Add a click handler to pilot checkbox to toggle enabling and requiredness of end term field
			KSCheckBox pilotCheckbox = ((KSCheckBox)pilotCourse.getFieldWidget());
	        pilotCheckbox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					//Disable/enable end term field based on new value of pilot checkbox
                    BaseSection.progressiveEnableAndRequireFields(event.getValue(), endTerm);
	            
			        //Clear out endTerm value if pilot course unchecked (as this field is not required when not pilot course)
			        if (!event.getValue()){
						((KSDropDown)((KSPicker)endTerm.getFieldWidget()).getInputWidget()).clear();				
					}					        
				}
			});
		}
    }

    @Override
    @SuppressWarnings("unchecked")
    public void requestModel(Class modelType, final ModelRequestCallback callback) {
        if(modelType == ReferenceModel.class){
        	if (cluProposalModel != null){
        		ReferenceModel ref = new ReferenceModel();

        		if(cluProposalModel.get(cfg.getProposalPath()) != null){
            		ref.setReferenceId((String)cluProposalModel.get(cfg.getProposalPath()+"/id"));
        		} else {
        			ref.setReferenceId(null);
        		}
        		
        		//Use the referenceAttribute to store misc data from the parent model like reference name, etc
        		if(cluProposalModel.get(cfg.getProposalPath()) != null){
        			Map<String, String> attributes = new HashMap<String, String>();
        			attributes.put("name", (String)cluProposalModel.get(cfg.getProposalPath()+"/name"));
        			ref.setReferenceAttributes(attributes);
        		} else {
        			ref.setReferenceAttributes(null);
        		}

        		ref.setReferenceTypeKey(cfg.getProposalReferenceTypeKey());
        		ref.setReferenceType(cfg.getProposalReferenceObjectType());
        		ref.setReferenceState(getViewContext().getState());

        		callback.onModelReady(ref);
        	}
        } else if (modelType == Data.class){
        	requestModel(cfg.getModelId(), callback);
        } else {
            super.requestModel(modelType, callback);
        }

    }

    protected void getCluProposalFromWorkflowId(@SuppressWarnings("rawtypes") final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
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

    protected void getCluProposalFromProposalId(String id, @SuppressWarnings("rawtypes") final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
    	KSBlockingProgressIndicator.addTask(loadDataTask);
    	getCourseProposalRpcService().getData(id, new KSAsyncCallback<Data>(){

			@Override
			public void handleFailure(Throwable caught) {
                Window.alert("Error loading Proposal: "+caught.getMessage());
                createNewCluProposalModel(callback, workCompleteCallback);
                KSBlockingProgressIndicator.removeTask(loadDataTask);
			}

			@Override
			public void onSuccess(Data result) {
				cluProposalModel.setRoot(result);
		        setHeaderTitle();
		        setLastUpdated();
                reqDataModel.retrieveStatementTypes(cluProposalModel.<String>get("id"), new Callback<Boolean>() {
                    @Override
                    public void exec(Boolean result) {
                       if(result){
                          getCourseComparisonModelAndReqs(callback, workCompleteCallback);
                       }
                    }
                });
			}

    	});
    }

    @SuppressWarnings("unchecked")
	protected void getCourseComparisonModelAndReqs(final ModelRequestCallback proposalModelRequestCallback, final Callback<Boolean> workCompleteCallback){
		if(cluProposalModel.get(VERSION_KEY) != null && !((String)cluProposalModel.get(VERSION_KEY)).equals("")){
			courseServiceAsync.getData((String)cluProposalModel.get(VERSION_KEY), new KSAsyncCallback<Data>(){
	
	    		@Override
	            public void handleFailure(Throwable caught) {
	                Window.alert("Error loading Proposal: "+caught.getMessage());
	                createNewCluProposalModel(proposalModelRequestCallback, workCompleteCallback);
	                KSBlockingProgressIndicator.removeTask(loadDataTask);
	            }

                @Override
                public void onSuccess(Data result) {
                	// ??? why result would be null ever?
                    if (result != null) 
                        comparisonModel.setRoot(result);

                    reqDataModel.retrieveStatementTypes(cluProposalModel.<String>get("id"), new Callback<Boolean>() {
                   		@Override
                   		public void exec(Boolean result) {
                   			if (result) {
                   				KSBlockingProgressIndicator.removeTask(loadDataTask);
                                reqDataModelComp.retrieveStatementTypes(comparisonModel.<String>get("id"), new Callback<Boolean>() {
                                    @Override
                                    public void exec(Boolean result) {
                                        if (result) {
                                            KSBlockingProgressIndicator.removeTask(loadDataTask);
                                         }
                                    }
                                });
                                proposalModelRequestCallback.onModelReady(cluProposalModel);
                                workCompleteCallback.exec(true);
                  			}
                   		}
                   	});
               }
            });
        } else {
            proposalModelRequestCallback.onModelReady(cluProposalModel);
            workCompleteCallback.exec(true);
            KSBlockingProgressIndicator.removeTask(loadDataTask);
        }
    }

    @SuppressWarnings("unchecked")
    protected void createNewCluProposalModel(final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
        Data data = new Data();
    	cluProposalModel.setRoot(data);
        
        Data proposalData = new Data();
        proposalData.set(new Data.StringKey("type"), currentDocType);
        data.set(new Data.StringKey("proposal"), proposalData);                
        if (cfg.getNextState() == null || cfg.getNextState().isEmpty()){
        	proposalData.set(new Data.StringKey("workflowNode"), "PreRoute");
        }
        
        isNew = true;
        setHeaderTitle();
        setLastUpdated();
        callback.onModelReady(cluProposalModel);
        workCompleteCallback.exec(true);
    }

    protected void createModifyCluProposalModel(String versionComment, final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){
        Data data = new Data();
        cluProposalModel.setRoot(data);        
        
        this.currentDocType = getViewContext().getAttribute(StudentIdentityConstants.DOCUMENT_TYPE_NAME);
        Data proposalData = new Data();
        proposalData.set(new Data.StringKey("type"), currentDocType);
        data.set(new Data.StringKey("proposal"), proposalData);
        if (cfg.getNextState() == null && cfg.getNextState().isEmpty()){
        	proposalData.set(new Data.StringKey("workflowNode"), "PreRoute");
        }
                
        Data versionData = new Data();
        versionData.set(new Data.StringKey("versionIndId"), getViewContext().getId());
        versionData.set(new Data.StringKey("versionComment"), versionComment);
        data.set(new Data.StringKey("versionInfo"), versionData);
        
        cluProposalRpcServiceAsync.saveData(cluProposalModel.getRoot(), new AsyncCallback<DataSaveResult>() {
			public void onSuccess(DataSaveResult result) {
				cluProposalModel.setRoot(result.getValue());
				setHeaderTitle();
		        setLastUpdated();
		        //add to recently viewed now that we know the id of proposal
		        ViewContext docContext = new ViewContext();
		        docContext.setId((String) cluProposalModel.get(cfg.getProposalPath()+"/id"));
		        docContext.setIdType(IdType.KS_KEW_OBJECT_ID);
		        //RecentlyViewedHelper.addDocument(getProposalTitle(), 
		        //	HistoryManager.appendContext(AppLocations.Locations.COURSE_PROPOSAL.getLocation(), docContext)
		        //		+ "/SUMMARY");
		        getCourseComparisonModelAndReqs(callback, workCompleteCallback);
		        
		        // We need to update the current view context so that if the user clicks the back button it doesn't 
		        // create a duplicate course proposal. 
		        getViewContext().setIdType(docContext.getIdType());
		        getViewContext().setId(docContext.getId());
		        
			}
			
			public void onFailure(Throwable caught) {
                Window.alert("Error loading Proposal: "+caught.getMessage());
                createNewCluProposalModel(callback, workCompleteCallback);
                KSBlockingProgressIndicator.removeTask(loadDataTask);
			}
		});
    }

    @SuppressWarnings("unchecked")
    protected void createCopyCourseModel(String originalCluId, final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){

    	cluProposalRpcServiceAsync.createCopyCourse(originalCluId, new AsyncCallback<DataSaveResult>() {
			public void onSuccess(DataSaveResult result) {
				cluProposalModel.setRoot(result.getValue());
				
				//Add in a blank proposal placeholder
		        Data proposalData = new Data();
		        cluProposalModel.getRoot().set(new Data.StringKey("proposal"), proposalData);
		        if (cfg.getNextState() == null || cfg.getNextState().isEmpty()){
		            proposalData.set(new Data.StringKey("workflowNode"), "PreRoute");
		        }
		        
		        isNew = true;
				setHeaderTitle();
		        setLastUpdated();

		        callback.onModelReady(cluProposalModel);
		        workCompleteCallback.exec(true);
			}
			
			public void onFailure(Throwable caught) {
                Window.alert("Error loading Proposal: "+caught.getMessage());
                createNewCluProposalModel(callback, workCompleteCallback);
                KSBlockingProgressIndicator.removeTask(loadDataTask);
			}
		});
    }
    
    @SuppressWarnings("unchecked")
    protected void createCopyCourseProposalModel(String originalProposalId, final ModelRequestCallback callback, final Callback<Boolean> workCompleteCallback){

    	cluProposalRpcServiceAsync.createCopyCourseProposal(originalProposalId, new AsyncCallback<DataSaveResult>() {
			public void onSuccess(DataSaveResult result) {
				cluProposalModel.setRoot(result.getValue());
		        setHeaderTitle();
		        setLastUpdated();
		        //add to recently viewed now that we know the id of proposal
		        ViewContext docContext = new ViewContext();
		        docContext.setId((String) cluProposalModel.get(cfg.getProposalPath()+"/id"));
		        docContext.setIdType(IdType.KS_KEW_OBJECT_ID);
		        RecentlyViewedHelper.addDocument(getProposalTitle(), 
		        		HistoryManager.appendContext(AppLocations.Locations.COURSE_PROPOSAL.getLocation(), docContext)
		        		+ "/COURSE_INFO");
		        
		        // We need to update the current view context so that if the user clicks the back button it doesn't 
		        // create a duplicate course proposal. 
		        getViewContext().setIdType(docContext.getIdType());
		        getViewContext().setId(docContext.getId());
		        
		        callback.onModelReady(cluProposalModel);
		        workCompleteCallback.exec(true);
			}
			
			public void onFailure(Throwable caught) {
                Window.alert("Error loading Proposal: "+caught.getMessage());
                createNewCluProposalModel(callback, workCompleteCallback);
                KSBlockingProgressIndicator.removeTask(loadDataTask);
			}
		});
    }
    
    public void doSaveAction(final SaveActionEvent saveActionEvent){
        requestModel(new ModelRequestCallback<DataModel>() {
            @Override
            public void onModelReady(DataModel model) {
                CourseProposalController.this.updateModelFromCurrentView();

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
                    		//saveActionEvent.doActionComplete();
                    		KSNotifier.add(new KSNotification("Unable to save, please check fields for errors.", false, true, 5000));
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
        String proposalId = cluProposalModel.get(cfg.getProposalPath()+"/id");
        
        //Defaulting the proposalTitle to courseTitle, this way course data gets set and assembler doesn't
        //complain. This may not be the correct approach.
        String proposalTitle = cluProposalModel.get(cfg.getProposalTitlePath());
        String courseTitle = cluProposalModel.get(cfg.getCourseTitlePath());
        if (proposalTitle == null || proposalTitle.isEmpty()){
            cluProposalModel.set(QueryPath.parse(cfg.getProposalTitlePath()), courseTitle);
        }
        
    	return proposalId==null && !CourseProposalController.this.isStartViewShowing() && !hasTitles(proposalTitle, courseTitle);
    }

    private boolean hasTitles(String proposalTitle, String courseTitle){
    	return (proposalTitle != null && !proposalTitle.isEmpty()) && (courseTitle != null && !courseTitle.isEmpty());
    }
    
    public void saveProposalClu(final SaveActionEvent saveActionEvent){
    	KSBlockingProgressIndicator.addTask(saving);
        getCourseProposalRpcService().saveData(cluProposalModel.getRoot(), new KSAsyncCallback<DataSaveResult>(){

            @Override
            public void handleFailure(Throwable caught) {
                GWT.log("Save Failed.", caught);
                KSBlockingProgressIndicator.removeTask(saving);
                KSNotifier.add(new KSNotification("Save Failed on server. Please try again.", false, true, 5000));
            }
                
            @Override
            public void handleVersionMismatch(Throwable caught) {
                super.handleVersionMismatch(caught);
                KSBlockingProgressIndicator.removeTask(saving);
            }

            public void onSuccess(DataSaveResult result) {
                KSBlockingProgressIndicator.removeTask(saving);

				Application.getApplicationContext().clearValidationWarnings();
				Application.getApplicationContext().addValidationWarnings(result.getValidationResults());
                	
				if(ValidatorClientUtils.hasErrors(result.getValidationResults())){
                	isValid(result.getValidationResults(), false, true);
                    saveActionEvent.setGotoNextView(false);
                    saveActionEvent.doActionComplete();
                    KSNotifier.add(new KSNotification("Save Failed. There were validation errors.", false, true, 5000));
                }else{
                		
                	saveActionEvent.setSaveSuccessful(true);
                	cluProposalModel.setRoot(result.getValue());
                	String title = getProposalTitle();
    	            View currentView = getCurrentView();
     				if (currentView instanceof SectionView){
    					((SectionView)currentView).updateView(cluProposalModel);
    					((SectionView) currentView).resetDirtyFlags();
    	            }
                    saveActionEvent.doActionComplete();
	                    
    				ViewContext context = CourseProposalController.this.getViewContext();
    				context.setId((String)cluProposalModel.get(proposalPath+"/id"));
    				context.setIdType(IdType.KS_KEW_OBJECT_ID);
    				
    				//Always update the status after a save.
    				if(workflowUtil != null){
    					workflowUtil.refresh();
    				}
	    				
    				setHeaderTitle();
    				setLastUpdated();
    				HistoryManager.logHistoryChange();
               		if(isNew){
               			RecentlyViewedHelper.addDocument(getProposalTitle(), 
        		        	HistoryManager.appendContext(AppLocations.Locations.COURSE_PROPOSAL.getLocation(), context)
        		        		+ "/SUMMARY");
               		}
               		else if(!currentTitle.equals(title)){
               			RecentlyViewedHelper.updateTitle(currentTitle, title, (String)cluProposalModel.get(proposalPath+"/id"));
               		}
               		isNew = false;
	    				
    				if(saveActionEvent.gotoNextView()){
    					CourseProposalController.this.showNextViewOnMenu();
    				}
	    				
    				if (ValidatorClientUtils.hasWarnings(result.getValidationResults())){
    					if (!saveActionEvent.gotoNextView()){
    						//Need to display warnings when view has not changed.
    						isValid(result.getValidationResults(), false, true);
    					}
    					KSNotifier.show("Saved with Warnings");
    				} else {
    					KSNotifier.show("Save Successful");
    				}  				
               	}
            }
        });

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
    	Application.getApplicationContext().clearCrossConstraintMap(null);
    	Application.getApplicationContext().clearPathToFieldMapping(null);
    	Application.getApplicationContext().clearValidationWarnings();
    	Application.getApplicationContext().setParentPath("");
    	   	
    	intializeView(onReadyCallback);
	}

    //Before show is called before the model is bound to the widgets. We need to update cross constraints and re-display 
    // validation warnings after widget binding
    //This gets called twice which is not optimal
	@Override
	public <V extends Enum<?>> void showView(final V viewType, final Callback<Boolean> onReadyCallback) {
		Callback<Boolean> finalizeView = new Callback<Boolean>(){
		    
			public void exec(Boolean result) {   // Called from at least CourseSumamryConfigurer.generateProposalSummarySection.verticalSection.beforeShow.exec
				
			    //Update cross constraints
				for(HasCrossConstraints crossConstraint:Application.getApplicationContext().getCrossConstraints(null)){
		        	crossConstraint.reprocessWithUpdatedConstraints();
		        }				
				
				//When showing summary section make sure data gets validated in case there are warnings.
				//TODO: Is it possible to cut down on this validation so it doesn't have to validate every time.
				if (viewType == CourseSections.SUMMARY){
				    
					KSBlockingProgressIndicator.addTask(initializingTask);
					
					courseServiceAsync.validate(cluProposalModel.getRoot(), new KSAsyncCallback<List<ValidationResultInfo>>(){ // server-side call
					    
						@Override
						public void onSuccess(List<ValidationResultInfo> result) {
						    
							Application.getApplicationContext().clearValidationWarnings();
							Application.getApplicationContext().addValidationWarnings(result);
							
							showWarnings();
							
							KSBlockingProgressIndicator.removeTask(initializingTask);
						}						
					});					
				} else {
				    
					showWarnings();					
				}
				
				onReadyCallback.exec(result);
			}
        };
        
		super.showView(viewType, finalizeView);
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
    }

	@Override
	/**
	 * Override method to determine if user has access to screen and if they have permission to open or initiate the proposal.
	 * 
	 *  FIXME: This method should not require a permissionType as a parameter
	 */
	public void checkAuthorization(final AuthorizationCallback authCallback) {
		GWT.log("Attempting Auth Check.", null);

		//Get attributes required for permission check
		Map<String,String> attributes = new HashMap<String,String>();
		addPermissionAttributes(attributes);

		//Note: Additional attributes required for permission check (eg. permission details and role qualifiers) will
		//be determined server side in the AbstractDataService.isAuthorized method. All that is required here is
		//id of the proposal object)
		cluProposalRpcServiceAsync.isAuthorized(getViewContext().getPermissionType(), attributes, new KSAsyncCallback<Boolean>(){

			@Override
			public void handleFailure(Throwable caught) {
				authCallback.isNotAuthorized("Error checking authorization.");
				GWT.log("Error checking proposal authorization.", caught);
                Window.alert("Error Checking Proposal Authorization: "+caught.getMessage());
			}

			@Override
			public void onSuccess(Boolean result) {
			    GWT.log("Succeeded checking auth for permission type '" + getViewContext().getPermissionType().toString() + "' with result: " + result, null);
				if (Boolean.TRUE.equals(result)) {
					authCallback.isAuthorized();
				}
				else {
				    authCallback.isNotAuthorized("User is not authorized: " + getViewContext().getPermissionType().toString());
				}
			}
    	});
	}
	
	@Override
    public void setViewContext(ViewContext viewContext) {
        //Determine the permission type being checked
        
//        viewContext.setPermissionType(PermissionType.MY_PERM);
        
        
        
        if (viewContext.getId() != null && !viewContext.getId().isEmpty()) {
            if (viewContext.getIdType() != IdType.COPY_OF_OBJECT_ID
                    && viewContext.getIdType() != IdType.COPY_OF_KS_KEW_OBJECT_ID) {
                //Id provided, and not a copy id, so opening an existing proposal
                viewContext.setPermissionType(PermissionType.OPEN);
            } else {
                //Copy id provided, so creating a proposal for modification
                viewContext.setPermissionType(PermissionType.INITIATE);
            }
        } else {
            //No id in view context, so creating new empty proposal
            viewContext.setPermissionType(PermissionType.INITIATE);
        }
        
        context = viewContext;
    }

	/**
	 * This method adds any permission attributes required for checking permissions
	 */
	public void addPermissionAttributes(Map<String, String> attributes){
        ViewContext viewContext = getViewContext();

        //Get the id to use to check permissions, this could either be the proposal id or the workflow document id,
        //will pass the id & id type as attributes to permission service.
        if ((viewContext.getId() != null) && (!"".equals(viewContext.getId()))) {
            attributes.put(viewContext.getIdType().toString(), viewContext.getId());
        }

        //Determine the permission type being checked
        if (viewContext.getId() != null && !viewContext.getId().isEmpty()) {
            if (viewContext.getIdType() != IdType.COPY_OF_OBJECT_ID
                    && viewContext.getIdType() != IdType.COPY_OF_KS_KEW_OBJECT_ID) {
                //Id provided, and not a copy id, so opening an existing proposal
                attributes.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, LUConstants.PROPOSAL_TYPE_COURSE_CREATE);
            }
            //Copy id provided, so creating a proposal for modification or retire           
            else if (currentDocType.equals(LUConstants.PROPOSAL_TYPE_COURSE_MODIFY)) {
                attributes.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, LUConstants.PROPOSAL_TYPE_COURSE_MODIFY);
            } else if (currentDocType.equals(LUConstants.PROPOSAL_TYPE_COURSE_RETIRE)) {
                attributes.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, LUConstants.PROPOSAL_TYPE_COURSE_RETIRE);
            } else {
                //No id in view context, so creating new empty proposal
                attributes.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, LUConstants.PROPOSAL_TYPE_COURSE_CREATE);
            }
        }
	}
	
	@Override
	public boolean isAuthorizationRequired() {
		return true;
	}

	@Override
	public void setAuthorizationRequired(boolean required) {
		throw new UnsupportedOperationException();
	}

    protected void setHeaderTitle(){
    	String title;
    	if (cluProposalModel.get(cfg.getProposalTitlePath()) != null){
    		title = getProposalTitle();
    	}
    	else{
    		title = "New Course (Proposal)";
    	}
    	this.setContentTitle(title);
    	this.setName(title);
    	WindowTitleUtils.setContextTitle(title);
		currentTitle = title;
    }

	@Override
	public WorkflowUtilities getWfUtilities() {
		return workflowUtil;
	}

	@Override
	public void beforeViewChange(final Enum<?> viewChangingTo, final Callback<Boolean> okToChange) {
		//Make sure the course information data is bound before viewing any other sections for cross field constraints
		final Callback<Boolean> reallyOkToChange = new Callback<Boolean>(){
			@Override
			public void exec(Boolean result) {
				if(result){
					if(CourseSections.GOVERNANCE.equals(viewChangingTo)){
						getView(CourseSections.COURSE_INFO, new Callback<View>(){
							@Override
							public void exec(final View view) {
								if(view!=null && view instanceof SectionView){
									requestModel(new ModelRequestCallback<DataModel>(){
										public void onModelReady(DataModel model) {
											((SectionView)view).updateWidgetData(model);
											okToChange.exec(true);
										}
										public void onRequestFail(Throwable cause) {
											okToChange.exec(false);
										}
									});
								}else{
									okToChange.exec(true);
								}
							}});
					} else
						okToChange.exec(true);					
				} else 	
					okToChange.exec(false);
			}
		};
		
		//We do this check here because theoretically the subcontroller views
		//will display their own messages to the user to give them a reason why the view
		//change has been cancelled, otherwise continue to check for reasons not to change
		//with this controller
		super.beforeViewChange(viewChangingTo, new Callback<Boolean>(){

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
											public void onActionComplete(ActionEvent action) {
												if(e.isSaveSuccessful()){
													reallyOkToChange.exec(true);
												}
												else{
													reallyOkToChange.exec(false);
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
												reallyOkToChange.exec(true);
												dialog.hide();
											}

											@Override
											public void onRequestFail(Throwable cause) {
												//TODO Is this correct... do we want to stop view change if we can't restore the data?  Possibly traps the user
												//if we don't it messes up saves, possibly warn the user that it failed and continue?
												reallyOkToChange.exec(false);
												dialog.hide();
												GWT.log("Unable to retrieve model for data restore on view change with no save", cause);
											}},
											NO_OP_CALLBACK);

										break;
									case CANCEL:
										reallyOkToChange.exec(false);
										dialog.hide();
										// Because this event fires after the history change event we need to "undo" the history events. 
										HistoryManager.logHistoryChange();  
										break;
								}
							}
						});
						dialog.addCloseLinkClickHandler(new ClickHandler() {
                            
                            @Override
                            public void onClick(ClickEvent event) {
                                okToChange.exec(false);
                                dialog.hide();
                                // Because this event fires after the history change event we need to "undo" the history events. 
                                HistoryManager.logHistoryChange();  
                            }
                        });
						dialog.show();
					}
					else{
						reallyOkToChange.exec(true);
					}
				}
				else{
					reallyOkToChange.exec(false);
				}
			}
		});
	}
	
    public KSButton getSaveButton(){
    	if(currentDocType != LUConstants.PROPOSAL_TYPE_COURSE_MODIFY && currentDocType != LUConstants.PROPOSAL_TYPE_COURSE_MODIFY_ADMIN){
	        return new KSButton("Save and Continue", new ClickHandler(){
	                    public void onClick(ClickEvent event) {
	                    	CourseProposalController.this.fireApplicationEvent(new SaveActionEvent(true));
	                    }
	                });
    	}
    	else{
    		return new KSButton("Save", new ClickHandler(){
                public void onClick(ClickEvent event) {
                    CourseProposalController.this.fireApplicationEvent(new SaveActionEvent(false));
                }
            });
    	}
    }
    
    public KSButton getCancelButton(final Enum<?> summaryView){
    	
        return new KSButton("Cancel", ButtonStyle.ANCHOR_LARGE_CENTERED, new ClickHandler(){
                    public void onClick(ClickEvent event) {
                    	if(!isNew){
                    		CourseProposalController.this.showView(summaryView);
                    	}
                    	else{
                    		Application.navigate(AppLocations.Locations.CURRICULUM_MANAGEMENT.getLocation());
                    	}
                    }
                });

    }
	
	@Override
	public void onHistoryEvent(String historyStack) {
		super.onHistoryEvent(historyStack);
		//we dont want to add proposals that are brand new before saving, or copy addresses (as they will initiate
		//the modify/copy logic again if called)
		if(cluProposalModel.get(cfg.getProposalTitlePath()) != null && 
				this.getViewContext().getIdType() != IdType.COPY_OF_OBJECT_ID){
			RecentlyViewedHelper.addCurrentDocument(getProposalTitle());
		}
	}
	
	private String getProposalTitle(){
		StringBuffer sb = new StringBuffer();
		sb.append(cluProposalModel.get(cfg.getProposalTitlePath()));
		sb.append(" (Proposal)");
		return sb.toString();
	}

    public String getCourseId(){
        return cluProposalModel.<String>get("id");
    }

    public String getCourseState(){
        return cluProposalModel.<String>get("stateKey");
    }

    public boolean isNew() {
        return isNew;
    }

    public CourseRequirementsDataModel getReqDataModel() {
        return reqDataModel;
    }

    public CourseRequirementsDataModel getReqDataModelComp() {
        return reqDataModelComp;
    }

    @Override
    public DataModel getExportDataModel() {
        return cluProposalModel;
    }
    
    /**
     * 
     * @see org.kuali.student.common.ui.client.reporting.ReportExport#getExportTemplateName()
     */
    @Override
    public String getExportTemplateName() {
        if (LUConstants.PROPOSAL_TYPE_COURSE_CREATE.equals(currentDocType)){
            return "base.template";
        }
        return "proposal.template";
    }
    
    @Override
    public List<ExportElement> getExportElementsFromView() {
        List<ExportElement> exportElements = new ArrayList<ExportElement>();
        if (this.getCurrentViewEnum().equals(CourseSections.SUMMARY)) {      
            SummaryTableSection tableSection = this.cfg.getSummaryConfigurer().getTableSection();
            ExportElement heading = new ExportElement();
            heading.setFieldLabel("");
            heading.setFieldValue(cluProposalModel.getModelName());
            heading.setFieldValue2(comparisonModel.getModelName());
            exportElements.add(heading);
            exportElements.addAll(ExportUtils.getDetailsForWidget(tableSection.getSummaryTable()));
        }
        return exportElements;
    }
    
    @Override
    public boolean isExportButtonActive() {
        if (this.getCurrentViewEnum() != null && this.getCurrentViewEnum().equals(CourseSections.SUMMARY)) {   
            return true;
        } else {
            return false;
        }
            
    }
    
    /**
     * This method is used to determine which state the dto will be when making the save call. The information
     * is used by the metadata service (along with workflow node) to determine the appropriate required indicators
     * for field elements.
     */
    protected String getStateforSaveAction(DataModel model){
		//The state for existing course proposal will be the state set on existing course
    	String state = (String)model.get(CreditCourseConstants.STATE);
		
    	//If null, this means it is a new course proposal, in which case we will default to configurer state
    	//which should be DRAFT
    	if (state == null){
			state = cfg.getState();
		}
    	
		return state;
    }
    
    public String getMessage(String courseMessageKey) {
    	String msg = Application.getApplicationContext().getMessage(MSG_GROUP, courseMessageKey);
    	if (msg == null) {
    		msg = courseMessageKey;
    	}
    	return msg;
    }

    /**
     * This method exists to allow the save/get implementations defined in this CourseProposalController
     * in this controller to be reused in the CourseAdminWithoutVersion controller. This is in an attempt
     * prevent duplication of a large chunk of code in the CourseAdminWithoutVersion controller. Rather than
     * have a save wrapped with proposal information, the  CourseAdminWithoutVersion will override this method
     * and return the standard course rpc service which does not use filters for proposal data. 
     * 
     * @return the course rpc service to use
     */
    protected  BaseDataOrchestrationRpcServiceAsync getCourseProposalRpcService(){
    	return cluProposalRpcServiceAsync;
    }
    
    public CourseProposalConfigurer getCourseProposalConfigurer() {
    	return cfg;
    }
}
