package org.kuali.student.lum.program.client.bacc.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.bacc.BaccController;

/**
 * @author Igor
 */
public class ViewBaccController extends BaccController{


    /**
     * Constructor.
     *
     * @param programModel
     */
    public ViewBaccController(String name, DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super(name, programModel, viewContext, eventBus);
        configurer = GWT.create(ViewBaccConfigurer.class);
    }
}
