package org.kuali.student.lum.program.client.widgets;

import com.google.gwt.event.shared.HandlerManager;

import org.kuali.student.common.assembly.data.ModelDefinition;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ActionCancelGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramController;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.events.MetadataLoadedEvent;
import org.kuali.student.lum.program.client.events.UpdateEvent;

/**
 * @author Igor
 */
class SideBarDialogManager {

    private HandlerManager eventBus;

    private KSLightBox dialog;

    private ButtonGroup<ButtonEnumerations.ButtonEnum> buttonGroup = new ActionCancelGroup(ButtonEnumerations.SaveCancelEnum.SAVE, ButtonEnumerations.SaveCancelEnum.CANCEL);

    private VerticalSectionView dialogView = new VerticalSectionView(DialogView.MAIN, getLabel(ProgramMsgConstants.SIDEBAR_DIALOG_TITLE), ProgramConstants.PROGRAM_MODEL_ID, true);

    private boolean viewConfigured = false;

    public SideBarDialogManager(HandlerManager eventBus) {
        this.eventBus = eventBus;
        dialog = new KSLightBox();
        dialog.setWidget(dialogView.asWidget());
        dialog.addButtonGroup(buttonGroup);
        dialog.setSize(300, 190);
        dialog.setModal(false);
        bind();
    }

    private void bind() {
        buttonGroup.addCallback(new Callback<ButtonEnumerations.ButtonEnum>() {
            @Override
            public void exec(ButtonEnumerations.ButtonEnum button) {
                if (button == ButtonEnumerations.SaveCancelEnum.SAVE) {
                    dialogView.updateModel();
                    eventBus.fireEvent(new UpdateEvent());
                }
                dialog.hide();

            }
        });
        eventBus.addHandler(MetadataLoadedEvent.TYPE, new MetadataLoadedEvent.Handler() {
            @Override
            public void onEvent(MetadataLoadedEvent event) {
                configureView(event.getModelDefinition(), event.getController());
            }
        });
    }

    public void configureView(ModelDefinition modelDefinition, ProgramController controller) {
        if (!viewConfigured) {
            VerticalSection verticalSection = new VerticalSection();
            verticalSection.addField(new FieldDescriptor(ProgramConstants.SCHEDULED_REVIEW_DATE, new MessageKeyInfo(ProgramMsgConstants.SIDEBAR_FORM_SCHEDULEDREVIEWDATE), modelDefinition.getMetadata(ProgramConstants.SCHEDULED_REVIEW_DATE)));
            FieldDescriptor reviewDateDescriptor = new FieldDescriptor(ProgramConstants.LAST_REVIEW_DATE, new MessageKeyInfo(ProgramMsgConstants.SIDEBAR_FORM_LASTREVIEWDATE), modelDefinition.getMetadata(ProgramConstants.LAST_REVIEW_DATE));
            reviewDateDescriptor.setWidgetBinding(new DateBinding());
            verticalSection.addField(reviewDateDescriptor);
            dialogView.setLayoutController(controller);
            dialogView.addSection(verticalSection);
            viewConfigured = true;
        }
    }

    public void show() {
        dialogView.beforeShow(new Callback<Boolean>() {
            @Override
            public void exec(Boolean result) {
            }
        });
        dialogView.updateModel();
        dialog.show();
    }

    private static enum DialogView {
        MAIN
    }
    
    private String getLabel(String messageKey) {
        return Application.getApplicationContext().getUILabel(ProgramMsgConstants.PROGRAM_MSG_GROUP, messageKey);
    }
}
