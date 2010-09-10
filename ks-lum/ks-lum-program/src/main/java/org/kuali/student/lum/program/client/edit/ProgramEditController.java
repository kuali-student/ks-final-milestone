package org.kuali.student.lum.program.client.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import org.kuali.student.lum.program.client.ProgramController;
import org.kuali.student.lum.program.client.framework.AbstractCallback;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class ProgramEditController extends ProgramController {

    private KSButton saveButton = new KSButton(ProgramProperties.get().common_save());
    private KSButton cancelButton = new KSButton(ProgramProperties.get().common_cancel());
    private ButtonMessageDialog<ButtonEnumerations.ConfirmCancelEnum> confirmDialog;

    /**
     * Constructor.
     *
     * @param programModel
     */
    public ProgramEditController(DataModel programModel, ViewContext viewContext) {
        super(programModel, viewContext);
        configurer = GWT.create(ProgramEditConfigurer.class);
        initHandlers();
        initializeConfirmDialog();
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
    }

    private void doCancel() {
        HistoryManager.navigate("/HOME/CURRICULUM_HOME/PROGRAM_VIEW", getViewContext());
    }

    private void doSave() {
        requestModel(new ModelRequestCallback<DataModel>() {
            @Override
            public void onModelReady(DataModel model) {
                ProgramEditController.this.updateModel();
            }

            @Override
            public void onRequestFail(Throwable cause) {
                GWT.log("Unable to retrieve model for validation and save", cause);
            }

        });
        programRemoteService.saveData(programModel.getRoot(), new AbstractCallback<DataSaveResult>(ProgramProperties.get().common_savingData()) {
            @Override
            public void onSuccess(DataSaveResult result) {
                super.onSuccess(result);
                programModel.setRoot(result.getValue());
            }
        });
    }
}