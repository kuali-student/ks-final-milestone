package org.kuali.student.lum.program.client.variation.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.major.MajorController;
import org.kuali.student.lum.program.client.variation.VariationController;

public class VariationViewController extends VariationController {

    public VariationViewController(DataModel programModel, ViewContext viewContext, HandlerManager eventBus, MajorController majorController) {
        super(programModel, viewContext, eventBus, majorController);
        configurer = GWT.create(VariationViewConfigurer.class);
        setDefaultView(ProgramSections.VIEW_ALL);
    }

    @Override
    protected void navigateToParent() {
        HistoryManager.navigate(AppLocations.Locations.VIEW_PROGRAM.getLocation(), getViewContext());
    }
}
