package org.kuali.student.lum.program.client;

import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.events.MajorViewEvent;
import org.kuali.student.lum.program.client.major.edit.ProgramEditController;
import org.kuali.student.lum.program.client.major.view.ProgramViewController;
import org.kuali.student.lum.program.client.variation.edit.VariationEditController;
import org.kuali.student.lum.program.client.variation.view.VariationViewController;

/**
 * @author Igor
 */
public class ProgramManager {

    private ProgramViewController programViewController;

    private ProgramEditController programEditController;

    private VariationViewController variationViewController;

    private VariationEditController variationEditController;

    protected DataModel programModel;

    private ViewContext viewContext = new ViewContext();

    private static HandlerManager eventBus = new HandlerManager(null);

    public ProgramManager() {
        programModel = new DataModel();
    }

    public ProgramViewController getProgramViewController() {
        programModel.resetRoot();
        if (programViewController == null) {
            programViewController = new ProgramViewController("Programs", programModel, viewContext, eventBus);
        }
        eventBus.fireEvent(new MajorViewEvent());
        return programViewController;
    }

    public VariationViewController getVariationViewController() {
        String name = programViewController.getProgramName();
        programModel.setRoot(VariationRegistry.getData());
        variationViewController = new VariationViewController(name, programModel, viewContext, eventBus);
        return variationViewController;
    }

    public VariationEditController getVariationEditController() {
        String name = programEditController.getProgramName();
        DataModel variationModel = new DataModel();
        variationModel.setDefinition(programModel.getDefinition());
        variationModel.setRoot(VariationRegistry.getData());
        variationEditController = new VariationEditController(name, variationModel, viewContext, eventBus);
        return variationEditController;
    }

    public ProgramEditController getProgramEditController() {
        programModel.resetRoot();
        programEditController = new ProgramEditController("Programs", programModel, viewContext, eventBus);
        return programEditController;
    }

    public static HandlerManager getEventBus() {
        return eventBus;
    }
}
