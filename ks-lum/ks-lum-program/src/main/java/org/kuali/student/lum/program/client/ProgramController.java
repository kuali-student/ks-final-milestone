package org.kuali.student.lum.program.client;

import com.google.gwt.core.client.GWT;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.MenuSectionController;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.rice.authorization.PermissionType;
import org.kuali.student.lum.program.client.framework.AbstractCallback;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.rpc.ProgramRpcService;
import org.kuali.student.lum.program.client.rpc.ProgramRpcServiceAsync;

/**
 * @author Igor
 */
public class ProgramController extends MenuSectionController {

    protected final ProgramRpcServiceAsync programRemoteService = GWT.create(ProgramRpcService.class);

    protected boolean initialized = false;

    protected final DataModel programModel;

    protected AbstractProgramConfigurer configurer;

    /**
     * Constructor.
     *
     * @param programModel
     */
    public ProgramController(DataModel programModel) {
        super("");
        this.programModel = programModel;
        setViewContext(new ViewContext());
        initializeModel();
        addStyleName("programController");
    }

    /**
     * Initialized model of the controller.
     */
    private void initializeModel() {
        setDefaultModelId(ProgramConstants.PROGRAM_MODEL_ID);
        registerModel(ProgramConstants.PROGRAM_MODEL_ID, new ModelProvider<DataModel>() {
            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                if (programModel.getRoot() == null || programModel.getRoot().size() == 0) {
                    loadModel(callback);
                } else {
                    callback.onModelReady(programModel);
                }
            }
        });
    }

    /**
     * Loads data model from the server.
     *
     * @param callback we have to invoke this callback when model is loaded or failed.
     */
    private void loadModel(final ModelRequestCallback<DataModel> callback) {
        programRemoteService.getData(getViewContext().getId(), new AbstractCallback<Data>(ProgramProperties.get().common_retrievingData()) {

            @Override
            public void onFailure(Throwable caught) {
                super.onFailure(caught);
                callback.onRequestFail(caught);
            }

            @Override
            public void onSuccess(Data result) {
                super.onSuccess(result);
                programModel.setRoot(result);
                callback.onModelReady(programModel);
            }
        });
    }

    /**
     * Got invoked by framework before showing the view of the controller.
     *
     * @param onReadyCallback
     */
    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {
        if (!initialized) {
            if (programModel.getDefinition() == null) {
                loadMetadata(onReadyCallback);
            } else {
                afterMetadataLoaded(onReadyCallback);
            }
        } else {
            onReadyCallback.exec(true);
        }
    }

    /**
     * Loads metadata from the server.
     *
     * @param onReadyCallback
     */
    protected void loadMetadata(final Callback<Boolean> onReadyCallback) {
        String idType = null;
        String viewContextId = null;
        if (getViewContext().getIdType() != null) {
            idType = getViewContext().getIdType().toString();
            viewContextId = getViewContext().getId();
            if (getViewContext().getIdType() == ViewContext.IdType.COPY_OF_OBJECT_ID) {
                viewContextId = null;
            }
        }
        programRemoteService.getMetadata(idType, viewContextId, new AbstractCallback<Metadata>() {

            @Override
            public void onSuccess(Metadata result) {
                super.onSuccess(result);
                DataModelDefinition def = new DataModelDefinition(result);
                programModel.setDefinition(def);
                afterMetadataLoaded(onReadyCallback);
            }

            @Override
            public void onFailure(Throwable caught) {
                super.onFailure(caught);
                onReadyCallback.exec(false);
            }
        });
    }

    protected void configureView() {
        configurer.setModelDefinition(programModel.getDefinition());
        configurer.configure(this);
        this.setContentTitle("Programs");
    }

    @Override
    public void setViewContext(ViewContext viewContext) {
        super.setViewContext(viewContext);
        if (viewContext.getId() != null && !viewContext.getId().isEmpty()) {
            viewContext.setPermissionType(PermissionType.OPEN);
        } else {
            viewContext.setPermissionType(PermissionType.INITIATE);
        }
    }

    /**
     * Called when metadata is loaded.
     *
     * @param onReadyCallback
     */
    private void afterMetadataLoaded(Callback<Boolean> onReadyCallback) {
        configureView();
        showDefaultView(onReadyCallback);
    }
}