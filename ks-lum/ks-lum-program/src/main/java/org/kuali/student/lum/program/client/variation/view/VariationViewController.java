package org.kuali.student.lum.program.client.variation.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.variation.VariationController;

public class VariationViewController extends VariationController {

    public VariationViewController(String name, DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super("Variations", programModel, viewContext, eventBus);
        configurer = GWT.create(VariationViewConfigurer.class);
    }
}
