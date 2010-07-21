package org.kuali.student.lum.program.client;


import java.util.List;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.MenuEditableSectionController;
import org.kuali.student.common.ui.client.event.ValidateRequestEvent;
import org.kuali.student.common.ui.client.event.ValidateRequestHandler;
import org.kuali.student.common.ui.client.event.ValidateResultEvent;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.common.ui.client.mvc.WorkQueue.WorkItem;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.rpc.ProgramRpcService;
import org.kuali.student.lum.program.client.rpc.ProgramRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ProgramController extends MenuEditableSectionController {

    private final ProgramRpcServiceAsync programRemoteService = GWT.create(ProgramRpcService.class);

    private final KSButton saveButton = new KSButton(ProgramProperties.get().common_save());
    private final KSButton cancelButton = new KSButton(ProgramProperties.get().common_cancel());

    private boolean initialized = false;

    private final BlockingTask initializingTask = new BlockingTask("Loading");
    private final BlockingTask loadDataTask = new BlockingTask("Retrieving Data");

    private final DataModel programModel = new DataModel();

    private WorkQueue modelRequestQueue;

    public ProgramController() {
        super(ProgramController.class.getName());
        initHandlers();
        setViewContext(new ViewContext());
        initialize();
    }

    private void initHandlers() {
        saveButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                setEditMode(false);
            }
        });
        cancelButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                setEditMode(false);
            }
        });
    }

    private void initialize() {
        super.setDefaultModelId(ProgramConstants.PROGRAM_MODEL_ID);
        super.registerModel(ProgramConstants.PROGRAM_MODEL_ID, new ModelProvider<DataModel>() {

            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                if (modelRequestQueue == null) {
                    modelRequestQueue = new WorkQueue();
                }

                WorkItem workItem = new WorkItem() {
                    @Override
                    public void exec(Callback<Boolean> workCompleteCallback) {
                        if (programModel.getRoot() == null || programModel.getRoot().size() == 0) {
                            initModel(callback, workCompleteCallback);
                        } else {
                            callback.onModelReady(programModel);
                            workCompleteCallback.exec(true);
                        }
                    }

                };
                modelRequestQueue.submit(workItem);
            }
        });
        super.addApplicationEventHandler(ValidateRequestEvent.TYPE, new ValidateRequestHandler() {

            @Override
            public void onValidateRequest(ValidateRequestEvent event) {
                requestModel(new ModelRequestCallback<DataModel>() {
                    @Override
                    public void onModelReady(DataModel model) {
                        model.validate(new Callback<List<ValidationResultInfo>>() {
                            @Override
                            public void exec(List<ValidationResultInfo> result) {
                                ValidateResultEvent e = new ValidateResultEvent();
                                e.setValidationResult(result);
                                fireApplicationEvent(e);
                            }
                        });
                    }

                    @Override
                    public void onRequestFail(Throwable cause) {
                        GWT.log("Unable to retrieve model for validation", cause);
                    }
                });
            }
        });
    }

    private void initModel(final ModelRequestCallback<DataModel> callback, final Callback<Boolean> workCompleteCallback) {
        KSBlockingProgressIndicator.addTask(loadDataTask);
        programRemoteService.getData("1", new AsyncCallback<Data>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Error loading Program from programId: " + getViewContext().getId() + ". " + caught.getMessage());
                KSBlockingProgressIndicator.removeTask(loadDataTask);
            }

            @Override
            public void onSuccess(Data result) {
                programModel.setRoot(result);
                callback.onModelReady(programModel);
                workCompleteCallback.exec(true);
                KSBlockingProgressIndicator.removeTask(loadDataTask);
            }
        });
    }


    @Override
    public void showDefaultView(final Callback<Boolean> onReadyCallback) {
        init(new Callback<Boolean>() {

            @Override
            public void exec(Boolean result) {
                onReadyCallback.exec(result);
                ProgramController.super.showDefaultView(Controller.NO_OP_CALLBACK);
            }
        });
    }

    private void init(final Callback<Boolean> onReadyCallback) {
        if (initialized) {
            onReadyCallback.exec(true);
        } else {
            KSBlockingProgressIndicator.addTask(initializingTask);
            String idType = null;
            String viewContextId = null;
            if (getViewContext().getIdType() != null) {
                idType = getViewContext().getIdType().toString();
                viewContextId = getViewContext().getId();
                if (getViewContext().getIdType() == ViewContext.IdType.COPY_OF_OBJECT_ID) {
                    viewContextId = null;
                }
            }

            programRemoteService.getMetadata(idType, viewContextId, new AsyncCallback<Metadata>() {

                @Override
                public void onSuccess(Metadata result) {
                    DataModelDefinition def = new DataModelDefinition(result);
                    programModel.setDefinition(def);
                    init(def);
                    initialized = true;
                    onReadyCallback.exec(true);
                    KSBlockingProgressIndicator.removeTask(initializingTask);
                }

                @Override
                public void onFailure(Throwable caught) {
                    onReadyCallback.exec(false);
                    KSBlockingProgressIndicator.removeTask(initializingTask);
                    GWT.log("Failed to get model definition.", caught);
                }
            });
        }
    }

    private void init(DataModelDefinition modelDefinition) {
        AbstractProgramConfigurer configurer = GWT.create(ProgramConfigurer.class);
        configurer.setModelDefinition(modelDefinition);
        if (null == programModel.getRoot()) {
            programModel.setRoot(new Data());
        }
        configurer.configure(this);
        this.setContentTitle("Programs");

        if (!initialized) {
            addButtonForView(ProgramSections.PROGRAM_DETAILS_EDIT, saveButton);
            addButtonForView(ProgramSections.PROGRAM_DETAILS_EDIT, cancelButton);
        }
        initialized = true;
    }
}
