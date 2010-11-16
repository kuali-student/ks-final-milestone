package org.kuali.student.lum.program.client.bacc.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.common.client.widgets.DropdownList;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramRegistry;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.bacc.CredentialController;
import org.kuali.student.lum.program.client.events.ProgramViewEvent;
import org.kuali.student.lum.program.client.major.ActionType;

/**
 * @author Igor
 */
public class BaccViewController extends CredentialController {

    private DropdownList actionBox = new DropdownList(ActionType.getValues());

    /**
     * Constructor.
     *
     * @param programModel
     */
    public BaccViewController(DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super(programModel, viewContext, eventBus);
        configurer = GWT.create(BaccViewConfigurer.class);
        bind();
    }

    private void bind() {
        actionBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                ActionType actionType = ActionType.of(actionBox.getSelectedValue());
            	ViewContext viewContext = getViewContext();
                if (actionType == ActionType.MODIFY) {
                    HistoryManager.navigate(AppLocations.Locations.EDIT_BACC_PROGRAM.getLocation(), getViewContext());
                    ProgramRegistry.setSection(ProgramSections.getEditSection(getCurrentViewEnum()));
                } else if (actionType == ActionType.MODIFY_VERSION){
                	String versionIndId = programModel.get(ProgramConstants.VERSION_IND_ID);
                	viewContext.setId(versionIndId);
                    viewContext.setIdType(IdType.COPY_OF_OBJECT_ID);
                	HistoryManager.navigate(AppLocations.Locations.EDIT_BACC_PROGRAM.getLocation(), viewContext);                    
                }                
            }
        });
        eventBus.addHandler(ProgramViewEvent.TYPE, new ProgramViewEvent.Handler() {
            @Override
            public void onEvent(ProgramViewEvent event) {
                actionBox.setSelectedIndex(0);
            }
        });
    }

    @Override
    protected void configureView() {
        super.configureView();
        addContentWidget(actionBox);
        initialized = true;
    }
}
