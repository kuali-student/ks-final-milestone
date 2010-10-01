package org.kuali.student.lum.program.client.bacc;

import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.ProgramController;

/**
 * @author Igor
 */
public class BaccController extends ProgramController{
    /**
     * Constructor.
     *
     * @param programModel
     */
    public BaccController(String name, DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super(name, programModel, viewContext, eventBus);
    }
}
