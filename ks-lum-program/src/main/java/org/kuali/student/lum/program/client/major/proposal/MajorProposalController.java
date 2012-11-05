package org.kuali.student.lum.program.client.major.proposal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.sections.CollapsableSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionHandler;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.security.AuthorizationCallback;
import org.kuali.student.common.ui.client.security.RequiresAuthorization;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.common.ui.client.util.ExportUtils;
import org.kuali.student.common.ui.client.validator.ValidatorClientUtils;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableSection;
import org.kuali.student.common.ui.shared.IdAttributes;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.core.proposal.ui.client.service.ProposalRpcService;
import org.kuali.student.core.proposal.ui.client.service.ProposalRpcServiceAsync;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowEnhancedNavController;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowUtilities;
import org.kuali.student.lum.common.client.configuration.LUMViews;
import org.kuali.student.lum.common.client.helpers.RecentlyViewedHelper;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.program.client.*;
import org.kuali.student.lum.program.client.events.*;
import org.kuali.student.lum.program.client.major.MajorController;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsDataModel;
import org.kuali.student.lum.program.client.rpc.AbstractCallback;
import org.kuali.student.lum.program.client.rpc.MajorDisciplineProposalRpcService;
import org.kuali.student.lum.program.client.rpc.MajorDisciplineRpcService;
import org.kuali.student.lum.program.client.rpc.MajorDisciplineRpcServiceAsync;
import org.kuali.student.lum.program.client.widgets.ProgramSideBar;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Data.Property;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r1.common.rice.authorization.PermissionType;
import org.kuali.student.r1.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.lum.clu.CLUConstants;

import java.util.*;

/**
 * @author Igor
 */
public class MajorProposalController extends MajorController implements WorkflowEnhancedNavController, RequiresAuthorization{

	private final KSButton saveButton;
    private final KSButton cancelButton;
    private final Set<String> existingVariationIds = new TreeSet<String>();
    protected String proposalPath = "";
    protected WorkflowUtilities workflowUtil; 
    private final ProposalRpcServiceAsync proposalServiceAsync = GWT.create(ProposalRpcService.class);

	protected final DataModel comparisonModel = new DataModel("Original Program");
	private String comparisonModelId = "ComparisonModel";

	protected final DataModel reqModel = new DataModel("");
	private String reqModelId = "reqModel";

    private ProgramRequirementsDataModel reqDataModel;
    private ProgramRequirementsDataModel reqDataModelComp;

    protected MajorDisciplineRpcServiceAsync majorDisciplineService;

    /**
     * Constructor.
     *
     * @param programModel
     */
    public MajorProposalController(DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super(programModel, viewContext, eventBus); 
        majorDisciplineService = createMajorDisciplineService();
        programModel.setModelName("Proposal");
        initializeComparisonModel();
        configurer = GWT.create(MajorProposalConfigurer.class);
        
        saveButton = new KSButton(getLabel(ProgramMsgConstants.COMMON_SAVE));
        cancelButton = new KSButton(getLabel(ProgramMsgConstants.COMMON_CANCEL), KSButtonAbstract.ButtonStyle.ANCHOR_LARGE_CENTERED);
        
        proposalPath = configurer.getProposalPath();
        
        workflowUtil = new WorkflowUtilities(MajorProposalController.this, proposalPath, "Proposal Actions",
   				ProgramSections.WF_APPROVE_DIALOG,"Required Fields", ProgramConstants.PROGRAM_MODEL_ID);

        sideBar.setState(ProgramSideBar.State.EDIT);
        initHandlers();
                
        reqDataModel = new ProgramRequirementsDataModel(eventBus);
        reqDataModelComp = new ProgramRequirementsDataModel(eventBus);
    }
      
    /**
     *  Overriding this method from the proposal controller and using the newly 
     *  defined reference type (referenceType.clu.proposal.program).  This will
     *  ensure comments stay with the proposal as it moves through the workflow.
     *  
     *  See KSLAB-2070
     */
    @Override
    public void requestModel(Class modelType, ModelRequestCallback callback) {
        if (modelType == ReferenceModel.class) {
            ReferenceModel referenceModel = new ReferenceModel();
            
            // Set the reference ID to the proposal ID (as opposed to the program ID)
            // so that supporting documents and comments will be linked to proposal
            // as opposed to being linked to the original program.
            referenceModel.setReferenceId(ProgramUtils.getProposalId(programModel));
           
            // The referenceTypeKey maps to the KSCO_REFERENCE_TYPE table.   
            // We are using the new value referenceType.clu.proposal.program (the new type added for program proposals)
            referenceModel.setReferenceTypeKey(ProgramConstants.PROPOSAL_REFERENCE_TYPE_ID);
            
            // This maps to KSLU_LUTYPE table.
            // Key value it is looking up is kuali.lu.type.MajorDiscipline
            // We can keep this the same for both programs and proposals
            referenceModel.setReferenceType(ProgramConstants.MAJOR_LU_TYPE_ID);
           
            Map<String, String> attributes = new HashMap<String, String>();
            attributes.put("name", getStringProperty("name"));
            referenceModel.setReferenceAttributes(attributes);
            callback.onModelReady(referenceModel);
        } else {
            super.requestModel(modelType, callback);
        }
    }
    
    /**
     * We need to override the method in MajorDisciplineController
     * to ensure our custom proposal service is called, which has
     * the filter needed to pull proposal info out of the model.
     */
    @Override
    protected MajorDisciplineRpcServiceAsync createProgramRemoteService() {
        return GWT.create(MajorDisciplineProposalRpcService.class);
    }

    /**
     * Create a ProgramRpcServiceAsync appropriate for this Controller
     */
    protected MajorDisciplineRpcServiceAsync createMajorDisciplineService() {
        return GWT.create(MajorDisciplineRpcService.class);
    }

    @Override
    protected void configureView() {
        super.configureView();
        if (!initialized) {
            eventBus.fireEvent(new MetadataLoadedEvent(programModel.getDefinition(), this));
            List<Enum<?>> excludedViews = new ArrayList<Enum<?>>();
            excludedViews.add(ProgramSections.PROGRAM_REQUIREMENTS_EDIT);
            excludedViews.add(ProgramSections.SUPPORTING_DOCUMENTS_EDIT);
            excludedViews.add(ProgramSections.SUMMARY);
            addCommonButton(getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS), saveButton, excludedViews);
            addCommonButton(getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS), cancelButton, excludedViews);
            initialized = true;
        }
    }

    /**
     * 
     * This overridden method is called by the framework to pass request parameters (basically).
     * <p>
     * See https://wiki.kuali.org/display/STUDENTDOC/6.4+History%2C+Breadcrumbs+and+ViewContext
     * <p>
     * 
     * @see org.kuali.student.lum.program.client.ProgramController#setViewContext(org.kuali.student.common.ui.client.application.ViewContext)
     */
    @Override
    public void setViewContext(ViewContext viewContext) {
        super.setViewContext(viewContext);
        if(viewContext.getId() != null && !viewContext.getId().isEmpty()){
            if(viewContext.getIdType() != IdType.COPY_OF_OBJECT_ID && viewContext.getIdType() != IdType.COPY_OF_KS_KEW_OBJECT_ID){
                

            viewContext.setPermissionType(PermissionType.OPEN);
            } else{
                // Since we are making a copy and we are in the proposal controller we know
                // we are submitting a new proposal.  We need to reset the model so that
                // work flow utilities reloads it.  This method is called multiple times 
                // by the history stack. 
                resetModel();
                //they are trying to make a modification

          viewContext.setPermissionType(PermissionType.INITIATE);
            }
        }
        else{

            viewContext.setPermissionType(PermissionType.INITIATE);
        }
    }

    /**
     * Used by configurer when adding widget to the screen.  Allows
     * us to get the workflow utilities we initialize in the 
     * controller. 
     * @return
     */
    public WorkflowUtilities getWfUtilities(){
        return workflowUtil;
    }
    
    private void initHandlers() {
        saveButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                doSave();
            }
        });
        cancelButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                doCancel();
            }
        });
        eventBus.addHandler(UpdateEvent.TYPE, new UpdateEvent.Handler() {
            @Override
            public void onEvent(UpdateEvent event) {
                Enum<?> view = event.getCurrentView();
                if (view != null) {
                    setCurrentViewEnum(view);
                }
                doSave(event.getOkCallback());

            }
        });
        eventBus.addHandler(StateChangeEvent.TYPE, new StateChangeEvent.Handler() {
            @Override
            public void onEvent(final StateChangeEvent event) {
            	//FIXME: The proper way of doing this would be a single server side call to validate next state
            	//which would retrieve warnings & required for next state, instead of re-validating warnings for
            	//current state server side and validating required for next state client side.
            	programRemoteService.validate(programModel.getRoot(), new KSAsyncCallback<List<ValidationResultInfo>>(){
					@Override
					public void onSuccess(final List<ValidationResultInfo> currentStateResults) {
		                programModel.validateNextState(new Callback<List<ValidationResultInfo>>() {
		                    @Override
		                    public void exec(List<ValidationResultInfo> nextStateResults) {
		                    	//Update validation warnings and process for all screens
		                    	Application.getApplicationContext().clearValidationWarnings();
		                    	Application.getApplicationContext().addValidationWarnings(currentStateResults);
		                    	isValid(Application.getApplicationContext().getValidationWarnings(), false);
		                        
		                    	boolean isSectionValid = isValid(nextStateResults, true) 
		                        	&& Application.getApplicationContext().getValidationWarnings().isEmpty();
		                        if (isSectionValid) {
		                            Callback<Boolean> callback = new Callback<Boolean>() {
		                                @Override
		                                public void exec(Boolean result) {
		                                    if (result) {
		                                        reloadMetadata = true;
		                                        loadMetadata(new Callback<Boolean>() {
		                                            @Override
		                                            public void exec(Boolean result) {
		                                                if (result) {
		                                                    ProgramUtils.syncMetadata(configurer, programModel.getDefinition());
		                                                    HistoryManager.navigate(AppLocations.Locations.VIEW_PROGRAM.getLocation(), context);
		                                                }
		                                            }
		                                        });
		                                    }
		                                }
		                            };
		                            updateState(event.getProgramStatus().getValue(), callback);
		                        } else {
		                            KSNotifier.add(new KSNotification("Unable to save, please check fields for errors.", false, true, 5000));
		                        }
		                    }
		                });						
					}            		
            	});                
            }
        });

        eventBus.addHandler(SpecializationSaveEvent.TYPE, new SpecializationSaveEvent.Handler() {
            @Override
            public void onEvent(SpecializationSaveEvent event) {

                Data currentVariations = getDataProperty(ProgramConstants.VARIATIONS);

                existingVariationIds.clear();

                for (Data.Property prop : currentVariations) {
                    String existingId = (String) ((Data) prop.getValue()).get(ProgramConstants.ID);
                    existingVariationIds.add(existingId);
                }
                String updatedId = event.getData().get(ProgramConstants.ID);
                Integer updatedKey = null;

                //FIXME: This is ugly but gets us past a  blocker issue. 
                // Theres something wrong with the way the models are
                // handled in the major and variation controllers  so they get out of sync.
                // This is a temporary workaround
                if (updatedId != null) { // this is an update of an existing variation
                    for (Data.Property prop : currentVariations) {
                        String id = (String) ((Data) prop.getValue()).get(ProgramConstants.ID);
                        if (updatedId.equals(id)) {
                            updatedKey = prop.getKey();
                            Data currentMetaInfo = ((Data) prop.getValue()).get("meta");
                            String latestVersionInd = currentMetaInfo.get("versionInd");
                            Data newMetaInfo = event.getData().get("meta");
                            if (newMetaInfo == null) {
                                newMetaInfo = new Data();
                                event.getData().set("meta", newMetaInfo);
                            }
                            newMetaInfo.set("versionInd", latestVersionInd);
                            break;
                        }
                    }

                    currentVariations.set(updatedKey, event.getData());
                } else {
                    currentVariations.add(event.getData());

                }
                doSave();
            }
        });
        eventBus.addHandler(AddSpecializationEvent.TYPE, new AddSpecializationEvent.Handler() {
            @Override
            public void onEvent(AddSpecializationEvent event) {
                String id = getStringProperty(ProgramConstants.ID);
                ProgramRegistry.setRow((getDataProperty(ProgramConstants.VARIATIONS)).size());
                Data variationData = ProgramUtils.createNewSpecializationBasedOnMajor(programModel);
                ProgramRegistry.setData(variationData);
                ViewContext viewContext = new ViewContext();
                viewContext.setId(id);
                viewContext.setIdType(IdAttributes.IdType.OBJECT_ID);
                if(programModel.get("proposal/id") != null){
                    // It is a proposal
                    variationData.set("isProposal", true);
                }
                HistoryManager.navigate(AppLocations.Locations.EDIT_VARIATION.getLocation(), viewContext);

            }
        });

        eventBus.addHandler(StoreRequirementIDsEvent.TYPE, new StoreRequirementIDsEvent.Handler() {
            @Override
            public void onEvent(StoreRequirementIDsEvent event) {
                List<String> ids = event.getProgramRequirementIds();

                programModel.set(QueryPath.parse(ProgramConstants.PROGRAM_REQUIREMENTS), new Data());
                Data programRequirements = programModel.get(ProgramConstants.PROGRAM_REQUIREMENTS);

                if (programRequirements == null) {
                    Window.alert("Cannot find program requirements in data model.");
                    GWT.log("Cannot find program requirements in data model", null);
                    return;
                }

                for (String id : ids) {
                    programRequirements.add(id);
                }
                
            	Callback<Boolean> reqCallback = new Callback<Boolean>() {
            		@Override
            		public void exec(Boolean result) {
            			majorDisciplineService.getData(getViewContext().getId(), new AbstractCallback<Data>(getLabel(ProgramMsgConstants.COMMON_RETRIEVINGDATA)) {
                            @Override
                            public void onSuccess(Data result) {
                                super.onSuccess(result);
                                reqModel.setRoot(result);
                                reqDataModel.retrieveProgramRequirements(MajorProposalController.this, reqModelId, new Callback<Boolean>() {
                                    @Override
                                    public void exec(Boolean result) {}
                                });                    
                            }
                            
                            @Override
                            public void onFailure(Throwable caught) {
                                super.onFailure(caught);
                            }
                        });
            		}
            	};

                doSave(reqCallback);
            }
        });
        eventBus.addHandler(ChangeViewEvent.TYPE, new ChangeViewEvent.Handler() {
            @Override
            public void onEvent(ChangeViewEvent event) {
                showView(event.getViewToken());
            }
        });
        eventBus.addHandler(ModelLoadedEvent.TYPE, new ModelLoadedEvent.Handler(){
			@Override
			public void onEvent(ModelLoadedEvent event) {
				if (workflowUtil != null){
					workflowUtil.requestAndSetupModel(NO_OP_CALLBACK);
				}
			}        	
        });
        
        addApplicationEventHandler(SaveActionEvent.TYPE, new SaveActionHandler(){
			@Override
			public void doSave(final SaveActionEvent saveAction) {
				MajorProposalController.this.doSave(new Callback<Boolean>(){
					@Override
					public void exec(Boolean result) {
						saveAction.doActionComplete();
					}
				});
			}
        });
   }

    /* (non-Javadoc)
     * @see org.kuali.student.common.ui.client.mvc.Controller#fireApplicationEvent(org.kuali.student.common.ui.client.mvc.ApplicationEvent)
     */
    @Override
	public void fireApplicationEvent(ApplicationEvent event) {
    	//Fire this event from the event bus
		if(event instanceof SaveActionEvent){
			eventBus.fireEvent(event);
		}
		super.fireApplicationEvent(event);
	}

    
    /**
     * Initialized comparison model of the controller.
     */
    private void initializeComparisonModel() {
        super.registerModel(comparisonModelId, new ModelProvider<DataModel>() {
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
                
        super.registerModel(reqModelId, new ModelProvider<DataModel>() {
            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
            	if(reqModel.getRoot() != null && reqModel.getRoot().size() != 0){
            		callback.onModelReady(reqModel);            		
            	}
            	else{
            		callback.onModelReady(null);
            	}                
             }
        });
    }

    @Override
    protected void loadMetadata(final Callback<Boolean> onReadyCallback) {
        Map<String, String> idAttributes = new HashMap<String, String>();
        ViewContext viewContext = getViewContext();
        IdType idType = viewContext.getIdType();
        String viewContextId = null;
        if (idType != null) {
            idAttributes.put(IdAttributes.ID_TYPE, idType.toString());
            viewContextId = viewContext.getId();
            if (idType == IdType.COPY_OF_OBJECT_ID) {
                viewContextId = null;
            }
        }
        idAttributes.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, CLUConstants.PROPOSAL_TYPE_MAJOR_DISCIPLINE_MODIFY);       
        if (programModel.getRoot() != null) {
            ProgramStatus programStatus = ProgramStatus.of(programModel);
            idAttributes.put(DtoConstants.DTO_STATE, programStatus.getValue());
            String workflowNode = programModel.get("proposal/workflowNode");
            if(workflowNode!=null){
            	idAttributes.put(DtoConstants.DTO_WORKFLOW_NODE, workflowNode);
            }
        }
        programRemoteService.getMetadata(viewContextId, idAttributes, new AbstractCallback<Metadata>() {

            @Override
            public void onSuccess(Metadata result) {
                super.onSuccess(result);
                DataModelDefinition def = new DataModelDefinition(result);
                programModel.setDefinition(def);
                comparisonModel.setDefinition(def);
                lastLoadedStatus = ProgramStatus.of(programModel);
                afterMetadataLoaded(onReadyCallback);
                if (result.isCanEdit()){
                    sideBar.setState(ProgramSideBar.State.EDIT);
                }else {
                	sideBar.setState(ProgramSideBar.State.VIEW);
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                super.onFailure(caught);
                onReadyCallback.exec(false);
            }
        });
    }

    @Override
    protected void loadModel(final ModelRequestCallback<DataModel> callback) {    	
    	ViewContext viewContext = getViewContext();
        if (viewContext.getIdType() == IdType.COPY_OF_OBJECT_ID) 
        {	
        	ModelRequestCallback<DataModel> comparisonModelCallback = new ModelRequestCallback<DataModel>() {
    			@Override
    			public void onModelReady(DataModel model) {
    				majorDisciplineService.getData((String)model.get("version/versionedFromId"), new AbstractCallback<Data>(getLabel(ProgramMsgConstants.COMMON_RETRIEVINGDATA)) {
                        @Override
                        public void onSuccess(Data result) {
                            super.onSuccess(result);
                            comparisonModel.setRoot(result);
                            reqDataModel.retrieveProgramRequirements(MajorProposalController.this, ProgramConstants.PROGRAM_MODEL_ID, new Callback<Boolean>() {
                                @Override
                                public void exec(Boolean result) {
                                    if (result) {
                                    	reqDataModelComp.retrieveProgramRequirements(MajorProposalController.this, comparisonModelId, new Callback<Boolean>() {
                                            @Override
                                            public void exec(Boolean result) {
                                                if (result) {
                                                    callback.onModelReady(comparisonModel);
                                                }
                                            }
                                        });
                                    }
                                }
                            });                    
                        }
                        
                        @Override
                        public void onFailure(Throwable caught) {
                            super.onFailure(caught);
                            callback.onRequestFail(caught);
                        }
                    });
        		}

    			@Override
    			public void onRequestFail(Throwable cause) {
                    GWT.log("Unable to retrieve comparison model", cause);
    			}
        	};	
        	
   			createNewVersionAndLoadModel(comparisonModelCallback, viewContext);   			
        }	
        else
        {
        	ModelRequestCallback<DataModel> comparisonModelCallback = new ModelRequestCallback<DataModel>() {
    			@Override
    			public void onModelReady(DataModel model) {
    				majorDisciplineService.getData((String)model.get("version/versionedFromId"), new AbstractCallback<Data>(getLabel(ProgramMsgConstants.COMMON_RETRIEVINGDATA)) {
                        @Override
                        public void onSuccess(Data result) {
                            super.onSuccess(result);
                            comparisonModel.setRoot(result);
                            reqDataModel.retrieveProgramRequirements(MajorProposalController.this, ProgramConstants.PROGRAM_MODEL_ID, new Callback<Boolean>() {
                                @Override
                                public void exec(Boolean result) {
                                    if (result) {
                                    	reqDataModelComp.retrieveProgramRequirements(MajorProposalController.this, comparisonModelId, new Callback<Boolean>() {
                                            @Override
                                            public void exec(Boolean result) {
                                                if (result) {
                                                    callback.onModelReady(comparisonModel);
                                                }
                                            }
                                        });
                                    }
                                }
                            });                    
                        }
                        
                        @Override
                        public void onFailure(Throwable caught) {
                            super.onFailure(caught);
                            callback.onRequestFail(caught);
                        }
                    });
        		}

    			@Override
    			public void onRequestFail(Throwable cause) {
                    GWT.log("Unable to retrieve comparison model", cause);
    			}
        	};	

        	if (viewContext.getIdType() == IdType.DOCUMENT_ID)
        		loadProgramModelFromWorkflowId(comparisonModelCallback); 
        	else 
        		super.loadModel(comparisonModelCallback);
        }	
    }

    protected void createNewVersionAndLoadModel(final ModelRequestCallback<DataModel> callback, final ViewContext viewContext) {
        Data data = new Data();                
        Data versionData = new Data();
        versionData.set(new Data.StringKey("versionIndId"), getViewContext().getId());
        versionData.set(new Data.StringKey("versionComment"), "Major Disicpline Version");
        data.set(new Data.StringKey("version"), versionData);

        programRemoteService.saveData(data, new AbstractCallback<DataSaveResult>(getLabel(ProgramMsgConstants.COMMON_RETRIEVINGDATA)) {
            @Override
            public void onSuccess(DataSaveResult result) {
                super.onSuccess(result);
                refreshModelAndView(result);
                
                viewContext.setId(ProgramUtils.getProposalId(programModel));
                viewContext.setIdType(IdType.KS_KEW_OBJECT_ID);
                callback.onModelReady(programModel);
                eventBus.fireEvent(new ModelLoadedEvent(programModel));
            }

            @Override
            public void onFailure(Throwable caught) {
                super.onFailure(caught);
                callback.onRequestFail(caught);
            }
        });

    }

    protected void loadProgramModelFromWorkflowId(final ModelRequestCallback<DataModel> callback){
        workflowUtil.getDataIdFromWorkflowId(getViewContext().getId(), new KSAsyncCallback<String>(){
			@Override
			public void handleFailure(Throwable caught) {
                Window.alert("Error loading Proposal from Workflow Document: "+caught.getMessage());
			}

			@Override
			public void onSuccess(String proposalId) {
				//Create a new view Context and navigate if coming from action list
        		ViewContext viewContext = new ViewContext();
                viewContext.setId(proposalId);
                getViewContext().setAttribute(StudentIdentityConstants.DOCUMENT_TYPE_NAME, CLUConstants.PROPOSAL_TYPE_MAJOR_DISCIPLINE_MODIFY);
                viewContext.setIdType(IdType.KS_KEW_OBJECT_ID);
                Application.navigate(AppLocations.Locations.PROGRAM_PROPOSAL.getLocation(), viewContext);
			}
        });    	
    }
    
    private void doSave(final Callback<Boolean> okCallback) {
        requestModel(new ModelRequestCallback<DataModel>() {
            @Override
            public void onModelReady(DataModel model) {
                MajorProposalController.this.updateModelFromCurrentView();
                model.validate(new Callback<List<ValidationResultInfo>>() {
                    @Override
                    public void exec(List<ValidationResultInfo> result) {
                        boolean isSectionValid = isValid(result, true);
                        if (isSectionValid) {
                            saveData(okCallback);
                        } else {
                            okCallback.exec(false);
                            KSNotifier.add(new KSNotification("Unable to save, please check fields for errors.", false, true, 5000));
                        }
                    }
                });

            }

            @Override
            public void onRequestFail(Throwable cause) {
                GWT.log("Unable to retrieve model for validation and save", cause);
            }
        });
    }

    private void doCancel() {
        showView(ProgramSections.SUMMARY);
    }

    @Override
    protected void doSave() {
        doSave(NO_OP_CALLBACK);
    }

    private void saveData(final Callback<Boolean> okCallback) {
        programRemoteService.saveData(programModel.getRoot(), new AbstractCallback<DataSaveResult>(getLabel(ProgramMsgConstants.COMMON_SAVINGDATA)) {
            @Override
            public void onSuccess(DataSaveResult result) {
                super.onSuccess(result);

                //Clear warning states on field and any warnings stored in ApplicationContext;
                clearAllWarnings();
                Application.getApplicationContext().clearValidationWarnings();
                
                List<ValidationResultInfo> validationResults = result.getValidationResults();
                Application.getApplicationContext().addValidationWarnings(validationResults);
                if (ValidatorClientUtils.hasErrors(validationResults)) {
                    ProgramUtils.retrofitValidationResults(validationResults);
                    isValid(validationResults, false, true);
                    ProgramUtils.handleValidationErrorsForSpecializations(validationResults, programModel);
                    
                    //Clean up anything created by earlier code
                    Data currentVariations = getDataProperty(ProgramConstants.VARIATIONS);

                    existingVariationIds.clear();

                    for (Iterator<Property> iter = currentVariations.iterator();iter.hasNext();) {
                    	Property prop = iter.next();
                        String existingId = (String) ((Data) prop.getValue()).get(ProgramConstants.ID);
                        if(existingId==null){
                        	iter.remove();
                        }else{
                        	existingVariationIds.add(existingId);
                        }
                    }
                    
                    okCallback.exec(false);
                } else {
                	refreshModelAndView(result);
                    if(workflowUtil != null){
                        workflowUtil.refresh();
                    }                	
                    resetFieldInteractionFlag();
                    configurer.applyPermissions();
                    handleSpecializations();
                    throwAfterSaveEvent();
                    HistoryManager.logHistoryChange();
                    ViewContext viewContext = getViewContext();
                    viewContext.setId(getStringProperty(ProgramConstants.ID));
                    viewContext.setIdType(IdType.OBJECT_ID);

    				if (ValidatorClientUtils.hasWarnings(validationResults)){
    					//Show validation warnings for major
	    				isValid(result.getValidationResults(), false, true);	    				
    					KSNotifier.show("Saved with Warnings");
    				} else {
                        KSNotifier.show(getLabel(ProgramMsgConstants.COMMON_SUCCESSFULSAVE));
    				}  				
                    
                    // add to recently viewed now that we're sure to know the program's id
                    ViewContext docContext = new ViewContext();
                    docContext.setId(getStringProperty(ProgramConstants.ID));
                    docContext.setIdType(IdType.OBJECT_ID);
                    docContext.setAttribute(ProgramConstants.TYPE, ProgramConstants.MAJOR_LU_TYPE_ID + '/' + ProgramSections.PROGRAM_DETAILS_VIEW);
                    RecentlyViewedHelper.addDocument(getProgramName(),
                    HistoryManager.appendContext(AppLocations.Locations.VIEW_PROGRAM.getLocation(), docContext));
                	okCallback.exec(true);
                	processCurrentView();                    	
                }
            }
        }); 
    }

    private void processCurrentView() {
        Enum<?> currentView = getCurrentViewEnum();
        if (currentView.name().equals(ProgramSections.VIEW_ALL.name())) {
            HistoryManager.navigate(AppLocations.Locations.VIEW_PROGRAM.getLocation(), getViewContext());
        } else {
            showView(currentView);
        }
    }

    /**
     * Handles after save work for specializations.
     */
    private void handleSpecializations() {
        String newVariationId = null;
        Data variations = programModel.get(ProgramConstants.VARIATIONS);
        for (Data.Property prop : variations) {
            String varId = (String) ((Data) prop.getValue()).get(ProgramConstants.ID);
            if (!existingVariationIds.contains(varId)) {
                newVariationId = varId;
                existingVariationIds.add(newVariationId);
                break;
            }
        }
        if (newVariationId != null) {
            eventBus.fireEvent(new SpecializationCreatedEvent(newVariationId));
        } else {
            eventBus.fireEvent(new SpecializationUpdateEvent(variations));
        }
    }

    private void throwAfterSaveEvent() {
        eventBus.fireEvent(new AfterSaveEvent(programModel, this));
    }

    @Override
    public void onModelLoadedEvent() {
        Enum<?> changeSection = ProgramRegistry.getSection();
        if (changeSection != null) {
            showView(changeSection);
            ProgramRegistry.setSection(null);
        } else {
            String id = getStringProperty(ProgramConstants.ID);
            if (id == null) {
                showView(ProgramSections.PROGRAM_DETAILS_EDIT);
            } else {
                showView(ProgramSections.PROGRAM_PROPOSAL_EDIT);
            }
        }

    }

	@Override
	public void beforeShow(final Callback<Boolean> onReadyCallback) {
		if(!initialized){
			Application.getApplicationContext().clearCrossConstraintMap(null);
			Application.getApplicationContext().clearPathToFieldMapping(null);
		}
		//Clear the parent path again
		Application.getApplicationContext().setParentPath("");
		super.beforeShow(onReadyCallback);						
	}

	//Before show is called before the model is bound to the widgets. We need to update cross constraints after widget binding
	//This gets called twice which is not optimal
	@Override
	public <V extends Enum<?>> void showView(V viewType,
			final Callback<Boolean> onReadyCallback) {
		Callback<Boolean> updateCrossConstraintsCallback = new Callback<Boolean>(){
			public void exec(Boolean result) {
				onReadyCallback.exec(result);
		        for(HasCrossConstraints crossConstraint:Application.getApplicationContext().getCrossConstraints(null)){
		        	crossConstraint.reprocessWithUpdatedConstraints();
		        }
		        showWarnings();	
			}
        };
		super.showView(viewType, updateCrossConstraintsCallback);
	}

	//Ensure that the managing bodies section view is updated before the user edits specializations
	@Override
	public void beforeViewChange(final Enum<?> viewChangingTo, final Callback<Boolean> okToChangeCallback){
	    final Callback<Boolean> reallyOkToChange = new Callback<Boolean>() {

            @Override
            public void exec(Boolean result) {
                if (result) {
                    if (LUMViews.VARIATION_EDIT.equals(viewChangingTo)) {
                        getView(ProgramSections.MANAGE_BODIES_EDIT, new Callback<View>() {
                            @Override
                            public void exec(final View view) {
                                if (view != null && view instanceof SectionView) {
                                    requestModel(new ModelRequestCallback<DataModel>() {
                                        public void onModelReady(DataModel model) {
                                            ((SectionView) view).updateWidgetData(model);
                                            okToChangeCallback.exec(true);
                                        }

                                        public void onRequestFail(Throwable cause) {
                                            okToChangeCallback.exec(false);
                                        }
                                    });
                                } else {
                                    okToChangeCallback.exec(true);
                                }
                            }
                        });
                    } else {
                        okToChangeCallback.exec(true);
                    }
                }
            }
        };
        super.beforeViewChange(viewChangingTo, reallyOkToChange);
        this.showExport(isExportButtonActive()); // KSLAB-1916
	}

    protected void setStatus() {
        String modelProposalId = programModel.get(QueryPath.parse(proposalPath + "/id"));

        if (modelProposalId != null && !modelProposalId.isEmpty()) {
            String workflowId = programModel.get(QueryPath.parse(proposalPath + "/workflowId"));
            proposalServiceAsync.getProposalByWorkflowId(workflowId, new KSAsyncCallback<ProposalInfo>() {
                @Override
                public void handleFailure(Throwable caught) {
                    statusLabel.setText("Proposal status: Unknown");
                }

                @Override
                public void onSuccess(ProposalInfo result) {
                    statusLabel.setText("Proposal status: " + result.getState());
                }
            });
        }
    }
	
    public ProgramRequirementsDataModel getReqDataModel() {
        return reqDataModel;
    }

    public ProgramRequirementsDataModel getReqDataModelComp() {
        return reqDataModelComp;
    }


	public void getMetadataForFinalState(final KSAsyncCallback<Metadata> callback) {
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

		idAttributes.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, "kuali.proposal.type.majorDiscipline.modify");
		idAttributes.put(DtoConstants.DTO_STATE, "Draft");		    		
		idAttributes.put(DtoConstants.DTO_NEXT_STATE, "Active");
		idAttributes.put(DtoConstants.DTO_WORKFLOW_NODE, "Publication Review");
		
		//Get metadata and complete initializing the screen
		programRemoteService.getMetadata(viewContextId, idAttributes, new KSAsyncCallback<Metadata>(){
			@Override
			public void onSuccess(Metadata metadata) {
				//This is not being used on screens so removing from validation
				metadata.getProperties().remove("orgCoreProgram"); 
				callback.onSuccess(metadata);
			}
		});
	}

	@Override
    public void checkAuthorization(final AuthorizationCallback callbackLocatedOnBaseControllerClass) {
        
        Map<String,String> attributes = new HashMap<String,String>();
        GWT.log("Attempting Auth Check.", null);
        if (programModel != null && programModel.getRoot() != null) {
            //This is to handle the case where entry into the proposal screens is by clicking parent breadcrumb or 
            //parent link from within specialization
            attributes.put(IdType.KS_KEW_OBJECT_ID.toString(), ProgramUtils.getProposalId(programModel));
            attributes.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, CLUConstants.PROPOSAL_TYPE_MAJOR_DISCIPLINE_MODIFY);
        } else if ( (getViewContext().getId() != null) && (!"".equals(getViewContext().getId())) && getViewContext().getIdType() != null ) {
            if (getViewContext().getIdType() == IdType.KS_KEW_OBJECT_ID || getViewContext().getIdType() == IdType.DOCUMENT_ID){
                attributes.put(getViewContext().getIdType().toString(), getViewContext().getId());
            }
        }
        programRemoteService.isAuthorized(getViewContext().getPermissionType(), attributes, new KSAsyncCallback<Boolean>(){

            @Override
            public void handleFailure(Throwable caught) {
                callbackLocatedOnBaseControllerClass.isNotAuthorized("Error checking authorization.");
                GWT.log("Error checking proposal authorization.", caught);
                Window.alert("Error Checking Proposal Authorization: "+caught.getMessage());
            }

            @Override
            public void onSuccess(Boolean result) {
                GWT.log("Succeeded checking auth for permission type '" + getViewContext().getPermissionType().toString() + "' with result: " + result, null);
                if (Boolean.TRUE.equals(result)) {
                    callbackLocatedOnBaseControllerClass.isAuthorized();
                }
                else {
                    callbackLocatedOnBaseControllerClass.isNotAuthorized("User is not authorized: " + getViewContext().getPermissionType().toString());
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
		//Does nothing at the momement
	}

    @Override
    public ArrayList<ExportElement> getExportElementsFromView() {
        String viewName = null;
        String sectionTitle = null;
        View currentView = this.getCurrentView();
        if (currentView != null) {
            
            ArrayList<ExportElement> exportElements = new ArrayList<ExportElement>();
            if (currentView != null && currentView instanceof Section) {
                Section currentSection = (Section) currentView;
                List<Section> nestedSections = currentSection.getSections();
               	for (int i = 0; i < nestedSections.size(); i++) {
               		ExportElement sectionExportItem = GWT.create(ExportElement.class);
               		ArrayList<ExportElement> subList = null;
               		Section nestedSection = nestedSections.get(i);
                   	if (nestedSection != null && nestedSection instanceof SectionView) {
                   		SectionView nestedSectionView = (SectionView) nestedSection;
                      	viewName =  nestedSectionView.getName();
                      	subList = new ArrayList<ExportElement>();
                        if (this.getCurrentViewEnum().equals(ProgramSections.SUMMARY)) {
                           	sectionExportItem.setSectionName(viewName);

                           	List<Section> sectionList = nestedSectionView.getSections();
                        	for (int j = 0; j < sectionList.size(); j++) {
                        		if (sectionList.get(j) instanceof SummaryTableSection)
                        		{	
                        			SummaryTableSection tableSection = (SummaryTableSection) sectionList.get(j);
                            		ExportElement heading = GWT.create(ExportElement.class);
                            		heading.setFieldLabel("");
                            		heading.setFieldValue(programModel.getModelName());
                            		heading.setFieldValue2(comparisonModel.getModelName());
                            		subList.add(heading);
                            		subList.addAll(ExportUtils.getDetailsForWidget(tableSection.getSummaryTable()));                        		
                        		} else if (sectionList.get(j) instanceof CollapsableSection)
                        		{
                        			List<Section> sectionColList = sectionList.get(j).getSections();
                                	for (int k = 0; k < sectionColList.size(); k++) {
                                		SummaryTableSection tableSection = (SummaryTableSection) sectionColList.get(k);
                                		ExportElement heading = GWT.create(ExportElement.class);
                                		heading.setFieldLabel("");
                                		heading.setFieldValue(programModel.getModelName());
                                		heading.setFieldValue2(comparisonModel.getModelName());
                                		subList.add(heading);
                                		subList.addAll(ExportUtils.getDetailsForWidget(tableSection.getSummaryTable()));                        		
                                	}	
                        		}	
                        	}	
                        } else { 	
                           	sectionTitle = nestedSectionView.getTitle();
                           	sectionExportItem.setSectionName(sectionTitle + " " + i + " - " + viewName);
                           	sectionExportItem.setViewName(sectionTitle + " " + i + " - " + viewName);
                           	subList = ExportUtils.getExportElementsFromView(nestedSectionView, subList, viewName, sectionTitle);
                    	}
                       	if (subList != null && subList.size()> 0) {
                       		sectionExportItem.setSubset(subList);
                       		if (i == 0 && this.getCurrentViewEnum().equals(ProgramSections.SUMMARY))
                       			exportElements.add(sectionExportItem);
                       		exportElements.add(sectionExportItem);
                       	}	
                    }	
               	}
            }
            return exportElements;           
        }

        return null;    
    }

    @Override
    public String getExportTemplateName() {
        if (this.getCurrentViewEnum().equals(ProgramSections.SUMMARY))
        	return "proposal.template";

        return "base.template";        
    }

}
