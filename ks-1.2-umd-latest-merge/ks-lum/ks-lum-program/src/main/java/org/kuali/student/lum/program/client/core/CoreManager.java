package org.kuali.student.lum.program.client.core;

import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.core.edit.CoreEditController;
import org.kuali.student.lum.program.client.core.view.CoreViewController;
import org.kuali.student.lum.program.client.events.ProgramViewEvent;
import org.kuali.student.lum.program.client.versions.ProgramVersionsController;
import org.kuali.student.lum.program.client.widgets.ProgramSideBar;

/**
 * @author Igor
 */
public class CoreManager {

    private CoreViewController viewController;

    private CoreEditController editController;

    private ProgramVersionsController programVersionsController;

    protected DataModel model;

    private ViewContext viewContext = new ViewContext();

    private static HandlerManager eventBus;

    public CoreManager() {
        eventBus = new HandlerManager(null);
        model = new DataModel();
    }

    public CoreViewController getViewController() {
        model.resetRoot();
        if (viewController == null) {
            viewController = new CoreViewController(model, viewContext, eventBus);
        }
        eventBus.fireEvent(new ProgramViewEvent());
        return viewController;
    }

    public CoreEditController getEditController() {
        model.resetRoot();
        if (editController == null) {
            editController = new CoreEditController(model, viewContext, eventBus);
        }
        return editController;
    }

    public ProgramVersionsController getProgramVersionsController() {
        if (programVersionsController == null) {
            programVersionsController = new ProgramVersionsController(model, ProgramSideBar.Type.CORE, viewContext, eventBus);
        }
        return programVersionsController;
    }

    public static HandlerManager getEventBus() {
        return eventBus;
    }
}
