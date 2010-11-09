package org.kuali.student.lum.program.client.bacc;

import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.bacc.edit.BaccEditController;
import org.kuali.student.lum.program.client.bacc.view.BaccViewController;
import org.kuali.student.lum.program.client.events.ProgramViewEvent;

/**
 * @author Igor
 */
public class CredentialManager {

    private BaccViewController baccViewController;

    private BaccEditController baccEditController;

    protected DataModel model;

    private ViewContext viewContext = new ViewContext();

    private static HandlerManager eventBus = new HandlerManager(null);

    public CredentialManager() {
        model = new DataModel();
    }

    public BaccViewController getBaccViewController() {
        model.resetRoot();
        if (baccViewController == null) {
            baccViewController = new BaccViewController(model, viewContext, eventBus);
        }
        eventBus.fireEvent(new ProgramViewEvent());
        return baccViewController;
    }

    public BaccEditController getBaccEditController() {
        model.resetRoot();
        if (baccEditController == null) {
            baccEditController = new BaccEditController(model, viewContext, eventBus);
        }
        return baccEditController;
    }
}
