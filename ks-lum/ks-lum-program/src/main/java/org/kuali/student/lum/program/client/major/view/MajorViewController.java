package org.kuali.student.lum.program.client.major.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Data.Property;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.common.client.widgets.DropdownList;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramRegistry;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.events.MajorViewEvent;
import org.kuali.student.lum.program.client.events.ModelLoadedEvent;
import org.kuali.student.lum.program.client.major.ActionType;
import org.kuali.student.lum.program.client.major.MajorController;


public class MajorViewController extends MajorController {

    private DropdownList actionBox = new DropdownList(ActionType.getValues());

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
                if (actionType == ActionType.MODIFY) {
                    HistoryManager.navigate(AppLocations.Locations.EDIT_PROGRAM.getLocation(), getViewContext());
                    ProgramRegistry.setSection(ProgramSections.getEditSection(getCurrentViewEnum()));
                }
            }
        });
        eventBus.addHandler(MajorViewEvent.TYPE, new MajorViewEvent.Handler() {
            @Override
            public void onEvent(MajorViewEvent event) {
                actionBox.setSelectedIndex(0);
            }
        });
        eventBus.addHandler(ModelLoadedEvent.TYPE, new ModelLoadedEvent.Handler() {
            @Override
            public void onEvent(ModelLoadedEvent event) {
                String type = context.getAttributes().get(ProgramConstants.TYPE);
                if (type != null) {
                    context.getAttributes().remove(ProgramConstants.TYPE);
                    if (type.equals(ProgramConstants.VARIATION_TYPE_KEY)) {
                        showVariationView();
                    } else {
                        showView(ProgramSections.VIEW_ALL);
                    }
                } else {
                    showView(ProgramSections.VIEW_ALL);
                }
            }
        });
    }

    private void showVariationView() {
        String variationId = context.getAttributes().get(ProgramConstants.VARIATION_ID);
        if (variationId != null) {
            context.getAttributes().remove(ProgramConstants.VARIATION_ID);
            final Data variationMap = programModel.get(ProgramConstants.VARIATIONS);
            if (variationMap != null) {
                int row = 0;
                for (Property p : variationMap) {
                    final Data variationData = p.getValue();
                    if (variationData != null) {
                        if (variationData.get(ProgramConstants.ID).equals(variationId)) {
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
