package org.kuali.student.lum.program.client.widgets;

import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ContinueCancelGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.core.assembly.data.ModelDefinition;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.events.MetadataLoadedEvent;
import org.kuali.student.lum.program.client.events.MetadataLoadedEventHandler;
import org.kuali.student.lum.program.client.major.edit.ProgramEditController;

/**
 * @author Igor
 */
class SideBarDialogManager {

    private HandlerManager eventBus;

    private KSLightBox dialog;

    private ButtonGroup<ButtonEnumerations.ContinueCancelEnum> buttonGroup = new ContinueCancelGroup();

    private VerticalSectionView dialogView = new VerticalSectionView(DialogView.MAIN, "Dates", ProgramConstants.PROGRAM_MODEL_ID);

    public SideBarDialogManager(HandlerManager eventBus) {
        this.eventBus = eventBus;
        dialog = new KSLightBox("Edit Dates");
        dialog.setWidget(dialogView.asWidget());
        dialog.addButtonGroup(buttonGroup);
        bind();
    }

    private void bind() {
        buttonGroup.addCallback(new Callback<ButtonEnumerations.ContinueCancelEnum>() {
            @Override
            public void exec(ButtonEnumerations.ContinueCancelEnum result) {
                switch (result) {
                    case CONTINUE:
                    case CANCEL:
                        dialog.hide();

                }
            }
        });
        eventBus.addHandler(MetadataLoadedEvent.TYPE, new MetadataLoadedEventHandler() {
            @Override
            public void onEvent(MetadataLoadedEvent event) {
                configureView(event.getModelDefinition(), event.getController());
            }
        });
    }

    private void configureView(ModelDefinition modelDefinition, ProgramEditController controller) {
        VerticalSection verticalSection = new VerticalSection();
        verticalSection.addField(new FieldDescriptor(ProgramConstants.SCHEDULED_REVIEW_DATE, new MessageKeyInfo("Scheduled Review Date"), modelDefinition.getMetadata(ProgramConstants.SCHEDULED_REVIEW_DATE)));
        verticalSection.addField(new FieldDescriptor(ProgramConstants.LAST_REVIEW_DATE, new MessageKeyInfo("Last Review Date"), modelDefinition.getMetadata(ProgramConstants.LAST_REVIEW_DATE)));
        dialogView.setLayoutController(controller);
        dialogView.addSection(verticalSection);
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
}
