package org.kuali.student.lum.program.client.core.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.core.CoreController;

/**
 * @author Igor
 */
public class CoreViewController extends CoreController {

    /**
     * Constructor.
     *
     * @param programModel
     */
    public CoreViewController(DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super(programModel, viewContext, eventBus);
        configurer = GWT.create(CoreViewConfigurer.class);
    }
}
