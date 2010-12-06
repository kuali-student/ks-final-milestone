package org.kuali.student.lum.program.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.common.client.widgets.DropdownList;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramRegistry;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.ProgramStatus;
import org.kuali.student.lum.program.client.core.CoreController;
import org.kuali.student.lum.program.client.events.ModelLoadedEvent;
import org.kuali.student.lum.program.client.events.ProgramViewEvent;
import org.kuali.student.lum.program.client.major.ActionType;

/**
 * @author Igor
 */
public class CoreViewController extends CoreController {

    private final DropdownList actionBox = new DropdownList(ActionType.getValues());

    /**
     * Constructor.
     *
     * @param programModel
     */
    public CoreViewController(DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super(programModel, viewContext, eventBus);
        configurer = GWT.create(CoreViewConfigurer.class);
        bind();
    }

    private void bind() {
        actionBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                ActionType actionType = ActionType.of(actionBox.getSelectedValue());
                ViewContext viewContext = getViewContext();
                if (actionType == ActionType.MODIFY) {
                    ProgramRegistry.setSection(ProgramSections.getEditSection(getCurrentViewEnum()));
                    HistoryManager.navigate(AppLocations.Locations.EDIT_CORE_PROGRAM.getLocation(), viewContext);
                } else if (actionType == ActionType.MODIFY_VERSION) {
                    String versionIndId = programModel.get(ProgramConstants.VERSION_IND_ID);
                    viewContext.setId(versionIndId);
                    viewContext.setIdType(IdType.COPY_OF_OBJECT_ID);
                    ProgramRegistry.setSection(ProgramSections.getEditSection(getCurrentViewEnum()));
                    HistoryManager.navigate(AppLocations.Locations.EDIT_CORE_PROGRAM.getLocation(), viewContext);
                }

            }
        });
        eventBus.addHandler(ProgramViewEvent.TYPE, new ProgramViewEvent.Handler() {
            @Override
            public void onEvent(ProgramViewEvent event) {
                actionBox.setSelectedIndex(0);
            }
        });
        eventBus.addHandler(ModelLoadedEvent.TYPE, new ModelLoadedEvent.Handler() {
            @Override
            public void onEvent(ModelLoadedEvent event) {
                resetActionList();
            }
        });
    }

    @Override
    protected void configureView() {
        super.configureView();
        addContentWidget(actionBox);
        initialized = true;
    }

    protected void resetActionList() {
        //Only allow modify with version option for an active course that id also the latest version
        ProgramStatus status = ProgramStatus.of(programModel.<String>get(ProgramConstants.STATE));
        String versionIndId = programModel.get(ProgramConstants.VERSION_IND_ID);
        Long sequenceNumber = programModel.get(ProgramConstants.VERSION_SEQUENCE_NUMBER);

        actionBox.clear();
        if (status == ProgramStatus.ACTIVE) {
            programRemoteService.isLatestVersion(versionIndId, sequenceNumber, new KSAsyncCallback<Boolean>() {
                public void onSuccess(Boolean isLatest) {
                    actionBox.setList(ActionType.getValues(isLatest));
                }
            });
        } else {
            actionBox.setList(ActionType.getValues(false));
        }
    }
}
