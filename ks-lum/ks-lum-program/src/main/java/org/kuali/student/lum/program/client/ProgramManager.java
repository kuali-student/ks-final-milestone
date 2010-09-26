package org.kuali.student.lum.program.client;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.edit.ProgramEditController;
import org.kuali.student.lum.program.client.variation.edit.VariationEditController;
import org.kuali.student.lum.program.client.variation.view.VariationViewController;
import org.kuali.student.lum.program.client.view.ProgramViewController;
import com.google.gwt.event.shared.HandlerManager;

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

    private HandlerManager eventBus = new HandlerManager(null);

    public ProgramManager() {
        programModel = new DataModel();
    }

    public ProgramViewController getProgramViewController() {
        programModel.resetRoot();
        if (programViewController == null) {
            programViewController = new ProgramViewController("Programs", programModel, viewContext, eventBus);
        }
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
        programModel.setRoot(VariationRegistry.getData());
        variationEditController = new VariationEditController(name, programModel, viewContext, eventBus);
        return variationEditController;
    }

    public ProgramEditController getProgramEditController() {
        programModel.resetRoot();
        if (programEditController == null) {
            programEditController = new ProgramEditController("Programs", programModel, viewContext, eventBus);
        }
        return programEditController;
    }
}
