package org.kuali.student.lum.program.client.major.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.dialog.ButtonMessageDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.shared.IdAttributes;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.VariationRegistry;
import org.kuali.student.lum.program.client.events.*;
import org.kuali.student.lum.program.client.major.MajorController;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.rpc.AbstractCallback;
import org.kuali.student.lum.program.client.widgets.ProgramSideBar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor
 */
public class ProgramEditController extends MajorController {

    private KSButton saveButton = new KSButton(ProgramProperties.get().common_save());
    private KSButton cancelButton = new KSButton(ProgramProperties.get().common_cancel());
    private ButtonMessageDialog<ButtonEnumerations.ConfirmCancelEnum> confirmDialog;

    /**
     * Constructor.
     *
     * @param programModel
     */
    public ProgramEditController(String name, DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super(name, programModel, viewContext, eventBus);
        configurer = GWT.create(ProgramEditConfigurer.class);
        sideBar.setState(ProgramSideBar.State.EDIT);
        initHandlers();
        initializeConfirmDialog();
    }

    @Override
    protected void configureView() {
        super.configureView();
        if (!initialized) {
            eventBus.fireEvent(new MetadataLoadedEvent(programModel.getDefinition(), this));
            List<Enum<?>> excludedViews = new ArrayList<Enum<?>>();
            excludedViews.add(ProgramSections.PROGRAM_REQUIREMENTS_EDIT);
            addCommonButton(ProgramProperties.get().program_menu_sections(), saveButton, excludedViews);
            addCommonButton(ProgramProperties.get().program_menu_sections(), cancelButton, excludedViews);
            initialized = true;
        }
    }

    private void initializeConfirmDialog() {
        ConfirmCancelGroup buttonGroup = new ConfirmCancelGroup();
        confirmDialog = new ButtonMessageDialog<ButtonEnumerations.ConfirmCancelEnum>(ProgramProperties.get().confirmDialog_title(), ProgramProperties.get().confirmDialog_text(), buttonGroup);
        buttonGroup.addCallback(new Callback<ButtonEnumerations.ConfirmCancelEnum>() {
            @Override
            public void exec(ButtonEnumerations.ConfirmCancelEnum result) {
                switch (result) {
                    case CONFIRM:
                        doSave();
                    case CANCEL:
                        confirmDialog.hide();
                }
            }
        });
    }

    private void initHandlers() {
        saveButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                confirmDialog.show();
            }
        });
        cancelButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                doCancel();
            }
        });
        eventBus.addHandler(UpdateEvent.TYPE, new UpdateEventHandler() {
            @Override
            public void onEvent(UpdateEvent event) {
                doSave();
            }
        });

        eventBus.addHandler(SpecializationCreatedEvent.TYPE, new SpecializationCreatedEventHandler() {
            @Override
            public void onEvent(SpecializationCreatedEvent event) {
                ((Data) programModel.get(ProgramConstants.VARIATIONS)).add(event.getData());
                doSave();
            }
        });
        eventBus.addHandler(AddSpecializationEvent.TYPE, new AddSpecializationEventHandler() {
            @Override
            public void onEvent(AddSpecializationEvent event) {
                String id = (String) programModel.get("id");
                ViewContext viewContext = new ViewContext();
                viewContext.setId(id);
                viewContext.setIdType(IdAttributes.IdType.OBJECT_ID);
                HistoryManager.navigate(ProgramConstants.VARIATION_EDIT_URL, viewContext);
            }
        });
    }

    private void doCancel() {
        HistoryManager.navigate("/HOME/CURRICULUM_HOME/PROGRAM_VIEW", getViewContext());
    }

    private void doSave() {
        requestModel(new ModelRequestCallback<DataModel>() {
            @Override
            public void onModelReady(DataModel model) {
                ProgramEditController.this.updateModel();
                programRemoteService.saveData(programModel.getRoot(), new AbstractCallback<DataSaveResult>(ProgramProperties.get().common_savingData()) {
                    @Override
                    public void onSuccess(DataSaveResult result) {
                        if (result.getValidationResults() != null && !result.getValidationResults().isEmpty()) {
                            isValid(result.getValidationResults(), false, true);
                            StringBuilder msg = new StringBuilder();
                            for (ValidationResultInfo vri : result.getValidationResults()) {
                                msg.append(vri.getMessage());
                            }
                            KSNotifier.add(new KSNotification("Save Failed. There were validation errors." + msg, false, 5000));
                        } else {
                            super.onSuccess(result);
                            programModel.setRoot(result.getValue());
                            setHeaderTitle();
                            setStatus();
                            eventBus.fireEvent(new ModelLoadedEvent(programModel));
                            HistoryManager.logHistoryChange();
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
}