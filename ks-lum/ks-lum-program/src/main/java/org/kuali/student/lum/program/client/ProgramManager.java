package org.kuali.student.lum.program.client;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.edit.ProgramEditController;
import org.kuali.student.lum.program.client.view.ProgramViewController;
import org.kuali.student.lum.program.client.view.variation.VariationViewController;

/**
 * @author Igor
 */
public class ProgramManager {

    private ProgramViewController programViewController;

    private ProgramEditController programEditController;

    private VariationViewController variationViewController;

    protected DataModel programModel;

    private ViewContext viewContext = new ViewContext();

    public ProgramManager() {
        programModel = new DataModel();
    }

    public ProgramViewController getProgramViewController() {
        programModel.resetRoot();
        if (programViewController == null) {
            programViewController = new ProgramViewController("Programs", programModel, viewContext);
        }
        return programViewController;
    }

    public VariationViewController getVariationViewController() {
        programModel.setRoot(VariationRegistry.getData());
        variationViewController = new VariationViewController(programModel, viewContext);
        return variationViewController;
    }

    public ProgramEditController getProgramEditController() {
        programModel.resetRoot();
        if (programEditController == null) {
            programEditController = new ProgramEditController("Programs", programModel, viewContext);
        }
        return programEditController;
    }
}
