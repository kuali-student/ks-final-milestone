package org.kuali.student.lum.program.client;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.events.MajorViewEvent;
import org.kuali.student.lum.program.client.major.edit.MajorEditController;
import org.kuali.student.lum.program.client.major.view.MajorViewController;
import org.kuali.student.lum.program.client.variation.edit.VariationEditController;
import org.kuali.student.lum.program.client.variation.view.VariationViewController;

import com.google.gwt.event.shared.HandlerManager;

/**
 * @author Igor
 */
public class ProgramManager {

    private MajorViewController majorViewController;

    private MajorEditController majorEditController;

    private VariationViewController variationViewController;

    private VariationEditController variationEditController;

    protected DataModel programModel;

    private ViewContext viewContext = new ViewContext();

    private static HandlerManager eventBus = new HandlerManager(null);

    public ProgramManager() {
        programModel = new DataModel();
    }

    public MajorViewController getProgramViewController() {
        programModel.resetRoot();
        if (majorViewController == null) {
            majorViewController = new MajorViewController("Programs", programModel, viewContext, eventBus);
        }
        eventBus.fireEvent(new MajorViewEvent());
        return majorViewController;
    }

    public VariationViewController getVariationViewController() {
        String name = majorViewController.getProgramName();
        DataModel variationModel = new DataModel();
        variationModel.setDefinition(programModel.getDefinition());
        variationModel.setRoot(ProgramRegistry.getData());
        variationViewController = new VariationViewController(name, variationModel, viewContext, eventBus);
        return variationViewController;
    }

    public VariationEditController getVariationEditController() {
        String name = majorEditController.getProgramName();
        DataModel variationModel = new DataModel();
        variationModel.setDefinition(programModel.getDefinition());
        variationModel.setRoot(ProgramRegistry.getData());
        variationEditController = new VariationEditController(name, variationModel, viewContext, eventBus);
        return variationEditController;
    }

    public MajorEditController getProgramEditController() {
        programModel.resetRoot();
        if (majorEditController == null)  {
            majorEditController = new MajorEditController("Programs", programModel, viewContext, eventBus);           
        }
        return majorEditController;
    }

    public static HandlerManager getEventBus() {
        return eventBus;
    }
}
