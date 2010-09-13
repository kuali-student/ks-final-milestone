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
        if (programViewController == null) {
            programModel.resetRoot();
            programViewController = new ProgramViewController("Programs", programModel, viewContext);
        } else {
            programModel.resetRoot();
        }
        return programViewController;
    }

    public VariationViewController getVariationViewController() {
        if (variationViewController == null) {
            variationViewController = new VariationViewController(programModel, viewContext);
        }
        return variationViewController;
    }

    public ProgramEditController getProgramEditController() {
        if (programEditController == null) {
            programModel.resetRoot();
            programEditController = new ProgramEditController("Programs", programModel, viewContext);
        } else {
            programModel.resetRoot();
        }
        return programEditController;
    }
}
