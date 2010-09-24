package org.kuali.student.lum.program.client.variation.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.lum.program.client.ProgramController;
import org.kuali.student.lum.program.client.variation.VariationController;
import org.kuali.student.lum.program.client.variation.view.VariationViewConfigurer;

public class VariationViewController extends VariationController {

    public VariationViewController(String name, DataModel programModel, ViewContext viewContext) {
        super("Variations", programModel, viewContext);
        configurer = GWT.create(VariationViewConfigurer.class);
    }
}
