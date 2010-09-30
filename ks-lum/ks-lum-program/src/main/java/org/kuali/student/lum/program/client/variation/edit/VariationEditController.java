package org.kuali.student.lum.program.client.variation.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.rpc.AbstractCallback;
import org.kuali.student.lum.program.client.variation.VariationController;

/**
 * @author Igor
 */
public class VariationEditController extends VariationController {

    private KSButton saveButton = new KSButton(ProgramProperties.get().common_save());
    private KSButton cancelButton = new KSButton(ProgramProperties.get().common_cancel());

    public VariationEditController(String name, DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super(name, programModel, viewContext, eventBus);
        configurer = GWT.create(VariationEditConfigurer.class);
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
    }

    @Override
    protected void configureView() {
        super.configureView();
        if (!initialized) {
            addCommonButton(ProgramProperties.get().program_menu_sections(), saveButton);
            addCommonButton(ProgramProperties.get().program_menu_sections(), cancelButton);
            initialized = true;
        }
    }

    private void doCancel() {
        HistoryManager.navigate("/HOME/CURRICULUM_HOME/PROGRAM_EDIT", getViewContext());
    }

    private void doSave() {
        requestModel(new ModelRequestCallback<DataModel>() {
            @Override
            public void onModelReady(DataModel model) {
                VariationEditController.this.updateModel();
                programRemoteService.saveData(programModel.getRoot(), new AbstractCallback<DataSaveResult>(ProgramProperties.get().common_savingData()) {
                    @Override
                    public void onSuccess(DataSaveResult result) {
                        super.onSuccess(result);
                        programModel.setRoot(result.getValue());
                        setHeaderTitle();
                        HistoryManager.logHistoryChange();
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
