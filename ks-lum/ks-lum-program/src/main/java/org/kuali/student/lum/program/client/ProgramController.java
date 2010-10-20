package org.kuali.student.lum.program.client;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.MenuSectionController;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.common.ui.client.mvc.dto.ReferenceModel;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.commenttool.CommentTool;
import org.kuali.student.common.ui.client.widgets.dialog.ButtonMessageDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.button.YesNoCancelGroup;
import org.kuali.student.common.ui.shared.IdAttributes;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.rice.authorization.PermissionType;
import org.kuali.student.lum.program.client.events.ChangeViewEvent;
import org.kuali.student.lum.program.client.events.ChangeViewEventHandler;
import org.kuali.student.lum.program.client.events.ModelLoadedEvent;
import org.kuali.student.lum.program.client.events.UpdateEvent;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.rpc.AbstractCallback;
import org.kuali.student.lum.program.client.rpc.ProgramRpcService;
import org.kuali.student.lum.program.client.rpc.ProgramRpcServiceAsync;
import org.kuali.student.lum.program.client.widgets.ProgramSideBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Igor
 */
public abstract class ProgramController extends MenuSectionController {

    protected final ProgramRpcServiceAsync programRemoteService = GWT.create(ProgramRpcService.class);

    protected boolean initialized = false;

    protected DataModel programModel;

    protected AbstractProgramConfigurer configurer;

    protected HandlerManager eventBus;

    protected Label statusLabel = new Label();

    protected ProgramSideBar sideBar;

    /**
     * Constructor.
     *
     * @param programModel
     */
    public ProgramController(String name, DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super(name);
        this.eventBus = eventBus;
        this.programModel = programModel;
        sideBar = new ProgramSideBar(eventBus);
        setViewContext(viewContext);
        initializeModel();
    }


    @Override
    public void beforeViewChange(Enum<?> viewChangingTo, final Callback<Boolean> okToChange) {
        super.beforeViewChange(viewChangingTo, new Callback<Boolean>() {

            @Override
            public void exec(Boolean result) {
                if (result) {
                    if (getCurrentView() instanceof SectionView && ((SectionView) getCurrentView()).isDirty()) {
                        ButtonGroup<ButtonEnumerations.YesNoCancelEnum> buttonGroup = new YesNoCancelGroup();
                        final ButtonMessageDialog<ButtonEnumerations.YesNoCancelEnum> dialog = new ButtonMessageDialog<ButtonEnumerations.YesNoCancelEnum>("Warning", "You may have unsaved changes.  Save changes?", buttonGroup);
                        buttonGroup.addCallback(new Callback<ButtonEnumerations.YesNoCancelEnum>() {

                            @Override
                            public void exec(ButtonEnumerations.YesNoCancelEnum result) {
                                switch (result) {
                                    case YES:
                                        dialog.hide();
                                        eventBus.fireEvent(new UpdateEvent());
                                        resetFieldInteractionFlag();
                                        okToChange.exec(true);
                                        break;
                                    case NO:
                                        dialog.hide();
                                        resetModel();
                                        resetFieldInteractionFlag();
                                        okToChange.exec(true);
                                        break;
                                    case CANCEL:
                                        okToChange.exec(false);
                                        dialog.hide();
                                        break;
                                }
                            }
                        });
                        dialog.show();
                    } else {
                        okToChange.exec(true);
                    }
                } else {
                    okToChange.exec(false);
                }
            }
        });
    }

    protected void resetModel() {
        programModel.resetRoot();
    }

    protected void resetFieldInteractionFlag() {
        //rule screen is not a section
        if (getCurrentView() instanceof Section) {
            ((Section) getCurrentView()).resetFieldInteractionFlags();
        }
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


    @Override
    public void requestModel(Class modelType, ModelRequestCallback callback) {
        if (modelType == ReferenceModel.class) {
            ReferenceModel referenceModel = new ReferenceModel();
            referenceModel.setReferenceId((String) programModel.get("id"));
            referenceModel.setReferenceTypeKey(ProgramConstants.MAJOR_TYPE_ID);
            referenceModel.setReferenceType(ProgramConstants.MAJOR_OBJECT_ID);
            Map<String, String> attributes = new HashMap<String, String>();
            attributes.put("name", (String) programModel.get("name"));
            referenceModel.setReferenceAttributes(attributes);
            callback.onModelReady(referenceModel);
        } else {
            super.requestModel(modelType, callback);
        }
    }


    /**
     * Loads data model from the server.
     *
     * @param callback we have to invoke this callback when model is loaded or failed.
     */
    protected void loadModel(final ModelRequestCallback<DataModel> callback) {
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
                setHeaderTitle();
                setStatus();
                callback.onModelReady(programModel);
                eventBus.fireEvent(new ModelLoadedEvent(programModel));                
            }
        });
    }

    protected void setStatus() {
        statusLabel.setText(ProgramProperties.get().common_status(programModel.<String>get(ProgramConstants.STATE)));
    }

    public String getProgramName() {
        String name = (String) programModel.get("/" + ProgramConstants.LONG_TITLE);
        if (name == null) {
            name = "New Program";
        }
        return name;
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
            if (getViewContext().getIdType() == IdType.COPY_OF_OBJECT_ID) {
                viewContextId = null;
            }
        }
        Map<String, String> idAttributes = new HashMap<String, String>();
        idAttributes.put(IdAttributes.ID_TYPE, idType);

        programRemoteService.getMetadata(viewContextId, idAttributes, new AbstractCallback<Metadata>() {

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
        addStyleName("programController");
        configurer.setModelDefinition(programModel.getDefinition());
        configurer.configure(this);
        addContentWidget(statusLabel);
        setSideBarWidget(sideBar);
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
        onReadyCallback.exec(true);
    }

    protected void setHeaderTitle() {
        String title = getProgramName();
        this.setContentTitle(title);
        this.setName(title);
    }

    protected Widget createCommentPanel() {
        final CommentTool commentTool = new CommentTool(ProgramSections.COMMENTS, "Comments", "kuali.comment.type.generalRemarks", "Program Comments");
        commentTool.setController(this);
        KSButton commentsButton = new KSButton(ProgramProperties.get().comments_button(), KSButtonAbstract.ButtonStyle.DEFAULT_ANCHOR, new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                commentTool.show();
            }
        });
        return commentsButton;
    }

    protected void doSave() {

    }
}