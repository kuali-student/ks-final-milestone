package org.kuali.student.lum.program.client.variation.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramRegistry;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.events.*;
import org.kuali.student.lum.program.client.major.edit.MajorEditController;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.variation.VariationController;
import org.kuali.student.lum.program.client.widgets.ProgramSideBar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor
 */
public class VariationEditController extends VariationController {

    private final KSButton saveButton = new KSButton(ProgramProperties.get().common_save());
    private final KSButton cancelButton = new KSButton(ProgramProperties.get().common_cancel(), KSButtonAbstract.ButtonStyle.ANCHOR_LARGE_CENTERED);

    private String currentId;

    public VariationEditController(DataModel programModel, ViewContext viewContext, HandlerManager eventBus, MajorEditController majorController) {
        super(programModel, viewContext, eventBus, majorController);
        configurer = GWT.create(VariationEditConfigurer.class);
        sideBar.setState(ProgramSideBar.State.EDIT);
        if (programModel.get("id") != null) {
            setDefaultView(ProgramSections.SUMMARY);
        }
        initHandlers();
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
        eventBus.addHandler(ModelLoadedEvent.TYPE, new ModelLoadedEvent.Handler() {
            @Override
            public void onEvent(ModelLoadedEvent event) {
                DataModel dataModel = event.getModel();
                Data variationMap = dataModel.get(ProgramConstants.VARIATIONS);
                if (variationMap != null) {
                    int row = 0;
                    for (Data.Property property : variationMap) {
                        final Data variationData = property.getValue();
                        if (variationData.get(ProgramConstants.ID).equals(currentId)) {
                            programModel.setRoot(variationData);
                            ProgramRegistry.setData(variationData);
                            ProgramRegistry.setRow(row);
                            setContentTitle(getProgramName());
                            row++;
                            return;
                        }
                    }
                }
            }
        });
        eventBus.addHandler(ChangeViewEvent.TYPE, new ChangeViewEvent.Handler() {
            @Override
            public void onEvent(ChangeViewEvent event) {
                showView(event.getViewToken());
            }
        });
        eventBus.addHandler(SpecializationCreatedEvent.TYPE, new SpecializationCreatedEvent.Handler() {

            @Override
            public void onEvent(SpecializationCreatedEvent event) {
                programModel.getRoot().set(ProgramConstants.ID, event.getSpecializationId());
            }
        });
    }

    @Override
    protected void fireUpdateEvent(Callback<Boolean> okToChange) {
        doSave(okToChange);
    }

    private void doSave(final Callback<Boolean> okToChange) {
        requestModel(new ModelRequestCallback<DataModel>() {
            @Override
            public void onModelReady(final DataModel model) {
                VariationEditController.this.updateModelFromCurrentView();
                model.setParentPath(ProgramConstants.VARIATIONS + "/" + ProgramRegistry.getRow());
                model.validate(new Callback<List<ValidationResultInfo>>() {
                    @Override
                    public void exec(List<ValidationResultInfo> results) {
                        boolean isSectionValid = isValid(results, true);
                        if (isSectionValid) {
                            saveData(model);
                            okToChange.exec(true);
                        } else {
                            okToChange.exec(false);
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

    @Override
    protected void configureView() {
        super.configureView();
        if (!initialized) {
            List<Enum<?>> excludedViews = new ArrayList<Enum<?>>();
            excludedViews.add(ProgramSections.PROGRAM_REQUIREMENTS_EDIT);
            excludedViews.add(ProgramSections.SUPPORTING_DOCUMENTS_EDIT);
            addCommonButton(ProgramProperties.get().program_menu_sections(), saveButton, excludedViews);
            addCommonButton(ProgramProperties.get().program_menu_sections(), cancelButton, excludedViews);
            initialized = true;
        }
    }

    @Override
    protected void resetModel() {
        currentId = programModel.get(ProgramConstants.ID);
        programModel.resetRoot();
    }

    private void doCancel() {
        showView(ProgramSections.SUMMARY);
    }

    @Override
    protected void doSave() {
        doSave(NO_OP_CALLBACK);
    }

    private void saveData(DataModel model) {
        currentId = model.get("id");
        if (currentId == null) {
            eventBus.fireEvent(new SpecializationSaveEvent(model.getRoot()));
        } else {
            eventBus.fireEvent(new SpecializationUpdateEvent());
        }
        resetFieldInteractionFlag();
    }
}
