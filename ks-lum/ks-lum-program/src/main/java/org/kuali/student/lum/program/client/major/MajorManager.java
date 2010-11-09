package org.kuali.student.lum.program.client.major;

import com.google.gwt.event.shared.HandlerManager;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.ProgramRegistry;
import org.kuali.student.lum.program.client.core.edit.CoreEditController;
import org.kuali.student.lum.program.client.core.view.CoreViewController;
import org.kuali.student.lum.program.client.events.ProgramViewEvent;
import org.kuali.student.lum.program.client.major.edit.MajorEditController;
import org.kuali.student.lum.program.client.major.view.MajorViewController;
import org.kuali.student.lum.program.client.variation.edit.VariationEditController;
import org.kuali.student.lum.program.client.variation.view.VariationViewController;

/**
 * @author Igor
 */
public class MajorManager {

    private MajorViewController majorViewController;

    private MajorEditController majorEditController;

    private VariationViewController variationViewController;

    private VariationEditController variationEditController;

    private CoreViewController coreViewController;

    private CoreEditController coreEditController;

    protected DataModel programModel;

    private ViewContext viewContext = new ViewContext();

    private static HandlerManager eventBus = new HandlerManager(null);

    public MajorManager() {
        programModel = new DataModel();
    }

    public MajorViewController getProgramViewController() {
        programModel.resetRoot();
        getMajorViewController();
        eventBus.fireEvent(new ProgramViewEvent());
        return majorViewController;
    }


    public VariationViewController getVariationViewController() {
        DataModel variationModel = new DataModel();
        variationModel.setDefinition(programModel.getDefinition());
        variationModel.setRoot(ProgramRegistry.getData());
        variationViewController = new VariationViewController(variationModel, viewContext, eventBus, majorViewController);
        return variationViewController;
    }

    public VariationEditController getVariationEditController() {
        DataModel variationModel = new DataModel();
        variationModel.setDefinition(programModel.getDefinition());
        variationModel.setRoot(ProgramRegistry.getData());
        variationEditController = new VariationEditController(variationModel, viewContext, eventBus, majorEditController);
        return variationEditController;
    }

    public MajorEditController getProgramEditController() {
        programModel.resetRoot();
        return getMajorEditController();
    }

    public static HandlerManager getEventBus() {
        return eventBus;
    }

    private MajorEditController getMajorEditController() {
        if (majorEditController == null) {
            majorEditController = new MajorEditController(programModel, viewContext, eventBus);
        }
        return majorEditController;
    }

    private MajorViewController getMajorViewController() {
        if (majorViewController == null) {
            majorViewController = new MajorViewController(programModel, viewContext, eventBus);
        }
        return majorViewController;
    }

    public CoreViewController getCoreViewController() {
        programModel.resetRoot();
        if (coreViewController == null) {
            coreViewController = new CoreViewController(programModel, viewContext, eventBus);
        }
        return coreViewController;
    }

    public CoreEditController getCoreEditController() {
        programModel.resetRoot();
        if (coreEditController == null) {
            coreEditController = new CoreEditController(programModel, viewContext, eventBus);
        }
        return coreEditController;
    }
}
