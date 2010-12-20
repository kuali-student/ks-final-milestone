package org.kuali.student.lum.program.client.major.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.shared.IdAttributes;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.common.client.helpers.RecentlyViewedHelper;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.program.client.*;
import org.kuali.student.lum.program.client.events.*;
import org.kuali.student.lum.program.client.major.MajorController;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.rpc.AbstractCallback;
import org.kuali.student.lum.program.client.widgets.ProgramSideBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Igor
 */
public class MajorEditController extends MajorController {

    private final KSButton saveButton = new KSButton(ProgramProperties.get().common_save());
    private final KSButton cancelButton = new KSButton(ProgramProperties.get().common_cancel(), KSButtonAbstract.ButtonStyle.ANCHOR_LARGE_CENTERED);
    private final Set<String> existingVariationIds = new TreeSet<String>();

    private ProgramStatus previousState;

    /**
     * Constructor.
     *
     * @param programModel
     */
    public MajorEditController(DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super(programModel, viewContext, eventBus);
        configurer = GWT.create(MajorEditConfigurer.class);
        sideBar.setState(ProgramSideBar.State.EDIT);
        initHandlers();
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
            addCommonButton(ProgramProperties.get().program_menu_sections(), saveButton, excludedViews);
            addCommonButton(ProgramProperties.get().program_menu_sections(), cancelButton, excludedViews);
            initialized = true;
        }
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
                programModel.validateNextState(new Callback<List<ValidationResultInfo>>() {
                    @Override
                    public void exec(List<ValidationResultInfo> result) {
                        boolean isSectionValid = isValid(result, true);
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
                            previousState = ProgramStatus.of(programModel);
                            ProgramUtils.setStatus(programModel, event.getProgramStatus().getValue());
                            saveData(callback);
                        } else {
                            Window.alert("Save failed.  Please check fields for errors.");
                        }
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
                            Data currentMetaInfo = ((Data) prop.getValue()).get("metaInfo");
                            String latestVersionInd = currentMetaInfo.get("versionInd");
                            Data newMetaInfo = event.getData().get("metaInfo");
                            if (newMetaInfo == null) {
                                newMetaInfo = new Data();
                                event.getData().set("metaInfo", newMetaInfo);
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
                ProgramRegistry.setData(ProgramUtils.createNewSpecializationBasedOnMajor(programModel));
                ViewContext viewContext = new ViewContext();
                viewContext.setId(id);
                viewContext.setIdType(IdAttributes.IdType.OBJECT_ID);
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
                doSave();
            }
        });
        eventBus.addHandler(ChangeViewEvent.TYPE, new ChangeViewEvent.Handler() {
            @Override
            public void onEvent(ChangeViewEvent event) {
                showView(event.getViewToken());
            }
        });
    }

    @Override
    protected void loadModel(ModelRequestCallback<DataModel> callback) {
        ViewContext viewContext = getViewContext();
        if (viewContext.getIdType() == IdType.COPY_OF_OBJECT_ID) {
            createNewVersionAndLoadModel(callback, viewContext);
        } else {
            super.loadModel(callback);
        }
    }

    protected void createNewVersionAndLoadModel(final ModelRequestCallback<DataModel> callback, final ViewContext viewContext) {
        Data data = new Data();
        Data versionData = new Data();
        versionData.set(new Data.StringKey("versionIndId"), getViewContext().getId());
        versionData.set(new Data.StringKey("versionComment"), "Major Disicpline Version");
        data.set(new Data.StringKey("versionInfo"), versionData);

        programRemoteService.saveData(data, new AbstractCallback<DataSaveResult>(ProgramProperties.get().common_retrievingData()) {
            @Override
            public void onSuccess(DataSaveResult result) {
                super.onSuccess(result);
                programModel.setRoot(result.getValue());
                viewContext.setId(ProgramUtils.getProgramId(programModel));
                viewContext.setIdType(IdType.OBJECT_ID);
                setHeaderTitle();
                setStatus();
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

    private void doSave(final Callback<Boolean> okCallback) {
        requestModel(new ModelRequestCallback<DataModel>() {
            @Override
            public void onModelReady(DataModel model) {
                MajorEditController.this.updateModelFromCurrentView();
                model.validate(new Callback<List<ValidationResultInfo>>() {
                    @Override
                    public void exec(List<ValidationResultInfo> result) {
                        boolean isSectionValid = isValid(result, true);
                        if (isSectionValid) {
                            saveData(okCallback);
                        } else {
                            okCallback.exec(false);
                            Window.alert("Save failed.  Please check fields for errors.");
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
        programRemoteService.saveData(programModel.getRoot(), new AbstractCallback<DataSaveResult>(ProgramProperties.get().common_savingData()) {
            @Override
            public void onSuccess(DataSaveResult result) {
                super.onSuccess(result);
                Data data = result.getValue();
                if (data != null) {
                    programModel.setRoot(result.getValue());
                }
                List<ValidationResultInfo> validationResults = result.getValidationResults();
                if (validationResults != null && !validationResults.isEmpty()) {
                    if (previousState != null) {
                        ProgramUtils.setStatus(programModel, previousState.getValue());
                    }
                    ProgramUtils.retrofitValidationResults(validationResults);
                    isValid(validationResults, false, true);
                    ProgramUtils.handleValidationErrorsForSpecializations(validationResults, programModel);
                    okCallback.exec(false);
                } else {
                    previousState = null;
                    setHeaderTitle();
                    setStatus();
                    resetFieldInteractionFlag();
                    configurer.applyPermissions();
                    handleSpecializations();
                    throwAfterSaveEvent();
                    HistoryManager.logHistoryChange();
                    ViewContext viewContext = getViewContext();
                    viewContext.setId(getStringProperty(ProgramConstants.ID));
                    viewContext.setIdType(IdType.OBJECT_ID);

                    // add to recently viewed now that we're sure to know the program's id
                    ViewContext docContext = new ViewContext();
                    docContext.setId(getStringProperty(ProgramConstants.ID));
                    docContext.setIdType(IdType.OBJECT_ID);
                    docContext.setAttribute(ProgramConstants.TYPE, ProgramConstants.MAJOR_LU_TYPE_ID + '/' + ProgramSections.PROGRAM_DETAILS_VIEW);
                    RecentlyViewedHelper.addDocument(getProgramName(),
                            HistoryManager.appendContext(AppLocations.Locations.VIEW_PROGRAM.getLocation(), docContext));
                    KSNotifier.show(ProgramProperties.get().common_successfulSave());
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
                showView(ProgramSections.SUMMARY);
            }
        }
    }
}
