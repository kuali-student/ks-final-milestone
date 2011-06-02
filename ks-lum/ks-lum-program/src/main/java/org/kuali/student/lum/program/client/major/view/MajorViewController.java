package org.kuali.student.lum.program.client.major.view;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.Data.Property;
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
import org.kuali.student.lum.program.client.events.ModelLoadedEvent;
import org.kuali.student.lum.program.client.events.ProgramViewEvent;
import org.kuali.student.lum.program.client.major.ActionType;
import org.kuali.student.lum.program.client.major.MajorController;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerManager;


public class MajorViewController extends MajorController {

    private final DropdownList actionBox = new DropdownList(ActionType.getValues());

    /**
     * Constructor.
     *
     * @param programModel
     */
    public MajorViewController(DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super(programModel, viewContext, eventBus);
        configurer = GWT.create(MajorViewConfigurer.class);
        initHandlers();
    }

    private void initHandlers() {
        actionBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                ActionType actionType = ActionType.of(actionBox.getSelectedValue());
                ViewContext viewContext = getViewContext();
                if (actionType == ActionType.MODIFY) {
                    ProgramRegistry.setSection(ProgramSections.getEditSection(getCurrentViewEnum()));
                    HistoryManager.navigate(AppLocations.Locations.EDIT_PROGRAM.getLocation(), viewContext);
                } else if (actionType == ActionType.MODIFY_VERSION) {
                    String versionIndId = getStringProperty(ProgramConstants.VERSION_IND_ID);
                    viewContext.setId(versionIndId);
                    viewContext.setIdType(IdType.COPY_OF_OBJECT_ID);
                    HistoryManager.navigate(AppLocations.Locations.EDIT_PROGRAM.getLocation(), viewContext);
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
                String type = context.getAttributes().get(ProgramConstants.TYPE);
                if (type != null) {
                    context.getAttributes().remove(ProgramConstants.TYPE);
                    if (type.equals(ProgramConstants.VARIATION_TYPE_KEY)) {
                        showVariationView();
                    } else {
                    	//Take out the vairationId if it exists for cleaner navigation
                    	context.getAttributes().remove(ProgramConstants.VARIATION_ID);
                        showView(ProgramSections.VIEW_ALL);
                    }
                } else {
                	context.getAttributes().remove(ProgramConstants.VARIATION_ID);
                    showView(ProgramSections.VIEW_ALL);
                }
            }
        });
    }

    protected void resetActionList() {
    	//Only allow modify with version option for an active course that id also the latest version
    	ProgramStatus status = ProgramStatus.of(programModel);
        String versionIndId = getStringProperty(ProgramConstants.VERSION_IND_ID);
        Long sequenceNumber = programModel.get(ProgramConstants.VERSION_SEQUENCE_NUMBER);
                       
        actionBox.clear();
        if (status == ProgramStatus.ACTIVE){
        	programRemoteService.isLatestVersion(versionIndId, sequenceNumber, new KSAsyncCallback<Boolean>(){
				public void onSuccess(Boolean isLatest) {
			        actionBox.setList(ActionType.getValues(isLatest));				
				}        	
	        });
        } else {
        	actionBox.setList(ActionType.getValues(false));
        }
    }

    private void showVariationView() {
        String variationId = context.getAttributes().get(ProgramConstants.VARIATION_ID);
        if (variationId != null) {
            final Data variationMap = getDataProperty(ProgramConstants.VARIATIONS);
            if (variationMap != null) {
                int row = 0;
                for (Property p : variationMap) {
                    final Data variationData = p.getValue();
                    if (variationData != null) {
                        if (variationData.get(ProgramConstants.ID).equals(variationId)) {
                            //FIXME: Find a better way to do this.
                            // We shouldn't be maintaining two separate datamodels for progs and variations
                            Data credData = getDataProperty(ProgramConstants.CREDENTIAL_PROGRAM);
                            variationData.set(ProgramConstants.CREDENTIAL_PROGRAM, credData);
                            ProgramRegistry.setData(variationData);
                            ProgramRegistry.setRow(row);
                        }
                        row++;
                    }
                }
                HistoryManager.navigate(AppLocations.Locations.VIEW_VARIATION.getLocation(), context);
            }
        }
    }

    @Override
    protected void configureView() {
        super.configureView();
        addContentWidget(actionBox);
        initialized = true;
    }
}
