package org.kuali.student.lum.program.client.credential;

import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.credential.edit.CredentialEditController;
import org.kuali.student.lum.program.client.credential.view.CredentialViewController;
import org.kuali.student.lum.program.client.events.ProgramViewEvent;
import org.kuali.student.lum.program.client.versions.ProgramVersionsController;
import org.kuali.student.lum.program.client.widgets.ProgramSideBar;

/**
 * @author Igor
 */
public class CredentialManager {

    private CredentialViewController credentialViewController;

    private CredentialEditController credentialEditController;

    private ProgramVersionsController programVersionsController;

    protected DataModel model;

    private ViewContext viewContext = new ViewContext();

    private static HandlerManager eventBus;

    public CredentialManager() {
        eventBus = new HandlerManager(null);
        model = new DataModel();
    }

    public CredentialViewController getBaccViewController() {
        model.resetRoot();
        if (credentialViewController == null) {
            credentialViewController = new CredentialViewController(model, viewContext, eventBus);
        }
        eventBus.fireEvent(new ProgramViewEvent());
        return credentialViewController;
    }

    public CredentialEditController getBaccEditController() {
        model.resetRoot();
        if (credentialEditController == null) {
            credentialEditController = new CredentialEditController(model, viewContext, eventBus);
        }
        return credentialEditController;
    }

    public ProgramVersionsController getProgramVersionsController() {
        if (programVersionsController == null) {
            programVersionsController = new ProgramVersionsController(model, ProgramSideBar.Type.CREDENTIAL, viewContext, eventBus);
        }
        return programVersionsController;
    }

    public static HandlerManager getEventBus() {
        return eventBus;
    }
}
