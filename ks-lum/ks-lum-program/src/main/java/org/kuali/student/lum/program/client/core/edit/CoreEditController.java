package org.kuali.student.lum.program.client.core.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.core.CoreController;

/**
 * @author Igor
 */
public class CoreEditController extends CoreController {

    /**
     * Constructor.
     *
     * @param programModel
     */
    public CoreEditController(String name, DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super(name, programModel, viewContext, eventBus);
        configurer = GWT.create(CoreEditConfigurer.class);
    }
}
