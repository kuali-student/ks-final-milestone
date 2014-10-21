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

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult.ErrorLevel;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionHandler;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.ModelProvider;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.WorkQueue;
import org.kuali.student.common.ui.client.mvc.WorkQueue.WorkItem;
import org.kuali.student.common.ui.client.security.AuthorizationCallback;
import org.kuali.student.common.ui.client.security.RequiresAuthorization;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ButtonEnum;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ActionCancelGroup;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.lum.common.client.lu.LUUIPermissions;
import org.kuali.student.lum.common.client.widgets.CluSetHelper;
import org.kuali.student.lum.common.client.widgets.CluSetManagementRpcService;
import org.kuali.student.lum.common.client.widgets.CluSetManagementRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class CluSetsManagementController extends BasicLayout implements RequiresAuthorization {  

    protected final DataModel cluSetModel = new DataModel();    
    private WorkQueue cluSetModelRequestQueue;
    
    protected ClusetView mainView;
    private ClusetView createClusetView;
    protected ClusetView editClusetView;
    protected ClusetView viewClusetView;

    private boolean initialized = false;          
    protected CluSetManagementRpcServiceAsync cluSetManagementRpcServiceAsync = GWT.create(CluSetManagementRpcService.class);
    public static final String CLUSET_MGT_MODEL = "cluSetManagementModel";
    
    private final BlockingTask initializingTask = new BlockingTask("Loading");
    protected final BlockingTask retrievingTask = new BlockingTask("Retrieving ...");
	private final BlockingTask saving = new BlockingTask("Saving");    

    public CluSetsManagementController(){
        super(CluSetsManagementController.class.getName());
        setName("Course Set Management");
        initialize();
    }
    
    public ClusetView getMainView() {
        return mainView;
    }
    
    @Override
    public <V extends Enum<?>> void showView(final V viewType) {
        if (viewType == ClusetView.CluSetsManagementViews.EDIT) {
            final String cluSetId = mainView.getSelectedCluSetId();
            editClusetView.setSelectedCluSetId(cluSetId);
            viewClusetView.setSelectedCluSetId(cluSetId);
            if (cluSetId != null) {
                KSBlockingProgressIndicator.addTask(retrievingTask);
                cluSetManagementRpcServiceAsync.getData(cluSetId,  new KSAsyncCallback<Data>() {
                    @Override
                    public void handleFailure(Throwable caught) {
                        KSBlockingProgressIndicator.removeTask(retrievingTask);
                        Window.alert("Failed to retrieve cluset with id" + cluSetId);
                    }
                    @Override
                    public void onSuccess(Data result) {
                        try {
                            cluSetModel.setRoot(result);
                            editClusetView.updateView(cluSetModel);
                            CluSetsManagementController.super.showView(viewType);
                        } finally {
                            KSBlockingProgressIndicator.removeTask(retrievingTask);
                        }
                    }
                });
            }
        } else if (viewType == ClusetView.CluSetsManagementViews.VIEW) {
            final String cluSetId = mainView.getSelectedCluSetId();
            editClusetView.setSelectedCluSetId(cluSetId);
            viewClusetView.setSelectedCluSetId(cluSetId);
            if (cluSetId != null) {
                KSBlockingProgressIndicator.addTask(retrievingTask);
                cluSetManagementRpcServiceAsync.getData(cluSetId,  new KSAsyncCallback<Data>() {
                    @Override
                    public void handleFailure(Throwable caught) {
                        KSBlockingProgressIndicator.removeTask(retrievingTask);
                        Window.alert("Failed to retrieve cluset with id" + cluSetId);
                    }
                    @Override
                    public void onSuccess(Data result) {
                        try {
                            cluSetModel.setRoot(result);
                            viewClusetView.updateView(cluSetModel);
                            afterModelLoaded();
                            CluSetsManagementController.super.showView(viewType);
                        } finally {
                            KSBlockingProgressIndicator.removeTask(retrievingTask);
                        }
                    }
                });
            }
        } else {
            cluSetModel.setRoot(new Data());
            super.showView(viewType);
        }
    }

    protected void afterModelLoaded() {
      viewClusetView.afterModelIsLoaded(cluSetModel);
    }

    private Widget getButtonPanel() {
        ActionCancelGroup actionCancel =  new ActionCancelGroup(
                ButtonEnumerations.SaveCancelEnum.SAVE,
                ButtonEnumerations.SaveCancelEnum.CANCEL);
        actionCancel.addCallback(new Callback<ButtonEnumerations.ButtonEnum>() {
            @Override
            public void exec(ButtonEnum result) {
                if (result == ButtonEnumerations.SaveCancelEnum.SAVE) {
                    fireApplicationEvent(new SaveActionEvent());
                } else if (result == ButtonEnumerations.SaveCancelEnum.CANCEL) {
                    showView(ClusetView.CluSetsManagementViews.MAIN);
                }
            }
        });
        return actionCancel;
    }

    private void initialize() {
        super.setDefaultModelId(CLUSET_MGT_MODEL);
        
        // the callback is used here to append widgets at the end of the view.
        // callback is needed here because there is an asynchronous call to retrieve
        // metadata during the construction of ClusetView
        createClusetView = GWT.create(ClusetView.class);
        createClusetView.init(ClusetView.CluSetsManagementViews.CREATE,
                "Build Course Set", CLUSET_MGT_MODEL, new Callback<Boolean>() {
                    @Override
                    public void exec(Boolean result) {
                        if (result == true) {
                            Widget buttonPanel = getButtonPanel();
                            buttonPanel.getElement().getStyle().setPaddingTop(50, Style.Unit.PX);
                            createClusetView.addWidget(buttonPanel);
                        }
                    }
        });
        editClusetView = GWT.create(ClusetView.class);
        editClusetView.init(ClusetView.CluSetsManagementViews.EDIT,
                "Edit Course Set", CLUSET_MGT_MODEL, new Callback<Boolean>() {
                    @Override
                    public void exec(Boolean result) {
                        if (result == true) {
                            Widget buttonPanel = getButtonPanel();
                            buttonPanel.getElement().getStyle().setPaddingTop(50, Style.Unit.PX);
                            editClusetView.addWidget(buttonPanel);
                        }
                    }
        });
        viewClusetView = GWT.create(ClusetView.class);
        viewClusetView.init(ClusetView.CluSetsManagementViews.VIEW,
                "View Course Set", CLUSET_MGT_MODEL, null);
        
        mainView = GWT.create(ClusetView.class);
        mainView.init(ClusetView.CluSetsManagementViews.MAIN,
                "", CLUSET_MGT_MODEL, null);
        
        setDefaultView(ClusetView.CluSetsManagementViews.MAIN);
        addView(createClusetView);
        addView(editClusetView);
        addView(viewClusetView);
        addView(mainView);
        
        super.registerModel(CLUSET_MGT_MODEL, new ModelProvider<DataModel>() {

            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                if (cluSetModelRequestQueue == null){
                    cluSetModelRequestQueue = new WorkQueue();
                }

                WorkItem workItem = new WorkItem(){
                    @Override
                    public void exec(Callback<Boolean> workCompleteCallback) {
                        if (cluSetModel.getRoot() == null || cluSetModel.getRoot().size() == 0){
                            cluSetModel.setRoot(new Data());
                        }
                        callback.onModelReady(cluSetModel);
                        workCompleteCallback.exec(true);

                    }               
                };
                cluSetModelRequestQueue.submit(workItem);                
            }

        });

    }
    
    private void init(final Callback<Boolean> onReadyCallback) {
		
        if (initialized) {
            onReadyCallback.exec(true);
        } else {
    		KSBlockingProgressIndicator.addTask(initializingTask);
            cluSetManagementRpcServiceAsync.getMetadata("courseSet", null, new KSAsyncCallback<Metadata>(){

                @Override
                public void handleFailure(Throwable caught) {
                    onReadyCallback.exec(false);
                    KSBlockingProgressIndicator.removeTask(initializingTask);
                    throw new RuntimeException("Failed to get model definition.", caught);                        
                }

                @Override
                public void onSuccess(Metadata result) {
                    DataModelDefinition def = new DataModelDefinition(result);
                    cluSetModel.setDefinition(def);
                    init(def);
                    initialized = true;
                    onReadyCallback.exec(true);
                    KSBlockingProgressIndicator.removeTask(initializingTask);
                }                
            });	        
        }
    }
    
    private void init(DataModelDefinition modelDefinition){

        if (!initialized){
//            cfg.configureCluSetManager(this);
//            addButton("Manage CLU Sets", getSaveButton());
//            addButton("Manage CLU Sets", getQuitButton());
//            addButton("View CLU Sets", getSaveButton());
//            addButton("View CLU Sets", getQuitButton());

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

    @Override
    protected void renderView(View view) {
        super.renderView(view);
//        getNextButton("Manage CLU Sets").setVisible(false);
//        getNextButton("View CLU Sets").setVisible(false);
    }

    @Override
    public void requestModel(Class modelType, final ModelRequestCallback callback) {
        super.requestModel(modelType, callback);
    }

    public void doSaveAction(final SaveActionEvent saveActionEvent){
//        Enum clusetSectionEnum = getCurrentViewEnum();

        getCurrentView().updateModel();

        if(cluSetModel!=null){
            cluSetModel.validate(new Callback<List<ValidationResultInfo>>() {
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
//	                    CluSetHelper.wrap((Data)cluSetModel.getRoot().get("cluset")).setReusable(new Boolean(true));
	                    CluSetHelper.wrap((Data)cluSetModel.getRoot()).setReusable(true);
	                    saveModel(cluSetModel, saveActionEvent);
	                }
	                else{
	                    Window.alert("Save failed.  Please check fields for errors.");
	                }
	
	            }
	        });
        }
    }

    private void saveModel(final DataModel dataModel, final SaveActionEvent saveActionEvent) {
    	KSBlockingProgressIndicator.addTask(saving);    	
        
    	cluSetManagementRpcServiceAsync.saveData(dataModel.getRoot(), new KSAsyncCallback<DataSaveResult>() {
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

            @Override
            public void onSuccess(DataSaveResult result) {
              	KSBlockingProgressIndicator.removeTask(saving);
               	if (result.getValidationResults() != null &&
               	        !result.getValidationResults().isEmpty()) {
               	    StringBuilder errorMessage = new StringBuilder();
                    errorMessage.append("Validation error: ");
                    for (ValidationResultInfo validationError : result.getValidationResults()) {
                        errorMessage.append(validationError.getMessage()).append(" ");
                    }
                    KSNotifier.add(new KSNotification("Save Failed. " + errorMessage, false, 5000));                        
               	} else {
                    dataModel.setRoot(result.getValue());
                    KSNotifier.add(new KSNotification("Save Successful", false, 4000));
                }
            }
        });
    	
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
    public void setParentController(Controller controller) {
        super.setParentController(controller);    
    }
    
    @Override
    public boolean isAuthorizationRequired() {
        return true;
    }

    @Override
    public void setAuthorizationRequired(boolean required) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void checkAuthorization(final AuthorizationCallback authCallback) {
        Application.getApplicationContext().getSecurityContext().checkScreenPermission(LUUIPermissions.USE_VIEW_COURSE_SET_MANAGEMENT_SCREENS, new Callback<Boolean>() {
            @Override
            public void exec(Boolean result) {

                final boolean isAuthorized = result;
            
                if(isAuthorized){
                    authCallback.isAuthorized();
                }
                else
                    authCallback.isNotAuthorized("User is not authorized: " + LUUIPermissions.USE_VIEW_COURSE_SET_MANAGEMENT_SCREENS);
            }   
        });
    }
    
}