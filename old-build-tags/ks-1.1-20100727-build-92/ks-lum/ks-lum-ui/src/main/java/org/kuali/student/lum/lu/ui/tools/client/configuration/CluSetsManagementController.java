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

package org.kuali.student.lum.lu.ui.tools.client.configuration;

import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.TabbedSectionLayout;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionHandler;
import org.kuali.student.common.ui.client.event.ValidateRequestEvent;
import org.kuali.student.common.ui.client.event.ValidateRequestHandler;
import org.kuali.student.common.ui.client.event.ValidateResultEvent;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.ModelProvider;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.WorkQueue;
import org.kuali.student.common.ui.client.mvc.WorkQueue.WorkItem;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicator;
import org.kuali.student.common.ui.client.widgets.buttongroups.OkGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.OkEnum;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CluSetHelper;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseConfigurer;
import org.kuali.student.lum.lu.ui.tools.client.service.CluSetManagementRpcService;
import org.kuali.student.lum.lu.ui.tools.client.service.CluSetManagementRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class CluSetsManagementController extends TabbedSectionLayout {  

    private final DataModel createCluSetModel = new DataModel();    
    private final DataModel editCluSetModel = new DataModel();
    private final DataModel viewCluSetModel = new DataModel();
    private WorkQueue createCluSetModelRequestQueue;
    private WorkQueue editCluSetModelRequestQueue;
    private WorkQueue viewCluSetModelRequestQueue;
    private WorkQueue editSearchCluSetModelRequestQueue;
    private WorkQueue viewSearchCluSetModelRequestQueue;
    private CluSetsConfigurer cfg = new CluSetsConfigurer();

    private boolean initialized = false;
    CluSetManagementRpcServiceAsync cluSetManagementRpcServiceAsync = GWT.create(CluSetManagementRpcService.class);

    final KSLightBox progressWindow = new KSLightBox();


    public CluSetsManagementController(){
        super(CluSetsManagementController.class.getName());
        initialize();
    }

    private void initialize() {
        super.setDefaultModelId(CluSetsConfigurer.CREATE_CLUSET_MGT_MODEL);
        super.registerModel(CluSetsConfigurer.CREATE_CLUSET_MGT_MODEL, new ModelProvider<DataModel>() {

            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                if (createCluSetModelRequestQueue == null){
                    createCluSetModelRequestQueue = new WorkQueue();
                }

                WorkItem workItem = new WorkItem(){
                    @Override
                    public void exec(Callback<Boolean> workCompleteCallback) {
                        if (createCluSetModel.getRoot() == null || createCluSetModel.getRoot().size() == 0){
                            createCluSetModel.setRoot(new Data());
                        }
                        callback.onModelReady(createCluSetModel);
                        workCompleteCallback.exec(true);

                    }               
                };
                createCluSetModelRequestQueue.submit(workItem);                
            }

        });

        super.registerModel(CluSetsConfigurer.EDIT_CLUSET_MGT_MODEL, new ModelProvider<DataModel>() {

            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                if (editCluSetModelRequestQueue == null){
                    editCluSetModelRequestQueue = new WorkQueue();
                }

                WorkItem workItem = new WorkItem(){
                    @Override
                    public void exec(Callback<Boolean> workCompleteCallback) {
                        if (editCluSetModel.getRoot() == null || editCluSetModel.getRoot().size() == 0){
                            editCluSetModel.setRoot(new Data());
                        }
                        callback.onModelReady(editCluSetModel);
                        workCompleteCallback.exec(true);

                    }               
                };
                editCluSetModelRequestQueue.submit(workItem);                
            }

        });

        super.registerModel(CluSetsConfigurer.VIEW_CLUSET_MGT_MODEL, new ModelProvider<DataModel>() {

            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                if (viewCluSetModelRequestQueue == null){
                    viewCluSetModelRequestQueue = new WorkQueue();
                }

                WorkItem workItem = new WorkItem(){
                    @Override
                    public void exec(Callback<Boolean> workCompleteCallback) {
                        if (viewCluSetModel.getRoot() == null || viewCluSetModel.getRoot().size() == 0){
                            viewCluSetModel.setRoot(new Data());
                        }
                        callback.onModelReady(viewCluSetModel);
                        workCompleteCallback.exec(true);

                    }               
                };
                viewCluSetModelRequestQueue.submit(workItem);                
            }

        });

        super.registerModel(CluSetsConfigurer.EDIT_SEARCH_CLUSET_MGT_MODEL, new ModelProvider<DataModel>() {
            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                if (editSearchCluSetModelRequestQueue == null){
                    editSearchCluSetModelRequestQueue = new WorkQueue();
                }

                WorkItem workItem = new WorkItem(){
                    @Override
                    public void exec(final Callback<Boolean> workCompleteCallback) {
                        if (cfg.getEditSearchCluSetId() != null) {
                            cluSetManagementRpcServiceAsync.getData(cfg.getEditSearchCluSetId(), new AsyncCallback<Data>() {
                                @Override
                                public void onFailure(Throwable caught) {
                                    Window.alert("Failed to retrieve cluset with id" + cfg.getEditSearchCluSetId());
                                    workCompleteCallback.exec(false);
                                }
                                @Override
                                public void onSuccess(Data result) {
                                    editCluSetModel.setRoot(result);
                                    callback.onModelReady(editCluSetModel);
                                    workCompleteCallback.exec(true);
                                }
                            });
                        }
                    }               
                };
                editSearchCluSetModelRequestQueue.submit(workItem);                
            }
        });

        super.registerModel(CluSetsConfigurer.VIEW_SEARCH_CLUSET_MGT_MODEL, new ModelProvider<DataModel>() {
            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                if (viewSearchCluSetModelRequestQueue == null){
                    viewSearchCluSetModelRequestQueue = new WorkQueue();
                }

                WorkItem workItem = new WorkItem(){
                    @Override
                    public void exec(final Callback<Boolean> workCompleteCallback) {
                        if (cfg.getViewSearchCluSetId() != null) {
                            cluSetManagementRpcServiceAsync.getData(cfg.getViewSearchCluSetId(), new AsyncCallback<Data>() {
                                @Override
                                public void onFailure(Throwable caught) {
                                    Window.alert("Failed to retrieve cluset with id" + cfg.getViewSearchCluSetId());
                                    workCompleteCallback.exec(false);
                                }
                                @Override
                                public void onSuccess(Data result) {
                                    viewCluSetModel.setRoot(result);
                                    callback.onModelReady(viewCluSetModel);
                                    workCompleteCallback.exec(true);
                                }
                            });
                        }
                    }               
                };
                viewSearchCluSetModelRequestQueue.submit(workItem);                
            }
        });

        super.addApplicationEventHandler(ValidateRequestEvent.TYPE, new ValidateRequestHandler() {

            @Override
            public void onValidateRequest(ValidateRequestEvent event) {
            	FieldDescriptor originatingField = event.getFieldDescriptor();
            	String modelId = null;
            	if (originatingField != null) {
            		modelId = originatingField.getModelId();
            	}
            	if (modelId == null) {
            		requestModel(new ModelRequestCallback<DataModel>() {
            			@Override
            			public void onModelReady(DataModel model) {
            				validateModel(model);
            			}

            			@Override
            			public void onRequestFail(Throwable cause) {
            				GWT.log("Unable to retrieve model for validation", cause);
            			}

            		});
            	} else {
            		requestModel(modelId, new ModelRequestCallback<DataModel>() {
            			@Override
            			public void onModelReady(DataModel model) {
            				validateModel(model);
            			}

            			@Override
            			public void onRequestFail(Throwable cause) {
            				GWT.log("Unable to retrieve model for validation", cause);
            			}

            		});
            	}
            }

        });
    }
    
    private void validateModel(DataModel model) {
		model.validate(new Callback<List<ValidationResultInfo>>() {
			@Override
			public void exec(List<ValidationResultInfo> result) {
				ValidateResultEvent e = new ValidateResultEvent();
				e.setValidationResult(result);
				fireApplicationEvent(e);
			}
		});
    }

    private KSButton getQuitButton(){
        return new KSButton("Quit", new ClickHandler(){
            public void onClick(ClickEvent event) {
                Application.navigate("/HOME/CURRICULUM_HOME");
            }
        });       
    }

    private KSButton getSaveButton(){
        return new KSButton("Save", new ClickHandler(){
            public void onClick(ClickEvent event) {
                fireApplicationEvent(new SaveActionEvent());
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

            cluSetManagementRpcServiceAsync.getMetadata("", "", new AsyncCallback<Metadata>(){

                @Override
                public void onFailure(Throwable caught) {
                    onReadyCallback.exec(false);
                    progressWindow.hide();
                    throw new RuntimeException("Failed to get model definition.", caught);                        
                }

                @Override
                public void onSuccess(Metadata result) {
                    DataModelDefinition def = new DataModelDefinition(result);
                    createCluSetModel.setDefinition(def);
                    editCluSetModel.setDefinition(def);
                    viewCluSetModel.setDefinition(def);
                    init(def);
                    initialized = true;
                    onReadyCallback.exec(true);
                    progressWindow.hide();
                }                
            });	        
        }
    }

    private void init(DataModelDefinition modelDefinition){

        cfg.setModelDefinition(modelDefinition);

        if (!initialized){
            cfg.configureCluSetManager(this);
            addButton("Manage CLU Sets", getSaveButton());
            addButton("Manage CLU Sets", getQuitButton());
            addButton("View CLU Sets", getSaveButton());
            addButton("View CLU Sets", getQuitButton());

            addApplicationEventHandler(SaveActionEvent.TYPE, new SaveActionHandler(){
                @Override
                public void doSave(SaveActionEvent saveAction) {
                    GWT.log("CluSetManagementController received save action request.", null);
                    doSaveAction(saveAction);
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
        return CourseConfigurer.CourseSections.class;
    }

    @Override
    protected void renderView(View view) {
        super.renderView(view);
        getNextButton("Manage CLU Sets").setVisible(false);
        getNextButton("View CLU Sets").setVisible(false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void requestModel(Class modelType, final ModelRequestCallback callback) {
        super.requestModel(modelType, callback);
    }

    public void doSaveAction(final SaveActionEvent saveActionEvent){
        Enum clusetSectionEnum = getCurrentViewEnum();
        final DataModel modelToBeSaved;
        final boolean clearData;

        getCurrentView().updateModel();

        if (clusetSectionEnum == CluSetsConfigurer.CluSetSections.CREATE_CLU_SET) {
            modelToBeSaved = createCluSetModel;
            // save the model and starts with an empty data once model is saved
            clearData = true;
        } else if (clusetSectionEnum == CluSetsConfigurer.CluSetSections.EDIT_CLU_SET) {
            modelToBeSaved = editCluSetModel;
            // save the model and populates the model with data saved
            clearData = false;
        } else {
            modelToBeSaved = null;
            clearData = false;
        }
        if(modelToBeSaved!=null){
	        modelToBeSaved.validate(new Callback<List<ValidationResultInfo>>() {
	            @Override
	            public void exec(List<ValidationResultInfo> result) {
	
	                boolean save = true;
	                View v = getCurrentView();
	                if(v instanceof Section){
	                    ((Section) v).setFieldHasHadFocusFlags(true);
	                    ErrorLevel status = ((Section) v).processValidationResults(result);
	                    if(status == ErrorLevel.ERROR){
	                        save = false;
	                    }
	                }
	
	                if(save){
	                    getCurrentView().updateModel();
	                    CluSetsManagementController.this.updateModel();
	                    // set reusable flag here for CluSetManagement.
	                    CluSetHelper.wrap((Data)modelToBeSaved.getRoot().get("cluset")).setReusable(new Boolean(true));
	                    saveModel(modelToBeSaved, saveActionEvent, clearData);
	                }
	                else{
	                    Window.alert("Save failed.  Please check fields for errors.");
	                }
	
	            }
	        });
        }
    }

    private void saveModel(final DataModel dataModel, final SaveActionEvent saveActionEvent,
            final boolean clearData) {
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
            cluSetManagementRpcServiceAsync.saveData(dataModel.getRoot(), new AsyncCallback<DataSaveResult>() {
                @Override
                public void onFailure(Throwable caught) {
                    saveFailedCallback.exec(caught); 
                }

                @Override
                public void onSuccess(DataSaveResult result) {
                    // FIXME needs to check validation results and display messages if validation failed
                    if (clearData) {
                        dataModel.setRoot(new Data());
                    } else {
                        dataModel.setRoot(result.getValue());
                    } 
                    if (saveActionEvent.isAcknowledgeRequired()){
                        saveMessage.setText("Save Successful");
                        buttonGroup.getButton(OkEnum.Ok).setEnabled(true);
                    } else {
                        saveWindow.hide();
                        if (dataModel == createCluSetModel) {
                            saveActionEvent.doActionComplete();  
                        }
                    } 
                }
            });

            // test code remove when done testing
            if (saveActionEvent.isAcknowledgeRequired()){
                saveMessage.setText("Save Successful");
                buttonGroup.getButton(OkEnum.Ok).setEnabled(true);
            } else {
                saveWindow.hide();
                saveActionEvent.doActionComplete();                        
            }
        } catch (Exception e) {
            saveFailedCallback.exec(e);
        }
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
    
    @Override
    public void beforeShow(Callback<Boolean> onReadyCallback) {
    	showDefaultView(onReadyCallback);
    }

    private void doShowDefaultView(final Callback<Boolean> onReadyCallback) {
        super.showDefaultView(onReadyCallback);
    }

    @Override
    public Enum<?> getViewEnumValue(String enumValue) {
        return null;
    }

    @Override
    public void setParentController(Controller controller) {
        super.setParentController(controller);    
    }
}