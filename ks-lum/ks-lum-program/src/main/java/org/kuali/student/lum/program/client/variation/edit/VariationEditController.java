package org.kuali.student.lum.program.client.variation.edit;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramRegistry;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.ProgramUtils;
import org.kuali.student.lum.program.client.events.*;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.variation.VariationController;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;

/**
 * @author Igor
 */
public class VariationEditController extends VariationController {

    private KSButton saveButton = new KSButton(ProgramProperties.get().common_save());
    private KSButton cancelButton = new KSButton(ProgramProperties.get().common_cancel(), KSButtonAbstract.ButtonStyle.ANCHOR_LARGE_CENTERED);

    private String currentId;

    public VariationEditController(String name, DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super(name, programModel, viewContext, eventBus);
        configurer = GWT.create(VariationEditConfigurer.class);
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
        eventBus.addHandler(ModelLoadedEvent.TYPE, new ModelLoadedEventHandler() {
            @Override
            public void onEvent(ModelLoadedEvent event) {
                DataModel dataModel = event.getModel();
                Data variationMap = dataModel.get(ProgramConstants.VARIATIONS);
                if (variationMap != null) {
                    for (Data.Property property : variationMap) {
                        final Data variationData = property.getValue();
                        if (variationData.get(ProgramConstants.ID).equals(currentId)) {
                            programModel.setRoot(variationData);
                            ProgramRegistry.setData(variationData);
                            setContentTitle(ProgramProperties.get().variation_title(getProgramName()));
                            return;
                        }
                    }
                }
            }
        });
        eventBus.addHandler(ChangeViewEvent.TYPE, new ChangeViewEventHandler() {
            @Override
            public void onEvent(ChangeViewEvent event) {
                showView(event.getViewToken());
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
        HistoryManager.navigate(AppLocations.Locations.EDIT_PROGRAM.getLocation(), getViewContext());
    }

    @Override
    protected void doSave() {
        requestModel(new ModelRequestCallback<DataModel>() {
            @Override
            public void onModelReady(final DataModel model) {
                VariationEditController.this.updateModelFromCurrentView();
                model.validate(new Callback<List<ValidationResultInfo>>() {
                    @Override
                    public void exec(List<ValidationResultInfo> result) {
                        ProgramUtils.cutParentPartOfKey(result);
                        boolean isSectionValid = isValid(result, true);
                        if (isSectionValid) {
                            saveData(model);
                        } else {
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
